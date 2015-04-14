package com.klcarwl.service;

import java.util.List;
import java.util.Map;

import com.klcarwl.model.MsgContent;
import com.klcarwl.model.UserInfo;
import com.klcarwl.util.JsonMsg;
import com.klcarwl.vo.MsgContentVo;

public interface MsgContentService extends BaseService<MsgContent, Long> {
	public JsonMsg saveMsgContent(MsgContentVo msgContentVo,String sourceName);
	
	public JsonMsg saveMsgContentQq(String content,String nickname,String sourceName);
	
	public JsonMsg findMsgContents(String station);
	public JsonMsg findMsgContents1(String station);
	public List<Map> findMsgContentsParseList(String station);
	public List<Map> findMsgContentsByUser(UserInfo userInfo);
	/**
	 * 查询从一个地方到一个地方的物流信息
	 * @param from
	 * @param to
	 * @return
	 */
	public JsonMsg findMsgContents(String from, String to);
	
	public Map findMsgContents(int pn,int size);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public JsonMsg deleteContent(Long[] ids);
	
	public JsonMsg updateContent(Long id,MsgContentVo msgContentVo);
	
	public JsonMsg getMsgContentsNums(String station);
}
