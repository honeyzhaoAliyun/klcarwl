<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%@ include file="/common/meta_dev.jsp"%>
</head>
<body>
<script type="text/javascript">
        var inputForm;
        var input_linkbutton;
        var $fromLocale,$toLocale,$goodstype,$weight,$username,$mobile,$remark;
        
        $(function(){
            $fromLocale = $("#fromLocale");
            $toLocale = $("#toLocale");
            $goodstype = $("#goodstype");
            $weight = $("#weight");
            $username = $("#username");
            $mobile = $("#mobile");
            $remark = $("#remark");
            
            inputForm = $("#inputForm").form();
            inputForm.form("validate");
            
            
	        //inputform 表单提交
	        $("#pulish_btn").click(function(){
	            if(inputForm.form('validate')){
	                $.ajax({
						url:"${ctx}/goods/addFromStation.do", 
						type:"post", 
						async:false,
						dataType:'json',
						data: inputForm.serialize(),
						success: function(data) {
							if (data.status=="success") {
								msg("货源添加成功！");
								window.location.href="${ctx}/member/myGoods.do";//操作结果提示;
							} else {
								msg(data.msg);
							}
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) { 
							msg("ERROR. " + XMLHttpRequest.status); 
							msg("ERROR. " + XMLHttpRequest.readyState); 
							msg("ERROR. " + textStatus); 
							msg("ERROR. " + errorThrown); 
						}
					});
	        }});
        });
        
function fromcity(){
	window.location.href="<%=basePath %>sysLocaleList.do?type=1&resource=1&publishType=1";
}
function tocity(){
	window.location.href="<%=basePath %>sysLocaleList.do?type=1&resource=2&publishType=1";
}        
        
</script>
<style>
.btn_goods {
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
	   class="bt1"><span class="bt11"><a href="<%=basePath %>index.do"><img src="${ctxStatic}/images/img_jt.png" /></a></span><span class="bt22"><a href="<%=basePath %>index.do">回首页</a></span></span><span class="btr">发布货源</span><span class="bt3"><a href="<%=basePath %>member/myGoods.do">
	    <button type="button" class="btn btn-warning">我的货源</button>
	    </a></span> </div>
	  <!--顶部结束-->
	  <div class="content"><%@ include file="/common/validate.jsp"%>
	    <div class="content_fbhy">
	      <form class="form-horizontal" id="inputForm" method="post" enctype="multipart/form-data" novalidate>
	        <input type="hidden" id="goodId" name="goodId" value="${model.id }" ></input>
	        <div >
	          <label class="col-sm-2 control-label" for="formGroupInputLarge">出发地</label>
	          <div class="col-sm-9" onclick="fromcity()">
	            <input class="easyui-validatebox form-control" placeholder="出发地" type="text"
			               id="fromLocale" name="fromLocale"  value="${sessionScope.fromcity_publish==null ? model.fromLocale.name : sessionScope.fromcity_publish}"  />
	          </div>
	        </div>
	        <div >
	          <label class="col-sm-2 control-label" for="formGroupInputSmall">到达地</label>
	          <div class="col-sm-9" onclick="tocity()">
	            <input class="easyui-validatebox form-control" placeholder="到达地" type="text"
			               id="toLocale" name="toLocale"  value="${sessionScope.tocity_publish==null ? model.toLocale.name : sessionScope.tocity_publish}"  />
	          </div>
	        </div>
	        <div >
	          <label class="col-sm-2 control-label" for="formGroupInputLarge">货物类型</label>
	          <div class="col-sm-9">
	            <input class="easyui-validatebox form-control" placeholder="货物类型" type="text"
			               id="goodstype" name="goodstype" value="${model.type }"   />
	          </div>
	        </div>
	        <div >
	          <label class="col-sm-2 control-label" for="formGroupInputSmall">重量（吨）</label>
	          <div class="col-sm-9">
	            <input class="easyui-validatebox form-control" placeholder="重量" type="text"
			               id="weight" name="weight" value="${model.weight }"   />
	          </div>
	        </div>
	        <div >
	          <label class="col-sm-2 control-label" for="formGroupInputLarge">联系人</label>
	          <div class="col-sm-9">
	            <input class="easyui-validatebox form-control" placeholder="联系人" type="text"
			               id="username" name="username" value="${model.name }"    />
	          </div>
	        </div>
	        <div >
	          <label class="col-sm-2 control-label" for="formGroupInputSmall">联系电话</label>
	          <div class="col-sm-9">
	            <input class="easyui-validatebox form-control" placeholder="联系电话" type="text"
			               id="mobile" name="mobile" value="${model.mobile }"  />
	          </div>
	        </div>
	        <div >
	          <label class="col-sm-2 control-label" for="formGroupInputSmall">说明</label>
	          <div class="col-sm-9">
	            <textarea class="form-control" name="remark" value="${model.remark }" placeholder="说明" rows="3"></textarea>
	          </div>
	        </div>
	      </form>
	    </div>
	    <div class="btn_dl">
	       <div style="width: 95%;padding: 0;">
	         <button type="button" class="btn_goods btn-warning" id="pulish_btn" >发布</button>
	       </div>
	    </div>
	  </div>
	</div>
  </body>
</html>
