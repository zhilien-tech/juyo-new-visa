package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_app_events_introduce")
public class TAppEventsIntroduceEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("活动id")
	private Integer eventsId;
	
	@Column
    @Comment("活动介绍图片url")
	private String introductionUrl;
	

}