<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 10/5/15
  Time: 6:50 PM
  To change this template use File | Settings | File Templates.
--%>
<link rel="stylesheet" href="/resources/css/menu.css">
<br>
<div class="row menu-style">
  <%
    Long idUser = (Long)request.getSession().getAttribute("idUser");
    if (idUser != null)
    {
  %>
  <div class="col-xs-2" align="center">
    <a class="btn btn-info" href="/user<%=idUser.toString()%>" role="button">My profile</a>
  </div>
  <div class="col-xs-2" align="center">
    <a class="btn btn-info" href="/user<%=idUser.toString()%>/friends" role="button">Friends</a>
  </div>
  <div class="col-xs-2" align="center">
    <a class="btn btn-info" href="/dialogs" role="button">Dialogs</a>
  </div>
  <div class="col-xs-2" align="center">
    <a class="btn btn-info" href="/user<%=idUser.toString()%>/albums" role="button">Albums</a>
  </div>
  <div class="col-xs-2"align="center">
    <a class="btn btn-info" href="/user<%=idUser.toString()%>/settings" role="button">Settings</a>
  </div>
  <div class="col-xs-2" align="center">
    <a class="btn btn-info" href="/j_spring_security_logout" role="button">Log out</a>
  </div>
  <%
    }
    else
    {
  %>
  <div class="col-xs-10">

  </div>
  <div class="col-xs-2" align="center">
    <a class="btn btn-info" href="/login" role="button">Sing in</a>
  </div>
  <%
    }
  %>
</div>
<br>
