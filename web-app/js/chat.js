var detect = $('#detect');
var header = $('#header');
var content = $('#content');
var input = $('#input');
var statusfield = $('#status');
var myName = false;
var author = null;
var logged = false;
var socket = $.atmosphere;
var subSocket;
var transport = 'ajax';


chat = {
    socket:null,
    subSocket:null,
    request:null,
    logged:false,


    init:function () {
        chat.socket = $.atmosphere
        chat.request = {
            url:'http://localhost:8199/GrailsChat/atmosphere/chatty',
            contentType:'application/json',
            logLevel:'debug',
            transport:'ajax',
            fallbackTransport:'long-polling'
        };

        console.log(chat.request);

        chat.request.onOpen = function (response) {
            console.log(statusfield);
            console.log(input);
            statusfield.html('Atmosphere connected using ' + response.transport);
        };

        chat.request.onMessage = function (response) {
            console.log('inside onMessage! Response is ');
            console.log(response);
            var message = response.responseBody;

            try {
                JSON.parse(message);
            } catch (e) {
                console.log("Not valid json: ");
                console.log(response);
                return;
            }

            if (!chat.logged) {
                chat.logged = true;
                console.log('w000000t');
            } else {
                console.log(json)
            }
        };

        chat.request.onError = function (response) {
           status.html('ERROR!');
            console.log(response);
        };

        chat.request.onReconnect = function(request, response){
            chat.socket.info("Reconnecting");
            console.log("Reconnecting...");
        };

        chat.subSocket = chat.socket.subscribe(chat.request);


    },

    sendMessage:function () {
        console.log($('#name').val() + ' said ' + $('#message').val())
        chat.subSocket.push(JSON.stringify({author:chat.nameField.val(), message:chat.messageField.val()}))
       input.val('')
    }



}

$(document).ready(
    chat.init()
);