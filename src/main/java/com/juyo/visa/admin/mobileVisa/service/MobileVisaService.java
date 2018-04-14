/**
 * MobileVisaService.java
 * com.juyo.visa.admin.mobileVisa.service
 * Copyright (c) 2018, 北京科技有限公司版权所有.
 */

package com.juyo.visa.admin.mobileVisa.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.Param;
import org.springframework.web.socket.TextMessage;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.mobileVisa.form.MobileVisaBasicInfoForm;
import com.juyo.visa.admin.orderUS.entity.USPassportJsonEntity;
import com.juyo.visa.admin.orderUS.entity.USStaffJsonEntity;
import com.juyo.visa.admin.orderUS.service.OrderUSViewService;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.MarryStatusEnum;
import com.juyo.visa.common.enums.AppPictures.AppPicturesTypeEnum;
import com.juyo.visa.common.enums.visaProcess.TAppStaffCredentialsEnum;
import com.juyo.visa.common.util.ImageDeal;
import com.juyo.visa.common.util.SpringContextUtil;
import com.juyo.visa.entities.TAppStaffBasicinfoEntity;
import com.juyo.visa.entities.TAppStaffCredentialsEntity;
import com.juyo.visa.entities.TAppStaffCredentialsExplainEntity;
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
	private UploadService qiniuUploadService;//文件上传

	@Inject
	private MobileVisaService mobileVisaService;

	private SimpleSendInfoWSHandler simpleSendInfoWSHandler = (SimpleSendInfoWSHandler) SpringContextUtil.getBean(
			"mySimpleSendInfoWSHandler", SimpleSendInfoWSHandler.class);

	//图片上传后连接websocket的地址
	private static final String BASIC_WEBSPCKET_ADDR = "simplesendinfosocket";

	public String uploadImageOnly(File file, Integer staffid, Integer type, int flag, Integer status, Integer sequence,
			HttpServletRequest request, HttpServletResponse response, String sessionid) {
		//将图片进行旋转处理
		ImageDeal imageDeal = new ImageDeal(file.getPath(), request.getContextPath(), UUID.randomUUID().toString(),
				"jpeg");
		File spin = null;
		try {
			spin = imageDeal.spin(-90);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//上传
		Map<String, Object> map = qiniuUploadService.ajaxUploadImage(spin);
		file.delete();
		if (!Util.isEmpty(spin)) {
			spin.delete();
		}
		String url = CommonConstants.IMAGES_SERVER_ADDR + map.get("data");
		if (!Util.eq(status, 0)) {
			saveUpdateImage(url, staffid, type, flag, status, sequence, request, response, sessionid);
		}
		return url;
	}

	/*
	 * 上传单张图片
	 */
	public Object saveUpdateImage(String url, Integer staffid, Integer type, int flag, Integer status,
			Integer sequence, HttpServletRequest request, HttpServletResponse response, String sessionid) {
		USStaffJsonEntity jsonEntity = null;
		USPassportJsonEntity passportJsonEntity = null;
		//String url = uploadImage(file, request);
		//身份证正面上传
		if (TAppStaffCredentialsEnum.IDCARD.intKey() == type) {
			TAppStaffCredentialsEntity credentialEntity = dbDao.fetch(
					TAppStaffCredentialsEntity.class,
					Cnd.where("staffid", "=", staffid).and("type", "=", type)
							.and("status", "=", AppPicturesTypeEnum.FRONT.intKey()));
			if (!Util.isEmpty(credentialEntity)) {
				credentialEntity.setType(type);
				credentialEntity.setUrl(url);
				credentialEntity.setUpdatetime(new Date());
				credentialEntity.setStaffid(staffid);
				credentialEntity.setStatus(AppPicturesTypeEnum.FRONT.intKey());
				dbDao.update(credentialEntity);
				changeStaffStatus(staffid, flag);
				try {
					simpleSendInfoWSHandler.sendMsg(new TextMessage("200"), sessionid);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				credentialEntity = new TAppStaffCredentialsEntity();
				credentialEntity.setType(type);
				credentialEntity.setUrl(url);
				credentialEntity.setUpdatetime(new Date());
				credentialEntity.setStaffid(staffid);
				credentialEntity.setCreatetime(new Date());
				credentialEntity.setStatus(AppPicturesTypeEnum.FRONT.intKey());
				dbDao.insert(credentialEntity);
				changeStaffStatus(staffid, flag);
				try {
					simpleSendInfoWSHandler.sendMsg(new TextMessage("200"), sessionid);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} else if (TAppStaffCredentialsEnum.IDCARDBACK.intKey() == type) {//身份证背面

			TAppStaffCredentialsEntity credentialEntity = dbDao.fetch(
					TAppStaffCredentialsEntity.class,
					Cnd.where("staffid", "=", staffid).and("type", "=", TAppStaffCredentialsEnum.IDCARD.intKey())
							.and("status", "=", AppPicturesTypeEnum.BACK.intKey()));
			if (!Util.isEmpty(credentialEntity)) {
				credentialEntity.setUrl(url);
				credentialEntity.setUpdatetime(new Date());
				credentialEntity.setStatus(AppPicturesTypeEnum.BACK.intKey());
				dbDao.update(credentialEntity);
				changeStaffStatus(staffid, flag);
				try {
					simpleSendInfoWSHandler.sendMsg(new TextMessage("200"), sessionid);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				credentialEntity = new TAppStaffCredentialsEntity();
				credentialEntity.setType(TAppStaffCredentialsEnum.IDCARD.intKey());
				credentialEntity.setUrl(url);
				credentialEntity.setUpdatetime(new Date());
				credentialEntity.setStaffid(staffid);
				credentialEntity.setCreatetime(new Date());
				credentialEntity.setStatus(AppPicturesTypeEnum.BACK.intKey());
				dbDao.insert(credentialEntity);
				changeStaffStatus(staffid, flag);
				try {
					simpleSendInfoWSHandler.sendMsg(new TextMessage("200"), sessionid);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} else if (TAppStaffCredentialsEnum.DRIVE.intKey() == type) {//行驶证
			TAppStaffCredentialsEntity credentialEntity = dbDao.fetch(TAppStaffCredentialsEntity.class,
					Cnd.where("staffid", "=", staffid).and("type", "=", type).and("status", "=", status));
			if (!Util.isEmpty(credentialEntity)) {
				credentialEntity.setUrl(url);
				credentialEntity.setStatus(status);
				credentialEntity.setUpdatetime(new Date());
				int update = dbDao.update(credentialEntity);
				changeStaffStatus(staffid, flag);
			} else {
				credentialEntity = new TAppStaffCredentialsEntity();
				credentialEntity.setCreatetime(new Date());
				credentialEntity.setStaffid(staffid);
				credentialEntity.setUpdatetime(new Date());
				credentialEntity.setType(type);
				credentialEntity.setStatus(status);
				credentialEntity.setUrl(url);
				dbDao.insert(credentialEntity);
				changeStaffStatus(staffid, flag);
			}
			try {
				simpleSendInfoWSHandler.sendMsg(new TextMessage("200"), sessionid);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (TAppStaffCredentialsEnum.NEWUS.intKey() == type) {//美国出签上传
			TAppStaffCredentialsEntity credentialEntity = dbDao.fetch(TAppStaffCredentialsEntity.class,
					Cnd.where("staffid", "=", staffid).and("type", "=", type));
			if (!Util.isEmpty(credentialEntity)) {
				credentialEntity.setUrl(url);
				credentialEntity.setUpdatetime(new Date());
				int update = dbDao.update(credentialEntity);
				changeStaffStatus(staffid, flag);
			} else {
				credentialEntity = new TAppStaffCredentialsEntity();
				credentialEntity.setCreatetime(new Date());
				credentialEntity.setStaffid(staffid);
				credentialEntity.setUpdatetime(new Date());
				credentialEntity.setType(type);
				credentialEntity.setUrl(url);
				dbDao.insert(credentialEntity);
				changeStaffStatus(staffid, flag);
			}
			try {
				simpleSendInfoWSHandler.sendMsg(new TextMessage("200"), sessionid);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if (TAppStaffCredentialsEnum.NEWHUZHAO.intKey() == type) {//新护照
			TAppStaffCredentialsEntity credentialEntity = dbDao.fetch(TAppStaffCredentialsEntity.class,
					Cnd.where("staffid", "=", staffid).and("type", "=", type));
			if (!Util.isEmpty(credentialEntity)) {
				credentialEntity.setType(type);
				credentialEntity.setUrl(url);
				credentialEntity.setUpdatetime(new Date());
				credentialEntity.setStaffid(staffid);
				dbDao.update(credentialEntity);
				changeStaffStatus(staffid, flag);
				try {
					simpleSendInfoWSHandler.sendMsg(new TextMessage("200"), sessionid);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				credentialEntity = new TAppStaffCredentialsEntity();
				credentialEntity.setType(type);
				credentialEntity.setUrl(url);
				credentialEntity.setUpdatetime(new Date());
				credentialEntity.setStaffid(staffid);
				credentialEntity.setCreatetime(new Date());
				dbDao.insert(credentialEntity);
				changeStaffStatus(staffid, flag);
				try {
					simpleSendInfoWSHandler.sendMsg(new TextMessage("200"), sessionid);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} else if (TAppStaffCredentialsEnum.MARRAY.intKey() == type) {//结婚证
			TAppStaffCredentialsEntity credentialEntity = dbDao.fetch(TAppStaffCredentialsEntity.class,
					Cnd.where("staffid", "=", staffid).and("type", "=", type).and("status", "=", status));
			if (!Util.isEmpty(credentialEntity)) {
				credentialEntity.setUrl(url);
				credentialEntity.setStatus(status);
				credentialEntity.setUpdatetime(new Date());
				int update = dbDao.update(credentialEntity);
				changeStaffStatus(staffid, flag);
			} else {
				credentialEntity = new TAppStaffCredentialsEntity();
				credentialEntity.setCreatetime(new Date());
				credentialEntity.setStaffid(staffid);
				credentialEntity.setUpdatetime(new Date());
				credentialEntity.setType(type);
				credentialEntity.setStatus(status);
				credentialEntity.setUrl(url);
				dbDao.insert(credentialEntity);
				changeStaffStatus(staffid, flag);
			}
			try {
				simpleSendInfoWSHandler.sendMsg(new TextMessage("200"), sessionid);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
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
				changeStaffStatus(staffid, flag);
				try {
					simpleSendInfoWSHandler.sendMsg(new TextMessage("200"), sessionid);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (TAppStaffCredentialsEnum.TWOINCHPHOTO.intKey() == type) {
				//获取用户基本信息
				TAppStaffBasicinfoEntity basicinfoEntity = dbDao.fetch(TAppStaffBasicinfoEntity.class,
						Cnd.where("id", "=", staffid));
				basicinfoEntity.setTwoinchphoto(url);
				dbDao.update(basicinfoEntity);
				changeStaffStatus(staffid, flag);
				try {
					simpleSendInfoWSHandler.sendMsg(new TextMessage("200"), sessionid);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//单张图片上传
			TAppStaffCredentialsEntity credentialEntity = dbDao.fetch(TAppStaffCredentialsEntity.class,
					Cnd.where("staffid", "=", staffid).and("type", "=", type));
			if (!Util.isEmpty(credentialEntity)) {
				credentialEntity.setUrl(url);
				credentialEntity.setUpdatetime(new Date());
				int update = dbDao.update(credentialEntity);
				changeStaffStatus(staffid, flag);
			} else {
				credentialEntity = new TAppStaffCredentialsEntity();
				credentialEntity.setCreatetime(new Date());
				credentialEntity.setStaffid(staffid);
				credentialEntity.setUpdatetime(new Date());
				credentialEntity.setType(type);
				credentialEntity.setUrl(url);
				dbDao.insert(credentialEntity);
				changeStaffStatus(staffid, flag);
			}
			try {
				simpleSendInfoWSHandler.sendMsg(new TextMessage("200"), sessionid);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return url;

	}

	public Object changeStaffStatus(Integer staffid, int flag) {
		TAppStaffBasicinfoEntity basic = dbDao.fetch(TAppStaffBasicinfoEntity.class, staffid.longValue());
		if (!Util.isEmpty(basic) && Util.eq(flag, 1)) {
			basic.setIsfirst(IsYesOrNoEnum.YES.intKey());
			dbDao.update(basic);
		}
		return null;
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
		String typeStr = "";
		if (!Util.isEmpty(credentialEntity)) {
			if (Util.eq(type, TAppStaffCredentialsEnum.MARRAY.intKey())) {
				Integer status = credentialEntity.getStatus();
				for (MarryStatusEnum enu : MarryStatusEnum.values()) {
					if (enu.intKey() == type) {
						typeStr = enu.value();
					}
				}
				return typeStr;
			} else {
				return type;
			}
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
			String sessionid, HttpServletRequest request) {
		String url = mobileVisaService.uploadImageOnly(file, staffid, type, 0, 0, sequence, request, null, sessionid);
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
		Map<String, Object> result = Maps.newHashMap();
		List<TAppStaffCredentialsEntity> query = new ArrayList<TAppStaffCredentialsEntity>();
		if (type == TAppStaffCredentialsEnum.HOME.intKey()) {
			query = dbDao.query(TAppStaffCredentialsEntity.class,
					Cnd.where("staffid", "=", staffid).and("type", "=", type).orderBy("sequence", "ASC"), null);
		} else {
			query = dbDao.query(TAppStaffCredentialsEntity.class,
					Cnd.where("staffid", "=", staffid).and("type", "=", type), null);
		}
		if (!Util.isEmpty(query)) {
			if (type != 4) {
				Collections.reverse(query);
				if (Util.eq(type, TAppStaffCredentialsEnum.HOME.intKey())) {
					TAppStaffCredentialsExplainEntity expain = dbDao.fetch(TAppStaffCredentialsExplainEntity.class,
							Cnd.where("staffid", "=", staffid));
					result.put("explain", expain);
					result.put("query", query);
					return result;
				} else {
					return query;
				}
			} else {
				return query;
			}
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

	public Object saveSecondHousecard(int type, int staffid, String propertyholder, String area, String address) {
		TAppStaffCredentialsExplainEntity fetch = dbDao.fetch(TAppStaffCredentialsExplainEntity.class,
				Cnd.where("staffid", "=", staffid).and("type", "=", type));
		if (!Util.isEmpty(fetch)) {
			fetch.setPropertyholder(propertyholder);
			fetch.setArea(area);
			fetch.setAddress(address);
			fetch.setUpdatetime(new Date());
			dbDao.update(fetch);
		} else {
			TAppStaffCredentialsExplainEntity explain = new TAppStaffCredentialsExplainEntity();
			explain.setStaffid(staffid);
			explain.setType(type);
			explain.setAddress(address);
			explain.setArea(area);
			explain.setCreatetime(new Date());
			explain.setPropertyholder(propertyholder);
			explain.setUpdatetime(new Date());
			dbDao.insert(explain);
		}
		return null;
	}

	public Object getSecondHousecard(int type, int staffid) {
		TAppStaffCredentialsExplainEntity fetch = dbDao.fetch(TAppStaffCredentialsExplainEntity.class,
				Cnd.where("staffid", "=", staffid).and("type", "=", type));
		return fetch;
	}

	/**
	 * 获取微信多图上传 图片集合
	 * @param type
	 * @param staffid
	 * @return
	 */
	public Object getWxMorePhotos(@Param("type") int type, @Param("staffid") int staffid) {
		List<TAppStaffCredentialsEntity> photoList = dbDao.query(TAppStaffCredentialsEntity.class,
				Cnd.where("staffid", "=", staffid).and("type", "=", type).orderBy("id", "DESC"), null);
		return photoList;
	}
}
