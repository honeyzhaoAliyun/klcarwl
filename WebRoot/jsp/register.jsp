<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
	<%@ include file="/common/meta_dev.jsp"%>
<script type="text/javascript" charset="utf-8">
var input_form;
var input_qy_form;
$(function(){
	//刷新验证码
	refreshCheckCode();
	//=====form相关变量---begin===============
	//---个人
	$mobilephone = $("input[name='mobilephone']");
    $password = $("input[name='password']");
    $confirmpassword = $("input[name='confirmpassword']");
    $validateCode = $("input[name='validateCode']");
    input_form = $("#input_form").form();
    
    
    //=======发送手机验证码==========
    $("#send_after").hide();
    /*验证码验证  */
	$("#send_msg").click(function(){
		$("#send_after").html("180s");
		var _sendTo=$mobilephone.val();
		if(_sendTo ==""){
			msg('请填写手机号!');
			return false;
		}
		$.ajax({
			url: "${ctx}/_sendRegisterValidateWithAjax.do",
			data: {"type":2,"sendTo":_sendTo,"code":"","ids":""},
			type: "POST",
			dataType: "json",
			async: false,
			success: function(data) {
				if (data.status=="success") {
					$("#send_msg").hide();
					$("#send_after").show();
					dijian();
				} else {
					msg(data.msg);
				}
			}
		});
	});	
	//=====form相关变量---end=================
	/* <!--个人注册  --> */
	$("#ipt_saveOrUpdate").click(function(){
		if(input_form.form('validate')){
			 $.ajax({
					url:"${ctx}/_save.do", 
					type:"post", 
					async:false,
					dataType:'json',
					data: input_form.serialize(),
					success: function(data) {
						if (data.status=="success") {
							msg("用户注册成功！");
							window.location.href=data.data;//操作结果提示;
						} else {
							msg(data.msg);
							refreshCheckCode();
							$validateCode.val("");
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
	});
	
});

//刷新验证码
function refreshCheckCode() {
    //加上随机时间 防止IE浏览器不请求数据
    var url = "${ctx}/servlet/ValidateCodeServlet?"+ new Date().getTime();
    $("#validateCode_img").attr('src',url);
}
function dijian(){
	var i = 179;
	var t = setInterval(function(){
		if (i<=0) {
			$("#send_msg").show();
			$("#send_after").hide();
			clearInterval(t);
			return;
		}
		$("#send_after").html(i+"s");
		i--;
	}, 1000);
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
</head>
  <body>
    <div id="wrapbox" > 
  <!--顶部-->
  <div class="top"> <span
   class="bt1"><span class="bt11"><a href="<%=basePath %>index.do"><img src="${ctxStatic}/images/img_jt.png" /></a></span><span class="bt22"><a href="<%=basePath %>index.do">回首页</a></span></span><span class="btr">注册</span><span class="bt2"><a href="<%=basePath %>loginpage.do">
    <button type="button" class="btn btn-warning">登录</button>
    </a></span> </div>
  <div class="content">
   <%@ include file="/common/validate.jsp"%>
  <form id="input_form" method="post" novalidate>
    <div class="login">
      <div class="login2">
       <div class="form-group">
          <label for="inputEmail3" class="col-sm-2 control-label">手机号</label>
          <div class="col-sm-10">
            <input type="text" placeholder="手机号" required="true" data-options="validType:'minLength[1]',missingMessage:''" class="easyui-validatebox form-control" name="mobilephone"></input>
          </div>
          <label for="inputEmail3" class="col-sm-2 control-label">密码</label>
          <div class="col-sm-10">
            <input type="password" placeholder="密码" required="true" data-options="validType:'minLength[1]',missingMessage:''" class="easyui-validatebox form-control" name="password"></input>
          </div>
          <label for="inputEmail3" class="col-sm-2 control-label">确认密码</label>
          <div class="col-sm-10">
            <input type="password" placeholder="确认密码" required="true" data-options="validType:'minLength[1]',missingMessage:''"  class="easyui-validatebox form-control" name="confirmpassword"></input>
          </div>
          <label for="inputEmail3" class="col-sm-2 control-label">验证码</label>
          <div class="col-sm-10">
          	 <!-- <input class="iptc" id="validateCode" name="validateCode" type="text" onkeydown="if(event.keyCode==13)login()" class="easyui-validatebox  form-control" style="width: 100px" required="true" data-options="tipPosition:'left',validType:'alphanum',missingMessage:'请输入验证码!'" />
             <img id="validateCode_img" align="middle" onclick="refreshCheckCode();" />
             <a href="javascript:void(0)" onclick="refreshCheckCode();" ><span style="font-size:11px;">看不清,换一个</span></a> -->
             <input class="iptc" type="text" name="validateCode" required="true" data-options="validType:'minLength[1]',missingMessage:''" class="easyui-validatebox form-control" style="width: 87px;"   placeholder="验证码"></input>
             <span class="yzm" id="send_msg"> 免费获取手机验证码 </span>
             <span class="yzm" id="send_after">180s</span>
          </div>
       </div>
      </div>
      <div class="btn_dl">
        <div class="form-group">
          <input type="submit" id="ipt_saveOrUpdate" class="btn btn-warning" value="立即注册" ></input>
        </div>
      </div>
    </div>
    </form>
  </div>
</div>
  </body>
</html>
