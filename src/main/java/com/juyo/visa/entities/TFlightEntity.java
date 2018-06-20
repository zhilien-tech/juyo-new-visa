package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_flight")
public class TFlightEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("航班号")
	private String flightnum;

	@Column
	@Comment("航空公司")
	private String airlinecomp;

	@Column
	@Comment("起飞机场")
	private String takeOffName;

	@Column
	@Comment("起飞机场三字代码")
	private String takeOffCode;

	@Column
	@Comment("降落机场")
	private String landingName;

	@Column
	@Comment("降落机场三字代码")
	private String landingCode;

	@Column
	@Comment("起飞城市id")
	private Integer takeOffCityId;

	@Column
	@Comment("降落城市id")
	private Integer landingCityId;

	@Column
	@Comment("起飞时间")
	private String takeOffTime;

	@Column
	@Comment("降落时间")
	private String landingTime;

	@Column
	@Comment("飞行时间")
	private String flytime;

	@Column
	@Comment("转机的关联航班")
	private Integer relationflight;

	@Column
	@Comment("起飞航站楼")
	private String takeOffTerminal;

	@Column
	@Comment("降落航站楼")
	private String landingTerminal;

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
		TFlightEntity other = (TFlightEntity) obj;
		if (airlinecomp == null) {
			if (other.airlinecomp != null)
				return false;
		} else if (!airlinecomp.equals(other.airlinecomp))
			return false;
		if (flightnum == null) {
			if (other.flightnum != null)
				return false;
		} else if (!flightnum.equals(other.flightnum))
			return false;
		if (landingCityId == null) {
			if (other.landingCityId != null)
				return false;
		} else if (!landingCityId.equals(other.landingCityId))
			return false;
		if (landingCode == null) {
			if (other.landingCode != null)
				return false;
		} else if (!landingCode.equals(other.landingCode))
			return false;
		if (landingName == null) {
			if (other.landingName != null)
				return false;
		} else if (!landingName.equals(other.landingName))
			return false;
		if (landingTime == null) {
			if (other.landingTime != null)
				return false;
		} else if (!landingTime.equals(other.landingTime))
			return false;
		if (takeOffCityId == null) {
			if (other.takeOffCityId != null)
				return false;
		} else if (!takeOffCityId.equals(other.takeOffCityId))
			return false;
		if (takeOffCode == null) {
			if (other.takeOffCode != null)
				return false;
		} else if (!takeOffCode.equals(other.takeOffCode))
			return false;
		if (takeOffName == null) {
			if (other.takeOffName != null)
				return false;
		} else if (!takeOffName.equals(other.takeOffName))
			return false;
		if (takeOffTime == null) {
			if (other.takeOffTime != null)
				return false;
		} else if (!takeOffTime.equals(other.takeOffTime))
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
		result = prime * result + ((airlinecomp == null) ? 0 : airlinecomp.hashCode());
		result = prime * result + ((flightnum == null) ? 0 : flightnum.hashCode());
		result = prime * result + ((landingCityId == null) ? 0 : landingCityId.hashCode());
		result = prime * result + ((landingCode == null) ? 0 : landingCode.hashCode());
		result = prime * result + ((landingName == null) ? 0 : landingName.hashCode());
		result = prime * result + ((landingTime == null) ? 0 : landingTime.hashCode());
		result = prime * result + ((takeOffCityId == null) ? 0 : takeOffCityId.hashCode());
		result = prime * result + ((takeOffCode == null) ? 0 : takeOffCode.hashCode());
		result = prime * result + ((takeOffName == null) ? 0 : takeOffName.hashCode());
		result = prime * result + ((takeOffTime == null) ? 0 : takeOffTime.hashCode());
		return result;
	}

}