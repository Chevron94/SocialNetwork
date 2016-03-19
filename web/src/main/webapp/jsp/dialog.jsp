<%@ page import="network.entity.User" %>
<%@ page import="java.util.List" %>
<%@ page import="network.entity.Dialog" %>
<%@ page import="network.entity.Message" %>
<%@ page import="java.text.SimpleDateFormat" %>
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
  <script type="text/javascript" src="/resources/js/dialog.js"></script>

  <%@include file="templates/scripts.jsp"%>
    <%
    List<Dialog> dialogs = (List<Dialog>)request.getAttribute("dialogs");
    List<Message> messages =  (List<Message>)request.getAttribute("messages");
  %>

</head>
<body onload="pageLoad()">
<br>
<%@include file="templates/header.jsp"%>
<!-- /container -->
<input type="hidden" class="input-block-level" id="nickname" value="<%=user.getLogin()%>">
<input type="hidden" id="idUser" value="<%=idUser%>">
<input type="hidden" id="dialogId" value="<%=dialogs!=null && dialogs.size()>0 ? dialogs.get(0).getId() : 0%>">
<div class="row table-fixed table-bordered">
  <div class="col-xs-3">
    <div class="row">
      <h4 align="center">Dialogs</h4>
    </div>
    <%
      if(dialogs != null)
      {
        for(Dialog d:dialogs)
        {
    %>
    <div class="row" style="margin: auto">
      <button id="<%=d.getId()%>" class="btn btn-danger btn-large btn-block"
              onclick="return chatClick(<%=d.getId()%>)" value="<%=d.getName()%>"><%=d.getName()%></button>
    </div>
    <br>
    <%
        }
      }
    %>
    <input type="hidden" name="current" id="current" value="">
  </div>
  <div class="col-xs-9 table-bordered">

      <h4 id="dialogName" align="center"><%=dialogs!=null && dialogs.size()>0 ? dialogs.get(0).getName() : "SELECT A DIALOG"%></h4>
      <div id="tableDiv" class="table-responsive" style="height: 300px; overflow-y: scroll">
        <table id="response" class="table table-bordered table-fixed" style="font-size: 100%">
          <%if(messages!= null && messages.size()>0){
            for(Message message:messages){
              %>
              <tr><td class="sender col-xs-2"> <%=message.getUser().getLogin()%>
                      </td><td class="sender col-xs-8">  <%=message.getText()%>
                      </td><td class="sender col-xs-2"><%
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                out.print(simpleDateFormat.format(message.getDateTime()));%>
              </td></tr><%
            }
          }else{%>
          <tr>
            <td class="col-xs-2"></td>
            <td class="col-xs-8"></td>
            <td class="col-xs-3"></td>
          </tr>
          <%}%>
        </table>
      </div>
      <div class="row">
        <div class="col-xs-8">
          <textarea class="form-control" id="message" rows="3"></textarea>
        </div>
        <div class="col-xs-4">
          <input type="button" onclick="return buttonAction();" class="btn btn-large btn-block btn-primary"
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
</script>

</body>
</html>
