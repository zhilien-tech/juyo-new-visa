/**
 * JsonPathGeneric.java
 * com.linyun.airline.common.util
 * Copyright (c) 2016, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.common.util;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.JsonPath;

/**
 * JsonPath转Java对象工具类
 * <p>
 *
@SuppressWarnings("cast")
 * @author   朱晓川
 * @Date	 2016年12月20日 	 
 */
public class JsonPathGeneric {

	private JsonDeserializer<java.util.Date> dateAdapter;

	private Gson gson;

	@SuppressWarnings("unchecked")
	public <T> T getGenericObject(String json, String jsonPath, Class<T> clazz) {
		String jsonResult = null;
		if (jsonPath == null || "".equals(jsonPath)) {
			jsonResult = json;
		} else {
			Map<String, Object> map = JsonPath.read(json, jsonPath);
			jsonResult = Json.toJson(map, JsonFormat.tidy());
		}
		if (String.class.equals(clazz)) {
			return (T) jsonResult;
		}
		return gson.fromJson(jsonResult, clazz);

	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getGenericList(String json, String jsonPath) {
		String jsonResult = null;
		if (jsonPath == null || "".equals(jsonPath)) {
			jsonResult = json;
		} else {
			Map<String, Object> map = JsonPath.read(json, jsonPath);
			jsonResult = Json.toJson(map, JsonFormat.tidy());
		}

		Type listtype = new TypeToken<List<T>>() {
		}.getType();
		return (List<T>) gson.fromJson(jsonResult, listtype);
	}

	public JsonPathGeneric() {
		GsonBuilder builder = new GsonBuilder();

		dateAdapter = new JsonDeserializer<Date>() {
			@Override
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
				//时间解析    
				DateTime dateTime = format.parseDateTime(json.getAsJsonPrimitive().getAsString());
				return dateTime.toDate();
			}
		};

		// Register an adapter to manage the date types as long values
		builder.registerTypeAdapter(Date.class, dateAdapter);
		gson = builder.create();
	}

	public void setDateAdapter(JsonDeserializer<java.util.Date> dateAdapter) {
		this.dateAdapter = dateAdapter;
	}
}
