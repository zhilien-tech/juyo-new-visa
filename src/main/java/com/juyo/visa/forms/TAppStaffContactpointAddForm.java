package com.juyo.visa.forms;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffContactpointAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**人员id*/
	private Integer staffid;

	/**联系人姓*/
	private String firstname;

	/**联系人名*/
	private String lastname;

	/**是否知道姓名*/
	private Integer isknowname;

	/**组织名称*/
	private String organizationname;

	/**是否知道组织名称*/
	private Integer isknoworganizationname;

	/**与你的关系*/
	private Integer ralationship;

	/**美国街道地址*/
	private String address;

	/**美国街道地址(次选)*/
	private String secaddress;

	/**市*/
	private String city;

	/**州*/
	private Integer state;

	/**邮政编码*/
	private String zipcode;

	/**邮箱*/
	private String email;

	/**电话*/
	private String telephone;

	/**联系人姓(英文)*/
	private String firstnameen;

	/**联系人名(英文)*/
	private String lastnameen;

	/**是否知道姓名(英文)*/
	private Integer isknownameen;

	/**组织名称(英文)*/
	private String organizationnameen;

	/**是否知道组织名称(英文)*/
	private Integer isknoworganizationnameen;

	/**与你的关系(英文)*/
	private Integer ralationshipen;

	/**美国街道地址(英文)*/
	private String addressen;

	/**美国街道地址(次选)(英文)*/
	private String secaddressen;

	/**市(英文)*/
	private String cityen;

	/**州(英文)*/
	private Integer stateen;

	/**邮政编码(英文)*/
	private String zipcodeen;

	/**邮箱(英文)*/
	private String emailen;

	/**电话(英文)*/
	private String telephoneen;

}