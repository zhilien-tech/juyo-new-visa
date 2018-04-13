package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_app_staff_credentials")
public class TAppStaffCredentialsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("人员id")
	private Integer staffid;
	
	@Column
    @Comment("主证件id")
	private Integer mainid;
	
	@Column
    @Comment("证件url")
	private String url;
	
	@Column
    @Comment("证件类型")
	private Integer type;
	
	@Column
    @Comment("证件状态")
	private Integer status;
	
	@Column
    @Comment("证件序号")
	private Integer sequence;
	
	@Column
    @Comment("页面元素id")
	private String pageelementid;
	
	@Column
    @Comment("创建时间")
	private Date createtime;
	
	@Column
    @Comment("更新时间")
	private Date updatetime;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TAppStaffCredentialsEntity other = (TAppStaffCredentialsEntity) obj;
		if (mainid == null) {
			if (other.mainid != null)
				return false;
		} else if (!mainid.equals(other.mainid))
			return false;
		if (pageelementid == null) {
			if (other.pageelementid != null)
				return false;
		} else if (!pageelementid.equals(other.pageelementid))
			return false;
		if (sequence == null) {
			if (other.sequence != null)
				return false;
		} else if (!sequence.equals(other.sequence))
			return false;
		if (staffid == null) {
			if (other.staffid != null)
				return false;
		} else if (!staffid.equals(other.staffid))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mainid == null) ? 0 : mainid.hashCode());
		result = prime * result + ((pageelementid == null) ? 0 : pageelementid.hashCode());
		result = prime * result + ((sequence == null) ? 0 : sequence.hashCode());
		result = prime * result + ((staffid == null) ? 0 : staffid.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}
	

}