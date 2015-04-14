package com.klcarwl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
  * 用户注册信息 用户注册信息
  */
@Entity
@Table(name="user_info")
public class UserInfo extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	public static int DEFAULT_LIST_PAGE_SIZE=10;
	//主键
	private Long id;
	//用户名	
	private String userName;	
	//姓名
	private String name;
	//呢称
	private String nickName;	
	private String mobile;
	//密码
	private String password;
	//状态: 0未激活 1激活 2锁定
	private String status;
	//IP地址 
	private String ip;
	//邮箱  
	private String email;
	//性别
	private String sex;
	
	private String wechatKey;
	//用户类型0管理员，1个人2企业
	private String type;
	//车牌号
	private String plateNo;
	//车长
	private Integer length;
	//载重
	private Integer load;
	//车型
	private String models;
	//联系座机
	private String phone;
	//公司名称
	private String companyName;
	//城市
	private String city;
	//地址
	private String address;
	//介绍
	private String abs;
	//公司联系人
	private String comLinkName;
	//公司联系电话
	private String comLinkPhone;
	//公司qq
	private String comQQ;
	//公司手机号
	private String comMobile;
	//当前余额
	private Double currentCost;
	
	@Column(name="plate_no")
	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getLoad() {
		return load;
	}

	public void setLoad(Integer load) {
		this.load = load;
	}

	public String getModels() {
		return models;
	}

	public void setModels(String models) {
		this.models = models;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name="company_name")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}
	@Column(name="com_link_name")
	public String getComLinkName() {
		return comLinkName;
	}

	public void setComLinkName(String comLinkName) {
		this.comLinkName = comLinkName;
	}
	@Column(name="com_link_phone")
	public String getComLinkPhone() {
		return comLinkPhone;
	}

	public void setComLinkPhone(String comLinkPhone) {
		this.comLinkPhone = comLinkPhone;
	}
	@Column(name="com_qq")
	public String getComQQ() {
		return comQQ;
	}

	public void setComQQ(String comQQ) {
		this.comQQ = comQQ;
	}
	@Column(name="com_mobile")
	public String getComMobile() {
		return comMobile;
	}

	public void setComMobile(String comMobile) {
		this.comMobile = comMobile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
	
	@SequenceGenerator(name="seq_user_info",allocationSize=1,initialValue=1,sequenceName="seq_user_info")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_info")
	@Column(name = "id",unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="user_name")
	public String getUserName(){
		return this.userName;
	}
	public void setUserName(String userName){
		this.userName = userName;
	}
	@Column(name="name")
	public String getName(){
		return this.name;
	}
	public void setName(String value){
		this.name = value;
	}
	@Column(name="nick_name")
	public String getNickName(){
		return this.nickName;
	}
	public void setNickName(String nickName){
		this.nickName = nickName;
	}
	@Column(name="mobile")
	public String getMobile(){
		return this.mobile;
	}
	public void setMobile(String value){
		this.mobile = value;
	}
	@Column(name="password")
	public String getPassword(){
		return this.password;
	}
	public void setPassword(String value){
		this.password = value;
	}
	@Column(name="status")
	public String getStatus(){
		return this.status;
	}
	public void setStatus(String value){
		this.status = value;
	}
	@Column(name="ip")
	public String getIp(){
		return this.ip;
	}
	public void setIp(String value){
		this.ip = value;
	}	
	@Column(name="email")
	public String getEmail(){
		return this.email;
	}
	public void setEmail(String value){
		this.email = value;
	}
	@Column(name="sex")
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Column(name = "wechat_key")
	public String getWechatKey() {
		return wechatKey;
	}

	public void setWechatKey(String wechatKey) {
		this.wechatKey = wechatKey;
	}
	@Column(name = "current_cost",columnDefinition="FLOAT default 0")
	public Double getCurrentCost() {
		return currentCost;
	}

	public void setCurrentCost(Double currentCost) {
		this.currentCost = currentCost;
	}
}
