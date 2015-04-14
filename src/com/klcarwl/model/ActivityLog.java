package com.klcarwl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 * 活动访问日志
 * @author honey.zhao@aliyun.com
 * @serial 2015-03-26
 * @version v1.0
 *
 */
@Entity
@Table(name="activity_log")
public class ActivityLog extends BaseEntity {
	private static final long serialVersionUID = 1L;
	//主键
	private Long id;
	private String openid;
	private String content;
	
	
	@SequenceGenerator(name="seq_activity_log",allocationSize=1,initialValue=1,sequenceName="seq_activity_log")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_activity_log")
	@Column(name = "id",unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Column(name="content")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name="openid")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
}
