/**
 * WeXinAccredit.java
 * com.juyo.visa.admin.weixinToken.service
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.weixinToken.service;

import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.juyo.visa.admin.bigcustomer.service.AppEventsViewService;
import com.juyo.visa.admin.weixinToken.module.WeiXinTokenModule;
import com.juyo.visa.common.util.HttpUtil;
import com.juyo.visa.entities.TAppStaffWxinfoEntity;
import com.juyo.visa.entities.TConfWxEntity;
import com.uxuexi.core.common.util.Util;
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

	@Inject
	private AppEventsViewService appEventsViewService;
	public static Log logger = LogFactory.getLog(WeiXinTokenModule.class);

	private String WX_APPID = "wxd77f341f1b849e68";
	private String WX_APPSECRET = "e30756ac75799946d0d89868d89547be";

	//获取access_token
	public JSONObject getAccessToken(String code) {
		String accessTokenUrl;
		JSONObject accessToken = null;
		if (!Util.isEmpty(code)) {
			//应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
			accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
			String requestUrl = accessTokenUrl.replace("APPID", WX_APPID).replace("CODE", code)
					.replace("SECRET", WX_APPSECRET);
			System.out.println("getCode.requestUrl====>" + requestUrl);
			accessToken = HttpUtil.doGet(requestUrl);
		}
		return accessToken;
	}

	//根据accessToken获取用户个人信息
	public Object SaveUser(String code) {
		Map<String, Object> result = Maps.newHashMap();
		if (!Util.isEmpty(code)) {
			System.out.println("code=" + code);
			JSONObject accessTokenObject = getAccessToken(code);
			//获取access_token
			String accessToken = accessTokenObject.get("access_token").toString();
			System.out.println("accessToken=" + accessToken);
			//获取openid
			String openid = accessTokenObject.get("openid").toString();
			System.out.println("openid=" + openid);
			//新增or更新微信授权用户信息
			.0
			//调用验证用户是否已注册
			Map<String, Object> userInfo = (Map<String, Object>) appEventsViewService.checkUserLogin(openid);

			if (!Util.isEmpty(userInfo)) {
				//用户已注册
				result.put("flag", "1");
				//openid
				result.put("openid", openid);
				System.out.println();
				//姓
				result.put("firstname", userInfo.get("firstname"));
				//名
				result.put("lastname", userInfo.get("lastname"));
				//电话
				result.put("telephone", userInfo.get("telephone"));
				//邮箱
				result.put("email", userInfo.get("email"));
			} else {
				result.put("flag", "0");
				//openid
				result.put("openid", openid);

			}

		}

		return result;
	}

	//根据 token openid  获取用户个人信息 并保存
	public Object SaveOrUpdateUserInfo(String accessToken, String openid) {
		String getUserUrl;
		getUserUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		String requestUrl = getUserUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openid);
		JSONObject user = HttpUtil.doGet(requestUrl);
		TAppStaffWxinfoEntity wxinfo = dbDao.fetch(TAppStaffWxinfoEntity.class, Cnd.where("openid", "=", openid));
		if (Util.isEmpty(wxinfo)) {
			wxinfo = new TAppStaffWxinfoEntity();
		}
		if (!Util.isEmpty(user)) {
			try {
				// 用户的标识
				wxinfo.setOpenid(user.getString("openid"));
				// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
				wxinfo.setSubscribe(user.getInteger("subscribe"));
				// 用户关注时间
				wxinfo.setSubscribeTime(user.getString("subscribe_time"));
				// 昵称
				wxinfo.setNickname(user.getString("nickname"));
				// 用户的性别（1是男性，2是女性，0是未知）
				wxinfo.setSex(user.getInteger("sex"));
				// 用户所在国家
				wxinfo.setCountry(user.getString("country"));
				// 用户所在省份
				wxinfo.setProvince(user.getString("province"));
				// 用户所在城市
				wxinfo.setCity(user.getString("city"));
				// 用户的语言，简体中文为zh_CN
				wxinfo.setLanguage(user.getString("language"));
				// 用户头像
				wxinfo.setHeadimgurl(user.getString("headimgurl"));
			} catch (Exception e) {

			}
		}
		if (Util.isEmpty(wxinfo.getId())) {
			dbDao.insert(wxinfo);
		} else {
			dbDao.update(wxinfo);
		}
		return null;

	}
}
