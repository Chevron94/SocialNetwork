<%@include file="templates/userForm.jsp"%>
<script type="text/javascript">
    var otherStart = 0;
</script>
<div class="col-xs-8">
    <div class="tab-content">
        <div id="otherUsersTab" style="max-height: 500px; overflow-y: scroll">
            <div id="otherUsers">

            </div>
            <div align="center">
                <button id="loadMoreOther" class="btn btn-info" style="display: none" onclick="loadMore(otherStart,idUser,'other')">Load more</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
