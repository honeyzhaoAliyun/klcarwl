package com.klcarwl.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.klcarwl.model.MsgContent;
import com.klcarwl.model.UserInfo;
import com.klcarwl.util.DateUtil;

@Repository
public class MsgContentDaoImpl extends BaseDaoImpl<MsgContent, Long> implements MsgContentDao {

	public List findMsgContentByTNT(String title, String name, String mobile,Date publicationDate) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findMsgContents(String station) {
		// TODO Auto-generated method stub
		Date theDate=new Date();
		String theDateStr=DateUtil.DateFormat(theDate, DateUtil.allDatePartern);
		
		List paramt=new ArrayList();
		StringBuffer sql=new StringBuffer("SELECT b.ID,b.TITLE,to_char(b.CREATE_DATE,'yyyy-mm-dd hh24:mi:ss') AS CREATE_DATE,b.NAME,b.REMARK,b.STATUS,b.KEYWORDS,b.PHONE,b.STEP,b.QQ,");
		sql.append("b.STATION,b.CONTENT,b.MOBILE,to_char(b.VALID_DATE,'yyyy-mm-dd hh24:mi:ss') AS VALID_DATE,to_char(b.PUBLICATION_DATE,'yyyy-mm-dd hh24:mi:ss') AS PUBLICATION_DATE ");
		sql.append(" FROM (SELECT c.*,rownum row_num FROM (SELECT t.* FROM MSG_CONTENT t WHERE t.STATION like ? AND t.is_use='1' AND t.PUBLICATION_DATE>? ORDER BY t.publication_date desc) c ) b ");
		int random=(int) (Math.random()*2);		
		paramt.add(DateUtil.addAnyDay(theDate, -2));
		sql.append("WHERE b.row_num BETWEEN ? AND ?");		
		paramt.add("%"+station+"%");
		paramt.add(random);
		paramt.add(random+4);
		return this.getJdbcTemplate().queryForList(sql.toString(), paramt.toArray());
	}

	public List findMsgContents(Long from, Long to) {
		// TODO Auto-generated method stub
		Date theDate=new Date();
		String theDateStr=DateUtil.DateFormat(theDate, DateUtil.allDatePartern);
		
		List paramt=new ArrayList();
		StringBuffer sql=new StringBuffer("SELECT b.ID,b.TITLE,to_char(b.CREATE_DATE,'yyyy-mm-dd hh24:mi:ss') AS CREATE_DATE,b.NAME,b.REMARK,b.STATUS,b.KEYWORDS,b.PHONE,b.STEP,b.QQ,");
		sql.append("b.STATION,b.STATION,b.CONTENT,b.MOBILE,to_char(b.VALID_DATE,'yyyy-mm-dd hh24:mi:ss') AS VALID_DATE,to_char(b.PUBLICATION_DATE,'yyyy-mm-dd hh24:mi:ss') AS PUBLICATION_DATE ");
		sql.append(" FROM (");
		sql.append("SELECT c.*,rownum row_num FROM (SELECT mc.* FROM MSG_CONTENT mc,SYS_LOCALE sl_from,SYS_LOCALE sl_to ");
		sql.append("WHERE MC.FROM_LOCALE=sl_from.ID AND MC.is_use='1' ");
		sql.append("AND MC.TO_LOCALE=sl_to.ID ");
		sql.append("AND (sl_from.ID=? OR sl_from.PARENT=?) ");
		paramt.add(from);
		paramt.add(from);
		sql.append("AND (sl_to.ID=? OR sl_to.PARENT=?) ");
		paramt.add(to);
		paramt.add(to);
		sql.append("AND mc.PUBLICATION_DATE>? ORDER BY mc.publication_date desc ");
		paramt.add(DateUtil.addAnyDay(theDate, -2));
		sql.append(") c ) b ");
		sql.append("WHERE b.row_num BETWEEN ? AND ?");
		int random=(int) (Math.random()*2);	
		paramt.add(random);
		paramt.add(random+4);
		return this.getJdbcTemplate().queryForList(sql.toString(), paramt.toArray());
	}
	
	/**
	 * findMsgContentList
	 * @param from
	 * @param to
	 * @return List
	 */
	public List findMsgContentList(Long from, Long to) {
		// TODO Auto-generated method stub
		Date theDate=new Date();
		String theDateStr=DateUtil.DateFormat(theDate, DateUtil.allDatePartern);
		
		List paramt=new ArrayList();
		StringBuffer sql=new StringBuffer("SELECT b.ID,b.TITLE,b.FROM_LOCALE,b.TO_LOCALE,b.KEYWORDS,b.WEIGHT,to_char(b.CREATE_DATE,'yyyy-mm-dd hh24:mi:ss') AS CREATE_DATE,b.NAME,b.REMARK,b.STATUS,b.KEYWORDS,b.PHONE,b.STEP,b.QQ,");
		sql.append("b.STATION,b.STATION,b.CONTENT,b.MOBILE,to_char(b.VALID_DATE,'yyyy-mm-dd hh24:mi:ss') AS VALID_DATE,to_char(b.PUBLICATION_DATE,'yyyy-mm-dd hh24:mi:ss') AS PUBLICATION_DATE ");
		sql.append(" FROM (");
		sql.append("SELECT c.*,rownum row_num FROM (SELECT mc.* FROM MSG_CONTENT mc,SYS_LOCALE sl_from,SYS_LOCALE sl_to ");
		sql.append("WHERE MC.FROM_LOCALE=sl_from.ID AND MC.is_use='1' ");
		sql.append("AND MC.TO_LOCALE=sl_to.ID ");
		sql.append("AND (sl_from.ID=? OR sl_from.PARENT=?) ");
		paramt.add(from);
		paramt.add(from);
		sql.append("AND (sl_to.ID=? OR sl_to.PARENT=?) ");
		paramt.add(to);
		paramt.add(to);
		sql.append("AND mc.PUBLICATION_DATE>? ORDER BY mc.publication_date desc ");
		paramt.add(DateUtil.addAnyDay(theDate, -2));
		sql.append(") c ) b ");
		return this.getJdbcTemplate().queryForList(sql.toString(), paramt.toArray());
	}
	
