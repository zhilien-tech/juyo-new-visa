package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TOrderRecipientUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**订单id*/
	private Integer orderId;
		
	/**收件地址id*/
	private Integer receiveAddressId;
		
	/**快递方式*/
	private Integer expressType;
		
	/**收件人*/
	private String receiver;
		
	/**电话*/
	private String telephone;
		
	/**收件地址*/
	private String expressAddress;
		
	/**操作人*/
	private Integer opId;
		
	/**创建时间*/
	private Date createTime;
		
	/**更新时间*/
	private Date updateTime;
		
}