package com.juyo.visa.admin.hotel.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.hotel.service.HotelViewService;
import com.juyo.visa.forms.THotelAddForm;
import com.juyo.visa.forms.THotelForm;
import com.juyo.visa.forms.THotelUpdateForm;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/hotel")
public class HotelModule {

	private static final Log log = Logs.get();

	@Inject
	private HotelViewService hotelViewService;

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
	public Pagination list(@Param("..") final THotelForm sqlParamForm,@Param("..") final Pager pager) {
		return hotelViewService.listPage(sqlParamForm,pager);
	}*/
	@At
	public Object listData(@Param("..") final THotelForm sqlParamForm) {
		return hotelViewService.listData(sqlParamForm);
	}

	/**
	 * 跳转到'添加操作'的录入数据页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object add() {
		return null;
	}

	/**
	 * 添加
	 */
	@At
	@POST
	public Object add(@Param("..") THotelAddForm addForm) {
		return hotelViewService.addHotel(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return hotelViewService.fetchHotel(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") THotelUpdateForm updateForm) {
		return hotelViewService.updateHotel(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		hotelViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		hotelViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}
}