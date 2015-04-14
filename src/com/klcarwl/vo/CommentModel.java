package com.klcarwl.vo;

import java.util.Date;


/**
 * XnComment entity. @author MyEclipse Persistence Tools
 */

public class CommentModel implements java.io.Serializable {

	// Fields

	private Long id;
	private Long user;
	public Long getUser() {
		return user;
	}



	public void setUser(Long user) {
		this.user = user;
	}

	private String postedBy;
	private Long featureSpot;
	private Integer star;
	private String content;
	private Date createTime;
	private String inUse;

	// Constructors

	/** default constructor */
	public CommentModel() {
	}

	

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPostedBy() {
		return this.postedBy;
	}

	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}

	public Long getFeatureSpot() {
		return this.featureSpot;
	}

	public void setFeatureSpot(Long featureSpot) {
		this.featureSpot = featureSpot;
	}

	public Integer getStar() {
		return this.star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getInUse() {
		return this.inUse;
	}

	public void setInUse(String inUse) {
		this.inUse = inUse;
	}

}