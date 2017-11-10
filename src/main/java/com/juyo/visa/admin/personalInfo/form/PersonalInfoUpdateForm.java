package com.juyo.visa.admin.personalInfo.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

/**
 * 个人信息 扩展类
 * @author   彭辉
 * @Date	 2017年11月9日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PersonalInfoUpdateForm extends ModForm {

	//用户id
	private Integer userid;
	//公司id
	private Integer comId;
	//联系QQ
	private String qq;
	//电子邮箱
	private String email;
	//用户姓名
	private String username;
	//用户名/手机号码
	private String mobile;
	//密码
	private String password;
	//用户类型
	private Integer userType;
	//所属部门
	private String department;
	//用户职位
	private String job;

}
