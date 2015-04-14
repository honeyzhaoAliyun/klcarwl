<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
	<%@ include file="/common/meta_dev.jsp"%>
 </head>
  
<body>
<script>
function getOneCity(str_name){
	var resource="${requestScope.resource}";
	var publishType="${requestScope.publishType}";
	if(publishType ==1){
		if(resource ==1){
			window.location.href="<%=basePath %>goods/publishGood.do?fromcity_publish="+str_name+"";
		}else{
			window.location.href="<%=basePath %>goods/publishGood.do?tocity_publish="+str_name+"";
		}
	}else{
		if(resource ==1){
			window.location.href="<%=basePath %>goods/searchGoods.do?fromcity="+str_name+"";
		}else{
			window.location.href="<%=basePath %>goods/searchGoods.do?tocity="+str_name+"";
		}
	
	}
}
function getCityChildren(str_id){
	var resource="${requestScope.resource}";
	var publishType="${requestScope.publishType}";
	window.location.href="<%=basePath %>sysLocaleList.do?type=2&id="+str_id+"&publishType="+publishType+"&resource="+resource+"";
}

function getInitList(){
	window.location.href="<%=basePath %>sysLocaleList.do?type=1";
}
function searchSysLocale(){
	var cityname = document.getElementsByName("cityName");
}

</script>
<style>
.bt2 {
    border: 0 none;
    clear: none;
    display: block;
    float: right;
    margin: 0px 10px 5px auto;
}

</style>
	  <!--顶部-->
	  <div class="top"> 
	  		<span  class="bt1"><span class="bt11"><a href="<%=basePath %>index.do"><img src="${ctxStatic}/images/img_jt.png" /></a></span><span class="bt22"><a href="<%=basePath %>index.do">回首页</a></span></span><span class="btr">选择城市</span>
	  </div>
	  <!--顶部结束-->
  
	  <div class="content"><%@ include file="/common/validate.jsp"%>
	 <%--  <div class="city_xz">
      	 <select class="easyui-combobox form-control" style="height:40px;width:100%;" name="cityName" >
        	<option value="" ></option>
        	<c:forEach  items="${sysLocaleList}" var="sysLocale" varStatus="_index">
	        	<option value="${sysLocale.id }" >${sysLocale.name }</option>
        	</c:forEach>
        </select>
        <span class="bt2" onclick="searchSysLocale()"><button type="button" class="btn btn-warning" >查询</button></span>
      </div> --%>
      <div class="add_xq">
	      <form class="form-horizontalr" id="cityForm" enctype="multipart/form-data" > 
	        <div class="form-group form-group-sm">
	          <label class="col-sm-10 control-label" for="formGroupInputLarge" onclick="getInitList()" ><h1 ><b>热门城市</b></h1></label>
	        </div>
			<c:forEach  items="${sysLocaleList}" var="sysLocale" varStatus="_index">
				<div class="form-group form-group-lg">
	         		<label class="col-sm-10 control-label" for="formGroupInputLarge" onclick="getOneCity('${sysLocale.name }')" ><span>${sysLocale.name }</span></label>
	         		<c:if test="${sysLocale.type ==1 }">
	         			<label for="formGroupInputLarge" style="width:15%;float: right;" class="col-sm-10 control-label" onclick="getCityChildren('${sysLocale.id }')"><span style="text-align: center;">&gt;</span></label>
	         		</c:if>
	       		</div>
			</c:forEach>
	      </form>
      </div>
   </div>
</body>
</html>
