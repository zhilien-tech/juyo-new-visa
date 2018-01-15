/**
 * Image.java
 * org.zxc.ocr.bean
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.common.ocr;

import java.util.Map;

import lombok.Data;

import org.nutz.json.Json;

import com.google.common.collect.Maps;
import com.uxuexi.core.common.util.Util;

/**
 * @author   朱晓川
 * @Date	 2017年9月15日 	 
 */
@Data
public class Input {

	private Image image;

	private Configure configure;

	public Input(String dataValue, String configValue) {
		image = new Image();
		configure = new Configure(configValue);
		image.setDataValue(dataValue);
	}

	public Input(String dataValue) {
		image = new Image();
		image.setDataValue(dataValue);
	}

	@Data
	private class Image {
		/**50表示image的数据类型为字符串*/
		private int dataType = 50;

		/**图片以base64编码的string/oss图片链接*/
		private String dataValue;
	}

	@Data
	private class Configure {
		/**50表示image的数据类型为字符串*/
		private int dataType = 50;

		/**身份证正反面类型:face/back*/
		private Map<String, Object> valObj = Maps.newHashMap();
		private String dataValue;

		public Configure(String val) {
			if (!Util.isEmpty(val)) {
				valObj.put("side", val);
			} else {
				valObj.put("side", "face");
			}
			dataValue = Json.toJson(valObj);
		}
	}
}
