package com.juyo.visa.admin.weixinToken.module;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.juyo.visa.common.util.HttpUtil;
import com.juyo.visa.entities.TConfMailEntity;
import com.juyo.visa.entities.TConfWxEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.redis.RedisDao;
import com.uxuexi.core.web.base.service.BaseService;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;



@IocBean
@At("admin/weixinToken")
public class WeiXinTokenModule extends BaseService<TConfWxEntity>{

	@Inject
	private RedisDao redisDao;
	
	public static Log logger = LogFactory.getLog(WeiXinTokenModule.class);
	
	//获取accessToken
	private Object getAccessToken(){
		
		TConfWxEntity wx = dbDao.fetch(TConfWxEntity.class, 1);
		String WX_APPID = wx.getAppid();
		String WX_APPSECRET = wx.getAppsecret();
		String WX_TOKENKEY = wx.getAccesstokenkey();
		
		String accessTokenUrl;
		if (wx == null) {
			 accessTokenUrl = "请联系管理员配置微信公众号!";
		} else {
			 accessTokenUrl = redisDao.get(WX_TOKENKEY);
			 if(Util.isEmpty(accessTokenUrl)) {
			    	accessTokenUrl= "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
		    	    String requestUrl = accessTokenUrl.replace("APPID",WX_APPID).replace("APPSECRET",WX_APPSECRET);
		    	    logger.info("getAccessToken.requestUrl====>"+requestUrl);
		    	    JSONObject result = HttpUtil.doGet(requestUrl);
		    	    //redis中设置 access_token
		    	    redisDao.set(WX_TOKENKEY, requestUrl);
		    	    redisDao.expire(WX_TOKENKEY, 5000);
		    	    accessTokenUrl = requestUrl;
			    }
		}
	    
	    return accessTokenUrl ;
	}

	//获取ticket
	private JSONObject getJsApiTicket(){
		String accessToken = (String)getAccessToken();
	    String apiTicketUrl= "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	    String requestUrl = apiTicketUrl.replace("ACCESS_TOKEN", accessToken);
	    logger.info("getJsApiTicket.requestUrl====>"+requestUrl);
	    JSONObject result = HttpUtil.doGet(requestUrl);
	    return result;
	}

	//生成微信权限验证的参数
	public Map<String, String> makeWXTicket(String jsApiTicket, String url) {
		
		TConfWxEntity wx = dbDao.fetch(TConfWxEntity.class, 1);
		String WX_APPID = wx.getAppid();
		String WX_APPSECRET = wx.getAppsecret();
		String WX_TOKENKEY = wx.getAccesstokenkey();
		
	    Map<String, String> ret = new HashMap<String, String>();
	    String nonceStr = createNonceStr();
	    String timestamp = createTimestamp();
	    String string1;
	    String signature = "";

	    //注意这里参数名必须全部小写，且必须有序
	    string1 = "jsapi_ticket=" + jsApiTicket +
	            "&noncestr=" + nonceStr +
	            "&timestamp=" + timestamp +
	            "&url=" + url;
	    logger.info("String1=====>"+string1);
	    try
	    {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(string1.getBytes("UTF-8"));
	        signature = byteToHex(crypt.digest());
	        logger.info("signature=====>"+signature);
	    }
	    catch (NoSuchAlgorithmException e)
	    {
	    	logger.error("WeChatController.makeWXTicket=====Start");
	    	logger.error(e.getMessage(),e);
	    	logger.error("WeChatController.makeWXTicket=====End");
	    }
	    catch (UnsupportedEncodingException e)
	    {
	    	logger.error("WeChatController.makeWXTicket=====Start");
	    	logger.error(e.getMessage(),e);
	        logger.error("WeChatController.makeWXTicket=====End");
	    }

	    ret.put("url", url);
	    ret.put("jsapi_ticket", jsApiTicket);
	    ret.put("nonceStr", nonceStr);
	    ret.put("timestamp", timestamp);
	    ret.put("signature", signature);
	    ret.put("appid", WX_APPID);

	    return ret;
	}
	//字节数组转换为十六进制字符串
	private static String byteToHex(final byte[] hash) {
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
	//生成随机字符串
	private static String createNonceStr() {
	    return UUID.randomUUID().toString();
	}
	//生成时间戳
	private static String createTimestamp() {
	    return Long.toString(System.currentTimeMillis() / 1000);
	}
	

}
