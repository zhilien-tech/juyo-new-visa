package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppEventsUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**公司id*/
	private Integer comid;
		
	/**活动号*/
	private String eventsNum;
		
	/**活动名称*/
	private String eventsName;
		
	/**活动图片url*/
	private String pictureUrl;
		
	/**截止日期*/
	private Date dueDate;
		
	/**出发日期*/
	private Date departureDate;
		
	/**返回时间*/
	private Date returnDate;
		
	/**签证国家（关联签证流程）*/
	private Integer visaCountry;
		
	/**访问城市*/
	private String visitCity;
		
	/**注意事项*/
	private String attentions;
		
	/**说明*/
	private String descriptions;
		
	/**活动状态*/
	private Integer status;
		
	/**是否发布*/
	private Integer isPublish;
		
	/**是否作废*/
	private Integer isInvalid;
		
	/**创建时间*/
	private Date createTime;
		
	/**更新时间*/
	private Date updateTime;
		
}