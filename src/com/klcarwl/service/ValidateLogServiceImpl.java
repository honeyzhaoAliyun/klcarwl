/**
 *  Copyright (c) 2014 http://oil.klcar.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.klcarwl.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.klcarwl.dao.ValidateLogDao;
import com.klcarwl.model.ValidateLog;

/**
 * 验证码日志管理Service层.
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-27 下午8:00:22 
 *
 */
@Service
public class ValidateLogServiceImpl extends BaseServiceImpl<ValidateLog,Long> implements ValidateLogService{

	@Resource
	private ValidateLogDao validateLogDao;
	
	@Resource
	public void setBaseDao(ValidateLogDao validateLogDao) {
		super.setBaseDao(validateLogDao);
	}
	
	public void saveAndSendValidateMsg(String type, String sendTo, String ids) {
		validateLogDao.saveAndSendValidateMsg(type, sendTo, ids);
		
	}

	public boolean validate(String type, String code, String sendTo) {
		
		return validateLogDao.validate(type, code, sendTo);
	}

	public ValidateLog getValidateLogBySendTo(String sendTo) {
		
		return validateLogDao.getValidateLogBySendTo(sendTo);
	}

	

}
