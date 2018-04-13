package com.juyo.visa.common.access;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.nutz.http.Http;
import org.nutz.lang.Lang;

import com.juyo.visa.common.access.sign.MD5;

public class AccessSubmit {

	/**连接超时时间*/
	private static final int default_timeout = 3000;

	/**
	 * 生成签名结果
	 * @param sPara 要签名的数组
	 * @return 签名结果字符串
	 */
	public static String buildRequestMysign(Map<String, String> sPara) {
		String prestr = AccessCore.createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		//    	System.out.println("请求端待签名字符串:" + prestr); 

		String mysign = "";
		if (AccessConfig.sign_type.equals("MD5")) {
			mysign = MD5.sign(prestr, AccessConfig.platform_secret, AccessConfig.INPUT_CHARSET);
		}
		return mysign;
	}

	/**
	 * 带MD5签名的post提交
	 * @param url     提交地址
	 * @param sPara   提交参数
	 * @param timeOut 超时时间
	 * @param signKey 签名使用的key
	 * @return
	 */
	public static String postWithMd5(String url, Map<String, String> sPara, int timeOut, String signKey) {
		//把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串(按照参数名排序)
		String prestr = AccessCore.createLinkString(sPara);
		String sign = "";
		if (AccessConfig.sign_type.equals("MD5")) {
			sign = MD5.sign(prestr, signKey, AccessConfig.INPUT_CHARSET);
		}

		Map<String, Object> params = Lang.obj2map(sPara);
		params.put("sign", sign);
		return Http.post(url, params, timeOut);
	}

	/**
	 * 
	 * 带MD5签名的post提交
	 * 使用默认的超时时间
	 */
	public static String postWithMd5(String url, Map<String, String> sPara, String signKey) {
		return postWithMd5(url, sPara, default_timeout, signKey);
	}

	/**
	* 建立请求，以表单HTML形式构造（默认）
	* @param sParaTemp 请求参数数组
	* @param strMethod 提交方式。两个值可选：post、get
	* @param strButtonName 确认按钮显示文字
	* @return 提交表单HTML文本
	*/
	public static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName) {
		//待请求参数数组
		Map<String, String> sPara = buildRequestPara(sParaTemp);
		List<String> keys = new ArrayList<String>(sPara.keySet());

		StringBuffer sbHtml = new StringBuffer();

		sbHtml.append("<form id=\"sso_submit\" name=\"sso_submit\" action=\"" + sParaTemp.get(AccessConfig.URL_KEY)
				+ "\" method=\"" + strMethod + "\">");

		for (int i = 0; i < keys.size(); i++) {
			String name = (String) keys.get(i);
			if (AccessConfig.URL_KEY.equals(name)) {
				continue;
			}

			String value = (String) sPara.get(name);
			sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
		}

		//submit按钮控件请不要含有name属性
		sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
		sbHtml.append("<script>document.forms['sso_submit'].submit();</script>");

		return sbHtml.toString();
	}

	/**
	 * 生成要请求给统一平台的参数数组
	 * @param sParaTemp 请求前的参数数组
	 * @return 要请求的参数数组
	 */
	private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {
		//除去数组中的空值和签名参数
		Map<String, String> sPara = AccessCore.paraFilter(sParaTemp);

		//生成签名结果
		String mysign = buildRequestMysign(sPara);

		//签名结果与签名方式加入请求提交参数组中
		sPara.put("sign", mysign);
		sPara.put("sign_type", AccessConfig.sign_type);
		return sPara;
	}

}
