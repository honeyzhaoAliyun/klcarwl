/**
 *  Copyright (c) 2014 http://oil.klcar.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.klcarwl.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 发送验证码日志ValidateLog.
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-21 上午12:28:04 
 *
 */
@Entity
@Table(name = "T_VALIDATE_LOG")
public class ValidateLog extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static int DEFAULT_LIST_PAGE_SIZE=10;
	//主键
	private Long id;
	//验证码类型，1邮箱验证码，2手机号验证码
	private String type;
	//生成的验证码,createDate是验证码生成时间
	private String validateCode;
	private String sendTo;//验证码发送的终端
	
	@SequenceGenerator(name="seq_validate_log",allocationSize=1,initialValue=1,sequenceName="seq_validate_log")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_validate_log")
	@Column(name = "id",unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="sendto")
	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	@Column(name="type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(name="validate_code")
	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	
}
