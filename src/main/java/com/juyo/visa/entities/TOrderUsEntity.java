package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_order_us")
public class TOrderUsEntity implements Serializable {
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
	@Comment("团名")
	private String groupname;

	@Column
	@Comment("大客户公司名称")
	private String bigcustomername;

	@Column
	@Comment("订单号")
	private String ordernumber;

	@Column
	@Comment("DS160预览页面截图")
	private String reviewurl;

	@Column
	@Comment("DS160确认页PDF链接")
	private String pdfurl;

	@Column
	@Comment("DS160申请成功后的DAT文件")
	private String daturl;

	@Column
	@Comment("错误信息图片")
	private String errorurl;

	@Column
	@Comment("错误信息")
	private String errormsg;

	@Column
	@Comment("申请人识别码")
	private String applyidcode;

	@Column
	@Comment("是否正在自动填表")
	private Integer isautofilling;

	@Column
	@Comment("领区")
	private Integer cityid;

	@Column
	@Comment("订单状态")
	private Integer status;

	@Column
	@Comment("是否作废")
	private Integer isdisable;

	@Column
	@Comment("是否付款")
	private Integer ispayed;

	@Column
	@Comment("操作人")
	private Integer opid;

	@Column
	@Comment("创建时间")
	private Date createtime;

	@Column
	@Comment("更新时间")
	private Date updatetime;

}
