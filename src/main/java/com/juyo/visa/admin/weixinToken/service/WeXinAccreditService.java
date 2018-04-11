/**
 * WeXinAccredit.java
 * com.juyo.visa.admin.weixinToken.service
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.weixinToken.service;

import org.nutz.ioc.loader.annotation.Inject;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.juyo.visa.admin.weixinToken.module.WeiXinTokenModule;
import com.juyo.visa.common.util.HttpUtil;
import com.juyo.visa.entities.TConfWxEntity;
import com.uxuexi.core.redis.RedisDao;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   朱晓川
 * @Date	 2018年4月10日 	 
 */
public class WeXinAccreditService extends BaseService<TConfWxEntity> {
	@Inject
	private RedisDao redisDao;

	public static Log logger = LogFactory.getLog(WeiXinTokenModule.class);

	//获取code
	public JSONObject getCode(String code) {
		TConfWxEntity wx = dbDao.fetch(TConfWxEntity.class, 1);
		String WX_APPID = wx.getAppid();
		String WX_APPSECRET = wx.getAppsecret();
		String WX_TOKENKEY = wx.getAccesstokenkey();
		String accessTokenUrl;
		JSONObject accessToken = null;
		if (wx != null) {
			//				WX_CODEURL = redisDao.get(WX_CODEURL);
			//			if (Util.isEmpty(WX_CODEURL)) {
			//应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
			accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
			String requestUrl = accessTokenUrl.replace("APPID", WX_APPID).replace("REDIRECT_URI", code)
					.replace("SECRET", WX_APPSECRET);
			logger.info("getCode.requestUrl====>" + requestUrl);
			accessToken = HttpUtil.doGet(requestUrl);
			//			{
			//			   "access_token":"ACCESS_TOKEN",
			//			   "expires_in":7200,
			//			   "refresh_token":"REFRESH_TOKEN",
			//			   "openid":"OPENID",
			//			   "scope":"SCOPE",
			//			   "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
			//			}
			//redis中设置 access_token
			redisDao.set("access_token", accessToken.get("access_token").toString());
			redisDao.set("openid", accessToken.get("openid").toString());
			//							redisDao.expire("openid", 5000);
		}

		return accessToken;
	}

	//根据accessToken获取用户个人信息
	public void SaveUser(String code) {
		JSONObject accessTokenObject = getCode(code);
		String getUserUrl;
		//获取access_token
		String accessToken = accessTokenObject.get("access_token").toString();
		//获取openid
		String openid = accessTokenObject.get("openid").toString();
		getUserUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		String requestUrl = getUserUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openid);
		JSONObject user = HttpUtil.doGet(requestUrl);
		//
	}

}
