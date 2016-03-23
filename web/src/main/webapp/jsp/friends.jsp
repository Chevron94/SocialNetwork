<%@include file="templates/userForm.jsp"%>
<%
    Long idRequestUser = (Long)request.getAttribute("idRequestUser");
%>
<script type="text/javascript">
    var friendsStart = 0;
    var receivedRequestsStart = 0;
    var sentRequestsStart = 0;
    var idRequestUser = <%=idRequestUser%>;
</script>
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

            </div>
            <div align="center">
                <button id="loadMoreFriends" onclick="loadMore(friendsStart,<%=idRequestUser%>,'friends')">Load more</button>
            </div>
        </div>
        <%if(idUser==idRequestUser){%>
        <div id="friendsRequestsTab" class="tab-pane fade" style="max-height: 500px; overflow-y: scroll">
            <h4 align="center">Received requests</h4>
            <div id="friendRequests">

            </div>
            <div align="center">
                <button id="loadMoreReceived" onclick="loadMore(receivedRequestsStart,<%=idRequestUser%>,'received')">Load more</button>
            </div>
        </div>
        <div id="sentRequestsTab" class="tab-pane fade" style="max-height: 500px; overflow-y: scroll">
            <h4 align="center">Sent Requests</h4>
            <div id="sentRequests">

            </div>
            <div align="center">
                <button id="loadMoreSent" onclick="loadMore(sentRequestsStart,<%=idRequestUser%>,'sent')">Load more</button>
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
