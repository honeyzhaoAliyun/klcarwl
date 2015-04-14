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
 * 人员充话费活动关系表
 * @author honey.zhao@aliyun.com
 *
 */
@Entity
@Table(name="user_activity")
public class UserActivity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	//主键
	private Long id;
	private Activity  activity;                   //当前活动ID
	private UserInfo userInfo;                    //当前活动人员ID
	private UserInfo parentUserInfo;              //帮谁充话费的所属人员ID
	private Double currentCost;                   //自己的当前话费
	private Double helpCost;                      //帮助充值的话费
	private Integer status;                       //0未充值 1已充值
	private String content;                       //内容
	
	@SequenceGenerator(name="seq_user_activity",allocationSize=1,initialValue=1,sequenceName="seq_user_activity")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_activity")
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name="activity_id",
		nullable=true,
		referencedColumnName="id"
	)
	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(
		name="parent_user_id",
		nullable=true,
		referencedColumnName="id"
	)
	public UserInfo getParentUserInfo() {
		return parentUserInfo;
	}

	public void setParentUserInfo(UserInfo parentUserInfo) {
		this.parentUserInfo = parentUserInfo;
	}

	@Column(name="current_cost",columnDefinition="FLOAT default 0")
    @NotNull
	public Double getCurrentCost() {
		return currentCost;
	}

	public void setCurrentCost(Double currentCost) {
		this.currentCost = currentCost;
	}
	@Column(name="help_cost",columnDefinition="FLOAT default 0")
    @NotNull
	public Double getHelpCost() {
		return helpCost;
	}

	public void setHelpCost(Double helpCost) {
		this.helpCost = helpCost;
	}
	
	@Column(name="status",length = 1,columnDefinition="INT default 0")
	/**
	 * 0未充值1已充值
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
