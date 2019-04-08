package com.juyo.visa.common.util;

import java.util.HashMap;
import java.util.Map;

public class InterfaceResultObject<T, R> {
	private String msg = "操作成功!";// 提示信息，一般是在失败时才有特殊提示
	private T data = null;// 请求成功是返回的数据
	private Map<String, R> attributes = null;// 附属属性信息
	private int success = 0;

	public static <T> InterfaceResultObject success(T... data) {
		InterfaceResultObject ro = new InterfaceResultObject();
		ro.msg = "";
		ro.success = 1;
		if (data != null && data.length > 0) {
			ro.data = data[0];
		}
		return ro;
	}

	public static InterfaceResultObject success() {
		InterfaceResultObject ro = new InterfaceResultObject();
		ro.msg = "";
		ro.success = 1;
		return ro;
	}

	public static InterfaceResultObject fail(String msg) {
		InterfaceResultObject ro = new InterfaceResultObject();
		ro.msg = msg;
		ro.success = 0;
		return ro;
	}

	public static InterfaceResultObject error(String msg) {
		InterfaceResultObject ro = new InterfaceResultObject();
		ro.msg = msg;
		return ro;
	}

	public static <T> InterfaceResultObject login(String msg, T... data) {
		InterfaceResultObject ro = new InterfaceResultObject();
		ro.msg = msg;
		if (data != null && data.length > 0) {
			ro.data = data[0];
		}
		return ro;
	}

	public static <T> InterfaceResultObject warn(ResultCode code, String msg, T... data) {
		InterfaceResultObject ro = new InterfaceResultObject();
		ro.msg = msg;
		if (data != null && data.length > 0) {
			ro.data = data[0];
		}
		return ro;
	}

	/**
	 * 添加额外属性信息，以Map的方式呈现
	 *
	 * @param key   外信息的key
	 * @param value 外信息的value
	 */
	public InterfaceResultObject<T, R> addAttribute(String key, R value) {
		if (attributes == null) {
			attributes = new HashMap<String, R>();
		}
		attributes.put(key, value);
		return this;
	}

	/**
	 * 添加多个额外的属性
	 *
	 * @param values 以map形式存在的属性值
	 */
	public InterfaceResultObject<T, R> addAttribute(Map<String, R> values) {
		if (attributes == null) {
			attributes = new HashMap<String, R>();
		}
		attributes.putAll(values);
		return this;
	}

	/**
	 * 重设额外信息集合，并置空attributes
	 */
	public void clearAttribute() {
		if (attributes != null) {
			attributes.clear();
			attributes = null;
		}
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public InterfaceResultObject<T, R> setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public T getData() {
		return data;
	}

	public InterfaceResultObject<T, R> setData(T data) {
		this.data = data;
		return this;
	}

	public Map<String, R> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, R> attributes) {
		this.attributes = attributes;
	}

	public static enum ResultCode {
		/**
		 * 0:访问失败;1:访问成功;2,未登录未授权，3:异常;4:其他情况服务不可用
		 */
		FAIL(404), SUCCESS(200), UNAUTHORIZED(401), EXCEPTION(500), OTHER(503);
		private int value;

		ResultCode(int value) { //    必须是private的，否则编译错误
			this.value = value;
		}

		public static ResultCode valueOf(int value) {
			//手写的从int到enum的转换函数
			switch (value) {
			case 200:
				return SUCCESS;
			case 401:
				return UNAUTHORIZED;
			case 500:
				return EXCEPTION;
			case 503:
				return OTHER;
			default:
				return FAIL;
			}
		}

		public int value() {
			return this.value;
		}
	}
}
