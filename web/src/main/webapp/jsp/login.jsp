<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Login page</title>
    <%@include file="templates/scripts.jsp"%>
    <link href="/resources/css/loginPage.css" rel="stylesheet">
</head>
<body>
<link rel="stylesheet" href="/resources/css/menu.css">
<br>
<div class="row menu-style" style="margin-right: auto; margin-left: auto">
    <div class="col-xs-10" align="center">

    </div>
    <div class="col-xs-2" align="center">
        <a class="btn btn-info" href="/registration" role="button">Registration</a>
    </div>
</div>
<br>
<!--
<div id="login-box">


    <form name='loginForm' action='j_spring_security_check'  method='POST'>

        <table>
            <tr>
                <td>User:</td>
                <td><input type='text' name='j_username' value=''></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type='password' name='j_password' /></td>
            </tr>
            <tr>
                <td colspan='2'>
                    <input name="submit" class="btn btn-primary" type="submit" value="submit" />
                </td>
            </tr>
        </table>

    </form>
</div>

</body>
-->
<form class="form-horizontal login-box" action='j_spring_security_check'  method='POST'>
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty msg}">
        <div class="msg">${msg}</div>
    </c:if>
    <div class="form-group">
        <label for="j_username" class="col-xs-2 control-label">Login</label>
        <div class="col-xs-10">
            <input type="text" class="form-control" name='j_username' id="j_username" placeholder="login">
        </div>
    </div>
    <div class="form-group">
        <label for="j_password" class="col-xs-2 control-label">Password</label>
        <div class="col-xs-10">
            <input type="password" class="form-control" name='j_password' id="j_password" placeholder="Password">
        </div>
    </div>
    <div class="form-group">
        <div class="col-xs-offset-2 col-xs-10">
            <div class="checkbox">
                <label>
                    <input type="checkbox" id="j_remember" name="_spring_security_remember_me"> Remember me
                </label>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-xs-offset-2 col-xs-10">
            <button type="submit" class="btn btn-default">Sign in</button>
        </div>
    </div>
</form>
</body>
</html>