package com.juyo.visa.common.base.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.nutz.ioc.loader.annotation.IocBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.ByteStreams;
import com.juyo.visa.common.base.BaseUploadService;
import com.qiniu.common.Config;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

@IocBean(name = "qiniuUploadService")
public class QiniuUploadServiceImpl extends BaseUploadService {

	protected Logger logger = LoggerFactory.getLogger(QiniuUploadServiceImpl.class);

	private static final String ACCESS_KEY = "ejKizudKcR-SDr-LhGfKUSJ8ykPuPxEFdum1m87d";

	private static final String SECRET_KEY = "Hz0wETTJHwbpfpLBJ25Agf2it3m70dhwdoRZDR7T";

	private static final String BUCKET = "ticket-file";

	private Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

	// 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
	private static UploadManager uploadManager = new UploadManager();

	// 简单上传，使用默认策略
	private String getUpToken() {
		return auth.uploadToken(BUCKET);
	}

	@Override
	public String uploadImage(InputStream inStream, String file_ext_name, String dest_filename) {

		byte[] data = null;
		try {
			data = ByteStreams.toByteArray(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String upToken = getUpToken();
		String fileName = UUID.randomUUID().toString() + "." + file_ext_name;

		DefaultPutRet ret = upload(data, fileName, upToken);
		return ret.key;
	}

	private DefaultPutRet upload(byte[] data, String key, String upToken) {
		DefaultPutRet ret = null;
		try {
			//华北区
			Config.zone = Zone.zone1();
			Response res = uploadManager.put(data, key, upToken);

			if (res.isOK()) {
				ret = res.jsonToObject(DefaultPutRet.class);
				return ret;
			} else {
				// 上传失败
				logger.info(res.bodyString());
			}
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时简单状态信息
			logger.error(r.toString());
			try {
				// 响应的文本信息
				logger.error(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
		return ret;
	}
}
