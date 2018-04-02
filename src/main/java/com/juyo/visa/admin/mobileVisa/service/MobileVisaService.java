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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.springframework.web.socket.TextMessage;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.mobileVisa.form.MobileVisaBasicInfoForm;
import com.juyo.visa.admin.orderUS.entity.USPassportJsonEntity;
import com.juyo.visa.admin.orderUS.entity.USStaffJsonEntity;
import com.juyo.visa.admin.orderUS.service.OrderUSViewService;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.AppPictures.AppPicturesTypeEnum;
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

	//图片上传后连接websocket的地址
	private static final String BASIC_WEBSPCKET_ADDR = "simplesendinfosocket";

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
	public Object updateImage(File file, Integer staffid, Integer type, Integer status, Integer sequence,
			HttpServletRequest request, HttpServletResponse response, String sessionid) {
		USStaffJsonEntity jsonEntity = null;
		USPassportJsonEntity passportJsonEntity = null;
		//身份证扫描上传
		if (TAppStaffCredentialsEnum.IDCARD.intKey() == type) {
			jsonEntity = (USStaffJsonEntity) orderUSViewService.IDCardRecognition(file, request, response);
			if (!Util.isEmpty(jsonEntity)) {
				if (jsonEntity.isSuccess()) {
					TAppStaffCredentialsEntity credentialEntity = dbDao.fetch(
							TAppStaffCredentialsEntity.class,
							Cnd.where("staffid", "=", staffid).and("type", "=", type)
									.and("status", "=", AppPicturesTypeEnum.FRONT.intKey()));
					if (!Util.isEmpty(credentialEntity)) {
						credentialEntity.setType(type);
						credentialEntity.setUrl(jsonEntity.getUrl());
						credentialEntity.setUpdatetime(new Date());
						credentialEntity.setStaffid(staffid);
						credentialEntity.setStatus(AppPicturesTypeEnum.FRONT.intKey());
						dbDao.update(credentialEntity);
						return 1;
					} else {
						credentialEntity = new TAppStaffCredentialsEntity();
						credentialEntity.setType(type);
						credentialEntity.setUrl(jsonEntity.getUrl());
						credentialEntity.setUpdatetime(new Date());
						credentialEntity.setStaffid(staffid);
						credentialEntity.setCreatetime(new Date());
						credentialEntity.setStatus(AppPicturesTypeEnum.FRONT.intKey());
						dbDao.insert(credentialEntity);
						try {
							simpleSendInfoWSHandler.sendMsg(new TextMessage("200"), sessionid);
						} catch (IOException e) {
							e.printStackTrace();
						}
						return 1;
					}
				} else {
					return 0;
				}
			}
		}
		//身份证背面扫描上传
		if (TAppStaffCredentialsEnum.IDCARDBACK.intKey() == type) {
			jsonEntity = (USStaffJsonEntity) orderUSViewService.IDCardRecognitionBack(file, request, response);
			if (!Util.isEmpty(jsonEntity)) {
				if (jsonEntity.isSuccess()) {
					TAppStaffCredentialsEntity credentialEntity = dbDao.fetch(TAppStaffCredentialsEntity.class, Cnd
							.where("staffid", "=", staffid).and("type", "=", TAppStaffCredentialsEnum.IDCARD.intKey())
							.and("status", "=", AppPicturesTypeEnum.BACK.intKey()));
					if (!Util.isEmpty(credentialEntity)) {
						credentialEntity.setUrl(jsonEntity.getUrl());
						credentialEntity.setUpdatetime(new Date());
						credentialEntity.setStatus(AppPicturesTypeEnum.BACK.intKey());
						dbDao.update(credentialEntity);
						try {
							simpleSendInfoWSHandler.sendMsg(new TextMessage("200"), sessionid);
						} catch (IOException e) {
							e.printStackTrace();
						}
						return 1;
					} else {
						credentialEntity = new TAppStaffCredentialsEntity();
						credentialEntity.setType(TAppStaffCredentialsEnum.IDCARD.intKey());
						credentialEntity.setUrl(jsonEntity.getUrl());
						credentialEntity.setUpdatetime(new Date());
						credentialEntity.setStaffid(staffid);
						credentialEntity.setCreatetime(new Date());
						credentialEntity.setStatus(AppPicturesTypeEnum.BACK.intKey());
						dbDao.insert(credentialEntity);
						try {
							simpleSendInfoWSHandler.sendMsg(new TextMessage("200"), sessionid);
						} catch (IOException e) {
							e.printStackTrace();
						}
						return 1;
					}
				} else {
					return 0;
				}
			}
			//新护照扫描上传
			if (TAppStaffCredentialsEnum.NEWHUZHAO.intKey() == type) {
				passportJsonEntity = (USPassportJsonEntity) orderUSViewService.passportRecognitionBack(file, request,
						response);
				if (!Util.isEmpty(jsonEntity)) {
					if (jsonEntity.isSuccess()) {
						TAppStaffCredentialsEntity credentialEntity = dbDao.fetch(TAppStaffCredentialsEntity.class, Cnd
								.where("staffid", "=", staffid).and("type", "=", type));
						if (!Util.isEmpty(credentialEntity)) {
							credentialEntity.setType(type);
							credentialEntity.setUrl(jsonEntity.getUrl());
							credentialEntity.setUpdatetime(new Date());
							credentialEntity.setStaffid(staffid);
							dbDao.update(credentialEntity);
							try {
								simpleSendInfoWSHandler.sendMsg(new TextMessage("200"), sessionid);
							} catch (IOException e) {
								e.printStackTrace();
							}
							return 1;
						} else {
							credentialEntity = new TAppStaffCredentialsEntity();
							credentialEntity.setType(type);
							credentialEntity.setUrl(jsonEntity.getUrl());
							credentialEntity.setUpdatetime(new Date());
							credentialEntity.setStaffid(staffid);
							credentialEntity.setCreatetime(new Date());
							dbDao.insert(credentialEntity);
							try {
								simpleSendInfoWSHandler.sendMsg(new TextMessage("200"), sessionid);
							} catch (IOException e) {
								e.printStackTrace();
							}
							return 1;
						}
					} else {
						return 0;
					}
				}
			}
		} else {

			//结婚证上传
			if (TAppStaffCredentialsEnum.MARRAY.intKey() == type) {
				String url = mobileVisaService.uploadImage(file);
				TAppStaffCredentialsEntity credentialEntity = dbDao.fetch(TAppStaffCredentialsEntity.class,
						Cnd.where("staffid", "=", staffid).and("type", "=", type).and("status", "=", status));
				if (!Util.isEmpty(credentialEntity)) {
					credentialEntity.setUrl(url);
					credentialEntity.setStatus(status);
					credentialEntity.setUpdatetime(new Date());
					int update = dbDao.update(credentialEntity);
				} else {
					credentialEntity = new TAppStaffCredentialsEntity();
					credentialEntity.setCreatetime(new Date());
					credentialEntity.setStaffid(staffid);
					credentialEntity.setUpdatetime(new Date());
					credentialEntity.setType(type);
					credentialEntity.setStatus(status);
					credentialEntity.setUrl(url);
					dbDao.insert(credentialEntity);
				}
				try {
					simpleSendInfoWSHandler.sendMsg(new TextMessage("200"), sessionid);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return 1;
			}
			String url = mobileVisaService.uploadImage(file);
			if (TAppStaffCredentialsEnum.TWOINCHPHOTO.intKey() == type) {
				//获取用户基本信息
				TAppStaffBasicinfoEntity basicinfoEntity = dbDao.fetch(TAppStaffBasicinfoEntity.class,
						Cnd.where("id", "=", staffid));
				basicinfoEntity.setTwoinchphoto(url);
				dbDao.update(basicinfoEntity);
			}
			//老护照添加
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
				try {
					simpleSendInfoWSHandler.sendMsg(new TextMessage("200"), sessionid);
				} catch (IOException e) {
					e.printStackTrace();
				}
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
		try {
			simpleSendInfoWSHandler.sendMsg(new TextMessage("200"), sessionid);
		} catch (IOException e) {
			e.printStackTrace();
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
		return credentialEntity;
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

	//上传多张图片
	public Object updateMuchImage(File file, Integer staffid, Integer mainid, Integer type, Integer sequence,
			String sessionid) {
		String url = mobileVisaService.uploadImage(file);
		TAppStaffCredentialsEntity credentialEntity = dbDao.fetch(
				TAppStaffCredentialsEntity.class,
				Cnd.where("staffid", "=", staffid).and("type", "=", type).and("mainid", "=", mainid)
						.and("sequence", "=", sequence));
		if (!Util.isEmpty(credentialEntity)) {
			credentialEntity.setUrl(url);
			credentialEntity.setMainid(mainid);
			credentialEntity.setSequence(sequence);
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
			credentialEntity.setMainid(mainid);
			credentialEntity.setSequence(sequence);
			dbDao.insert(credentialEntity);
		}
		try {
			simpleSendInfoWSHandler.sendMsg(new TextMessage("200"), sessionid);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return url;

	}

	/*
	 * 获取多张图片
	 */
	public Object getMuchPhotoByStaffid(Integer staffid, Integer type) {
		List<TAppStaffCredentialsEntity> query = dbDao.query(TAppStaffCredentialsEntity.class,
				Cnd.where("staffid", "=", staffid).and("type", "=", type), null);
		if (!Util.isEmpty(query)) {
			return query;
		} else {
			return 0;
		}
	}

	/*
	 * 获取身份证图片
	 */
	public Object getIDcardphoto(Integer staffid, Integer type, Integer status) {
		TAppStaffCredentialsEntity cardEntity = dbDao.fetch(TAppStaffCredentialsEntity.class,
				Cnd.where("staffid", "=", staffid).and("type", "=", type).and("status", "=", status));
		return cardEntity;

	}
}
