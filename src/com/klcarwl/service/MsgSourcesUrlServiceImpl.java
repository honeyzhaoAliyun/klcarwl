package com.klcarwl.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.klcarwl.dao.MsgContentDao;
import com.klcarwl.dao.MsgSourcesUrlDao;
import com.klcarwl.model.MsgSourcesUrl;

@Service
public class MsgSourcesUrlServiceImpl extends BaseServiceImpl<MsgSourcesUrl, Long>
		implements MsgSourcesUrlService {
	@Resource
	private MsgSourcesUrlDao msgSourcesUrlDao;
	
	@Resource
	public void setBaseDao(MsgSourcesUrlDao msgSourcesUrlDao) {
		super.setBaseDao(msgSourcesUrlDao);
	}
	
}
