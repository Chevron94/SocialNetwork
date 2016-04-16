<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Login page</title>
    <%@include file="templates/scripts.jsp"%>
</head>
<body>
<link rel="stylesheet" href="/resources/css/menu.css">
<br>
<%@include file="templates/header.jsp"%>
<br>
<form class="form-horizontal login-box" action="/j_spring_security_check" method='POST'>
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty msg}">
        <div class="msg">${msg}</div>
    </c:if>
    <div class="form-group form-group-sm">
        <label for="j_username" class="col-xs-2 control-label">Login</label>
        <div class="col-xs-10">
            <input type="text" class="form-control" name='j_username' id="j_username" placeholder="Login">
        </div>
    </div>
    <div class="form-group form-group-sm">
        <label for="j_password" class="col-xs-2 control-label">Password</label>
        <div class="col-xs-10">
            <input type="password" class="form-control" name='j_password' id="j_password" placeholder="Password">
        </div>
    </div>
    <div class="form-group">
        <div class="col-xs-offset-2 col-xs-10">
            <button type="submit" class="btn btn-primary">Sign in</button>
        </div>
    </div>
</form>
</body>
</html>