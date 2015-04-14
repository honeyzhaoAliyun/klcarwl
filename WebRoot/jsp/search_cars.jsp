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
   class="bt1"><span class="bt11"><a href="<%=basePath %>index.do"><img src="${ctxStatic}/images/img_jt.png" /></a></span><span class="bt22"><a href="<%=basePath %>index.do">回首页</a></span></span><span class="btr">找车</span><span class="bt2"><a href="goods.html">
    <button type="button" class="btn btn-warning">发车</button>
    </a></span> </div>
 
  <div class="content"><%@ include file="/common/validate.jsp"%>
    <div class="login">
      <form class="form-horizontal" role="form">
        <div class="loginbox">
          <div class="form-group">
            <label for="inputPassword3" class="col-sm-2 control-label">出发城市</label>
            <div class="col-sm-10">
              <input type="password" class="form-control" id="inputPassword3" placeholder="">
            </div>
          </div>
          <div class="form-group">
            <label for="inputPassword3" class="col-sm-2 control-label">到达城市</label>
            <div class="col-sm-10">
              <input type="password" class="form-control" id="inputPassword3" placeholder="">
            </div>
          </div>
        </div>
        <div class="login_box">
        
          <div class="btn_dl">
         <div class="form-group">
            <button type="submit" class="btn btn-warning"> <a href="car.html">搜索</a></button>
          </div></div>
        </div>
      </form>
    </div>
  </div>
</div>

  </body>
</html>
