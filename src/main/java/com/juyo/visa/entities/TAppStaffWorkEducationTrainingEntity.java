package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_app_staff_work_education_training")
public class TAppStaffWorkEducationTrainingEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("人员id")
	private String staffid;

	@Column
	@Comment("我的职业")
	private Integer occupation;

	@Column
	@Comment("所需资料")
	private String preparematerials;

	@Column
	@Comment("单位名称")
	private String unitname;

	@Column
	@Comment("街道地址")
	private String address;

	@Column
	@Comment("市")
	private String city;

	@Column
	@Comment("省")
	private String province;

	@Column
	@Comment("省是否适用")
	private Integer isprovinceapply;

	@Column
	@Comment("邮政编码")
	private String zipcode;

	@Column
	@Comment("邮政编码是否适用")
	private Integer iszipcodeapply;

	@Column
	@Comment("国家")
	private Integer country;

	@Column
	@Comment("电话")
	private String telephone;

	@Column
	@Comment("工作开始日期")
	private Date workstartdate;

	@Column
	@Comment("当地月收入(如果雇用)")
	private Double salary;

	@Column
	@Comment("月收入是否适用")
	private Integer issalaryapply;

	@Column
	@Comment("工作职责")
	private String duty;

	@Column
	@Comment("不受雇用说明")
	private String notemployedexplain;

	@Column
	@Comment("指定其他")
	private String specifyother;

	@Column
	@Comment("以前是否工作过")
	private Integer isemployed;

	@Column
	@Comment("雇主姓名")
	private String employername;

	@Column
	@Comment("雇主街道地址")
	private String employeraddress;

	@Column
	@Comment("雇主所在市")
	private String employercity;

	@Column
	@Comment("雇主所在省")
	private String employerprovince;

	@Column
	@Comment("雇主所在省是否适用")
	private Integer isemployerprovinceapply;

	@Column
	@Comment("雇主邮政编码")
	private String employerzipcode;

	@Column
	@Comment("雇主邮政编码是否适用")
	private Integer isemployerzipcodeapply;

	@Column
	@Comment("雇主所在国家")
	private Integer employercountry;

	@Column
	@Comment("雇主电话")
	private String employertelephone;

	@Column
	@Comment("职称")
	private String jobtitle;

	@Column
	@Comment("主管的姓")
	private String supervisorfirstname;

	@Column
	@Comment("是否知道主管的姓")
	private Integer isknowsupervisorfirstname;

	@Column
	@Comment("主管的名")
	private String supervisorlastname;

	@Column
	@Comment("是否知道主管的名")
	private Integer isknowsupervisorlastname;

	@Column
	@Comment("入职时间")
	private Date employstartdate;

	@Column
	@Comment("离职时间")
	private Date employenddate;

	@Column
	@Comment("以前的工作职责")
	private String previousduty;

	@Column
	@Comment("是否上过中学")
	private Integer issecondarylevel;

	@Column
	@Comment("机构名称")
	private String institution;

	@Column
	@Comment("机构地址")
	private String institutionaddress;

	@Column
	@Comment("机构所在市")
	private String institutioncity;

	@Column
	@Comment("机构所在省")
	private String institutionprovince;

	@Column
	@Comment("机构所在省是否适用")
	private Integer isinstitutionprovinceapply;

	@Column
	@Comment("机构邮政编码")
	private String institutionzipcode;

	@Column
	@Comment("机构邮政编码是否适用")
	private Integer isinstitutionzipcodeapply;

	@Column
	@Comment("机构所在国家")
	private Integer institutioncountry;

	@Column
	@Comment("学科")
	private String course;

	@Column
	@Comment("课程开始时间")
	private Date coursestartdate;

	@Column
	@Comment("课程结束时间")
	private Date courseenddate;

	@Column
	@Comment("是否属于氏族或部落")
	private Integer isclan;

	@Column
	@Comment("氏族或部落名称")
	private String clanname;

	@Column
	@Comment("使用的语言名称")
	private String languagename;

	@Column
	@Comment("过去五年是否去过任何国家旅游")
	private Integer istraveledanycountry;

	@Column
	@Comment("去旅游的国家")
	private Integer traveledcountry;

	@Column
	@Comment("是否参加过慈善组织")
	private Integer isworkedcharitableorganization;

	@Column
	@Comment("组织名称")
	private String organizationname;

	@Column
	@Comment("是否有专业技能")
	private Integer hasspecializedskill;

	@Column
	@Comment("专业技能说明")
	private String skillexplain;

	@Column
	@Comment("是否曾服兵役")
	private Integer hasservedinmilitary;

	@Column
	@Comment("服兵役国家")
	private Integer militarycountry;

	@Column
	@Comment("服务分支")
	private String servicebranch;

	@Column
	@Comment("排名")
	private String rank;

	@Column
	@Comment("军事专业")
	private String militaryspecialty;

	@Column
	@Comment("服兵役开始时间")
	private Date servicestartdate;

	@Column
	@Comment("服兵役结束时间")
	private Date serviceenddate;

	@Column
	@Comment("是否参与或参加过准军事部队、治安单位、叛乱集团、游击队或叛乱组织")
	private Integer isservedinrebelgroup;

	@Column
	@Comment("我的职业(英文)")
	private Integer occupationen;

	@Column
	@Comment("单位名称(英文)")
	private String unitnameen;

	@Column
	@Comment("街道地址(英文)")
	private String addressen;

	@Column
	@Comment("市(英文)")
	private String cityen;

	@Column
	@Comment("省(英文)")
	private String provinceen;

	@Column
	@Comment("省是否适用(英文)")
	private Integer isprovinceapplyen;

	@Column
	@Comment("邮政编码(英文)")
	private String zipcodeen;

	@Column
	@Comment("邮政编码是否适用(英文)")
	private Integer iszipcodeapplyen;

	@Column
	@Comment("国家(英文)")
	private Integer countryen;

	@Column
	@Comment("电话(英文)")
	private String telephoneen;

	@Column
	@Comment("工作开始日期(英文)")
	private Date workstartdateen;

	@Column
	@Comment("当地月收入(如果雇用)(英文)")
	private Double salaryen;

	@Column
	@Comment("月收入是否适用(英文)")
	private Integer issalaryapplyen;

	@Column
	@Comment("工作职责(英文)")
	private String dutyen;

	@Column
	@Comment("不受雇用说明(英文)")
	private String notemployedexplainen;

	@Column
	@Comment("指定其他(英文)")
	private String specifyotheren;

	@Column
	@Comment("以前是否工作过(英文)")
	private Integer isemployeden;

	@Column
	@Comment("雇主姓名(英文)")
	private String employernameen;

	@Column
	@Comment("雇主街道地址(英文)")
	private String employeraddressen;

	@Column
	@Comment("雇主所在市(英文)")
	private String employercityen;

	@Column
	@Comment("雇主所在省(英文)")
	private String employerprovinceen;

	@Column
	@Comment("雇主所在省是否适用(英文)")
	private Integer isemployerprovinceapplyen;

	@Column
	@Comment("雇主邮政编码(英文)")
	private String employerzipcodeen;

	@Column
	@Comment("雇主邮政编码是否适用(英文)")
	private Integer isemployerzipcodeapplyen;

	@Column
	@Comment("雇主所在国家(英文)")
	private Integer employercountryen;

	@Column
	@Comment("雇主电话(英文)")
	private String employertelephoneen;

	@Column
	@Comment("职称(英文)")
	private String jobtitleen;

	@Column
	@Comment("主管的姓(英文)")
	private String supervisorfirstnameen;

	@Column
	@Comment("是否知道主管的姓(英文)")
	private Integer isknowsupervisorfirstnameen;

	@Column
	@Comment("主管的名(英文)")
	private String supervisorlastnameen;

	@Column
	@Comment("是否知道主管的名(英文)")
	private Integer isknowsupervisorlastnameen;

	@Column
	@Comment("入职时间(英文)")
	private Date employstartdateen;

	@Column
	@Comment("离职时间(英文)")
	private Date employenddateen;

	@Column
	@Comment("以前的工作职责(英文)")
	private String previousdutyen;

	@Column
	@Comment("是否上过中学(英文)")
	private Integer issecondarylevelen;

	@Column
	@Comment("机构名称(英文)")
	private String institutionen;

	@Column
	@Comment("机构地址(英文)")
	private String institutionaddressen;

	@Column
	@Comment("机构所在市(英文)")
	private String institutioncityen;

	@Column
	@Comment("机构所在省(英文)")
	private String institutionprovinceen;

	@Column
	@Comment("机构所在省是否适用(英文)")
	private Integer isinstitutionprovinceapplyen;

	@Column
	@Comment("机构邮政编码(英文)")
	private String institutionzipcodeen;

	@Column
	@Comment("机构邮政编码是否适用(英文)")
	private Integer isinstitutionzipcodeapplyen;

	@Column
	@Comment("机构所在国家(英文)")
	private Integer institutioncountryen;

	@Column
	@Comment("学科(英文)")
	private String courseen;

	@Column
	@Comment("课程开始时间(英文)")
	private Date coursestartdateen;

	@Column
	@Comment("课程结束时间(英文)")
	private Date courseenddateen;

	@Column
	@Comment("是否属于氏族或部落(英文)")
	private Integer isclanen;

	@Column
	@Comment("氏族或部落名称(英文)")
	private String clannameen;

	@Column
	@Comment("使用的语言名称(英文)")
	private String languagenameen;

	@Column
	@Comment("过去五年是否去过任何国家旅游(英文)")
	private Integer istraveledanycountryen;

	@Column
	@Comment("去旅游的国家(英文)")
	private Integer traveledcountryen;

	@Column
	@Comment("是否参加过慈善组织(英文)")
	private Integer isworkedcharitableorganizationen;

	@Column
	@Comment("组织名称(英文)")
	private String organizationnameen;

	@Column
	@Comment("是否有专业技能(英文)")
	private Integer hasspecializedskillen;

	@Column
	@Comment("专业技能说明(英文)")
	private String skillexplainen;

	@Column
	@Comment("是否曾服兵役(英文)")
	private Integer hasservedinmilitaryen;

	@Column
	@Comment("服兵役国家(英文)")
	private Integer militarycountryen;

	@Column
	@Comment("服务分支(英文)")
	private String servicebranchen;

	@Column
	@Comment("排名(英文)")
	private String ranken;

	@Column
	@Comment("军事专业(英文)")
	private String militaryspecialtyen;

	@Column
	@Comment("服兵役开始时间(英文)")
	private Date servicestartdateen;

	@Column
	@Comment("服兵役结束时间(英文)")
	private Date serviceenddateen;

	@Column
	@Comment("是否参与或参加过准军事部队、治安单位、叛乱集团、游击队或叛乱组织(英文)")
	private Integer isservedinrebelgroupen;

}