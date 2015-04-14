package com.klcarwl.service;

import com.klcarwl.model.ValidateLog;

public interface ValidateLogService extends BaseService<ValidateLog, Long>{
	/**
	 * 生成一个验证码，发送给用户的邮箱或者手机号，同时将其存储
	 * @param type		发送类型，1邮箱验证码，2手机号验证码
	 * @param sendTo	发送的目的地
	 */
	public void saveAndSendValidateMsg(String type, String sendTo,String ids);
	/**
	 * 验证验证码信息是否正确
	 * @param type
	 * @param code
	 * @return
	 */
	public boolean validate(String type,String code, String sendTo);
	
	public ValidateLog getValidateLogBySendTo(String sendTo);

}
