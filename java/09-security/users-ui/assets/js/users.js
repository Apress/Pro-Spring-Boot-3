$(function() {

  var currentTarget = null;

  function getAuth(){
    return document.cookie.replace(/(?:(?:^|.*;\s*)auth\s*\=\s*([^;]*).*$)|^.*$/, '$1')
  }

  function getAllUsers(){
    console.log(getAuth())
    $.ajax({
      url: 'http://localhost:8080/users',
      method: 'GET',
      headers: {
        'Authorization': 'Basic ' + getAuth(),
        'Content-Type': 'application/json'
      }
    })
    .done(function( response, textStatus, xhr ) {
      if(xhr.status === 200){
        let results = response.length;
        for (let i=0; i<results; i++){
          appendUserToTable(response[i], i);
        }
      }
    });
  }

  // Create/Edit Users
  $('#btn__create-user').click(function(e){
    e.preventDefault();
    $('.message').text('').hide();

    save();

  });

  //Update Users
  $(document).on('click','.user__action-update', function(e){
    e.preventDefault();
    let target = $(this).data('target');

    currentTarget = target;

    let checked = $(target).find('td').eq(3).text().toLowerCase() == 'yes' ? true : false;

    $('input[name="name"]').val($(target).find('td').eq(0).text());
    $('input[name="email"]').val($(target).find('td').eq(1).text());
    $('input[name="password"]').val('');
    $('select[name="userRole"]').val($(target).find('td').eq(2).text());
    if (checked) {
      $('input[name="active"]').prop('checked', true);
    }
    $('#overlay__create-user').addClass('open');

    $('#btn__create-user').text('Update User')
    $('#updateUserTitle').text('Update User')
  });


  //Delete Users
  $(document).on('click','.user__action-delete', function(e){
    e.preventDefault()
    let target = $(this).data('target');
    let url = 'http://localhost:8080/users/'+$(this).data('email');

    $.ajax({
      url: url,
      method: 'DELETE',
      headers: {
        'Authorization': 'Basic ' + getAuth(),
        'Content-Type': 'application/json'
      }
    })
    .done(function( response, textStatus, xhr ) {
      console.log(xhr.status);
      if(xhr.status === 200 || xhr.status === 204) {
        $(target).remove();
      }
    });
  });

  function save() {
    let formData = {
      name      : $('input[name="name"]').val(),
      email     : $('input[name="email"]').val(),
      password  : $('input[name="password"]').val(),
      userRole  : [$('select[name="userRole"]').val()],
      active    : $('input[name="active"]').is(':checked')
    }

    let jsonData = JSON.stringify(formData);

    $.ajax({
      url: 'http://localhost:8080/users',
      method: 'POST',
      headers: {
        'Authorization': 'Basic ' + getAuth(),
        'Content-Type': 'application/json'
      },
      data: jsonData
    }).done(function( response, textStatus, xhr ) {
          if( xhr.status === 201 ) {

            if(currentTarget != null){
                $(currentTarget).remove();
                currentTarget = null;
            }

            appendUserToTable(response);
            $('#overlay__create-user').removeClass('open');

            clearFields();
            return true;
          }
        });
  }

  function clearFields(){
    $('input[name="name"]').val('');
    $('input[name="email"]').val('');
    $('input[name="password"]').val('');
    $('select[name="userRole"]').val('USER');

    $('#btn__create-user').text('Create User');
    $('#updateUserTitle').text('New User');
  }

  function appendUserToTable(item){
    let active = 'NO';
    if( item.active == true ) { active = 'YES'; }
    
    let targetName = item.email.replace('@','_');
    targetName = targetName.replace('.','_');

    let html = `<tr id="user_${targetName}">
        <td>${item.name}</td>
        <td><a href="" class="action-mail">${item.email}</a></td>
        <td>${item.userRole}</td>
        <td>${active}</td>
        <td class="actions">
          <a href="#" data-target="#user_${targetName}" data-email="${item.email}" class="action user__action-update"><img src="assets/images/icon-edit.svg" width="18" alt="Edit"> Edit</a>
          <a href="#"  data-target="#user_${targetName}" data-email=${item.email} class="action user__action-delete"><img src="assets/images/icon-delete.svg" width="18" alt="Delete"> Delete</a>
        </td>
      </tr>`;
    $('#list-users tbody').append(html);
  }


  getAllUsers();




});