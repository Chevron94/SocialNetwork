<%@ page import="network.entity.Country" %>
<%@ page import="java.util.List" %>
<%@ page import="network.entity.City" %>
<%@ page import="network.entity.Gender" %>
<%@ page import="network.dao.implementation.CityDaoImplementation" %>
<%@ page import="network.service.CountryService" %>
<%@ page import="network.service.CityService" %>
<%@ page import="network.dto.UserDto" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 9/28/15
  Time: 11:01 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
  <script type="text/javascript">
    function updateSelectOptions() {
      var url = window.location.protocol+'//'+window.location.hostname+':'+window.location.port+'/registration/citiesByCountry';
      var id = $('#country_select').select().val();
      $('#country').val($('#country_select').select().val());
      $.getJSON(url,
              {searchId: id,
                ajax : 'true'},

              function(data) {
                var html = '<option value="0" selected>Select city</option>';
                var len = data.length;
                for (var i = 0; i< len; i++) {
                  html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                }
                $('#city_select').html(html);
                $('#city').val('0');
              }
      );
    }
    function updateGender() {
        $('#gender').val($('#gender_select').select().val());
    }
    function updateCity() {
        $('#city').val($('#city_select').select().val());
    }
  </script>
  <title>Registration</title>
  <%@include file="templates/scripts.jsp"%>
  <script type="text/javascript" src="/resources/js/md5.js"></script>
</head>
<body>
<%
  List<Country> countryList = (List<Country>) request
          .getAttribute("countries");
  List<Gender> genders = (List<Gender>) request
          .getAttribute("genders");
  List<String> errors = (List<String>)request.getAttribute("errors");
  List<City> cities = (List<City>)request.getAttribute("cities");

  UserDto user = (UserDto)request.getAttribute("user");
%>
<script type="text/javascript">
  function hashPassword(form)
  {
    var i = form.elements.length;
    $('#password').val(md5($('#open_password').val()));
    $('#open_password').val('');
  }
</script>

<%@include file="templates/menu.jsp"%>


