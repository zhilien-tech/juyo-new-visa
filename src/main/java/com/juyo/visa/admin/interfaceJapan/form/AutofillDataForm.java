/**
 * AutofillDataForm.java
 * com.juyo.visa.admin.interfaceJapan.form
 * Copyright (c) 2019, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.interfaceJapan.form;

import java.util.List;

import lombok.Data;

import com.juyo.visa.admin.interfaceJapan.entity.ApplicantInfo;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @param <T>
 * @Date	 2019年4月3日 	 
 */
@Data
public class AutofillDataForm {

	/*公司或者用户信息(身份标记，并以此为依据创建订单)  临时*/
	private String userName;

	/*出行日期*/
	private String goDate;

	/*返回日期*/
	private String returnDate;

	/*送签社指定番号*/
	private String designatedNum;

	/*订单识别码*/
	private String orderVoucher;

	/*行为，send为发招宝，update为招宝变更，cancel为招宝取消*/
	private String action;

	/*受付番号*/
	//private String acceptDesign;

	/*签证类型*/
	private Integer visaType;

	/*申请人信息*/
	private List<ApplicantInfo> applicantsList;

}
