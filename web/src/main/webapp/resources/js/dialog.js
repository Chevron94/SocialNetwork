var wsocket;
var serviceLocation = "wss://" + document.location.host + "/dialog";
var $nickName;
var $message;
var $chatWindow;
var $dialog;

function chatClick(idDialog)
{
    var url = window.location.protocol+'//'+window.location.hostname+':'+window.location.port+'/dialog/getMessages';
    $('#'+idDialog).html($('#'+idDialog).val());

    $.getJSON(url,
        {
        idDialog:idDialog,
        ajax:true
        },
        function(data){
        var len = data.length;
        var messageLine = '';
        for (var i = 0; i< len; i++) {
            var text = data[i].messageText.replace(/\n\r/g,"<br>");
            messageLine+='<tr><td class="sender col-xs-2">' + data[i].sender
                + '</td><td class="sender col-xs-8">' + text
                + '</td><td class="sender col-xs-2">' + data[i].received;
                + '</td></tr>';
        }
        $('#dialogName').html($('#'+idDialog).html());
        $('#response').html(messageLine);
        $('#dialogId').val(idDialog);
        var elem = document.getElementById('tableDiv');
        elem.scrollTop = elem.scrollHeight;
    })
}

function onMessageReceived(evt) {
    //var msg = eval('(' + evt.data + ')');
    var msg = JSON.parse(evt.data); // native API
    if($dialog.val() == msg.receiver){
        var text = msg.messageText.replace(/\n\r/g, "<br>");
        var $messageLine = $('<tr><td class="sender col-xs-2">' + msg.sender
            + '</td><td class="sender col-xs-8">' + text
            + '</td><td class="sender col-xs-2">' + msg.received
            + '</td></tr>');
        $chatWindow.append($messageLine);
        var elem = document.getElementById('tableDiv');
        elem.scrollTop = elem.scrollHeight;
    }else {
        $('#'+msg.receiver).html($('#'+msg.receiver).val()+" <b>NEW!!!</b>");
    }
}
function sendMessage(messageText,sender,dialog){
    var msg;
    if(sender == null && messageText==null && dialog==null){
        if($message.val().trim()!="") {
            msg = '{"messageText":"' + $message.val().trim() + '", "sender":"'
                + $nickName.val() + '", "received":"","dialog":"'+$dialog.val()+'"}';
            wsocket.send(msg);
        }
        $message.val('').focus();
    }else {
        msg = '{"messageText":"' + messageText.trim() + '", "sender":"'
            + sender + '", "received":"","dialog":"' + dialog + '"}';
        wsocket.send(msg);
    }
}
function connectToChatServer() {
    wsocket = new WebSocket(serviceLocation);
    wsocket.onopen = function(){
        var msg = '{"id":"'+ $("#idUser").val() + '", "sender":"' //ADD ID TO MESSAGE AND CHECK IT IN ENDPOINT
            + $nickName.val() + '", "received":""}';
        wsocket.send(msg);
    };
    wsocket.onmessage = onMessageReceived;

}

function leaveRoom() {
    wsocket.close();
    $chatWindow.empty();
    $nickName.focus();
}

function buttonAction()
{
    sendMessage(null,null,null);
}

function pageLoad()
{
    $nickName = $('#nickname');
    $message = $('#message');
    $chatWindow = $('#response');
    $dialog = $('#dialogId');
    $('.chat-wrapper h4').text('Chat # '+$nickName.val());
    $message.focus();
    connectToChatServer();
}
/*
$(document).ready(function() {
    $nickName = $('#nickname');
    $message = $('#message');
    $chatWindow = $('#response');
    $dialog = $('#current');
    $('.chat-wrapper h4').text('Chat # '+$nickName.val());
    $message.focus();
    connectToChatServer();
});*/