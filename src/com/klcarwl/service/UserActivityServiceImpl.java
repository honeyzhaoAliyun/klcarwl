package com.klcarwl.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.klcarwl.dao.UserActivityDao;
import com.klcarwl.model.UserActivity;

@Service
public class UserActivityServiceImpl extends BaseServiceImpl<UserActivity, Long>
		implements UserActivityService {
	@Resource
	private UserActivityDao userActivityDao;
	
	@Resource
	public void setBaseDao(UserActivityDao userActivityDao) {
		super.setBaseDao(userActivityDao);
	}
	
	
}
