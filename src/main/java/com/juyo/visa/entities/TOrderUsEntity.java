package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_order_us")
public class TOrderUsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("公司id")
	private Integer comid;

	@Column
	@Comment("用户id")
	private Integer userid;

	@Column
	@Comment("团名")
	private String groupname;

	@Column
	@Comment("订单号")
	private String ordernumber;

	@Column
	@Comment("领区")
	private Integer cityid;

	@Column
	@Comment("订单状态")
	private Integer status;

	@Column
	@Comment("是否作废")
	private Integer isdisable;

	@Column
	@Comment("是否付款")
	private Integer ispayed;

	@Column
	@Comment("操作人")
	private Integer opid;

	@Column
	@Comment("创建时间")
	private Date createtime;

	@Column
	@Comment("更新时间")
	private Date updatetime;

}
