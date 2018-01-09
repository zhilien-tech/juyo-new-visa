package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 
 * 日本订单状态 枚举
 * @author   彭辉
 * @Date	 2017年10月20日
 */
public enum JPOrderStatusEnum implements IEnum {
	PLACE_ORDER(1, "下单"), SHARE(2, "分享"), FILLING_INFORMATION(3, "填写资料"), FILLED_INFORMATION(4, "填写完成"), FIRSTTRIAL_ORDER(
			5, "初审"), UNQUALIFIED_ORDER(6, "不合格"), QUALIFIED_ORDER(7, "合格"), SEND_ADDRESS(8, "已发地址"), SEND_DATA(9,
			"已寄出"), RECEPTION_ORDER(10, "待收件"), RECEPTION_RECEIVED(11, "前台实收"), TRANSFER_VISA(12, "移交签证"), VISA_ORDER(
			13, "签证"), VISA_RECEIVED(14, "签证实收"), AUTO_FILL_FORM_PREPARE(15, "准备发招宝"), AUTO_FILL_FORM_ING(16, "发招宝中"), AUTO_FILL_FORM_ED(
			17, "已发招宝"), AUTO_FILL_FORM_FAILED(18, "发招宝失败"), BIANGENGZHONG(19, "招宝变更中"), YIBIANGENG(20, "招宝已变更"), BIANGENGSHIBAI(
			21, "招宝变更失败"), QUXIAOZHONG(22, "招宝取消中"), YIQUXIAO(23, "招宝已取消"), QUXIAOSHIBAI(24, "招宝取消失败"), REFUSE_SIGN(27,
			"报告拒签"), AFTERMARKET_ORDER(25, "售后"), DISABLED(26, "作废"), CHANGE_PRINCIPAL_OF_ORDER(88, "负责人变更");
	private int key;
	private String value;

	private JPOrderStatusEnum(final int key, final String value) {
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
