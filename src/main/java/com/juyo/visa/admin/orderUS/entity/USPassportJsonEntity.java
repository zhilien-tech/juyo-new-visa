package com.juyo.visa.admin.orderUS.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class USPassportJsonEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String url;

	private String type;

	private String xingCn;

	private String mingCn;

	private String xingEn;

	private String mingEn;

	private String num;

	private String birth;

	private String expiryDay;

	private String visaCountry;

	private String birthCountry;

	private String issueDate;

	private String sex;

	private String sexEn;

	private String request_id;

	private String OCRline1;

	private String OCRline2;

	private boolean success;

}