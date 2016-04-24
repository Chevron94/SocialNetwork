<%@ page import="network.entity.User" %>
<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 10/4/15
  Time: 5:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% User user = (User)request.getAttribute("user"); %>
<html>
<head>
  <title id="title">Dialogs</title>
  <script type="text/javascript">
    var dialogStart = 0;
  </script>

  <%@include file="templates/scripts.jsp"%>

</head>
<body onload="pageLoad()">
<br>
<%@include file="templates/header.jsp"%>
<!-- /container -->
<input type="hidden" class="input-block-level" id="nickname" value="<%=user.getLogin()%>">
<input type="hidden" id="dialogId" value="0">
<div class="row table-fixed table-bordered" style="margin: 1%">
  <div class="col-xs-3" style="height: 525px; overflow-y: scroll">
    <div id="dialogs" style="margin-top: 1%;">

    </div>
    <button id="loadMoreDialogsButton" class="btn btn-info btn-block" style="display: none" onclick="loadMoreDialogs()">Load more dialogs</button>
    <input type="hidden" name="current" id="current" value="">
  </div>
  <div class="col-xs-9" style="margin-top: 1%">
    <div class="panel panel-primary">
      <div class="panel-body" id="tableDiv">
        <button class="btn btn-info btn-block" style="display: none" id="loadMoreMessages">Load previous messages</button>
        <ul class="chat" id="response">

        </ul>
      </div>
    </div>
      <div class="row" style="height: 70px">
        <div class="col-xs-10">
          <textarea class="form-control" style="resize: none" id="message" rows="2"></textarea>
        </div>
        <div class="col-xs-2">
          <input type="button" onclick="return buttonAction();" class="btn btn-large btn-block btn-block-full btn-primary"
                 value="Send message" />
        </div>
      </div>
  </div>
</div>
<script type="text/javascript">
$('#message').keydown(function (e) {
if (e.ctrlKey && e.keyCode == 13) {
  if($('#message').val().trim() != "")
    sendMessage(null,null,null);
}
});
if($('#dialogId').val() != '0')
  $('#dialog' + $('#dialogId').val()).addClass("active");
</script>

</body>
</html>
