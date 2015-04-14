package com.klcarwl.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.klcarwl.model.Message;

@Repository
public class MessageDaoImpl extends BaseDaoImpl<Message, Long> implements MessageDao {
	
	public Map findMessages(int pn, int size,Long uid) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> pageMessages = new HashMap<String, Object>();
		List<Object> param = new ArrayList<Object>();
		// 总数据量
		int sumItem = 0;
		// 总页数
		int sumPage = 0;
		// 开始数据条目
		int startPagItem = 0;
		// 结束数据条目
		int endPagItem = 0;

		StringBuffer sqlCount = new StringBuffer("select count(m.id) ");
		sqlCount.append("FROM message m ");
		sqlCount.append(" WHERE m.isUse=1 ");
		if(!(uid==null||uid==0)){
			sqlCount.append(" AND m.userInfo_id=? ");
			param.add(uid);
		}
		sumItem=this.getJdbcTemplate().queryForInt(sqlCount.toString(),param.toArray());

		sumPage = ((sumItem - 1) / size) + 1;
		if (pn > sumPage && sumPage != 0) {
			throw new Exception("当前页数超过了总页数");
		}
		startPagItem = (pn - 1) * size;
		param.add(startPagItem);
		param.add(size);
		StringBuffer sql = new StringBuffer("SELECT * FROM message ");
		if(!(uid==null||uid==0)){
			sql.append(" WHERE userInfo_id=? ");
		}
		sql.append(" limit ?,? ");
		List list = this.getJdbcTemplate().queryForList(sql.toString(),
				param.toArray());
		pageMessages.put("sumItem", sumItem);
		pageMessages.put("sumPage", sumPage);
		pageMessages.put("list", list);
		return pageMessages;
	}

}
