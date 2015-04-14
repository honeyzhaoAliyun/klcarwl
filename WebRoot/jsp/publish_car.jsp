<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
	<%@ include file="/common/meta_dev.jsp"%>
 </head>
  
  <body>
    <div id="wrapbox" > 
  <!--顶部-->
  <div class="top"> <span
   class="bt1"><span class="bt11"><a href="<%=basePath %>index.do"><img src="${ctxStatic}/images/img_jt.png" /></a></span><span class="bt22"><a href="<%=basePath %>index.do">回首页</a></span></span><span class="btr">发布车源</span><span class="bt3"><a href="cars.html">
    <button type="button" class="btn btn-warning">我的车源</button>
    </a></span> </div>
  <!--顶部结束-->
  <div class="content"><%@ include file="/common/validate.jsp"%>
    <div class="content_fbhy">
      <form class="form-horizontal" role="form">
        <div class="form-group form-group-lg">
          <label class="col-sm-2 control-label" for="formGroupInputLarge">出发地</label>
          <div class="col-sm-10">
            <input class="form-control" type="text" id="formGroupInputLarge" placeholder="北京">
          </div>
        </div>
        <div class="form-group form-group-sm">
          <label class="col-sm-2 control-label" for="formGroupInputSmall">到达地</label>
          <div class="col-sm-10">
            <input class="form-control" type="text" id="formGroupInputSmall" placeholder="上海">
          </div>
        </div>
        <div class="form-group form-group-lg">
          <label class="col-sm-2 control-label" for="formGroupInputLarge">报价</label>
          <div class="col-sm-10">
            <input class="form-control" type="text" id="formGroupInputLarge" placeholder="请输入数字">
          </div>
        </div>
        <div class="form-group form-group-lg">
          <label class="col-sm-2 control-label" for="formGroupInputLarge">车牌号</label>
          <div class="col-sm-10">
            <input class="form-control" type="text" id="formGroupInputLarge" placeholder="W46517g">
          </div>
        </div>
        <div class="form-group form-group-lg">
          <label class="col-sm-2 control-label" for="formGroupInputLarge">车长</label>
          <div class="col-sm-10">
            <input class="form-control" type="text" id="formGroupInputLarge" placeholder="请输入数字">
          </div>
        </div>
        <div class="form-group form-group-lg">
          <label class="col-sm-2 control-label" for="formGroupInputLarge">载重（吨）</label>
          <div class="col-sm-10">
            <input class="form-control" type="text" id="formGroupInputLarge" placeholder="请输入数字">
          </div>
        </div>
        <div class="form-group form-group-lg">
          <label class="col-sm-2 control-label" for="formGroupInputLarge">车型</label>
          <div class="col-sm-10">
            <select class="form-control">
              <option>前四后八</option>
              <option>牵引</option>
              <option>重型</option>
              <option>拖挂</option>
              <option>四轮</option>
            </select>
          </div>
        </div>
        <div class="form-group form-group-sm">
          <label class="col-sm-2 control-label" for="formGroupInputSmall">说明</label>
          <div class="col-sm-10">
            <textarea class="form-control" rows="3">请输入</textarea>
          </div>
        </div>
        <div class="form-group form-group-lg">
          <label class="col-sm-2 control-label" for="formGroupInputLarge">联系人</label>
          <div class="col-sm-10">
            <input class="form-control" type="text" id="formGroupInputLarge" placeholder="李四">
          </div>
        </div>
        <div class="form-group form-group-sm">
          <label class="col-sm-2 control-label" for="formGroupInputSmall">联系电话</label>
          <div class="col-sm-10">
            <input class="form-control" type="text" id="formGroupInputSmall" placeholder="请输入">
          </div>
        </div>
      </form>
    </div>
    <div class="btn_dl">
      <div class="form-group"><a href="car_xq.html">
        <button type="submit" class="btn btn-warning">发布</button></a>
      </div>
    </div>
  </div>
</div>
  </body>
</html>
