/**
 * OrderUSModule.java
 * com.juyo.visa.admin.orderUS.module
 * Copyright (c) 2018, 北京科技有限公司版权所有.
 */

package com.juyo.visa.admin.orderUS.module;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.UploadAdaptor;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.orderUS.form.OrderUSListDataForm;
import com.juyo.visa.admin.orderUS.service.AutofillService;
import com.juyo.visa.admin.orderUS.service.OrderUSViewService;
import com.juyo.visa.admin.simulate.form.JapanSimulatorForm;
import com.juyo.visa.forms.OrderUpdateForm;
import com.juyo.visa.forms.TAppStaffVisaUsAddForm;
import com.juyo.visa.forms.TAppStaffVisaUsUpdateForm;

/**
 * 美国订单US
 *
 * @author   
 * @Date	 2018年3月30日 	 
 */
@IocBean
@At("/admin/orderUS")
public class OrderUSModule {

	@Inject
	private OrderUSViewService orderUSViewService;

	@Inject
	private AutofillService autofillService;

	/**
	 * 跳转到美国订单list页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object listUS(HttpServletRequest request) {
		return orderUSViewService.toList(request);
	}

	/**
	 * 加载列表页数据
	 */
	@At
	@POST
	public Object listDetailUSData(@Param("..") final OrderUSListDataForm sqlParamForm, HttpSession session) {
		return orderUSViewService.listData(sqlParamForm, session);
	}

	/**
	 * 点击我的或者全部后列表数据回到之前位置
	 */
	@At
	@POST
	public Object toNewListdata(@Param("..") final OrderUSListDataForm sqlParamForm, HttpSession session) {
		return orderUSViewService.newlistData(sqlParamForm, session);
	}

	/**
	 * 跳转到美国订单详情页/下单 共用
	 */
	@At
	@GET
	@Ok("jsp")
	public Object orderUSDetail(@Param("orderid") int orderid, @Param("addOrder") int addOrder,
			HttpServletRequest request) {
		return orderUSViewService.getOrderUSDetail(orderid, addOrder, request);
	}

	/**
	 * 下单
	 */
	@At
	@GET
	@Ok("jsp")
	public Object addOrderUS() {
		return null;
	}

	/**
	 * 获取最新订单
	 */
	@At
	@POST
	public Object getOrderRefresh(@Param("orderid") int orderid, @Param("addOrder") int addOrder,
			HttpServletRequest request) {
		return orderUSViewService.getOrderUSDetail(orderid, addOrder, request);
	}

	/**
	 * 获取大客户公司姓名select2下拉
	 */
	@At
	@POST
	public Object getBigcustomerSelect(@Param("bigcustomername") String bigcustomername) {
		return orderUSViewService.getBigcustomerSelect(bigcustomername);
	}

	/**
	 * 认领按钮功能
	 */
	@At
	@POST
	public Object toMyself(@Param("orderid") int orderid, HttpSession session) {
		return orderUSViewService.toMyself(orderid, session);
	}

	/**
	 * 跳转到签证录入页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object visaInput(@Param("staffid") int staffid, @Param("orderid") int orderid, HttpSession session) {
		return orderUSViewService.visaInfo(staffid, orderid, session);
	}

	/**
	 * 获取签证录入页面数据
	 */
	@At
	@POST
	public Object getVisaInfoData(@Param("staffid") int staffid) {
		return orderUSViewService.getVisaInfoData(staffid);
	}

	/**
	 * 跳转到签证录入修改页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object visaInputUpdate(@Param("id") int id, @Param("orderid") int orderid) {
		return orderUSViewService.visaInputUpdate(id, orderid);
	}

	/**
	 * 删除签证录入
	 */
	@At
	@POST
	public Object deleteVisainput(@Param("id") int id) {
		return orderUSViewService.deleteVisainput(id);
	}

	/**
	 * 跳转到添加签证录入页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object visaInputAdd(@Param("staffid") int staffid, @Param("orderid") int orderid) {
		return orderUSViewService.visaInputAdd(staffid, orderid);
	}

	/**
	 * 保存签证录入添加
	 */
	@At
	@POST
	public Object addVisainput(@Param("..") TAppStaffVisaUsAddForm addForm, HttpSession session) {
		return orderUSViewService.addVisainput(addForm, session);
	}

	/**
	 * 保存签证录入修改信息
	 */
	@At
	@POST
	public Object updateVisainput(@Param("..") TAppStaffVisaUsUpdateForm updateForm, HttpSession session) {
		return orderUSViewService.updatedata(updateForm, session);
	}

	/**
	 * 跳转到跟进页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object addFollow(@Param("orderid") int orderid, @Param("addorder") int addorder) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("orderid", orderid);
		result.put("addorder", addorder);
		return result;
	}

	/**
	 * 保存跟进内容
	 */
	@At
	@POST
	public Object saveFollow(@Param("orderid") int orderid, @Param("content") String content, HttpSession session) {
		return orderUSViewService.saveFollow(orderid, content, session);
	}

	/**
	 * 跟进解决按钮
	 */
	@At
	@POST
	public Object solveFollow(@Param("id") int id, HttpSession session) {
		return orderUSViewService.solveFollow(id, session);
	}

	/**
	 * 跳转到日志页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object log(@Param("orderid") int orderid, HttpSession session) {
		return orderUSViewService.toLog(orderid, session);
	}

	/**
	 * 获取日志页面数据
	 */
	@At
	@POST
	public Object getLogs(@Param("orderid") int orderid, HttpSession session) {
		return orderUSViewService.getLogs(orderid, session);
	}

