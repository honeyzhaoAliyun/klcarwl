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
   <span class="bt1"><span class="bt11"><a href="<%=basePath %>index.do"><img src="${ctxStatic}/images/img_jt.png" /></a></span><span class="bt22"><a href="<%=basePath %>index.do">回首页</a></span></span><span class="btr">货源详情</span></div>
  <!--顶部结束-->
  <div class="content"><%@ include file="/common/validate.jsp"%>
    <div class="content_fbhy_xq">
      <div class="address1">
        <h1>北京</h1>
        <span class="img_fx1"><img src="${ctxStatic}/images/img_xq.png" /></span>
        <h1 style="text-align:left;">上海</h1>
      </div>
      <div class="date1">2014-12-22 15:30</div>
      <div class="xq">
        <form class="form-horizontalr" role="form">
          <div class="form-group form-group-lg">
            <label class="col-sm-9 control-label" for="formGroupInputLarge">出发地:&nbsp;&nbsp;&nbsp; 北京</label>
          </div>
          <div class="form-group form-group-sm">
            <label class="col-sm-9 control-label" for="formGroupInputSmall">到达地:&nbsp;&nbsp;&nbsp; 上海</label>
          </div>
          <div class="form-group form-group-lg">
            <label class="col-sm-9 control-label" for="formGroupInputLarge">货物类型:&nbsp;&nbsp;&nbsp;设备</label>
          </div>
          <div class="form-group form-group-sm">
            <label class="col-sm-9 control-label" for="formGroupInputSmall">重量(吨):&nbsp;&nbsp;&nbsp;180 </label>
          </div>
          <div class="form-group form-group-lg">
            <label class="col-sm-9 control-label" for="formGroupInputLarge">联系人:&nbsp;&nbsp;&nbsp;李四</label>
          </div>
          <div class="form-group form-group-lg">
            <label class="col-sm-9 control-label" for="formGroupInputLarge">联系电话:&nbsp;&nbsp;&nbsp;13789652314</label>
          </div>
          <div class="form-group form-group-sm">
            <label class="col-sm-10 control-label" for="formGroupInputSmall">说明：&nbsp;&nbsp;&nbsp;这批货物非常着急运输，看到信息的可以立刻联系</label>
          </div>
        </form>
      </div>
    </div>
    <div class="sctp"><img src="${ctxStatic}/images/hyzp.png" alt="上传货物图片" class="img-thumbnail"></div>
    <div class="btn_dlr">
      <div class="form-groupr">
        <button type="submit" class="btnr btn-warning"> 电话咨询</button>
      </div>
    </div>
  </div>
</div>
  </body>
</html>
