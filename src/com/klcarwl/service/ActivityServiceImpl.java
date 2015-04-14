package com.klcarwl.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.klcarwl.dao.ActivityDao;
import com.klcarwl.model.Activity;

@Service
public class ActivityServiceImpl extends BaseServiceImpl<Activity, Long>
		implements ActivityService {
	@Resource
	private ActivityDao activityDao;
	
	@Resource
	public void setBaseDao(ActivityDao activityDao) {
		super.setBaseDao(activityDao);
	}
	
	
}
