package com.klcarwl.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.klcarwl.dao.MessageDao;
import com.klcarwl.model.Message;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message, Long>
		implements MessageService {
	@Resource
	private MessageDao messageDao;
	
	@Resource
	public void setBaseDao(MessageDao messageDao) {
		super.setBaseDao(messageDao);
	}
	public Map findMessages(int pn, int size, Long uid) throws Exception {
		// TODO Auto-generated method stub
		return messageDao.findMessages(pn, size, uid);
	}
	
}
