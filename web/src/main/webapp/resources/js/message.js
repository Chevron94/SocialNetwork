var senderId;
var receiverId;
var messageUrl = window.location.protocol+'//'+window.location.hostname+':'+window.location.port+"/message";
function newMessage(sender, receiver, receiverName){
    senderId = sender;
    receiverId = receiver;
    $('#receiverName').val(receiverName);
}

function send(){
    var msg = $('#messageText').val();
    if (msg.trim() != "") {
        $.post(messageUrl,
            {
                idSender: senderId,
                idReceiver: receiverId,
                text: msg,
                ajax: 'true'
            }, function (data) {
                if (data==="true") {
                    $('#messageText').val('');
                    $('#messageWindow').modal('hide');
                } else {
                    alert("error");
                }

            }
        );
    }else{
        alert("you can't send empty message");
    }
}