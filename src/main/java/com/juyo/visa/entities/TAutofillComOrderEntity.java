package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_autofill_com_order")
public class TAutofillComOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("公司id")
	private Integer comid;

	@Column
	@Comment("用户id")
	private Integer userid;

	@Column
	@Comment("订单id")
	private Integer orderid;

	@Column
	@Comment("订单识别码")
	private String ordervoucher;

	@Column
	@Comment("护照号")
	private String passportNo;

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
		TAutofillComOrderEntity other = (TAutofillComOrderEntity) obj;
		if (comid == null) {
			if (other.comid != null)
				return false;
		} else if (!comid.equals(other.comid))
			return false;
		if (orderid == null) {
			if (other.orderid != null)
				return false;
		} else if (!orderid.equals(other.orderid))
			return false;
		if (ordervoucher == null) {
			if (other.ordervoucher != null)
				return false;
		} else if (!ordervoucher.equals(other.ordervoucher))
			return false;
		if (passportNo == null) {
			if (other.passportNo != null)
				return false;
		} else if (!passportNo.equals(other.passportNo))
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
		result = prime * result + ((orderid == null) ? 0 : orderid.hashCode());
		result = prime * result + ((ordervoucher == null) ? 0 : ordervoucher.hashCode());
		result = prime * result + ((passportNo == null) ? 0 : passportNo.hashCode());
		return result;
	}

}