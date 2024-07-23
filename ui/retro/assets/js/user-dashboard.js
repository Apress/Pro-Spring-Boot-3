$(function() {

  getAllRetros();

  function getAllRetros(){
    let email = localStorage.getItem('ls_email');

    $.ajax({
      url: 'assets/json/getAllRetros.json',
      method: 'GET'
    })
    .done(function( response,  textStatus, xhr ) {
      if( xhr.status === 200 ) {
        let results = response.length;
        for (let i=0; i<results; i++){
          appendRetroToList(response[i]);
        }
      }
    });
  }

  function appendRetroToList(item){
    let html = `<li >
                  <a href="retro.html?id=${item.id}">${item.name}</a>
                </li>`;
    $('.retros-list').append(html);
  }
});