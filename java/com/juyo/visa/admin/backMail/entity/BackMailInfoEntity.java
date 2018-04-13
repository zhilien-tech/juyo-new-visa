package com.juyo.visa.admin.backMail.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class BackMailInfoEntity implements Serializable {
	/**主键*/
	private Integer id;

	/**申请人id*/
	private Integer applicantid;

	/**日本申请人id*/
	private Integer applicantjpid;

	/**资料来源*/
	private Integer source;

	/**回邮方式*/
	private Integer expresstype;

	/**团队名称*/
	private String teamname;

	/**快递号*/
	private String expressnum;

	/**回邮地址*/
	private String expressaddress;

	/**联系人*/
	private String linkman;

	/**电话*/
	private String telephone;

	/**发票项目内容*/
	private String invoicecontent;

	/**发票抬头*/
	private String invoicehead;

	/**税号*/
	private String taxnum;

	/**备注*/
	private String remark;

	/**操作人*/
	private Integer opid;

	/**创建时间*/
	private Date createtime;

	/**更新时间*/
	private Date updatetime;

}