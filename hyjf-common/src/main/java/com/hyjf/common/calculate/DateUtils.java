/**
 * Description:日期工具类
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: b
 * @version: 1.0
 * Created at: 2015年11月30日 上午10:59:23
 * Modification History:
 * Modified by :
 */

package com.hyjf.common.calculate;

import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;

import com.hyjf.common.util.GetDate;

/**
 * @author b
 */

public class DateUtils {

    
    /**
     * 获取当前时间前一天
     * @return
     */
    public static String getBeforeDate() {
        
        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        dBefore = calendar.getTime();   //得到前一天的时间

        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); //设置时间格式
        String year = sdf.format(dBefore);    //格式化前一天的年份
        return year;
    }
    /**
     * 获取当前时间前一天的年份
     * @return
     */
    public static String getBeforeYear() {
        
        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        dBefore = calendar.getTime();   //得到前一天的时间

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy"); //设置时间格式
        String year = sdf.format(dBefore);    //格式化前一天的年份
        return year;
    }
    
    /**
     * 获取当前时间前一天的年份+MMDD时间戳
     * @return
     */
    public static int getBeforeYearTime(String cendt) {
        
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);// 前一天
        date = calendar.getTime();
        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
        String year = sdfYear.format(date);

        // 将时间戳转换为时间
        String res= year+cendt;
        // 加上年 转成时间戳
        long ts = GetDate.strYYYYMMDDTimestamp(res);
        String result = String.valueOf(ts);
        return Integer.valueOf(result);
        
    }
    
    /**
     * 获取当前时间前一天的月份
     * @return
     */
    public static String getBeforeMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        dBefore = calendar.getTime();   //得到前一天的时间

        String month = sdf.format(dBefore);
        return month;
    }
    
    /**
     * 获取当前时间前一天的几号
     * @return
     */
    public static String getBeforeDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        dBefore = calendar.getTime();   //得到前一天的时间
        String day = sdf.format(dBefore);
        return day;
    }
	/**
	 * 获取现在时间
	 *
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static String getNowDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	/**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static String getNowDateByHH(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
	
	/**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyyMMdd
     */
    public static String getNowDateOfDay() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取前一天时间
     *
     * @return 返回时间类型 yyyyMMdd
     */
    public static String getBeforeDateOfDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        dBefore = calendar.getTime();   //得到前一天的时间
        String day = sdf.format(dBefore);
        return day;
    }

	/**
	 * 判断是否闰年
	 *
	 * @param year
	 *            年份
	 * @return true是闰年（2月29日），false是平年（2月29天）
	 */
	public static boolean isLeapYear(int year) {
		if (year % 100 == 0) {
			if (year % 400 == 0) {
				return true;
			}
		} else {
			if (year % 4 == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取还款日期 month.等额本息（每个月还的本息和一样） end.按月计息，到期还本还息
	 * ;endmonth.先息后本（每月只还利息，最后一个月还本金）; endday.按天计息，到期还本还息;
	 * principal等额本金（每月本金一样，利息不一样），新增数据库没有;
	 *
	 * @param repayType
	 *            还款类型
	 * @param provideDate
	 *            放款日期
	 * @param borrowMonths
	 *            还款月数，月标时必填，如果是日标，输入0即可
	 * @param borrowMonths
	 *            还款月数，日标时必填，如果是月标，输入0即可
	 * @return 单期还款，只返回一个日期值，如"2015-01-31"；如果是多期还款，则返回逗号分隔的日期串儿,
	 *         如"1951-06-09,1951-07-09,1951-08-09"
	 */
	public static Integer getRepayNextDate(String repayType, Date provideDate, int borrowMonths, int borrowDays) {
		if (StringUtils.isBlank(repayType) || provideDate == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(provideDate);
		if ("end".equals(repayType)) {
			//calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
			calendar.add(Calendar.MONTH, borrowMonths);
			return Integer.valueOf(String.valueOf(calendar.getTime().getTime() / 1000));
		}
		if ("endday".equals(repayType)) {
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + borrowDays);
			return Integer.valueOf(String.valueOf(calendar.getTime().getTime() / 1000));
		}
		if ("endmonth".equals(repayType) || "month".equals(repayType) || "principal".equals(repayType)) {
			Integer repayDate = 0;
			//calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
//			for (int i = 1; i <= borrowMonths; i++) {
//				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
//				repayDate = Integer.valueOf(String.valueOf(calendar.getTime().getTime() / 1000));
//				break;
//			}
			calendar.add(Calendar.MONTH, borrowMonths);
			repayDate = (int)(calendar.getTime().getTime() / 1000);

			return repayDate;
		}

		return null;
	}

	/**
	 * 获取还款日期 month.等额本息（每个月还的本息和一样） end.按月计息，到期还本还息
	 * ;endmonth.先息后本（每月只还利息，最后一个月还本金）; endday.按天计息，到期还本还息;
	 * 7等额本金（每月本金一样，利息不一样），新增数据库没有;
	 *
	 * @param repayType
	 *            还款类型
	 * @param provideDate
	 *            放款日期
	 * @param borrowMonths
	 *            还款月数，月标时必填，如果是日标，输入0即可
	 * @param borrowMonths
	 *            还款月数，日标时必填，如果是月标，输入0即可
	 * @return 单期还款，只返回一个日期值，如"2015-01-31"；如果是多期还款，则返回逗号分隔的日期串儿,
	 *         如"1951-06-09,1951-07-09,1951-08-09"
	 */
	public static Integer getRepayDate(String repayType, Date provideDate, int borrowMonths, int borrowDays) {
		if (StringUtils.isBlank(repayType) || provideDate == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(provideDate);
		if ("end".equals(repayType)) {
			//calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
			calendar.add(Calendar.MONTH, borrowMonths);
			return Integer.valueOf(String.valueOf(calendar.getTime().getTime() / 1000));
		}
		if ("endday".equals(repayType)) {
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + borrowDays);
			return Integer.valueOf(String.valueOf(calendar.getTime().getTime() / 1000));
		}
		if ("endmonth".equals(repayType) || "month".equals(repayType) || "principal".equals(repayType)) {
			Integer repayDate = 0;
			//calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
//			for (int i = 1; i <= borrowMonths; i++) {
//				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
//			}
			calendar.add(Calendar.MONTH, borrowMonths);
			repayDate = Integer.valueOf(String.valueOf(calendar.getTime().getTime() / 1000));
			return repayDate;
		}

		return null;
	}

	/**
	 * 获取还款日期 month.等额本息（每个月还的本息和一样） end.按月计息，到期还本还息
	 * ;endmonth.先息后本（每月只还利息，最后一个月还本金）; endday.按天计息，到期还本还息;
	 * principal等额本金（每月本金一样，利息不一样），新增数据库没有;
	 *
	 * @param repayType
	 *            还款类型
	 * @param provideDate
	 *            放款日期
	 * @param borrowMonths
	 *            还款月数，月标时必填，如果是日标，输入0即可
	 * @param borrowMonths
	 *            还款月数，日标时必填，如果是月标，输入0即可
	 * @return 单期还款，只返回一个日期值，如"2015-01-31"；如果是多期还款，则返回逗号分隔的日期串儿,
	 *         如"1951-06-09,1951-07-09,1951-08-09"
	 */
	public static String getRepayDateString(String repayType, Date provideDate, int borrowMonths, int borrowDays) {
		if (StringUtils.isBlank(repayType) || provideDate == null) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(provideDate);
		if ("end".equals(repayType)) {
			//calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
			calendar.add(Calendar.MONTH, borrowMonths);
			return df.format(calendar.getTime());
		}
		if ("endday".equals(repayType)) {
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + borrowDays);
			return df.format(calendar.getTime());
		}
		if ("endmonth".equals(repayType) || "month".equals(repayType) || "principal".equals(repayType)) {
			String repayDate = "";
			//calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
//			for (int i = 1; i <= borrowMonths; i++) {
//				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
//				repayDate = repayDate + df.format(calendar.getTime()) + ",";
//			}
			calendar.add(Calendar.MONTH, borrowMonths);
			repayDate = repayDate + df.format(calendar.getTime()) + ",";
			return repayDate;
		}

		return null;
	}

	public static String getRepayEachTime(Integer millis) {
		Date date = new Date(millis * 1000);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str[] = sdf.format(date).split("[-]");
		return str[1];
	}
	
	/**
     * timeEnd比timeStar多的天数
     * @param timeStar    
     * @param timeEnd
     * @return    
     */
	public static long differentDays(long timeStar,long timeEnd) {
	    Long c = 0L;
        c = timeEnd-timeStar;
	    long d = c/1000/60/60/24;//天
	    return d;
    }

	/**
     * timeEnd比timeStar多的天数
     * @param timeStar-秒数
     * @param timeEnd-秒数
     * @return
     */
    public static long differentDaysByString(String timeEnd,String timeStar) {
        Long c = 0L;
        if(StringUtils.isNotBlank(timeStar) && StringUtils.isNotBlank(timeEnd) ){
            c = Long.valueOf(timeEnd)-Long.valueOf(timeStar);
        }else if(StringUtils.isBlank(timeStar)){
            c = Long.valueOf(timeEnd)-GetDate.getNowTime10();
        }else if(StringUtils.isBlank(timeEnd)){
            c = GetDate.getNowTime10()-Long.valueOf(timeStar);
        }
        long d = c/60/60/24;//天
        return d;
    }
	/**
     * 整数(秒数)转换为时分秒格式
     * @param timeStar
     * @return
     */
	public static String SToHMSStr(long value) {
		String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (value <= 0)
            return "00时00分";
        else {
        	int intValue=(int)value;
            minute = intValue / 60;
            if (intValue<60) {
            	return "一分钟之后";
			}else if (minute < 60) {
				timeStr =unitFormat(minute) + "分";
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
//                second = intValue - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + "时" + unitFormat(minute) + "分";
            }
        }
        return timeStr;
    }
	/**
	 * 设置时间加当前系统时间（小时）
	 * @param loginLockTime
	 * @return
	 */
	public static Date nowDateAddDate(int loginLockTime){
		Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, loginLockTime);
        return calendar.getTime();
	}
	public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }


	   public static void main(String[] args) throws ParseException {

	        /*// 按月计息，到期还本还息
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
	        String dstr = "2015-12-30";
	        java.util.Date date = sdf.parse(dstr);
	        // String repayDateMonths = getRepayDate("3", date, 1, 0);
	        // System.out.println(repayDateMonths);
	        //
	        // // 按天计息，到期还本还息
	        // String repayDateDays = getRepayDate("5", date, 0, 45);
	        // System.out.println(repayDateDays);
	        // 等额本息（每个月还的本息和一样）
	        // 先息后本（每月只还利息，最后一个月还本金）
	        // 等额本金（每月本金一样，利息不一样），新增数据库没有
	        String repayDateM = getRepayDateString("principal", date, 3, 3);
	        System.out.println(repayDateM);
	        Integer repayDateMI = getRepayDate("principal", date, 3, 3);
	        System.out.println(repayDateMI);
	        System.out.println(getRepayEachTime(1456761600));
	        // repayDateM = getRepayNextDate("1", date, 3, 0);

	        Calendar cal = Calendar.getInstance();
	        cal.setTimeInMillis(1421568000000L);
	         System.out.println("aa:" + getRepayNextDate("principal",GetDate.getDate(1421568000*1000L), 2, 0));


		String date1 = getNowDate();
		System.out.println("date1:"+date1);*/
	    //String string[] = "http://test.hyjf.com:8080/hyjf-admin/manager/borrow/borrowcommon/insertAction?sss".split("\\?");
	       /*long time = getBeforeYearTime();
	       ;*/

	   }

}
