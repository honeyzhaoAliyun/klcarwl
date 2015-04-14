package com.klcarwl.cache;

import java.util.ArrayList;
import java.util.List;

import com.klcarwl.model.SysLocale;


public class CacheData{
	public static List<SysLocale> cityCache=new ArrayList<SysLocale>();
	public static List<SysLocale> provinceCache=new ArrayList<SysLocale>();
	
	public static SysLocale getLocaleByStation(String station){
		for(SysLocale sysLocale:cityCache){
			if(station.indexOf(sysLocale.getName())>=0){
				return sysLocale;
			}
		}
		for(SysLocale sysLocale:provinceCache){
			if(station.indexOf(sysLocale.getName())>=0){
				return sysLocale;
			}
		}
		return null;
	}
}
