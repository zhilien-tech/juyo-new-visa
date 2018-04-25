/**
 * TCustomerHistoryEntity.java
 * com.juyo.visa.entities
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   朱晓川
 * @Date	 2018年4月3日 	 
 */
@Data
@Table("t_customer_his")
public class TCustomerHisEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	@Column
	@Comment("订单Id")
	private Integer orderId;
	@Column
	@Comment("用户id")
	private Integer userId;

	@Column
	@Comment("公司id")
	private Integer compId;

	@Column
	@Comment("公司名称")
	private String name;

	@Column
	@Comment("支付方式")
	private String payType;

	@Column
	@Comment("是否来自客户信息")
	private String isCustomerAdd;

	@Column
	@Comment("公司简称")
	private String shortname;

	@Column
	@Comment("客户来源")
	private String source;

	@Column
	@Comment("联系人")
	private String linkman;

	@Column
	@Comment("手机")
	private String mobile;

	@Column
	@Comment("邮箱")
	private String email;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("更新时间")
	private Date updateTime;

}
