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
    <%@include file="scripts.jsp" %>

    <%
        List<Continent> continents = (List<Continent>) request.getAttribute("continents");
        List<Country> countries = (List<Country>) request.getAttribute("countries");
        City city = (City) request.getAttribute("city");

        SearchDto searchDto = (SearchDto) request.getAttribute("searchDto");
    %>
</head>
<body>
<br>
<%@include file="header.jsp"%>
<%@include file="message.jsp"%>
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