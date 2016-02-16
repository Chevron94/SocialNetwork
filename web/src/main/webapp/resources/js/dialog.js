var wsocket;
var serviceLocation = "ws://" + document.location.host + "/dialog";
var $nickName;
var $message;
var $chatWindow;
var room = '';

function chatClick(idDialog)
{
    $("dialogId").val(idDialog);
    var url = window.location.protocol+'//'+window.location.hostname+':'+window.location.port+'/dialog/getMessages';
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
                + '</td><td class="sender col-xs-2">' + data[i].received
                + '</td></tr>';
        }
        $('#dialogName').html($('#'+idDialog).html());
        $('#response').html(messageLine);
        var elem = document.getElementById('tableDiv');
        elem.scrollTop = elem.scrollHeight;
    })
}

function onMessageReceived(evt) {
    //var msg = eval('(' + evt.data + ')');
    var msg = JSON.parse(evt.data); // native API
  //  if($("#dialogID").val() == msg.idDialog) {
        var text = msg.messageText.replace(/\n\r/g, "<br>");
        var $messageLine = $('<tr><td class="sender col-xs-2">' + msg.sender
            + '</td><td class="sender col-xs-8">' + text
            + '</td><td class="sender col-xs-2">' + msg.received
            + '</td></tr>');
        $chatWindow.append($messageLine);
        var elem = document.getElementById('tableDiv');
        elem.scrollTop = elem.scrollHeight;
  /*  }else
    {
        $("#"+msg.idDialog).val($("#"+msg.idDialog).val()+' NEW!!!')
    }*/
}
function sendMessage() {
    if($message.val().trim()!="") {
        var msg = '{"messageText":"' + $message.val().trim() + '", "sender":"'
            + $nickName.val() + '", "received":""}';
        wsocket.send(msg);
    }
    $message.val('').focus();
}

function connectToChatserver() {
    wsocket = new WebSocket(serviceLocation);
    wsocket.onmessage = onMessageReceived;
   // var msg = '{"id":"'+ $("#idUser").val() + '", "sender":"' ADD ID TO MESSAGE AND CHECK IT IN ENDPOINT
   //     + $nickName.val() + '", "received":""}';
  //  wsocket.send(msg);
}

function leaveRoom() {
    wsocket.close();
    $chatWindow.empty();
    $nickName.focus();
}

function buttonAction()
{
    sendMessage();
}

function pageLoad()
{
    connectToChatserver();
    $nickName = $('#nickname');
    $message = $('#message');
    $chatWindow = $('#response');
    $('.chat-wrapper h4').text('Chat # '+$nickName.val());
    $message.focus();
}

$(document).ready(function() {
    $nickName = $('#nickname');
    $message = $('#message');
    $chatWindow = $('#response');
    connectToChatserver();
    $('.chat-wrapper h4').text('Chat # '+$nickName.val());
    $message.focus();
});