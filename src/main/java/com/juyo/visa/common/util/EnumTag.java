package com.juyo.visa.common.util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.common.enums.IEnum;
import com.uxuexi.core.common.util.EnumUtil;

@Data
@EqualsAndHashCode(callSuper = true)
public class EnumTag<T extends IEnum> extends SimpleTagSupport {
	
	private String className ; 
	
	private Integer key ;
	
	@Override
    public void doTag() throws JspException, IOException {
    	JspWriter out = getJspContext().getOut();
        
        Class<T> clazz;
		try {
			clazz = (Class<T>) Class.forName(className);
			out.println(EnumUtil.getValue(clazz, key));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			super.doTag() ;
		}
    }
}