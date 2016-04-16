<%@ page import="network.entity.Country" %>
<%@ page import="java.util.List" %>
<%@ page import="network.entity.City" %>
<%@ page import="network.entity.Gender" %>
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
      $('#country').val($('#country_select').select().val());
      $("#city_select").empty().append('<option value="0"></option>').val('0').trigger('change');
      $('#city').val('0');
    }
    function updateGender() {
      if(document.getElementById("gender_select_1").checked){
        $('#gender').val($('#gender_select_1').val());
      }else $('#gender').val($('#gender_select_2').val());
    }
    function updateCity() {
        $('#city').val($('#city_select').select().val());
    }
  </script>
  <title>Registration</title>
  <%@include file="templates/scripts.jsp"%>
</head>
<body>
<%
  List<Country> countryList = (List<Country>) request
          .getAttribute("countries");
  List<Gender> genders = (List<Gender>) request
          .getAttribute("genders");
  List<String> errors = (List<String>)request.getAttribute("errors");
  City city = (City) request.getAttribute("city");

  UserDto user = (UserDto)request.getAttribute("user");
%>
<br>
<%@include file="templates/header.jsp"%>


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
<form method="POST" id="registration" class="form-horizontal" name="registration" enctype="multipart/form-data">

    <div class="form-group form-group-sm">
      <div class="row" style="margin-left:20%">
        <label class="control-label col-sm-2" for="login">Login<sup>*</sup></label>
        <div class="col-xs-6">
          <input type="text" id="login" class="form-control" name="login" pattern="^\w+$" required
            <%
              if(user!=null)
              {
            %>
                  value="<%=user.getLogin()%>"
            <%
              }
            %>
          >
          <label class="control-label" for="login" style="font-size: smaller">Can contains only A-Z,a-z,0-9,_</label>
        </div>
      </div>
    </div>
    <div class="form-group form-group-sm">
      <div class="row" style="margin-left:20%">
        <label class="control-label col-xs-2" for="password">Password<sup>*</sup></label>
        <div class="col-xs-6" align="left">
          <input type="password" class="form-control" id="password" name="password" pattern="[A-Za-z0-9_-]{6,64}" required onchange="checkPasswords()">
          <label class="control-label" for="login" style="font-size: smaller">Password must have length 6-64 and can contains A-Z,a-z,0-9,_,-</label>
        </div>
      </div>
    </div>

  <div class="form-group form-group-sm">
    <div class="row" style="margin-left:20%">
      <label class="control-label col-xs-2" for="retype">Retype password<sup>*</sup></label>
      <div class="col-xs-6" align="left">
        <input type="password" class="form-control" id="retype" name="retype" pattern="[A-Za-z0-9_-]{6,64}" required onchange="checkPasswords()">
      </div>
    </div>
  </div>

    <div class="form-group form-group-sm">
      <div class="row" style="margin-left:20%">
        <label class="control-label col-xs-2" for="email">E-mail<sup>*</sup></label>
        <div class="col-xs-6" align="left">
          <input type="email" id="email" class="form-control" name="email" required
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
    <div class="form-group form-group-sm">
      <div class="row" style="margin-left:20%">
        <label class="control-label col-xs-2" for="name">Name<sup>*</sup></label>
        <div class="col-xs-6" align="left">
          <input type="text" id="name" class="form-control" pattern="^\w+([\s-]\w+)*$" name="name"required
            <%
              if(user!=null)
              {
            %>
                 value="<%=user.getName()%>"
            <%
              }
            %>
          >
          <label class="control-label" for="name" style="font-size: smaller">Can contains only A-Z,a-z,0-9,_,- and space</label>
        </div>
      </div>
    </div>
    <div class="form-group form-group-sm">
      <div class="row" style="margin-left:20%">
        <label class="control-label col-xs-2" for="filePhoto">Photo</label>
        <div class="col-xs-6" align="left">
          <div class="input-group">
            <span class="input-group-btn">
               <span class="btn btn-default btn-file btn-sm">
            Browse&hellip;<input type="file" id="filePhoto" name="filePhoto" placeholder="http://site.com/photo.jpg" accept="image/*">
              </span>
            </span>
            <input type="text" id="filename" class="form-control" readonly>
          </div>

        </div>
      </div>
    </div>
    <div class="form-group form-group-sm">
      <div class="row" style="margin-left:20%">
        <label class="control-label col-xs-2" for="gender_select_1">Gender<sup>*</sup></label>
        <div class="col-xs-6" align="left">
          <label class="radio-inline"><input type="radio" name="gender_select" id="gender_select_1" value="<%=genders.get(0).getId()%>" <%=(user==null || user.getGender().equals(genders.get(0).getId().toString()))?"checked":""%> onchange="updateGender()" ><%=genders.get(0).getName()%></label>
          <label class="radio-inline"><input type="radio" name="gender_select" id="gender_select_2" value="<%=genders.get(1).getId()%>" <%=(user!=null && user.getGender().equals(genders.get(1).getId().toString()))?"checked":""%>  onchange="updateGender()"><%=genders.get(1).getName()%></label>
        </div>
      </div>
    </div>
    <script language="JavaScript">
      $(function() {
        $( "#birthday" ).datepicker({
          dateFormat: 'yy-mm-dd',
          changeMonth: true,
          changeYear: true,
          yearRange: '-99:-3'});
      });
    </script>
    <div class="form-group form-group-sm">
      <div class="row" style="margin-left:20%">
        <label class="control-label col-xs-2" for="birthday">Birthday<sup>*</sup></label>
        <div class="col-xs-6" align="left">
          <input type="date" id="birthday" pattern="(19|20)\d\d-((0[1-9]|1[012])-(0[1-9]|[12]\d)|(0[13-9]|1[012])-30|(0[13578]|1[02])-31)" placeholder="YYYY-MM-DD" class="form-control" name="birthday" required
            <%
              if(user!=null)
              {
            %>
                 value="<%=user.getBirthday().toString().split(" ")[0]%>"
            <%
              }
            %>
          >
          <label class="control-label" for="birthday" style="font-size: smaller">Format: YYYY-MM-DD. Example: 1990-12-31</label>
        </div>
      </div>
    </div>
    <div class="form-group form-group-sm">
      <div class="row" style="margin-left:20%">
        <label class="control-label col-xs-2" for="country_select">Country<sup>*</sup></label>
        <div class="col-xs-6" align="left">
          <select name="country_select" class="icon-menu form-control" id="country_select" onchange="updateSelectOptions()">
            <option value="0" selected>Select country</option>
            <% for (int i=0;i<countryList.size(); i++)
            {
            %>
              <option style="background-image:url(<%=countryList.get(i).getFlagURL()%>); background-size: 20px 20px; background-position: left center;" value="<%=countryList.get(i).getId()%>"
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
    <div class="form-group form-group-sm">
      <div class="row" style="margin-left:20%">
        <label class="control-label col-xs-2" for="city_select">City<sup>*</sup></label>
        <div class="col-xs-6" align="left">
          <select name="city_select" id="city_select"  class="js-example-data-ajax form-control" onchange="updateCity()">
            <% if (city != null){
            %>
            <option value="<%=city.getId()%>" selected="selected"><%=city.getName()%></option>
            <%
            }else{
            %><option value="0" selected="selected">Select city</option><%
            }
          %>
          </select>
        </div>
      </div>
    </div>
    <div class="form-group form-group-sm">
      <div class="row" style="margin-left:20%">
        <label class="control-label col-xs-2" for="description">About me</label>
        <div class="col-xs-6" align="left">
          <textarea class="form-control" id="description"  name="description" rows="3"
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
    <div class="form-group form-group-sm">
      <div class="row" style="margin-left:20%">
        <div class="col-xs-offset-2 col-xs-6">
          <input type="submit" class="btn btn-primary" value="Submit">
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
         value="<%=genders.get(0).getId()%>"
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
  <script>

    function formatCity (data) {
      return data.name;
    }

    function formatCitySelection (data) {
      return data.name;
    }

    $('#city_select').select2({
      ajax: {
        url : window.location.protocol+'//'+window.location.hostname+':'+window.location.port+'/registration/citiesByCountry',
        dataType:'json',
        type: "GET",
        quietMillis: 250,
        data:function(params){
          return{
            searchId : $('#country_select').select().val(),
            name: params.term
          };
        },
        processResults: function (data) {
          return {
            results: $.map(data, function (item) {
              return {
                name: item.name,
                id: item.id
              }
            })
          };
        }
      },
      placeholder: 'Select a city',
      minimumInputLength: 1,
      allowClear: true,
      templateResult: formatCity,
      templateSelection: formatCitySelection,
      escapeMarkup: function (m) {
        return m;
      }
    }).val(<%
              if(user!=null)
              { out.print(user.getCity());
              }
              else
              {
    %>
    '0'
    <%
              }
    %>).trigger('change');

    function checkPasswords()
    {
      var passl = document.getElementById("password");
      var pass2 = document.getElementById("retype");
      if(passl.value!=pass2.value)
        passl.setCustomValidity("Passwords are not equals!");
      else
        passl.setCustomValidity("");
    }

    $(document).on('change', '.btn-file :file', function() {
      var input = $(this),
              numFiles = input.get(0).files ? input.get(0).files.length : 1,
              label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
      input.trigger('fileselect', [numFiles, label]);
    });

    $(document).ready( function() {
      $('.btn-file :file').on('fileselect', function(event, numFiles, label) {

        var input = $(this).parents('.input-group').find(':text'),
                log = numFiles > 1 ? numFiles + ' files selected' : label;

        if( input.length ) {
          input.val(log);
        } else {
          if( log ) alert(log);
        }

      });
    });

  </script>
</form>
</body>
</html>