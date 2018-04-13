package com.juyo.visa.common.base;

import static com.uxuexi.core.common.util.MapUtil.map;

import java.util.Map;

import com.uxuexi.core.common.enums.IEnum;
import com.uxuexi.core.common.util.ExceptionUtil;

public class MobileResult {

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
	 * 同dwz框架配合的状态返回值
	 * <p>
	 * SUCCESS 成功
	 * FAIL 失败
	 * TIMEOUT 用户登录信息超时
	 * @author   庄君祥
	 * @Date	 2012-5-1
	 */
	public static enum StatusCode implements IEnum {
		SUCCESS("200", "成功"), 
		FAIL("300", "失败"), 
		VCODE_ERROR("301", "手机验证码错误"), 
		CAR_AREA_ERROR("302", "车牌地区不存在"),
		ERR_BAND("303", "手机号已绑定其他微信账号"),
		SUCC_NULL("304", "操作成功，信息为空"),
		WAIT_PAY("401", "等待支付"), 
		PAYED("402", "已支付"), 
		COMPLETE("403", "已完成"),
		REPEAT("405", "重复");
		
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
	public static Map<String, Object> info(final Object status,final String message, final Object data) {
		Map<String, Object> map = map();
		map.put(CODE_KEY, status);
		map.put(MESSAGE_KEY, message);
		map.put(DATA_KEY, data);
		return map;
	}
	
	/**
	 * 返回成功信息和数据
	 * <p>
	 * 消息为空时，不提示，不为空则进行提示
	 * @param message 提示消息
	 * @param 要向客户端传输的数据
	 * @return json对象
	 */
	public static Map<String, Object> success(final String message, final Object data) {
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
	public static Map<String, Object> error(final String message, final Object data) {
		Map<String, Object> map = map();
		map.put(CODE_KEY, StatusCode.FAIL.key());
		map.put(MESSAGE_KEY, message);
		map.put(DATA_KEY, data);
		return map;
	}

	public static Map<String, Object> vCodeError(final String message, final Object data) {
		Map<String, Object> map = map();
		map.put(CODE_KEY, StatusCode.VCODE_ERROR.key());
		map.put(MESSAGE_KEY, message);
		map.put(DATA_KEY, data);
		return map;
	}

	public static Map<String, Object> carAreaError(final String message, final Object data) {
		Map<String, Object> map = map();
		map.put(CODE_KEY, StatusCode.CAR_AREA_ERROR.key());
		map.put(MESSAGE_KEY, message);
		map.put(DATA_KEY, data);
		return map;
	}
	
	public static Map<String, Object> errBand(final String message, final Object data) {
		Map<String, Object> map = map();
		map.put(CODE_KEY, StatusCode.ERR_BAND.key());
		map.put(MESSAGE_KEY, message);
		map.put(DATA_KEY, data);
		return map;
	}

	public static Map<String, Object> succesDataEmpty(final String message, final Object data) {
		Map<String, Object> map = map();
		map.put(CODE_KEY, StatusCode.SUCC_NULL.key());
		map.put(MESSAGE_KEY, message);
		return map;
	}
	
	/**
	 * 将异常转换为错误信息
	 * 
	 * @param th 异常
	 * @return 错误信息json对象
	 */
	public static Map<String, Object> exceptionResult(final Throwable th) {
		return error(ExceptionUtil.getSimpleMessage(th), null);
	}

}
