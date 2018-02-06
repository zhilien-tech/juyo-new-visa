package com.juyo.visa.admin.bigcustomer.module;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.juyo.visa.admin.bigcustomer.service.BigCustomerViewService;
import com.juyo.visa.forms.TAppStaffBasicinfoAddForm;
import com.juyo.visa.forms.TAppStaffBasicinfoForm;
import com.juyo.visa.forms.TAppStaffBasicinfoUpdateForm;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
@At("/admin/bigCustomer")
public class BigCustomerModule {

	private static final Log log = Logs.get();

	@Inject
	private BigCustomerViewService bigCustomerViewService;

	/**
	 * 跳转到list页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object list(HttpServletRequest request) {
		return bigCustomerViewService.toList(request);
	}

	/**
	 * 分页查询
	 */
	/*@At
	@Ok("jsp")
	public Pagination list(@Param("..") final TAppStaffBasicinfoForm sqlParamForm,@Param("..") final Pager pager) {
		return bigcustomerViewService.listPage(sqlParamForm,pager);
	}*/
	@At
	public Object listData(@Param("..") final TAppStaffBasicinfoForm sqlParamForm, HttpSession session) {
		return bigCustomerViewService.listData(sqlParamForm, session);
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
	public Object add(@Param("..") TAppStaffBasicinfoAddForm addForm) {
		return bigCustomerViewService.add(addForm);
	}

	/**
	 * 人员管理添加
	 */
	@At
	@POST
	public Object addStaff(@Param("..") TAppStaffBasicinfoAddForm addForm, HttpSession session) {
		return bigCustomerViewService.addStaff(addForm, session);
	}

	/**
	 * 根据id获取人员信息
	 */
	@At
	public Object getStaffInfo(@Param("staffId") Integer staffId) {
		return bigCustomerViewService.getStaffInfo(staffId);
	}

	/**
	 * 跳转到'修改操作'的录入数据页面,实际就是[按照主键查询单个实体]
	 */
	@At
	@GET
	@Ok("jsp")
	public Object update(@Param("id") final long id) {
		return bigCustomerViewService.fetch(id);
	}

	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object update(@Param("..") TAppStaffBasicinfoUpdateForm updateForm) {
		return bigCustomerViewService.update(updateForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		bigCustomerViewService.deleteById(id);
		return JsonResult.success("删除成功");
	}

	/**
	 * 批量删除记录
	 */
	@At
	public Object batchDelete(@Param("ids") final Long[] ids) {
		bigCustomerViewService.batchDelete(ids);
		return JsonResult.success("删除成功");
	}

	/**
	 * 
	 * 人员管理Excel信息导入
	 */
	@At
	@Ok("json")
	@AdaptBy(type = UploadAdaptor.class)
	public Object importExcel(@Param("file") File file, HttpServletRequest request) {
		try {
			bigCustomerViewService.importExcel(file, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 文件下载
	 */
	@At
	public Object download(HttpServletRequest request, HttpServletResponse response) {
		return bigCustomerViewService.downloadTemplate(request, response);
	}

}