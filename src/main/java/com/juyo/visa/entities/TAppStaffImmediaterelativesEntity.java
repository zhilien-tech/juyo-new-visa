package com.juyo.visa.entities;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_app_staff_immediaterelatives")
public class TAppStaffImmediaterelativesEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("人员id")
	private Integer staffid;

	@Column
	@Comment("亲属的姓")
	private String relativesfirstname;

	@Column
	@Comment("亲属的名")
	private String relativeslastname;

	@Column
	@Comment("与你的关系")
	private Integer relationship;

	@Column
	@Comment("亲属的身份")
	private Integer relativesstatus;

	@Column
	@Comment("亲属的姓(英文)")
	private String relativesfirstnameen;

	@Column
	@Comment("亲属的名(英文)")
	private String relativeslastnameen;

	@Column
	@Comment("与你的关系(英文)")
	private Integer relationshipen;

	@Column
	@Comment("亲属的身份(英文)")
	private Integer relativesstatusen;

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
		TAppStaffImmediaterelativesEntity other = (TAppStaffImmediaterelativesEntity) obj;
		if (relationship == null) {
			if (other.relationship != null)
				return false;
		} else if (!relationship.equals(other.relationship))
			return false;
		if (relationshipen == null) {
			if (other.relationshipen != null)
				return false;
		} else if (!relationshipen.equals(other.relationshipen))
			return false;
		if (relativesfirstname == null) {
			if (other.relativesfirstname != null)
				return false;
		} else if (!relativesfirstname.equals(other.relativesfirstname))
			return false;
		if (relativesfirstnameen == null) {
			if (other.relativesfirstnameen != null)
				return false;
		} else if (!relativesfirstnameen.equals(other.relativesfirstnameen))
			return false;
		if (relativeslastname == null) {
			if (other.relativeslastname != null)
				return false;
		} else if (!relativeslastname.equals(other.relativeslastname))
			return false;
		if (relativeslastnameen == null) {
			if (other.relativeslastnameen != null)
				return false;
		} else if (!relativeslastnameen.equals(other.relativeslastnameen))
			return false;
		if (relativesstatus == null) {
			if (other.relativesstatus != null)
				return false;
		} else if (!relativesstatus.equals(other.relativesstatus))
			return false;
		if (relativesstatusen == null) {
			if (other.relativesstatusen != null)
				return false;
		} else if (!relativesstatusen.equals(other.relativesstatusen))
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
		result = prime * result + ((relationship == null) ? 0 : relationship.hashCode());
		result = prime * result + ((relationshipen == null) ? 0 : relationshipen.hashCode());
		result = prime * result + ((relativesfirstname == null) ? 0 : relativesfirstname.hashCode());
		result = prime * result + ((relativesfirstnameen == null) ? 0 : relativesfirstnameen.hashCode());
		result = prime * result + ((relativeslastname == null) ? 0 : relativeslastname.hashCode());
		result = prime * result + ((relativeslastnameen == null) ? 0 : relativeslastnameen.hashCode());
		result = prime * result + ((relativesstatus == null) ? 0 : relativesstatus.hashCode());
		result = prime * result + ((relativesstatusen == null) ? 0 : relativesstatusen.hashCode());
		result = prime * result + ((staffid == null) ? 0 : staffid.hashCode());
		return result;
	}

}