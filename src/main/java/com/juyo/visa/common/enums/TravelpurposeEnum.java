/**
 * TravelpurposeEnum.java
 * com.juyo.visa.common.enums
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.common.enums;

import com.uxuexi.core.common.enums.IEnum;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 * 出行目的
 * @author   马云鹏
 * @Date	 2018年3月24日 	 
 */
public enum TravelpurposeEnum implements IEnum {
	TRAVELCONSUMER("TEMP. BUSINESS PLEASURE VISITOR(B)", "商务旅游游客(B)"), FOREIGNlEADER("FOREIGN GOVERNMENT OFFICIAL(A)",
			"外国政府官员（A）"), TRAVELFOREIGN("ALIEN IN TRANSIT(C)", "过境的外国公民(C)"), WORKER("CNMI WORKER OR INVESTOR(CW/E2C)",
			"CNMI工作者或投资者(CW/E2C)"), AIRCRAFTCREW("CREWMEMBER(D)", "机船组人员(D)"), INVESTOR("TREATY TRADER OR INVESTOR(E)",
			"贸易协议国贸易人员或投资者(E)"), STUDENT("ACADEMIC OR LANGUAGE STUDENT(F)", "学术或语言学生(F)"), EMPLOYEE(
			"INTERNATIONAL ORGANIZATION REP./EMP.(G)", "国际组织代表/雇员(G)"), CHARE("TEMPORARY WORKER(H)", "临时工作(H)"), FOREIGNMEDIA(
			"FOREIGN MEDIA REPRESENTATIVE(I)", "外国媒体代表(I)"), EXCHANGEVISITOR("EXCHANGE VISITOR(J)", "交流访问者(J)"), FIANCEE(
			"FIANCE(E) OR SPOUSE OF A U.S. CITIZEN(K)", "美国公民的未婚夫（妻）或配偶（K）"), DEPLOYED("INTRACOMPANY TRANSFEREE(L)",
			"公司内部调派人员(L)"), VOCATIONAL("VOCATIONAL/NONACADEMIC STUDENT(M)", "职业/非学术学校的学生(M)"), OTHER("OTHER(N)",
			"其他(N)"), NATOSTAFF("NATO STAFF(NATO)", "北约工作人员(NATO)"), ABILITY("ALIEN WITH EXTRAORDINARY ABILITY(O)",
			"具有特殊才能的人员(O)"), RECOGNIZEDALIEN("INTERNATIONALLY RECOGNIZED ALIEN(P)", "国际承认的外国人士(P)"), VISITOR(
			"CULTURAL EXCHANGE VISITOR(Q)", "文化交流访问者(Q)"), RELIGIOUS("RELIGIOUS WORKER(R)", "宗教人士(R)"), WITNESS(
			"INFORMANT OR WITNESS(S)", "提供信息者或证人(S)"), VICTIM("VICTIM OF TRAFFICKING(T)", "人口贩运的受害者(T)"), PROFESSIONAL(
			"NAFTA PROFESSIONAL(TD/TN)", "北美自由贸易协议专业人员(TD/TN)"), CRIMINAL("VICTIM OF CRIMINAL ACTIVITY(U)",
			"犯罪活动的受害者(U)"), BENEFICIARY("PAROLE BENEFICIARY(PARCIS)", "假释收益者(PARCIS)");

	private String key;
	private String value;

	private TravelpurposeEnum(final String key, final String value) {
		this.value = value;
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String key() {

		return key;

	}

	public static TravelpurposeEnum getValue(String key) {
		for (TravelpurposeEnum travelpurposeEnum : values()) {
			if (key.equals(travelpurposeEnum.getKey())) {
				return travelpurposeEnum;
			}
		}
		return null;
	}

	public static TravelpurposeEnum getEnum(String value) {
		for (TravelpurposeEnum travelpurposeEnum : values()) {
			if (value.equals(travelpurposeEnum.getValue())) {
				return travelpurposeEnum;
			}
		}
		return null;
	}

	@Override
	public String value() {

		return value;

	}

}
