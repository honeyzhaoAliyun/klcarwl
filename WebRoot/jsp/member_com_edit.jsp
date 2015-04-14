<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
	<%@ include file="/common/meta_dev.jsp"%>
 </head>
  
  <body>
<style>
.bt3 {
    border: 0 none;
    clear: none;
    display: block;
    float: right;
    margin: -40px 10px 5px auto;
    min-height: 37px;
    min-width: 90px;
    text-align: left;
    width: 25%;
}
.btn_com {
    -moz-user-select: none;
    background-image: none;
    border: 1px solid transparent;
    border-radius: 4px;
    cursor: pointer;
    display: inline-block;
    line-height: 30px;
    font-size: 18px;
    font-weight: 400;
    padding:0;
    text-align: center;
    vertical-align: middle;
    white-space: nowrap;
    width: 100%;
    margin-top:10px;
}

</style>
    <div id="wrapbox" > 
  <!--顶部-->
  <div class="top"> <span
   class="bt1"><span class="bt11"><a href="<%=basePath %>index.do"><img src="${ctxStatic}/images/img_jt.png" /></a></span><span class="bt22"><a href="<%=basePath %>index.do">回首页</a></span></span><span class="btrq"><a href="${basePath }updateCarInfo.do"><span style="color:#f08300;">车辆</span></a>&nbsp;&nbsp;<a  href="${basePath }updateComInfo.do"><span style="color:#fff; ">公司</span></a></span><span class="bt3"><a href="${basePath }memberCenter.do">
    <button type="button" class="btn btn-warning">会员中心</button>
    </a></span> </div>
  <!--顶部结束-->
  <div class="content"><%@ include file="/common/validate.jsp"%>
    <div class="content_fbhy">
      <form method="post" action="${basePath }updateComInfo.do">
        <div >
          <label class="col-sm-2 control-label" for="formGroupInputLarge">公司名称</label>
          <div class="col-sm-10">
            <input class="form-control" type="text" id="formGroupInputLarge" placeholder="输入公司名称" name="companyName" value="${userInfo.companyName }">
          </div>
        </div>
        <div >
          <label class="col-sm-2 control-label" for="formGroupInputLarge">城市</label>
          <div class="col-sm-10">
            <input class="form-control" type="text" id="formGroupInputLarge" placeholder="城市" name="city" value="${userInfo.city }">
          </div>
        </div>
        
        
        <div >
          <label class="col-sm-2 control-label" for="formGroupInputSmall">地址</label>
          <div class="col-sm-10">
            <input class="form-control" type="text" id="formGroupInputSmall" placeholder="地址" name="address" value="${userInfo.address }">
          </div>
        </div><div >
          <label class="col-sm-2 control-label" for="formGroupInputSmall">介绍</label>
          <div class="col-sm-10">
            <textarea class="form-control" rows="3" name="abs">${userInfo.abs }</textarea>
          </div>
        </div>
        <div >
          <label class="col-sm-2 control-label" for="formGroupInputLarge">联系人</label>
          <div class="col-sm-10">
            <input class="form-control" type="text" id="formGroupInputLarge" placeholder="联系人" name="comLinkName" value="${userInfo.comLinkName }">
          </div>
        </div>
        <div >
          <label class="col-sm-2 control-label" for="formGroupInputSmall">联系电话</label>
          <div class="col-sm-10">
            <input class="form-control" type="text" id="formGroupInputSmall" placeholder="联系电话" name="comLinkPhone" value="${userInfo.comLinkPhone }">
          </div>
        </div><div >
          <label class="col-sm-2 control-label" for="formGroupInputSmall">QQ</label>
          <div class="col-sm-10">
            <input class="form-control" type="text" id="formGroupInputSmall" placeholder="QQ" name="comQQ" value="${userInfo.comQQ }">
          </div>
        </div>
        <div >
          <label class="col-sm-2 control-label" for="formGroupInputSmall">手机</label>
          <div class="col-sm-10">
            <input class="form-control" type="text" id="formGroupInputSmall" placeholder="手机" name="comMobile" value="${userInfo.comMobile }">
          </div>
        </div>
        <c:if test="${!empty errorMsg }">
			<div >
				<label class="col-sm-2 control-label" for="formGroupInputSmall"></label>
				<div class="col-sm-10" style="color: red">
								${errorMsg }
				</div>							
			</div>
		</c:if>
        <div class="btn_dl">
	      <div style="width: 95%;padding: 0;">
	        <button type="submit" class="btn_com btn-warning">保存</button>
	      </div>
	    </div>
      </form>
    </div>
    
  </div>
</div>
  </body>
</html>
