package com.juyo.visa.admin.order.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class PassportJsonEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String url;

	private String type;

	private String num;

	private String birth;

	private String expiryDay;

	private String visaCountry;

	private String birthCountry;

	private String issueDate;

	private String sex;

	private String sexEn;

	private String request_id;

	private boolean success;

}