<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/common/meta_dev.jsp"%>
</head>

<body>
	<div id="wrapbox">
		<!--顶部-->
		<div class="top">
			<span class="bt1"><span class="bt11"><a href="<%=basePath %>index.do"><img src="${ctxStatic}/images/img_jt.png" /></a></span><span
				class="bt22"><a href="<%=basePath %>index.do">回首页</a></span></span><span class="btr">修改密码</span>
			<!--<span class="bt2"><a href="register.html">
    <button type="button" class="btn btn-warning">注册</button>
    </a></span>-->
		</div>

		<div class="content"><%@ include file="/common/validate.jsp"%>
			<div class="login">
				<form class="form-horizontal" role="form" method="post" action="${basePath }updatePwd.do">
					<div class="loginboxr">

						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">旧密码</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" id="inputPassword3" name="oldPwd"
									placeholder="请输入旧密码">
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">新密码</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" id="inputPassword3" name="newPwd"
									placeholder="请输新入密码">
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">确认新密码</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" id="inputPassword3" name="newPwd2"
									placeholder="密码长度至少6个字符，最多16个字符。">
							</div>
						</div>
						<c:if test="${!empty errorMsg }">
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label"></label>
							<div class="col-sm-10" style="color: red">
								${errorMsg }
							</div>							
						</div>
						</c:if>						
					</div>
					<div class="login_box">
						<div class="btn_dl">
							<div class="form-group">
								<button type="submit" class="btn btn-warning">确定</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
