package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_function")
public class TFunctionEntity implements Serializable, Cloneable, Comparable<TFunctionEntity> {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("上级功能id")
	private Integer parentId;

	@Column
	@Comment("功能名称")
	private String funName;

	@Column
	@Comment("访问地址")
	private String url;

	@Column
	@Comment("功能等级，是指在功能树中所处的层级")
	private Integer level;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("更新时间")
	private Date updateTime;

	@Column
	@Comment("备注")
	private String remark;

	@Column
	@Comment("序号")
	private Integer sort;

	@Column
	@Comment("菜单栏图标")
	private String portrait;

	/**在树形节点中是否选中*/
	private String checked = "false";

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
		TFunctionEntity other = (TFunctionEntity) obj;
		if (id != other.id)
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
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public TFunctionEntity clone() throws CloneNotSupportedException {
		TFunctionEntity clone = null;
		try {
			clone = (TFunctionEntity) super.clone();

		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e); // won't happen 
		}
		return clone;
	}

	@Override
	public int compareTo(TFunctionEntity o) {
		if (null == o || null == o.getSort()) {
			return -1;
		}
		return this.getSort().compareTo(o.getSort());
	}

}