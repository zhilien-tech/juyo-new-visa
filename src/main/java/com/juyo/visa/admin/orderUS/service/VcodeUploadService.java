/**
 * VcodeUploadService.java
 * com.juyo.visa.admin.orderUS.service
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.orderUS.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.springframework.web.socket.TextMessage;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.orderUS.form.VcodeForm;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.util.SpringContextUtil;
import com.juyo.visa.entities.TAppStaffVcodeEntity;
import com.juyo.visa.entities.TOrderJpEntity;
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
		List<TAppStaffVcodeEntity> vcodeList = dbDao.query(TAppStaffVcodeEntity.class, null, null);
		if (!Util.isEmpty(vcodeList)) {
			for (TAppStaffVcodeEntity tVcodeEntity : vcodeList) {
				dbDao.delete(tVcodeEntity);
			}
		}
		TAppStaffVcodeEntity vcodeEntity = new TAppStaffVcodeEntity();
		vcodeEntity.setVcodeurl(String.valueOf(map.get("data")));
		TAppStaffVcodeEntity insert = dbDao.insert(vcodeEntity);
		return map;
	}

	public Object writeVcode() {
		Map<String, Object> result = Maps.newHashMap();
		List<TAppStaffVcodeEntity> usList = dbDao.query(TAppStaffVcodeEntity.class, null, null);
		if (!Util.isEmpty(usList)) {
			result.put("vcode", usList.get(0));
		}
		return result;
	}

	public Object returnVcode(VcodeForm form) {
		String vcode = form.getVcode();
		TAppStaffVcodeEntity vcodeEntity = null;
		List<TAppStaffVcodeEntity> usList = dbDao.query(TAppStaffVcodeEntity.class, null, null);
		if (!Util.isEmpty(usList)) {
			vcodeEntity = usList.get(0);
			vcodeEntity.setVcode(vcode);
			dbDao.update(vcodeEntity);
		}
		return vcodeEntity;
	}

	public Object getVcode() {
		String result = "";
		List<TAppStaffVcodeEntity> usList = dbDao.query(TAppStaffVcodeEntity.class, null, null);
		if (!Util.isEmpty(usList)) {
			result = usList.get(0).getVcode();
		}
		return result;
	}
}
