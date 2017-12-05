package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * <p>
 * 日本初审检索状态
 * @author   彭辉
 * @Date	 2017年10月20日
 */
public enum ReceptionSearchStatusEnum_JP implements IEnum {

	SEND_ADDRESS(JPOrderStatusEnum.SEND_ADDRESS.intKey(), "已发地址"), SEND_DATA(JPOrderStatusEnum.SEND_DATA.intKey(),
			"已寄出"), RECEPTION_ORDER(JPOrderStatusEnum.RECEPTION_ORDER.intKey(), "前台"), RECEPTION_RECEIVED(
			JPOrderStatusEnum.RECEPTION_RECEIVED.intKey(), "前台实收"), TRANSFER_VISA(JPOrderStatusEnum.TRANSFER_VISA
			.intKey(), "移交签证");
	private int key;
	private String value;

	private ReceptionSearchStatusEnum_JP(final int key, final String value) {
		this.value = value;
		this.key = key;
	}

	@Override
	public String key() {
		return String.valueOf(key);
	}

	@Override
	public String value() {
		return value;
	}

	public int intKey() {
		return key;
	}
}
