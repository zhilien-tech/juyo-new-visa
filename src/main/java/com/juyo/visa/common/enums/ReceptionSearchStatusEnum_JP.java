package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * <p>
 * 前台订单状态 枚举
 * @author   
 * @Date	 2017年10月20日
 */
public enum ReceptionSearchStatusEnum_JP implements IEnum {

	SEND_ADDRESS(JPOrderStatusEnum.SEND_ADDRESS.intKey(), "已发地址"), SEND_DATA(JPOrderStatusEnum.SEND_DATA.intKey(),
			"已寄出"), RECEPTION_ORDER(JPOrderStatusEnum.RECEPTION_ORDER.intKey(), "待收件"), RECEPTION_RECEIVED(
			JPOrderStatusEnum.RECEPTION_RECEIVED.intKey(), "前台实收"), TRANSFER_VISA(JPOrderStatusEnum.TRANSFER_VISA
			.intKey(), "移交签证"), VISA_ORDER(JPOrderStatusEnum.VISA_ORDER.intKey(), "签证"), VISA_RECEIVED(
			JPOrderStatusEnum.VISA_RECEIVED.intKey(), "签证实收"), AUTO_FILL_FORM_PREPARE(
			JPOrderStatusEnum.AUTO_FILL_FORM_PREPARE.intKey(), "准备发招宝"), AUTO_FILL_FORM_ING(
			JPOrderStatusEnum.AUTO_FILL_FORM_ING.intKey(), "发招宝中"), AUTO_FILL_FORM_ED(
			JPOrderStatusEnum.AUTO_FILL_FORM_ED.intKey(), "已发招宝"), AUTO_FILL_FORM_FAILED(
			JPOrderStatusEnum.AUTO_FILL_FORM_FAILED.intKey(), "发招宝失败"), AFTERMARKET_ORDER(
			JPOrderStatusEnum.AFTERMARKET_ORDER.intKey(), "售后");
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
