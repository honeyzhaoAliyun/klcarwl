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
   class="bt1"><span class="bt11"><a href="<%=basePath %>index.do"><img src="${ctxStatic}/images/img_jt.png" /></a></span><span class="bt22"><a href="<%=basePath %>index.do">回首页</a></span></span><span class="btr">注册</span><span class="bt2"><a href="#">
    <button type="button" class="btn btn-warning">登录</button>
    </a></span> </div>
  <div class="content"><%@ include file="/common/validate.jsp"%>
  <div class="tishi">验证短信已发往15901291966，请稍等！</div>

    <div class="login">
      <form class="form-horizontal" role="form">
        <div class="loginbox">
          <div class="form-group">
            <label for="inputEmail3" class="col-sm-2 control-label">验证码</label>
            <div class="col-sm-5">
              <input type="email" class="form-controlr" id="inputEmail3" placeholder=" 验证短信中的数字">
            </div> <div class="col-sm-3"><button type="button" class="btn_rest btn-danger">160s以后重新获取</button></div>
          </div>
          <div class="form-group">
            <label for="inputPassword3" class="col-sm-2 control-label">密码</label>
            <div class="col-sm-10">
              <input type="password" class="form-control" id="inputPassword3" placeholder="6-16个字符">
            </div>
          </div>
        </div>
        <div class="login_box">
        
          <div class="btn_dl">
          <div class="form-group">
            <button type="submit" class="btn btn-warning">注册</button>
          </div></div>
        </div>
      </form>
    </div>
  </div>
</div>
  </body>
</html>
