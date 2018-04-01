package com.juyo.visa.admin.bigcustomer.module;

import java.io.UnsupportedEncodingException;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.springframework.beans.factory.annotation.Value;

import com.juyo.visa.common.util.TranslateUtil;

@IocBean
@At("/admin/translate")
public class TranslateController {

	private final String ENCODING = "UTF-8";

	private final String BAIDU = "baidu";
	private final String GOOGLE = "google";

	private final String URL_BAIDU = "http://api.fanyi.baidu.com/api/trans/vip/translate?q={0}&from={1}&to={2}&appid={3}&salt={4}&sign={5}";
	private final String URL_GOOGLE = "http://translate.google.cn/translate_a/single?client=gtx&sl={0}&tl={1}&dt=t&q={2}";

	@Value("${translate.baidu.appid}")
	private String baiduAppId;//百度翻译 appid
	@Value("${translate.baidu.appkey}")
	private String baiduAppKey;//百度翻译 appkey

	@At
	@POST
	public Object translate(@Param("q") String q) throws UnsupportedEncodingException {

		String result = null;
		try {
			result = TranslateUtil.translate(q, "en");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return ResultObject.success(result);
		return result;
	}
}
