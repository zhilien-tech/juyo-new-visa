package com.juyo.visa.entities;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_app_staff_companioninfo")
public class TAppStaffCompanioninfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("人员id")
	private Integer staffid;

	@Column
	@Comment("同伴的姓")
	private String firstname;

	@Column
	@Comment("同伴的姓(英文)")
	private String firstnameen;

	@Column
	@Comment("同伴的名")
	private String lastname;

	@Column
	@Comment("同伴的名(英文)")
	private String lastnameen;

	@Column
	@Comment("与你的关系")
	private Integer relationship;

	@Column
	@Comment("与你的关系(英文)")
	private Integer relationshipen;

	@Column
	@Comment("说明")
	private String explain;

	@Column
	@Comment("说明(英文)")
	private String explainen;

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
		TAppStaffCompanioninfoEntity other = (TAppStaffCompanioninfoEntity) obj;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (firstnameen == null) {
			if (other.firstnameen != null)
				return false;
		} else if (!firstnameen.equals(other.firstnameen))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (lastnameen == null) {
			if (other.lastnameen != null)
				return false;
		} else if (!lastnameen.equals(other.lastnameen))
			return false;
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
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((firstnameen == null) ? 0 : firstnameen.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + ((lastnameen == null) ? 0 : lastnameen.hashCode());
		result = prime * result + ((relationship == null) ? 0 : relationship.hashCode());
		result = prime * result + ((relationshipen == null) ? 0 : relationshipen.hashCode());
		result = prime * result + ((staffid == null) ? 0 : staffid.hashCode());
		return result;
	}

}