package com.klcarwl.service;

import com.klcarwl.model.SysLocale;
import com.klcarwl.util.JsonMsg;

public interface SysLocaleService extends BaseService<SysLocale, Long> {
	/**
	 * 检测地区名称是否合法
	 * @param name
	 * @return
	 */
	public JsonMsg checkLocaleName(String name);
}
