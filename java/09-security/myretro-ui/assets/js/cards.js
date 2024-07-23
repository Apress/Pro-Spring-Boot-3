$(function() {

  
  let url = new URL(window.location.href)
  let params = new URLSearchParams(url.search);
  const idRetro = params.get('id')
  console.log(idRetro);

  function loadCards(){
    $.ajax({
      url: 'http://localhost:8081/retros/'+idRetro,
      method: 'GET',
      xhrFields: {
        withCredentials: true
      }
    })
    .done(function( response, textStatus, xhr ) {
      if(xhr.status === 200){
        $('#retro_name').text(response.name);
        let cards = response.cards;
        for (let i=0; i<cards.length; i++){
          console.log(cards[i]);
          populateCards(cards[i], cards[i].cardType);
        }
      }
    });
  }

  function populateCards(card, cardType){
    let container;

    switch (cardType) {
      case 'HAPPY':
        container = '#retro__comments-happy';
        break;
      case 'MEH':
        container = '#retro__comments-meh';
        break;
      case 'SAD':
        container = '#retro__comments-sad';
        break;
      default:
        container = '#retro__comments-happy';
    }

    let html = `<li id="${card.id}">
          <div class="comment">
            <div class="text-comment">
              ${card.comment}
            </div>
            <div class="actions">          
              <div class="edit-comment">
                <button class="btn__card-edit" data-id="${card.id}" data-target="#${card.id}">
                  <img src="assets/images/icon-edit-retro.svg" alt="">
                </button>
              </div>
              <div class="remove-comment">
                <button class="btn__card-remove" data-id="${card.id}" data-target="#${card.id}">
                  <img src="assets/images/icon-delete-retro.svg" alt="">
                </button>
              </div>
            </div>
          </div>
          <div class="form-item" style="display:none">    
          <input  type="text" 
                  name="retro-comment" 
                  placeholder="Write your comment">
          <button class="card__update" data-cardtype=${card.cardType} data-id="${card.id}" data-target="#${card.id}">Update</button>
          </div>
        </li>`;
      $(container).append(html);
  }

  $(document).on('click','.btn__card-edit', function(){
    console.log('update')
    let form = $(this).closest('li').find('.form-item');

    if(!form.hasClass('open')) {
      form.addClass('open').slideDown({
        start: function () {
          $(this).css({
            display: "flex"
          })
        }
      });
    } else {
      form.removeClass('open').slideUp();
    }
  });

  $(document).on('click','.card__update', function(e){
    e.preventDefault();
    let target = $(this).data('target');
    let cardType = $(this).data('cardtype');
    let comment = $(this).parent().find('input').val();
    let userEmail = localStorage.getItem('ls_email');

    console.log(cardType);
    console.log(comment);
    console.log(userEmail);

    $.ajax({
      url: 'assets/json/updateCard.json',
      //url: 'http://localhost:8080/retros/'+idRetro+'/cards/'+$(this).data('id');
      method: 'POST',
      //method: 'PUT',
      //data : {_method: put"}
      data : {
        _method   : "put",
        cardType  : cardType,
        comment   : comment,
        userEmail : userEmail
      }
    })
    .done(function( response, textStatus, xhr ) {
      console.log(xhr.status);
      if(xhr.status === 200 ) {
        $(target).find('.text-comment').text(response.comment)
      }
    });
  });

  $(document).on('click','.btn__card-remove', function(e){
    e.preventDefault()
    let target = $(this).data('target');

    $.ajax({
      url: 'assets/json/deleteComment.json',
      //url:  'http://localhost:8080/retros/'+idRetro+'/cards/'+$(this).data('id');
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

  $('.card__add').click(function(e){
    e.preventDefault();
    let cardType = $(this).data('cardtype');
    let comment = $(this).parent().find('input').val();
    let userEmail = localStorage.getItem('ls_email');
    let json;
    switch (cardType) {
      case 'HAPPY':
        json = 'addCardHappy.json';
        break;
      case 'MEH':
        json = 'addCardMeh.json';
        break;
      case 'SAD':
        json = 'addCardSad.json';
        break;
    }
    $.ajax({
      url: 'assets/json/'+json,
      //url : 'http://localhost:8080/retros/'+idRetro+'/cards',
      method: 'POST',
      data: { 
        comment    : comment,
        cardType   : cardType,
        userEmail  : userEmail
      }
    })
    .done(function( response, textStatus, xhr ) {
      if(xhr.status === 200){
        populateCards(response, response.cardType);
      }
    });
  })


  loadCards();
});