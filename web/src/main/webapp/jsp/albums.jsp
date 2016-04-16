<%@ page import="network.entity.Album" %>
<%@ page import="java.util.List" %>
<%@ page import="network.entity.Photo" %>
<%@ page import="network.entity.User" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: Роман
  Date: 31.03.2016
  Time: 12:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Albums</title>
    <%@include file="templates/scripts.jsp" %>
    <%
        User requestUser = (User) request.getAttribute("requestUser");
    %>
    <script>
        var albumStart = 0;
    </script>
</head>
<body onload="loadAlbums(<%=requestUser.getId()%>,albumStart,6)">
<br>
<%@include file="templates/header.jsp" %>
<a href="/user<%=requestUser.getId()%>"><h2 style="margin: 1%"><%=requestUser.getLogin()%>
</h2></a>
<div class="row" style="margin: 1%">
    <%if (idUser == requestUser.getId()) {%>
        <button class="btn btn-success" value="Create Album" onclick="newAlbum();return false;">Create Album</button>
    <%}%>
</div>
    <div id="albums" class="row" style="max-height: 385px; margin: 1%; overflow-y: scroll">
        <div class="col-xs-12" id="albumsField">

        </div>

    </div>
<div class="row" style="margin: 1%;">
    <button class="btn btn-info" id="loadMore" value="LoadMore" style="display: none" onclick="loadAlbums(<%=requestUser.getId()%>,albumStart,6);">LoadMore</button>
</div>
<%@include file="templates/photoView.jsp" %>
<div id="albumWindow"><!-- Сaмo oкнo -->
    <span id="closeWindow">X</span> <!-- Кнoпкa зaкрыть -->
    <br>
    <form method="post" id="messageForm">
        <div class="control-group">
            <div class="row">
                <div class="col-xs-3" align="left">
                    <label for="albumName" class="control-label">Album name</label>
                </div>
                <div class="col-xs-9" align="right">
                    <input type="text" id="albumName" name="albumName" class="form-control" value="">
                </div>
            </div>
        </div>
        <br>
        <div class="row">
            <div class="col-xs-12" align="center">
                <input align="center" type="button" class="btn btn-primary" value="Create"
                       onclick="createAlbum(<%=idUser%>)">
            </div>
        </div>
    </form>
</div>
<div id="overlay"></div>
</body>
</html>
