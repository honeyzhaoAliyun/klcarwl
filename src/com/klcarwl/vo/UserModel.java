package com.klcarwl.vo;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * XnUser entity. @author MyEclipse Persistence Tools
 */

public class UserModel implements java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private String pwd;
	private Date regDate;
	private Date lastLogin;
	private String type;
	private String inUse;
	private String trueName;
	private Date delDate;
	private String phone;
	private String mobile;
	private String email;
	private String sex;
	private String idCard;
	private String smallImg;
	public String getSmallImg() {
		return smallImg;
	}

	public void setSmallImg(String smallImg) {
		this.smallImg = smallImg;
	}

	private Set xnComments = new HashSet(0);

	// Constructors

	/** default constructor */
	public UserModel() {
	}

	/** full constructor */
	public UserModel(String name, String pwd, Date regDate,
			Date lastLogin, String type, String inUse, String trueName,
			Date delDate, String phone, String mobile, String email,
			String sex, String idCard, Set xnComments) {
		this.name = name;
		this.pwd = pwd;
		this.regDate = regDate;
		this.lastLogin = lastLogin;
		this.type = type;
		this.inUse = inUse;
		this.trueName = trueName;
		this.delDate = delDate;
		this.phone = phone;
		this.mobile = mobile;
		this.email = email;
		this.sex = sex;
		this.idCard = idCard;
		this.xnComments = xnComments;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Date getRegDate() {
		return this.regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getLastLogin() {
		return this.lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInUse() {
		return this.inUse;
	}

	public void setInUse(String inUse) {
		this.inUse = inUse;
	}

	public String getTrueName() {
		return this.trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public Date getDelDate() {
		return this.delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdCard() {
		return this.idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Set getXnComments() {
		return this.xnComments;
	}

	public void setXnComments(Set xnComments) {
		this.xnComments = xnComments;
	}

}