package com.klcarwl.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 * 活动
 * @author honey.zhao@aliyun.com
 * @serial 2015-03-26
 * @version v1.0
 *
 */
@Entity
@Table(name="activity")
public class Activity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	//主键
	private Long id;
	private String title;
	private String content;
	private Date beginDate;// 开始日期
	private Date endDate;// 结束日期
	
	@SequenceGenerator(name="seq_activity",allocationSize=1,initialValue=1,sequenceName="seq_activity")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_activity")
	@Column(name = "id",unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name="content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name="begin_date")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	@Column(name="end_date")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
