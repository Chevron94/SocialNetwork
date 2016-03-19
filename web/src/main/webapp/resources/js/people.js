/**
 * Created by roman on 10/12/15.
 */
var acceptUrl = window.location.protocol+'//'+window.location.hostname+':'+window.location.port+'/people/confirmRequest';
var sendUrl = window.location.protocol+'//'+window.location.hostname+':'+window.location.port+'/people/sendRequest';
var deleteUrl = window.location.protocol+'//'+window.location.hostname+':'+window.location.port+'/people/deleteRequest';
var loadMoreUrl = window.location.protocol+'//'+window.location.hostname+':'+window.location.port+'/people/more';

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
            if (data==="true"){
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

Date.prototype.customFormat = function(formatString){
    var YYYY,YY,MMMM,MMM,MM,M,DDDD,DDD,DD,D,hhhh,hhh,hh,h,mm,m,ss,s,ampm,AMPM,dMod,th;
    YY = ((YYYY=this.getFullYear())+"").slice(-2);
    MM = (M=this.getMonth()+1)<10?('0'+M):M;
    MMM = (MMMM=["January","February","March","April","May","June","July","August","September","October","November","December"][M-1]).substring(0,3);
    DD = (D=this.getDate())<10?('0'+D):D;
    DDD = (DDDD=["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"][this.getDay()]).substring(0,3);
    th=(D>=10&&D<=20)?'th':((dMod=D%10)==1)?'st':(dMod==2)?'nd':(dMod==3)?'rd':'th';
    formatString = formatString.replace("#YYYY#",YYYY).replace("#YY#",YY).replace("#MMMM#",MMMM).replace("#MMM#",MMM).replace("#MM#",MM).replace("#M#",M).replace("#DDDD#",DDDD).replace("#DDD#",DDD).replace("#DD#",DD).replace("#D#",D).replace("#th#",th);
    h=(hhh=this.getHours());
    if (h==0) h=24;
    if (h>12) h-=12;
    hh = h<10?('0'+h):h;
    hhhh = h<10?('0'+hhh):hhh;
    AMPM=(ampm=hhh<12?'am':'pm').toUpperCase();
    mm=(m=this.getMinutes())<10?('0'+m):m;
    ss=(s=this.getSeconds())<10?('0'+s):s;
    return formatString.replace("#hhhh#",hhhh).replace("#hhh#",hhh).replace("#hh#",hh).replace("#h#",h).replace("#mm#",mm).replace("#m#",m).replace("#ss#",ss).replace("#s#",s).replace("#ampm#",ampm).replace("#AMPM#",AMPM);
};


function loadMore(start,sender,list){
    var male =( $('#gender').val().indexOf('m')>0);
    var female = ( $('#gender').val().indexOf('f')>0);
    $.getJSON(loadMoreUrl,
        {
            idUser: sender,
            start: start,
            list: list,
            idContinent: $('#continent').val(),
            idCountry: $('#country').val(),
            idCity: $('#city').val(),
            male: male,
            female: female,
            ageFrom: $('#ageFrom').val(),
            ageTo: $('#ageTo').val(),
            idLanguage: $('#language').val(),
            ajax : 'true'},function(data){
            var html='';
            var len = data.length;
            for (var i = 0; i< len; i++) {
                var date = new Date(data[i].birthday);
                html+='<div class="row scale-text" style="margin: auto">'+
                        '<div class="col-xs-11">'+
                            '<div class="row" style="margin: auto">'+
                                '<div class="col-xs-12">'+
                                    '<div id="'+data[i].id+'" class="row">'+
                                        '<div class="col-xs-3">'+
                                            '<img src="'+data[i].photoURL+'" class="img img-responsive" alt="Responsive image">'+
                                        '</div>'+
                                        '<div class="col-xs-9">'+
                                            '<div class="row table-bordered table-fixed">'+
                                                '<div class="col-xs-3">'+
                                                    '<label class="control-label">Name</label>'+
                                                '</div>'+
                                                '<div class="col-xs-9">'+
                                                    '<label class="control-label">'+data[i].name+'</label>'+
                                                '</div>'+
                                            '</div>'+
                                            '<div class="row table-bordered table-fixed">'+
                                                '<div class="col-xs-3">'+
                                                    '<label class="control-label">Birthday</label>'+
                                                '</div>'+
                                                '<div class="col-xs-9">'+
                                                    '<label class="control-label">'+date.customFormat("#YYYY#-#MM#-#DD#") +'</label>'+
                                                '</div>'+
                                            '</div>'+
                                            '<div class="row table-bordered table-fixed">'+
                                                '<div class="col-xs-3">'+
                                                    '<label class="control-label">From</label>'+
                                                '</div>'+
                                                '<div class="col-xs-9">'+
                                                    '<label class="control-label">'+
                                                        '<img style="height: 20px" src="'+data[i].country.flagURL+'">'+data[i].city.name + ' (' + data[i].country.name +')'+
                                                    '</label>'+
                                                '</div>'+
                                            '</div>'+
                                            '<div class="row table-bordered table-fixed">'+
                                                '<div class="col-xs-3">'+
                                                    '<label class="control-label">Gender</label>'+
                                                '</div>'+
                                                '<div class="col-xs-9">'+
                                                    '<label class="control-label">'+data[i].gender.name+'</label>'+
                                                '</div>'+
                                            '</div>'+
                                            '<div class="row table-bordered table-fixed">'+
                                                '<div class="col-xs-3">'+
                                                    '<label class="control-label">Languages</label>'+
                                                '</div>'+
                                                '<div class="col-xs-9">'+
                                                    '<label class="control-label">'+data[i].country.name+'</label>'+
                                                '</div>'+
                                            '</div>'+
                                            '<div class="row table-bordered table-fixed">'+
                                                '<div class="col-xs-3">'+
                                                    '<label class="control-label">Profile</label>'+
                                                '</div>'+
                                            '<div class="col-xs-9">'+
                                                '<a href="/user'+data[i].id+'">'+
                                                    window.location.protocol+'//'+window.location.hostname+'/user'+data[i].id+
                                                '</a>'+
                                            '</div>'+
                                        '</div>'+
                                        '<div class="row table-bordered table-fixed btn-xs" align="center">'+
                                            '<button id="add_'+data[i].id+'" disabled type="button" class="Add btn btn-success btn-xs" onclick="acceptRequest('+data[i].id+','+idUser+'); return false">Add'+
                                            '</button>\n'+
                                            '<button id="send_'+data[i].id+'" type="button" class="btn btn-info btn-xs" onclick="newMessage('+idUser+', '+data[i].id+','+data[i].login+');return false">Send Message'+
                                            '</button>\n'+
                                            '<button id="delete_'+data[i].id+'" type="button" class="Add btn btn-danger btn-xs" onclick="deleteRequest('+idUser+', '+data[i].id+'); return false">Delete'+
                                            '</button>'+
                                        '</div>'+
                                    '</div>'+
                                '</div>'+
                            '</div>'+
                        '</div>'+
                    '</div>'+
                '</div>'+'<br>';
            }
            if (list == 'friends') {
                $('#friends').append(html);
            }else if(list == 'sent'){
                $('#sentRequests').append(html);
            }else if(list == 'received'){
                $('#friendRequests').append(html);
            }else $('#otherUsers').append(html);
        }

    );
}

