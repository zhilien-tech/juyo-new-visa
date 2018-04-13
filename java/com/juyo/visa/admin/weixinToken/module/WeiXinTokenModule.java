package com.juyo.visa.admin.weixinToken.module;

import java.util.Map;

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
			@Param("type") Integer type) {
		return weXinTokenViewService.wechatJsSDKUploadToQiniu(staffId, mediaIds, type);
	}

	//获取图片集合
	@At
	@POST
	public Object getEchoPictureList(@Param("staffId") Integer staffId, @Param("type") Integer type) {
		return weXinTokenViewService.getEchoPictureList(staffId, type);

	}

}
