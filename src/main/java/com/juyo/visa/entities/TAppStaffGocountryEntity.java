package com.juyo.visa.entities;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_app_staff_gocountry")
public class TAppStaffGocountryEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("人员id")
	private Integer staffid;

	@Column
	@Comment("去旅游的国家")
	private Integer traveledcountry;

	@Column
	@Comment("去旅游的国家(英文)")
	private Integer traveledcountryen;

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
		TAppStaffGocountryEntity other = (TAppStaffGocountryEntity) obj;
		if (staffid == null) {
			if (other.staffid != null)
				return false;
		} else if (!staffid.equals(other.staffid))
			return false;
		if (traveledcountry == null) {
			if (other.traveledcountry != null)
				return false;
		} else if (!traveledcountry.equals(other.traveledcountry))
			return false;
		if (traveledcountryen == null) {
			if (other.traveledcountryen != null)
				return false;
		} else if (!traveledcountryen.equals(other.traveledcountryen))
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
		result = prime * result + ((staffid == null) ? 0 : staffid.hashCode());
		result = prime * result + ((traveledcountry == null) ? 0 : traveledcountry.hashCode());
		result = prime * result + ((traveledcountryen == null) ? 0 : traveledcountryen.hashCode());
		return result;
	}

}