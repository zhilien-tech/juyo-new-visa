package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_applicant_front_paperwork_jp")
public class TApplicantFrontPaperworkJpEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("申请人id")
	private Integer applicantId;

	@Column
	@Comment("资料类型(同职业状况)")
	private Integer type;

	@Column
	@Comment("真实资料(证件)")
	private Integer content;

	@Column
	@Comment("资料本数")
	private Integer count;

	@Column
	@Comment("前台收件时间")
	private Date receiveDate;

	@Column
	@Comment("操作人")
	private Integer opId;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("更新时间")
	private Date updateTime;

	@Column
	@Comment("真实资料")
	private String realInfo;

	@Column
	@Comment("状态 1：已收 2、未收")
	private Integer status;

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
		TApplicantFrontPaperworkJpEntity other = (TApplicantFrontPaperworkJpEntity) obj;
		if (applicantId == null) {
			if (other.applicantId != null)
				return false;
		} else if (!applicantId.equals(other.applicantId))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (count == null) {
			if (other.count != null)
				return false;
		} else if (!count.equals(other.count))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (opId == null) {
			if (other.opId != null)
				return false;
		} else if (!opId.equals(other.opId))
			return false;
		if (realInfo == null) {
			if (other.realInfo != null)
				return false;
		} else if (!realInfo.equals(other.realInfo))
			return false;
		if (receiveDate == null) {
			if (other.receiveDate != null)
				return false;
		} else if (!receiveDate.equals(other.receiveDate))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
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
		result = prime * result + ((applicantId == null) ? 0 : applicantId.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((count == null) ? 0 : count.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((opId == null) ? 0 : opId.hashCode());
		result = prime * result + ((realInfo == null) ? 0 : realInfo.hashCode());
		result = prime * result + ((receiveDate == null) ? 0 : receiveDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		return result;
	}

}