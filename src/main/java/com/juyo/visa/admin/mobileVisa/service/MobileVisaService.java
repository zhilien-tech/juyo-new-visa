/**
 * MobileVisaService.java
 * com.juyo.visa.admin.mobileVisa.service
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.mobileVisa.service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.mobileVisa.form.MobileVisaBasicInfoForm;
import com.juyo.visa.admin.orderUS.entity.USStaffJsonEntity;
import com.juyo.visa.admin.orderUS.service.OrderUSViewService;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.visaProcess.TAppStaffCredentialsEnum;
import com.juyo.visa.common.util.SpringContextUtil;
import com.juyo.visa.entities.TAppStaffBasicinfoEntity;
import com.juyo.visa.entities.TAppStaffCredentialsEntity;
import com.juyo.visa.websocket.SimpleSendInfoWSHandler;
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

	@Inject
	private OrderUSViewService orderUSViewService;

	@Inject
	private MobileVisaService mobileVisaService;

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
	public Object updateImage(File file, Integer staffid, Integer type, Integer state, Integer sequence,
			HttpServletRequest request, HttpServletResponse response) {
		USStaffJsonEntity jsonEntity = null;
		//身份证扫描上传
		if (TAppStaffCredentialsEnum.IDCARD.intKey() == type) {
			jsonEntity = (USStaffJsonEntity) orderUSViewService.IDCardRecognition(file, request, response);
		}
		//身份证背面扫描上传
		if (TAppStaffCredentialsEnum.IDCARDBACK.intKey() == type) {
			jsonEntity = (USStaffJsonEntity) orderUSViewService.IDCardRecognitionBack(file, request, response);
		}
		//新护照扫描上传
		if (TAppStaffCredentialsEnum.NEWHUZHAO.intKey() == type) {
			jsonEntity = (USStaffJsonEntity) orderUSViewService.passportRecognitionBack(file, request, response);
		}
		//旧护照扫描上传
		if (TAppStaffCredentialsEnum.OLDHUZHAO.intKey() == type) {
			jsonEntity = (USStaffJsonEntity) orderUSViewService.passportRecognitionBack(file, request, response);
		}
		if (!Util.isEmpty(jsonEntity)) {
			if (jsonEntity.isSuccess()) {
				TAppStaffCredentialsEntity credentialEntity = dbDao.fetch(TAppStaffCredentialsEntity.class,
						Cnd.where("staffid", "=", staffid).and("type", "=", type));
				if (!Util.isEmpty(credentialEntity)) {
					credentialEntity.setType(type);
					credentialEntity.setUrl(jsonEntity.getUrl());
					credentialEntity.setUpdatetime(new Date());
					credentialEntity.setStaffid(staffid);
					dbDao.update(credentialEntity);
					return 1;
				} else {
					credentialEntity = new TAppStaffCredentialsEntity();
					credentialEntity.setType(type);
					credentialEntity.setUrl(jsonEntity.getUrl());
					credentialEntity.setUpdatetime(new Date());
					credentialEntity.setStaffid(staffid);
					credentialEntity.setCreatetime(new Date());
					dbDao.insert(credentialEntity);
					return 1;
				}
			} else {
				return 0;
			}
		} else {
			//多套单张上传(户口本，房产证)
			if (TAppStaffCredentialsEnum.HUKOUBEN.intKey() == type || TAppStaffCredentialsEnum.HOME.intKey() == type) {

			}
			String url = mobileVisaService.uploadImage(file);
			if (TAppStaffCredentialsEnum.TWOINCHPHOTO.intKey() == type) {
				//获取用户基本信息
				TAppStaffBasicinfoEntity basicinfoEntity = dbDao.fetch(TAppStaffBasicinfoEntity.class,
						Cnd.where("id", "=", staffid));
				basicinfoEntity.setTwoinchphoto(url);
				dbDao.update(basicinfoEntity);
			}
			if (TAppStaffCredentialsEnum.OLDHUZHAO.intKey() == type) {
				//			TAppStaffCredentialsEntity credentialEntity = dbDao.fetch(TAppStaffCredentialsEntity.class,
				//					Cnd.where("staffid", "=", staffid).and("type", "=", type));

				TAppStaffCredentialsEntity credentialEntity = new TAppStaffCredentialsEntity();
				credentialEntity.setCreatetime(new Date());
				credentialEntity.setStaffid(staffid);
				credentialEntity.setUpdatetime(new Date());
				credentialEntity.setType(type);
				credentialEntity.setUrl(url);
				dbDao.insert(credentialEntity);
				return 1;
			}
			TAppStaffCredentialsEntity credentialEntity = dbDao.fetch(TAppStaffCredentialsEntity.class,
					Cnd.where("staffid", "=", staffid).and("type", "=", type));
			if (!Util.isEmpty(credentialEntity)) {
				credentialEntity.setUrl(url);
				credentialEntity.setUpdatetime(new Date());
				int update = dbDao.update(credentialEntity);
			} else {
				credentialEntity = new TAppStaffCredentialsEntity();
				credentialEntity.setCreatetime(new Date());
				credentialEntity.setStaffid(staffid);
				credentialEntity.setUpdatetime(new Date());
				credentialEntity.setType(type);
				credentialEntity.setUrl(url);
				dbDao.insert(credentialEntity);
			}
		}

		return 1;

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
		if (!Util.isEmpty(credentialEntity)) {
			result.put("url", credentialEntity.getUrl());
			result.put("type", credentialEntity.getType());
		}
		return result;
	}

	/*
	 * 图片是否存在
	 */
	public Object getImageInfoBytypeAndStaffid(Integer staffid, Integer type) {
		//获取该用户的资料类型
		TAppStaffCredentialsEntity credentialEntity = dbDao.fetch(TAppStaffCredentialsEntity.class,
				Cnd.where("staffid", "=", staffid).and("type", "=", type));
		if (!Util.isEmpty(credentialEntity)) {
			return type;
		} else {
			return 0;
		}

	}

	/*
	 * 获取用户基本信息
	 */
	public Object getBasicInfoByStaffid(Integer staffid) {
		TAppStaffBasicinfoEntity entity = dbDao.fetch(TAppStaffBasicinfoEntity.class, Cnd.where("id", "=", staffid));
		return entity;

	}

	public Object updateMuchImage(File file, Integer staffid, Integer type, Integer sequence) {
		String url = mobileVisaService.uploadImage(file);
		TAppStaffCredentialsEntity credentialEntity = dbDao.fetch(TAppStaffCredentialsEntity.class,
				Cnd.where("staffid", "=", staffid).and("type", "=", type).and("sequnce", "=", sequence));
		if (!Util.isEmpty(credentialEntity)) {
			credentialEntity.setUrl(url);
			credentialEntity.setUpdatetime(new Date());
			int update = dbDao.update(credentialEntity);
		} else {
			credentialEntity = new TAppStaffCredentialsEntity();
			credentialEntity.setCreatetime(new Date());
			credentialEntity.setStaffid(staffid);
			credentialEntity.setUpdatetime(new Date());
			credentialEntity.setType(type);
			credentialEntity.setSequence(sequence);
			credentialEntity.setUrl(url);
			dbDao.insert(credentialEntity);
		}
		return null;

	}

	/*
	 * 获取一类多张图片
	 */
	public Object getMuchPhotoByStaffid(Integer staffid, Integer type) {
		//获取该订单下的申请人
		String sqlStr = sqlManager.get("t_app_staffid_credentials_getmuchphoto");
		Sql applysql = Sqls.create(sqlStr);
		Cnd cnd = Cnd.NEW();
		Cnd.where("staffid", "=", staffid).and("type", "=", type);
		List<Record> applicantList = dbDao.query(applysql, cnd, null);
		return applicantList;

	}
}
