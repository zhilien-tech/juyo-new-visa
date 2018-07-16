package com.juyo.visa.forms;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TFlightAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**航班号*/
	private String flightnum;

	/**航空公司*/
	private String airlinecomp;

	/**起飞机场*/
	private String takeOffName;

	private String flytime;

	private Integer relationflight;

	/**起飞机场三字代码*/
	private String takeOffCode;

	/**降落机场*/
	private String landingName;

	/**降落机场三字代码*/
	private String landingCode;

	/**起飞城市id*/
	private Integer takeOffCityId;

	/**降落城市id*/
	private Integer landingCityId;

	/**起飞时间*/
	private String takeOffTime;

	/**降落时间*/
	private String landingTime;

	/**起飞航站楼*/
	private String takeOffTerminal;

	/**降落航站楼*/
	private String landingTerminal;

	/**创建时间*/
	private Date createTime;

	/**更新时间*/
	private Date updateTime;

}