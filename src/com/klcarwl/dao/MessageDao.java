package com.klcarwl.dao;

import java.util.Map;

import com.klcarwl.model.Message;

public interface MessageDao extends BaseDao<Message, Long> {
	
	public Map findMessages(int pn, int size,Long uid) throws Exception;
}
