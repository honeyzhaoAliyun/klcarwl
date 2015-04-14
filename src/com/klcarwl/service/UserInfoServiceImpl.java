package com.klcarwl.service;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.klcarwl.dao.UserInfoDao;
import com.klcarwl.model.UserInfo;
import com.klcarwl.util.Pager;

@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo,Long> implements UserInfoService{
	
	private static final Logger logger=Logger.getLogger(UserInfoServiceImpl.class);
	
	@Resource
	private UserInfoDao userInfoDao;
	
	@Resource
	public void setBaseDao(UserInfoDao userInfoDao) {
		super.setBaseDao(userInfoDao);
	}
	public void dododo(){
		System.out.println("dddddddddd");
		logger.info(UserInfoServiceImpl.class+"sdfsdfs");
	}
	public UserInfo getUserInfoByUsername(String username) {
		// TODO Auto-generated method stub
		return userInfoDao.getUserInfoByUsername(username);
	}
	public boolean verifyUserInfo(String username, String password) {
		// TODO Auto-generated method stub
		UserInfo userInfo = getUserInfoByUsername(username);
		if (userInfo != null && userInfo.getPassword().equals(DigestUtils.md5Hex(password))) {
			return true;
		} else {
			return false;
		}
	}
	public void saveUser(UserInfo userInfo) {
		// TODO Auto-generated method stub
		Long userId=userInfoDao.save(userInfo);
		
	}
	public Pager findUserInfo(Pager pager, String username,
			BigDecimal minaccount, BigDecimal maxaccount) {
		// TODO Auto-generated method stub
		return userInfoDao.findUserInfo(pager, username, minaccount, maxaccount);
	}
public static void main(String[] args) {
	System.out.println(DigestUtils.md5Hex("123456"));
}
}
