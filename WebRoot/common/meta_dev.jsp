<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.klcarwl.common.utils.browser.BrowserType" %>
<%@ page import="com.klcarwl.common.utils.browser.BrowserUtils" %>
<%
    String ctx = request.getContextPath();
    BrowserType browserType = BrowserUtils.getBrowserType(request);
%>
<script type="text/javascript" charset="utf-8">
    var ctx = "${ctx}";
    var ctxStatic = "${ctxStatic}";
</script>
<title>快乐物流</title>
<c:set var="ev" value="1.3.6" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="author" content="honey.zhao@aliyun.com" />
<meta name="apple-touch-fullscreen" content="yes" />
<meta content="initial-scale=1.0,user-scalable=no,maximum-scale=1,width=device-width" name="viewport" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<link rel="shortcut icon" href="${ctxStatic}/img/favicon.ico" />

<script type="text/javascript" src="${ctxStatic}/js/jquery/parent/jquery-1.11.2.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctxStatic}/js/jquery/parent/jquery-migrate-1.2.1.min.js" charset="utf-8"></script>
<link href="${ctxStatic}/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${ctxStatic}/css/klwl.css" rel="stylesheet" type="text/css" />
<!-- jQuery Cookie插件 -->
<script type="text/javascript" src="${ctxStatic}/js/jquery/parent/jquery.cookie-min.js" charset="utf-8"></script>

<!--easyUI----begin  -->
<link id="easyuiTheme" rel="stylesheet" type="text/css" href="${ctxStatic}/js/jquery/easyui-${ev}/themes/<c:out value="${cookie.easyuiThemeName.value}" default="bootstrap"/>/easyui.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/js/jquery/easyui-${ev}/themes/default/my97.css" />

<link rel="stylesheet" type="text/css" href="${ctxStatic}/js/jquery/easyui-${ev}/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/js/jquery/easyui-${ev}/portal/portal.css">
<script type="text/javascript" src="${ctxStatic}/js/My97DatePicker/WdatePicker.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctxStatic}/js/jquery/easyui-${ev}/jquery.easyui.mine.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctxStatic}/js/jquery/easyui-${ev}/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctxStatic}/js/jquery/easyui-${ev}/jquery.easyui.my97-min.js" charset="utf-8"></script>

<script type="text/javascript" src="${ctxStatic}/js/jquery/easyui-${ev}/portal/jquery.portal.js" charset="utf-8"></script>

<!--easyUI----end  -->

<!-- easyui自定义表单校验扩展 -->
<script type="text/javascript" src="${ctxStatic}/js/validatebox-extend-min.js" charset="utf-8"></script>
<!-- easyui后台异步校验 -->
<script type="text/javascript" src="${ctxStatic}/js/validatebox-ajax-min.js" charset="utf-8"></script>

<!-- 公用消息提示 -->
<script type="text/javascript" src="${ctxStatic}/js/common.js" charset="utf-8"></script>

<!-- 微信公用jsSDK  在需要调用JS接口的页面引入如下JS文件，（支持https）-->
<script type="text/javascript" src="${ctxStatic}/js/wechatShare/jquery.sha1.js" charset="utf-8"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctxStatic}/js/wechatShare/jquery.wechat.share.js" charset="utf-8"></script>


<style>
/*input 验证css  */
.validatebox-invalid {
    background-color: #fff3f3;
    background-image: url("");
    background-position: right center;
    background-repeat: no-repeat;
    border-color: #FFF;
    color: #000;
}
/*重置货源列表 button 样式  */
.btn {
    -moz-user-select: none;
    background-image: none;
    border: 1px solid transparent;
    border-radius: 4px;
    cursor: pointer;
    display: inline-block;
    font-size: 18px;
    font-weight: 400;
    line-height: 1;
    margin-bottom: 0;
    padding: 6px 12px;
    text-align: center;
    vertical-align: middle;
    white-space: nowrap;
    width: 100%;
}

.btnlist {
    -moz-user-select: none;
    background-image: none;
    border: 1px solid transparent;
    border-radius: 4px;
    cursor: pointer;
    display: inline-block;
    font-size: 12px;
    font-weight: 400;
    line-height: 1;
    margin-bottom: 0;
    padding: 6px 12px;
    text-align: center;
    vertical-align: middle;
    white-space: nowrap;
    width: 100%;
}
</style>