package com.klcarwl.util;

import java.io.Serializable;

import com.klcarwl.common.utils.mapper.JsonMapper;

public class JsonMsg implements Serializable{	
	private static final long serialVersionUID = 2799179868363430338L;
	public static final String STATUS_ERROR="error";
	public static final String STATUS_SUCCESS="success";
	private String status="error";
	private String msg;
	private Object data;
	/**
	 * 成功消息
	 */
	public static final String SUCCESS_MSG = "操作成功！";
	/**
	 * 失败消息
	 */
	public static final String ERROR_MSG = "操作失败:发生未知异常！";

	/**
	 * 
	 * @param code
	 *            结果状态码(可自定义结果状态码或者使用内部静态变量 1:操作成功 0:操作失败 2:警告)
	 * @param msg
	 *            响应结果描述
	 * @param obj
	 *            其它数据信息（比如跳转地址）
	 */
	public JsonMsg(String status, String msg, Object data) {
		super();
		this.status = status;
		this.msg = msg;
		this.data = data;
	}
	
	/**
	 * 默认操作成功结果.
	 */
	public static JsonMsg successResult() {
		return new JsonMsg(STATUS_SUCCESS, SUCCESS_MSG, null);
	}

	/**
	 * 默认操作失败结果.
	 */
	public static JsonMsg errorJsonMsg() {
		return new JsonMsg(STATUS_ERROR, ERROR_MSG, null);
	}
	
	@SuppressWarnings("static-access")
	@Override
	public String toString() {
		return new JsonMapper().nonDefaultMapper().toJson(this);
	}
	
	public JsonMsg() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public static String getStatusError() {
		return STATUS_ERROR;
	}
	public static String getStatusSuccess() {
		return STATUS_SUCCESS;
	}
	public static String getSuccessMsg() {
		return SUCCESS_MSG;
	}
	public static String getErrorMsg() {
		return ERROR_MSG;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
