package com.juyo.visa.entities;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_app_staff_driverinfo")
public class TAppStaffDriverinfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("人员id")
	private Integer staffid;

	@Column
	@Comment("驾照号")
	private String driverlicensenumber;

	@Column
	@Comment("是否知道驾照号")
	private Integer isknowdrivernumber;

	@Column
	@Comment("哪个州的驾照")
	private Integer witchstateofdriver;

	@Column
	@Comment("驾照号(英文)")
	private String driverlicensenumberen;

	@Column
	@Comment("是否知道驾照号(英文)")
	private Integer isknowdrivernumberen;

	@Column
	@Comment("哪个州的驾照(英文)")
	private Integer witchstateofdriveren;

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
		TAppStaffDriverinfoEntity other = (TAppStaffDriverinfoEntity) obj;
		if (driverlicensenumber == null) {
			if (other.driverlicensenumber != null)
				return false;
		} else if (!driverlicensenumber.equals(other.driverlicensenumber))
			return false;
		if (driverlicensenumberen == null) {
			if (other.driverlicensenumberen != null)
				return false;
		} else if (!driverlicensenumberen.equals(other.driverlicensenumberen))
			return false;
		if (isknowdrivernumber == null) {
			if (other.isknowdrivernumber != null)
				return false;
		} else if (!isknowdrivernumber.equals(other.isknowdrivernumber))
			return false;
		if (isknowdrivernumberen == null) {
			if (other.isknowdrivernumberen != null)
				return false;
		} else if (!isknowdrivernumberen.equals(other.isknowdrivernumberen))
			return false;
		if (staffid == null) {
			if (other.staffid != null)
				return false;
		} else if (!staffid.equals(other.staffid))
			return false;
		if (witchstateofdriver == null) {
			if (other.witchstateofdriver != null)
				return false;
		} else if (!witchstateofdriver.equals(other.witchstateofdriver))
			return false;
		if (witchstateofdriveren == null) {
			if (other.witchstateofdriveren != null)
				return false;
		} else if (!witchstateofdriveren.equals(other.witchstateofdriveren))
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
		result = prime * result + ((driverlicensenumber == null) ? 0 : driverlicensenumber.hashCode());
		result = prime * result + ((driverlicensenumberen == null) ? 0 : driverlicensenumberen.hashCode());
		result = prime * result + ((isknowdrivernumber == null) ? 0 : isknowdrivernumber.hashCode());
		result = prime * result + ((isknowdrivernumberen == null) ? 0 : isknowdrivernumberen.hashCode());
		result = prime * result + ((staffid == null) ? 0 : staffid.hashCode());
		result = prime * result + ((witchstateofdriver == null) ? 0 : witchstateofdriver.hashCode());
		result = prime * result + ((witchstateofdriveren == null) ? 0 : witchstateofdriveren.hashCode());
		return result;
	}

}