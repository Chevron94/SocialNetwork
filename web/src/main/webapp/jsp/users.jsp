<%@include file="templates/userForm.jsp"%>
<script type="text/javascript">
    var otherStart = 0;
</script>
<div class="col-xs-8">
    <div class="tab-content">
        <div id="otherUsersTab" style="max-height: 500px; overflow-y: scroll">
            <h4 align="center">Other users</h4>
            <div id="otherUsers">

            </div>
            <div align="center">
                <button id="loadMoreOther" onclick="loadMore(otherStart,idUser,'other')">Load more</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
