$(function() {

  getAllRetros();

  function getAllRetros(){
    $.ajax({
      url: 'http://localhost:8081/retros',
      method: 'GET',
      xhrFields: {
        withCredentials: true
      }
    })
    .done(function( response,  textStatus, xhr ) {
      if( xhr.status === 200 ) {
        let results = response.length;
        for (let i=0; i<results; i++){
          console.log(response[i]);
          appendRetroToTable(response[i]);
        }
      }
    });
  }

  // Create Retro
  $('#btn__create-retro').click(function(e){
    e.preventDefault();

    $.ajax({
      url: 'assets/json/addRetro.json',
      method: 'POST',
      data: { 
        name: $('input[name="retro_name"]').val() 
      }
    })
    .done(function( response, textStatus, xhr ) {
      if( xhr.status === 200 ) {
        appendRetroToTable(response);
        $('#overlay__create-retro').removeClass('open');
      }
    });
  });

  //Update Retro
  //1. Get Info and Open Modal
  $(document).on('click','.retro__action-update', function(e){
    e.preventDefault();
    let id = $(this).data('id');
    let target = $(this).data('target');

    $.ajax({
      url: 'assets/json/findRetro.json',
      //url: 'http://localhost:8080/retros/'+id,
      method: 'GET',
    })
    .done(function( response, textStatus, xhr ) {
      if(xhr.status === 200) {
        console.log(response)
        $('#overlay__update-retro').addClass('open');
        $('#retro_name_update').val(response.name);
        $('#retro_id_update').val(response.id);
        $('#retro_target').val(target);
      }
    });
  });

  // 2. Send Data and Update / Close Modal
  $('#btn__update-retro').click(function(e){
    e.preventDefault();
    let target =  $('#retro_target').val();

    $.ajax({
      url: 'assets/json/updateRetro.json',
      method: 'POST',
      data: { 
        id : $('#retro_id_update').val(),
        name: $('#retro_name_update').val()
      }
    })
    .done(function( response, textStatus, xhr ) {
      if( xhr.status === 200 ) {
        $('#overlay__update-retro').removeClass('open');
        $(target).find('.retro__id a').text(response.name);
      }
    });
  });

  //Delete Retro
  $(document).on('click','.retro__action-delete', function(e){
    e.preventDefault()
    let target = $(this).data('target');
    let url = 'http://localhost:8080/retros/'+$(this).data('id');
    
    $.ajax({
      url: 'assets/json/deleteRetro.json',
      //url: url,
      method: 'POST',
      //method: 'DELETE',
      data : {_method: "delete"}
    })
    .done(function( response, textStatus, xhr ) {
      if(xhr.status === 200 || xhr.status === 204) {
        $(target).remove();
      }
    });
  });

  function appendRetroToTable(item){
    let html = `<tr id="retro_${item.id}">
      <td class="retro__id"><a href="retro.html?id=${item.id}">${item.name}</a></td>
      <td class="actions">
        <a href="#" data-target="#retro_${item.id}" data-id="${item.id}" class="action retro__action-users"><img src="assets/images/icon-edit.svg" width="18">Users</a>
        <a href="#" data-target="#retro_${item.id}" data-id="${item.id}" class="action retro__action-update"><img src="assets/images/icon-edit.svg" width="18">Edit</a>
        <a href="#" data-target="#retro_${item.id}" data-id="${item.id}" class="action retro__action-delete"><img src="assets/images/icon-delete.svg" width="18">Delete</a>
      </td>
    </tr>`;
    $('#list-retros tbody').append(html);
  }

  // Preload all Users in Modal
  // getAllUsers();
  //
  // function getAllUsers(){
  //   $.ajax({
  //     url: 'assets/json/getAllUsers.json',
  //     method: 'GET'
  //   })
  //   .done(function( response, textStatus, xhr ) {
  //     if(xhr.status === 200){
  //       let results = response.length;
  //       for (let i=0; i<results; i++){
  //         appendUserToModal(response[i], i);
  //       }
  //     }
  //   });
  // }

  $(document).on('click','.retro__action-users', function(e){
    e.preventDefault();
    let id = $(this).data('id');
    //let target = $(this).data('target');
    $.ajax({
      url: 'assets/json/getUsersInRetro.json',
      //url: 'http://localhost:8080/retros/'+id+'/users',
      method: 'GET',
    })
    .done(function( response, textStatus, xhr ) {
      if(xhr.status === 200) {
      
        $('#overlay__add-users').addClass('open');
        $('.checkbox-users').data('id', id);
        let num = response.length;
        for (let i=0; i<num; i++) {
          let inTable = emailInTable(response[i].email);
        }
      }
    });
  });

  function emailInTable(email){
    $( '.user__row' ).each(function( index ) {
     let mailInRow = $(this).find('.user__email').text()
     if (mailInRow == email) {
      $(this).find('.checkbox-users').prop('checked', true);
     } 
    });
  }

  $(document).on('change','.checkbox-users', function(e){
    let checked = $(this).prop('checked');
    let retroID = $(this).data('id');
    let email = $(this).data('email');

    if(checked) {
      $.ajax({
        url: 'assets/json/addUser.json',
        //url: 'http://localhost:8080/retros/'+retroID+'/users/'+email,
        method: 'POST'
      })
      .done(function( response, textStatus, xhr ) {
        if(xhr.status === 200){
          console.log('aggregate it')
        }
      });
    } else {
      $.ajax({
        url: 'assets/json/removeUser.json',
        //url: 'http://localhost:8080/retros/'+retroID+'/users/'+email,
        method: 'POST'
      })
      .done(function( response, textStatus, xhr ) {
        if(xhr.status === 200 || xhr.status === 204 ){
          console.log('deleted it')
        }
      });
    }
  })

  

  function appendUserToModal(item){
    console.log(item);
    let html = `<tr class="user__row">
    <td>
      <label class="box-check">
        <input type="checkbox"
                class="checkbox-users"
                data-email="${item.email}"
                data-id="">
        <span class="checkmark"></span>
      </label>
    </td>
    <td class="user__name">${item.name}</td>
    <td class="user__email">${item.email}</td>
  </tr>`;
    $('#table-add-users tbody').append(html);
  }
});