<% if(errors != null)
{
%>
<div class="alert alert-danger" id="div-error" role="alert">
  <%
    for(int i = 0; i<errors.size();i++)
    {
  %>
    <p><%=errors.get(i)%></p>
  <%
    }
  %>
</div>
<%
}
%>
<form method="POST" id="registration" name="registration" enctype="multipart/form-data" onsubmit="return hashPassword(this)">
  <div class="container" align="center">
    <div class="form-group">
      <div class="row" style="margin-left:20%">
        <div class="col-xs-2" align="left">
          <label class="control-label" for="login">Login</label>
        </div>
        <div class="col-xs-2" align="left">
          <input type="text" id="login" name="login" required
            <%
              if(user!=null)
              {
            %>
                  value="<%=user.getLogin()%>"
            <%
              }
            %>
          >
        </div>
      </div>
    </div>
    <div class="control-group">
      <div class="row" style="margin-left:20%">
        <div class="col-xs-2" align="left">
          <label class="control-label" for="open_password">Password</label>
        </div>
        <div class="col-xs-2" align="left">
          <input type="password" id="open_password" name="open_password" required>
          <input type="hidden" id="password" name="password">
        </div>
      </div>
    </div>
    <br>
    <div class="form-group">
      <div class="row" style="margin-left:20%">
        <div class="col-xs-2" align="left">
          <label class="control-label" for="email">E-mail</label>
        </div>
        <div class="col-xs-2" align="left">
          <input type="email" id="email" name="email" required
            <%
              if(user!=null)
              {
            %>
                 value="<%=user.getEmail()%>"
            <%
              }
            %>
          >
        </div>
      </div>
    </div>
    <div class="form-group">
      <div class="row" style="margin-left:20%">
        <div class="col-xs-2" align="left">
          <label class="control-label" for="name">Name</label>
        </div>
        <div class="col-xs-2" align="left">
          <input type="text" id="name" name="name"required
            <%
              if(user!=null)
              {
            %>
                 value="<%=user.getName()%>"
            <%
              }
            %>
          >
        </div>
      </div>
    </div>

    <div class="form-group">
      <div class="row" style="margin-left:20%">
        <div class="col-xs-2" align="left">
          <label class="control-label" for="filePhoto">Photo</label>
        </div>
        <div class="col-xs-2" align="left">
          <input type="file" id="filePhoto" name="filePhoto" placeholder="http://site.com/photo.jpg">
        </div>
      </div>
    </div>

    <div class="form-group">
      <div class="row" style="margin-left:20%">
        <div class="col-xs-2" align="left">
          <label class="control-label" for="gender_select">Gender</label>
        </div>
        <div class="col-xs-2" align="left">
          <select name="gender_select" id="gender_select" onchange="updateGender()">
            <option value="0" selected>Select gender</option>
            <% for (int i=0;i<genders.size(); i++)
            {
            %>
              <option value="<%=genders.get(i).getId()%>"
                      <%
                        if(user!=null && user.getGender().equals(genders.get(i).getId().toString()))
                        {
                      %>
                      selected
                      <%
                        }
                      %>
              ><%=genders.get(i).getName()%></option>
            <%
            }
            %>
          </select>
        </div>
      </div>
    </div>
    <script language="JavaScript">
      $(function() {
        $( "#birthday" ).datepicker({
          dateFormat: 'yy-mm-dd',
          changeMonth: true,
          changeYear: true,
          maxDate: new Date(),
          yearRange: '-99:+0'});
      });
    </script>
    <div class="form-group">
      <div class="row" style="margin-left:20%">
        <div class="col-xs-2" align="left">
          <label class="control-label" for="birthday">Birthday</label>
        </div>
        <div class="col-xs-2" align="left">
          <input type="text" id="birthday" name="birthday" required readonly
            <%
              if(user!=null)
              {
            %>
                 value="<%=user.getBirthday().toString().split(" ")[0]%>"
            <%
              }
            %>
          >
        </div>
      </div>
    </div>
    <div class="form-group">
      <div class="row" style="margin-left:20%">
        <div class="col-xs-2" align="left">
          <label class="control-label" for="country_select">Country</label>
        </div>
        <div class="col-xs-2" align="left">
          <select name="country_select" id="country_select" onchange="updateSelectOptions()">
            <option value="0" selected>Select country</option>
            <% for (int i=0;i<countryList.size(); i++)
            {
            %>
              <option value="<%=countryList.get(i).getId()%>"
                      <%
                        if(user!=null && user.getCountry().equals(countryList.get(i).getId().toString()))
                        {
                      %>
                      selected
                      <%
                        }
                      %>
                      >
                <%=countryList.get(i).getName()%>
              </option>
            <%
              }
            %>
          </select>
        </div>
      </div>
    </div>
    <div class="form-group">
      <div class="row" style="margin-left:20%">
        <div class="col-xs-2" align="left">
          <label class="control-label" for="city_select">City</label>
        </div>
        <div class="col-xs-2" align="left">
          <select name="city_select" id="city_select" onchange="updateCity()">
            <option value="0" selected>Select city</option>
            <%
              if (cities != null)
              {
                for(int i = 0; i < cities.size(); i++)
                {
            %>
                  <option value="<%=cities.get(i).getId()%>"
                          <%
                            if(user!=null && user.getCity().equals(cities.get(i).getId().toString()))
                            {
                          %>
                          selected
                          <%
                            }
                          %>
                  ><%=cities.get(i).getName()%></option>
            <%
                }
              }
            %>
          </select>
        </div>
      </div>
    </div>
    <div class="form-group">
      <div class="row" style="margin-left:20%">
        <div class="col-xs-2" align="left">
          <label class="control-label" for="description">About me</label>
        </div>
        <div class="col-xs-6" align="left">
          <textarea class="form-control" id="description"  name="description" rows="3" required
            <%
              if(user!=null)
              {
            %>
                 value="<%=user.getDescription()%>"
            <%
              }
            %>
                  ></textarea>
        </div>
      </div>
    </div>
    <div class="row" style="margin-left:20%">
      <div class="col-xs-4" align="center">
        <input class="btn btn-primary" type="submit" value="Submit">
      </div>
    </div>
  </div>
  <input type="hidden" name="city" id="city"
    <%
              if(user!=null)
              {
    %>
         value="<%=user.getCity()%>"
    <%
              }
              else
              {
    %>
          value="0"
    <%
              }
    %>
  >
  <input type="hidden" name="gender" id="gender"
    <%
              if(user!=null)
              {
    %>
         value="<%=user.getGender()%>"
    <%
              }
              else
              {
    %>
         value="0"
    <%
              }
    %>
  >
  <input type="hidden" name="country" id="country"
    <%
              if(user!=null)
              {
    %>
         value="<%=user.getCountry()%>"
    <%
              }
              else
              {
    %>
         value="0"
    <%
              }
    %>
  >
</form>
</body>
</html>