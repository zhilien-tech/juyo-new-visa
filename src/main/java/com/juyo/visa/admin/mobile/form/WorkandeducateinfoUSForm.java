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
 * @Date	 2018年8月22日 	 
 */
@Data
public class WorkandeducateinfoUSForm {

	/**主键*/
	private Integer id;

	private String encode;

	/**人员id*/
	private Integer staffid;

	/**机构名称*/
	private String institution;

	private String position;

	private String positionen;

	private Integer highesteducation;

	private Integer highesteducationen;

	/**机构地址*/
	private String institutionaddress;

	/**机构地址次选*/
	private String secinstitutionaddress;

	/**机构所在市*/
	private String institutioncity;

	/**机构所在省*/
	private String institutionprovince;

	/**机构所在省是否适用*/
	private Integer isinstitutionprovinceapply;

	/**机构邮政编码*/
	private String institutionzipcode;

	/**机构邮政编码是否适用*/
	private Integer isinstitutionzipcodeapply;

	/**机构所在国家*/
	private Integer institutioncountry;

	/**学科*/
	private String course;

	/**课程开始时间*/
	private Date coursestartdate;

	/**课程结束时间*/
	private Date courseenddate;

	/**机构名称(英文)*/
	private String institutionen;

	/**机构地址(英文)*/
	private String institutionaddressen;

	/**机构地址次选(英文)*/
	private String secinstitutionaddressen;

	/**机构所在市(英文)*/
	private String institutioncityen;

	/**机构所在省(英文)*/
	private String institutionprovinceen;

	/**机构所在省是否适用(英文)*/
	private Integer isinstitutionprovinceapplyen;

	/**机构邮政编码(英文)*/
	private String institutionzipcodeen;

	/**机构邮政编码是否适用(英文)*/
	private Integer isinstitutionzipcodeapplyen;

	/**机构所在国家(英文)*/
	private Integer institutioncountryen;

	/**学科(英文)*/
	private String courseen;

	/**课程开始时间(英文)*/
	private Date coursestartdateen;

	/**课程结束时间(英文)*/
	private Date courseenddateen;

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

	/**我的职业*/
	private Integer occupation;

	/**所需资料*/
	private String preparematerials;

	/**单位名称*/
	private String unitname;

	/**市*/
	private String city;

	/**街道地址*/
	private String address;

	/**省*/
	private String province;

	/**省是否适用*/
	private Integer isprovinceapply;

	/**邮政编码*/
	private String zipcode;

	/**邮政编码是否适用*/
	private Integer iszipcodeapply;

	/**国家*/
	private Integer country;

	/**电话*/
	private String telephone;

	/**工作开始日期*/
	private Date workstartdate;

	/**当地月收入(如果雇用)*/
	private Double salary;

	/**月收入是否适用*/
	private Integer issalaryapply;

	/**工作职责*/
	private String duty;

	/**不受雇用说明*/
	private String notemployedexplain;

	/**指定其他*/
	private String specifyother;

	/**以前是否工作过*/
	private Integer isemployed;

	/**是否上过中学*/
	private Integer issecondarylevel;

	/**是否属于氏族或部落*/
	private Integer isclan;

	/**氏族或部落名称*/
	private String clanname;

	/**过去五年是否去过任何国家旅游*/
	private Integer istraveledanycountry;

	/**是否参加过慈善组织*/
	private Integer isworkedcharitableorganization;

	/**是否有专业技能*/
	private Integer hasspecializedskill;

	/**专业技能说明*/
	private String skillexplain;

	/**是否曾服兵役*/
	private Integer hasservedinmilitary;

	/**是否参与或参加过准军事部队、治安单位、叛乱集团、游击队或叛乱组织*/
	private Integer isservedinrebelgroup;

	/**我的职业(英文)*/
	private Integer occupationen;

	/**单位名称(英文)*/
	private String unitnameen;

	/**街道地址(英文)*/
	private String addressen;

	/**市(英文)*/
	private String cityen;

	/**街道地址(次选)(英文)*/
	private String secaddressen;

	/**省(英文)*/
	private String provinceen;

	/**省是否适用(英文)*/
	private Integer isprovinceapplyen;

	/**邮政编码(英文)*/
	private String zipcodeen;

	/**邮政编码是否适用(英文)*/
	private Integer iszipcodeapplyen;

	/**国家(英文)*/
	private Integer countryen;

	/**电话(英文)*/
	private String telephoneen;

	/**工作开始日期(英文)*/
	private Date workstartdateen;

	/**当地月收入(如果雇用)(英文)*/
	private Double salaryen;

	/**月收入是否适用(英文)*/
	private Integer issalaryapplyen;

	/**工作职责(英文)*/
	private String dutyen;

	/**不受雇用说明(英文)*/
	private String notemployedexplainen;

	/**指定其他(英文)*/
	private String specifyotheren;

	/**以前是否工作过(英文)*/
	private Integer isemployeden;

	/**是否上过中学(英文)*/
	private Integer issecondarylevelen;

	/**是否属于氏族或部落(英文)*/
	private Integer isclanen;

	/**氏族或部落名称(英文)*/
	private String clannameen;

	/**过去五年是否去过任何国家旅游(英文)*/
	private Integer istraveledanycountryen;

	/**是否参加过慈善组织(英文)*/
	private Integer isworkedcharitableorganizationen;

	/**是否有专业技能(英文)*/
	private Integer hasspecializedskillen;

	/**专业技能说明(英文)*/
	private String skillexplainen;

	/**是否曾服兵役(英文)*/
	private Integer hasservedinmilitaryen;

	/**是否参与或参加过准军事部队、治安单位、叛乱集团、游击队或叛乱组织(英文)*/
	private Integer isservedinrebelgroupen;

	/**街道地址(次选)*/
	private String secaddress;

	/**证明人姓名*/
	private String witnessname;

	/**证明人电话*/
	private String witnessmoblie;

	/**证明人职务*/
	private String witnessposition;

	/**证明人姓名(英文)*/
	private String witnessnameen;

	/**证明人电话(英文)*/
	private String witnessmoblieen;

	/**证明人职务(英文)*/
	private String witnesspositionen;

}
