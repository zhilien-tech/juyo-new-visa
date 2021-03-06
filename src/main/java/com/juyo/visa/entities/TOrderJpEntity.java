package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_order_jp")
public class TOrderJpEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("订单id")
	private Integer orderId;

	@Column
	@Comment("签证类型")
	private Integer visaType;

	@Column
	@Comment("签证县")
	private String visaCounty;

	@Column
	@Comment("过去三年是否访问")
	private Integer isVisit;

	@Column
	@Comment("房间数")
	private Integer roomcount;

	@Column
	@Comment("过去三年访问过的县")
	private String threeCounty;

	@Column
	@Comment("受付番号")
	private String acceptDesign;

	@Column
	@Comment("签证状态")
	private Integer visastatus;

	@Column
	@Comment("备注")
	private String remark;

	@Column
	@Comment("归国报告文件地址")
	private String returnHomeFileUrl;

	@Column
	@Comment("递送交接单时间")
	private Date deliveryDataTime;

	@Column
	@Comment("送签社id")
	private Integer sendsignid;

	@Column
	@Comment("地接社id")
	private Integer groundconnectid;

	@Column
	@Comment("发招宝时间")
	private Date zhaobaotime;

	@Column
	@Comment("错误代码")
	private Integer errorcode;

	@Column
	@Comment("错误消息")
	private String errormsg;

	@Column
	@Comment("上传名簿的URL")
	private String excelurl;

	@Column
	@Comment("金额")
	private String amount;

	@Column
	@Comment("上次赴日时间")
	private Date laststartdate;

	@Column
	@Comment("上次停留天数")
	private Integer laststayday;

	@Column
	@Comment("上次返回时间")
	private Date lastreturndate;

}