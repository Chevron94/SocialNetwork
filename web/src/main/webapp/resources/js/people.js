/**
 * Created by roman on 10/12/15.
 */
var acceptUrl = window.location.protocol+'//'+window.location.hostname+':'+window.location.port+'/people/confirmRequest';
var sendUrl = window.location.protocol+'//'+window.location.hostname+':'+window.location.port+'/people/sendRequest';
var deleteUrl = window.location.protocol+'//'+window.location.hostname+':'+window.location.port+'/people/deleteRequest';

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
    $('#friendsNotFound').html('');
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
    $('#sentRequests').append(html);
    $('#sentRequestsNotFound').html('');

}

function deleteFriend(receiver){
    $('#'+receiver).html('');
}

function acceptRequest(sender,receiver)
{
    var x = sender + receiver;
    $.post(acceptUrl,
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
    $.post(sendUrl,
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

function deleteRequest(sender,receiver){
    var x=sender+receiver;
    $.post(deleteUrl,
        {
            idSender: sender,
            idReceiver: receiver,
            ajax : 'true'},function(data){
                deleteFriend(receiver);
            }

    );
}
