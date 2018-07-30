package com.juyo.visa.admin.simple.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class WealthEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String wealthtitle;
	private String wealthvalue;

}