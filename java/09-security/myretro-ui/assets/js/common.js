$(function() {

  $('.open-modal').click(function(e){
    console.log('open')
    e.preventDefault();
    let target = $(this).data('target');
    $(target).addClass('open');
  });

  $('.close-modal').click(function(e){
    console.log('close')
    e.preventDefault();
    let target = $(this).data('target');
    $(target).removeClass('open');
  });

  $('#btn__logout').click(function(e){
    e.preventDefault();
    localStorage.removeItem('ls_name');
    localStorage.removeItem('ls_email');
    localStorage.removeItem('ls_gravatar');
    localStorage.removeItem('ls_role');
    window.location.href = "login.html";
  })

  $('#btn__profile').click(function(e){
    e.preventDefault();
    $('#profile').slideToggle();
    if(!$('#profile').hasClass('open')) {
      $('#profile').addClass('open');
      $(this).text('Close Profile');
    } else {
      $('#profile').removeClass('open');
      $(this).text('My Profile');
    }
  })

  function checkLogin(){
    console.log(localStorage.getItem('ls_email'))
    if (localStorage.getItem('ls_email') != null ) {
      $('#ls_name').text(localStorage.getItem('ls_name'));
      $('#ls_gravatar').attr('src', localStorage.getItem('ls_gravatar'));
      $('#profile__data-name').text(localStorage.getItem('ls_name'))
      $('#profile__data-email').text(localStorage.getItem('ls_email'))
      $('#profile__data-role').text(localStorage.getItem('ls_role'))
      $('#profile__data-gravatar').attr('src',localStorage.getItem('ls_gravatar'))
    } else {
      window.location.href = "login.html";
    }
  }

  checkLogin();

});