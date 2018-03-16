/**
 * VcodeUploadService.java
 * com.juyo.visa.admin.orderUS.service
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.orderUS.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.springframework.web.socket.TextMessage;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.orderUS.form.VcodeForm;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.util.SpringContextUtil;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TVcodeEntity;
import com.juyo.visa.websocket.VcodeWSHandler;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   
 * @Date	 2018年3月15日 	 
 */
public class VcodeUploadService extends BaseService<TOrderJpEntity> {
	@Inject
	private UploadService qiniuUploadService;//文件上传

	private VcodeWSHandler visainfowebsocket = (VcodeWSHandler) SpringContextUtil.getBean("myVcodeHander",
			VcodeWSHandler.class);

	//连接websocket的地址
	private static final String BASIC_WEBSPCKET_ADDR = "vcodewebsocket";

	public Object vcodeUpload(File file) {
		Map<String, Object> map = qiniuUploadService.ajaxUploadImage(file);
		file.delete();
		map.put("data", CommonConstants.IMAGES_SERVER_ADDR + map.get("data"));
		String picurl = (String) map.get("data");
		try {
			//通知页面
			visainfowebsocket.broadcast(new TextMessage(picurl));
		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		List<TVcodeEntity> vcodeList = dbDao.query(TVcodeEntity.class, null, null);
		if (!Util.isEmpty(vcodeList)) {
			for (TVcodeEntity tVcodeEntity : vcodeList) {
				dbDao.delete(tVcodeEntity);
			}
		}
		TVcodeEntity vcodeEntity = new TVcodeEntity();
		vcodeEntity.setVcodeurl(String.valueOf(map.get("data")));
		TVcodeEntity insert = dbDao.insert(vcodeEntity);
		return map;
	}

	public void socketLink() {
		try {
			ServerSocket server = new ServerSocket(8080);
			System.out.println("Server start!");
			Socket socket = server.accept(); //阻塞等待, 直到一个客户端 socket过来  
			System.out.println("There comes a socket!");
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //输入，from 客户端  
			PrintWriter out = new PrintWriter(socket.getOutputStream()); //输出，to 客户端  
			System.out.println(in.readLine()); // 打印 客户 socket 发过来的字符，按行(\n,\r,或\r\n)  
			out.println("Server reponse! ^_^ ");
			out.flush(); // to 客户端，输出  
			socket.close();
			server.close();
			System.out.println("Server end!");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Object writeVcode() {
		Map<String, Object> result = Maps.newHashMap();
		List<TVcodeEntity> usList = dbDao.query(TVcodeEntity.class, null, null);
		if (!Util.isEmpty(usList)) {
			result.put("vcode", usList.get(0));
		}
		return result;
	}

	public Object returnVcode(VcodeForm form) {
		String vcode = form.getVcode();
		TVcodeEntity vcodeEntity = null;
		List<TVcodeEntity> usList = dbDao.query(TVcodeEntity.class, null, null);
		if (!Util.isEmpty(usList)) {
			vcodeEntity = usList.get(0);
			vcodeEntity.setVcode(vcode);
			dbDao.update(vcodeEntity);
		}
		return vcodeEntity;
	}

	public Object getVcode() {
		String result = "";
		List<TVcodeEntity> usList = dbDao.query(TVcodeEntity.class, null, null);
		if (!Util.isEmpty(usList)) {
			result = usList.get(0).getVcode();
		}
		return result;
	}
}
