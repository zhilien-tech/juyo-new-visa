package com.juyo.visa.admin.bigcustomer.module;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.ArrayUtils;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.springframework.beans.factory.annotation.Value;

import com.juyo.visa.common.util.TranslateUtil;
import com.uxuexi.core.common.util.Util;

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
	public Object translate(@Param("strType") String type, @Param("q") String q) throws UnsupportedEncodingException {
		//内蒙古，新疆，广西，宁夏，西藏，屯，州，盟，旗，苏木
		StringBuffer sb = new StringBuffer();
		String result = "";
		try {
			if (Util.eq(type, "addressen") || Util.eq(type, "detailedAddressen")) {
				String translateStr = TranslateUtil.translate(q, "en");
				if (translateStr.contains("/")) {
					String[] strings = translateStr.split("/");
					ArrayUtils.reverse(strings);
					for (int i = 0; i < strings.length; i++) {
						result += strings[i] + ' ';
					}
				}
			} else {
				result = TranslateUtil.translate(q, "en");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return ResultObject.success(result);
		return result;
	}

}
