package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_order_us_travelinfo")
public class TOrderUsTravelinfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("订单id")
	private Integer orderid;

	@Column
	@Comment("出行目的")
	private String travelpurpose;

	@Column
	@Comment("指定")
	private Integer specify;

	@Column
	@Comment("赞助团体/组织")
	private String sponsorshipgroup;

	@Column
	@Comment("联系人姓")
	private String firstname;

	@Column
	@Comment("联系人名")
	private String lastname;

	@Column
	@Comment("美国地址")
	private String address;

	@Column
	@Comment("美国地址(英文)")
	private String addressen;

	@Column
	@Comment("市")
	private String city;

	@Column
	@Comment("市(英文)")
	private String cityen;

	@Column
	@Comment("州")
	private Integer state;

	@Column
	@Comment("邮政编码")
	private String zipcode;

	@Column
	@Comment("电话号码")
	private String telephone;

	@Column
	@Comment("申请编号")
	private String petitionnumber;

	@Column
	@Comment("是否有具体的旅行计划")
	private Integer hastripplan;

	@Column
	@Comment("预计出发日期")
	private Date godate;

	@Column
	@Comment("抵达美国日期")
	private Date arrivedate;

	@Column
	@Comment("停留天数")
	private Integer staydays;

	@Column
	@Comment("停留天数单位")
	private Integer stayunit;

	@Column
	@Comment("离开美国日期")
	private Date leavedate;

	@Column
	@Comment("出发城市(去程)")
	private Integer godeparturecity;

	@Column
	@Comment("抵达城市(去程)")
	private Integer goArrivedCity;

	@Column
	@Comment("航班号(去程)")
	private String goFlightNum;

	@Column
	@Comment("出发城市(返程)")
	private Integer returnDepartureCity;

	@Column
	@Comment("返回城市(返程)")
	private Integer returnArrivedCity;

	@Column
	@Comment("航班号(返程)")
	private String returnFlightNum;

	@Column
	@Comment("计划去美国的地点(州)")
	private Integer planstate;

	@Column
	@Comment("计划去美国的地点(市)")
	private String plancity;

	@Column
	@Comment("计划去美国的地点(地址)")
	private String planaddress;

}