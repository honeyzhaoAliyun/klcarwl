package com.klcarwl.domain;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import com.klcarwl.util.DateUtil;

public class StringToDateConverter implements Converter<String,Date>{
	public Date convert(String source) {
		Date date = null;
		if(source != null){
			date=DateUtil.getDate(source, "yyyy-MM-dd");
		}
		return date;
	}
}
