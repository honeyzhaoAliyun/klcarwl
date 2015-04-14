package com.klcarwl.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.klcarwl.dao.ActivityLogDao;
import com.klcarwl.model.ActivityLog;

@Service
public class ActivityLogServiceImpl extends BaseServiceImpl<ActivityLog, Long>
		implements ActivityLogService {
	@Resource
	private ActivityLogDao activityLogDao;
	
	@Resource
	public void setBaseDao(ActivityLogDao activityLogDao) {
		super.setBaseDao(activityLogDao);
	}
	
	
}
