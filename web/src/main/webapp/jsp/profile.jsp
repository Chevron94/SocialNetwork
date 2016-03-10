<%@ page import="network.entity.User" %>
<%@ page import="java.util.List" %>
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
  <% List<User> friends = (List<User>)request.getAttribute("friends"); %>

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
        <div class="col-xs-2">
          <div class="row table-bordered table-fixed">
            <img src="<%=user.getPhotoURL()%>" class="img img-responsive" alt="Responsive image">
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
        </div>
        <div class="col-xs-6" >

          <div class="row table-bordered table-fixed" style="margin-left: 10%">
            <div class="col-xs-3">
              <b>Name</b>
            </div>
            <div class="col-xs-9">
              <%=user.getName()%>
            </div>
          </div>

          <div class="row table-bordered table-fixed" style="margin-left: 10%">
            <div class="col-xs-3">
              <b>Birthday</b>
            </div>
            <div class="col-xs-9">
              <%=user.getBirthday().toString().split(" ")[0]%>
            </div>
          </div>

          <div class="row table-bordered table-fixed" style="margin-left: 10%">
            <div class="col-xs-3">
              <b>From</b>
            </div>
            <div class="col-xs-9">
              <img style="height: 20px" src="<%=user.getCountry().getFlagURL()%>"> <%=user.getCity().getName()+" ("+ user.getCountry().getName()+")"%>
            </div>
          </div>

          <div class="row table-bordered table-fixed" style="margin-left: 10%">
            <div class="col-xs-3">
              <b>Gender</b>
            </div>
            <div class="col-xs-9">
              <%=user.getGender().getName()%>
            </div>
          </div>
          <div class="row table-bordered table-fixed" style="margin-left: 10%">
            <div class="col-xs-3">
              <b>Languages</b>
            </div>
            <div class="col-xs-9">
              <%=user.getCountry().getName()%>
            </div>
          </div>

          <div class="row table-bordered table-fixed" style="margin-left: 10%">
            <div class="col-xs-3">
              <b>About me</b>
            </div>
            <div class="col-xs-9">
                <%=user.getDescription()%>
            </div>
          </div>

        </div>
      </div>
    </div>
  </div>
</body>
</html>
