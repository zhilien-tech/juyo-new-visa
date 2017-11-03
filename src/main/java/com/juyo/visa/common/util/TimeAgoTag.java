package com.juyo.visa.common.util;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.common.util.Util;

@Data
@EqualsAndHashCode(callSuper = true)
public class TimeAgoTag extends SimpleTagSupport{
	
	private final static long minute = 60 * 1000;// 1分钟  
	private final static long hour = 60 * minute;// 1小时  
    private final static long day = 24 * hour;// 1天  
    private final static long month = 31 * day;// 月  
    private final static long year = 12 * month;// 年  
	
	private Timestamp datetime;

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		createTree(out);
		super.doTag();
	}

	private void createTree(final JspWriter out) throws IOException {
		String content = getTimeFormatText(datetime); 
		if (Util.isEmpty(content)) {
			return;
		}
		out.print(content);//输出结尾
	}
	
    public static String getTimeFormatText(Date date) {  
        if (date == null) {  
            return null;  
        }  
        long diff = new Date().getTime() - date.getTime();  
        long r = 0;  
        if (diff > year) {  
            r = (diff / year);  
            return r + "年前";  
        }  
        if (diff > month) {  
            r = (diff / month);  
            return r + "个月前";  
        }  
        if (diff > day) {  
            r = (diff / day);  
            return r + "天前";  
        }  
        if (diff > hour) {  
            r = (diff / hour);  
            return r + "小时前";  
        }  
        if (diff > minute) {  
            r = (diff / minute);  
            return r + "分钟前";  
        }  
        return "刚刚";  
    }  
}
