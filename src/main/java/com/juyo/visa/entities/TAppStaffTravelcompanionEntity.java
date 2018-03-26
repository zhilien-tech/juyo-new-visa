package com.juyo.visa.entities;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_app_staff_travelcompanion")
public class TAppStaffTravelcompanionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("人员id")
	private Integer staffid;

	@Column
	@Comment("是否与其他人一起旅行")
	private Integer istravelwithother;

	@Column
	@Comment("是否与其他人一起旅行(英文)")
	private Integer istravelwithotheren;

	@Column
	@Comment("是否作为团队或组织的一部分")
	private Integer ispart;

	@Column
	@Comment("是否作为团队或组织的一部分(英文)")
	private Integer isparten;

	@Column
	@Comment("团队名称")
	private String groupname;

	@Column
	@Comment("团队名称(英文)")
	private String groupnameen;

}