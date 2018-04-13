package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TOrderUsFollowupUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**订单id*/
	private Integer orderid;
		
	/**用户id*/
	private Integer userid;
		
	/**内容*/
	private String content;
		
	/**解决人*/
	private Integer solveid;
		
	/**解决时间*/
	private Date solvetime;
		
	/**状态*/
	private Integer status;
		
	/**创建时间*/
	private Date createtime;
		
	/**更新时间*/
	private Date updatetime;
		
}