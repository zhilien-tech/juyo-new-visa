package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_app_staff_conscientious")
public class TAppStaffConscientiousEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("人员id")
	private Integer staffid;

	@Column
	@Comment("服兵役国家")
	private Integer militarycountry;

	@Column
	@Comment("服务分支")
	private String servicebranch;

	@Column
	@Comment("排名")
	private String rank;

	@Column
	@Comment("军事专业")
	private String militaryspecialty;

	@Column
	@Comment("服兵役开始时间")
	private Date servicestartdate;

	@Column
	@Comment("服兵役结束时间")
	private Date serviceenddate;

	@Column
	@Comment("服兵役国家(英文)")
	private Integer militarycountryen;

	@Column
	@Comment("服务分支(英文)")
	private String servicebranchen;

	@Column
	@Comment("排名(英文)")
	private String ranken;

	@Column
	@Comment("军事专业(英文)")
	private String militaryspecialtyen;

	@Column
	@Comment("服兵役开始时间(英文)")
	private Date servicestartdateen;

	@Column
	@Comment("服兵役结束时间(英文)")
	private Date serviceenddateen;

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
		TAppStaffConscientiousEntity other = (TAppStaffConscientiousEntity) obj;
		if (militarycountry == null) {
			if (other.militarycountry != null)
				return false;
		} else if (!militarycountry.equals(other.militarycountry))
			return false;
		if (militarycountryen == null) {
			if (other.militarycountryen != null)
				return false;
		} else if (!militarycountryen.equals(other.militarycountryen))
			return false;
		if (militaryspecialty == null) {
			if (other.militaryspecialty != null)
				return false;
		} else if (!militaryspecialty.equals(other.militaryspecialty))
			return false;
		if (militaryspecialtyen == null) {
			if (other.militaryspecialtyen != null)
				return false;
		} else if (!militaryspecialtyen.equals(other.militaryspecialtyen))
			return false;
		if (rank == null) {
			if (other.rank != null)
				return false;
		} else if (!rank.equals(other.rank))
			return false;
		if (ranken == null) {
			if (other.ranken != null)
				return false;
		} else if (!ranken.equals(other.ranken))
			return false;
		if (servicebranch == null) {
			if (other.servicebranch != null)
				return false;
		} else if (!servicebranch.equals(other.servicebranch))
			return false;
		if (servicebranchen == null) {
			if (other.servicebranchen != null)
				return false;
		} else if (!servicebranchen.equals(other.servicebranchen))
			return false;
		if (serviceenddate == null) {
			if (other.serviceenddate != null)
				return false;
		} else if (!serviceenddate.equals(other.serviceenddate))
			return false;
		if (serviceenddateen == null) {
			if (other.serviceenddateen != null)
				return false;
		} else if (!serviceenddateen.equals(other.serviceenddateen))
			return false;
		if (servicestartdate == null) {
			if (other.servicestartdate != null)
				return false;
		} else if (!servicestartdate.equals(other.servicestartdate))
			return false;
		if (servicestartdateen == null) {
			if (other.servicestartdateen != null)
				return false;
		} else if (!servicestartdateen.equals(other.servicestartdateen))
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
		result = prime * result + ((militarycountry == null) ? 0 : militarycountry.hashCode());
		result = prime * result + ((militarycountryen == null) ? 0 : militarycountryen.hashCode());
		result = prime * result + ((militaryspecialty == null) ? 0 : militaryspecialty.hashCode());
		result = prime * result + ((militaryspecialtyen == null) ? 0 : militaryspecialtyen.hashCode());
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((ranken == null) ? 0 : ranken.hashCode());
		result = prime * result + ((servicebranch == null) ? 0 : servicebranch.hashCode());
		result = prime * result + ((servicebranchen == null) ? 0 : servicebranchen.hashCode());
		result = prime * result + ((serviceenddate == null) ? 0 : serviceenddate.hashCode());
		result = prime * result + ((serviceenddateen == null) ? 0 : serviceenddateen.hashCode());
		result = prime * result + ((servicestartdate == null) ? 0 : servicestartdate.hashCode());
		result = prime * result + ((servicestartdateen == null) ? 0 : servicestartdateen.hashCode());
		result = prime * result + ((staffid == null) ? 0 : staffid.hashCode());
		return result;
	}

}