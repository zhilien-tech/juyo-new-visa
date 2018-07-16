package com.juyo.visa.admin.weixinToken.module;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.alibaba.fastjson.JSONObject;
import com.juyo.visa.admin.weixinToken.service.WeXinTokenViewService;

@IocBean
@Filters
@At("admin/weixinToken")
public class WeiXinTokenModule {

	@Inject
	private WeXinTokenViewService weXinTokenViewService;

	/**
	 *获取 AccessToken
	 * <p>
	 */
	@At
	@POST
	public Object getAccessToken() {
		return weXinTokenViewService.getAccessToken();
	}

	/**
	 *获取 九宫格访问路径
	 * <p>
	 */
	@At
	@POST
	public String getFenrollUrl() {
		return weXinTokenViewService.getFenrollUrl();
	}

	/**
	 *获取 进度查询访问路径
	 * <p>
	 */
	@At
	@POST
	public String getProgressUrl() {
		return weXinTokenViewService.getProgressUrl();
	}

	//获取ticket
	@At
	@POST
	public JSONObject getJsApiTicket() {
		return weXinTokenViewService.getJsApiTicket();
	}

	//生成微信权限验证的参数
	@At
	@POST
	public Map<String, String> makeWXTicket(@Param("jsApiTicket") String jsApiTicket, @Param("url") String url) {
		return weXinTokenViewService.makeWXTicket(jsApiTicket, url);
	}

	//微信JSSDK上传的文件需要重新下载后上传到七牛云
	@At
	@POST
	public Object wechatJsSDKUploadToQiniu(@Param("staffId") Integer staffId, @Param("mediaIds") String mediaIds,
			@Param("sessionid") String sessionid, @Param("type") Integer type) {
		return weXinTokenViewService.wechatJsSDKUploadToQiniu(staffId, mediaIds, sessionid, type);
	}

	//扫描护照
	@At
	@POST
	public Object wechatJsSDKToPassportScan(@Param("applicantid") final int applicantid,
			@Param("mediaIds") String mediaIds, HttpServletRequest request, HttpServletResponse response) {
		return weXinTokenViewService.wechatJsSDKToPassportScan(applicantid, mediaIds, request, response);
	}

	//扫描身份证正面
	@At
	@POST
	public Object wechatJsSDKToCardScan(@Param("applicantid") final int applicantid,
			@Param("mediaIds") String mediaIds, HttpServletRequest request, HttpServletResponse response) {
		return weXinTokenViewService.wechatJsSDKToCardScan(applicantid, mediaIds, request, response);
	}

	//扫描身份证背面
	@At
	@POST
	public Object wechatJsSDKToCardBackScan(@Param("applicantid") final int applicantid,
			@Param("mediaIds") String mediaIds, HttpServletRequest request, HttpServletResponse response) {
		return weXinTokenViewService.wechatJsSDKToCardBackScan(applicantid, mediaIds, request, response);
	}

	//微信JSSDK上传的文件需要重新下载后上传到七牛云(多参数)
	@At
	@POST
	public Object wechatJsSDKNewploadToQiniu(@Param("staffId") Integer staffId, @Param("mediaIds") String mediaIds,
			@Param("sessionid") String sessionid, @Param("type") Integer type, @Param("mainid") Integer mainid,
			@Param("sequence") Integer sequence, @Param("status") Integer status) {
		return weXinTokenViewService.wechatJsSDKNewuploadToQiniu(staffId, mediaIds, sessionid, type, mainid, sequence,
				status);
	}

	//获取图片集合
	@At
	@POST
	public Object getEchoPictureList(@Param("staffId") Integer staffId, @Param("type") Integer type) {
		return weXinTokenViewService.getEchoPictureList(staffId, type);
	}

}
