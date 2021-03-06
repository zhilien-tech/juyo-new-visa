package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffPaperworkUsAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**人员id*/
	private Integer staffid;
		
	/**资料类型(同职业状况)*/
	private Integer type;
		
	/**真实资料(证件)*/
	private Integer content;
		
	/**资料本数*/
	private Integer count;
		
	/**操作人*/
	private Integer opid;
		
	/**创建时间*/
	private Date createtime;
		
	/**更新时间*/
	private Date updatetime;
		
	/**真实资料*/
	private String realinfo;
		
	/**状态 1：已收 0：未收*/
	private Integer status;
		
}