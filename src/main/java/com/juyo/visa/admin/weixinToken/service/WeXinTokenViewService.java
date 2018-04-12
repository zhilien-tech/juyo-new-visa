package com.juyo.visa.admin.weixinToken.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.annotation.Param;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.juyo.visa.admin.weixinToken.module.WeiXinTokenModule;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.enums.visaProcess.TAppStaffCredentialsEnum;
import com.juyo.visa.common.util.HttpUtil;
import com.juyo.visa.entities.TAppStaffCredentialsEntity;
import com.juyo.visa.entities.TConfWxEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.redis.RedisDao;
import com.uxuexi.core.web.base.service.BaseService;

public class WeXinTokenViewService extends BaseService<TConfWxEntity> {

	@Inject
	private RedisDao redisDao;

	@Inject
	private UploadService qiniuUploadService;//文件上传

	public static Log logger = LogFactory.getLog(WeiXinTokenModule.class);

	//获取accessToken
	public Object getAccessToken() {

		TConfWxEntity wx = dbDao.fetch(TConfWxEntity.class, 1);
		String WX_APPID = wx.getAppid();
		String WX_APPSECRET = wx.getAppsecret();
		String WX_TOKENKEY = wx.getAccesstokenkey();

		String accessTokenUrl;
		if (wx == null) {
			accessTokenUrl = "请联系管理员配置微信公众号!";
		} else {
			accessTokenUrl = redisDao.get(WX_TOKENKEY);
			if (Util.isEmpty(accessTokenUrl)) {
				accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
				String requestUrl = accessTokenUrl.replace("APPID", WX_APPID).replace("APPSECRET", WX_APPSECRET);
				JSONObject result = HttpUtil.doGet(requestUrl);
				//redis中设置 access_token
				accessTokenUrl = result.getString("access_token");
				redisDao.set(WX_TOKENKEY, accessTokenUrl);
				redisDao.expire(WX_TOKENKEY, 5000);

				accessTokenUrl = requestUrl;

			}
		}

		return accessTokenUrl;
	}

