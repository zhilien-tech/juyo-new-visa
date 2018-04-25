package com.juyo.visa.common.enums.visaProcess;

import com.uxuexi.core.common.enums.IEnum;

/**
 * 美国各个州 枚举  英文部分
 */
public enum VisaUSStatesEnEnum implements IEnum {
	ALABAMA(1, "ALABAMA"), ALASKA(2, "ALASKA"), ARIZONA(3, "ARIZONA"), ARKANSAS(4, "ARKANSAS"), CALIFORNIA(5,
			"CALIFORNIA"), COLORADO(6, "COLORADO"), CONNECTICUT(7, "CONNECTICUT"), DELAWARE(8, "DELAWARE"), FLORIDA(9,
			"FLORIDA"), GEORGIA(10, "GEORGIA"),

	HAWAII(11, "HAWAII"), IDAHO(12, "IDAHO"), ILLINOIS(13, "ILLINOIS("), INDIANA(14, "INDIANA"), IOWA(15, "IOWA"), KANSAS(
			16, "KANSAS"), KENTUCKY(17, "KENTUCKY"), LOUISIANA(18, "LOUISIANA"), MAINE(19, "MAINE"), MARYLAND(20,
			"MARYLAND"),

	MASSACHUSETTS(21, "MASSACHUSETTS"), MICHIGAN(22, "MICHIGAN"), MINNESOTA(23, "MINNESOTA"), MISSISSIPPI(24,
			"MISSISSIPPI"), MISSOURI(25, "MISSOURI"), MONTANA(26, "MONTANA"), NEBRASKA(27, "NEBRASKA"), NEVADA(28,
			"NEVADA"), NEW_HAMPSHIRE(29, "NEW_HAMPSHIRE"), NEW_JERSEY(30, "NEW_JERSEY"),

	NEW_MEXICO(31, "NEW_MEXICO"), NEW_YORK(32, "NEW_YORK"), NORTH_CAROLINA(33, "NORTH_CAROLINA"), NORTH_DAKOTA(34,
			"NORTH_DAKOTA"), OHIO(35, "OHIO"), OKLAHOMA(36, "OKLAHOMA"), OREGON(37, "OREGON"), PENNSYLVANIA(38,
			"PENNSYLVANIA"), RHODE_ISLAND(39, "RHODE_ISLAND"), SOUTH_CAROLINA(40, "SOUTH_CAROLINA"),

	SOUTH_DAKOTA(41, "SOUTH_DAKOTA"), TENNESSEE(42, "SOUTH_DAKOTA"), TEXAS(43, "TEXAS"), UTAH(44, "UTAH"), VERMONT(45,
			"VERMONT"), VIRGINIA(46, "VIRGINIA"), WASHINGTON(47, "WASHINGTON"), WEST_VIRGINIA(48, "WEST_VIRGINIA"), WISCONSIN(
			49, "WISCONSIN"), WYOMING(50, "WYOMING");

	private int key;
	private String value;

	private VisaUSStatesEnEnum(final int key, final String value) {
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
