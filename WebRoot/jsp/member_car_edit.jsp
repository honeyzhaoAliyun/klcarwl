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

.btn_car {
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
   class="bt1"><span class="bt11"><a href="<%=basePath %>index.do"><img src="${ctxStatic}/images/img_jt.png" /></a></span><span class="bt22"><a href="<%=basePath %>index.do">回首页</a></span></span><span class="btrq2"><a href="${basePath }updateCarInfo.do"><span style="color:#FFF;">车辆&nbsp;&nbsp;</span></a><a  href="${basePath }updateComInfo.do"><span style="color:#f08300; ">公司</span></a></span><span class="bt3"><a href="${basePath }memberCenter.do">
    <button type="button" class="btn btn-warning">会员中心</button>
    </a></span> </div>
  <!--顶部结束-->
  <div class="content"><%@ include file="/common/validate.jsp"%>
    <div class="content_fbhy">
      <form action="${basePath }updateCarInfo.do" method="post">
        <div >
          <label class="col-sm-2 control-label" for="formGroupInputLarge">车牌号</label>
          <div class="col-sm-10">
            <input class="form-control" type="text" id="formGroupInputLarge" placeholder="车牌号" name="plateNo" value="${userInfo.plateNo }">
          </div>
        </div>
        <div >
          <label class="col-sm-2 control-label" for="formGroupInputLarge">车长（米）</label>
          <div class="col-sm-10">
          	<input class="form-control" type="text" id="formGroupInputLarge" placeholder="车长" name="length" value="${userInfo.length }">
          </div>
        </div>
        <div >
          <label class="col-sm-2 control-label" for="formGroupInputLarge">载重（吨）</label>
          <div class="col-sm-10">
            <input class="form-control" type="text" id="formGroupInputLarge" placeholder="载重" name="load" value="${userInfo.load }">
          </div>
        </div>
        <div >
          <label class="col-sm-2 control-label" for="formGroupInputLarge">车型</label>
          <div class="col-sm-10">
            <select class="form-control" name="models">
              <option <c:if test="${userInfo.models=='前四后八' }">selected="selected"</c:if>>前四后八</option>
              <option <c:if test="${userInfo.models=='牵引' }">selected="selected"</c:if>>牵引</option>
              <option <c:if test="${userInfo.models=='重型' }">selected="selected"</c:if>>重型</option>
              <option <c:if test="${userInfo.models=='拖挂' }">selected="selected"</c:if>>拖挂</option>
              <option <c:if test="${userInfo.models=='四轮' }">selected="selected"</c:if>>四轮</option>
            </select>
          </div>
        </div>
        <div >
          <label class="col-sm-2 control-label" for="formGroupInputLarge">联系人</label>
          <div class="col-sm-10">
            <input class="form-control" type="text" id="formGroupInputLarge" placeholder="联系人" name="name" value="${userInfo.name }">
          </div>
        </div>
        <div >
          <label class="col-sm-2 control-label" for="formGroupInputSmall">联系电话</label>
          <div class="col-sm-10">
            <input class="form-control" type="text" id="formGroupInputSmall" placeholder="联系电话" name="phone" value="${userInfo.phone }">
          </div>
        </div>
        <div >
          <label class="col-sm-2 control-label" for="formGroupInputSmall">手机</label>
          <div class="col-sm-10">
            <input class="form-control" type="text" id="formGroupInputSmall" placeholder="手机" name="mobile" value="${userInfo.mobile }">
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
	        <button type="submit" class="btn_car btn-warning">保存</button>
	      </div>
	    </div>
      </form>
    </div>
  </div>
  </div>
  </body>
</html>
