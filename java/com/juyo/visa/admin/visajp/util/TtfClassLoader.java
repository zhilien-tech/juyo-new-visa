/**
 * ClassLoader.java
 * com.juyo.visa.admin.visajp.util
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.visajp.util;

import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年11月16日 	 
 */
public class TtfClassLoader {

	public String getFontURL() {
		return getClass().getClassLoader().getResource("simsun.ttf").toString();
	}

	public BaseFont getBaseFont() throws IOException, DocumentException {
		BaseFont bf = BaseFont.createFont(getFontURL(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		return bf;
	}

	public Font getFont() throws IOException, DocumentException {
		BaseFont bf = BaseFont.createFont(getFontURL(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font f = new Font(bf, 10);
		return f;
	}
}
