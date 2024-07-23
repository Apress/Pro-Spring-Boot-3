$(function() {

  function setAuthCookie(username, password) {
    const authCookie = btoa(username + ':' + password);
    document.cookie = 'auth=' + authCookie + '; path=/';
  }

  $('#btn__login').click(function(e) {
    e.preventDefault();
    let user_email = $('input[name="user_email"]').val();
    let user_password = $('input[name="user_password"]').val();
    $.ajax({
      url: 'http://localhost:8080/users/'+user_email,
      method: 'GET',
      headers: {
        'Authorization': 'Basic ' + btoa(user_email + ':' + user_password),
        'Content-Type': 'application/json'
      }
    })
    .done(function( response, textStatus, xhr ) {
      console.log(response)
      setAuthCookie(user_email, user_password);
      if(xhr.status === 200 &&
        response.active === true &&
        response.email === user_email &&
        response.password === user_password ) {

        let isAdmin = response.userRole.includes("ADMIN");

        localStorage.setItem('ls_name', response.name);
        localStorage.setItem('ls_email', response.email);
        localStorage.setItem('ls_gravatar', response.gravatarUrl);
        localStorage.setItem('ls_role', response.userRole);

        // if (isAdmin){
        //   window.location.href = "admin-dashboard.html";
        //   return;
        // }
        window.location.href = "admin-users.html";
      }
    });
  });
});