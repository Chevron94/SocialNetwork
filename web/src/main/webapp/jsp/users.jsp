<%@include file="templates/users.jsp"%>
<%
    List<User> users = (List<User>) request.getAttribute("users");
%>
<div class="col-xs-8">
    <div class="tab-content">
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
</div>
</body>
</html>
