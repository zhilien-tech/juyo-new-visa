package com.juyo.visa.common.access;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.juyo.visa.common.access.sign.MD5;

public class AccessCore {

	/** 
	* 除去数组中的空值和签名参数
	* @param sArray 签名参数组
	* @return 去掉空值与签名参数后的新签名参数组
	*/
	public static Map<String, String> paraFilter(Map<String, String> sArray) {

		Map<String, String> result = new HashMap<String, String>();

		if (sArray == null || sArray.size() <= 0) {
			return result;
		}

		for (String key : sArray.keySet()) {
			String value = sArray.get(key);
			if (value == null || value.equals("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")
					|| key.equals(AccessConfig.URL_KEY)) {
				continue;
			}
			result.put(key, value);
		}
		return result;
	}

	/** 
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * @param params 需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		/*
		 * 排序：将集合转为对象数组，然后调用Arrays.sort()进行排序；
		 * 之后利用ListIterator将对象数组中的值逐个替换到原集合中(set方法)
		 */
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}

	/**
	 * 验证消息是否是统一平台发出的合法消息
	 * @param params 通知返回来的参数数组
	 * @param signKey 签名 秘钥
	 * @return 验证结果
	 */
	public static boolean verify(Map<String, String> params, String signKey) {
		//////1,得到请求端发送的签名信息//////
		String sign = (String) params.get("sign");

		//////2,重新生成签名与得到的签名进行对比//////
		//除去数组中的空值和签名参数
		Map<String, String> sPara = paraFilter(params);
		//生成签名结果
		String mysign = buildVerifysign(sPara, signKey);
		System.out.println("服务器端签名值:" + mysign);

		return sign.equals(mysign);
	}

	/**
	 * 生成签名结果
	 * @param sPara   要签名的数组
	 * @param signKey 签名 秘钥
	 * @return 签名结果字符串
	 */
	public static String buildVerifysign(Map<String, String> sPara, String signKey) {
		String prestr = createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		System.out.println("待签名字符串:" + prestr);
		String mysign = "";
		if (AccessConfig.sign_type.equals("MD5")) {
			mysign = MD5.sign(prestr, signKey, AccessConfig.INPUT_CHARSET);
		}
		return mysign;
	}
}
