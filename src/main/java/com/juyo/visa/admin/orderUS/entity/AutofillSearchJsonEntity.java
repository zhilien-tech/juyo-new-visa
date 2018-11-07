package com.juyo.visa.admin.orderUS.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class AutofillSearchJsonEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/*申请人ID*/
	private Integer id;

	/*AA号*/
	private String app_id;

	/*状态:暂存、已保存、正在申请、申请成功、申请失败、正在提交、提交成功、提交失败*/
	private String status;

	/*姓名*/
	private String name;

	/*姓名（拼音）*/
	private String name_en;

	/*出生日期*/
	private String date_of_birth;

	/*护照号码*/
	private String passport_num;

	/*预览页URL:DS160官网 预览页面截图*/
	private String review_url;

	/*PDF确认页URL:DS160官网 递交成功后出现确认页pdf链接*/
	private String pdf_url;

	/*错误图片URL:申请或递交失败后DS160官网错误截图*/
	private String error_url;

	/*Dat文件URL:DS160官网 申请成功后出现DAT文件*/
	private String dat_url;

	/*识别码*/
	private String code;

	private boolean success;

	private String errorMsg;

	private String AAcode;

	private Integer orderstatus;

}