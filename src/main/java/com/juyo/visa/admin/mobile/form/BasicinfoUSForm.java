/**
 * MobileApplicantForm.java
 * com.juyo.visa.admin.mobile.form
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.mobile.form;

import java.util.Date;

import lombok.Data;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author  
 * @Date	 2018年8月21日 	 
 */
@Data
public class BasicinfoUSForm {

	private String encode;

	private Integer staffid;

	/**公司id*/
	private Integer comid;

	/**用户id（登录用户id）*/
	private Integer userid;

	/**姓*/
	private String firstname;

	/**姓(拼音)*/
	private String firstnameen;

	private Integer isfirst;

	private Integer iscompleted;

	/**名*/
	private String lastname;

	/**名(拼音)*/
	private String lastnameen;

	/**微信Token*/
	private String wechattoken;

	/**状态*/
	private Integer status;

	/**手机号*/
	private String telephone;

	private Date marrieddate;

	private Date divorcedate;

	private String divorcecountry;

	private String divorcecountryen;

	/**手机号(英文)*/
	private String telephoneen;

	/**邮箱*/
	private String email;

	/**邮箱(英文)*/
	private String emailen;

	/**性别*/
	private String sex;

	/**部门*/
	private String department;

	/**职位*/
	private String job;

	/**民族*/
	private String nation;

	/**民族(英文)*/
	private String nationen;

	/**出生日期*/
	private Date birthday;

	private Integer ismailsamewithlive;

	private Integer ismailsamewithliveen;

	private String mailcity;

	private String mailcityen;

	private String mailprovince;

	private String mailprovinceen;

	private String mailcountry;

	private String mailcountryen;

	private String mailaddress;

	private String mailaddressen;

	private String birthcountry;

	private String birthcountryen;

	/**住址*/
	private String address;
	private String addressen;

	/**卡号*/
	private String cardnum;
	private String cardnumen;

	/**身份证号*/
	private String cardid;

	//private String cardid;

	/**公民身份证号(英文)*/
	private String cardIden;

	/**身份证正面*/
	private String cardfront;

	/**二寸免冠照*/
	private String twoinchphoto;

	/**身份证反面*/
	private String cardback;

	/**签发机关*/
	private String issueorganization;

	/**有效期始*/
	private Date validstartdate;

	/**有效期至*/
	private Date validenddate;

	/**现居住地址省份*/
	private String province;

	/**现居住地址省份(英文)*/
	private String provinceen;

	/**现居住地址城市*/
	private String city;

	/**现居住地址城市(英文)*/
	private String cityen;

	/**详细地址*/
	private String detailedaddress;

	/**详细地址(英文)*/
	private String detailedaddressen;

	/**身份证省份*/
	private String cardprovince;

	/**身份证省份(英文)*/
	private String cardprovinceen;

	/**身份证城市*/
	private String cardcity;

	/**身份证城市(英文)*/
	private String cardcityen;

	/**电子代码姓*/
	private String telecodefirstname;

	/**电子代码姓(拼音)*/
	private String telecodefirstnameEn;

	/**电子代码名*/
	private String telecodelastname;

	/**电子代码名(拼音)*/
	private String telecodelastnameEn;

	/**曾用姓*/
	private String otherfirstname;

	/**曾用姓(拼音)*/
	private String otherfirstnameen;

	/**曾用名*/
	private String otherlastname;

	/**曾用名(拼音)*/
	private String otherlastnameen;

	/**紧急联系人姓名*/
	private String emergencylinkman;

	/**紧急联系人手机*/
	private String emergencytelephone;

	/**是否另有国籍*/
	private Integer hasothernationality;

	/**是否另有国籍(英文)*/
	private Integer hasothernationalityen;

	/**是否有其他国家的护照*/
	private Integer hasotherpassport;

	/**是否有其他国家的护照(英文)*/
	private Integer hasotherpassporten;

	/**其他国家护照号码*/
	private String otherpassportnumber;

	/**其他国家护照号码(英文)*/
	private String otherpassportnumberen;

	/**是否是其他国家的永久居民*/
	private Integer isothercountrypermanentresident;

	/**是否是其他国家的永久居民(英文)*/
	private Integer isothercountrypermanentresidenten;

	/**是否有代表姓名的电子代码*/
	private Integer hastelecode;

	/**是否有代表姓名的电子代码(英文)*/
	private Integer hastelecodeEn;

	/**是否有曾用名*/
	private Integer hasothername;

	/**是否有曾用名(英文)*/
	private Integer hasothernameen;

	/**结婚证/离婚证地址*/
	private String marryurl;

	/**结婚状况*/
	private Integer marrystatus;

	/**结婚状况(英文)*/
	private Integer marrystatusen;

	/**婚姻状况说明*/
	private String marryexplain;

	/**婚姻状况说明(英文)*/
	private String marryexplainen;

	/**婚姻状况证件类型*/
	private Integer marryurltype;

	/**其他国家永久居民*/
	private String othercountry;

	/**其他国家永久居民(英文)*/
	private String othercountryen;

	/**国籍*/
	private String nationality;

	/**国籍(英文)*/
	private String nationalityen;

	/**国家注册码*/
	private String nationalidentificationnumber;

	/**国家注册码(英文)*/
	private String nationalidentificationnumberen;

	/**注册码是否适用*/
	private Integer isidentificationnumberapply;

	/**注册码是否适用(英文)*/
	private Integer isidentificationnumberapplyen;

	/**美国社会安全号码*/
	private String socialsecuritynumber;

	/**美国社会安全号码(英文)*/
	private String socialsecuritynumberen;

	/**安全码是否适用*/
	private Integer issecuritynumberapply;

	/**安全码是否适用(英文)*/
	private Integer issecuritynumberapplyen;

	/**美国纳税人证件号*/
	private String taxpayernumber;

	/**美国纳税人证件号(英文)*/
	private String taxpayernumberen;

	/**纳税人证件号是否适用*/
	private Integer istaxpayernumberapply;

	/**纳税人证件号是否适用(英文)*/
	private Integer istaxpayernumberapplyen;

	/**现居住地是否与身份证相同*/
	private Integer addressIssamewithcard;

	/**现居住地是否与身份证相同(英文)*/
	private Integer addressIssamewithcarden;

	/**操作人*/
	private Integer opid;

	/**创建时间*/
	private Date createtime;

	/**更新时间*/
	private Date updatetime;

	/**面试时间*/
	private Date interviewdate;

	/**签证状态*/
	private Integer visastatus;

	/**AA码*/
	private String aacode;

	/**姓名*/
	private String name;

	/**检索字段*/
	private String searchStr;

}
