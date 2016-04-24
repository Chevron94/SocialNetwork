/**
 * Created by Роман on 31.03.2016.
 */
var createAlbumUrl = window.location.protocol+'//'+window.location.hostname+':'+window.location.port+'/albums';
var loadMoreAlbumsUrl = window.location.protocol+'//'+window.location.hostname+':'+window.location.port+'/albums/more';
function loadAlbums(idUser,start,count){
    $.getJSON(loadMoreAlbumsUrl,
        {
            idUser:idUser,
            start:start,
            count:count,
            ajax:true
        },
        function(data){
            var length = data.length;
            if(length == 0 && albumStart == 0){
                var html='<div class="row" style="margin: 1%">';
                html+="<h4>No albums</h4></div>";
                $('#albumsField').append(html);
                $('#loadMore').hide();
            }else{
                if(length<count){
                    $('#loadMore').hide();
                }else{
                    $('#loadMore').show();
                }
                albumStart+=length;
                var html = '';
                for(var i = 0; i<length;i++){
                    html+=
                        '<div id="'+data[i].id+'" class="row table-bordered" style="margin-bottom: 1%">'+
                            '<div class="col-xs-12">'+
                                '<div class="row">'+
                                    '<div class="col-xs-4">'+
                                        '<a href="/albums/'+data[i].id+'"><h4 id="'+data[i].id+'"><b>'+data[i].name+
                                        '</b></h4></a>'+
                                    '</div>'+
                                '</div>'+
                                '<div class="row" style="margin-bottom: 1%; margin-right: 1%;">';
                                if (data[i].photos.length == 0){
                                    html+='<div class="col-xs-2">'+
                                        '<b>Empty album<b>'+
                                        '</div>';
                                }
                                for(var j = 0; j<data[i].photos.length; j++){
                                    html+='<div class="col-xs-2" style="height: 135px; overflow: hidden; position: relative;">'+
                                    '<input type="image" src="'+data[i].photos[j].photoUrl+'" class="img img-responsive" alt="Responsive image" style="position: absolute" data-toggle="modal" data-target="#photoViewer" onclick="showPhoto('+data[i].photos[j].id+",'"+data[i].photos[j].photoUrl+"',"+data[i].user.id+","+data[i].id+",'"+(new Date(data[i].photos[j].uploaded)).customFormat("#YYYY#-#MM#-#DD#")+"',"+j+')">'+
                                    '</div>';
                                }
                                html+=
                                '</div>'+
                            '</div>'+
                        '</div>';
                }
                $('#albumsField').append(html);
            }
        })
}

function createAlbum(id){
    $.post(createAlbumUrl,
        {
            idCreator: id,
            name: $('#albumName').val(),
            ajax : 'true'},function(data){
            if(data == ''){
                alert("Error");
            }else
            if(data == 0){
                alert("Album already exists");
            }else{
                $('#empty').remove();
                    var html =
                        '<div id="'+data+'" class="row table-bordered" style="margin: auto; margin-right:1%;">'+
                        '<div class="col-xs-12">'+
                        '<div class="row">'+
                        '<div class="col-xs-4">'+
                        '<a href="/albums/'+data+'"><h4><b>'+$('#albumName').val()+'</b></h4></a>'+
                        '</div>'+
                        '</div>'+
                        '<div class="row" style="margin-bottom: 1%; margin-right: 1%;">'+
                        '<div class="col-xs-2">\n'+
                        '<b>Empty album<b>'+
                        '</div>\n'+
                        '</div>'+
                        '</div>' +
                        '</div>'+
                        '<br>';

                    $('#albums').prepend(html);
            }
            $('#closeWindow').trigger("click");
        }
    );
}

function deleteAlbum(id){
    $.ajax({
        url: createAlbumUrl+'/'+id,
        type: 'DELETE',
        success: function(result) {
            if (result != null){

            }
            albumStart--;
            window.location = createAlbumUrl;
        }
    });
}

function renameAlbum(id){
    var newName = $('#albumName').val();
    $.post(createAlbumUrl+'/'+id+'/rename',
        {
            newName: newName,
            ajax: true},
        function(result) {
            if(result){
                $("div#titleName h2").html(newName);
                document.title = newName;
            }else alert("You already have album with this name");
        }
    );

}