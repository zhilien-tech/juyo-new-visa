package com.juyo.visa.admin.bigcustomer.form;

import lombok.Data;

@Data
public class SignUpEventForm {

	/**活动id*/
	private Integer eventId;

	/**微信Token*/
	private String weChatToken;

	/**活动号*/
	private String eventsNum;

	/**活动名称*/
	private String eventsName;

	/**姓*/
	private String firstname;

	/**名*/
	private String lastname;

	/**手机号*/
	private String telephone;

	/**邮箱*/
	private String email;

}