package com.klcarwl.vo;

import java.util.Date;

public class SigninByLocaleModel {
	private Long id;
	private Long user;
	private Long locale;
	private String content;
	private String inUse;
	private Date createTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUser() {
		return user;
	}
	public void setUser(Long user) {
		this.user = user;
	}
	public Long getLocale() {
		return locale;
	}
	public void setLocale(Long locale) {
		this.locale = locale;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getInUse() {
		return inUse;
	}
	public void setInUse(String inUse) {
		this.inUse = inUse;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
