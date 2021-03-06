package com.klcarwl.vo;
/**
 *
 * Title: klcarwl
 *
 * Author:  zhaoguoqing
 *
 * Date: 2015年1月07日
 *
 * Description:微信关注者临时用户对象
 *
 */
public class WechatUser {

	private String subscribe;//用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	private String openid;//openid  key 用户的标识，对当前公众号唯一
	private String nickname; //账号名称
	private Integer sex; //用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	private String language; // 用户的语言，简体中文为zh_CN
	private String city ; //城市
	private String province; //省
	private String country; //国家
	private String headimgurl; //头像 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
	private String subscribe_time; //用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	public String getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getSubscribe_time() {
		return subscribe_time;
	}
	public void setSubscribe_time(String subscribe_time) {
		this.subscribe_time = subscribe_time;
	}
	public WechatUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WechatUser(String subscribe, String openid, String nickname,
			Integer sex, String language, String city, String province,
			String country, String headimgurl, String subscribe_time) {
		super();
		this.subscribe = subscribe;
		this.openid = openid;
		this.nickname = nickname;
		this.sex = sex;
		this.language = language;
		this.city = city;
		this.province = province;
		this.country = country;
		this.headimgurl = headimgurl;
		this.subscribe_time = subscribe_time;
	}
	@Override
	public String toString() {
		return "WechatUser [subscribe=" + subscribe + ", openid=" + openid
				+ ", nickname=" + nickname + ", sex=" + sex + ", language="
				+ language + ", city=" + city + ", province=" + province
				+ ", country=" + country + ", headimgurl=" + headimgurl
				+ ", subscribe_time=" + subscribe_time + "]";
	}
}
