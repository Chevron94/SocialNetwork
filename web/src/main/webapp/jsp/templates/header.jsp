<%--
  Created by IntelliJ IDEA.
  User: Роман
  Date: 26.02.2016
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="/profile">Hello From</a>
            </div>
            <%
                Long idUser = (Long)request.getSession().getAttribute("idUser");
                if (idUser != null)
                {
            %>
            <script type="text/javascript">
                var idUser = <%=idUser%>;
            </script>
            <ul class="nav navbar-nav">
                <li><a href="/profile">My profile</a></li>
                <li><a href="/friends">Friends</a></li>
                <li><a href="/users">Users</a></li>
                <li><a href="/dialogs">Dialogs</a></li>
                <li><a href="/user<%=idUser.toString()%>/albums">Albums</a></li>
                <li><a href="/user<%=idUser.toString()%>/settings">Settings</a></li>
            </ul>
            <%}%>
            <ul class="nav navbar-nav navbar-right">
                <%if (idUser != null)
                {%>
                <li><a href="/j_spring_security_logout"><span class="glyphicon glyphicon-log-out"></span> Log out</a></li>
                <%}else{%>
                <li><a href="/registration"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
                <li><a href="/login"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                <%}%>
            </ul>
        </div>
    </nav>
