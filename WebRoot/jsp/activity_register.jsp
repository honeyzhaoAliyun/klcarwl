<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
	<%@ include file="/common/meta_dev_actity.jsp"%>
	<link href="${ctxStatic}/activity/css/style.css" rel="stylesheet" type="text/css" />
	
<script type="text/javascript" charset="utf-8">
var input_form;
$(function(){
	//刷新验证码
	refreshCheckCode();
	//=====form相关变量---begin===============
	//---个人
	$mobilephone = $("input[name='mobilephone']");
    //$password = $("input[name='password']");
    //$confirmpassword = $("input[name='confirmpassword']");
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
					url:"${ctx}/_saveActivity.do", 
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
	<%@ include file="/common/validate_activity.jsp"%>
	<div class="templatesbox">
		<form class="form-horizontal" id="input_form" method="post" enctype="multipart/form-data" novalidate>
			<input type="hidden" id="sumCost" name="sumCost" value="${sumCost}"></input>
			<div class="warp">
				<div class="bgimg">
					<img src="${ctxStatic}/activity/images/xbg.png" width="100%" /><img src="${ctxStatic}/activity/images/bgg.png"
						width="100%" />
				</div>
				<div class="top">
					<div class="logo_car">
						<img src="${ctxStatic}/activity/images/login1.png" />
					</div>
					<div class="tishiActivity">
						<div class="tishiActivity2">成功为好友抢到${sumCost}元话费</div>
						验证手机号，将${sumCost}元话费送到好友账号，<br /> 同时为自己充值${sumCost}元话费（同一个号码只能送一次）
					</div>
				</div>
				<div class="login">
					  <div class="user">
							<input type="text" placeholder="手机号" required="true" data-options="validType:'minLength[1]',missingMessage:''" class="easyui-validatebox form-control" name="mobilephone"></input>
					  </div>
					  <div class="password">
					       <input type="text" name="validateCode" required="true" data-options="validType:'minLength[1]',missingMessage:''" class="easyui-validatebox form-control iptc"  placeholder="输入验证码">
					       <div class="user_en">
					           <span class="yzm" id="send_msg"><input type="button" value="获取验证码" ></span>
			             	   <span class="yzm" id="send_after">180s</span>
					       </div>
					  </div>
				</div>
				<div class="xbtn">
					 <div class="btn_dl">
				        <div class="form-group">
				          <input type="submit" id="ipt_saveOrUpdate" class="btn btn-warning" value="立刻充值" ></input>
				        </div>
				      </div>
				</div>
			</div>
		</form>
		<div class="blank3"></div>
		<div class="footer">
			<img src="${ctxStatic}/activity/images/login_ewm.png" />
			<p>长按二维码识别图中二维码关注领话费</p>
		</div>
	</div>
</body>
</html>
