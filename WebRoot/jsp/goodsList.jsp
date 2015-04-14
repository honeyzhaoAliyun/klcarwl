<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
	<%@ include file="/common/meta_dev.jsp"%>
 </head>
  
  <body>
    <div id="wrapbox" > 
    <div class="top"> 
    	<span class="bt1"><span class="bt11"><a href="<%=basePath %>index.do"><img src="${ctxStatic}/images/img_jt.png" /></a></span><span class="bt22"><a href="<%=basePath %>index.do">回首页</a></span></span><span class="btr">货源</span><span class="bt3"><a href="${ctx}/member/publishGood.do">
    	<button type="button" class="btn btn-warning">发货</button>
    	</a></span> </div>
    	<!--循环开始------begin  -->
    	<c:forEach  items="${msgContentVoList}" var="msgContentVo" varStatus="_index">
		    <div class="content_fh2">
		      <div class="content_fh_dz">
		        <div class="address">
		          <h1>${msgContentVo.FromName }</h1>
		          <span class="img_fx"><img src="${ctxStatic}/images/img_fx.png" /></span>
		          <h1>${msgContentVo.ToName }</h1>
		        </div>
		        <div class="date">${msgContentVo.PUBLICATION_DATE }</div>
		      </div>
		      <div class="fh_xq">
		        <div class="fh_xq_left">
		        	<!-- 信息--类型  重量--begin -->
		        	<div class="img_xq" >
		        		<span class="cc">
				        	<button type="button" class="btnlist btn-success" style="width: 120px;" >货物类型:${msgContentVo.KEYWORDS==null?"配货":msgContentVo.KEYWORDS }</button>
				        </span>
			        	<span class="cz">
			        		<button type="button" class="btnlist btn-primary" style="width: 90px;" >重量:${msgContentVo.WEIGHT==null?0:msgContentVo.WEIGHT }吨</button>
			        	</span>
			        	<br/>
		        		<%-- <div class="dzxw"> <span class="dz"><img src="${ctxStatic}/images/img_dz.png" />【点赞】</span><span class="jb"><img src="${ctxStatic}/images/img_jb.png"/> 【举报】</span></div> --%>
			       </div>
		        	<!-- 信息--类型  重量--end -->
		        	${msgContentVo.CONTENT }
		        	<c:if test="${msgContentVo.mobile!=null }">
		        		<br/>联系方式：${msgContentVo.mobile}
		        	</c:if>
		        	
		        </div>
		        <div class="fh_xq_right"><a href="tel:${msgContentVo.MOBILE}" ><img src="${ctxStatic}/images/img_dh.png"  /></a><span style="font-size:14px;text-align: center;">拨打电话</span></div>
		      </div>
		    </div>
   		</c:forEach>
   		<c:if test="${msgContentVoList.size()==0}">
   			<div style="text-align: center;padding-top: 100px;">
   			<img src="${ctxStatic}/images/nolist.png" width="50px;" height="49px;" ></img>
   			<br/><br/>
			<span style="font-family: 'Microsoft YaHei' ! important; ">亲，该线路目前没有货源~请选择相邻的地区试试哦~</span>
   			</div>
   		</c:if>
    
   <!--循环开始------end  -->
  </div>
  </body>
</html>
