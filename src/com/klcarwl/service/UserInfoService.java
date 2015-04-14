package com.klcarwl.service;

import java.math.BigDecimal;

import com.klcarwl.model.UserInfo;
import com.klcarwl.util.Pager;

public interface UserInfoService extends BaseService<UserInfo, Long>{
	/**
	 * 通过用户名获取用户信息
	 * @param username
	 * @return
	 */
	public UserInfo getUserInfoByUsername(String username);
	/**
	 * 验证用户密码
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean verifyUserInfo(String username, String password);
	/**
	 * 注册时添加用户,同时为其分配账户
	 * @param userInfo
	 */
	public void saveUser(UserInfo userInfo);
	/**
	 * 查询用户信息
	 * @param pager
	 * @param username
	 * @param minaccount
	 * @param maxaccount
	 * @return
	 */
	public Pager findUserInfo(Pager pager, String username,
			BigDecimal minaccount, BigDecimal maxaccount);
}
