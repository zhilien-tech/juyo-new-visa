package com.juyo.visa.admin.firstTrialJp.from;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.juyo.visa.entities.TOrderBackmailEntity;

/**
 *初审编辑信息保存
 *
 * @author   彭辉
 * @Date	 2017年11月17日 	 
 */
@Data
public class FirstTrialJpEditDataForm {

	private Integer id;

	private String ordernum;

	private Integer userid;

	private Integer comid;

	//private Integer status;

	private Integer number;

	private Integer cityid;

	private Integer urgenttype;

	private Integer urgentday;

	private Integer travel;

	private Integer paytype;

	private Double money;

	private Date gotripdate;

	private Integer stayday;

	private Date backtripdate;

	private Date sendvisadate;

	private Date outvisadate;

	private String realreceiveremark;

	private Integer customerId;

	private Integer isdirectcus;

	private String comName;

	private String comshortname;

	private String linkman;

	private String telephone;

	private String email;

	private Integer orderid;

	private Integer visatype;

	private String visacounty;

	private Integer isvisit;

	private String threecounty;

	//回邮信息
	private List<TOrderBackmailEntity> backMailInfos;
}
