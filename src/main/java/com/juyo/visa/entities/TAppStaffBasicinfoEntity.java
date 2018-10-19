package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_app_staff_basicinfo")
public class TAppStaffBasicinfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("公司id")
	private Integer comid;

	@Column
	@Comment("用户id（登录用户id）")
	private Integer userid;

	@Column
	@Comment("姓")
	private String firstname;

	@Column
	@Comment("是否填写完成")
	private Integer iscompleted;

	@Column
	@Comment("姓(拼音)")
	private String firstnameen;

	@Column
	@Comment("名")
	private String lastname;

	@Column
	@Comment("名(拼音)")
	private String lastnameen;

	@Column
	@Comment("微信Token")
	private String wechattoken;

	@Column
	@Comment("状态")
	private Integer status;

	@Column
	@Comment("手机号")
	private String telephone;

	@Column
	@Comment("是否是第一次上传图片")
	private Integer isfirst;

	@Column
	@Comment("手机号(英文)")
	private String telephoneen;

	@Column
	@Comment("邮箱")
	private String email;

	@Column
	@Comment("邮箱(英文)")
	private String emailen;

	@Column
	@Comment("性别")
	private String sex;

	@Column
	@Comment("部门")
	private String department;

	@Column
	@Comment("职位")
	private String job;

	@Column
	@Comment("民族")
	private String nation;

	@Column
	@Comment("民族(英文)")
	private String nationen;

	@Column
	@Comment("出生日期")
	private Date birthday;

	@Column
	@Comment("住址")
	private String address;

	@Column
	@Comment("住址(英文)")
	private String addressen;

	@Column
	@Comment("卡号")
	private String cardnum;

	@Column
	@Comment("卡号(英文)")
	private String cardnumen;

	@Column
	@Comment("身份证号")
	private String cardId;

	@Column
	@Comment("公民身份证号(英文)")
	private String cardIden;

	@Column
	@Comment("身份证正面")
	private String cardfront;

	@Column
	@Comment("二寸免冠照")
	private String twoinchphoto;

	@Column
	@Comment("身份证反面")
	private String cardback;

	@Column
	@Comment("签发机关")
	private String issueorganization;

	@Column
	@Comment("有效期始")
	private Date validstartdate;

	@Column
	@Comment("有效期至")
	private Date validenddate;

	@Column
	@Comment("现居住地址省份")
	private String province;

	@Column
	@Comment("现居住地址省份(英文)")
	private String provinceen;

	@Column
	@Comment("现居住地址城市")
	private String city;

	@Column
	@Comment("现居住地址城市(英文)")
	private String cityen;

	@Column
	@Comment("详细地址")
	private String detailedaddress;

	@Column
	@Comment("详细地址(英文)")
	private String detailedaddressen;

	@Column
	@Comment("身份证省份")
	private String cardprovince;

	@Column
	@Comment("身份证省份(英文)")
	private String cardprovinceen;

	@Column
	@Comment("身份证城市")
	private String cardcity;

	@Column
	@Comment("身份证城市(英文)")
	private String cardcityen;

	@Column
	@Comment("电子代码姓")
	private String telecodefirstname;

	@Column
	@Comment("电子代码姓(拼音)")
	private String telecodefirstnameEn;

	@Column
	@Comment("电子代码名")
	private String telecodelastname;

	@Column
	@Comment("电子代码名(拼音)")
	private String telecodelastnameEn;

	@Column
	@Comment("曾用姓")
	private String otherfirstname;

	@Column
	@Comment("曾用姓(拼音)")
	private String otherfirstnameen;

	@Column
	@Comment("曾用名")
	private String otherlastname;

	@Column
	@Comment("曾用名(拼音)")
	private String otherlastnameen;

	@Column
	@Comment("紧急联系人姓名")
	private String emergencylinkman;

	@Column
	@Comment("紧急联系人手机")
	private String emergencytelephone;

	@Column
	@Comment("是否另有国籍")
	private Integer hasothernationality;

	@Column
	@Comment("是否另有国籍(英文)")
	private Integer hasothernationalityen;

	@Column
	@Comment("是否有其他国家的护照")
	private Integer hasotherpassport;

	@Column
	@Comment("是否有其他国家的护照(英文)")
	private Integer hasotherpassporten;

	@Column
	@Comment("其他国家护照号码")
	private String otherpassportnumber;

	@Column
	@Comment("其他国家护照号码(英文)")
	private String otherpassportnumberen;

	@Column
	@Comment("是否是其他国家的永久居民")
	private Integer isothercountrypermanentresident;

	@Column
	@Comment("是否是其他国家的永久居民(英文)")
	private Integer isothercountrypermanentresidenten;

	@Column
	@Comment("是否有代表姓名的电子代码")
	private Integer hastelecode;

	@Column
	@Comment("是否有代表姓名的电子代码(英文)")
	private Integer hastelecodeEn;

	@Column
	@Comment("是否有曾用名")
	private Integer hasothername;

	@Column
	@Comment("是否有曾用名(英文)")
	private Integer hasothernameen;

	@Column
	@Comment("结婚证/离婚证地址")
	private String marryurl;

	@Column
	@Comment("结婚状况")
	private Integer marrystatus;

	@Column
	@Comment("结婚状况(英文)")
	private Integer marrystatusen;

	@Column
	@Comment("婚姻状况说明")
	private String marryexplain;

	@Column
	@Comment("婚姻状况说明(英文)")
	private String marryexplainen;

	@Column
	@Comment("婚姻状况证件类型")
	private Integer marryurltype;

	@Column
	@Comment("其他国家永久居民")
	private String othercountry;

	@Column
	@Comment("其他国家永久居民(英文)")
	private String othercountryen;

	@Column
	@Comment("国籍")
	private String nationality;

	@Column
	@Comment("国籍(英文)")
	private String nationalityen;

	@Column
	@Comment("出生国家")
	private String birthcountry;

	@Column
	@Comment("出生国家(英文)")
	private String birthcountryen;

	@Column
	@Comment("现居住国家(英文)")
	private String nowcountry;

	@Column
	@Comment("现居住国家(英文)")
	private String nowcountryen;

	@Column
	@Comment("邮寄城市")
	private String mailcity;

	@Column
	@Comment("邮寄城市(英文)")
	private String mailcityen;

	@Column
	@Comment("邮寄省份")
	private String mailprovince;

	@Column
	@Comment("邮寄省份(英文)")
	private String mailprovinceen;

	@Column
	@Comment("邮寄国家")
	private String mailcountry;

	@Column
	@Comment("邮寄国家(英文)")
	private String mailcountryen;

	@Column
	@Comment("邮寄地址")
	private String mailaddress;

	@Column
	@Comment("邮寄地址(英文)")
	private String mailaddressen;

	@Column
	@Comment("国家注册码")
	private String nationalidentificationnumber;

	@Column
	@Comment("国家注册码(英文)")
	private String nationalidentificationnumberen;

	@Column
	@Comment("注册码是否适用")
	private Integer isidentificationnumberapply;

	@Column
	@Comment("注册码是否适用(英文)")
	private Integer isidentificationnumberapplyen;

	@Column
	@Comment("美国社会安全号码")
	private String socialsecuritynumber;

	@Column
	@Comment("美国社会安全号码(英文)")
	private String socialsecuritynumberen;

	@Column
	@Comment("安全码是否适用")
	private Integer issecuritynumberapply;

	@Column
	@Comment("安全码是否适用(英文)")
	private Integer issecuritynumberapplyen;

	@Column
	@Comment("美国纳税人证件号")
	private String taxpayernumber;

	@Column
	@Comment("美国纳税人证件号(英文)")
	private String taxpayernumberen;

	@Column
	@Comment("纳税人证件号是否适用")
	private Integer istaxpayernumberapply;

	@Column
	@Comment("纳税人证件号是否适用(英文)")
	private Integer istaxpayernumberapplyen;

	@Column
	@Comment("现居住地是否与身份证相同")
	private Integer addressIssamewithcard;

	@Column
	@Comment("现居住地是否与身份证相同(英文)")
	private Integer addressIssamewithcarden;

	@Column
	@Comment("操作人")
	private Integer opid;

	@Column
	@Comment("创建时间")
	private Date createtime;

	@Column
	@Comment("更新时间")
	private Date updatetime;

	@Column
	@Comment("面试时间")
	private Date interviewdate;

	@Column
	@Comment("签证状态")
	private Integer visastatus;
	@Column
	@Comment("AA码")
	private String aacode;

}
