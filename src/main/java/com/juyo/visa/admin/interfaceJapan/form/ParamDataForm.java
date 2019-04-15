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
public class ParamDataForm {
	/*时间戳*/
	private String timeStamp;

	/*随机串*/
	private String nonce;

	/*签名*/
	private String msg_signature;

	/*密文*/
	private String encrypt;
}
