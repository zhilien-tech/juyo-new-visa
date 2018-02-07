package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAppEventsIntroduceAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**活动id*/
	private Integer eventsId;
		
	/**活动介绍图片url*/
	private String introductionUrl;
		
}