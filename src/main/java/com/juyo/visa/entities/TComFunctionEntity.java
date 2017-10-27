package com.juyo.visa.entities;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_com_function")
public class TComFunctionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("功能id")
	private Integer funId;

	@Column
	@Comment("公司id")
	private Integer comId;

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
		TComFunctionEntity other = (TComFunctionEntity) obj;
		if (comId == null) {
			if (other.comId != null)
				return false;
		} else if (!comId.equals(other.comId))
			return false;
		if (funId == null) {
			if (other.funId != null)
				return false;
		} else if (!funId.equals(other.funId))
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
		result = prime * result + ((comId == null) ? 0 : comId.hashCode());
		result = prime * result + ((funId == null) ? 0 : funId.hashCode());
		return result;
	}

}