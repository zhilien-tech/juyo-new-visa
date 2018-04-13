package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_job")
public class TJobEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("部门id")
	private Integer deptId;

	@Column
	@Comment("职位名称")
	private String jobName;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("修改时间")
	private Date updateTime;

	@Column
	@Comment("备注")
	private String remark;

	@Column
	@Comment("操作人id")
	private Integer opId;

	/**标记该职位是否属于某个登录用户*/
	private boolean checked = false;

}