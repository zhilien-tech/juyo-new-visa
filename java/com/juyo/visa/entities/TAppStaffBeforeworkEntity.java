package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_app_staff_beforework")
public class TAppStaffBeforeworkEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("人员id")
	private Integer staffid;

	@Column
	@Comment("雇主姓名")
	private String employername;

	@Column
	@Comment("雇主街道地址")
	private String employeraddress;

	@Column
	@Comment("雇主街道地址(次选)")
	private String employeraddressSec;

	@Column
	@Comment("雇主所在市")
	private String employercity;

	@Column
	@Comment("雇主所在省")
	private String employerprovince;

	@Column
	@Comment("雇主所在省是否适用")
	private Integer isemployerprovinceapply;

	@Column
	@Comment("雇主邮政编码")
	private String employerzipcode;

	@Column
	@Comment("雇主邮政编码是否适用")
	private Integer isemployerzipcodeapply;

	@Column
	@Comment("雇主所在国家")
	private Integer employercountry;

	@Column
	@Comment("雇主电话")
	private String employertelephone;

	@Column
	@Comment("职称")
	private String jobtitle;

	@Column
	@Comment("主管的姓")
	private String supervisorfirstname;

	@Column
	@Comment("是否知道主管的姓")
	private Integer isknowsupervisorfirstname;

	@Column
	@Comment("主管的名")
	private String supervisorlastname;

	@Column
	@Comment("是否知道主管的名")
	private Integer isknowsupervisorlastname;

	@Column
	@Comment("入职时间")
	private Date employstartdate;

	@Column
	@Comment("离职时间")
	private Date employenddate;

	@Column
	@Comment("以前的工作职责")
	private String previousduty;

	@Column
	@Comment("雇主姓名(英文)")
	private String employernameen;

	@Column
	@Comment("雇主街道地址(英文)")
	private String employeraddressen;

	@Column
	@Comment("雇主街道地址(次选)(英文)")
	private String employeraddressSecen;

	@Column
	@Comment("雇主所在市(英文)")
	private String employercityen;

	@Column
	@Comment("雇主所在省(英文)")
	private String employerprovinceen;

	@Column
	@Comment("雇主所在省是否适用(英文)")
	private Integer isemployerprovinceapplyen;

	@Column
	@Comment("雇主邮政编码(英文)")
	private String employerzipcodeen;

	@Column
	@Comment("雇主邮政编码是否适用(英文)")
	private Integer isemployerzipcodeapplyen;

	@Column
	@Comment("雇主所在国家(英文)")
	private Integer employercountryen;

	@Column
	@Comment("雇主电话(英文)")
	private String employertelephoneen;

	@Column
	@Comment("职称(英文)")
	private String jobtitleen;

	@Column
	@Comment("主管的姓(英文)")
	private String supervisorfirstnameen;

	@Column
	@Comment("是否知道主管的姓(英文)")
	private Integer isknowsupervisorfirstnameen;

	@Column
	@Comment("主管的名(英文)")
	private String supervisorlastnameen;

	@Column
	@Comment("是否知道主管的名(英文)")
	private Integer isknowsupervisorlastnameen;

	@Column
	@Comment("入职时间(英文)")
	private Date employstartdateen;

	@Column
	@Comment("离职时间(英文)")
	private Date employenddateen;

	@Column
	@Comment("以前的工作职责(英文)")
	private String previousdutyen;

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
		TAppStaffBeforeworkEntity other = (TAppStaffBeforeworkEntity) obj;
		if (employenddate == null) {
			if (other.employenddate != null)
				return false;
		} else if (!employenddate.equals(other.employenddate))
			return false;
		if (employenddateen == null) {
			if (other.employenddateen != null)
				return false;
		} else if (!employenddateen.equals(other.employenddateen))
			return false;
		if (employeraddress == null) {
			if (other.employeraddress != null)
				return false;
		} else if (!employeraddress.equals(other.employeraddress))
			return false;
		if (employeraddressSec == null) {
			if (other.employeraddressSec != null)
				return false;
		} else if (!employeraddressSec.equals(other.employeraddressSec))
			return false;
		if (employeraddressSecen == null) {
			if (other.employeraddressSecen != null)
				return false;
		} else if (!employeraddressSecen.equals(other.employeraddressSecen))
			return false;
		if (employeraddressen == null) {
			if (other.employeraddressen != null)
				return false;
		} else if (!employeraddressen.equals(other.employeraddressen))
			return false;
		if (employercity == null) {
			if (other.employercity != null)
				return false;
		} else if (!employercity.equals(other.employercity))
			return false;
		if (employercityen == null) {
			if (other.employercityen != null)
				return false;
		} else if (!employercityen.equals(other.employercityen))
			return false;
		if (employercountry == null) {
			if (other.employercountry != null)
				return false;
		} else if (!employercountry.equals(other.employercountry))
			return false;
		if (employercountryen == null) {
			if (other.employercountryen != null)
				return false;
		} else if (!employercountryen.equals(other.employercountryen))
			return false;
		if (employername == null) {
			if (other.employername != null)
				return false;
		} else if (!employername.equals(other.employername))
			return false;
		if (employernameen == null) {
			if (other.employernameen != null)
				return false;
		} else if (!employernameen.equals(other.employernameen))
			return false;
		if (employerprovince == null) {
			if (other.employerprovince != null)
				return false;
		} else if (!employerprovince.equals(other.employerprovince))
			return false;
		if (employerprovinceen == null) {
			if (other.employerprovinceen != null)
				return false;
		} else if (!employerprovinceen.equals(other.employerprovinceen))
			return false;
		if (employertelephone == null) {
			if (other.employertelephone != null)
				return false;
		} else if (!employertelephone.equals(other.employertelephone))
			return false;
		if (employertelephoneen == null) {
			if (other.employertelephoneen != null)
				return false;
		} else if (!employertelephoneen.equals(other.employertelephoneen))
			return false;
		if (employerzipcode == null) {
			if (other.employerzipcode != null)
				return false;
		} else if (!employerzipcode.equals(other.employerzipcode))
			return false;
		if (employerzipcodeen == null) {
			if (other.employerzipcodeen != null)
				return false;
		} else if (!employerzipcodeen.equals(other.employerzipcodeen))
			return false;
		if (employstartdate == null) {
			if (other.employstartdate != null)
				return false;
		} else if (!employstartdate.equals(other.employstartdate))
			return false;
		if (employstartdateen == null) {
			if (other.employstartdateen != null)
				return false;
		} else if (!employstartdateen.equals(other.employstartdateen))
			return false;
		if (isemployerprovinceapply == null) {
			if (other.isemployerprovinceapply != null)
				return false;
		} else if (!isemployerprovinceapply.equals(other.isemployerprovinceapply))
			return false;
		if (isemployerprovinceapplyen == null) {
			if (other.isemployerprovinceapplyen != null)
				return false;
		} else if (!isemployerprovinceapplyen.equals(other.isemployerprovinceapplyen))
			return false;
		if (isemployerzipcodeapply == null) {
			if (other.isemployerzipcodeapply != null)
				return false;
		} else if (!isemployerzipcodeapply.equals(other.isemployerzipcodeapply))
			return false;
		if (isemployerzipcodeapplyen == null) {
			if (other.isemployerzipcodeapplyen != null)
				return false;
		} else if (!isemployerzipcodeapplyen.equals(other.isemployerzipcodeapplyen))
			return false;
		if (isknowsupervisorfirstname == null) {
			if (other.isknowsupervisorfirstname != null)
				return false;
		} else if (!isknowsupervisorfirstname.equals(other.isknowsupervisorfirstname))
			return false;
		if (isknowsupervisorfirstnameen == null) {
			if (other.isknowsupervisorfirstnameen != null)
				return false;
		} else if (!isknowsupervisorfirstnameen.equals(other.isknowsupervisorfirstnameen))
			return false;
		if (isknowsupervisorlastname == null) {
			if (other.isknowsupervisorlastname != null)
				return false;
		} else if (!isknowsupervisorlastname.equals(other.isknowsupervisorlastname))
			return false;
		if (isknowsupervisorlastnameen == null) {
			if (other.isknowsupervisorlastnameen != null)
				return false;
		} else if (!isknowsupervisorlastnameen.equals(other.isknowsupervisorlastnameen))
			return false;
		if (jobtitle == null) {
			if (other.jobtitle != null)
				return false;
		} else if (!jobtitle.equals(other.jobtitle))
			return false;
		if (jobtitleen == null) {
			if (other.jobtitleen != null)
				return false;
		} else if (!jobtitleen.equals(other.jobtitleen))
			return false;
		if (previousduty == null) {
			if (other.previousduty != null)
				return false;
		} else if (!previousduty.equals(other.previousduty))
			return false;
		if (previousdutyen == null) {
			if (other.previousdutyen != null)
				return false;
		} else if (!previousdutyen.equals(other.previousdutyen))
			return false;
		if (staffid == null) {
			if (other.staffid != null)
				return false;
		} else if (!staffid.equals(other.staffid))
			return false;
		if (supervisorfirstname == null) {
			if (other.supervisorfirstname != null)
				return false;
		} else if (!supervisorfirstname.equals(other.supervisorfirstname))
			return false;
		if (supervisorfirstnameen == null) {
			if (other.supervisorfirstnameen != null)
				return false;
		} else if (!supervisorfirstnameen.equals(other.supervisorfirstnameen))
			return false;
		if (supervisorlastname == null) {
			if (other.supervisorlastname != null)
				return false;
		} else if (!supervisorlastname.equals(other.supervisorlastname))
			return false;
		if (supervisorlastnameen == null) {
			if (other.supervisorlastnameen != null)
				return false;
		} else if (!supervisorlastnameen.equals(other.supervisorlastnameen))
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
		result = prime * result + ((employenddate == null) ? 0 : employenddate.hashCode());
		result = prime * result + ((employenddateen == null) ? 0 : employenddateen.hashCode());
		result = prime * result + ((employeraddress == null) ? 0 : employeraddress.hashCode());
		result = prime * result + ((employeraddressSec == null) ? 0 : employeraddressSec.hashCode());
		result = prime * result + ((employeraddressSecen == null) ? 0 : employeraddressSecen.hashCode());
		result = prime * result + ((employeraddressen == null) ? 0 : employeraddressen.hashCode());
		result = prime * result + ((employercity == null) ? 0 : employercity.hashCode());
		result = prime * result + ((employercityen == null) ? 0 : employercityen.hashCode());
		result = prime * result + ((employercountry == null) ? 0 : employercountry.hashCode());
		result = prime * result + ((employercountryen == null) ? 0 : employercountryen.hashCode());
		result = prime * result + ((employername == null) ? 0 : employername.hashCode());
		result = prime * result + ((employernameen == null) ? 0 : employernameen.hashCode());
		result = prime * result + ((employerprovince == null) ? 0 : employerprovince.hashCode());
		result = prime * result + ((employerprovinceen == null) ? 0 : employerprovinceen.hashCode());
		result = prime * result + ((employertelephone == null) ? 0 : employertelephone.hashCode());
		result = prime * result + ((employertelephoneen == null) ? 0 : employertelephoneen.hashCode());
		result = prime * result + ((employerzipcode == null) ? 0 : employerzipcode.hashCode());
		result = prime * result + ((employerzipcodeen == null) ? 0 : employerzipcodeen.hashCode());
		result = prime * result + ((employstartdate == null) ? 0 : employstartdate.hashCode());
		result = prime * result + ((employstartdateen == null) ? 0 : employstartdateen.hashCode());
		result = prime * result + ((isemployerprovinceapply == null) ? 0 : isemployerprovinceapply.hashCode());
		result = prime * result + ((isemployerprovinceapplyen == null) ? 0 : isemployerprovinceapplyen.hashCode());
		result = prime * result + ((isemployerzipcodeapply == null) ? 0 : isemployerzipcodeapply.hashCode());
		result = prime * result + ((isemployerzipcodeapplyen == null) ? 0 : isemployerzipcodeapplyen.hashCode());
		result = prime * result + ((isknowsupervisorfirstname == null) ? 0 : isknowsupervisorfirstname.hashCode());
		result = prime * result + ((isknowsupervisorfirstnameen == null) ? 0 : isknowsupervisorfirstnameen.hashCode());
		result = prime * result + ((isknowsupervisorlastname == null) ? 0 : isknowsupervisorlastname.hashCode());
		result = prime * result + ((isknowsupervisorlastnameen == null) ? 0 : isknowsupervisorlastnameen.hashCode());
		result = prime * result + ((jobtitle == null) ? 0 : jobtitle.hashCode());
		result = prime * result + ((jobtitleen == null) ? 0 : jobtitleen.hashCode());
		result = prime * result + ((previousduty == null) ? 0 : previousduty.hashCode());
		result = prime * result + ((previousdutyen == null) ? 0 : previousdutyen.hashCode());
		result = prime * result + ((staffid == null) ? 0 : staffid.hashCode());
		result = prime * result + ((supervisorfirstname == null) ? 0 : supervisorfirstname.hashCode());
		result = prime * result + ((supervisorfirstnameen == null) ? 0 : supervisorfirstnameen.hashCode());
		result = prime * result + ((supervisorlastname == null) ? 0 : supervisorlastname.hashCode());
		result = prime * result + ((supervisorlastnameen == null) ? 0 : supervisorlastnameen.hashCode());
		return result;
	}

}