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
@Table(name="sys_locale")
public class SysLocale extends BaseEntity {
	//主键
	private Long id;
	//父
	private Long parent;
	//数据类型，1省，2城市
	private String type;
	//地区名称
	private String name;
	//地区全拼
	private String spelling;
	//地区简拼
	private String abbreviation;
	
	@SequenceGenerator(name="seq_sys_locale",allocationSize=1,initialValue=1,sequenceName="seq_sys_locale")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sys_locale")
	@Column(name = "id",unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getParent() {
		return parent;
	}
	public void setParent(Long parent) {
		this.parent = parent;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpelling() {
		return spelling;
	}
	public void setSpelling(String spelling) {
		this.spelling = spelling;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
}
