package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffBeforeworkUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**人员id*/
	private Integer staffid;
		
	/**雇主姓名*/
	private String employername;
		
	/**雇主街道地址*/
	private String employeraddress;
		
	/**雇主街道地址(次选)*/
	private String employeraddressSec;
		
	/**雇主所在市*/
	private String employercity;
		
	/**雇主所在省*/
	private String employerprovince;
		
	/**雇主所在省是否适用*/
	private Integer isemployerprovinceapply;
		
	/**雇主邮政编码*/
	private String employerzipcode;
		
	/**雇主邮政编码是否适用*/
	private Integer isemployerzipcodeapply;
		
	/**雇主所在国家*/
	private Integer employercountry;
		
	/**雇主电话*/
	private String employertelephone;
		
	/**职称*/
	private String jobtitle;
		
	/**主管的姓*/
	private String supervisorfirstname;
		
	/**是否知道主管的姓*/
	private Integer isknowsupervisorfirstname;
		
	/**主管的名*/
	private String supervisorlastname;
		
	/**是否知道主管的名*/
	private Integer isknowsupervisorlastname;
		
	/**入职时间*/
	private Date employstartdate;
		
	/**离职时间*/
	private Date employenddate;
		
	/**以前的工作职责*/
	private String previousduty;
		
	/**雇主姓名(英文)*/
	private String employernameen;
		
	/**雇主街道地址(英文)*/
	private String employeraddressen;
		
	/**雇主街道地址(次选)(英文)*/
	private String employeraddressSecen;
		
	/**雇主所在市(英文)*/
	private String employercityen;
		
	/**雇主所在省(英文)*/
	private String employerprovinceen;
		
	/**雇主所在省是否适用(英文)*/
	private Integer isemployerprovinceapplyen;
		
	/**雇主邮政编码(英文)*/
	private String employerzipcodeen;
		
	/**雇主邮政编码是否适用(英文)*/
	private Integer isemployerzipcodeapplyen;
		
	/**雇主所在国家(英文)*/
	private Integer employercountryen;
		
	/**雇主电话(英文)*/
	private String employertelephoneen;
		
	/**职称(英文)*/
	private String jobtitleen;
		
	/**主管的姓(英文)*/
	private String supervisorfirstnameen;
		
	/**是否知道主管的姓(英文)*/
	private Integer isknowsupervisorfirstnameen;
		
	/**主管的名(英文)*/
	private String supervisorlastnameen;
		
	/**是否知道主管的名(英文)*/
	private Integer isknowsupervisorlastnameen;
		
	/**入职时间(英文)*/
	private Date employstartdateen;
		
	/**离职时间(英文)*/
	private Date employenddateen;
		
	/**以前的工作职责(英文)*/
	private String previousdutyen;
		
}