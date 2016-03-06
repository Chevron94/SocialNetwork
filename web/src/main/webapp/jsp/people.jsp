<%@ page import="network.entity.User" %>
<%@ page import="java.util.List" %>
<%@ page import="network.service.FriendRequestService" %>
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
    <script src="/resources/js/people.js"></script>
    <script src="/resources/js/dropdown.js"></script>
    <%@include file="templates/scripts.jsp" %>
    <%
        List<User> friends = (List<User>) request.getAttribute("friends");
        List<User> sent_friends_requests = (List<User>) request.getAttribute("sent_friends_requests");
        List<User> received_friends_requests = (List<User>) request.getAttribute("received_friends_requests");
        List<User> users = (List<User>) request.getAttribute("users");


        List<Continent> continents = (List<Continent>) request.getAttribute("continents");
        List<Country> countries = (List<Country>) request.getAttribute("countries");
        List<City> cities = (List<City>) request.getAttribute("cities");

        SearchDto searchDto = (SearchDto) request.getAttribute("searchDto");
    %>
</head>
<body>
<%@include file="templates/menu.jsp" %>

<div class="col-xs-5">
    <div class="row">
        <div class="col-xs-3"></div>
        <div class="col-xs-9">
            <h4>Search</h4>

            <form method="post" id="search" name="search">
                <div class="row" style="margin-bottom: 3%">
                    <div class="col-xs-3">
                        <label class="control-label" for="continent-select">Continent</label>
                    </div>
                    <div class="col-xs-9">
                        <select name="continent-select" id="continent-select" onchange="updateContinentSelectOptions()">
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
                        <select name="country-select" id="country-select" onchange="updateCountrySelectOptions()">
                            <option value="0" selected>Select country</option>
                            <% if (countries != null)
                                for (int i = 0; i < countries.size(); i++) {
                                    if (searchDto != null && countries.get(i).getId().toString().equals(searchDto.getCountry())) {
                            %>
                            <option selected value="<%=countries.get(i).getId()%>"><%=countries.get(i).getName()%>
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
                        <select name="city-select" id="city-select" onchange="updateValues('city-select','city')">
                            <option value="0" selected>Select city</option>
                            <% if (cities != null)
                                for (int i = 0; i < cities.size(); i++) {
                                    if (searchDto != null && cities.get(i).getId().toString().equals(searchDto.getCity())) {
                            %>
                            <option selected value="<%=cities.get(i).getId()%>"><%=cities.get(i).getName()%>
                            </option>
                            <%
                            } else {
                            %>
                            <option value="<%=cities.get(i).getId()%>"><%=cities.get(i).getName()%>
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
                            <input type="checkbox" id="genderFemale" checked onchange="checkFemale(this)">
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
                        <label class="control-label" for="from-select">Age</label>
                    </div>
                    <div class="col-xs-9">
                        <select name="from-select" id="from-select" onchange="updateValues('from-select','ageFrom')">
                            <option value="0" selected>0</option>
                            <%
                                for (Integer i = 1; i <= 100; i++) {
                                    if (searchDto != null && searchDto.getAgeFrom().equals(i.toString())) {
                            %>
                            <option selected value="<%=i%>"><%=i%>
                            </option>
                            <%
                            } else {
                            %>
                            <option value="<%=i%>"><%=i%>
                            </option>
                            <%
                                    }
                                }
                            %>
                        </select> <b> - </b>
                        <select name="to-select" id="to-select" onchange="updateValues('to-select','ageTo')">
                            <%
                                for (Integer i = 1; i <= 100; i++) {
                                    if (searchDto != null && searchDto.getAgeTo().equals(i.toString())) {
                            %>
                            <option selected value="<%=i%>"><%=i%>
                            </option>
                            <%
                            } else {
                                if (searchDto != null) {
                            %>
                            <option value="<%=i%>"><%=i%>
                            </option>
                            <%
                            } else {
                            %>
                            <option selected value="<%=i%>"><%=i%>
                            </option>
                            <%
                                        }
                                    }
                                }%>
                        </select>
                    </div>
                </div>
                <div class="row" style="margin-bottom: 3%">
                    <div class="col-xs-3">
                        <label class="control-label" for="languages-select">Language</label>
                    </div>
                    <div class="col-xs-9">
                        <select name="languages-select" id="languages-select"
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

            </form>
        </div>
    </div>
</div>
<div class="col-xs-7" style="max-height: 500px; overflow-y: scroll">
    <div id="friendsRequests">
        <%
            if (received_friends_requests != null && received_friends_requests.size() > 0) {
        %>
        <h4 align="center">Received requests</h4>
        <%
            for (int i = 0; i < received_friends_requests.size(); i++) {
        %>
        <div class="row scale-text" style="margin: auto">
            <div class="col-xs-9">
                <div class="row" style="margin: auto">
                    <div class="col-xs-12">
                        <div id="<%=received_friends_requests.get(i).getId()%>" class="row">
                            <div class="col-xs-3">
                                <img src="<%=received_friends_requests.get(i).getPhotoURL()%>"
                                     class="img img-responsive" alt="Responsive image">
                            </div>
                            <div class="col-xs-9">
                                <div class="row table-bordered table-fixed">
                                    <div class="col-xs-3">
                                        <label class="control-label">Name</label>
                                    </div>
                                    <div class="col-xs-9">
                                        <label class="control-label"><%=received_friends_requests.get(i).getName()%>
                                        </label>
                                    </div>
                                </div>

                                <div class="row table-bordered table-fixed">
                                    <div class="col-xs-3">
                                        <label class="control-label">Birthday</label>
                                    </div>
                                    <div class="col-xs-9">
                                        <label class="control-label"><%=received_friends_requests.get(i).getBirthday().toString().split(" ")[0]%>
                                        </label>
                                    </div>
                                </div>

                                <div class="row table-bordered table-fixed">
                                    <div class="col-xs-3">
                                        <label class="control-label">From</label>
                                    </div>
                                    <div class="col-xs-9">
                                        <label class="control-label"><img
                                                src="<%=received_friends_requests.get(i).getCountry().getFlagURL()%>"> <%=received_friends_requests.get(i).getCity().getName() + " (" + received_friends_requests.get(i).getCountry().getName() + ")"%>
                                        </label>
                                    </div>
                                </div>

                                <div class="row table-bordered table-fixed">
                                    <div class="col-xs-3">
                                        <label class="control-label">Gender</label>
                                    </div>
                                    <div class="col-xs-9">
                                        <label class="control-label"><%=received_friends_requests.get(i).getGender().getName()%>
                                        </label>
                                    </div>
                                </div>
                                <div class="row table-bordered table-fixed">
                                    <div class="col-xs-3">
                                        <label class="control-label">Languages</label>
                                    </div>
                                    <div class="col-xs-9">
                                        <label class="control-label"><%=received_friends_requests.get(i).getCountry().getName()%>
                                        </label>
                                    </div>
                                </div>

                                <div class="row table-bordered table-fixed">
                                    <div class="col-xs-3">
                                        <label class="control-label">Profile</label>
                                    </div>
                                    <div class="col-xs-9">
                                        <a href="/user<%=received_friends_requests.get(i).getId()%>">
                                            <%=pageContext.getRequest().getScheme() + "://"
                                                    + pageContext.getRequest().getServerName() + ":"
                                                    + pageContext.getRequest().getServerPort() + "/user"
                                                    + received_friends_requests.get(i).getId()%>
                                        </a>
                                    </div>
                                </div>
                                <div class="row table-bordered table-fixed btn-xs" align="center">
                                    <button id="add_<%=received_friends_requests.get(i).getId()%>" type="button"
                                            class="Add btn btn-success btn-xs" onclick="acceptRequest(
                                        <%=received_friends_requests.get(i).getId()%>,
                                        <%=idUser%>); return false">Add
                                    </button>
                                    <button id="send_<%=received_friends_requests.get(i).getId()%>" type="button"
                                            class="btn btn-info btn-xs">Send Message
                                    </button>
                                    <button id="delete_<%=received_friends_requests.get(i).getId()%>" type="button"
                                            class="Add btn btn-danger btn-xs">Delete
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
            }
        %>
    </div>
    <div id="friends">
        <h4 align="center">Friends</h4>
        <%
            if (friends.size() > 0) {
                for (int i = 0; i < friends.size(); i++) {
        %>
        <div class="row scale-text" style="margin: auto">
            <div class="col-xs-9">
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
                                        <label class="control-label"><img
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
                                                    + pageContext.getRequest().getServerName() + ":"
                                                    + pageContext.getRequest().getServerPort() + "/user"
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
                                            class="btn btn-info btn-xs">Send Message
                                    </button>
                                    <button id="delete_<%=friends.get(i).getId()%>" type="button"
                                            class="Add btn btn-danger btn-xs">Delete
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
        %>

        <%
        } else {
        %> <h5 align="center">You haven't friends yet</h5><%
        }
    %>
    </div>
    <div id="sended_requests">

        <%
            if (sent_friends_requests != null && sent_friends_requests.size() > 0) {
                %>
        <h4 align="center">Sended Requests</h4>
        <%
                for (int i = 0; i < sent_friends_requests.size(); i++) {
        %>
        <div class="row scale-text" style="margin: auto">
            <div class="col-xs-9">
                <div class="row" style="margin: auto">
                    <div class="col-xs-12">
                        <div id="<%=sent_friends_requests.get(i).getId()%>" class="row">
                            <div class="col-xs-3">
                                <img src="<%=sent_friends_requests.get(i).getPhotoURL()%>" class="img img-responsive"
                                     alt="Responsive image">
                            </div>
                            <div class="col-xs-9">
                                <div class="row table-bordered table-fixed">
                                    <div class="col-xs-3">
                                        <label class="control-label">Name</label>
                                    </div>
                                    <div class="col-xs-9">
                                        <label class="control-label"><%=sent_friends_requests.get(i).getName()%>
                                        </label>
                                    </div>
                                </div>

                                <div class="row table-bordered table-fixed">
                                    <div class="col-xs-3">
                                        <label class="control-label">Birthday</label>
                                    </div>
                                    <div class="col-xs-9">
                                        <label class="control-label"><%=sent_friends_requests.get(i).getBirthday().toString().split(" ")[0]%>
                                        </label>
                                    </div>
                                </div>

                                <div class="row table-bordered table-fixed">
                                    <div class="col-xs-3">
                                        <label class="control-label">From</label>
                                    </div>
                                    <div class="col-xs-9">
                                        <label class="control-label"><img
                                                src="<%=sent_friends_requests.get(i).getCountry().getFlagURL()%>"> <%=sent_friends_requests.get(i).getCity().getName() + " (" + sent_friends_requests.get(i).getCountry().getName() + ")"%>
                                        </label>
                                    </div>
                                </div>

                                <div class="row table-bordered table-fixed">
                                    <div class="col-xs-3">
                                        <label class="control-label">Gender</label>
                                    </div>
                                    <div class="col-xs-9">
                                        <label class="control-label"><%=sent_friends_requests.get(i).getGender().getName()%>
                                        </label>
                                    </div>
                                </div>
                                <div class="row table-bordered table-fixed">
                                    <div class="col-xs-3">
                                        <label class="control-label">Languages</label>
                                    </div>
                                    <div class="col-xs-9">
                                        <label class="control-label"><%=sent_friends_requests.get(i).getCountry().getName()%>
                                        </label>
                                    </div>
                                </div>

                                <div class="row table-bordered table-fixed">
                                    <div class="col-xs-3">
                                        <label class="control-label">Profile</label>
                                    </div>
                                    <div class="col-xs-9">
                                        <a href="/user<%=sent_friends_requests.get(i).getId()%>">
                                            <%=pageContext.getRequest().getScheme() + "://"
                                                    + pageContext.getRequest().getServerName() + ":"
                                                    + pageContext.getRequest().getServerPort() + "/user"
                                                    + sent_friends_requests.get(i).getId()%>
                                        </a>
                                    </div>
                                </div>
                                <div class="row table-bordered table-fixed btn-xs" align="center">
                                    <button id="add_<%=sent_friends_requests.get(i).getId()%>" type="button" disabled
                                            class="Add btn btn-success btn-xs" onclick="acceptRequest(
                                        <%=sent_friends_requests.get(i).getId()%>,
                                        <%=idUser%>); return false">Add
                                    </button>
                                    <button id="send_<%=sent_friends_requests.get(i).getId()%>" type="button"
                                            class="btn btn-info btn-xs">Send Message
                                    </button>
                                    <button id="delete_<%=sent_friends_requests.get(i).getId()%>" type="button"
                                            class="Add btn btn-danger btn-xs">Delete
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
            %>
        <h5 align="center">You haven't not aswered sended requests</h5>
        <%
        } else {
        %>

        <%
            }
        %>
    </div>
    <div id="other_users">
        <%
            if (users != null && users.size() > 0) {
        %>
        <h4 align="center">Other users</h4>


        <%
            for (int i = 0; i < users.size(); i++) {
        %>
        <div class="row scale-text" style="margin: auto">
            <div class="col-xs-9">
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
                                        <label class="control-label"><img
                                                src="/<%=users.get(i).getCountry().getFlagURL()%>"> <%=users.get(i).getCity().getName() + " (" + users.get(i).getCountry().getName() + ")"%>
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
                                                    + pageContext.getRequest().getServerName() + ":"
                                                    + pageContext.getRequest().getServerPort() + "/user"
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
                                            class="btn btn-info btn-xs">Send Message
                                    </button>
                                    <button id="delete_<%=users.get(i).getId()%>" type="button" disabled
                                            class="Add btn btn-danger btn-xs">Delete
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
            }
        %>
    </div>
</div>
</body>
</html>
