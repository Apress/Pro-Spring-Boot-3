let stompClient = null;

function connect() {
    let socket = new SockJS('/logs');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/user-logs', function (response) {
            console.log(response);
            showLogs(response.body);
        });
    });
}

function showLogs(message) {
    $("#logs").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
   connect();
});