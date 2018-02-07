package com.juyo.visa.admin.myData.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.myData.service.TouristVisaService;
import com.juyo.visa.forms.TTouristtVisaInputAddForm;
import com.juyo.visa.forms.TTouristtVisaInputUpdateForm;

/**
 * 签证录入Module
 * <p>
 *
 * @author   
 * @Date	 2018年02月06日 	 
 */
@IocBean
@At("/admin/myData/touristVisainput")
@Filters
public class TouristVisaModule {

	@Inject
	private TouristVisaService touristVisaService;

	/**
	 * 跳转到签证录入页面
	 */
	@At
	@Ok("jsp")
	public Object visaInput(@Param("userId") Integer userid) {
		return touristVisaService.visaInput(userid);
	}

	/**
	 * 获取签证录入页面信息
	 */
	@At
	@POST
	public Object getTouristVisaInput(@Param("userId") Integer userid) {
		return touristVisaService.getTouristVisaInput(userid);
	}

	/**
	 * 跳转添加签证信息页面
	 */
	@At
	@Ok("jsp")
	public Object visaInputAdd(@Param("userId") Integer userid) {
		return touristVisaService.visaInputAdd(userid);
	}

	/**
	 * 添加签证信息
	 */
	@At
	@POST
	public Object add(@Param("..") TTouristtVisaInputAddForm addForm) {
		return touristVisaService.addTouristVisainput(addForm);
	}

	/**
	 * 跳转到签证信息修改页面
	 */
	@At
	@Ok("jsp")
	public Object visaInputUpdate(@Param("id") Integer id) {
		return touristVisaService.visaInputUpdate(id);
	}

	/**
	 * 更新签证信息
	 */
	@At
	@POST
	public Object updateVisainput(@Param("..") TTouristtVisaInputUpdateForm updateForm, HttpSession session) {
		return touristVisaService.updateVisainput(updateForm, session);
	}

	/**
	 * 删除签证信息
	 */
	@At
	@POST
	public Object deleteVisainput(@Param("id") Integer id) {
		return touristVisaService.deleteVisainput(id);
	}

}
