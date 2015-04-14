package com.klcarwl.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.klcarwl.dao.SysLocaleDao;
import com.klcarwl.model.SysLocale;
import com.klcarwl.util.JsonMsg;

@Service
public class SysLocaleServiceImpl extends BaseServiceImpl<SysLocale, Long>
		implements SysLocaleService {
	@Resource
	private SysLocaleDao sysLocaleDao;
	@Resource
	public void setBaseDao(SysLocaleDao sysLocaleDao) {
		super.setBaseDao(sysLocaleDao);
	}
	/**
	 * 检测地区名称是否合法
	 * @param name
	 * @return
	 */
	public JsonMsg checkLocaleName(String name) {
		// TODO Auto-generated method stub	
		JsonMsg jsonMsg=new JsonMsg();
		if(StringUtils.isEmpty(name)){			
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg("地区名称不能为空");
			return jsonMsg;
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SysLocale.class);
		name=name.replace("省", "");
		name=name.replace("市", "");
		name=name.replace("古", "");
		detachedCriteria.add(Restrictions.eq("name",name));
		List<SysLocale> sysLocales= sysLocaleDao.find(detachedCriteria);
		
		if(sysLocales.size()>0){
			jsonMsg.setStatus(JsonMsg.STATUS_SUCCESS);
			jsonMsg.setData(sysLocales.size());
		}else{
			jsonMsg.setStatus(JsonMsg.STATUS_ERROR);
			jsonMsg.setMsg("不是有效的地区名称");
		}
		return jsonMsg;
	}
	
}
