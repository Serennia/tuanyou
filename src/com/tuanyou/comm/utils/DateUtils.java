package com.tuanyou.comm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	public static String TIME = "yyyy-MM-dd HH:mm:ss";
	public static String OTHER_TIME="yyyy/MM/dd HH:mm:ss";
	public static String DATE ="yyyy-MM-dd";
	public static String OTHER_DATE="yyyy/MM/dd";
	public static String SAMPLE_TIME="yyyyMMddHHmmss";
	public static String SAMPLE_DATE="yyyyMMdd";
	public static String HOUR_TIME = "yyyyMMddHH";
	public static String SAMPLE_Month="yyyy-MM";
	
	public static SimpleDateFormat getTimeFormat(){ 
		return new SimpleDateFormat(TIME);
	}
	public static SimpleDateFormat getOtherTimeFormat() {
		return new SimpleDateFormat(OTHER_TIME);
	}
	public static SimpleDateFormat getOtherDateFormat() {
		return new SimpleDateFormat(OTHER_DATE);
	}
	public static SimpleDateFormat getDateFormat() {
		return new SimpleDateFormat(DATE);
	}
	public static SimpleDateFormat getSampleTimeFormat() {
		return new SimpleDateFormat(SAMPLE_TIME);
	}
	public static SimpleDateFormat getSampleDateFormat() {
		return new SimpleDateFormat(SAMPLE_DATE);
	}
	public static SimpleDateFormat getHourDateFormat(){
		return new SimpleDateFormat(HOUR_TIME);
	}
	public static SimpleDateFormat getMonthDateFormat(){
		return new SimpleDateFormat(SAMPLE_Month);
	}
	public static Date toDateYYYYmmdd(String day){
		try {
		SimpleDateFormat sdf =getSampleDateFormat();
		Date d= sdf.parse(day);
			return d;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 计算N个月后的日期
	 */
	public static String getNewDayByAddMonth(String day,int month){
		try {
			SimpleDateFormat sdf =getDateFormat();
			Date d1 = sdf.parse(day);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d1);
			System.out.println(sdf.format(calendar.getTime()));
			calendar.add(Calendar.MONTH, month);
			String returnday=sdf.format(calendar.getTime());
			System.out.println(returnday);
			return returnday;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date getNewDayByAddMonth(Date day,int month){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(day);
		calendar.add(Calendar.MONTH, month);
		Date d=calendar.getTime();
		System.out.println(d);
		return d;
	}
	
	public static int return_thisyear(){
		  Calendar cal = Calendar.getInstance();
		  int year = cal.get(Calendar.YEAR);
		  return year;
	}
	public static String getBeforeMonth(){
		SimpleDateFormat sdf =getMonthDateFormat();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		String BeforeMonth=sdf.format(calendar.getTime());
		System.out.println(BeforeMonth);
		return BeforeMonth;
	}
	public static void main(String[] args) {
		getNewDayByAddMonth("2014-05-03",25);
		getNewDayByAddMonth(new Date(),1);
		return_thisyear();
		getBeforeMonth();
	}
}
