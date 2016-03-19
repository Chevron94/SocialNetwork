<%@include file="templates/users.jsp"%>
<%
    List<User> friends = (List<User>) request.getAttribute("friends");
    List<User> sentFriendsRequests = (List<User>) request.getAttribute("sentFriendsRequests");
    List<User> receivedFriendsRequests = (List<User>) request.getAttribute("receivedFriendsRequests");
    Long idRequestUser = (Long)request.getAttribute("idRequestUser");
%>
<div class="col-xs-8">
    <ul class="nav nav-pills nav-justified" id="tabMenu">
        <li class="active"><a data-toggle="pill" href="#friendsTab">Friends</a></li>
        <%if(idUser==idRequestUser){%>
        <li><a data-toggle="pill" href="#friendsRequestsTab">Received requests</a></li>
        <li><a data-toggle="pill" href="#sentRequestsTab">Sent requests</a></li>
        <%}%>
    </ul>
    <div class="tab-content">
        <div id="friendsTab" class="tab-pane fade in active" style="max-height: 500px; overflow-y: scroll">
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
            <button onclick="loadMore(0,idUser,'friends')">LOAD</button>
        </div>
        <%if(idUser==idRequestUser){%>
        <div id="friendsRequestsTab" class="tab-pane fade" style="max-height: 500px; overflow-y: scroll">
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
        <%}%>
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
