package com.juyo.visa.admin.orderUS.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class USStaffJsonEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String url;
	private String address;
	private String birth;

	private String name;

	private String nationality;

	private String num;

	private String sex;

	private String request_id;

	private String province;

	private String city;

	private String issue;

	private String starttime;

	private String endtime;

	private boolean success;

}