package com.juyo.visa.forms;

import java.io.Serializable;
import java.util.Date;

import org.nutz.dao.entity.annotation.Comment;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCompanyAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**公司名称*/
	private String name;

	/**公司简称*/
	private String shortName;

	/**管理员账号id*/
	private Integer adminId;

	/**用户名*/
	private String adminLoginName;

	/**用户名*/
	private String telephone;

	/**联系人*/
	private String linkman;

	/**联系人手机号*/
	private String mobile;

	/**邮箱*/
	private String email;

	/**地址*/
	private String address;

	/**公司类型*/
	private Integer comType;

	/**营业执照*/
	private String license;

	private String seal;

	/**经营范围*/
	private String businessScopes;

	/**指定番号*/
	private String designatedNum;

	/**是否是客户"*/
	private Integer isCustomer;

	/**客户指定番号*/
	private String cdesignNum;

	/**账户余额*/
	private Double accountBalance;

	/**奖励金额*/
	private Double awardMoney;

	/**广告url*/
	private String adUrl;

	/**操作人*/
	private Integer opId;

	/**创建时间*/
	private Date createTime;

	/**修改时间*/
	private Date updateTime;

	/**删除标识*/
	private Integer deletestatus;
	
	/**传真*/
	private String fax;
	
	/**pdf类型*/
	private Integer pdftype;

}