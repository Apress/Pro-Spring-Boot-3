$(function() {

  getAllUsers();
  
  function getAllUsers(){
    $.ajax({
      url: 'assets/json/getAllUsers.json',
      method: 'GET'
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

  // Create Users
  $('#btn__create-user').click(function(e){
    e.preventDefault();
    $('.message').text('').hide();

    $.ajax({
      url: 'assets/json/addUser.json',
      method: 'POST',
      data: { 
        name      : $('input[name="name"]').val(),
        email     : $('input[name="email"]').val(),
        password  : $('input[name="password"]').val(),
        userRole  :  $('input[name="userRole"]').val(),
        active    : true
      }
    })
    .done(function( response, textStatus, xhr ) {
      if( xhr.status === 200 ) {
        appendUserToTable(response);
        $('#overlay__create-user').removeClass('open');
      }
    });
  });

  //Delete Users
  $(document).on('click','.user__action-delete', function(e){
    e.preventDefault()
    let target = $(this).data('target');
    let url = 'http://localhost:8080/users/'+$(this).data('email');

    $.ajax({
      url: 'assets/json/deleteRetro.json',
      //url: url,
      method: 'POST',
      //method: 'DELETE',
      data : {_method: "delete"}
    })
    .done(function( response, textStatus, xhr ) {
      console.log(xhr.status);
      if(xhr.status === 200 || xhr.status === 204) {
        $(target).remove();
      }
    });
  });
  
  function appendUserToTable(item){
    let active = 'NO';
    if( item.active == true ) { active = 'YES'; }
    
    let targetName = item.email.replace('@','_');
    targetName = targetName.replace('.','_');

    let html = `<tr id="user_${targetName}">
        <td>${item.name}</td>
        <td><a href="" class="action-mail">${item.email}</a></td>
        <td>${item.userRole[0]}</td>
        <td>${active}</td>
        <td class="actions">
          <a href="#" data-target="#user_${targetName}" data-email="${item.email}" class="action user__action-update"><img src="assets/images/icon-edit.svg" width="18" alt="Edit"> Edit</a>
          <a href="#"  data-target="#user_${targetName}" data-email=${item.email} class="action user__action-delete"><img src="assets/images/icon-delete.svg" width="18" alt="Delete"> Delete</a>
        </td>
      </tr>`;
    $('#list-users tbody').append(html);
  }
});