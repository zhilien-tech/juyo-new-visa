package com.juyo.visa.common.base;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import com.google.common.collect.Maps;

public class SystemProperties {
	
	//获取system_kv_config_dev.properties
	public static Map<String, Object> getKvConfigProperties() {
		Map<String, Object> result = Maps.newHashMap();
		try {
			result = GetAllProperties("src/main/resources/system_kv_config_dev.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Map<String, Object> GetAllProperties(String filePath) throws IOException {
		Map<String, Object> result = Maps.newHashMap();
		Properties pps = new Properties();
		InputStream in = new BufferedInputStream(new FileInputStream(filePath));
		pps.load(in);
		Enumeration en = pps.propertyNames();
		while(en.hasMoreElements()) {
			String strKey = (String) en.nextElement();
			String strValue = pps.getProperty(strKey);
			System.out.println(strKey + "----"+ strValue);
			result.put(strKey, strValue);
		}
		return result;
	}
	
}
