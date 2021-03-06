package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TComJobUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**职位id*/
	private Integer jobId;
		
	/**公司id*/
	private Integer comId;
		
}