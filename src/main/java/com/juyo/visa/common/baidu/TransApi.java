/**
 * package-info.java
 * com.juyo.visa.common.baidu
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 * @author   刘旭利
 * @Date	 2018年12月11日
 */

package com.juyo.visa.common.baidu;

import java.util.HashMap;
import java.util.Map;

public class TransApi {
	//private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";
	private static final String TRANS_API_HOST = "https://fanyi-api.baidu.com/api/trans/vip/translate";
	private static final String APP_ID = "20181211000246598";
	private static final String SECURITY_KEY = "8_MjFaIQyqSO5FvZCvm7";

	private String appid;
	private String securityKey;

	public TransApi(String appid, String securityKey) {
		this.appid = appid;
		this.securityKey = securityKey;
	}

	public TransApi() {
		this.appid = APP_ID;
		this.securityKey = SECURITY_KEY;
	}

	public String getTransResult(String query, String from, String to) {
		Map<String, String> params = buildParams(query, from, to);
		return HttpGet.get(TRANS_API_HOST, params);
	}

	private Map<String, String> buildParams(String query, String from, String to) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("q", query);
		params.put("from", from);
		params.put("to", to);

		params.put("appid", appid);

		// 随机数
		String salt = String.valueOf(System.currentTimeMillis());
		params.put("salt", salt);

		// 签名
		String src = appid + query + salt + securityKey; // 加密前的原文
		params.put("sign", MD5.md5(src));

		return params;
	}

}
