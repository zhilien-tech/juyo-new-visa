package com.juyo.visa;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import com.google.common.collect.Maps;
import com.juyo.visa.common.base.SystemProperties;

public class TestPropertiew {

	public static void main(String[] args) {
		SystemProperties.getKvConfigProperties();
	}


	//读取Properties的全部信息
	public static Map<String, Object> GetAllProperties(String filePath) throws IOException {
		Map<String, Object> result = Maps.newHashMap();
		Properties pps = new Properties();
		InputStream in = new BufferedInputStream(new FileInputStream(filePath));
		pps.load(in);
		Enumeration en = pps.propertyNames(); //得到配置文件的名字

		while(en.hasMoreElements()) {
			String strKey = (String) en.nextElement();
			String strValue = pps.getProperty(strKey);
			System.out.println(strKey + "----"+ strValue);
			result.put(strKey, strValue);
		}
		return result;
	}
}
