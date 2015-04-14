<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
	<%@ include file="/common/meta_dev.jsp"%>
 </head>

<body>
<div>
    <div><h1>你没有访问该页面的权限.</h1></div>
    <div><a href="<%=basePath %>index.do">返回首页</a> <a href="<%=basePath %>logout.do">退出登录</a></div>
</div>
</body>
</html>