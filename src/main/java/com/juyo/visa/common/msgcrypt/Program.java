package com.juyo.visa.common.msgcrypt;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Program {

	public static void main(String[] args) throws Exception {

		//
		// 第三方回复公众平台
		//

		// 需要加密的明文
		String encodingAesKey = "jllZTM3ZWEzZGI1NGQ5NGI3MTc4NDNhNzAzODE5NTYt";
		String token = "ODBiOGIxNDY4NjdlMzc2Yg==";
		String timestamp = "0522060378";
		String nonce = "378224ea";
		String appId = "jhhMThiZjM1ZGQ2Y";
		String replyMsg = "123456HH哈哈哈";
		replyMsg = "{\r\n" + "\"msg\": null,\r\n" + "\"data\": [\r\n" + "{\r\n" + "\"status\": \"申请失败\",\r\n"
				+ "\"passport_num\": \"EC3023073\",\r\n" + "\"error_url\": null,\r\n" + "\"app_id\": \"\",\r\n"
				+ "\"name_en\": \"XING MING \",\r\n" + "\"id\": 2,\r\n" + "\"dat_url\": null,\r\n"
				+ "\"name\": \"姓名\",\r\n" + "\"pdf_url\": null,\r\n" + "\"date_of_birth\": \"2011-05-04\",\r\n"
				+ "\"review_url\": null,\r\n" + "\"code\":\"cd3aefad\"\r\n" + "},\r\n" + "{\r\n"
				+ "\"status\": \"已保存\",\r\n" + "\"passport_num\": \"EC3023073\",\r\n" + "\"error_url\": null,\r\n"
				+ "\"app_id\": null,\r\n" + "\"name_en\": \"ZHANG SAN\",\r\n" + "\"id\": 1,\r\n"
				+ "\"dat_url\": null,\r\n" + "\"name\": \"张三\",\r\n" + "\"pdf_url\": null,\r\n"
				+ "\"date_of_birth\": \"2001-01-27\",\r\n" + "\"review_url\": null,\r\n" + "\"code\":\"io456nlm\"\r\n"
				+ "}\r\n" + "],\r\n" + "\"success\": 1\r\n" + "}";

		WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
		String mingwen = pc.encryptMsg(replyMsg, timestamp, nonce);
		System.out.println("加密后: " + mingwen);

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		StringReader sr = new StringReader(mingwen);
		InputSource is = new InputSource(sr);
		Document document = db.parse(is);

		Element root = document.getDocumentElement();
		NodeList nodelist1 = root.getElementsByTagName("Encrypt");
		NodeList nodelist2 = root.getElementsByTagName("MsgSignature");

		String encrypt = nodelist1.item(0).getTextContent();
		String msgSignature = nodelist2.item(0).getTextContent();

		String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
		String fromXML = String.format(format, encrypt);

		//
		// 公众平台发送消息给第三方，第三方处理
		//

		// 第三方收到公众号平台发送的消息
		String result2 = pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
		System.out.println("解密后明文: " + result2);

		//pc.verifyUrl(null, null, null, null);
	}
}
