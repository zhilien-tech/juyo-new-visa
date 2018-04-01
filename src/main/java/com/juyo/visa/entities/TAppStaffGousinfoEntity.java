package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_app_staff_gousinfo")
public class TAppStaffGousinfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("人员id")
	private Integer staffid;

	@Column
	@Comment("抵达日期")
	private Date arrivedate;

	@Column
	@Comment("停留时间")
	private Integer staydays;

	@Column
	@Comment("日期单位")
	private Integer dateunit;

	@Column
	@Comment("抵达日期(英文)")
	private Date arrivedateen;

	@Column
	@Comment("停留时间(英文)")
	private Integer staydaysen;

	@Column
	@Comment("日期单位(英文)")
	private Integer dateuniten;

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
		TAppStaffGousinfoEntity other = (TAppStaffGousinfoEntity) obj;
		if (arrivedate == null) {
			if (other.arrivedate != null)
				return false;
		} else if (!arrivedate.equals(other.arrivedate))
			return false;
		if (arrivedateen == null) {
			if (other.arrivedateen != null)
				return false;
		} else if (!arrivedateen.equals(other.arrivedateen))
			return false;
		if (dateunit == null) {
			if (other.dateunit != null)
				return false;
		} else if (!dateunit.equals(other.dateunit))
			return false;
		if (dateuniten == null) {
			if (other.dateuniten != null)
				return false;
		} else if (!dateuniten.equals(other.dateuniten))
			return false;
		if (staydays == null) {
			if (other.staydays != null)
				return false;
		} else if (!staydays.equals(other.staydays))
			return false;
		if (staydaysen == null) {
			if (other.staydaysen != null)
				return false;
		} else if (!staydaysen.equals(other.staydaysen))
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
		result = prime * result + ((arrivedate == null) ? 0 : arrivedate.hashCode());
		result = prime * result + ((arrivedateen == null) ? 0 : arrivedateen.hashCode());
		result = prime * result + ((dateunit == null) ? 0 : dateunit.hashCode());
		result = prime * result + ((dateuniten == null) ? 0 : dateuniten.hashCode());
		result = prime * result + ((staydays == null) ? 0 : staydays.hashCode());
		result = prime * result + ((staydaysen == null) ? 0 : staydaysen.hashCode());
		return result;
	}

}