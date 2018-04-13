package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffConscientiousAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**人员id*/
	private Integer staffid;
		
	/**服兵役国家*/
	private Integer militarycountry;
		
	/**服务分支*/
	private String servicebranch;
		
	/**排名*/
	private String rank;
		
	/**军事专业*/
	private String militaryspecialty;
		
	/**服兵役开始时间*/
	private Date servicestartdate;
		
	/**服兵役结束时间*/
	private Date serviceenddate;
		
	/**服兵役国家(英文)*/
	private Integer militarycountryen;
		
	/**服务分支(英文)*/
	private String servicebranchen;
		
	/**排名(英文)*/
	private String ranken;
		
	/**军事专业(英文)*/
	private String militaryspecialtyen;
		
	/**服兵役开始时间(英文)*/
	private Date servicestartdateen;
		
	/**服兵役结束时间(英文)*/
	private Date serviceenddateen;
		
}