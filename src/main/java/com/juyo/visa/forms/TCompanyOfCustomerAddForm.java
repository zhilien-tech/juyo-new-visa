package com.juyo.visa.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCompanyOfCustomerAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**公司id*/
	private Integer comid;
	
	/**送签社公司id*/
	private Integer sendcomid;
		
	/**公司名称*/
	private String fullname;
		
	/**公司简称*/
	private String shortname;
		
	/**指定番号*/
	private String designatedNum;
		
	/**联系人*/
	private String linkman;
		
	/**电话*/
	private String mobile;
		
	/**邮箱*/
	private String email;
		
	/**地址*/
	private String address;
		
	/**操作人*/
	private Integer opId;
		
	/**创建时间*/
	private Date createTime;
		
	/**修改时间*/
	private Date updateTime;
		
}