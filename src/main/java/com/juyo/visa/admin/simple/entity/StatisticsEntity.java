package com.juyo.visa.admin.simple.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class StatisticsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer orderscount;
	private Integer peopletotal;
	private Integer disableorder;
	private Integer disablepeople;
	private Integer zhaobaoorder;
	private Integer zhaobaopeople;
	private Integer singleperson;
	private Integer multiplayer;

	;

}