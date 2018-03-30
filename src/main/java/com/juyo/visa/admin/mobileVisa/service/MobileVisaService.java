/**
 * MobileVisaService.java
 * com.juyo.visa.admin.mobileVisa.service
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.mobileVisa.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.springframework.web.socket.TextMessage;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.mobileVisa.form.MobileVisaBasicInfoForm;
import com.juyo.visa.admin.mobileVisa.form.UpdateImageForm;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.util.SpringContextUtil;
import com.juyo.visa.entities.TAppStaffCredentialsEntity;
import com.juyo.visa.websocket.SimpleSendInfoWSHandler;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   马云鹏
 * @Date	 2018年3月29日 	 
 */
@IocBean
public class MobileVisaService extends BaseService<TAppStaffCredentialsEntity> {

	@Inject
	private UploadService qiniuupService;

	private SimpleSendInfoWSHandler simpleSendInfoWSHandler = (SimpleSendInfoWSHandler) SpringContextUtil.getBean(
			"mySimpleSendInfoWSHandler", SimpleSendInfoWSHandler.class);

	public String uploadImage(File file) {
		Map<String, Object> map = qiniuupService.ajaxUploadImage(file);
		file.delete();
		map.put("data", CommonConstants.IMAGES_SERVER_ADDR + map.get("data"));
		String string = (String) map.get("data");
		return string;
	}

	/*
	 * 上传单张图片
	 */
	public Object updateImage(UpdateImageForm updateImageForm, HttpSession session) {
		Map<String, Object> result = Maps.newHashMap();
		TAppStaffCredentialsEntity credentialEntity = dbDao.fetch(TAppStaffCredentialsEntity.class,
				Cnd.where("staffid", "=", updateImageForm.getStaffid()).and("type", "=", updateImageForm.getType()));
		if (!Util.isEmpty(credentialEntity)) {
			credentialEntity.setUrl(updateImageForm.getUrl());
			credentialEntity.setUpdatetime(new Date());
			result.put("url", updateImageForm.getUrl());
			int update = dbDao.update(credentialEntity);
		} else {
			credentialEntity = new TAppStaffCredentialsEntity();
			credentialEntity.setCreatetime(new Date());
			credentialEntity.setStaffid(updateImageForm.getStaffid());
			credentialEntity.setUpdatetime(new Date());
			credentialEntity.setType(updateImageForm.getType());
			credentialEntity.setUrl(updateImageForm.getUrl());
			result.put("url", updateImageForm.getUrl());
			dbDao.insert(credentialEntity);
		}
		try {
			//刷新电脑端页面
			simpleSendInfoWSHandler.broadcast(new TextMessage(JsonUtil.toJson(updateImageForm)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}

	/*
	 * 单张图片回显
	 */
	public Object getInfoByType(MobileVisaBasicInfoForm mobileVisaBasicInfoForm) {
		Map<String, Object> result = Maps.newHashMap();
		Integer staffid = mobileVisaBasicInfoForm.getStaffid();
		Integer type = mobileVisaBasicInfoForm.getType();
		TAppStaffCredentialsEntity credentialEntity = dbDao.fetch(TAppStaffCredentialsEntity.class,
				Cnd.where("staffid", "=", staffid).and("type", "=", type));
		result.put("credentialEntity", credentialEntity);
		return result;

	}
}
