<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
	<%@ include file="/common/meta_dev_actity.jsp"%>
	<link href="${ctxStatic}/activity/css/style.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<script type="text/javascript" charset="utf-8" >
var w=0,tip=$("<b>");
var isStart=false;
var isStop = false;
$(function(){
	tip.css({
   			"z-index":99999,position:"absolute",color:"6633FF",display:"none","font-size":"30px"
    });
    
	 /********点击次数统计  begin**********/
     $("body").append(tip);//页面创建b标签用来显示数字
     $("#robbing").on("click",function(e){
     	  if(!isStart){
   	  		isStart= true;
   	  		countdown();
     	  }
     	  if(!isStop){
     	  	var x=e.pageX,y=e.pageY;//获取点击页面坐标
           	tip.text(++w).css({//数字加1
             	display:"block",top:y-15,left:x,opacity:1//定位显示
           	}).stop(!0,!1).animate({//stop(stopAll,goToEnd),如果发生多次点击时，要停止上一个动画的执行
           	top:y-180,opacity:0},800,function(){
              	tip.hide();
           	}),
           	e.stopPropagation();
     	  }
     });
     /********点击次数统计  end**********/
});

function countdown(){
	var i = 10;
	var t = setInterval(function(){
		if (i<=0) {
			/*<!-- 跳转抢话费结果页面！ --> */
			$("#sumtip").val(w);
			if(w !=""){
				window.location.href="<%=basePath %>robbing.do?sumtip="+w;
			}else{
				window.location.href="<%=basePath %>robbing.do";
			}
			w=0;
			clearInterval(t);
			return;
	   }
	   $("#timeclock").html(i);
	   i--;
	}, 1000);
	if(i ==0){
		isStop = true;
	}
}
</script>
	<div class="templatesbox">
		<input  type="hidden" id="sumtip"></input>
		<div class="warp">
			<div class="bgimg">
				<img src="${ctxStatic}/activity/images/dbg.png" width="100%" /><img src="${ctxStatic}/activity/images/bgg.png" width="100%" />
			</div>
			<div class="top">
				<div class="clock">
					<img src="${ctxStatic}/activity/images/clock.png" />
					<div class="clock_text">倒计时：</div>
					<div class="clock_text"><span id="timeclock">10</span>s</div>
				</div>
				<div class="logo">
					<a href="javascript:void(0);" id="robbing"><img src="${ctxStatic}/activity/images/car.png" /> </a>
				</div>
				<div class="glod">
					<img src="${ctxStatic}/activity/images/gold.png" />
				</div>
				<div class="btn">
					<a href="javascript:void(0);" onclick="gamerules()"><img src="${ctxStatic}/activity/images/btn_1.png" /> </a><a href="${ctx}/winners.do" target="_self" ><img src="${ctxStatic}/activity/images/btn_2.png" /> </a>
				</div>
			</div>
		</div>
		<div class="content">
			<img src="${ctxStatic}/activity/images/icon_m.png" />&nbsp;<b>他的好友抢话费</b>
		</div>
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
		     <div style="margin-top:13%;"></div>
		    <div class="rule"><img src="${ctxStatic}/activity/images/gz2.png" width="70%" />
		    	<div class="rule_btn"><a href="javascript:void(0);" onclick="closeDivR()"><img src="${ctxStatic}/activity/images/btn_gz.png" /></a></div>
		    </div>
	  	</div>
	</div>
	<div id="bg" class="bg" style="display:none;"></div>
	<iframe id='popIframe' class='popIframe' frameborder='0' ></iframe>
	<!--  规则弹出层end   -->
	
</body>
</html>
