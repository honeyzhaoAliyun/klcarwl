package com.klcarwl.service;

import java.util.Map;

import com.klcarwl.model.Message;

public interface MessageService extends BaseService<Message, Long> {
	public Map findMessages(int pn, int size,Long userId) throws Exception;
}
