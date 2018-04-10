package com.juyo.visa.entities;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_app_staff_organization")
public class TAppStaffOrganizationEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("人员id")
	private Integer staffid;

	@Column
	@Comment("组织名称")
	private String organizationname;

	@Column
	@Comment("组织名称(英文)")
	private String organizationnameen;

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TAppStaffOrganizationEntity other = (TAppStaffOrganizationEntity) obj;
		if (organizationname == null) {
			if (other.organizationname != null)
				return false;
		} else if (!organizationname.equals(other.organizationname))
			return false;
		if (organizationnameen == null) {
			if (other.organizationnameen != null)
				return false;
		} else if (!organizationnameen.equals(other.organizationnameen))
			return false;
		if (staffid == null) {
			if (other.staffid != null)
				return false;
		} else if (!staffid.equals(other.staffid))
			return false;
		return true;
	}

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((organizationname == null) ? 0 : organizationname.hashCode());
		result = prime * result + ((organizationnameen == null) ? 0 : organizationnameen.hashCode());
		result = prime * result + ((staffid == null) ? 0 : staffid.hashCode());
		return result;
	}

}