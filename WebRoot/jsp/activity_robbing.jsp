<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
	<%@ include file="/common/meta_dev_actity.jsp"%>
	<link href="${ctxStatic}/activity/css/style.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<script type="text/javascript">
$(function(){
	setTimeout(function(){window.location="<%=basePath %>registerActivity.do?sumCost=${sumCost}";},500);
});

</script>
	<div class="templatesbox">
		<div class="warp">
			<div class="bgimg">
				<img src="${ctxStatic}/activity/images/dbg.png" width="100%" /><img src="${ctxStatic}/activity/images/bgg.png"
					width="100%" />
			</div>
			<div class="top">
				<div class="clock">
					<img src="${ctxStatic}/activity/images/clock.png" /><span class="clock_text">倒计时：00.0s</span>
				</div>
				<div class="blank"></div>
				<div class="logo">
					<a href=""><img src="${ctxStatic}/activity/images/car.png" />
					</a>
				</div>
				<div class="glod_c">
					<div class="one_glod">
						<img src="${ctxStatic}/activity/images/one_glod.png" />
					</div>
					<div class="number">
						<img src="${ctxStatic}/activity/images/k.png" />
						<h1>${sumCost}元</h1>
					</div>
				</div>
				<div class="glod">
					<img src="${ctxStatic}/activity/images/gold.png" />
				</div>
				<div class="btn">
					<a href="javascript:void(0);" onclick="gamerules()"><img src="${ctxStatic}/activity/images/btn_1.png" />
					</a><a href="${ctx}/winners.do" target="_self" ><img src="${ctxStatic}/activity/images/btn_2.png" />
					</a>
				</div>
			</div>
		</div>
		<div class="contentr">
			<img src="${ctxStatic}/activity/images/icon_m.png" />&nbsp;<b>他的好友抢话费</b>
		</div>
<!--  规则弹出层begin -->
	<style type="text/css">
	<!--
	html,body {height:100%; margin:0px; font-size:12px;}
	.mydiv {
	border: 0px solid #f00;
	text-align: center;
	line-height: 40px;
	font-size: 12px;
	font-weight: bold;
	z-index:9999;
	width: 100%;
	height: auto;
	left:50%;
	top:20%;
	margin-left:-50%!important;/*FF IE7 该值为本身宽的一半 */
	margin-top:-50%!important;/*FF IE7 该值为本身高的一半*/
	margin-top:0px;
	position:fixed!important;/* FF IE7*/
	position:absolute;/*IE6*/
	_top:       expression(eval(document.compatMode &&
	            document.compatMode=='CSS1Compat') ?
	            documentElement.scrollTop + (document.documentElement.clientHeight-this.offsetHeight)/2 :/*IE6*/
	            document.body.scrollTop + (document.body.clientHeight - this.clientHeight)/2);/*IE5 IE5.5*/
	
	}
	
	.bg,.popIframe {
	background-color: #666; display:none;
	width: 100%;
	height: 100%;
	left:0;
	top:0;/*FF IE7*/
	filter:alpha(opacity=50);/*IE*/
	opacity:0.5;/*FF*/
	z-index:8881;
	position:fixed!important;/*FF IE7*/
	position:absolute;/*IE6*/
	_top:       expression(eval(document.compatMode &&
	            document.compatMode=='CSS1Compat') ?
	            documentElement.scrollTop + (document.documentElement.clientHeight-this.offsetHeight)/2 :/*IE6*/
	            document.body.scrollTop + (document.body.clientHeight - this.clientHeight)/2);
	}
	.popIframe {
	filter:alpha(opacity=0);/*IE*/
	opacity:0;/*FF*/
	}
	-->
	</style>
	<script language="javascript" type="text/javascript">
	function gamerules(){
		showDivR();
	}
	
	//--------传递url重定向参数
	function showDivR(){
		document.getElementById('popDiv').style.display='block';
		document.getElementById('popIframe').style.display='block';
		document.getElementById('bg').style.display='block';
	}
	
	function closeDivR(obj){
		if(obj =='' || typeof(obj) == 'undefined'){
			document.getElementById('popDiv').style.display='none';
			document.getElementById('bg').style.display='none';
			document.getElementById('popIframe').style.display='none';
		}
	}
	
	</script>
	</head>
	<body>
	<div id="popDiv" class="mydiv" style="display:none;">
	    <%-- <center>
	        <div class="p3" style="height:1px;overflow:hidden;background:#ff6c00;width:294px;border-left:1px solid #ff6c00;border-right:1px solid #ff6c00;"></div>
	        <div class='p2' style='height:1px;overflow:hidden;background:#ff6c00;width:298px;border-left:1px solid #ff6c00;border-right:1px solid #ff6c00;'></div>
	        <div class="p1" style="height:30px;overflow:hidden;background:#ff6c00;width:300px;border-left:1px solid #ff6c00;border-right:1px solid #ff6c00;color:#fff;text-align:left;font-family: 微软雅黑;"><span style="font-size: 18px; margin-left: 5px; float: left; margin-top: -5px;">提示</span> <span style="float:right;margin-top:5px;z-index: 9999;"><img src="${ctxStatic}/images/gb.png" onclick="javascript:closeDivR()" /></span></div>
	        <div id="c" style="height:150px;width:300px;background-color:#FFF;overflow:hidden;border-left:1px solid #ff6c00;border-right:1px solid #ff6c00;padding-top:10px;padding-bottom:10px;"><span id="contentR" style="font-size:18px;font-family: 微软雅黑;color:#515151;"></span><br><div class="blank"></div><a href="javascript:closeDivR()"><button style="background-image:url(${ctxStatic}/images/btn.png);vertical-align: middle; border: 0px;width: 116px; height: 30px;font-size:18px;font-family: 微软雅黑;color:#515151;">关闭</button></a></div>
	        <div class="p1" style="height:1px;overflow:hidden;background:#FEEACB;width:298px;border-left:1px solid #ff6c00;border-right:1px solid #ff6c00;"></div>
	        <div class="p2" style="height:1px;overflow:hidden;background:#FEEACB;width:296px;border-left:1px solid #ff6c00;border-right:1px solid #ff6c00;"></div>
	        <div class="p3" style="height:1px;overflow:hidden;background:red;width:294px;border-left:1px solid #ff6c00;border-right:1px solid #ff6c00"></div>
	    </center> --%>
	    <div class="diceng">
		    <div class="blank2"></div>
		    <div class="rule"><img src="${ctxStatic}/activity/images/gz2.png" width="90%" />
		    	<div class="rule_btn"><a href="javascript:void(0);" onclick="closeDivR()"><img src="${ctxStatic}/activity/images/btn_gz.png" /></a></div>
		    </div>
	  	</div>
	</div>
	<div id="bg" class="bg" style="display:none;"></div>
	<iframe id='popIframe' class='popIframe' frameborder='0' ></iframe>
	<!--  规则弹出层end   -->
</body>
</html>
