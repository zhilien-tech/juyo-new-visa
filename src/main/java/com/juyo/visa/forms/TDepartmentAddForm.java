package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TDepartmentAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**公司id*/
	private Integer comId;
		
	/**部门名称*/
	private String deptName;
		
	/**创建时间*/
	private Date createTime;
		
	/**修改时间*/
	private Date updateTime;
		
	/**备注*/
	private String remark;
		
	/**操作人id*/
	private Integer opId;
		
}