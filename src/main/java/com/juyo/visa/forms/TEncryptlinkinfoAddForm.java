package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TEncryptlinkinfoAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**原链接*/
	private String originallink;
		
	/**加密链接*/
	private String encryptlink;
		
	/**创建时间*/
	private Date createTime;
		
	/**操作人*/
	private Integer opId;
		
}