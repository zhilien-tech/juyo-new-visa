package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_order_backmail")
public class TOrderBackmailEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("订单id")
	private Integer orderId;

	@Column
	@Comment("资料来源")
	private Integer source;

	@Column
	@Comment("回邮方式")
	private Integer expressType;

	@Column
	@Comment("团队名称")
	private String teamName;

	@Column
	@Comment("快递号")
	private String expressNum;

	@Column
	@Comment("回邮地址")
	private String expressAddress;

	@Column
	@Comment("联系人")
	private String linkman;

	@Column
	@Comment("电话")
	private String telephone;

	@Column
	@Comment("发票项目内容")
	private String invoiceContent;

	@Column
	@Comment("发票抬头")
	private String invoiceHead;

	@Column
	@Comment("发票电话")
	private String invioceMobile;

	@Column
	@Comment("发票地址")
	private String invoiceAddress;

	@Column
	@Comment("税号")
	private String taxNum;

	@Column
	@Comment("备注")
	private String remark;

	@Column
	@Comment("操作人")
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
		TOrderBackmailEntity other = (TOrderBackmailEntity) obj;
		if (expressAddress == null) {
			if (other.expressAddress != null)
				return false;
		} else if (!expressAddress.equals(other.expressAddress))
			return false;
		if (expressNum == null) {
			if (other.expressNum != null)
				return false;
		} else if (!expressNum.equals(other.expressNum))
			return false;
		if (expressType == null) {
			if (other.expressType != null)
				return false;
		} else if (!expressType.equals(other.expressType))
			return false;
		if (invioceMobile == null) {
			if (other.invioceMobile != null)
				return false;
		} else if (!invioceMobile.equals(other.invioceMobile))
			return false;
		if (invoiceAddress == null) {
			if (other.invoiceAddress != null)
				return false;
		} else if (!invoiceAddress.equals(other.invoiceAddress))
			return false;
		if (invoiceContent == null) {
			if (other.invoiceContent != null)
				return false;
		} else if (!invoiceContent.equals(other.invoiceContent))
			return false;
		if (invoiceHead == null) {
			if (other.invoiceHead != null)
				return false;
		} else if (!invoiceHead.equals(other.invoiceHead))
			return false;
		if (linkman == null) {
			if (other.linkman != null)
				return false;
		} else if (!linkman.equals(other.linkman))
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
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (taxNum == null) {
			if (other.taxNum != null)
				return false;
		} else if (!taxNum.equals(other.taxNum))
			return false;
		if (teamName == null) {
			if (other.teamName != null)
				return false;
		} else if (!teamName.equals(other.teamName))
			return false;
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
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
		result = prime * result + ((expressAddress == null) ? 0 : expressAddress.hashCode());
		result = prime * result + ((expressNum == null) ? 0 : expressNum.hashCode());
		result = prime * result + ((expressType == null) ? 0 : expressType.hashCode());
		result = prime * result + ((invioceMobile == null) ? 0 : invioceMobile.hashCode());
		result = prime * result + ((invoiceAddress == null) ? 0 : invoiceAddress.hashCode());
		result = prime * result + ((invoiceContent == null) ? 0 : invoiceContent.hashCode());
		result = prime * result + ((invoiceHead == null) ? 0 : invoiceHead.hashCode());
		result = prime * result + ((linkman == null) ? 0 : linkman.hashCode());
		result = prime * result + ((opId == null) ? 0 : opId.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((taxNum == null) ? 0 : taxNum.hashCode());
		result = prime * result + ((teamName == null) ? 0 : teamName.hashCode());
		result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
		return result;
	}

}