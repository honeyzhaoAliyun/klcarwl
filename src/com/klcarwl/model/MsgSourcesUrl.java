package com.klcarwl.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 * 信息采集来源地址管理
 * @author szf
 *
 */
@Entity
@Table(name="msg_sources_url")
public class MsgSourcesUrl extends BaseEntity {
	//主键
	private Long id;
	//信息来源网站名称
	private String sourcesName;
	//信息来源网站地址
	private String sourcesUrl;
	//host
	private String sourcesHost;
	
	@Column(name="sources_host")
	public String getSourcesHost() {
		return sourcesHost;
	}
	public void setSourcesHost(String sourcesHost) {
		this.sourcesHost = sourcesHost;
	}
	@SequenceGenerator(name="seq_msg_sources_url",allocationSize=1,initialValue=1,sequenceName="seq_msg_sources_url")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_msg_sources_url")
	@Column(name = "id",unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="sources_name")
	public String getSourcesName() {
		return sourcesName;
	}
	public void setSourcesName(String sourcesName) {
		this.sourcesName = sourcesName;
	}
	@Column(name="sources_url")
	public String getSourcesUrl() {
		return sourcesUrl;
	}
	public void setSourcesUrl(String sourcesUrl) {
		this.sourcesUrl = sourcesUrl;
	}
}
