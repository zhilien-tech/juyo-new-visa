package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_customer_company_map")
public class TCustomerCompanyMapEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("大客户id")
	private Integer bigCustomerId;

	@Column
	@Comment("所属公司id")
	private Integer belongComId;

}