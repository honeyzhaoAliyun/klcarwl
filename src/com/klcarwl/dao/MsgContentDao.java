package com.klcarwl.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.klcarwl.model.MsgContent;
import com.klcarwl.model.UserInfo;
import com.klcarwl.util.JsonMsg;

public interface MsgContentDao extends BaseDao<MsgContent, Long> {
	/**
	 * 根据标题，发布人，发布手机号，发布时间获取货物信息列表
	 * @param title
	 * @param name
	 * @param mobile
	 * @param publicationDate
	 * @return
	 */
	public List findMsgContentByTNT(String title,String name,String mobile,Date publicationDate);
	
	public List findMsgContents(String station); 
	public List findMsgContentList(Long from, Long to);
	public List findMsgContentByUser(UserInfo userInfo );
	
	public int getMsgContentsNums(Long from);
	/**
	 * 查询从一个地方到一个地方的物流信息
	 * @param from
	 * @param to
	 * @return
	 */
	public List findMsgContents(Long from,Long to); 
	/**
	 * 分页查询货物信息
	 * @param pn
	 * @param size
	 * @return
	 */
	public Map<String,Object> findMsgContents(int pn,int size); 
}