	/**
	 * 日志页面点击保存时负责人变更
	 */
	@At
	@POST
	public Object changePrincipal(@Param("orderid") int orderid, @Param("principal") int principal, HttpSession session) {
		return orderUSViewService.changePrincipal(orderid, principal, session);
	}

	/**
	 * 
	 * 分享发送消息
	 *
	 * @param staffId 人员id
	 * @param orderid 订单id
	 * @param telephone 手机号
	 * @return 
	 */
	@At
	@POST
	public Object sendShareMsg(@Param("staffId") Integer staffId, @Param("orderid") Integer orderid,
			@Param("sendType") String sendType, HttpServletRequest request) {
		return orderUSViewService.sendShareMsg(staffId, orderid, sendType, request);
	}

	/**
	 * 验证信息是否完整
	 */
	@At
	@POST
	public Object validateInfoIsFull(@Param("orderid") int orderid, @Param("staffid") int staffid, HttpSession session) {
		return autofillService.getData(orderid, staffid);
	}

	@At
	@GET
	@Ok("jsp")
	public Object autofillError(@Param("errData") String errData) {
		String result = "";

		/*try {
			result = new String(errData.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}*/

		return errData;
	}

	/*
	 * 预检查
	 */
	@At
	@POST
	public Object preautofill(@Param("orderid") int orderid, HttpSession session) {
		return orderUSViewService.preautofill(orderid, session);
	}

	/*
	 * 正式填写
	 */
	@At
	@POST
	public Object autofill(@Param("orderid") int orderid) {
		return orderUSViewService.formallyfill(orderid);
	}

	/**
	 * 打开验证码页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object testUS() {
		return null;
	}

	@At
	@GET
	@Ok("jsp")
	public Object writeVcode() {
		return null;
	}

	@At
	@POST
	public Object returnVcode(@Param("vcode") String vcode, HttpSession session) {
		return orderUSViewService.returnVcode(vcode, session);
	}

	@At
	public Object toRenturnVcode(@Param("..") JapanSimulatorForm form) {
		return orderUSViewService.toRenturnVcode(form);
	}

	/**
	 * 通过
	 */
	@At
	@POST
	public Object passUS(@Param("orderid") int orderid, HttpSession session) {
		return orderUSViewService.passUS(orderid, session);
	}

	/**
	 * 拒绝
	 */
	@At
	@POST
	public Object refuseUS(@Param("orderid") int orderid, HttpSession session) {
		return orderUSViewService.refuseUS(orderid, session);
	}

	/**
	 * 作废
	 */
	@At
	@POST
	public Object disabled(@Param("orderid") int orderid, HttpSession session) {
		return orderUSViewService.disabled(orderid, session);
	}

	/**
	 * 还原
	 */
	@At
	@POST
	public Object undisabled(@Param("orderid") int orderid) {
		return orderUSViewService.undisabled(orderid);
	}

	/**
	 * 订单保存
	 */
	@At
	@POST
	public Object orderSave(@Param("..") OrderUpdateForm form, HttpSession session) {
		return orderUSViewService.orderSave(form, session);
	}

	/**
	 * 跳转到通知页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object notice(@Param("orderid") int orderid, @Param("staffid") int staffid, HttpSession session) {
		return orderUSViewService.toNotice(orderid, staffid, session);
	}

	/**
	 * 身份证正面扫描
	 */
	@At
	@Ok("json")
	@Filters
	@AdaptBy(type = UploadAdaptor.class)
	public Object IDCardRecognition(@Param("encode") String encode, @Param("image") File file,
			@Param("staffid") int staffid) {
		return orderUSViewService.IDCardRecognition(encode, file, staffid);
	}

	/**
	 * 身份证背面扫描
	 */
	@At
	@Ok("json")
	@Filters
	@AdaptBy(type = UploadAdaptor.class)
	public Object IDCardRecognitionBack(@Param("encode") String encode, @Param("image") File file,
			@Param("staffid") int staffid) {
		return orderUSViewService.IDCardRecognitionBack(encode, file, staffid);
	}

	/**
	 * 护照扫描
	 */
	@At
	@Ok("json")
	@Filters
	@AdaptBy(type = UploadAdaptor.class)
	public Object passportRecognition(@Param("encode") String encode, @Param("image") File file,
			@Param("staffid") int staffid) {
		return orderUSViewService.passportRecognitionBack(encode, file, staffid);
	}

	//微信JSSDK上传的文件需要重新下载后上传到七牛云
	/*@At
	@POST
	public Object wechatJsSDKUploadToQiniu(@Param("staffId") Integer staffId, @Param("mediaIds") String mediaIds,
			@Param("sessionid") String sessionid, @Param("type") Integer type) {
		return orderUSViewService.wechatJsSDKUploadToQiniu(staffId, mediaIds, sessionid, type);
	}*/

	/**
	 * 下载文件
	 */
	@At
	@GET
	public Object downloadFile(@Param("orderid") int orderid, @Param("type") int type, HttpServletResponse response) {
		return orderUSViewService.downloadFile(orderid, type, response);
	}

	/**
	 * 获取错误信息图片
	 */
	@At
	@GET
	@Ok("jsp")
	public Object toErrorphoto(@Param("errorurl") String errorurl, @Param("type") int type) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("imgurl", errorurl);
		result.put("imgtype", type);
		return result;
		//return orderUSViewService.toErrorphoto(errorurl);
	}

	@At
	@POST
	public Object isAutofilled(@Param("orderid") int orderid, @Param("staffid") int staffid) {
		return orderUSViewService.isAutofilled(orderid, staffid);
	}
}
