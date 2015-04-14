<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!-- 验证框架--begin -->
<script type="text/javascript" charset="utf-8" >
function msg(obj){
	$("#validate_content").html(obj);
	$("#validate_tishi").show();
	$("#bg").show();
	$("#popIframe").show();
	setTimeout("closeValidate()",2000);
}

function closeValidate(){
	$("#validate_content").html("");
	$("#validate_tishi").hide();
	$("#bg").hide();
	$("#popIframe").hide();
}
</script>
<style>
.bg,.popIframe {
background-color: #666; display:none;
width: 100%;
height: 7%;
left:0;
top:45px;/*FF IE7*/
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
</style>
<div id="validate_tishi" class="tishi" style="display:none;z-index:9999;margin:0 auto;border: 0px solid #f00;position:fixed!important;">
	<span id="validate_content" style="color:#fff; font-size:medium; margin:0 auto;display:block;text-align:center;line-height: 37px;"></span>
</div>
<div id="bg" class="bg" style="display:none;"></div>
<iframe id='popIframe' class='popIframe' frameborder='0' ></iframe>
<!-- 验证框架--end -->
