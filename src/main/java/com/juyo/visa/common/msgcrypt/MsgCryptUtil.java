package com.juyo.visa.common.msgcrypt;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

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
	 * @param encodingAesKey
	 * @param token
	 * @param appId
	 * @param nonce 随机串
	 * @param cryptMsg 需要解密的字符串
	 * @return 解密的明文
	 * @throws Exception
	 */
	public static String deCryptMsg(String encodingAesKey, String token, String appId, String nonce, String cryptMsg)
			throws Exception {
		String timestamp = DateUtil.nowDateTimeString();
		WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		StringReader sr = new StringReader(cryptMsg);
		InputSource is = new InputSource(sr);
		Document document = db.parse(is);

		Element root = document.getDocumentElement();
		NodeList nodelist1 = root.getElementsByTagName("Encrypt");
		NodeList nodelist2 = root.getElementsByTagName("MsgSignature");

		String encrypt = nodelist1.item(0).getTextContent();
		String msgSignature = nodelist2.item(0).getTextContent();

		String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
		String fromXML = String.format(format, encrypt);

		// 第三方收到公众号平台发送的消息
		String decryptMsg = pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
		System.out.println("解密后明文: " + decryptMsg);
		return decryptMsg;
	}
}
