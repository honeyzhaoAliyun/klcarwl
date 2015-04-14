package com.klcarwl.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
	public static Object ifNull(Object object){
		return object==null?"":object;
	}
	
	public static boolean checkPattern(String pattern,String str){
		str=str==null?"":str;
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		return m.find();
	}	
}
