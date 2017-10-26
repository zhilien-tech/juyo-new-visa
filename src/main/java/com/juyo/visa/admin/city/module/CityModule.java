package com.juyo.visa.admin.city.module;

import java.util.List;

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

import com.juyo.visa.admin.city.service.CityViewService;
import com.juyo.visa.forms.TCityAddForm;
import com.juyo.visa.forms.TCityForm;
import com.juyo.visa.forms.TCityUpdateForm;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/city")
@Filters({//@By(type = AuthFilter.class)
})
public class CityModule {

	private static final Log log = Logs.get();

	@Inject
	private CityViewService cityViewService;

	/**
	 * 跳转到list页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object list() {
		return cityViewService.listCountrySearch();
	}

	/**
	 * 分页查询
	 */
	/*@At
	@Ok("jsp")
	public Pagination list(@Param("..") final TCityForm sqlParamForm,@Param("..") final Pager pager) {
		return cityViewService.listPage(sqlParamForm,pager);
	}*/
	@At
	public Object listData(@Param("..") final TCityForm sqlParamForm) {
		return cityViewService.listData(sqlParamForm);
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
	public Object add(@Param("..") TCityAddForm addForm) {
		return cityViewService.addCity(addForm);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return cityViewService.fetch(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TCityUpdateForm updateForm) {
		return cityViewService.updateCity(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		cityViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		cityViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

	@At
	public Object searchByCityId(@Param("..") final TCityForm sqlParamForm) {
		return cityViewService.searchByCityId(sqlParamForm);
	}

	/**
	 * 获取城市下拉列表
	 */
	@At
	@POST
	public Object getCustomerCitySelect(@Param("cityname") String cityname, @Param("exname") String exname) {
		return cityViewService.getCustomerCitySelect(cityname, exname);
	}

	/**国家省城市三级联动*/

	@At
	@POST
	public List<String> getProvince(@Param("country") String country) {
		return cityViewService.queryProvince(country);
	}

	@At
	@POST
	public List<String> getCity(@Param("province") String province) {
		return cityViewService.queryCity(province);
	}

}