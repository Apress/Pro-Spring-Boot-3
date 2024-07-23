$(function() {

  $('#btn__login').click(function(e) {
    e.preventDefault();
    let user_email = $('input[name="user_email"]').val();
    let user_password = $('input[name="user_password"]').val();
    $.ajax({
      url: 'http://localhost:8081/login',
      method: 'POST',
      data: {
        username: user_email,
        password: user_password
      },
        xhrFields: {
            withCredentials: true
        },
      headers:{
        'Access-Control-Allow-Origin': '*'
      }
    })
        .done(function( response, textStatus, xhr ) {
          console.log(response)

          if(xhr.status === 200 &&
              response.active === true &&
              response.email === user_email &&
              response.password === user_password ) {

            let isAdmin = response.userRole.includes("ADMIN");

            localStorage.setItem('ls_name', response.name);
            localStorage.setItem('ls_email', response.email);
            localStorage.setItem('ls_gravatar', response.gravatarUrl);
            localStorage.setItem('ls_role', response.userRole);

            document.cookie = xhr.getResponseHeader('x-myretro');

            console.log(xhr.getAllResponseHeaders());

            if (isAdmin){
              window.location.href = "admin-dashboard.html";
            }else {
              window.location.href = "admin-users.html";
            }

            return;
          }
        });
  });
});