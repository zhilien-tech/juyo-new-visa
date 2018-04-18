package com.juyo.visa.admin.bigcustomer.module;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.ArrayUtils;
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
	public Object translate(@Param("strType") String type, @Param("q") String q) throws UnsupportedEncodingException {
		//内蒙古，新疆，广西，宁夏，西藏，屯，州，盟，旗，苏木
		StringBuffer sb = new StringBuffer(q);
		if (q.contains("内蒙古")) {
			sb.insert(sb.toString().indexOf("内蒙古") + 3, "/");
		}
		if (q.contains("新疆")) {
			sb.insert(sb.toString().indexOf("新疆") + 2, "/");
		}
		if (q.contains("广西")) {
			sb.insert(sb.toString().indexOf("广西") + 2, "/");
		}
		if (q.contains("宁夏")) {
			sb.insert(sb.toString().indexOf("宁夏") + 2, "/");
		}
		if (q.contains("西藏")) {
			sb.insert(sb.toString().indexOf("西藏") + 2, "/");
		}
		sb = (StringBuffer) addLine(q, "省", sb);
		sb = (StringBuffer) addLine(q, "屯", sb);
		sb = (StringBuffer) addLine(q, "州", sb);
		sb = (StringBuffer) addLine(q, "盟", sb);
		sb = (StringBuffer) addLine(q, "旗", sb);
		sb = (StringBuffer) addLine(q, "市", sb);
		sb = (StringBuffer) addLine(q, "区", sb);
		sb = (StringBuffer) addLine(q, "县", sb);
		sb = (StringBuffer) addLine(q, "乡", sb);
		sb = (StringBuffer) addLine(q, "村", sb);
		sb = (StringBuffer) addLine(q, "路", sb);
		sb = (StringBuffer) addLine(q, "楼", sb);
		if (q.contains("单元")) {
			sb.insert(sb.toString().indexOf("单元") + 2, "/");
		}
		if (q.contains("苏木")) {
			sb.insert(sb.toString().indexOf("苏木") + 2, "/");
		}
		String result = "";
		String translateStr = sb.toString();
		if (translateStr.contains("/")) {
			String[] strings2 = translateStr.split("/");
			ArrayUtils.reverse(strings2);
			for (int i = 0; i < strings2.length; i++) {
				result += strings2[i] + ' ';
			}
		}
		try {
			System.out.println("最终翻译" + TranslateUtil.translate(result, "en"));
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		/*StringBuffer sb = new StringBuffer();
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
		}*/
		//return ResultObject.success(result);
		return result;
	}

	public Object addLine(String address, String type, StringBuffer sb) {
		if (address.contains(type)) {
			sb.insert(sb.toString().indexOf(type) + 1, "/");
		}
		return sb;
	}

}
