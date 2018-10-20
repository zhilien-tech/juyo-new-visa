package com.juyo.visa.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TOrderUsAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**订单号*/
	private String ordernumber;

	/**公司id*/
	private Integer comid;

	/**用户id*/
	private Integer userid;

	/**团名*/
	private String groupname;

	/**大客户公司名称*/
	private String bigcustomername;

	private String reviewurl;

	private String pdfurl;

	private String daturl;

	/**领区*/
	private Integer cityid;

	/**订单状态*/
	private Integer status;

	/**是否作废*/
	private Integer isdisable;

	/**是否付款*/
	private Integer ispayed;

	/**操作人*/
	private Integer opid;

	/**创建时间*/
	private Date createtime;

	/**更新时间*/
	private Date updatetime;

}