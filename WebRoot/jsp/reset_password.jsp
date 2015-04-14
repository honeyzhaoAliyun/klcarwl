<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
	<%@ include file="/common/meta_dev.jsp"%>
 </head>
  
  
  <body>
<script type="text/javascript" charset="utf-8">
var input_form;
$(function(){
	
	//=====form相关变量---begin===============
	$password = $("input[name='password']");
    $confirmpassword = $("input[name='confirmpassword']");
    input_form = $("#input_form").form();
	//=====form相关变量---end=================
	/* <!--重置密码  --> */
	$("#ipt_reset").click(function(){
		if(input_form.form('validate')){
			$.ajax({
			url: "${ctx}/_resetpassword.do",
			data: input_form.serialize(),
			type: "POST",
			dataType: "json",
			async: false,
			success: function(data) {
				if (data.status=="success") {
					msg(data.msg);
					window.location.href=data.data;//操作结果提示;
				}else{
					msg(data.msg);
				}
			}
		});
		}
	});
	
});

</script>
    <div id="wrapbox" > 
  <!--顶部-->
  <div class="top"> <span
   class="bt1"><span class="bt11"><a href="<%=basePath %>index.do"><img src="${ctxStatic}/images/img_jt.png" /></a></span><span class="bt22"><a href="<%=basePath %>index.do">回首页</a></span></span><span class="btr">忘记密码</span><span class="bt2"><a href="<%=basePath %>loginpage.do">
    <button type="button" class="btn btn-warning">登录</button>
    </a></span> </div>
  <div class="content"><%@ include file="/common/validate.jsp"%>
  <div class="tishi"><span style="color:#fc8914; font-size:medium; margin-left:10px;">${user.userName}</span>请重设您的密码（6-16字符）</div>

    <div class="login">
      <form class="form-horizontal" id="input_form" method="post" novalidate>
        <input name="ids" type="hidden" value="${user.id}"></input>
        <div class="loginbox">
          <div class="form-group">
            <label for="inputEmail3" class="col-sm-2 control-label">新密码</label>
            <div class="col-sm-10">
              <input type="password" placeholder="新密码" required="true" data-options="validType:'minLength[1]',missingMessage:''" class="easyui-validatebox form-control" name="password"></input>
            </div>
          </div>
          <div class="form-group">
            <label for="inputPassword3" class="col-sm-2 control-label">确认密码</label>
            <div class="col-sm-10">
              <input type="password" placeholder="确认密码" required="true" data-options="validType:'minLength[1]',missingMessage:''"  class="easyui-validatebox form-control" name="confirmpassword"></input>
            </div>
          </div>
        </div>
        <div class="login_box">
        
          <div class="btn_dl">
          <div class="form-group">
            <input type="submit" id="ipt_reset" class="btn btn-warning" value="提  交" ></input>
          </div></div>
        </div>
      </form>
    </div>
  </div>
</div>
  </body>
</html>
