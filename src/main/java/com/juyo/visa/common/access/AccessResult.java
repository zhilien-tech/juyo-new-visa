package com.juyo.visa.common.access;

import static com.uxuexi.core.common.util.MapUtil.map;

import java.util.Map;

import com.uxuexi.core.common.enums.IEnum;

public class AccessResult {
	
	/**
	 * 消息Key
	 */
	public static final String MESSAGE_KEY = "message";
	/**
	 * 编码Key
	 */
	public static final String CODE_KEY = "status";
	
	/**
	 * 数据key
	 */
	public static final String DATA_KEY = "data";

	/**
	 * SUCCESS 成功
	 * FAIL 失败
	 * TIMEOUT 用户登录信息超时
	 */
	public static enum StatusCode implements IEnum {
		SUCCESS("200", "成功"), FAIL("300", "失败"),CONNECT_ERROR_PLATFORM("501","调用平台接口失败") ;
		private String key;
		private String value;

		private StatusCode(final String key, final String value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public String key() {
			return key;
		}

		@Override
		public String value() {
			return value;
		}
	}


	/**
	 * 返回成功信息和数据
	 * <p>
	 * 消息为空时，不提示，不为空则进行提示
	 * @param message 提示消息
	 * @param 要向客户端传输的数据
	 * @return json对象
	 */
	public static Map<String, Object> success(final String message,final Object data) {
		Map<String, Object> map = map();
		map.put(CODE_KEY, StatusCode.SUCCESS.key());
		map.put(MESSAGE_KEY, message);
		map.put(DATA_KEY, data);
		return map;
	}

	/**
	 * 返回错误信息
	 * <p>
	 * @param message 提示消息
	 * @param 要向客户端传输的数据
	 * @return json对象
	 */
	public static Map<String, Object> error(final String message,final Object data) {
		Map<String, Object> map = map();
		map.put(CODE_KEY, StatusCode.FAIL.key());
		map.put(MESSAGE_KEY, message);
		map.put(DATA_KEY, data);
		return map;
	}
	
	/**
	 * 返回【调用平台接口失败】信息和数据
	 * @param message  提示信息
	 * @param data     数据
	 */
	public static Map<String, Object> connectErrorPlatform(final String message,final Object data) {
		Map<String, Object> map = map();
		map.put(CODE_KEY, StatusCode.CONNECT_ERROR_PLATFORM.key());
		map.put(MESSAGE_KEY, message);
		map.put(DATA_KEY, data);
		return map;
	}
	
}
