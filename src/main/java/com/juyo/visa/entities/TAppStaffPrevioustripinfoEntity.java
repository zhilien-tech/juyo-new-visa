package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_app_staff_previoustripinfo")
public class TAppStaffPrevioustripinfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("人员id")
	private String staffid;

	@Column
	@Comment("是否去过美国")
	private Integer hasbeeninus;

	@Column
	@Comment("抵达日期")
	private Date arrivedate;

	@Column
	@Comment("停留时间")
	private Integer staydays;

	@Column
	@Comment("日期单位")
	private Integer dateunit;

	@Column
	@Comment("日期单位(英文)")
	private Integer dateuniten;

	@Column
	@Comment("是否有美国驾照")
	private Integer hasdriverlicense;

	@Column
	@Comment("驾照号")
	private String driverlicensenumber;

	@Column
	@Comment("是否知道驾照号")
	private Integer isknowdrivernumber;

	@Column
	@Comment("哪个州的驾照")
	private Integer witchstateofdriver;

	@Column
	@Comment("是否有美国签证")
	private Integer isissuedvisa;

	@Column
	@Comment("最后一次签证的签发日期")
	private Date issueddate;

	@Column
	@Comment("签证号码")
	private String visanumber;

	@Column
	@Comment("是否知道签证号码")
	private Integer idknowvisanumber;

	@Column
	@Comment("是否在申请相同类型的签证")
	private Integer isapplyingsametypevisa;

	@Column
	@Comment("是否在签发上述签证的国家或地区申请并且是您所在国家或地区的居住地")
	private Integer issamecountry;

	@Column
	@Comment("你的美国签证是否丢失或被盗过")
	private Integer islost;

	@Column
	@Comment("丢失年份")
	private String lostyear;

	@Column
	@Comment("丢失说明")
	private String lostexplain;

	@Column
	@Comment("是否采集过指纹")
	private Integer istenprinted;

	@Column
	@Comment("你的美国签证是否被取消或撤销过")
	private Integer iscancelled;

	@Column
	@Comment("签证被取消说明")
	private String cancelexplain;

	@Column
	@Comment("是否被拒绝过美国签证，或被拒绝入境美国，或者撤回入境口岸的入境申请")
	private Integer isrefused;

	@Column
	@Comment("拒绝原因")
	private String refusedexplain;

	@Column
	@Comment("曾经是否是美国合法永久居民")
	private Integer islegalpermanentresident;

	@Column
	@Comment("永久居民说明")
	private String permanentresidentexplain;

	@Column
	@Comment("是否曾有人代表您向美国公民和移民服务局提交过移民申请")
	private Integer isfiledimmigrantpetition;

	@Column
	@Comment("移民申请说明")
	private String immigrantpetitionexplain;

	@Column
	@Comment("是否去过美国(英文)")
	private Integer hasbeeninusen;

	@Column
	@Comment("抵达日期(英文)")
	private Date arrivedateen;

	@Column
	@Comment("停留时间(英文)")
	private Integer staydaysen;

	@Column
	@Comment("是否有美国驾照(英文)")
	private Integer hasdriverlicenseen;

	@Column
	@Comment("驾照号(英文)")
	private String driverlicensenumberen;

	@Column
	@Comment("是否知道驾照号(英文)")
	private Integer isknowdrivernumberen;

	@Column
	@Comment("哪个州的驾照(英文)")
	private Integer witchstateofdriveren;

	@Column
	@Comment("是否有美国签证(英文)")
	private Integer isissuedvisaen;

	@Column
	@Comment("最后一次签证的签发日期(英文)")
	private Date issueddateen;

	@Column
	@Comment("签证号码(英文)")
	private String visanumberen;

	@Column
	@Comment("是否知道签证号码(英文)")
	private Integer idknowvisanumberen;

	@Column
	@Comment("是否在申请相同类型的签证(英文)")
	private Integer isapplyingsametypevisaen;

	@Column
	@Comment("是否在签发上述签证的国家或地区申请并且是您所在国家或地区的居住地(英文)")
	private Integer issamecountryen;

	@Column
	@Comment("你的美国签证是否丢失或被盗过(英文)")
	private Integer islosten;

	@Column
	@Comment("丢失年份(英文)")
	private String lostyearen;

	@Column
	@Comment("丢失说明(英文)")
	private String lostexplainen;

	@Column
	@Comment("是否采集过指纹(英文)")
	private Integer istenprinteden;

	@Column
	@Comment("你的美国签证是否被取消或撤销过(英文)")
	private Integer iscancelleden;

	@Column
	@Comment("签证被取消说明(英文)")
	private String cancelexplainen;

	@Column
	@Comment("是否被拒绝过美国签证，或被拒绝入境美国，或者撤回入境口岸的入境申请(英文)")
	private Integer isrefuseden;

	@Column
	@Comment("拒绝原因(英文)")
	private String refusedexplainen;

	@Column
	@Comment("曾经是否是美国合法永久居民(英文)")
	private Integer islegalpermanentresidenten;

	@Column
	@Comment("永久居民说明(英文)")
	private String permanentresidentexplainen;

	@Column
	@Comment("是否曾有人代表您向美国公民和移民服务局提交过移民申请(英文)")
	private Integer isfiledimmigrantpetitionen;

	@Column
	@Comment("移民申请说明(英文)")
	private String immigrantpetitionexplainen;

}