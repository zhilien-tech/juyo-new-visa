package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppStaffWorkEducationTrainingUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**人员id*/
	private Integer staffid;
		
	/**我的职业*/
	private Integer occupation;
		
	/**所需资料*/
	private String preparematerials;
		
	/**单位名称*/
	private String unitname;
		
	/**街道地址*/
	private String address;
		
	/**市*/
	private String city;
		
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
		
}