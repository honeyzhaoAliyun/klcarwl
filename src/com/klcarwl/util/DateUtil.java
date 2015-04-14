package com.klcarwl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {
	public static final String allDatePartern="yyyy-MM-dd HH:mm:ss";
	public static final String allDateParternNoSen="yyyy-MM-dd HH:mm";
	public static final String datePartern="yyyy-MM-dd";
	public static String DateFormat(Date date, String partern) {
		if(date == null){
			return "";
		}
		SimpleDateFormat sf = new SimpleDateFormat(partern);
		return sf.format(date);
	}

	public static Date getDate(String d,String partern){
		if(d == null){
			return null;
		}
		SimpleDateFormat sf = new SimpleDateFormat(partern);
		try {
			return sf.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 取得两个时间之间间隔天数
	 * 
	 * @param startday
	 * @param endday
	 */
	public static int getIntervalDays(Date startday, Date endday) {
		// 分别得到两个时间的毫秒数
		if(startday == null||endday == null){
			//默认租车时间为1天
			return 1;
		}
			long sl = startday.getTime();
			long el = endday.getTime();
	
			long ei = el - sl;
			// 根据毫秒数计算间隔天数，再除以30
			return (int) (ei / (1000 * 60 * 60 * 24));
		
	}
	public static Date addAnyDay(Date startDate,int day){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(startDate);
		gc.add(Calendar.DATE, day);
		return gc.getTime();
	}
	public static Date addAnyHours(Date startDate,int hours){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(startDate);
		gc.add(Calendar.HOUR, hours);
		return gc.getTime();
	}
	public static Date addAnyMinutes(Date startDate,int minutes){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(startDate);
		gc.add(Calendar.MINUTE, minutes);
		return gc.getTime();
	}
	/**
	 * 获取两个时间直接所隔的小时数
	 * @param startday
	 * @param endday
	 * @return
	 */
	public static int getIntervalHours(Date startday, Date endday,int minuteHour) {		
		long sl = startday.getTime();
		long el = endday.getTime();
		long ei = el - sl;
		if(ei<=0){
			return 0;
		}
		// 根据毫秒数计算间隔天数，再除以30
		if((ei%(1000 * 60 * 60))>=minuteHour*60*1000){
			return (int)(ei/(1000 * 60 * 60))+1;
		}else{
			return (int)(ei/(1000 * 60 * 60));
		}
				
	}
	/**
	 * 取得两个时间之间间隔天数
	 * 
	 * @param startday
	 * @param endday
	 */
	public static Map getIntervalDaysAndHours(Date startday, Date endday,int hourDay,int minuteHour){
		// 分别得到两个时间的毫秒数
		Map map=new HashMap<String,Integer>();
		int day;
		int hour;
		if(startday == null||endday == null){
			//默认租车时间为1天
			day=1;
			hour=0;
		}
		long sl = startday.getTime();
		long el = endday.getTime()-(hourDay*60*60*1000)-(minuteHour*60*1000);	
		long el2=endday.getTime()-(minuteHour*60*1000);
		long ei = el - sl-1;
		long ei2=el2-sl-1;
			// 根据毫秒数计算间隔天数，再除以30
		day=(int)(ei / (1000 * 60 * 60 * 24))+1;
		hour=(int)((ei2 % (1000 * 60 * 60 * 24))/(1000 * 60 * 60))+1;
		
		Calendar cStart = Calendar.getInstance();
		cStart.setTime(startday);
		int cDay=cStart.get(Calendar.DATE);
		cStart.set(Calendar.DATE, cDay + 1);
		if(startday.after(endday)){
			map.put("day", 0);
		}else{
			map.put("day", day);
		}
		if(endday.before(cStart.getTime())){
			map.put("hour", 0);
		}else{			
			if(hour>hourDay){
				map.put("hour", 0);
			}else{
				map.put("hour", hour);
			}
		}
		
		return map;
		
	}
	/**
	 * 判断当前日期是星期几
	 * @param DateStr
	 * @return
	 */
	public static String getWeekDay(Date dateStr){
	      SimpleDateFormat formatYMD=new SimpleDateFormat("yyyy-MM-dd");
	      SimpleDateFormat formatD=new SimpleDateFormat("E");//"E"表示"day in week"
	      String weekDay="";
	      try{
	         weekDay=formatD.format(dateStr);
	      }catch (Exception e){
	         e.printStackTrace();
	      }
	     return weekDay;
	 }
	public static String getIntervalTime(Date startday, Date endday) {
		// 分别得到两个时间的毫秒数
		long sl = startday.getTime();
		long el = endday.getTime();

		long ei = el - sl;
		// 根据毫秒数计算间隔天数，再除以30
		int day = (int) (ei / (1000 * 60 * 60 * 24));
		long left = ei % (1000 * 60 * 60 * 24);
		int hour = (int) (left / (1000 * 60 * 60));
		left = left % (1000 * 60 * 60);
		int minute = (int) (left / (1000 * 60));
		return day+"天"+hour+"小时"+minute+"分钟";
	}
	public static int getIntervalTimeMinute(Date startday, Date endday) {
		// 分别得到两个时间的毫秒数
		long sl = startday.getTime();
		long el = endday.getTime();
		long ei = el - sl;
		int minute = (int) (ei / (1000 * 60));
		return minute;
	}
	public static int getIntervalTimeHour(Date startday, Date endday) {
		// 分别得到两个时间的毫秒数
		long sl = startday.getTime();
		long el = endday.getTime();
		long ei = el - sl;
		int minute = (int) (ei / (1000 * 60 * 60));
		return minute;
	}
	public static long getIntervalTimeLong(Date startday, Date endday) {
		// 分别得到两个时间的毫秒数
		long sl = startday.getTime();
		long el = endday.getTime();
		long ei = el - sl;
		return ei;
	}

	/**
	 * 取得两个时间之间间隔月数
	 * 
	 * @param startday
	 * @param endday
	 */
	public static int getIntervalMonths(Date startday, Date endday) {
		return (int) (getIntervalDays(startday, endday) / 30);
	}

	/**
	 * 得到给定日期所在月份的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDateOfMonth(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(Calendar.MONTH, 1);
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), 1);
		gc.add(Calendar.DAY_OF_MONTH, -1);
		return gc.getTime();
	}

	/**
	 * 得到给定日期所在月份的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDateOfMonth(Date date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), 1);
		return gc.getTime();
	}

	public static Date getNextDate(Date date) {
		Date result = new Date();
		result.setTime((date.getTime() / 1000 + 60 * 60 * 24) * 1000);
		return result;
	}

	public static Date getIntervalMonthDate(Date startDate, int intervalMonth) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(startDate);
		gc.add(Calendar.MONTH, intervalMonth);
		System.out.println(gc.getTime());
		return gc.getTime();
	}
	public static Date addOneDay(Date startDate){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(startDate);
		gc.add(Calendar.DATE, 1);
		return gc.getTime();
	}
	public static Date minusOneDay(Date startDate){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(startDate);
		gc.add(Calendar.DATE, -1);
		return gc.getTime();
	}
	/**
	 * 判断当前日期是星期几
	 * @param DateStr
	 * @return
	 */
	public static int getWeekDayInt(Date dateStr){	      
		Calendar cStart = Calendar.getInstance();		
		cStart.setTime(dateStr);		
		int weekDay =cStart.get(Calendar.DAY_OF_WEEK);	
		if(weekDay==1){
			return 6;
		}else{
			return weekDay-2;			
		}
	    
	 }
	public static int getWeekEnds(Date startday, Date endday){
		
		int x=getIntervalDays(startday, endday);		
		int a=getWeekDayInt(startday);
		int b=getWeekDayInt(endday);
		//
		int days=(x/7)*2;
		int otherDays=0;
		if(0<=a&&a<=b&&b<=4){
			otherDays=0;
		}
		if(0<=a&&a<=4&&b>=4){
			otherDays=b-4;
		}
		if(4<=a&&a<=b){
			otherDays=b-a;
		}
		if(b<=a&&a<=4){
			otherDays=2;
		}
		if(b<=4&&a>=4){
			otherDays=6-a;
		}
		if(4<=b&&b<=a){
			otherDays=2+b-a;
		}
		return days+otherDays;
	}
	
	public static int getWeekEnds1(Date startday, Date endday){
		
		int x=getIntervalDaysAdd1(startday, endday);		
		int a=getWeekDayInt(startday);
		int b=getWeekDayInt(endday);
		//
		int days=(x/7)*2;
		int otherDays=0;
		if(0<=a&&a<=b&&b<=4){
			otherDays=0;
		}else if(0<=a&&a<=4&&b>=4){
			otherDays=b-4;
		}else if(4<=a&&a<=b){
			otherDays=b-a;
		}else if(b<=a&&a<=4){
			otherDays=2;
		}else if(b<=4&&a>=4){
			otherDays=6-a;
		}else if(4<=b&&b<=a){
			otherDays=2+b-a;
		}
		return days+otherDays;
	}
	/**
	 * 两个日期间天数
	 * @param startday
	 * @param endday
	 * @return
	 */
	public static int getIntervalDaysAdd1(Date startday, Date endday) {
		// 分别得到两个时间的毫秒数
		if(startday == null||endday == null){
			//默认租车时间为1天
			return 1;
		}
			long sl = startday.getTime();
			long el = endday.getTime();
	
			long ei = el - sl;
			// 根据毫秒数计算间隔天数，再除以30
			return (int) (ei / (1000 * 60 * 60 * 24))+1;
		
	}
	
	public static void main(String[] args) {
		/*Map haha=getIntervalDaysAndHours(getDate("2012-06-11 09:00", "yyyy-MM-dd HH:mm"),
				getDate("2012-06-02 11:01", "yyyy-MM-dd HH:mm"),3,0);
		System.out.println(haha.get("day")+":天");
		System.out.println(haha.get("hour")+":小时");*/
		System.out.println(getWeekEnds1(getDate("2013-04-12", "yyyy-MM-dd"),getDate("2013-04-14", "yyyy-MM-dd")));
	}
}

