<%@ page import="network.entity.User" %>
<%@ page import="java.util.List" %>
<%@ page import="network.entity.FriendRequest" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 10/4/15
  Time: 5:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>My Profile</title>
  <%@include file="templates/scripts.jsp"%>
  <link href="/resources/css/text.css" rel="stylesheet">
  <% User user = (User)request.getAttribute("user"); %>
  <% SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");%>
  <% List<User> friends = (List<User>)request.getAttribute("friends"); %>
  <% FriendRequest friendRequest = (FriendRequest)request.getAttribute("friendRequest");%>
  <style>
    .word {
      word-break: break-all;
    }
  </style>
</head>
<body>
<br>
<%@include file="templates/header.jsp"%>
  <div class="row scale-text" style="margin: auto">
    <div class="col-xs-12">
      <div class="row" style="margin-left: 20%">
        <div class="col-xs-3">
          <div class="row table-bordered table-fixed">
          <%if (user.getAlbums().get(0).getPhotos() != null && user.getAlbums().get(0).getPhotos().size()>0){
          %>
            <input type="image" src="<%=user.getPhotoURL()%>" class="img img-responsive" alt="Responsive image" style="width: 100%"  data-toggle="modal" data-target="#photoViewer" onclick="showPhoto(<%=user.getAlbums().get(0).getPhotos().get(0).getId()%>,'<%=user.getPhotoURL()%>',<%=user.getId()%>,<%=user.getAlbums().get(0).getId()%>,'<%=simpleDateFormat.format(user.getAlbums().get(0).getPhotos().get(0).getUploaded())%>',0)">
            <%
          }else{
            %>
            <img src="<%=user.getPhotoURL()%>" class="img img-responsive" style="width: 100%" alt="Responsive image">
            <%
          }%>
          </div>
          <div id="buttons">
            <%if(idUser == user.getId()){
            %>
            <div class="row" id="newPhotoButton">
              <input type="button" class="btn btn-success btn-block" value="Upload new photo" onclick="newAlbum()">
            </div>
            <%
              }%>
          <%if (user.getId()!=idUser){
          %>
          <%@include file="templates/message.jsp"%>
            <div class="row" id="friendButton">
            <%if (friendRequest == null || (friendRequest.getSender().getId()==user.getId()&&friendRequest.isConfirmed()==false)){
            %>
              <input type="button" class="btn btn-success btn-block" value="Add to friends" onclick="sendRequest(<%=idUser%>,<%=user.getId()%>)">
            <%
            }%>
            </div>
            <div class="row" id="messageButton">
              <input type="button" class="btn btn-info btn-block" value="Send message" onclick="newMessage(<%=idUser%>,<%=user.getId()%>,'<%=user.getLogin()%>')">
            </div>
            <div class="row" id="deleteButton">
              <%if (friendRequest != null && (friendRequest.isConfirmed() || friendRequest.getSender().getId() == idUser)){
              %>
              <input type="button" class="btn btn-danger btn-block" value="Delete" onclick="deleteRequest(<%=idUser%>,<%=user.getId()%>)">
              <%
                }%>
            </div>
          <%
          }%>
          </div>
          <div class="row" style="background: lightgrey; margin-top: 1%">
            <a href="<%=pageContext.getRequest().getScheme()+"://"
                            + pageContext.getRequest().getServerName()+"/user"+user.getId()+"/friends"%>">
                            <b>Friends <%="("+friends.size()+")"%></b>
            </a>
          </div>
          <%
            if(friends.size()>0)
            {
              for(int i = 0; i<2; i++)
              {
                if(i*3 < friends.size())
                {
          %>
          <div class="row" style="margin-top: 1%">


            <%
              for(int j=0; j<3; j++)
              {
                if(friends.size()>3*i+j)
                {
            %>
            <div class="col-xs-3" style="margin: 1%; width: 30%">
              <div class="row fixed-width" style="overflow: hidden">
                <a href="/user<%=friends.get(3*i+j).getId()%>"><img src="<%=friends.get(3*i+j).getPhotoURL()%>" class="img img-responsive" alt="Responsive image"></a>
              </div>
              <div class="row" align="center">
                <a href="/user<%=friends.get(3*i+j).getId()%>"><b><p class="word"> <%=friends.get(3*i+j).getName()%></p></b></a>
              </div>
            </div>

            <%
                  }
                }

            %>

          </div>
          <%
                }
              }
            }
          %>
          <div class="row" style="background: lightgrey; margin-top: 1%">
            <a href="<%=pageContext.getRequest().getScheme()+"://"
                            + pageContext.getRequest().getServerName()+"/user"+user.getId()+"/albums"%>">
              <b>Albums <%="("+"0"+")"%></b>
            </a>
          </div>
        </div>
        <div class="col-xs-6" >
          <div class="row table-bordered table-fixed" style="margin-left: 10%">
            <div class="col-xs-4">
              Name
            </div>
            <div class="col-xs-8">
              <b><%=user.getName()%></b>
            </div>
          </div>

          <div class="row table-bordered table-fixed" style="margin-left: 10%">
            <div class="col-xs-4">
              Birthday
            </div>
            <div class="col-xs-8">
              <b><%=user.getBirthday().toString().split(" ")[0]%></b>
            </div>
          </div>

          <div class="row table-bordered table-fixed" style="margin-left: 10%">
            <div class="col-xs-4">
              From
            </div>
            <div class="col-xs-8">
              <img style="height: 20px" src="<%=user.getCountry().getFlagURL()%>"> <b><%=user.getCity().getName()+" ("+ user.getCountry().getName()+")"%></b>
            </div>
          </div>

          <div class="row table-bordered table-fixed" style="margin-left: 10%">
            <div class="col-xs-4">
              Gender
            </div>
            <div class="col-xs-8">
              <b><%=user.getGender().getName()%></b>
            </div>
          </div>
          <div class="row table-bordered table-fixed" style="margin-left: 10%">
            <div class="col-xs-4">
              Languages
            </div>
            <div class="col-xs-8">
              <b><%=user.getCountry().getName()%></b>
            </div>
          </div>

          <div class="row table-bordered table-fixed" style="margin-left: 10%">
            <div class="col-xs-4">
              About me
            </div>
            <div class="col-xs-8">
                <b><%=user.getDescription()%></b>
            </div>
          </div>

        </div>
      </div>
    </div>
  </div>

<%@include file="templates/photoView.jsp"%>
<div id="albumWindow"><!-- Сaмo oкнo -->
  <span id="closeWindow">X</span> <!-- Кнoпкa зaкрыть -->
  <br>
  <form method="post" action="/profile" id="albumForm" enctype="multipart/form-data">

    <div class="control-group">
      <div class="row">
        <div class="col-xs-3" align="left">
          <label for="photoInput" class="control-label">Photo</label>
        </div>
        <div class="col-xs-9" align="right">
          <input type="file" id="photoInput" name="photoInput" value="">
        </div>
      </div>
    </div>
    <br>
    <div class="row">
      <div class="col-xs-12" align="center">
        <input align="center" type="submit" class="btn btn-primary" value="Upload">
      </div>
    </div>
  </form>
</div>
<div id="overlay"></div>
</body>
</html>
