/**
 * Created by roman on 10/12/15.
 */
var acceptUrl = window.location.protocol+'//'+window.location.hostname+':'+window.location.port+'/people/confirmRequest';
var sendUrl = window.location.protocol+'//'+window.location.hostname+':'+window.location.port+'/people/sendRequest';

function updateFriends(sender)
{
    $('#add_'+sender).disabled = true;
    var html = '<div class="row" style="margin: auto">'+
        '<div class="col-xs-9">' +
        '<div class="row" style="margin: auto">' +
        '<div class="col-xs-12">' +
        ' <div id="'+sender+'" class="row table-fixed">';
    var x = document.getElementById(sender);
    html += x.innerHTML;
    html +='</div></div></div></div></div>';
    $('#'+sender).html('');
    $('#friends').append(html);
}

function updateRequests(receiver)
{
    $('#add_'+receiver).disabled = true;
    var html = '<div class="row" style="margin: auto">'+
        '<div class="col-xs-9">' +
        '<div class="row" style="margin: auto">' +
        '<div class="col-xs-12">' +
        ' <div id="'+receiver+'" class="row table-fixed">';
    var x = document.getElementById(receiver);
    html += x.innerHTML;
    html +='</div></div></div></div></div>';
    $('#'+receiver).html('');
    $('#sended_requests').append(html);

}

function acceptRequest(sender,receiver)
{
    var x = sender + receiver;
    $.getJSON(acceptUrl,
        {
            idSender: sender,
            idReceiver: receiver,
            ajax: 'true'
        }, updateFriends(sender)
    )

}
function sendRequest(sender,receiver)
{
    var x=sender+receiver;
    $.getJSON(sendUrl,
        {
            idSender: sender,
            idReceiver: receiver,
            ajax : 'true'},function(data){
            if (data==true){
                updateFriends(receiver)
            }else {
                updateRequests(receiver)
            }
        }

    );
}
