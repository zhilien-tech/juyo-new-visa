package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_autofill_company")
public class TAutofillCompanyEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("公司id")
	private Integer comid;

	@Column
	@Comment("公司是否有权限使用")
	private Integer isdisabled;

	@Column
	@Comment("地接社")
	private Integer groundconnectid;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("更新时间")
	private Date updateTime;

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
		TAutofillCompanyEntity other = (TAutofillCompanyEntity) obj;
		if (comid == null) {
			if (other.comid != null)
				return false;
		} else if (!comid.equals(other.comid))
			return false;
		if (isdisabled == null) {
			if (other.isdisabled != null)
				return false;
		} else if (!isdisabled.equals(other.isdisabled))
			return false;
		if (groundconnectid == null) {
			if (other.groundconnectid != null)
				return false;
		} else if (!groundconnectid.equals(other.groundconnectid))
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
		result = prime * result + ((comid == null) ? 0 : comid.hashCode());
		result = prime * result + ((isdisabled == null) ? 0 : isdisabled.hashCode());
		result = prime * result + ((groundconnectid == null) ? 0 : groundconnectid.hashCode());
		return result;
	}

}