package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffCredentialsUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**人员id*/
	private Integer staffId;
		
	/**主证件id*/
	private Integer mainId;
		
	/**证件url*/
	private String url;
		
	/**证件类型*/
	private Integer type;
		
	/**证件状态*/
	private Integer status;
		
	/**证件序号*/
	private Integer sequence;
		
	/**页面元素id*/
	private String pageElementId;
		
	/**创建时间*/
	private Date createtime;
		
	/**更新时间*/
	private Date updatetime;
		
}