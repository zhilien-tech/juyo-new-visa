/**
 * FormatDateUtil.java
 * com.linyun.airline.admin.order.inland.util
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.uxuexi.core.common.util.Util;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月22日 	 
 */
public class FormatDateUtil {

	private static DateFormat DF = new SimpleDateFormat("ddMMM", Locale.ENGLISH);

	private static String[] WEEKS = { "MO", "TU", "WE", "TH", "FR", "SA", "SU" };

	/**
	 * 返回订单列表相应的日期格式
	 * <p>
	 * TODO返回订单列表相应的日期格式
	 *
	 * @param date
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public static String dateToOrderDate(Date date) {
		String result = "";
		if (!Util.isEmpty(date)) {
			result += WEEKS[dayForWeek(date) - 1] + DF.format(date).toUpperCase();
		}
		return result;
	}

	/**
	 * 判断日期是周几
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 * @param date
	 * @return
	 * @throws Exception TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public static int dayForWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	/**  
	 * 使用java正则表达式去掉多余的.与0  
	 * @param s  
	 * @return   
	 */
	public static String subZeroAndDot(String s) {
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");//去掉多余的0    
			s = s.replaceAll("[.]$", "");//如最后一位是.则去掉    
		}
		return s;
	}
}
