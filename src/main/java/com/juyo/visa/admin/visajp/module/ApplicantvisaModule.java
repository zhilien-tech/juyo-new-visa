package com.juyo.visa.admin.visajp.module;

import java.util.Date;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.visajp.service.ApplicantvisaViewService;
import com.juyo.visa.common.enums.AlredyVisaTypeEnum;
import com.juyo.visa.forms.TApplicantVisaJpAddForm;
import com.juyo.visa.forms.TApplicantVisaJpForm;
import com.juyo.visa.forms.TApplicantVisaJpUpdateForm;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/visaJapan/visainput")
@Filters({//@By(type = AuthFilter.class)
})
public class ApplicantvisaModule {

	private static final Log log = Logs.get();

	@Inject
	private ApplicantvisaViewService applicantvisaViewService;

	/**
	 * 跳转到list页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object list() {
		return null;
	}

	/**
	 * 分页查询
	 */
	/*@At
	@Ok("jsp")
	public Pagination list(@Param("..") final TApplicantVisaJpForm sqlParamForm,@Param("..") final Pager pager) {
		return applicantvisaViewService.listPage(sqlParamForm,pager);
	}*/
	@At
	public Object listData(@Param("..") final TApplicantVisaJpForm sqlParamForm) {
		return applicantvisaViewService.listData(sqlParamForm);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object visaInputAdd(@Param("applicantId") Integer applicantId, @Param("isvisa") Integer isvisa,
			@Param("tourist") Integer tourist) {
		return applicantvisaViewService.visaInputAdd(applicantId, isvisa, tourist);
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..") TApplicantVisaJpAddForm addForm, @Param("isvisa") Integer isvisa) {
		if (!Util.isEmpty(isvisa)) {
			addForm.setVisaEntryTime(new Date());
		}
		return applicantvisaViewService.add(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object visaInputUpdate(@Param("id") final long id, @Param("isvisa") Integer isvisa,
			@Param("tourist") Integer tourist) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("visadatatypeenum", EnumUtil.enum2(AlredyVisaTypeEnum.class));
		result.put("applicantvisa", applicantvisaViewService.fetch(id));
		result.put("isvisa", isvisa);
		result.put("tourist", tourist);
		return result;
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TApplicantVisaJpUpdateForm updateForm) {
		return applicantvisaViewService.updatedata(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		applicantvisaViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		applicantvisaViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

}