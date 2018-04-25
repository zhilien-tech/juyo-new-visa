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
	private Integer staffid;

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
	@Comment("街道地址(次选)")
	private String secaddress;

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
	@Comment("是否上过中学")
	private Integer issecondarylevel;

	@Column
	@Comment("是否属于氏族或部落")
	private Integer isclan;

	@Column
	@Comment("氏族或部落名称")
	private String clanname;

	@Column
	@Comment("过去五年是否去过任何国家旅游")
	private Integer istraveledanycountry;

	@Column
	@Comment("是否参加过慈善组织")
	private Integer isworkedcharitableorganization;

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
	@Comment("街道地址(次选)(英文)")
	private String secaddressen;

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
	@Comment("是否上过中学(英文)")
	private Integer issecondarylevelen;

	@Column
	@Comment("是否属于氏族或部落(英文)")
	private Integer isclanen;

	@Column
	@Comment("氏族或部落名称(英文)")
	private String clannameen;

	@Column
	@Comment("过去五年是否去过任何国家旅游(英文)")
	private Integer istraveledanycountryen;

	@Column
	@Comment("是否参加过慈善组织(英文)")
	private Integer isworkedcharitableorganizationen;

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
	@Comment("是否参与或参加过准军事部队、治安单位、叛乱集团、游击队或叛乱组织(英文)")
	private Integer isservedinrebelgroupen;

}