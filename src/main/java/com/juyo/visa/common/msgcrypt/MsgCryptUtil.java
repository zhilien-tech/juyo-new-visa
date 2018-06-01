package com.juyo.visa.common.msgcrypt;

import java.security.MessageDigest;
import java.util.Arrays;

import com.uxuexi.core.common.util.DateUtil;

public class MsgCryptUtil {

	/**
	 * 
	 * @param encodingAesKey 
	 * @param token
	 * @param appId
	 * @param nonce 随机串
	 * @param replyMsg 需要加密的字符串
	 * @return 加密的明文
	 * @throws Exception
	 */
	public static String enCryptMsg(String encodingAesKey, String token, String appId, String nonce, String replyMsg)
			throws Exception {
		String timestamp = DateUtil.nowDateTimeString();
		WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
		String mingwen = pc.encryptMsg(replyMsg, timestamp, nonce);
		System.out.println("加密后: " + mingwen);
		return mingwen;
	}

	/**
	 * 
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param encodingAesKey
	 * @param token
	 * @param appId
	 * @param nonce
	 * @param cryptMsg
	 * @return
	 * @throws Exception TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public static String deCryptMsg(String encodingAesKey, String token, String appId, String nonce, String cryptMsg)
			throws Exception {
		WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
		String decryptMsg = pc.decrypt(cryptMsg);
		System.out.println("解密后明文: " + decryptMsg);
		return decryptMsg;
	}

	/**
	 * 生成加密字符串的签名
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param token
	 * @param timestamp
	 * @param nonce
	 * @param encrypt
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public static String getSignature(String token, String timestamp, String nonce, String encrypt) {
		try {
			String[] array = new String[] { token, timestamp, nonce, encrypt };
			Arrays.sort(array);
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < 4; ++i) {
				sb.append(array[i]);
			}

			String str = sb.toString();
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes());
			byte[] digest = md.digest();
			StringBuffer hexstr = new StringBuffer();
			String shaHex = "";

			for (int i = 0; i < digest.length; ++i) {
				shaHex = Integer.toHexString(digest[i] & 255);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}

				hexstr.append(shaHex);
			}

			return hexstr.toString();
		} catch (Exception var13) {
			System.out.println(var13);
		}
		return null;
	}
}
