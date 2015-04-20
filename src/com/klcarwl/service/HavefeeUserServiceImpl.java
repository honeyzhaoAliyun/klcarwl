package com.klcarwl.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.klcarwl.dao.HavefeeUserDao;
import com.klcarwl.model.HavefeeUser;

@Service
public class HavefeeUserServiceImpl extends BaseServiceImpl<HavefeeUser, Long>
		implements HavefeeUserService {
	@Resource
	private HavefeeUserDao havefeeUserDao;
	
	@Resource
	public void setBaseDao(HavefeeUserDao havefeeUserDao) {
		super.setBaseDao(havefeeUserDao);
	}
	
	
}
