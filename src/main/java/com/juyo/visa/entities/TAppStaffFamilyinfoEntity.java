package com.juyo.visa.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_app_staff_familyinfo")
public class TAppStaffFamilyinfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("人员id")
	private Integer staffid;

	@Column
	@Comment("父亲的姓")
	private String fatherfirstname;

	@Column
	@Comment("是否知道父亲的姓")
	private Integer isknowfatherfirstname;

	@Column
	@Comment("父亲的名")
	private String fatherlastname;

	@Column
	@Comment("是否知道父亲的名")
	private Integer isknowfatherlastname;

	@Column
	@Comment("父亲的出生日期")
	private Date fatherbirthday;

	@Column
	@Comment("是否知道父亲的出生日期")
	private Integer isknowfatherbirthday;

	@Column
	@Comment("父亲是否在美国")
	private Integer isfatherinus;

	@Column
	@Comment("父亲在美国的身份")
	private Integer fatherstatus;

	@Column
	@Comment("母亲的姓")
	private String motherfirstname;

	@Column
	@Comment("是否知道母亲的姓")
	private Integer isknowmotherfirstname;

	@Column
	@Comment("母亲的名")
	private String motherlastname;

	@Column
	@Comment("是否知道母亲的名")
	private Integer isknowmotherlastname;

	@Column
	@Comment("母亲的出生日期")
	private Date motherbirthday;

	@Column
	@Comment("结婚日期")
	private Date marrieddate;

	@Column
	@Comment("离婚日期")
	private Date divorcedate;

	@Column
	@Comment("离婚国家")
	private Date divorcecountry;

	@Column
	@Comment("离婚国家(英文)")
	private Date divorcecountryen;

	@Column
	@Comment("离婚原因")
	private Date divorcereason;

	@Column
	@Comment("是否知道母亲的出生日期")
	private Integer isknowmotherbirthday;

	@Column
	@Comment("母亲是否在美国")
	private Integer ismotherinus;

	@Column
	@Comment("母亲在美国的身份")
	private Integer motherstatus;

	@Column
	@Comment("在美国除了父母是否还有直系亲属")
	private Integer hasimmediaterelatives;

	@Column
	@Comment("是否还有别的亲属")
	private Integer hasotherrelatives;

	@Column
	@Comment("配偶的姓")
	private String spousefirstname;

	@Column
	@Comment("配偶的名")
	private String spouselastname;

	@Column
	@Comment("配偶的生日")
	private Date spousebirthday;

	@Column
	@Comment("配偶的国籍")
	private Integer spousenationality;

	@Column
	@Comment("配偶的出生城市")
	private String spousecity;

	@Column
	@Comment("是否知道配偶的出生城市")
	private Integer isknowspousecity;

	@Column
	@Comment("配偶的出生国家")
	private Integer spousecountry;

	@Column
	@Comment("配偶的联系地址")
	private Integer spouseaddress;

	@Column
	@Comment("街道地址(首选)")
	private String firstaddress;

	@Column
	@Comment("街道地址(次选)")
	private String secondaddress;

	@Column
	@Comment("市")
	private String city;

	@Column
	@Comment("省")
	private String province;

	@Column
	@Comment("省是否适用")
	private Integer isprovinceapply;

	@Column
	@Comment("邮政编码")
	private String zipcode;

	@Column
	@Comment("邮政编码是否适用")
	private Integer iszipcodeapply;

	@Column
	@Comment("国家")
	private Integer country;

	@Column
	@Comment("父亲的姓(英文)")
	private String fatherfirstnameen;

	@Column
	@Comment("是否知道父亲的姓(英文)")
	private Integer isknowfatherfirstnameen;

	@Column
	@Comment("父亲的名(英文)")
	private String fatherlastnameen;

	@Column
	@Comment("是否知道父亲的名(英文)")
	private Integer isknowfatherlastnameen;

	@Column
	@Comment("父亲的出生日期(英文)")
	private Date fatherbirthdayen;

	@Column
	@Comment("是否知道父亲的出生日期(英文)")
	private Integer isknowfatherbirthdayen;

	@Column
	@Comment("父亲是否在美国(英文)")
	private Integer isfatherinusen;

	@Column
	@Comment("父亲在美国的身份(英文)")
	private Integer fatherstatusen;

	@Column
	@Comment("母亲的姓(英文)")
	private String motherfirstnameen;

	@Column
	@Comment("是否知道母亲的姓(英文)")
	private Integer isknowmotherfirstnameen;

	@Column
	@Comment("母亲的名(英文)")
	private String motherlastnameen;

	@Column
	@Comment("是否知道母亲的名(英文)")
	private Integer isknowmotherlastnameen;

	@Column
	@Comment("母亲的出生日期(英文)")
	private Date motherbirthdayen;

	@Column
	@Comment("是否知道母亲的出生日期(英文)")
	private Integer isknowmotherbirthdayen;

	@Column
	@Comment("母亲是否在美国(英文)")
	private Integer ismotherinusen;

	@Column
	@Comment("母亲在美国的身份(英文)")
	private Integer motherstatusen;

	@Column
	@Comment("在美国除了父母是否还有直系亲属(英文)")
	private Integer hasimmediaterelativesen;

	@Column
	@Comment("是否还有别的亲属(英文)")
	private Integer hasotherrelativesen;

	@Column
	@Comment("配偶的姓(英文)")
	private String spousefirstnameen;

	@Column
	@Comment("配偶的名(英文)")
	private String spouselastnameen;

	@Column
	@Comment("配偶的生日(英文)")
	private Date spousebirthdayen;

	@Column
	@Comment("配偶的国籍(英文)")
	private Integer spousenationalityen;

	@Column
	@Comment("配偶的出生城市(英文)")
	private String spousecityen;

	@Column
	@Comment("是否知道配偶的出生城市(英文)")
	private Integer isknowspousecityen;

	@Column
	@Comment("配偶的出生国家(英文)")
	private Integer spousecountryen;

	@Column
	@Comment("配偶的联系地址(英文)")
	private Integer spouseaddressen;

	@Column
	@Comment("街道地址(首选)(英文)")
	private String firstaddressen;

	@Column
	@Comment("街道地址(次选)(英文)")
	private String secondaddressen;

	@Column
	@Comment("市(英文)")
	private String cityen;

	@Column
	@Comment("省(英文)")
	private String provinceen;

	@Column
	@Comment("省是否适用(英文)")
	private Integer isprovinceapplyen;

	@Column
	@Comment("邮政编码(英文)")
	private String zipcodeen;

	@Column
	@Comment("邮政编码是否适用(英文)")
	private Integer iszipcodeapplyen;

	@Column
	@Comment("国家(英文)")
	private Integer countryen;

}