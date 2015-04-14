/**
*----------------------------------
*公用弹出消息提示-honey.zhao@aliyun.com
*@see /WebRoot/static/js/jquery/easyui-1.3.6/demo/messager/basic.html 有EN实例
*----------------------------------
*
**/

function show(){
	$.messager.show({
		title:'My Title',
		msg:'消息将在2秒后关闭！',
		showType:'show'
	});
}
function slide(){
	$.messager.show({
		title:'My Title',
		msg:'消息将在3秒后关闭！',
		timeout:3000,
		showType:'slide'
	});
}
function fade(){
	$.messager.show({
		title:'My Title',
		msg:'消息没有被关闭！',
		timeout:0,
		showType:'fade'
	});
}
function progress(){
	var win = $.messager.progress({
		title : '提示信息！',
        text : '数据处理中，请稍后....'
	});
	setTimeout(function(){
		$.messager.progress('close');
	},3000);
}
