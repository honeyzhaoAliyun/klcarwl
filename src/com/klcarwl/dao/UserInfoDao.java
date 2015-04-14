package com.klcarwl.dao;

import java.math.BigDecimal;

import com.klcarwl.model.UserInfo;
import com.klcarwl.util.Pager;

public interface UserInfoDao extends BaseDao<UserInfo, Long> {
	public UserInfo getUserInfoByUsername(String username);
	public Pager findUserInfo(Pager pager,String username,BigDecimal minaccount,BigDecimal maxaccount);
}
