package com.klcarwl.model;

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

import com.sun.istack.internal.NotNull;
/**
 * 充话费记录表
 * @author honey.zhao@aliyun.com
 *
 */
@Entity
@Table(name="havefee_user")
public class HavefeeUser extends BaseEntity {
	private static final long serialVersionUID = 1L;
	//主键
	private Long id;
	private UserInfo userInfo;                    //当前活动人员ID
	private Double TopupCost;                   //自己的当前话费
	private String content;                       //内容
	
	@SequenceGenerator(name="seq_havefee_user",allocationSize=1,initialValue=1,sequenceName="seq_havefee_user")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_havefee_user")
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(
		name="user_id",
		nullable=true,
		referencedColumnName="id"
	)
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@Column(name="topup_cost",columnDefinition="FLOAT default 0")
    @NotNull
	public Double getTopupCost() {
		return TopupCost;
	}

	public void setTopupCost(Double topupCost) {
		TopupCost = topupCost;
	}
}
