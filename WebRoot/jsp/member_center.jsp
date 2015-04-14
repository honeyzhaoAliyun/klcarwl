<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
	<%@ include file="/common/meta_dev.jsp"%>
 </head>
  
  <body>
    <div id="wrapbox" > 
  <!--顶部-->
  <div class="top"> <span
   class="bt1"><span class="bt11"><a href="<%=basePath %>index.do"><img src="${ctxStatic}/images/img_jt.png" /></a></span><span class="bt22"><a href="<%=basePath %>index.do">回首页</a></span></span><span class="btr">个人中心</span><span class="bt2"><a href="<%=basePath %>loginOut.do">
    <button type="button" class="btn btn-warning">退出</button>
    </a></span> </div>
 <div id="focusr">
 <h2>欢迎来到快乐物流</h2>
 <h2><div style="text-align: right;float:left;">您好,<c:if test="${!(sessionInfo.userName) }">[ ${sessionInfo.userName} ]</c:if> 欢迎您！</div></h2>
     <!--  <div class="gr"><button type="submit" class="btnr btn-warning"> 登陆</button></div> -->
 </div>
  <div class="contentr">
  <div class="personal_xq">    
      <form class="form-horizontalr" role="form"> 
        <div class="form-group form-group-lg">
          <label class="col-sm-10 control-label" for="formGroupInputLarge"><a href="${basePath }updateComInfo.do"><span>基本资料</span></a></label><span class="bt_p"><a href="${basePath }updateComInfo.do"><img src="${ctxStatic}/images/img_pt.png" /></a></span>
        </div>
        <%-- <div class="form-group form-group-sm">
          <label class="col-sm-10 control-label" for="formGroupInputSmall"><a href="${basePath }myAccount.do"><span>我的账户</span></a></label><span class="bt_p"><a href=""><img src="${ctxStatic}/images/img_pt.png" /></a></span>
        </div> --%>
        <div class="form-group form-group-lg">
          <label class="col-sm-10 control-label" for="formGroupInputLarge"><a href="${basePath }myGoods.do"><span>我的货源</span></a></label><span class="bt_p"><a href=""><img src="${ctxStatic}/images/img_pt.png" /></a></span>
        </div>
        <div class="form-group form-group-sm">
          <label class="col-sm-10 control-label" for="formGroupInputSmall"><a href="${basePath }updatePwd.do"><span>修改密码</span></a></label><span class="bt_p"><a href="${basePath }updatePwd.do"><img src="${ctxStatic}/images/img_pt.png" /></a></span>
        </div>
      </form>
    </div>
  </div>
</div>
  </body>
</html>
