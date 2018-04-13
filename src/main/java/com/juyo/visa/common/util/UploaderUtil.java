package com.juyo.visa.common.util;

import java.util.Arrays;
import java.util.Iterator;

/**
 * UEditor文件上传辅助类
 *
 */
public class UploaderUtil {

	// 文件允许格式
	private static String[] allowFiles = { ".rar", ".doc", ".docx", ".zip", ".pdf", ".txt", ".swf", ".wmv", ".gif",
			".png", ".jpg", ".jpeg", ".bmp" };

	/**
	 * 文件类型判断
	 * 
	 * @param fName
	 * @return
	 */
	public static boolean checkFileType(String fName) {
		Iterator<String> types = Arrays.asList(allowFiles).iterator();
		while (types.hasNext()) {
			String ext = types.next();
			if (fName.toLowerCase().endsWith(ext)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @return string
	 */
	public static String getFileExt(String fName) {
		if(fName.contains("?")){
			fName = fName.substring(0, fName.indexOf("?")) ;
		}
		
		if (fName.contains(".")) {
			return fName.substring(fName.lastIndexOf(".") + 1);
		} else {
			return "";
		}
	}
}
