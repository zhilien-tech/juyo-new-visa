package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_app_events")
public class TAppEventsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("公司id")
	private Integer comid;

	@Column
	@Comment("活动号")
	private String eventsNum;

	@Column
	@Comment("活动名称")
	private String eventsName;

	@Column
	@Comment("活动图片url")
	private String pictureUrl;

	@Column
	@Comment("活动详情url")
	private String detailsUrl;

	@Column
	@Comment("截止日期")
	private Date dueDate;

	@Column
	@Comment("出发日期")
	private Date departureDate;

	@Column
	@Comment("返回时间")
	private Date returnDate;

	@Column
	@Comment("签证国家（关联签证流程）")
	private Integer visaCountry;

	@Column
	@Comment("访问城市")
	private String visitCity;

	@Column
	@Comment("注意事项")
	private String attentions;

	@Column
	@Comment("说明")
	private String descriptions;

	@Column
	@Comment("活动状态")
	private Integer status;

	@Column
	@Comment("是否发布")
	private Integer isPublish;

	@Column
	@Comment("是否作废")
	private Integer isInvalid;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("更新时间")
	private Date updateTime;

}