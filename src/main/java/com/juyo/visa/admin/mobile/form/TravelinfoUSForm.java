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
public class TravelinfoUSForm {

	private String encode;

	/**人员id*/
	private Integer staffid;

	/**是否与其他人一起旅行*/
	private Integer istravelwithother;

	/**是否与其他人一起旅行(英文)*/
	private Integer istravelwithotheren;

	/**是否作为团队或组织的一部分*/
	private Integer ispart;

	/**是否作为团队或组织的一部分(英文)*/
	private Integer isparten;

	/**团队名称*/
	private String groupname;

	/**团队名称(英文)*/
	private String groupnameen;

	/**是否去过美国*/
	private Integer hasbeeninus;

	/**是否有美国驾照*/
	private Integer hasdriverlicense;

	private Integer costpayer;

	private Integer costpayeren;

	private Integer emigrationreason;

	private Integer emigrationreasonen;

	/**是否有美国签证*/
	private Integer isissuedvisa;

	/**最后一次签证的签发日期*/
	private Date issueddate;

	/**签证号码*/
	private String visanumber;

	/**是否知道签证号码*/
	private Integer idknowvisanumber;

	/**是否在申请相同类型的签证*/
	private Integer isapplyingsametypevisa;

	/**是否在签发上述签证的国家或地区申请并且是您所在国家或地区的居住地*/
	private Integer issamecountry;

	/**你的美国签证是否丢失或被盗过*/
	private Integer islost;

	/**丢失年份*/
	private String lostyear;

	/**丢失说明*/
	private String lostexplain;

	/**是否采集过指纹*/
	private Integer istenprinted;

	/**你的美国签证是否被取消或撤销过*/
	private Integer iscancelled;

	/**签证被取消说明*/
	private String cancelexplain;

	/**是否被拒绝过美国签证，或被拒绝入境美国，或者撤回入境口岸的入境申请*/
	private Integer isrefused;

	/**拒绝原因*/
	private String refusedexplain;

	/**曾经是否是美国合法永久居民*/
	private Integer islegalpermanentresident;

	/**永久居民说明*/
	private String permanentresidentexplain;

	/**是否曾有人代表您向美国公民和移民服务局提交过移民申请*/
	private Integer isfiledimmigrantpetition;

	/**移民申请说明*/
	private String immigrantpetitionexplain;

	/**是否去过美国(英文)*/
	private Integer hasbeeninusen;

	/**是否有美国驾照(英文)*/
	private Integer hasdriverlicenseen;

	/**是否有美国签证(英文)*/
	private Integer isissuedvisaen;

	/**最后一次签证的签发日期(英文)*/
	private Date issueddateen;

	/**签证号码(英文)*/
	private String visanumberen;

	/**是否知道签证号码(英文)*/
	private Integer idknowvisanumberen;

	/**是否在申请相同类型的签证(英文)*/
	private Integer isapplyingsametypevisaen;

	/**是否在签发上述签证的国家或地区申请并且是您所在国家或地区的居住地(英文)*/
	private Integer issamecountryen;

	/**你的美国签证是否丢失或被盗过(英文)*/
	private Integer islosten;

	/**丢失年份(英文)*/
	private String lostyearen;

	/**丢失说明(英文)*/
	private String lostexplainen;

	/**是否采集过指纹(英文)*/
	private Integer istenprinteden;

	/**你的美国签证是否被取消或撤销过(英文)*/
	private Integer iscancelleden;

	/**签证被取消说明(英文)*/
	private String cancelexplainen;

	/**是否被拒绝过美国签证，或被拒绝入境美国，或者撤回入境口岸的入境申请(英文)*/
	private Integer isrefuseden;

	/**拒绝原因(英文)*/
	private String refusedexplainen;

	/**曾经是否是美国合法永久居民(英文)*/
	private Integer islegalpermanentresidenten;

	/**永久居民说明(英文)*/
	private String permanentresidentexplainen;

	/**是否曾有人代表您向美国公民和移民服务局提交过移民申请(英文)*/
	private Integer isfiledimmigrantpetitionen;

	/**移民申请说明(英文)*/
	private String immigrantpetitionexplainen;

	/**抵达日期*/
	private Date arrivedate;

	private Date arrivedateen;

	/**停留时间*/
	private Integer staydays;

	private Integer staydaysen;

	/**日期单位*/
	private Integer dateunit;

	private Integer dateuniten;

	/**过去五年是否去过任何国家旅游*/
	private Integer istraveledanycountry;

	private Integer istraveledanycountryen;

	/**去旅游的国家*/
	private String traveledcountry;

	/**去旅游的国家(英文)*/
	private String traveledcountryen;

	private String driverlicensenumber;

	private Integer witchstateofdriver;

	private String companioninfoList;

	private String gocountry;

}
