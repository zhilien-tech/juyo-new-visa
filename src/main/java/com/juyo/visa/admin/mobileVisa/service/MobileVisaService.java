/**
 * MobileVisaService.java
 * com.juyo.visa.admin.mobileVisa.service
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.mobileVisa.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.springframework.web.socket.TextMessage;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.mobileVisa.form.MobileVisaBasicInfoForm;
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
	public Object updateImage(String url, Integer staffid, Integer type) {
		Map<String, Object> result = Maps.newHashMap();
		TAppStaffCredentialsEntity credentialEntity = dbDao.fetch(TAppStaffCredentialsEntity.class,
				Cnd.where("staffid", "=", staffid).and("type", "=", type));
		if (!Util.isEmpty(credentialEntity)) {
			credentialEntity.setUrl(url);
			credentialEntity.setUpdatetime(new Date());
			result.put("url", url);
			int update = dbDao.update(credentialEntity);
		} else {
			credentialEntity = new TAppStaffCredentialsEntity();
			credentialEntity.setCreatetime(new Date());
			credentialEntity.setStaffid(staffid);
			credentialEntity.setUpdatetime(new Date());
			credentialEntity.setType(type);
			credentialEntity.setUrl(url);
			result.put("url", url);
			dbDao.insert(credentialEntity);
		}
		try {
			//刷新电脑端页面
			simpleSendInfoWSHandler.broadcast(new TextMessage(JsonUtil.toJson("")));
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

	/*
	 * 图片是否存在
	 */
	public Object getImageInfoBytypeAndStaffid(Integer staffid) {
		//获取该用户的资料类型
		String sqlStr = sqlManager.get("t_app_paperwork_US_info");
		Sql applysql = Sqls.create(sqlStr);
		Cnd cnd = Cnd.NEW();
		cnd.and("staffid", "=", staffid);
		List<Record> infoList = dbDao.query(applysql, cnd, null);
		for (Record appRecord : infoList) {
			if (!Util.isEmpty(appRecord.getInt("type"))) {
				appRecord.set("type", "已上传");
			} else {
				appRecord.set("type", "待上传");
			}
		}
		return infoList;

	}
}
