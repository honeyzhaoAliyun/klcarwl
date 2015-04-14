<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
	<%@ include file="/common/meta_dev.jsp"%>
 </head>
<body>
<div id="wrapbox" > 
	  <!--顶部-->
	  <div class="top"> 
		  <span class="bt1"></span>
		  <span class="bt">快乐物流</span>
		  <c:if test="${sessionScope.sessionInfo==null }">
			  <span class="bt2">
				  <a href="<%=basePath %>loginpage.do">
				  	<button type="button" class="btn btn-warning">登录</button>
				  </a>
			  </span>
		  </c:if>
	  </div>
	  <!--顶部结束--> 
	  <!--焦点图-->
	  <div id="focus">
	    <ul>
	      <li> <a href="${ctx}/goods/searchGoods.do" target="_self"><img src="${ctxStatic}/images/img_00.png"/></a> </li>
	    </ul>
	  </div>
	  <!--焦点图结束--> 
	<div class="box">
	  <div class="box_left">
	    <ul>
	   		<li><a href="${ctx}/goods/searchGoods.do" target="_self"><img src="${ctxStatic}/images/img_hy.png" /></a></li>
	   		<li><a href="${ctx}/member/memberCenter.do" target="_self"><img src="${ctxStatic}/images/img_zx.png"  /> </a></li>
	    </ul>
	  </div>
	  <div class="box_right">
	    <ul>
	      <li><a href="${ctx}/weizhang.do" target="_self"><img src="${ctxStatic}/images/img_wz.png"/></a></li>
	      <li ><a href="${ctx}/member/publishGood.do" target="_self"><img src="${ctxStatic}/images/img_cy.png" /></a></li>
	    </ul>
	  </div>
	</div> 
	<div id="footer">
	  <ul> 
	  	<li><a href="${ctx}/member/memberCenter.do" target="_self" >会员中心</a></li>
	    <li ><a href="${ctx}/aboutUs.do" target="_self">关于我们</a></li>
	    <li ><a href="${ctx}/contractUs.do" target="_self">联系我们</a></li>
	  </ul>
	</div> 
</div>
</body>
</html>
