package com.klcarwl.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.klcarwl.model.UserInfo;
import com.klcarwl.util.Pager;

@Repository
public class UserInfoDaoImpl extends BaseDaoImpl<UserInfo, Long> implements UserInfoDao {

	public UserInfo getUserInfoByUsername(String username) {
		// TODO Auto-generated method stub
		String hql = "from UserInfo userInfo where userInfo.isUse=1 AND lower(userInfo.userName) = lower(?)";
		return (UserInfo)queryForObject(hql, new Object[]{username});
	}

	public Pager findUserInfo(Pager pager, String username,
			BigDecimal minaccount, BigDecimal maxaccount) {
		// TODO Auto-generated method stub
		List param = new ArrayList<Object>();
		if (pager == null) {
			pager = new Pager();
		}
		if (pager.getPageSize() == null) {
			pager.setPageSize(UserInfo.DEFAULT_LIST_PAGE_SIZE);
		}
		// 查询总数据
		StringBuffer hqlCount = new StringBuffer(
				"SELECT count(*) FROM UserInfo ui,UserAccount ua ");
		hqlCount.append(" WHERE ua.userInfo=ui AND ui.isUse=1 ");		
		if (!(username == null||"".equals(username))) {
			hqlCount.append(" AND ui.username like ? ");
			param.add("%"+username+"%");
		}
		if (!(minaccount == null ||minaccount.equals(0))) {
			hqlCount.append(" AND ua.balance>=? ");
			param.add(minaccount);
		}
		if (!(maxaccount == null ||maxaccount.equals(0))) {
			hqlCount.append(" AND ua.balance<=? ");
			param.add(maxaccount);
		}
		// 总数据条数
		Object obj = queryForObject(hqlCount.toString(), param.toArray());
		int totalCount = Integer.parseInt(obj.toString());
		// 数据分页
		StringBuffer hql = new StringBuffer(
				"SELECT ui,ua FROM UserInfo ui,UserAccount ua  ");
		hql.append(" WHERE ua.userInfo=ui AND ui.isUse=1 ");
		if (!(username == null||"".equals(username))) {
			hql.append(" AND ui.username like ? ");
		}
		if (!(minaccount == null ||minaccount.equals(0))) {
			hql.append(" AND ua.balance>=? ");
		}
		if (!(maxaccount == null ||maxaccount.equals(0))) {
			hql.append(" AND ua.balance<=? ");
		}
		hql.append(" ORDER BY ui.id DESC ");
		pager.setTotalCount(totalCount);
		// 总页数
		int pageCount = pager.getPageCount();
		int startPagItem = (pager.getPageNumber() - 1) * pager.getPageSize();
		List list = queryForList(hql.toString(), param.toArray(), startPagItem,
				pager.getPageSize());
		pager.setList(list);
		return pager;
	}

	

}