	/**
	 * findMsgContentByUser
	 * @param from
	 * @param to
	 * @return List
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List findMsgContentByUser(UserInfo userInfo ) {
		
		List paramt=new ArrayList();
		StringBuffer sql=new StringBuffer("SELECT b.ID,b.TITLE,b.FROM_LOCALE,b.TO_LOCALE,b.KEYWORDS,b.WEIGHT,to_char(b.CREATE_DATE,'yyyy-mm-dd hh24:mi:ss') AS CREATE_DATE,b.NAME,b.REMARK,b.STATUS,b.KEYWORDS,b.PHONE,b.STEP,b.QQ,");
		sql.append("b.STATION,b.STATION,b.CONTENT,b.MOBILE,to_char(b.VALID_DATE,'yyyy-mm-dd hh24:mi:ss') AS VALID_DATE,to_char(b.PUBLICATION_DATE,'yyyy-mm-dd hh24:mi:ss') AS PUBLICATION_DATE ");
		sql.append(" FROM (");
		sql.append("SELECT c.*,rownum row_num FROM (SELECT mc.* FROM MSG_CONTENT mc,SYS_LOCALE sl_from,SYS_LOCALE sl_to ");
		sql.append("WHERE MC.FROM_LOCALE=sl_from.ID AND MC.is_use='1' ");
		sql.append("AND MC.TO_LOCALE=sl_to.ID ");
		sql.append("AND MC.USERID=? ");
		paramt.add(userInfo.getId().toString());
		sql.append(") c ) b ");
		return this.getJdbcTemplate().queryForList(sql.toString(), paramt.toArray());
	}

	public Map<String, Object> findMsgContents(int pn, int size) {
		// TODO Auto-generated method stub
		Map<String,Object> pageMap=new HashMap<String, Object>();
		List params=new ArrayList();		
		StringBuffer sql=new StringBuffer("SELECT mc.ID,mc.TITLE,mc.NAME,mc.MOBILE,mc.QQ,mc.STATUS,mc.PUBLICATION_DATE,mc.CREATE_DATE,mc.STEP,mc.PHONE,msu.SOURCES_NAME  ");		
		StringBuffer sqlBody=new StringBuffer("FROM MSG_CONTENT mc,MSG_SOURCES_URL msu WHERE mc.URL_ID=msu.ID AND MC.is_use='1' ");
		
		StringBuffer sqlCount=new StringBuffer("SELECT count(*) ");
		sqlCount.append(sqlBody);
		
		int sumItems=(Integer)this.getJdbcTemplate().queryForInt(sqlCount.toString(),params.toArray());
		if(size<=0){
			size =10;
		}
		int pageCount = sumItems/size;
		if (sumItems % size > 0) {
			pageCount ++;
		}
		if(pn>pageCount){
			pageMap.put("pageNo", 1);
			pageMap.put("pageSize", size);	
			pageMap.put("sumPage", 0);
			pageMap.put("list",null);
			pageMap.put("sumItems", sumItems);
			return pageMap;
		}
		sql.append(sqlBody);
		@SuppressWarnings("rawtypes")
		List list= sqlQueryForList(sql.toString(), params.toArray(), pn, size);
		
		pageMap.put("pageNo",pn);
		pageMap.put("pageSize", size);	
		pageMap.put("sumPage",pageCount);
		pageMap.put("list",list);
		pageMap.put("sumItems", sumItems);
		return pageMap;
	}

	public int getMsgContentsNums(Long from) {
		// TODO Auto-generated method stub
		Date theDate=new Date();
		List paramt=new ArrayList();		
		
		StringBuffer sql=new StringBuffer("SELECT count(mc.id) FROM MSG_CONTENT mc,SYS_LOCALE sl_from ");
		sql.append("WHERE MC.FROM_LOCALE=sl_from.ID AND MC.is_use='1' ");
		sql.append("AND (sl_from.ID=? OR sl_from.PARENT=?) ");
		paramt.add(from);
		paramt.add(from);
		sql.append("AND mc.PUBLICATION_DATE>? ");
		paramt.add(DateUtil.addAnyDay(theDate, -2));
		return this.getJdbcTemplate().queryForInt(sql.toString(), paramt.toArray());
	}
	

}
