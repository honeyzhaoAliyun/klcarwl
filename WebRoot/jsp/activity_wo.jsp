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
       link: 'http://wechat.klcar.com:7000/klcarwl/activity.do?openidA=${openidA}',
       imgUrl: 'http://wechat.klcar.com:7000/klcarwl/static/activity/images/over.png'
    };
    $.wechatShare(shareData);

});

</script>
	<div class="templatesbox">
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
	
	
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
    window.shareData = {
        "timeLineLink": "http://wechat.klcar.com:7000/klcarwl/activity.do?openidA=${sessionScope.openidB}",    
        "sendFriendLink": "http://wechat.klcar.com:7000/klcarwl/activity.do?openidA=${sessionScope.openidB}",
        "weiboLink": "http://wechat.klcar.com:7000/klcarwl/activity.do?openidA=${sessionScope.openidB}",
        "tTitle": "首波10万话费来袭",
        "tContent": "首波10万话费来袭",
        "fTitle": "首波10万话费来袭",
        "fContent": "首波10万话费来袭",
        "wContent": "首波10万话费来袭"
        };
        // 发送给好友
        WeixinJSBridge.on('menu:share:appmessage', function (argv) {
            WeixinJSBridge.invoke('sendAppMessage', {
                "img_url": "http://wechat.klcar.com:7000/klcarwl/static/activity/images/over.png",
                "img_width": "401",
                "img_height": "275",
                "link": window.shareData.sendFriendLink,
                "desc": window.shareData.fContent,
                "title": window.shareData.fTitle
            }, function (res) {
                _report('send_msg', res.err_msg);
            })
        });
        // 分享到朋友圈
        WeixinJSBridge.on('menu:share:timeline', function (argv) {
            WeixinJSBridge.invoke('shareTimeline', {
                "img_url": "http://wechat.klcar.com:7000/klcarwl/static/activity/images/over.png",
                "img_width": "401",
                "img_height": "275",
                "link": window.shareData.timeLineLink,
                "desc": window.shareData.tContent,
                "title": window.shareData.tTitle
            }, function (res) {
                _report('timeline', res.err_msg);
            });
        });
 
    }, false);	
	</script>
	</head>
	<body>
	<div id="popDiv" class="mydiv" style="display:none;">
	    <div class="diceng">
		    <div class="zhishi"><img width="100%" src="${ctxStatic}/activity/images/tis.png"></div>
		    <div class="share">直接分享到微信群或者好友，抢话费更给力！</div>
	    </div>
	</div>
	<div id="bg" class="bg" style="display:none;"></div>
	<iframe id='popIframe' class='popIframe' frameborder='0' ></iframe>
	<!--  规则弹出层end   -->
</body>
</html>
