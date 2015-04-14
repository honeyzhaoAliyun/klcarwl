package com.klcarwl.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
/**
 * 货物信息表
 * @author szf
 *
 */
@Entity
@Table(name="msg_content")
public class MsgContent extends BaseEntity {
	private static final long serialVersionUID = 1L;
	//主键
	private Long id;
	private MsgSourcesUrl msgSourcesUrl;
	//出发地
	private SysLocale fromLocale;
	//到达地
	private SysLocale toLocale;
	
	//信息标题
	private String title;
	//信息内容
	private String content;
	//发布者
	private String name;
	//联系电话
	private String mobile;
	//qq号码
	private String qq;
	//状态
	private String status;
	//备注
	private String remark;
	//货物类型
	private String type;
	//重量吨
	private Integer weight;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	//信息发布日期
	private Date publicationDate;
	//信息有效期
	private Date validDate;
	//信息级别
	private String step;
	//座机
	private String phone;
	//货运有效区域
	private String station;
	//关键词
	private String keywords;
	//userid
	private String userid;
	
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	@SequenceGenerator(name="seq_msg_content",allocationSize=1,initialValue=1,sequenceName="seq_msg_content")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_msg_content")
	@Column(name = "id",unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name="url_id",
		nullable=true,
		referencedColumnName="id"
	)
	public MsgSourcesUrl getMsgSourcesUrl() {
		return msgSourcesUrl;
	}
	public void setMsgSourcesUrl(MsgSourcesUrl msgSourcesUrl) {
		this.msgSourcesUrl = msgSourcesUrl;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(
		name="from_locale",
		nullable=true,
		referencedColumnName="id"
	)
	public SysLocale getFromLocale() {
		return fromLocale;
	}
	public void setFromLocale(SysLocale fromLocale) {
		this.fromLocale = fromLocale;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(
		name="to_locale",
		nullable=true,
		referencedColumnName="id"
	)
	public SysLocale getToLocale() {
		return toLocale;
	}
	public void setToLocale(SysLocale toLocale) {
		this.toLocale = toLocale;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="publication_date")
	public Date getPublicationDate() {
		return publicationDate;
	}
	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}
	@Column(name="valid_date")
	public Date getValidDate() {
		return validDate;
	}
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}
	@Column(name="userid")
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
}
