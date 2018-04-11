package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffWxinfoUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**人员id*/
	private Integer staffid;
		
	/**用户唯一标识*/
	private String openid;
		
	/**用户昵称*/
	private String nickname;
		
	/**性别*/
	private Integer sex;
		
	/**省*/
	private String province;
		
	/**市*/
	private String city;
		
	/**国家*/
	private String country;
		
	/**用户头像*/
	private String headimgurl;
		
	/**用户特权信息*/
	private String privilege;
		
	/**个人信息id*/
	private String unionid;
		
}