<%--
  Created by IntelliJ IDEA.
  User: Роман
  Date: 09.03.2016
  Time: 15:14
  To change this template use File | Settings | File Templates.
--%>
<div id="messageWindow"><!-- Сaмo oкнo -->
    <span id="closeWindow">X</span> <!-- Кнoпкa зaкрыть -->
    <br>
    <form method="post" id="messageForm">

        <div class="control-group">
            <div class="row">
                <div class="col-xs-3" align="left">
                    <label for="receiverName" class="control-label">Receiver</label>
                </div>
                <div class="col-xs-9" align="right">
                    <input type="text" id="receiverName" class="form-control" readonly="readonly" value="">
                </div>
            </div>
        </div>
        <br>
        <div class="control-group">
            <div class="row">
                <div class="col-xs-3" align="left">
                    <label for="messageText" class="control-label">Message:</label>
                </div>
                <div class="col-xs-9" align="right">
                    <textarea id="messageText" style="resize: none" required class="form-control" rows="3"></textarea>
                </div>
            </div>
        </div>
        <br>
        <div class="row">
            <div class="col-xs-12" align="center">
                <input align="center" type="button" class="btn btn-primary" value="Send" onclick="send()">
            </div>
        </div>
    </form>
</div>
<div id="overlay"></div><!-- Пoдлoжкa -->
