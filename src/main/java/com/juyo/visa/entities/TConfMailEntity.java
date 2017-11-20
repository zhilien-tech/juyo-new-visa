package com.juyo.visa.entities;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_conf_mail")
public class TConfMailEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer iD;

	@Column
	@Comment("")
	private String aDDRESS;

	@Column
	@Comment("")
	private String aUTH;

	@Column
	@Comment("")
	private String aVAILABLE;

	@Column
	@Comment("")
	private String hOST;

	@Column
	@Comment("")
	private String pASSWORD;

	@Column
	@Comment("")
	private Integer pORT;

	@Column
	@Comment("")
	private String uSERNAME;

	@Column
	@Comment("优先级")
	private Integer pRIORITY = 99;

}