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
	private Integer staffid;

	@Column
	@Comment("是否去过美国")
	private Integer hasbeeninus;

	@Column
	@Comment("申请移民的理由")
	private Integer emigrationreason;

	@Column
	@Comment("申请移民的理由(英文)")
	private Integer emigrationreasonen;

	@Column
	@Comment("费用付款人")
	private Integer costpayer;

	@Column
	@Comment("费用付款人(英文)")
	private Integer costpayeren;

	@Column
	@Comment("公司/组织名称")
	private String comname;
	@Column
	@Comment("公司/组织名称(英文)")
	private String comnameen;

	@Column
	@Comment("公司电话")
	private String comtelephone;

	@Column
	@Comment("公司与你的关系")
	private String comrelationwithyou;

	@Column
	@Comment("公司与你的关系(英文)")
	private String comrelationwithyouen;

	@Column
	@Comment("支付人国家")
	private String paycountry;

	@Column
	@Comment("支付人国家(英文)")
	private String paycountryen;

	@Column
	@Comment("支付人省份(英文)")
	private String payprovinceen;

	@Column
	@Comment("支付人省份")
	private String payprovince;

	@Column
	@Comment("支付人城市")
	private String paycity;

	@Column
	@Comment("支付人城市(英文)")
	private String paycityen;

	@Column
	@Comment("支付人地址(英文)")
	private String payaddressen;

	@Column
	@Comment("支付人地址")
	private String payaddress;

	@Column
	@Comment("支付人姓")
	private String payfirstname;

	@Column
	@Comment("支付人姓(英文)")
	private String payfirstnameen;

	@Column
	@Comment("支付人名(英文)")
	private String paylastnameen;

	@Column
	@Comment("支付人名")
	private String paylastname;

	@Column
	@Comment("支付人电话")
	private String paytelephone;

	@Column
	@Comment("支付人邮箱")
	private String paymail;

	@Column
	@Comment("支付人与你的关系")
	private Integer payrelationwithyou;

	@Column
	@Comment("支付人的地址是否与你一样")
	private Integer payaddressissamewithyou;

	@Column
	@Comment("是否有美国驾照")
	private Integer hasdriverlicense;

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
	@Comment("是否有美国驾照(英文)")
	private Integer hasdriverlicenseen;

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