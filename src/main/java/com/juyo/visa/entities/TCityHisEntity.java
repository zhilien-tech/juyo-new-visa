/**
 * TCityHisEntity.java
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
 * TODO(城市历史信息表)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   董霖鹏
 * @Date	 2018年4月3日 	 
 */
@Data
@Table("t_city_his")
public class TCityHisEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("订单Id")
	private Integer orderId;

	@Column
	@Comment("国家")
	private String country;

	@Column
	@Comment("省/州/县")
	private String province;

	@Column
	@Comment("城市")
	private String city;

	@Column
	@Comment("城市三字码")
	private String code;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("更新时间")
	private Date updateTime;

}
