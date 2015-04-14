<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
	<%@ include file="/common/meta_dev.jsp"%>

</head>
<body>
    <script type="text/javascript">
        var loginForm;
        var login_linkbutton;
        var $loginName,$password,$rememberPassword,$autoLogin;
        $(function(){
            $loginName = $("#loginName");
            $password = $("#password");
            $rememberPassword = $("#rememberPassword");
            $autoLogin = $("#autoLogin");
            loginForm = $("#loginForm").form();

            $rememberPassword.prop("checked", "${cookie.rememberPassword.value}" == "" ? false : true);
            $autoLogin.prop("checked", "${cookie.autoLogin.value}" == "" ? false : true);

            $loginName.val("${cookie.loginName.value}");
            if("${cookie.rememberPassword.value}" != ""){
                $password.val("${cookie.password.value}");
            }
            loginForm.form("validate");


            if("${cookie.autoLogin.value}" != ""){
                login();
            }else{
                $loginName.focus();
            }

            $rememberPassword.click(function(){
                var checked = $(this).prop('checked');
                if(checked){
                    $.cookie('password', $password.val(), {
                        expires : 7
                    });
                    $.cookie('rememberPassword', checked, {
                        expires : 7
                    });
                }else{
                    $.cookie('password', "", {
                        expires : 7
                    });
                    $.cookie('rememberPassword', "", {
                        expires : 7
                    });
                }
            });
            $autoLogin.click(function(){
                var checked = $(this).prop('checked');
                if(checked){
                    $.cookie('autoLogin', checked, {
                        expires : 7
                    });
                    $rememberPassword.prop('checked',checked);
                    $.cookie('rememberPassword', checked, {
                        expires : 7
                    });
                }else{
                    $.cookie('autoLogin', "", {
                        expires : 7
                    });
                }
            });
        });
        // 登录
        function login() {
            $.cookie('loginName', $loginName.val(), {
                expires : 7
            });
            if($rememberPassword.prop("checked")){
                $.cookie('password', $password.val(), {
                    expires : 7
                });
            }
            if(loginForm.form('validate')){
                $.ajax({
					url:"${ctx}/login.do", 
					type:"post", 
					async:false,
					dataType:'json',
					data: loginForm.serialize(),
					success: function(data) {
						if (data.status=="success") {
							window.location.href=data.data;//操作结果提示;
						} else {
							msg(data.msg);
							$("#password").val("");
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) { 
						msg("ERROR. " + XMLHttpRequest.status); 
						msg("ERROR. " + XMLHttpRequest.readyState); 
						msg("ERROR. " + textStatus); 
						msg("ERROR. " + errorThrown); 
					}
				});
            }
        }
    </script>
<style>
.col-sm-2 {
    margin-top: 5px;
}
.col-sm-10 {
    margin-top: 5px;
    width: 100%;
}
</style>
<div id="wrapbox" > 
  <!--顶部-->
  <div class="top"> <span
   class="bt1"><span class="bt11"><a href="<%=basePath %>index.do"><img src="${ctxStatic}/images/img_jt.png" /></a></span><span class="bt22"><a href="<%=basePath %>index.do">回首页</a></span></span><span class="btr">登录</span><span class="bt2"><a href="<%=basePath %>register.do">
    <button type="button" class="btn btn-warning">注册</button>
    </a></span> </div>
 
  <div class="content"><%@ include file="/common/validate.jsp"%>
    <div class="login">
      <form id="loginForm" method="post" enctype="multipart/form-data" novalidate>
      	<input name="openid" value="${wechatUser.openid}" type="hidden"></input>
        <div class="loginbox_login">
          <div class="form-group">
            <label for="inputPassword3" class="col-sm-2 control-label">微信帐号</label>
            <div class="col-sm-10">
              <span><img src="${wechatUser.headimgurl==null?'':wechatUser.headimgurl}" width="20px" height="20px" />${wechatUser.nickname ==null?'':wechatUser.nickname}</span>
            </div>
          </div>
          <div class="form-group">
            <label for="inputPassword3" class="col-sm-2 control-label">手机号</label>
            <div class="col-sm-10">
              <input class="easyui-validatebox form-control" placeholder="手机..." type="text"
		               id="loginName" class="loginName" name="loginName" required="true"
		               data-options="validType:'minLength[1]',missingMessage:''"  />
            </div>
          </div>
          <div class="form-group">
            <label for="inputPassword3" class="col-sm-2 control-label">密码</label>
            <div class="col-sm-10">
              <input type="password" id="password" name="password" placeholder="密码..."  class="easyui-validatebox form-control"
		               required="true" onkeydown="if(event.keyCode==13)login()"
		               data-options="validType:'minLength[1]',missingMessage:''" />
            </div>
          </div>
        </div>
        <div class="login_box">
        <div class="btn_dl">
          <div class="form-group">
            <div class="checkbox">
              <input id="rememberPassword" type="checkbox"/> 
              <label for="rememberPassword">记住密码</label>
              <label> <span class="forgetLink"><a href="<%=basePath %>_forgetpasswordinput.do">忘记密码</a></span></label>
            </div>
          </div></div>
          <div class="btn_dl">
          <div class="form-group">
            <!-- <a  class="btn btn-warning easyui-linkbutton"  >登录</a> -->
            <button id="login_linkbutton" type="submit" class="btn btn-warning" onclick="login()">登录</button>
          </div></div>
        </div>
      </form>
    </div>
  </div>
</div>
</body>
</html>
