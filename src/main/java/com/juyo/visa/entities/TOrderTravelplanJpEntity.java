package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_order_travelplan_jp")
public class TOrderTravelplanJpEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("订单id")
	private Integer orderId;

	@Column
	@Comment("天数")
	private String day;

	@Column
	@Comment("日期")
	private Date outDate;

	@Column
	@Comment("景区")
	private String scenic;

	@Column
	@Comment("酒店")
	private Integer hotel;

	@Column
	@Comment("城市id")
	private Integer cityId;

	@Column
	@Comment("城市名称")
	private String cityName;

	@Column
	@Comment("操作人id")
	private Integer opId;

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
		TOrderTravelplanJpEntity other = (TOrderTravelplanJpEntity) obj;
		if (cityId == null) {
			if (other.cityId != null)
				return false;
		} else if (!cityId.equals(other.cityId))
			return false;
		if (cityName == null) {
			if (other.cityName != null)
				return false;
		} else if (!cityName.equals(other.cityName))
			return false;
		if (day == null) {
			if (other.day != null)
				return false;
		} else if (!day.equals(other.day))
			return false;
		if (hotel == null) {
			if (other.hotel != null)
				return false;
		} else if (!hotel.equals(other.hotel))
			return false;
		if (opId == null) {
			if (other.opId != null)
				return false;
		} else if (!opId.equals(other.opId))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (outDate == null) {
			if (other.outDate != null)
				return false;
		} else if (!outDate.equals(other.outDate))
			return false;
		if (scenic == null) {
			if (other.scenic != null)
				return false;
		} else if (!scenic.equals(other.scenic))
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
		result = prime * result + ((cityId == null) ? 0 : cityId.hashCode());
		result = prime * result + ((cityName == null) ? 0 : cityName.hashCode());
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result + ((hotel == null) ? 0 : hotel.hashCode());
		result = prime * result + ((opId == null) ? 0 : opId.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((outDate == null) ? 0 : outDate.hashCode());
		result = prime * result + ((scenic == null) ? 0 : scenic.hashCode());
		return result;
	}

}