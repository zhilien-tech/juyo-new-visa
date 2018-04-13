package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_user_job")
public class TUserJobEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("用户id")
	private Integer empId;
	
	@Column
    @Comment("公司职位id")
	private Integer comJobId;
	
	@Column
    @Comment("状态")
	private Integer status;
	
	@Column
    @Comment("入职时间")
	private Date hireDate;
	
	@Column
    @Comment("离职时间")
	private Date leaveDate;
	
	@Column
    @Comment("备注")
	private String remark;
	

}