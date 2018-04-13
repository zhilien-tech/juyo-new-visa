package com.juyo.visa.entities;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_app_staff_language")
public class TAppStaffLanguageEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("人员id")
	private Integer staffid;

	@Column
	@Comment("使用的语言名称")
	private String languagename;

	@Column
	@Comment("使用的语言名称(英文)")
	private String languagenameen;

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
		TAppStaffLanguageEntity other = (TAppStaffLanguageEntity) obj;
		if (languagename == null) {
			if (other.languagename != null)
				return false;
		} else if (!languagename.equals(other.languagename))
			return false;
		if (languagenameen == null) {
			if (other.languagenameen != null)
				return false;
		} else if (!languagenameen.equals(other.languagenameen))
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
		result = prime * result + ((languagename == null) ? 0 : languagename.hashCode());
		result = prime * result + ((languagenameen == null) ? 0 : languagenameen.hashCode());
		result = prime * result + ((staffid == null) ? 0 : staffid.hashCode());
		return result;
	}

}