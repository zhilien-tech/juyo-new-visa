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
import com.juyo.visa.forms.TAppStaffCredentialsAddForm;
import com.juyo.visa.forms.TAppStaffPassportUpdateForm;

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
		return bigCustomerViewService.staffList(request);
	}

	/**
	 * 分页查询
	 */
	/*@At
	@Ok("jsp")
	public Pagination staffList(@Param("..") final TAppStaffBasicinfoForm sqlParamForm,@Param("..") final Pager pager) {
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
	public Object addBaseInfo() {
		return null;
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
	 *跳转到工作人员基本信息编辑页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object updateBaseInfo(@Param("staffId") Integer staffId, @Param("isDisable") Integer isDisable,
			@Param("flag") Integer flag, HttpSession session) {
		return bigCustomerViewService.getStaffInfo(staffId, isDisable, flag, session);
	}

	/**
	 * 家庭信息
	 */
	@At
	@GET
	@Ok("jsp")
	public Object updateFamilyInfo(
		@Param("staffId") Integer staffId, 
		@Param("isDisable") Integer isDisable,
		@Param("flag") Integer flag,
		HttpSession session
	) {
		return bigCustomerViewService.updateVisaInfo(staffId, isDisable, flag, session);
	}

	/**
	 * 工作信息
	 */
	@At
	@GET
	@Ok("jsp")
	public Object updateWorkInfo(
		@Param("staffId") Integer staffId, 
		@Param("isDisable") Integer isDisable,
		@Param("flag") Integer flag,
		HttpSession session
	) {
		return bigCustomerViewService.updateVisaInfo(staffId, isDisable, flag, session);
	}

	/**
	 * 旅行信息
	 */
	@At
	@GET
	@Ok("jsp")
	public Object updateTravelInfo(
		@Param("staffId") Integer staffId, 
		@Param("isDisable") Integer isDisable,
		@Param("flag") Integer flag,
		HttpSession session
	) {
		return bigCustomerViewService.updateVisaInfo(staffId, isDisable, flag, session);
	}


	/**
	 * 执行'修改操作'
	 */
	@At
	@POST
	public Object updateStaffInfo(@Param("..") TAppStaffBasicinfoUpdateForm updateForm, HttpSession session) {
		return bigCustomerViewService.updateStaffInfo(updateForm, session);
	}

	/**
	 * 删除记录
	 */
	@At
	@POST
	public Object delete(@Param("id") final long staffId, HttpSession session) {
		return bigCustomerViewService.deleteStaffById(staffId, session);
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

	/**
	 *跳转到护照信息编辑页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object updatePassportInfo(@Param("passportId") Integer passportId, @Param("isDisable") Integer isDisable,
			@Param("orderid") Integer orderid, HttpSession session) {
		return bigCustomerViewService.getPassportInfo(passportId, isDisable, orderid, session);
	}

	/**
	 *跳转到工作人员签证信息页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object updateVisaInfo(@Param("staffId") Integer staffId, @Param("isDisable") Integer isDisable,@Param("flag") Integer flag,
			HttpSession session) {
		return bigCustomerViewService.updateVisaInfo(staffId, isDisable, flag, session);
	}


	/**
	 *跳转到游客登录签证信息页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object touristVisaInfo(@Param("staffId") Integer staffId, @Param("isDisable") Integer isDisable,@Param("flag") Integer flag,
			HttpSession session) {
		return bigCustomerViewService.updateVisaInfo(staffId, isDisable, flag, session);
	}

	/**
	 * 获取签证信息数据
	 */
	@At
	@POST
	public Object getVisaInfos(@Param("staffId") Integer staffId, HttpSession session) {
		return bigCustomerViewService.getVisaInfos(staffId, session);
	}

	/**
	 * 更新签证信息
	 */
	@At
	@POST
	public Object updateVisaInfos(@Param("data") String data, HttpServletRequest request) {
		return bigCustomerViewService.updateVisaInfos(data, request);
	}

	/**
	 *跳转到其他证件页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object otherSredentials(@Param("staffId") Integer staffId, HttpSession session) {
		return bigCustomerViewService.otherSredentials(staffId, session);
	}

	/**
	 * 执行护照信息保存
	 */
	@At
	@POST
	public Object saveEditPassport(@Param("..") TAppStaffPassportUpdateForm passportForm, HttpSession session) {
		return bigCustomerViewService.saveEditPassport(passportForm, session);
	}

	/**
	 * 护照号唯一性验证
	 */
	@At
	@POST
	public Object checkPassport(@Param("passport") String passport, @Param("passportId") Integer passportId,
			@Param("orderid") Integer orderid, HttpSession session) {
		return bigCustomerViewService.checkPassport(passport, passportId, orderid, session);
	}

	/**
	 * 上传文件
	 */
	@At
	@Ok("json")
	@AdaptBy(type = UploadAdaptor.class)
	public Object uploadFile(@Param("image") File file, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return bigCustomerViewService.uploadFile(file, request, response);
	}

	/**
	 * 
	 * 保存App拍摄资料
	 * <p>
	 *
	 * @param shootingsEntity
	 * @param session
	 * @return 
	 */
	@At
	@POST
	public Object saveAppFile(@Param("..") TAppStaffCredentialsAddForm addForm, HttpSession session) {
		return bigCustomerViewService.saveAppFile(addForm, session);
	}

	/**
	 * 
	 * 根据 人员id 和 证件类型 查询对应的证件集合
	 *
	 * @param staffId 人员id
	 * @param credentialType 证件类型
	 * @return 符合条件的证件集合
	 */
	@At
	@POST
	public Object getAppFileByCondition(@Param("staffId") Integer staffId,
			@Param("credentialType") Integer credentialType) {
		return bigCustomerViewService.getAppFileByCondition(staffId, credentialType);
	}
}