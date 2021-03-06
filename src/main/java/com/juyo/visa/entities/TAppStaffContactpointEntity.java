package com.juyo.visa.entities;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_app_staff_contactpoint")
public class TAppStaffContactpointEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("人员id")
	private Integer staffid;

	@Column
	@Comment("联系人姓")
	private String firstname;

	@Column
	@Comment("联系人名")
	private String lastname;

	@Column
	@Comment("是否知道姓名")
	private Integer isknowname;

	@Column
	@Comment("组织名称")
	private String organizationname;

	@Column
	@Comment("是否知道组织名称")
	private Integer isknoworganizationname;

	@Column
	@Comment("与你的关系")
	private Integer ralationship;

	@Column
	@Comment("美国街道地址")
	private String address;

	@Column
	@Comment("美国街道地址(次选)")
	private String secaddress;

	@Column
	@Comment("市")
	private String city;

	@Column
	@Comment("州")
	private Integer state;

	@Column
	@Comment("邮政编码")
	private String zipcode;

	@Column
	@Comment("邮箱")
	private String email;

	@Column
	@Comment("电话")
	private String telephone;

	@Column
	@Comment("联系人姓(英文)")
	private String firstnameen;

	@Column
	@Comment("联系人名(英文)")
	private String lastnameen;

	@Column
	@Comment("是否知道姓名(英文)")
	private Integer isknownameen;

	@Column
	@Comment("组织名称(英文)")
	private String organizationnameen;

	@Column
	@Comment("是否知道组织名称(英文)")
	private Integer isknoworganizationnameen;

	@Column
	@Comment("与你的关系(英文)")
	private Integer ralationshipen;

	@Column
	@Comment("美国街道地址(英文)")
	private String addressen;

	@Column
	@Comment("美国街道地址(次选)(英文)")
	private String secaddressen;

	@Column
	@Comment("市(英文)")
	private String cityen;

	@Column
	@Comment("州(英文)")
	private Integer stateen;

	@Column
	@Comment("邮政编码(英文)")
	private String zipcodeen;

	@Column
	@Comment("邮箱(英文)")
	private String emailen;

	@Column
	@Comment("电话(英文)")
	private String telephoneen;
	
	@Column
    @Comment("是否知道邮箱")
	private Integer isknowemail;
	
	@Column
    @Comment("是否知道邮箱(英文)")
	private Integer isknowemailen;

}