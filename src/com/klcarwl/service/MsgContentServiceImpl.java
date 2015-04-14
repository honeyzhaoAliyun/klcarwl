package com.klcarwl.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.klcarwl.cache.CacheData;
import com.klcarwl.common.ErrorMsg;
import com.klcarwl.dao.MsgContentDao;
import com.klcarwl.dao.MsgSourcesUrlDao;
import com.klcarwl.dao.SysLocaleDao;
import com.klcarwl.model.MsgContent;
import com.klcarwl.model.MsgSourcesUrl;
import com.klcarwl.model.SysLocale;
import com.klcarwl.model.UserInfo;
import com.klcarwl.util.CommonUtil;
import com.klcarwl.util.DateUtil;
import com.klcarwl.util.JsonMsg;
import com.klcarwl.vo.MsgContentVo;

@Service
public class MsgContentServiceImpl extends BaseServiceImpl<MsgContent, Long>
		implements MsgContentService {
	@Resource
	private MsgContentDao msgContentDao;
	@Resource
	private MsgSourcesUrlDao msgSourcesUrlDao;
	@Resource
	private SysLocaleDao sysLocaleDao;
	@Resource
	public void setBaseDao(MsgContentDao msgContentDao) {
		super.setBaseDao(msgContentDao);
	}

	public JsonMsg saveMsgContent(MsgContentVo msgContentVo,String sourceName) {
		// TODO Auto-generated method stub
		JsonMsg jsonMsg=new JsonMsg();
		Long id=null;
		MsgContent msgContent=new MsgContent();
		if(msgContentVo.getId()!=null && !msgContentVo.getId().equals("")){
			msgContent = msgContentDao.get(msgContentVo.getId());
		}
		msgContent.setContent(msgContentVo.getContent());
		msgContent.setCreateDate(new Date());
		msgContent.setIsUse("1");
		msgContent.setKeywords(msgContentVo.getKeywords());
		msgContent.setMobile(msgContentVo.getMobile());
		msgContent.setName(msgContentVo.getName());
		msgContent.setPhone(msgContentVo.getPhone());
		msgContent.setQq(msgContentVo.getQq());
		msgContent.setStatus("1");
		msgContent.setStep("1");
		msgContent.setTitle(msgContentVo.getTitle());
		msgContent.setType(msgContentVo.getType());
		msgContent.setWeight(msgContentVo.getWeight());
		msgContent.setUserid(msgContentVo.getUserid());
		SysLocale fromLocale=null;
		SysLocale toLocale=null;
		if(msgContentVo.getFrom()==null||"".equals(msgContentVo.getFrom())){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.FROM_LOCALE_ERROR);
			return jsonMsg;
		}
		if(msgContentVo.getTo()==null||"".equals(msgContentVo.getTo())){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.TO_LOCALE_ERROR);
			return jsonMsg;
		}
		
		if(CacheData.cityCache.size()<=0){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysLocale.class);
			detachedCriteria.add(Restrictions.eq("type", "2"));
			CacheData.cityCache=sysLocaleDao.find(detachedCriteria);
		}
		if(CacheData.provinceCache.size()<=0){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysLocale.class);
			detachedCriteria.add(Restrictions.eq("type", "1"));
			CacheData.provinceCache=sysLocaleDao.find(detachedCriteria);
		}
		
		fromLocale=CacheData.getLocaleByStation(msgContentVo.getFrom());
		toLocale=CacheData.getLocaleByStation(msgContentVo.getTo());
		if(fromLocale==null){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.FROM_LOCALE_ERROR);
			return jsonMsg;
		}
		if(toLocale==null){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.TO_LOCALE_ERROR);
			return jsonMsg;
		}
		
		/*if(fromLocale.getId().equals(toLocale.getId())){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.FROM_LOCALE_EQUALS_TO_LOCALE);
			return jsonMsg;
		}*/
		//验证信息发布时间
		String pattern="^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$";
		String msg;
		if(!CommonUtil.checkPattern(pattern,msgContentVo.getPublicationDate())){
			msg = ErrorMsg.PUBLICATION_DATE_ERROR;
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(msg);
			return jsonMsg;
		}else{
			msgContent.setPublicationDate(DateUtil.getDate(msgContentVo.getPublicationDate(), DateUtil.allDatePartern));
		}
		//验证有效期
		if(!(msgContentVo.getValidDate()==null||"".equals(msgContentVo.getValidDate()))){
			if(!CommonUtil.checkPattern(pattern,msgContentVo.getValidDate())){
				msg = ErrorMsg.PUBLICATION_DATE_ERROR;
				jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
				jsonMsg.setMsg(msg);
				return jsonMsg;
			}else{
				msgContent.setValidDate(DateUtil.getDate(msgContentVo.getValidDate(), DateUtil.allDatePartern));
			}
		}
		
		fromLocale=sysLocaleDao.get(fromLocale.getId());
		toLocale=sysLocaleDao.get(toLocale.getId());
		msgContent.setFromLocale(fromLocale);
		msgContent.setToLocale(toLocale);
		
		DetachedCriteria detachedCriteria2 = DetachedCriteria.forClass(MsgSourcesUrl.class);
		detachedCriteria2.add(Restrictions.eq("sourcesHost",sourceName));
		List<MsgSourcesUrl> msgSourcesUrls= msgSourcesUrlDao.find(detachedCriteria2);
		if(!(msgSourcesUrls==null||msgSourcesUrls.size()<0)){
			msgContent.setMsgSourcesUrl(msgSourcesUrls.get(0));
			msgContentDao.saveOrUpdate(msgContent);			
		}
		jsonMsg.setStatus(JsonMsg.STATUS_SUCCESS);
		return jsonMsg;
	}

	public JsonMsg findMsgContents(String station) {
		// TODO Auto-generated method stub
		JsonMsg jsonMsg=new JsonMsg();
		String from=null,to=null;
		if(!(station==null||"".equals(station))&&station.indexOf("到")>=0){
			String[] places=station.split("到");
			from=places[0];
			if(places.length>1){
				to=places[1];
			}
		}
		
		if(StringUtils.isEmpty(from)){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.FROM_LOCALE_IS_EMPTY);
			return jsonMsg;
		}
		if(StringUtils.isEmpty(to)){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.TO_LOCALE_IS_EMPTY);
			return jsonMsg;
		}		
		if(CacheData.cityCache.size()<=0){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysLocale.class);
			detachedCriteria.add(Restrictions.eq("type", "2"));
			CacheData.cityCache=sysLocaleDao.find(detachedCriteria);
		}
		if(CacheData.provinceCache.size()<=0){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysLocale.class);
			detachedCriteria.add(Restrictions.eq("type", "1"));
			CacheData.provinceCache=sysLocaleDao.find(detachedCriteria);
		}
		
		SysLocale fromLocale=null;
		SysLocale toLocale=null;
		
		fromLocale=CacheData.getLocaleByStation(from);
		toLocale=CacheData.getLocaleByStation(to);
		if(fromLocale==null){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.FROM_LOCALE_ERROR);
			return jsonMsg;
		}
		if(toLocale==null){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.TO_LOCALE_ERROR);
			return jsonMsg;
		}
		
		/*if(fromLocale.getId().equals(toLocale.getId())){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.FROM_LOCALE_EQUALS_TO_LOCALE);
			return jsonMsg;
		}*/
		List<Map> msgContents=msgContentDao.findMsgContents(fromLocale.getId(),toLocale.getId());
		StringBuffer theResultMsg=new StringBuffer();
		int i=1;
		for(Map msgContent:msgContents){
			String id=msgContent.get("ID").toString();
			String title=(String)msgContent.get("TITLE");
			String name=(String)msgContent.get("NAME");
			String phone=(String)msgContent.get("PHONE");
			String mobile=(String)msgContent.get("MOBILE");
			String content=(String)msgContent.get("CONTENT");
			String publicationDate=(String)msgContent.get("PUBLICATION_DATE");
			Date thePublicationDate=DateUtil.getDate(publicationDate, DateUtil.allDatePartern);
			Date theDate=new Date();
			int mint=DateUtil.getIntervalTimeMinute(thePublicationDate,theDate);
			String time="";
			if(mint<0){
				mint=0;
				time=mint+"分钟内";
			}else if(mint<60){
				time=mint+"分钟内";
			}else{
				int hour=DateUtil.getIntervalTimeHour(thePublicationDate,theDate);
				time=hour+"小时内";
			}
			theResultMsg.append(i+"、");
			theResultMsg.append(content+"/");
			theResultMsg.append(time);
			//theResultMsg.append(title+";");
			theResultMsg.append("\n");
			if(!StringUtils.isEmpty(name)){
				theResultMsg.append("联系人:"+name+"/");
			}
			
			if(StringUtils.isEmpty(mobile)){
				theResultMsg.append("电话:"+phone);		
			}else{
				theResultMsg.append("电话:"+mobile);	
			}
			
				
			theResultMsg.append("\n");
			i++;
		}
		if(msgContents.size()>0){
			theResultMsg.append("请及时联系！");
			theResultMsg.append("\n");
			theResultMsg.append("————————————————");
			theResultMsg.append("\n");
			theResultMsg.append("快乐大车配货服务,客服电话：4000580777\n");
			theResultMsg.append("或关注微信服务号：klcarclub");
			theResultMsg.append("\n");
			theResultMsg.append("自动免费查货");
		}else{
			theResultMsg.append("对不起，暂时没有找到"+station+"的货源。");
			theResultMsg.append("\n");
			theResultMsg.append("请您尝试更换附近城市作为出发地或目的地再次查询！");
			theResultMsg.append("\n");
			theResultMsg.append("快乐大车配货服务,客服电话：4000580777\n");
			theResultMsg.append("或关注微信服务号：klcarclub");
			theResultMsg.append("\n");
			theResultMsg.append("自动免费查货");
		}
		jsonMsg.setStatus(JsonMsg.STATUS_SUCCESS);
		jsonMsg.setData(theResultMsg.toString());
		return jsonMsg;
	}
	public JsonMsg findMsgContents1(String station) {
		// TODO Auto-generated method stub
		JsonMsg jsonMsg=new JsonMsg();
		String from=null,to=null;
		if(!(station==null||"".equals(station))&&station.indexOf("到")>=0){
			String[] places=station.split("到");
			from=places[0];
			if(places.length>1){
				to=places[1];
			}
		}
		
		if(StringUtils.isEmpty(from)){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.FROM_LOCALE_IS_EMPTY);
			return jsonMsg;
		}
		if(StringUtils.isEmpty(to)){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.TO_LOCALE_IS_EMPTY);
			return jsonMsg;
		}		
		if(CacheData.cityCache.size()<=0){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysLocale.class);
			detachedCriteria.add(Restrictions.eq("type", "2"));
			CacheData.cityCache=sysLocaleDao.find(detachedCriteria);
		}
		if(CacheData.provinceCache.size()<=0){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysLocale.class);
			detachedCriteria.add(Restrictions.eq("type", "1"));
			CacheData.provinceCache=sysLocaleDao.find(detachedCriteria);
		}
		
		SysLocale fromLocale=null;
		SysLocale toLocale=null;
		
		fromLocale=CacheData.getLocaleByStation(from);
		toLocale=CacheData.getLocaleByStation(to);
		if(fromLocale==null){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.FROM_LOCALE_ERROR);
			return jsonMsg;
		}
		if(toLocale==null){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.TO_LOCALE_ERROR);
			return jsonMsg;
		}
		
		/*if(fromLocale.getId().equals(toLocale.getId())){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.FROM_LOCALE_EQUALS_TO_LOCALE);
			return jsonMsg;
		}*/
		List<Map> msgContents=msgContentDao.findMsgContents(fromLocale.getId(),toLocale.getId());
		StringBuffer theResultMsg=new StringBuffer();
		int i=1;
		for(Map msgContent:msgContents){
			String id=msgContent.get("ID").toString();
			String title=(String)msgContent.get("TITLE");
			String name=(String)msgContent.get("NAME");
			String phone=(String)msgContent.get("PHONE");
			String mobile=(String)msgContent.get("MOBILE");
			String content=(String)msgContent.get("CONTENT");
			String publicationDate=(String)msgContent.get("PUBLICATION_DATE");
			Date thePublicationDate=DateUtil.getDate(publicationDate, DateUtil.allDatePartern);
			Date theDate=new Date();
			int mint=DateUtil.getIntervalTimeMinute(thePublicationDate,theDate);
			String time="";
			if(mint<0){
				mint=0;
				time=mint+"分钟内";
			}else if(mint<60){
				time=mint+"分钟内";
			}else{
				int hour=DateUtil.getIntervalTimeHour(thePublicationDate,theDate);
				time=hour+"小时内";
			}
			theResultMsg.append(i+"、");
			theResultMsg.append(content+"/");
			theResultMsg.append(time);
		//	theResultMsg.append(title+";");
			theResultMsg.append("\n");
			if(!StringUtils.isEmpty(name)){
				theResultMsg.append("联系人:"+name+"/");
			}
			if(StringUtils.isEmpty(mobile)){
				theResultMsg.append("电话:"+phone);		
			}else{
				theResultMsg.append("电话:"+mobile);	
			}	
			theResultMsg.append("\n");
			i++;
		}
		if(msgContents.size()>0){
			theResultMsg.append("请及时联系！");
			theResultMsg.append("\n");
			theResultMsg.append("您也可以加QQ群与车友们交流互动：306620131");
			theResultMsg.append("————————————————");
			theResultMsg.append("\n");
			theResultMsg.append("快乐大车免费配货服务,客服电话：4000580777");
		}else{
			theResultMsg.append("对不起，暂时没有找到"+station+"的货源。");
			theResultMsg.append("\n");
			theResultMsg.append("请您尝试更换附近城市作为出发地或目的地再次查询!");
			theResultMsg.append("\n");
			theResultMsg.append("您也可以加QQ群与车友们交流互动：306620131");
			theResultMsg.append("————————————————");
			theResultMsg.append("\n");
			theResultMsg.append("快乐大车免费配货服务,客服电话：4000580777");
		}
		jsonMsg.setStatus(JsonMsg.STATUS_SUCCESS);
		jsonMsg.setData(theResultMsg.toString());
		return jsonMsg;
	}
	
	/**
	 * 查询货物返回List
	 * @return List<Map>
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> findMsgContentsParseList(String station) {
		
		String from=null,to=null;
		if(!(station==null||"".equals(station))&&station.indexOf("到")>=0){
			String[] places=station.split("到");
			from=places[0];
			if(places.length>1){
				to=places[1];
			}
		}
		if(CacheData.cityCache.size()<=0){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysLocale.class);
			detachedCriteria.add(Restrictions.eq("type", "2"));
			CacheData.cityCache=sysLocaleDao.find(detachedCriteria);
		}
		if(CacheData.provinceCache.size()<=0){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysLocale.class);
			detachedCriteria.add(Restrictions.eq("type", "1"));
			CacheData.provinceCache=sysLocaleDao.find(detachedCriteria);
		}
		
		SysLocale fromLocale=null;
		SysLocale toLocale=null;
		
		fromLocale=CacheData.getLocaleByStation(from);
		toLocale=CacheData.getLocaleByStation(to);
		
		List<Map> msgContents=msgContentDao.findMsgContentList(fromLocale.getId(),toLocale.getId());
		
		return msgContents;
	}
	/**
	 * 根据用户查询我的货源
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> findMsgContentsByUser(UserInfo userInfo) {
		List<Map> msgContents=msgContentDao.findMsgContentByUser(userInfo);
		return msgContents;
	}
	
	
	public JsonMsg findMsgContents(String from, String to) {
		// TODO Auto-generated method stub
		
		JsonMsg jsonMsg=new JsonMsg();
		if(StringUtils.isEmpty(from)){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.FROM_LOCALE_IS_EMPTY);
			return jsonMsg;
		}
		if(StringUtils.isEmpty(to)){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.TO_LOCALE_IS_EMPTY);
			return jsonMsg;
		}		
		if(CacheData.cityCache.size()<=0){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysLocale.class);
			detachedCriteria.add(Restrictions.eq("type", "2"));
			CacheData.cityCache=sysLocaleDao.find(detachedCriteria);
		}
		if(CacheData.provinceCache.size()<=0){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysLocale.class);
			detachedCriteria.add(Restrictions.eq("type", "1"));
			CacheData.provinceCache=sysLocaleDao.find(detachedCriteria);
		}
		
		SysLocale fromLocale=null;
		SysLocale toLocale=null;
		
		fromLocale=CacheData.getLocaleByStation(from);
		toLocale=CacheData.getLocaleByStation(to);
		if(fromLocale==null){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.FROM_LOCALE_ERROR);
			return jsonMsg;
		}
		if(toLocale==null){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.TO_LOCALE_ERROR);
			return jsonMsg;
		}
		
		/*if(fromLocale.getId().equals(toLocale.getId())){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.FROM_LOCALE_EQUALS_TO_LOCALE);
			return jsonMsg;
		}*/
		List msgContents=msgContentDao.findMsgContents(fromLocale.getId(),toLocale.getId());
		jsonMsg.setStatus(JsonMsg.STATUS_SUCCESS);
		jsonMsg.setData(msgContents);
		return jsonMsg;
	}

	public Map findMsgContents(int pn, int size) {
		// TODO Auto-generated method stub
		if(pn<=0){
			pn=1;
		}
		return msgContentDao.findMsgContents(pn, size);
	}

	public JsonMsg deleteContent(Long[] ids) {
		// TODO Auto-generated method stub
		JsonMsg jsonMsg=new JsonMsg();
		
		if(ids==null||ids.length<0){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.NO_SELECT_CONTENT);
			return jsonMsg;
		}
		for(Long id:ids){
			MsgContent msgContent= msgContentDao.load(id);
			msgContent.setIsUse("0");
			msgContentDao.update(msgContent);
		}
		jsonMsg.setStatus(JsonMsg.STATUS_SUCCESS);
		return jsonMsg;
	}

	public JsonMsg updateContent(Long id, MsgContentVo msgContentVo) {
		// TODO Auto-generated method stub
		JsonMsg jsonMsg=new JsonMsg();
		MsgContent msgContent=msgContentDao.load(id);
		msgContent.setContent(msgContentVo.getContent());
		msgContent.setMobile(msgContentVo.getMobile());
		msgContent.setName(msgContentVo.getName());
		msgContent.setPhone(msgContentVo.getPhone());
		msgContent.setQq(msgContentVo.getQq());
		msgContent.setStep(msgContentVo.getStep());
		msgContent.setTitle(msgContentVo.getTitle());

		SysLocale fromLocale=null;
		SysLocale toLocale=null;
		if(msgContentVo.getFrom()==null||"".equals(msgContentVo.getFrom())){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.FROM_LOCALE_ERROR);
			return jsonMsg;
		}
		if(msgContentVo.getTo()==null||"".equals(msgContentVo.getTo())){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.TO_LOCALE_ERROR);
			return jsonMsg;
		}
		
		if(CacheData.cityCache.size()<=0){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysLocale.class);
			detachedCriteria.add(Restrictions.eq("type", "2"));
			CacheData.cityCache=sysLocaleDao.find(detachedCriteria);
		}
		if(CacheData.provinceCache.size()<=0){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysLocale.class);
			detachedCriteria.add(Restrictions.eq("type", "1"));
			CacheData.provinceCache=sysLocaleDao.find(detachedCriteria);
		}
		
		fromLocale=CacheData.getLocaleByStation(msgContentVo.getFrom());
		toLocale=CacheData.getLocaleByStation(msgContentVo.getTo());
		if(fromLocale==null){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.FROM_LOCALE_ERROR);
			return jsonMsg;
		}
		if(toLocale==null){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.TO_LOCALE_ERROR);
			return jsonMsg;
		}
		
		/*if(fromLocale.getId().equals(toLocale.getId())){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.FROM_LOCALE_EQUALS_TO_LOCALE);
			return jsonMsg;
		}*/
		//验证信息发布时间
		String pattern="^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}[.]?[0-9]?$";
		String msg;
		if(!CommonUtil.checkPattern(pattern,msgContentVo.getPublicationDate())){
			msg = ErrorMsg.PUBLICATION_DATE_ERROR;
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(msg);
			return jsonMsg;
		}else{
			msgContent.setPublicationDate(DateUtil.getDate(msgContentVo.getPublicationDate(), DateUtil.allDatePartern));
		}
		//验证有效期
		if(!(msgContentVo.getValidDate()==null||"".equals(msgContentVo.getValidDate()))){
			if(!CommonUtil.checkPattern(pattern,msgContentVo.getValidDate())){
				msg = ErrorMsg.PUBLICATION_DATE_ERROR;
				jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
				jsonMsg.setMsg(msg);
				return jsonMsg;
			}else{
				msgContent.setValidDate(DateUtil.getDate(msgContentVo.getValidDate(), DateUtil.allDatePartern));
			}
		}
		
		fromLocale=sysLocaleDao.get(fromLocale.getId());
		toLocale=sysLocaleDao.get(toLocale.getId());
		msgContent.setFromLocale(fromLocale);
		msgContent.setToLocale(toLocale);
		msgContentDao.update(msgContent);
		jsonMsg.setStatus(JsonMsg.STATUS_SUCCESS);
		return jsonMsg;
	}

	public JsonMsg getMsgContentsNums(String station) {
		// TODO Auto-generated method stub
		JsonMsg jsonMsg=new JsonMsg();
		
		if(StringUtils.isEmpty(station)){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.STATION_MSG_ERROR);
			return jsonMsg;
		}
			
		if(CacheData.cityCache.size()<=0){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysLocale.class);
			detachedCriteria.add(Restrictions.eq("type", "2"));
			CacheData.cityCache=sysLocaleDao.find(detachedCriteria);
		}
		if(CacheData.provinceCache.size()<=0){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysLocale.class);
			detachedCriteria.add(Restrictions.eq("type", "1"));
			CacheData.provinceCache=sysLocaleDao.find(detachedCriteria);
		}
		
		SysLocale locale=null;
		
		locale=CacheData.getLocaleByStation(station);
		if(locale==null){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.STATION_MSG_ERROR);
			return jsonMsg;
		}
		
		int sumCount=msgContentDao.getMsgContentsNums(locale.getId());
		jsonMsg.setStatus(JsonMsg.STATUS_SUCCESS);
		if(sumCount>0){
			jsonMsg.setMsg("为您找到从"+station+"出发的新货源数量"+sumCount+"条。");
		}else{
			jsonMsg.setMsg("对不起，暂时没有找到从"+station+"出发的新货源数量。\\n");
			jsonMsg.setMsg("请您尝试更换附近城市或省份作为出发地或目的地再次查询。");
		}
		return jsonMsg;
	}

	public JsonMsg saveMsgContentQq(String content, String nickname,
			String sourceName) {
		// TODO Auto-generated method stub
		String title=content.substring(0, content.indexOf(" "));
		String mobile=content.substring(content.lastIndexOf(" 电话")+4).replace(":", "").replace("：", "");
		String contentMsg=content.replace(title, "").replace(mobile, "").replace("电话", "").replace(":", "").replace("：", "");
		Date theDate=new Date();
		
		JsonMsg jsonMsg=new JsonMsg();
		Long id=null;
		MsgContent msgContent=new MsgContent();
		msgContent.setContent(contentMsg);
		msgContent.setCreateDate(theDate);
		msgContent.setIsUse("1");
		msgContent.setKeywords("");
		msgContent.setMobile(mobile);
		msgContent.setName("");
		msgContent.setPhone("");
		msgContent.setQq("");
		msgContent.setStatus("1");
		msgContent.setStep("1");
		msgContent.setTitle(title);
		msgContent.setType("");
		msgContent.setWeight(0);
		
		String[] place=title.split("到");
		String from=place[0];
		String to=place[1];
		SysLocale fromLocale=null;
		SysLocale toLocale=null;
		if(from==null||"".equals(from)){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.FROM_LOCALE_ERROR);
			return jsonMsg;
		}
		if(to==null||"".equals(to)){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.TO_LOCALE_ERROR);
			return jsonMsg;
		}
		
		if(CacheData.cityCache.size()<=0){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysLocale.class);
			detachedCriteria.add(Restrictions.eq("type", "2"));
			CacheData.cityCache=sysLocaleDao.find(detachedCriteria);
		}
		if(CacheData.provinceCache.size()<=0){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysLocale.class);
			detachedCriteria.add(Restrictions.eq("type", "1"));
			CacheData.provinceCache=sysLocaleDao.find(detachedCriteria);
		}
		
		fromLocale=CacheData.getLocaleByStation(from);
		toLocale=CacheData.getLocaleByStation(to);
		if(fromLocale==null){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.FROM_LOCALE_ERROR);
			return jsonMsg;
		}
		if(toLocale==null){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.TO_LOCALE_ERROR);
			return jsonMsg;
		}
		
		/*if(fromLocale.getId().equals(toLocale.getId())){
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg(ErrorMsg.FROM_LOCALE_EQUALS_TO_LOCALE);
			return jsonMsg;
		}*/
		//验证信息发布时间
		
		msgContent.setPublicationDate(theDate);
		
		fromLocale=sysLocaleDao.get(fromLocale.getId());
		toLocale=sysLocaleDao.get(toLocale.getId());
		msgContent.setFromLocale(fromLocale);
		msgContent.setToLocale(toLocale);
		
		DetachedCriteria detachedCriteria2 = DetachedCriteria.forClass(MsgSourcesUrl.class);
		detachedCriteria2.add(Restrictions.eq("sourcesHost",sourceName));
		List<MsgSourcesUrl> msgSourcesUrls= msgSourcesUrlDao.find(detachedCriteria2);
		if(!(msgSourcesUrls==null||msgSourcesUrls.size()<0)){
			msgContent.setMsgSourcesUrl(msgSourcesUrls.get(0));
			id =msgContentDao.save(msgContent);			
		}
		if(id!=null&&id>0){
			jsonMsg.setStatus(JsonMsg.STATUS_SUCCESS);
			jsonMsg.setMsg("@"+nickname+" 乐乐已经收录了这条货源信息啦！发送："+title+" 试试吧！");
			
		}else{
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
		}
		return jsonMsg;
	}
	
}
