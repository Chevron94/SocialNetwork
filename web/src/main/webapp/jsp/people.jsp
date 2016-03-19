<%@ page import="network.entity.User" %>
<%@ page import="java.util.List" %>
<%@ page import="network.entity.Country" %>
<%@ page import="network.entity.City" %>
<%@ page import="network.dto.SearchDto" %>
<%@ page import="network.entity.Continent" %>
<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 10/6/15
  Time: 9:27 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>People</title>
    <%@include file="templates/scripts.jsp" %>

    <%
        List<User> friends = (List<User>) request.getAttribute("friends");
        List<User> sentFriendsRequests = (List<User>) request.getAttribute("sentFriendsRequests");
        List<User> receivedFriendsRequests = (List<User>) request.getAttribute("receivedFriendsRequests");
        List<User> users = (List<User>) request.getAttribute("users");


        List<Continent> continents = (List<Continent>) request.getAttribute("continents");
        List<Country> countries = (List<Country>) request.getAttribute("countries");
        City city = (City) request.getAttribute("city");

        SearchDto searchDto = (SearchDto) request.getAttribute("searchDto");
    %>
</head>
<body>
<br>
<%@include file="templates/header.jsp"%>
<%@include file="templates/message.jsp"%>
<div class="col-xs-4">
    <div class="row">
        <div class="col-xs-2"></div>
        <div class="col-xs-9">
            <h4>Search</h4>

            <form method="post" id="search" name="search">
                <div class="row" style="margin-bottom: 3%">
                    <div class="col-xs-3">
                        <label class="control-label" for="continent-select">Continent</label>
                    </div>
                    <div class="col-xs-9">
                        <select name="continent-select" class="form-control" id="continent-select" onchange="updateContinentSelectOptions()">
                            <option value="0" selected>Select continent</option>
                            <%
                                for (int i = 0; i < continents.size(); i++) {
                                    if (searchDto != null && continents.get(i).getId().toString().equals(searchDto.getContinent())) {
                            %>
                            <option selected value="<%=continents.get(i).getId()%>"><%=continents.get(i).getName()%>
                            </option>
                            <%
                            } else {
                            %>
                            <option value="<%=continents.get(i).getId()%>"><%=continents.get(i).getName()%>
                            </option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>
                </div>
                <div class="row" style="margin-bottom: 3%">
                    <div class="col-xs-3">
                        <label class="control-label" for="country-select">Country</label>
                    </div>
                    <div class="col-xs-9">
                        <select name="country-select" class="form-control" id="country-select" onchange="updateCountrySelectOptions()">
                            <option value="0" selected>Select country</option>
                            <% if (countries != null)
                                for (int i = 0; i < countries.size(); i++) {
                                    if (searchDto != null && countries.get(i).getId().toString().equals(searchDto.getCountry())) {
                            %>
                            <option selected="selected" value="<%=countries.get(i).getId()%>"><%=countries.get(i).getName()%>
                            </option>
                            <%
                            } else {
                            %>
                            <option value="<%=countries.get(i).getId()%>"><%=countries.get(i).getName()%>
                            </option>
                            <%
                                        }
                                    }
                            %>
                        </select>
                    </div>
                </div>
                <div class="row" style="margin-bottom: 3%">
                    <div class="col-xs-3">
                        <label class="control-label" for="city-select">City</label>
                    </div>
                    <div class="col-xs-9">
                        <select name="city-select" class="js-data-example-ajax form-control" id="city-select" onchange="updateValues('city-select','city')">
                            <% if (city != null){
                            %>
                            <option selected="selected" value="<%=city.getId()%>"><%=city.getName()%>
                            </option>
                            <%
                                }else{
                                    %><option value="0" selected>Select city</option><%
                                }
                            %>
                        </select>
                    </div>
                </div>
                <div class="row" style="margin-bottom: 3%">
                    <div class="col-xs-3">
                        <label class="control-label">Gender</label>
                    </div>
                    <div class="col-xs-9">
                        <label>
                            <%
                                if (searchDto != null && searchDto.getGender() != null && searchDto.getGender().contains("m")) {
                            %>
                            <input type="checkbox" id="genderMale" checked value="" onclick="checkMale(this)">
                            <%
                            } else {
                            %>
                            <input type="checkbox" id="genderMale" value="" onclick="checkMale(this)">
                            <%}%>
                            Male
                        </label>
                        <label>
                            <%
                                if (searchDto != null && searchDto.getGender() != null && searchDto.getGender().contains("f")) {
                            %>
                            <input type="checkbox" id="genderFemale"  checked onchange="checkFemale(this)">
                            <%
                            } else {
                            %>
                            <input type="checkbox" id="genderFemale" value="" onchange="checkFemale(this)">
                            <%}%>
                            Female
                        </label>
                    </div>
                </div>

                <script type="text/javascript">
                    function checkMale(cb) {
                        if (cb.checked) {
                            $('#gender').val($('#gender').val() + 'm');
                        } else {
                            if ($('#gender').val() == 'fm' || $('#gender').val() == 'mf')
                                $('#gender').val('f');
                            else $('#gender').val('');
                        }
                    }
                    function checkFemale(cb) {

                        if (cb.checked) {
                            $('#gender').val($('#gender').val() + 'f');
                        } else {
                            if ($('#gender').val() == 'fm' || $('#gender').val() == 'mf')
                                $('#gender').val('m');
                            else $('#gender').val('');
                        }
                    }
                </script>

                <div class="row" style="margin-bottom: 3%">
                    <div class="col-xs-3">
                        <label class="control-label" for="range">Age</label>
                    </div>
                    <div class="col-xs-9">
                        <input type="text" name="range" id="range" readonly style="border:0; font-weight:bold;">
                        <div id="slider-range"></div>
                        <script>
                            $(function() {
                                $( "#slider-range" ).slider({
                                    range: true,
                                    min: 1,
                                    max: 100,
                                    <%
                                if(searchDto==null){
                            %>
                                    values: [ 1, 100 ],
                                    <%}else{%>
                                    values: [ <%=searchDto.getAgeFrom()%>, <%=searchDto.getAgeTo()%> ],
                                    <%}%>
                                    slide: function( event, ui ) {
                                        $( "#range" ).val( ui.values[ 0 ] + " - " + ui.values[ 1 ] );
                                        $("#ageFrom").val( ui.values[ 0 ]);
                                        $("#ageTo").val( ui.values[ 1 ]);
                                    }
                                });
                                $( "#range" ).val($( "#slider-range" ).slider( "values", 0 ) +
                                        " - " + $( "#slider-range" ).slider( "values", 1 ) );
                            });
                        </script>
                    </div>
                </div>
                <div class="row" style="margin-bottom: 3%">
                    <div class="col-xs-3">
                        <label class="control-label" for="languages-select">Language</label>
                    </div>
                    <div class="col-xs-9">
                        <select name="languages-select" id="languages-select" class="form-control"
                                onchange="updateValues('languages-select','language')">
                            <option value="0" selected>Select languages</option>
                        </select>
                    </div>
                </div>
                <div class="row" style="margin-bottom: 3%">
                    <div class="col-xs-12" align="center">
                        <input align="center" type="submit" class="btn btn-primary" value="Search">
                    </div>
                </div>

                <input type="hidden" name="continent" id="continent"
                       value="<%= searchDto != null ? searchDto.getContinent() : "0"%>">
                <input type="hidden" name="country" id="country"
                       value="<%= searchDto != null  ? searchDto.getCountry() : "0"%>">
                <input type="hidden" name="city" id="city" value="<%= searchDto != null ? searchDto.getCity() : "0"%>">
                <input type="hidden" name="gender" id="gender"
                       value="<%= searchDto != null ? searchDto.getGender() : ""%>">
                <input type="hidden" name="ageFrom" id="ageFrom"
                       value="<%= searchDto != null ? searchDto.getAgeFrom() : "0"%>">
                <input type="hidden" name="ageTo" id="ageTo"
                       value="<%= searchDto != null ? searchDto.getAgeTo() : "100"%>">
                <input type="hidden" name="Language" id="language"
                       value="<%= searchDto != null ? searchDto.getLanguage() : "0"%>">


                <script>

                    function formatCity (data) {
                        return data.name;
                    }

                    function formatCitySelection (data) {
                        return data.name;
                    }

                    $('#city-select').select2({
                        placeholder: "Select a city",
                        allowClear: true,
                        ajax: {
                            url : window.location.protocol+'//'+window.location.hostname+':'+window.location.port+'/registration/citiesByCountry',
                            dataType:'json',
                            type: "GET",
                            quietMillis: 250,
                            data:function(params){
                                return{
                                    searchId : $('#country-select').select().val(),
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
                        minimumInputLength: 1,
                        templateResult: formatCity,
                        templateSelection: formatCitySelection,
                        escapeMarkup: function (m) {
                            return m;
                        }
                    }).val(<%
              if(city!=null)
              { out.print(city.getId());
              }
              else
              {
    %>
                            '0'
                            <%
                                      }
                            %>);
                </script>
            </form>
        </div>
    </div>
</div>
<div class="col-xs-8">
    <ul class="nav nav-pills nav-justified" id="tabMenu">
        <li class="active"><a data-toggle="pill" href="#friendsRequestsTab">Received requests</a></li>
        <li><a data-toggle="pill" href="#friendsTab">Friends</a></li>
        <li><a data-toggle="pill" href="#sentRequestsTab">Sent requests</a></li>
        <li><a data-toggle="pill" href="#otherUsersTab">Other users</a></li>
    </ul>
    <div class="tab-content">
        <div id="friendsRequestsTab" class="tab-pane fade in active" style="max-height: 500px; overflow-y: scroll">
            <h4 align="center">Received requests</h4>
            <div id="friendRequests">
                <%
                    if (receivedFriendsRequests != null && receivedFriendsRequests.size() > 0) {
                        for (int i = 0; i < receivedFriendsRequests.size(); i++) {
                %>
                <div class="row scale-text" style="margin: auto">
                    <div class="col-xs-11">
                        <div class="row" style="margin: auto">
                            <div class="col-xs-12">
                                <div id="<%=receivedFriendsRequests.get(i).getId()%>" class="row">
                                    <div class="col-xs-3">
                                        <img src="<%=receivedFriendsRequests.get(i).getPhotoURL()%>"
                                             class="img img-responsive" alt="Responsive image">
                                    </div>
                                    <div class="col-xs-9">
                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">Name</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <label class="control-label"><%=receivedFriendsRequests.get(i).getName()%>
                                                </label>
                                            </div>
                                        </div>

                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">Birthday</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <label class="control-label"><%=receivedFriendsRequests.get(i).getBirthday().toString().split(" ")[0]%>
                                                </label>
                                            </div>
                                        </div>

                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">From</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <label class="control-label"><img style="height: 20px"
                                                                                  src="<%=receivedFriendsRequests.get(i).getCountry().getFlagURL()%>"> <%=receivedFriendsRequests.get(i).getCity().getName() + " (" + receivedFriendsRequests.get(i).getCountry().getName() + ")"%>
                                                </label>
                                            </div>
                                        </div>

                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">Gender</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <label class="control-label"><%=receivedFriendsRequests.get(i).getGender().getName()%>
                                                </label>
                                            </div>
                                        </div>
                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">Languages</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <label class="control-label"><%=receivedFriendsRequests.get(i).getCountry().getName()%>
                                                </label>
                                            </div>
                                        </div>

                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">Profile</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <a href="/user<%=receivedFriendsRequests.get(i).getId()%>">
                                                    <%=pageContext.getRequest().getScheme() + "://"
                                                            + pageContext.getRequest().getServerName() +  "/user"
                                                            + receivedFriendsRequests.get(i).getId()%>
                                                </a>
                                            </div>
                                        </div>
                                        <div class="row table-bordered table-fixed btn-xs" align="center">
                                            <button id="add_<%=receivedFriendsRequests.get(i).getId()%>" type="button"
                                                    class="Add btn btn-success btn-xs" onclick="acceptRequest(
                                                <%=receivedFriendsRequests.get(i).getId()%>,
                                                <%=idUser%>); return false">Add
                                            </button>
                                            <button id="send_<%=receivedFriendsRequests.get(i).getId()%>" type="button"
                                                    class="btn btn-info btn-xs" onclick="newMessage(<%=idUser%>, <%=receivedFriendsRequests.get(i).getId()%>, '<%=receivedFriendsRequests.get(i).getLogin()%>');return false">Send Message
                                            </button>
                                            <button id="delete_<%=receivedFriendsRequests.get(i).getId()%>" type="button"
                                                    class="Add btn btn-danger btn-xs" onclick="deleteRequest(<%=idUser%>, <%=receivedFriendsRequests.get(i).getId()%>); return false">Delete
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <%
                        }
                        %><div id="receivedRequestsNotFound"></div><%
                    }else{
                %><div id="receivedRequestsNotFound"><h5 align="center">Not found</h5></div><%
                        }
                %>
            </div>
        </div>
        <div id="friendsTab" class="tab-pane fade" style="max-height: 500px; overflow-y: scroll">
            <h4 align="center">Friends</h4>
            <div id="friends">
                <%
                    if (friends.size() > 0) {
                        for (int i = 0; i < friends.size(); i++) {
                %>
                <div class="row scale-text" style="margin: auto">
                    <div class="col-xs-11">
                        <div class="row" style="margin: auto">
                            <div class="col-xs-12">
                                <div id="<%=friends.get(i).getId()%>" class="row">
                                    <div class="col-xs-3">
                                        <img src="<%=friends.get(i).getPhotoURL()%>" class="img img-responsive"
                                             alt="Responsive image">
                                    </div>
                                    <div class="col-xs-9">
                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">Name</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <label class="control-label"><%=friends.get(i).getName()%>
                                                </label>
                                            </div>
                                        </div>

                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">Birthday</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <label class="control-label"><%=friends.get(i).getBirthday().toString().split(" ")[0]%>
                                                </label>
                                            </div>
                                        </div>

                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">From</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <label class="control-label"><img style="height: 20px"
                                                                                  src="<%=friends.get(i).getCountry().getFlagURL()%>"> <%=friends.get(i).getCity().getName() + " (" + friends.get(i).getCountry().getName() + ")"%>
                                                </label>
                                            </div>
                                        </div>

                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">Gender</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <label class="control-label"><%=friends.get(i).getGender().getName()%>
                                                </label>
                                            </div>
                                        </div>
                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">Languages</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <label class="control-label"><%=friends.get(i).getCountry().getName()%>
                                                </label>
                                            </div>
                                        </div>

                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">Profile</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <a href="/user<%=friends.get(i).getId()%>">
                                                    <%=pageContext.getRequest().getScheme() + "://"
                                                            + pageContext.getRequest().getServerName() +  "/user"
                                                            + friends.get(i).getId()%>
                                                </a>
                                            </div>
                                        </div>
                                        <div class="row table-bordered table-fixed btn-xs" align="center">
                                            <button id="add_<%=friends.get(i).getId()%>" disabled type="button"
                                                    class="Add btn btn-success btn-xs" onclick="acceptRequest(
                                                <%=friends.get(i).getId()%>,
                                                <%=idUser%>); return false">Add
                                            </button>
                                            <button id="send_<%=friends.get(i).getId()%>" type="button"
                                                    class="btn btn-info btn-xs" onclick="newMessage(<%=idUser%>, <%=friends.get(i).getId()%>,'<%=friends.get(i).getLogin()%>');return false">Send Message
                                            </button>
                                            <button id="delete_<%=friends.get(i).getId()%>" type="button"
                                                    class="Add btn btn-danger btn-xs" onclick="deleteRequest(<%=idUser%>, <%=friends.get(i).getId()%>); return false">Delete
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <%
                    }
                %><div id="friendsNotFound"></div><%
            }else{
            %><div id="friendsNotFound"><h5 align="center">Not found</h5></div><%
                }
            %>
            </div>
        </div>
        <div id="sentRequestsTab" class="tab-pane fade" style="max-height: 500px; overflow-y: scroll">
            <h4 align="center">Sent Requests</h4>
            <div id="sentRequests">
                <%
                    if (sentFriendsRequests != null && sentFriendsRequests.size() > 0) {
                %>

                <%
                    for (int i = 0; i < sentFriendsRequests.size(); i++) {
                %>
                <div class="row scale-text" style="margin: auto">
                    <div class="col-xs-11">
                        <div class="row" style="margin: auto">
                            <div class="col-xs-12">
                                <div id="<%=sentFriendsRequests.get(i).getId()%>" class="row">
                                    <div class="col-xs-3">
                                        <img src="<%=sentFriendsRequests.get(i).getPhotoURL()%>" class="img img-responsive"
                                             alt="Responsive image">
                                    </div>
                                    <div class="col-xs-9">
                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">Name</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <label class="control-label"><%=sentFriendsRequests.get(i).getName()%>
                                                </label>
                                            </div>
                                        </div>

                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">Birthday</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <label class="control-label"><%=sentFriendsRequests.get(i).getBirthday().toString().split(" ")[0]%>
                                                </label>
                                            </div>
                                        </div>

                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">From</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <label class="control-label"><img style="height: 20px"
                                                                                  src="<%=sentFriendsRequests.get(i).getCountry().getFlagURL()%>"> <%=sentFriendsRequests.get(i).getCity().getName() + " (" + sentFriendsRequests.get(i).getCountry().getName() + ")"%>
                                                </label>
                                            </div>
                                        </div>

                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">Gender</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <label class="control-label"><%=sentFriendsRequests.get(i).getGender().getName()%>
                                                </label>
                                            </div>
                                        </div>
                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">Languages</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <label class="control-label"><%=sentFriendsRequests.get(i).getCountry().getName()%>
                                                </label>
                                            </div>
                                        </div>

                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">Profile</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <a href="/user<%=sentFriendsRequests.get(i).getId()%>">
                                                    <%=pageContext.getRequest().getScheme() + "://"
                                                            + pageContext.getRequest().getServerName() + "/user"
                                                            + sentFriendsRequests.get(i).getId()%>
                                                </a>
                                            </div>
                                        </div>
                                        <div class="row table-bordered table-fixed btn-xs" align="center">
                                            <button id="add_<%=sentFriendsRequests.get(i).getId()%>" type="button" disabled
                                                    class="Add btn btn-success btn-xs" onclick="acceptRequest(
                                                <%=sentFriendsRequests.get(i).getId()%>,
                                                <%=idUser%>); return false">Add
                                            </button>
                                            <button id="send_<%=sentFriendsRequests.get(i).getId()%>" type="button"
                                                    class="btn btn-info btn-xs"  onclick="newMessage(<%=idUser%>, <%=sentFriendsRequests.get(i).getId()%>,'<%=sentFriendsRequests.get(i).getLogin()%>');return false">Send Message
                                            </button>
                                            <button id="delete_<%=sentFriendsRequests.get(i).getId()%>" type="button"
                                                    class="Add btn btn-danger btn-xs" onclick="deleteRequest(<%=idUser%>, <%=sentFriendsRequests.get(i).getId()%>); return false">Delete
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <%
                    }
                %><div id="sentRequestsNotFound"></div><%
            }else{
            %><div id="sentRequestsNotFound"><h5 align="center">Not found</h5></div><%
                }
            %>
            </div>
        </div>
        <div id="otherUsersTab" class="tab-pane fade" style="max-height: 500px; overflow-y: scroll">
            <h4 align="center">Other users</h4>
            <div id="otherUsers">
                <%
                    if (users != null && users.size() > 0) {
                    for (int i = 0; i < users.size(); i++) {
                %>
                <div class="row scale-text" style="margin: auto">
                    <div class="col-xs-11">
                        <div class="row" style="margin: auto">
                            <div class="col-xs-12">
                                <div id="<%=users.get(i).getId()%>" class="row">
                                    <div class="col-xs-3">
                                        <img src="<%=users.get(i).getPhotoURL()%>" class="img img-responsive"
                                             alt="Responsive image">
                                    </div>
                                    <div class="col-xs-9">
                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">Name</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <label class="control-label"><%=users.get(i).getName()%>
                                                </label>
                                            </div>
                                        </div>

                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">Birthday</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <label class="control-label"><%=users.get(i).getBirthday().toString().split(" ")[0]%>
                                                </label>
                                            </div>
                                        </div>

                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">From</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <label class="control-label"><img style="height: 20px"
                                                                                  src="<%=users.get(i).getCountry().getFlagURL()%>"> <%=users.get(i).getCity().getName() + " (" + users.get(i).getCountry().getName() + ")"%>
                                                </label>
                                            </div>
                                        </div>

                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">Gender</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <label class="control-label"><%=users.get(i).getGender().getName()%>
                                                </label>
                                            </div>
                                        </div>
                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">Languages</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <label class="control-label"><%=users.get(i).getCountry().getName()%>
                                                </label>
                                            </div>
                                        </div>

                                        <div class="row table-bordered table-fixed">
                                            <div class="col-xs-3">
                                                <label class="control-label">Profile</label>
                                            </div>
                                            <div class="col-xs-9">
                                                <a href="/user<%=users.get(i).getId()%>">
                                                    <%=pageContext.getRequest().getScheme() + "://"
                                                            + pageContext.getRequest().getServerName() + "/user"
                                                            + users.get(i).getId()%>
                                                </a>
                                            </div>
                                        </div>
                                        <div class="row table-bordered table-fixed btn-xs" align="center">
                                            <button id="add_<%=users.get(i).getId()%>" type="button"
                                                    class="Add btn btn-success btn-xs" onclick="sendRequest(
                                                <%=idUser%>,<%=users.get(i).getId()%>); return false">Add
                                            </button>
                                            <button id="send_<%=users.get(i).getId()%>" type="button"
                                                    class="btn btn-info btn-xs"  onclick="newMessage(<%=idUser%>, <%=users.get(i).getId()%>,'<%=users.get(i).getLogin()%>');return false">Send Message
                                            </button>
                                            <button id="delete_<%=users.get(i).getId()%>" type="button" disabled
                                                    class="Add btn btn-danger btn-xs" onclick="deleteRequest(<%=idUser%>, <%=users.get(i).getId()%>); return false">Delete
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <%
                        }
                    }else{
                          %>  <h5 align="center">Not found</h5>  <%
                        }
                %>
            </div>
        </div>
    </div>

    <script>
        $('#tabMenu a').click(function(e) {
            e.preventDefault();
            $(this).tab('show');
        });

        // store the currently selected tab in the hash value
        $("ul.nav-pills > li > a").on("shown.bs.tab", function(e) {
            var id = $(e.target).attr("href").substr(1);
            window.location.hash = id;
        });

        // on load of the page: switch to the currently selected tab
        var hash = window.location.hash;
        $('#tabMenu a[href="' + hash + '"]').tab('show');
    </script>
</div>
</body>
</html>