	//获取ticket
	public JSONObject getJsApiTicket() {
		String accessToken = (String) getAccessToken();
		String apiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
		String requestUrl = apiTicketUrl.replace("ACCESS_TOKEN", accessToken);
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
		string1 = "jsapi_ticket=" + jsApiTicket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + url;
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
			//logger.info("signature=====>" + signature);
		} catch (NoSuchAlgorithmException e) {
			logger.error("WeChatController.makeWXTicket=====Start");
			logger.error(e.getMessage(), e);
			logger.error("WeChatController.makeWXTicket=====End");
		} catch (UnsupportedEncodingException e) {
			logger.error("WeChatController.makeWXTicket=====Start");
			logger.error(e.getMessage(), e);
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



	/**
	 * 微信JSSDK上传的文件需要重新下载后上传到七牛云
	 *
	 * @param 
	 * @param 
	 * @param mediaIds
	 * @param 
	 * @return
	 */
	public Object wechatJsSDKUploadToQiniu(Integer staffId, String[] mediaIds, Integer type) {
		Date nowDate = DateUtil.nowDate();

		List<TAppStaffCredentialsEntity> celist_old = dbDao.query(TAppStaffCredentialsEntity.class, Cnd.where("staffid","=",staffId), null);

		List<TAppStaffCredentialsEntity> celist_new = new ArrayList<TAppStaffCredentialsEntity>();
		if(!Util.isEmpty(mediaIds)) {
			for (String mediaId : mediaIds) {
				String accessToken = (String)getAccessToken();
				String extName = getExtName(accessToken, mediaId);//获取扩展名
				InputStream inputStream = getInputStream(accessToken, mediaId);//获取输入流
				String url = "http://oyu1xyxxk.bkt.clouddn.com/"+qiniuUploadService.uploadImage(inputStream, extName, mediaId);

				TAppStaffCredentialsEntity credentialEntity = new TAppStaffCredentialsEntity();
				credentialEntity.setStaffid(staffId);
				credentialEntity.setUrl(url);
				credentialEntity.setType(type);
				credentialEntity.setCreatetime(nowDate);
				credentialEntity.setUpdatetime(nowDate);

				celist_new.add(credentialEntity);
			}
		}

		dbDao.updateRelations(celist_old, celist_new);

		return null;
	}


	/**
	 * 
	 * @param staffId 人员id
	 * @param type 图片枚举类型
	 * @return
	 */
	public Object getEchoPictureList(Integer staffId, Integer type) {
		List<TAppStaffCredentialsEntity> celist = dbDao.query(TAppStaffCredentialsEntity.class, Cnd.where("staffid","=",staffId).and("type", "=", type), null);
		String jsonStr = "";
		if(!Util.isEmpty(celist)) {
			jsonStr = JsonUtil.toJson(celist);
		}
				
		return jsonStr;
	}


	/**
	 * 获取媒体文件
	 * @param accessToken 接口访问凭证
	 * @param mediaId 媒体文件id
	 * @param savePath 文件在本地服务器上的存储路径
	 * */
	public static String downloadMedia(String accessToken, String mediaId, String savePath) {
		String filePath = null;
		// 拼接请求地址
		String requestUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");

			if (!savePath.endsWith("/")) {
				savePath += "/";
			}
			// 根据内容类型获取扩展名
			String fileExt = getFileexpandedName(conn.getHeaderField("Content-Type"));
			// 将mediaId作为文件名
			filePath = savePath + mediaId + fileExt;
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1)
				fos.write(buf, 0, size);
			fos.close();
			bis.close();

			conn.disconnect();
			String info = String.format("下载媒体文件成功，filePath=" + filePath);
			System.out.println(info);
		} catch (Exception e) {
			filePath = null;
			String error = String.format("下载媒体文件失败：%s", e);
			System.out.println(error);
		}
		return filePath;
	}

	/**
	 * 获取扩展名
	 */
	public static String getExtName(String accessToken, String mediaId) {
		String fileExt = null;
		// 拼接请求地址
		String requestUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);

		HttpURLConnection conn;
		try {
			URL url = new URL(requestUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");

			// 根据内容类型获取扩展名
			fileExt = getFileexpandedName(conn.getHeaderField("Content-Type"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return fileExt;
	}

	/**
	 * 根据内容类型判断文件扩展名
	 *
	 * @param contentType 内容类型
	 * @return
	 */
	public static String getFileexpandedName(String contentType) {
		String fileEndWitsh = "";
		if ("image/jpeg".equals(contentType)) {
			fileEndWitsh = ".jpeg";
		}else if ("application/x-jpg".equals(contentType)) {
			fileEndWitsh = ".jpg";
		}else if ("application/x-png".equals(contentType)) {
			fileEndWitsh = ".png";
		}else if ("image/gif".equals(contentType)) {
			fileEndWitsh = ".gif";
		}else if ("application/x-bmp".equals(contentType)) {
			fileEndWitsh = ".bmp";
		}else if ("image/fax".equals(contentType)) {
			fileEndWitsh = ".fax";
		}else if ("image/x-icon".equals(contentType)) {
			fileEndWitsh = ".ico";
		}else if ("image/pnetvue".equals(contentType)) {
			fileEndWitsh = ".net";
		}

		return fileEndWitsh;
	}

	/** 
	 *  
	 * 根据文件id下载文件 
	 * @param mediaId 媒体id 
	 *  
	 * @throws Exception 
	 */  

	public static InputStream getInputStream(String accessToken, String mediaId) {  
		InputStream is = null;  
		String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="  
				+ accessToken + "&media_id=" + mediaId;  
		try {  
			URL urlGet = new URL(url);  
			HttpURLConnection http = (HttpURLConnection) urlGet  
					.openConnection();  
			http.setRequestMethod("GET"); // 必须是get方式请求  
			http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  
			http.setDoOutput(true);  
			http.setDoInput(true);  
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒  
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒  
			http.connect();  
			// 获取文件转化为byte流  
			is = http.getInputStream();  

		} catch (Exception e) {  
			e.printStackTrace();  
		}  

		return is;

	}  

	//字节数组转换为十六进制字符串
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
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
