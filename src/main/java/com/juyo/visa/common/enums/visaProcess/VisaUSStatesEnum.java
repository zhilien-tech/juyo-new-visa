package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 美国各个州 枚举
 */
public enum VisaUSStatesEnum implements IEnum {
	ALABAMA(1, "阿拉巴马州"), ALASKA(2, "阿拉斯加州"), ARIZONA(3, "亚利桑那州"), ARKANSAS(4, "阿肯色州"), CALIFORNIA(5, "加利福尼亚州"), COLORADO(
			6, "科罗拉多州"), CONNECTICUT(7, "康涅狄格州"), DELAWARE(8, "特拉华州"), FLORIDA(9, "佛罗里达州"), GEORGIA(10, "乔治亚州"),

	HAWAII(11, "夏威夷州"), IDAHO(12, "爱达荷州"), ILLINOIS(13, "伊利诺斯州"), INDIANA(14, "印第安纳州"), IOWA(15, "爱荷华州"), KANSAS(16,
			"堪萨斯州"), KENTUCKY(17, "肯塔基州"), LOUISIANA(18, "路易斯安那州"), MAINE(19, "缅因州"), MARYLAND(20, "马里兰州"),

	MASSACHUSETTS(21, "马萨诸塞州"), MICHIGAN(22, "密歇根州"), MINNESOTA(23, "明尼苏达州"), MISSISSIPPI(24, "密西西比州"), MISSOURI(25,
			"密苏里州"), MONTANA(26, "蒙大拿州"), NEBRASKA(27, "内布拉斯加州"), NEVADA(28, "内华达州"), NEW_HAMPSHIRE(29, "新罕布什尔州"), NEW_JERSEY(
			30, "新泽西州"),

	NEW_MEXICO(31, "新墨西哥州"), NEW_YORK(32, "纽约州"), NORTH_CAROLINA(33, "北卡罗来纳州"), NORTH_DAKOTA(34, "北达科他州"), OHIO(35,
			"俄亥俄州"), OKLAHOMA(36, "俄克拉荷马州"), OREGON(37, "新泽西州"), PENNSYLVANIA(38, "宾夕法尼亚州"), RHODE_ISLAND(39, "罗得岛州"), SOUTH_CAROLINA(
			40, "南卡罗来纳州"),

	SOUTH_DAKOTA(41, "南达科他州"), TENNESSEE(42, "田纳西州"), TEXAS(43, "德克萨斯州"), UTAH(44, "犹他州"), VERMONT(45, "佛蒙特州"), VIRGINIA(
			46, "弗吉尼亚州"), WASHINGTON(47, "华盛顿州"), WEST_VIRGINIA(48, "西弗吉尼亚州"), WISCONSIN(49, "威斯康辛州"), WYOMING(50,
			"怀俄明州");

	private int key;
	private String value;

	private VisaUSStatesEnum(final int key, final String value) {
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
