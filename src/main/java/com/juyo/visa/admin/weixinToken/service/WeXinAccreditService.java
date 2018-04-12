/**
 * WeXinAccredit.java
 * com.juyo.visa.admin.weixinToken.service
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.weixinToken.service;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
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

	public static Log logger = LogFactory.getLog(WeiXinTokenModule.class);

	//获取code
	private String WX_APPID = "wxd77f341f1b849e68";
	private String WX_APPSECRET = "e30756ac75799946d0d89868d89547be";

	public JSONObject getAccessToken(String code) {
		String accessTokenUrl;
		JSONObject accessToken = null;
		if (!Util.isEmpty(code)) {
			//			TConfWxEntity wx = dbDao.fetch(TConfWxEntity.class, 1);
			//		String WX_TOKENKEY = wx.getAccesstokenkey();

			//应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
			accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
			String requestUrl = accessTokenUrl.replace("APPID", WX_APPID).replace("CODE", code)
					.replace("SECRET", WX_APPSECRET);
			System.out.println("getCode.requestUrl====>" + requestUrl);
			accessToken = HttpUtil.doGet(requestUrl);
			//返回的参数
			//{"access_token":"ACCESS_TOKEN","expires_in":7200,"refresh_token":"REFRESH_TOKEN",
			//"openid":"OPENID","scope":"SCOPE","unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"}
			//redis中设置 access_token
			//		redisDao.set("access_token", accessToken.get("access_token").toString());
			//		redisDao.set("openid", accessToken.get("openid").toString());
			//							redisDao.expire("openid", 5000);
			//		}
		}
		return accessToken;
	}

	//根据accessToken获取用户个人信息
	public Object SaveUser(String code) {
		if (!Util.isEmpty(code)) {
			System.out.println("code=" + code);
			JSONObject accessTokenObject = getAccessToken(code);
			String getUserUrl;
			//获取access_token
			String accessToken = accessTokenObject.get("access_token").toString();
			System.out.println("accessToken=" + accessToken);
			//获取openid
			String openid = accessTokenObject.get("openid").toString();
			System.out.println(openid);
			//判断用户是否授权过
			TAppStaffWxinfoEntity wxinfoEntity = dbDao.fetch(TAppStaffWxinfoEntity.class,
					Cnd.where("openid", "=", openid));
			if (Util.isEmpty(wxinfoEntity)) {
				return "12";
			} else {
				System.out.println("openid=" + openid);
				getUserUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
				String requestUrl = getUserUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openid);
				System.out.println("userUrl" + requestUrl);
				JSONObject user = HttpUtil.doGet(requestUrl);
				//		{
				//			   "openid":" OPENID",
				//			   " nickname": NICKNAME,
				//			   "sex":"1",
				//			   "province":"PROVINCE"
				//			   "city":"CITY",
				//			   "country":"COUNTRY",
				//			    "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46", 
				//				"privilege":[
				//				"PRIVILEGE1"
				//				"PRIVILEGE2"
				//			    ],
				//			    "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
				//			}
				//根据openid查询用户信息
				TAppStaffWxinfoEntity wxinfo = dbDao.fetch(TAppStaffWxinfoEntity.class,
						Cnd.where("openid", "=", openid));
				if (Util.isEmpty(wxinfo)) {
					wxinfo = new TAppStaffWxinfoEntity();
				}
				//openid
				wxinfo.setOpenid(openid);
				//微信用户昵称
				System.out.println("nickname=" + user.get("nickname").toString());
				wxinfo.setNickname(user.get("nickname").toString());
				//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
				wxinfo.setSex((int) user.get("sex"));
				//用户个人资料填写的省份
				wxinfo.setPrivilege(user.get("province").toString());
				//普通用户个人资料填写的城市
				wxinfo.setCity(user.get("city").toString());
				//国家，如中国为CN
				wxinfo.setCountry(user.get("country").toString());
				//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
				wxinfo.setHeadimgurl(user.get("headimgurl").toString());
				if (Util.isEmpty(wxinfo.getId())) {
					dbDao.insert(wxinfo);
				} else {
					dbDao.update(wxinfo);
				}
			}
		}
		return "1";
	}

	//	public Object VerifyUser(String code) {
	//		
	//		//获取用户openid
	//		String openidUrl;
	//		JSONObject openidObject = null;
	//		//通过静默snsapi_base授权获取用户openid
	//		openidUrl = "";
	//		openidObject = HttpUtil.doGet(openidUrl);
	//		//获取用户openid
	//		Object object = openidObject.get("openid");
	//		accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	//		String requestUrl = accessTokenUrl.replace("APPID", WX_APPID).replace("CODE", code)
	//		return null;
	//
	//	}
}
