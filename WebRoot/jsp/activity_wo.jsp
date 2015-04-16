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
	var shareData = {
       title: '首波10万话费来袭',
       desc: '首波10万话费来袭',
       link: 'http://klcar.com/klcarwl/activity.do?openidA=${openidA}',
       imgUrl: 'http://klcar.com/klcarwl/static/activity/images/shareicon.png',
       success: function(){
       	  closeDivR();
       	  showDivRSuccess();
	   }
    };
    $.wechatShare(shareData);

});

</script>
	<div class="templatesbox">
		<input type="hidden" id="jsapi_ticket" name="jsapi_ticket" value="${jsapi_ticket}"></input>
		<div class="warp">
			<div class="bgimg">
				<img src="${ctxStatic}/activity/images/xbg.png" width="100%" /><img src="${ctxStatic}/activity/images/bgg.png"
					width="100%" />
			</div>
			<div class="top">
				<div class="glod">
					<img src="${ctxStatic}/activity/images/gold.png" />
				</div>
				<div class="tishiActivity2">
					累计${BsumCost}元啦，<br />
					满10元或10元整倍数即可领取。
				</div>
				<div class="xbtn">
					<a href="javascript:void(0);" onclick="share()"><img src="${ctxStatic}/activity/images/x_btnn.png" />
					</a>
				</div>
			</div>
		</div>
		<div class="blank2"></div>
		<div class="contentr">
			<img src="${ctxStatic}/activity/images/icon_m.png" />&nbsp;<b>我的好友抢话费</b>
			<ul class="list">
				<c:forEach items="${userActivityList}" var="userActivityList" varStatus="index">
					<li>
						<span class="qiang">
							<img src="${ctxStatic}/activity/images/tou.png" width="10%" />&nbsp;${userActivityList.userInfo.nickName }抢了${userActivityList.helpCost}  元&nbsp;&nbsp;
						</span> 
						<span class="date"><fmt:formatDate value="${userActivityList.createDate}" pattern="yyyy-MM-dd"/></span>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
		<!--  规则弹出层begin -->
	<style type="text/css">
	<!--
	html,body {height:100%; margin:0px; font-size:12px;}
	.mydiv {
	text-align: center;
	line-height: 40px;
	font-size: 12px;
	z-index:9999;
	width: 100%;
	height: auto;
	left:50%;
	top:20%;
	margin-left:-50%!important;/*FF IE7 该值为本身宽的一半 */
	margin-top:-15%!important;/*FF IE7 该值为本身高的一半*/
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
	function share(){
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
	function showDivRSuccess(){
		document.getElementById('popDiv2').style.display='block';
		document.getElementById('popIframe').style.display='block';
		document.getElementById('bg').style.display='block';
	}
	function closeDivRSuccess(obj){
		if(obj =='' || typeof(obj) == 'undefined'){
			document.getElementById('popDiv2').style.display='none';
			document.getElementById('bg').style.display='none';
			document.getElementById('popIframe').style.display='none';
		}
	}
	
	</script>
	</head>
	<body>
	<div id="popDiv" class="mydiv" style="display:none;">
	    <div class="diceng">
		    <div class="zhishi"><img width="100%" src="${ctxStatic}/activity/images/tis.png"></div>
		    <div class="share">直接分享到微信群或者好友，抢话费更给力！</div>
	    </div>
	</div>
	<div id="popDiv2" class="mydiv" style="display:none;">
		<div class="diceng">
			<div class="blank2"></div>
			<div class="box">
				<div class="gb">
					<img src="${ctxStatic}/activity/images/gb.png" onclick="closeDivRSuccess()" />
				</div>
				<span>关注快乐物流领取话费</span> <img src="${ctxStatic}/activity/images/ts_ewm.png" width="90%" />
				<p class="tk">长按二维码识别图中二维码 关注领话费</p>
			</div>
		</div>
	</div>
	<div id="bg" class="bg" style="display:none;"></div>
	<iframe id='popIframe' class='popIframe' frameborder='0' ></iframe>
	<!--  规则弹出层end   -->
</body>
</html>
