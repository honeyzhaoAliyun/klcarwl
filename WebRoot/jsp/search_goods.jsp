<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
	<%@ include file="/common/meta_dev.jsp"%>
 </head>
  
  <body>
<script type="text/javascript">
function searchfromcity(){
	window.location.href="<%=basePath %>sysLocaleList.do?type=1&resource=1&publishType=0";
}
function searchtocity(){
	window.location.href="<%=basePath %>sysLocaleList.do?type=1&resource=2&publishType=0";
} 

$(function(){
	var searchForm = $("#searchForm").form();
	
	 $("#searchsubmit").click(function(){
		 if(searchForm.form('validate')){
		 	submitForm();
		 }
	});
});
function submitForm(){
	document.searchForm.submit();
}

</script>

<div id="wrapbox" > 
  <!--顶部-->
  <div class="top"> 
  	<span class="bt1"><span class="bt11"><a href="<%=basePath %>index.do"><img src="${ctxStatic}/images/img_jt.png" /></a></span><span class="bt22"><a href="<%=basePath %>index.do">回首页</a></span></span><span class="btr">找货</span><span class="bt2">
    	<a href="${ctx}/member/publishGood.do"><button type="button" class="btn btn-warning">发货</button></a>
    </span>
  </div>
 
  <div class="content"><%@ include file="/common/validate.jsp"%>
    <div class="login">
      <form class="form-horizontal" id="searchForm" action="${ctx}/goods/listFromStation1.do" name="searchForm" method="post" enctype="multipart/form-data" novalidate>
        <div class="loginbox">
          <div class="form-group">
            <label for="inputPassword3" class="col-sm-2 control-label">出发城市</label>
            <div class="col-sm-10" onclick="searchfromcity()">
              <input class="easyui-validatebox form-control" placeholder="出发地" type="text"
			               id="fromcity" name="fromcity" value="${sessionScope.fromcity==null?'':sessionScope.fromcity}"  />
            </div>
          </div>
          <div class="form-group">
            <label for="inputPassword3" class="col-sm-2 control-label">到达城市</label>
            <div class="col-sm-10" onclick="searchtocity()">
              <input class="easyui-validatebox form-control" placeholder="到达地" type="text"
			               id="tocity" name="tocity" value="${sessionScope.tocity==null?'':sessionScope.tocity}"  />
            </div>
          </div>
        </div>
        <div class="login_box">
        
          <div class="btn_dl">
          <div class="form-group">
            <button type="button" class="btn btn-warning" id="searchsubmit">搜索</button>
          </div></div>
        </div>
      </form>
    </div>
  </div>
</div>
  </body>
</html>
