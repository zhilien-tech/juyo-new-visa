/**
 * ParamDataForm.java
 * com.juyo.visa.admin.interfaceJapan.form
 * Copyright (c) 2019, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.interfaceJapan.form;

import lombok.Data;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 2019年4月4日 	 
 */
@Data
public class OrdervoucherForm {
	/*公司或者用户信息(身份标记，并以此为依据创建订单)  临时*/
	private String userName;

	/*订单识别码*/
	private String orderVoucher;

}
