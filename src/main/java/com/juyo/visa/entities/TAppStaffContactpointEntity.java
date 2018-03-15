package com.juyo.visa.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_app_staff_contactpoint")
public class TAppStaffContactpointEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("人员id")
	private String staffid;
	
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
	

}