package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_app_staff_beforeeducation")
public class TAppStaffBeforeeducationEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("人员id")
	private Integer staffid;

	@Column
	@Comment("最高学历")
	private Integer highesteducation;
	@Column
	@Comment("最高学历(英文)")
	private Integer highesteducationen;

	@Column
	@Comment("机构名称")
	private String institution;

	@Column
	@Comment("机构地址")
	private String institutionaddress;

	@Column
	@Comment("机构地址次选")
	private String secinstitutionaddress;

	@Column
	@Comment("机构所在市")
	private String institutioncity;

	@Column
	@Comment("机构所在省")
	private String institutionprovince;

	@Column
	@Comment("机构所在省是否适用")
	private Integer isinstitutionprovinceapply;

	@Column
	@Comment("机构邮政编码")
	private String institutionzipcode;

	@Column
	@Comment("机构邮政编码是否适用")
	private Integer isinstitutionzipcodeapply;

	@Column
	@Comment("机构所在国家")
	private Integer institutioncountry;

	@Column
	@Comment("学科")
	private String course;

	@Column
	@Comment("课程开始时间")
	private Date coursestartdate;

	@Column
	@Comment("课程结束时间")
	private Date courseenddate;

	@Column
	@Comment("机构名称(英文)")
	private String institutionen;

	@Column
	@Comment("机构地址(英文)")
	private String institutionaddressen;

	@Column
	@Comment("机构地址次选(英文)")
	private String secinstitutionaddressen;

	@Column
	@Comment("机构所在市(英文)")
	private String institutioncityen;

	@Column
	@Comment("机构所在省(英文)")
	private String institutionprovinceen;

	@Column
	@Comment("机构所在省是否适用(英文)")
	private Integer isinstitutionprovinceapplyen;

	@Column
	@Comment("机构邮政编码(英文)")
	private String institutionzipcodeen;

	@Column
	@Comment("机构邮政编码是否适用(英文)")
	private Integer isinstitutionzipcodeapplyen;

	@Column
	@Comment("机构所在国家(英文)")
	private Integer institutioncountryen;

	@Column
	@Comment("学科(英文)")
	private String courseen;

	@Column
	@Comment("课程开始时间(英文)")
	private Date coursestartdateen;

	@Column
	@Comment("课程结束时间(英文)")
	private Date courseenddateen;

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
		TAppStaffBeforeeducationEntity other = (TAppStaffBeforeeducationEntity) obj;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		if (courseen == null) {
			if (other.courseen != null)
				return false;
		} else if (!courseen.equals(other.courseen))
			return false;
		if (courseenddate == null) {
			if (other.courseenddate != null)
				return false;
		} else if (!courseenddate.equals(other.courseenddate))
			return false;
		if (courseenddateen == null) {
			if (other.courseenddateen != null)
				return false;
		} else if (!courseenddateen.equals(other.courseenddateen))
			return false;
		if (coursestartdate == null) {
			if (other.coursestartdate != null)
				return false;
		} else if (!coursestartdate.equals(other.coursestartdate))
			return false;
		if (coursestartdateen == null) {
			if (other.coursestartdateen != null)
				return false;
		} else if (!coursestartdateen.equals(other.coursestartdateen))
			return false;
		if (institution == null) {
			if (other.institution != null)
				return false;
		} else if (!institution.equals(other.institution))
			return false;
		if (institutionaddress == null) {
			if (other.institutionaddress != null)
				return false;
		} else if (!institutionaddress.equals(other.institutionaddress))
			return false;
		if (institutionaddressen == null) {
			if (other.institutionaddressen != null)
				return false;
		} else if (!institutionaddressen.equals(other.institutionaddressen))
			return false;
		if (institutioncity == null) {
			if (other.institutioncity != null)
				return false;
		} else if (!institutioncity.equals(other.institutioncity))
			return false;
		if (institutioncityen == null) {
			if (other.institutioncityen != null)
				return false;
		} else if (!institutioncityen.equals(other.institutioncityen))
			return false;
		if (institutioncountry == null) {
			if (other.institutioncountry != null)
				return false;
		} else if (!institutioncountry.equals(other.institutioncountry))
			return false;
		if (institutioncountryen == null) {
			if (other.institutioncountryen != null)
				return false;
		} else if (!institutioncountryen.equals(other.institutioncountryen))
			return false;
		if (institutionen == null) {
			if (other.institutionen != null)
				return false;
		} else if (!institutionen.equals(other.institutionen))
			return false;
		if (institutionprovince == null) {
			if (other.institutionprovince != null)
				return false;
		} else if (!institutionprovince.equals(other.institutionprovince))
			return false;
		if (institutionprovinceen == null) {
			if (other.institutionprovinceen != null)
				return false;
		} else if (!institutionprovinceen.equals(other.institutionprovinceen))
			return false;
		if (institutionzipcode == null) {
			if (other.institutionzipcode != null)
				return false;
		} else if (!institutionzipcode.equals(other.institutionzipcode))
			return false;
		if (institutionzipcodeen == null) {
			if (other.institutionzipcodeen != null)
				return false;
		} else if (!institutionzipcodeen.equals(other.institutionzipcodeen))
			return false;
		if (isinstitutionprovinceapply == null) {
			if (other.isinstitutionprovinceapply != null)
				return false;
		} else if (!isinstitutionprovinceapply.equals(other.isinstitutionprovinceapply))
			return false;
		if (isinstitutionprovinceapplyen == null) {
			if (other.isinstitutionprovinceapplyen != null)
				return false;
		} else if (!isinstitutionprovinceapplyen.equals(other.isinstitutionprovinceapplyen))
			return false;
		if (isinstitutionzipcodeapply == null) {
			if (other.isinstitutionzipcodeapply != null)
				return false;
		} else if (!isinstitutionzipcodeapply.equals(other.isinstitutionzipcodeapply))
			return false;
		if (isinstitutionzipcodeapplyen == null) {
			if (other.isinstitutionzipcodeapplyen != null)
				return false;
		} else if (!isinstitutionzipcodeapplyen.equals(other.isinstitutionzipcodeapplyen))
			return false;
		if (secinstitutionaddress == null) {
			if (other.secinstitutionaddress != null)
				return false;
		} else if (!secinstitutionaddress.equals(other.secinstitutionaddress))
			return false;
		if (secinstitutionaddressen == null) {
			if (other.secinstitutionaddressen != null)
				return false;
		} else if (!secinstitutionaddressen.equals(other.secinstitutionaddressen))
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
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result + ((courseen == null) ? 0 : courseen.hashCode());
		result = prime * result + ((courseenddate == null) ? 0 : courseenddate.hashCode());
		result = prime * result + ((courseenddateen == null) ? 0 : courseenddateen.hashCode());
		result = prime * result + ((coursestartdate == null) ? 0 : coursestartdate.hashCode());
		result = prime * result + ((coursestartdateen == null) ? 0 : coursestartdateen.hashCode());
		result = prime * result + ((institution == null) ? 0 : institution.hashCode());
		result = prime * result + ((institutionaddress == null) ? 0 : institutionaddress.hashCode());
		result = prime * result + ((institutionaddressen == null) ? 0 : institutionaddressen.hashCode());
		result = prime * result + ((institutioncity == null) ? 0 : institutioncity.hashCode());
		result = prime * result + ((institutioncityen == null) ? 0 : institutioncityen.hashCode());
		result = prime * result + ((institutioncountry == null) ? 0 : institutioncountry.hashCode());
		result = prime * result + ((institutioncountryen == null) ? 0 : institutioncountryen.hashCode());
		result = prime * result + ((institutionen == null) ? 0 : institutionen.hashCode());
		result = prime * result + ((institutionprovince == null) ? 0 : institutionprovince.hashCode());
		result = prime * result + ((institutionprovinceen == null) ? 0 : institutionprovinceen.hashCode());
		result = prime * result + ((institutionzipcode == null) ? 0 : institutionzipcode.hashCode());
		result = prime * result + ((institutionzipcodeen == null) ? 0 : institutionzipcodeen.hashCode());
		result = prime * result + ((isinstitutionprovinceapply == null) ? 0 : isinstitutionprovinceapply.hashCode());
		result = prime * result
				+ ((isinstitutionprovinceapplyen == null) ? 0 : isinstitutionprovinceapplyen.hashCode());
		result = prime * result + ((isinstitutionzipcodeapply == null) ? 0 : isinstitutionzipcodeapply.hashCode());
		result = prime * result + ((isinstitutionzipcodeapplyen == null) ? 0 : isinstitutionzipcodeapplyen.hashCode());
		result = prime * result + ((secinstitutionaddress == null) ? 0 : secinstitutionaddress.hashCode());
		result = prime * result + ((secinstitutionaddressen == null) ? 0 : secinstitutionaddressen.hashCode());
		result = prime * result + ((staffid == null) ? 0 : staffid.hashCode());
		return result;
	}

}