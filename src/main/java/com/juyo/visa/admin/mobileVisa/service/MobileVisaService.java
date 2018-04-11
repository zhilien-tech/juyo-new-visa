/**
 * MobileVisaService.java
 * com.juyo.visa.admin.mobileVisa.service
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.mobileVisa.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

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
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.AppPictures.AppPicturesTypeEnum;
import com.juyo.visa.common.enums.visaProcess.TAppStaffCredentialsEnum;
import com.juyo.visa.common.util.PinyinTool;
import com.juyo.visa.common.util.PinyinTool.Type;
import com.juyo.visa.common.util.SpringContextUtil;
import com.juyo.visa.common.util.TranslateUtil;
import com.juyo.visa.entities.TAppStaffBasicinfoEntity;
import com.juyo.visa.entities.TAppStaffCredentialsEntity;
import com.juyo.visa.entities.TAppStaffPassportEntity;
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

	//翻译英文
	public String translate(String str) {
		String result = null;
		try {
			result = TranslateUtil.translate(str, "en");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	//翻译姓名
	public String translatename(String str) {
		PinyinTool tool = new PinyinTool();
		String result = null;
		try {
			result = tool.toPinYin(str, "", Type.UPPERCASE);
		} catch (BadHanyuPinyinOutputFormatCombination e1) {

			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
		return result;

	}

	/*
	 * 身份证正面资料保存
	 */
	public void saveBasicinfoFront(USStaffJsonEntity jsonEntity, TAppStaffBasicinfoEntity staffInfo) {
		staffInfo.setAddress(jsonEntity.getAddress());
		staffInfo.setAddressen(translate(jsonEntity.getAddress()));
		staffInfo.setCardfront(jsonEntity.getUrl());
		staffInfo.setCardId(jsonEntity.getNum());
		staffInfo.setCardIden(jsonEntity.getNum());
		staffInfo.setCardcity(jsonEntity.getCity());
		staffInfo.setCardcityen(translate(jsonEntity.getCity()));
		staffInfo.setCardprovince(jsonEntity.getProvince());
		staffInfo.setCardprovinceen(translate(jsonEntity.getProvince()));
		staffInfo.setSex(jsonEntity.getSex());
		staffInfo.setNation(jsonEntity.getNationality());
		staffInfo.setNationen(translate(jsonEntity.getNationality()));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			String d = jsonEntity.getBirth() + " 00:00:00";
			date = formatter.parse(d);
		} catch (ParseException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		staffInfo.setBirthday(date);
		dbDao.update(staffInfo);
	}

	/*
	 * 身份证背面信息保存
	 */
	public void saveBasicinfoBack(USStaffJsonEntity jsonEntity, TAppStaffBasicinfoEntity staffInfo) {
		staffInfo.setIssueorganization(jsonEntity.getIssue());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date datestar = new Date();
		Date dateend = new Date();
		try {
			String dstar = jsonEntity.getStarttime() + " 00:00:00";
			datestar = formatter.parse(dstar);
			String dend = jsonEntity.getEndtime() + " 00:00:00";
			dateend = formatter.parse(dend);
		} catch (ParseException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		staffInfo.setValidstartdate(datestar);
		staffInfo.setValidenddate(dateend);
		staffInfo.setCardback(jsonEntity.getUrl());
		dbDao.update(staffInfo, null);
	}

	/*
	 * 护照信息保存
	 */
	public void savePassport(USPassportJsonEntity passportJsonEntity, TAppStaffPassportEntity passportEntity,
			TAppStaffBasicinfoEntity staffInfo) {
		//用户基本信息修改
		staffInfo.setFirstname(passportJsonEntity.getXingCn());//姓
		staffInfo.setFirstnameen(translatename(passportJsonEntity.getXingCn()));//姓英
		staffInfo.setLastname(passportJsonEntity.getMingCn());//名
		staffInfo.setLastnameen(translatename(passportJsonEntity.getMingCn()));//名英
		dbDao.update(staffInfo);
		//护照信息修改
		passportEntity.setFirstname(passportJsonEntity.getXingCn());//姓
		passportEntity.setFirstnameen(translatename(passportJsonEntity.getXingCn()));//姓英
		passportEntity.setLastname(passportJsonEntity.getMingCn());//名
		passportEntity.setLastnameen(translatename(passportJsonEntity.getMingCn()));//名英	
		passportEntity.setPassporturl(passportJsonEntity.getUrl());//护照照片地址
		passportEntity.setOCRline1(passportJsonEntity.getOCRline1());
		passportEntity.setOCRline2(passportJsonEntity.getOCRline2());
		passportEntity.setSex(passportJsonEntity.getSex());
		passportEntity.setSexen(translate(passportJsonEntity.getSexEn()));
		passportEntity.setBirthaddress(passportJsonEntity.getBirthCountry());//出生地
		passportEntity.setBirthaddressen(translate(passportJsonEntity.getBirthCountry()));
		passportEntity.setType(passportJsonEntity.getType());
		//出生日期
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date birthday = new Date();
		Date issueDate = new Date();//签发日期
		Date expiryDay = new Date();//有效期
		try {
			String d1 = passportJsonEntity.getBirth() + " 00:00:00";
			String d2 = passportJsonEntity.getIssueDate() + " 00:00:00";
			String d3 = passportJsonEntity.getExpiryDay() + " 00:00:00";
			birthday = formatter.parse(d1);
			issueDate = formatter.parse(d2);
			expiryDay = formatter.parse(d3);
		} catch (ParseException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		passportEntity.setBirthday(birthday);//设置出生日期
		passportEntity.setIssueddate(issueDate);//设置签发日期
		passportEntity.setValidenddate(expiryDay);//设置有效期至
		passportEntity.setIssuedplace(passportJsonEntity.getVisaCountry());//设置签发地点
		passportEntity.setIssuedplaceen(translate(passportJsonEntity.getVisaCountry()));
		passportEntity.setPassport(passportJsonEntity.getNum());
		dbDao.update(passportEntity, null);

	}

	/*
	 * 上传单张图片
	 */
	public Object updateImage(File file, Integer staffid, Integer type, int flag, Integer status, Integer sequence,
			HttpServletRequest request, HttpServletResponse response, String sessionid) {
		USStaffJsonEntity jsonEntity = null;
		USPassportJsonEntity passportJsonEntity = null;
		//身份证扫描上传
		if (TAppStaffCredentialsEnum.IDCARD.intKey() == type) {
			jsonEntity = (USStaffJsonEntity) orderUSViewService.IDCardRecognition(file, request, response);
			if (!Util.isEmpty(jsonEntity)) {
				if (jsonEntity.isSuccess()) {
					//获取用户的基本信息
					TAppStaffBasicinfoEntity staffInfo = dbDao.fetch(TAppStaffBasicinfoEntity.class,
							Cnd.where("id", "=", staffid));
					//保存身份证正面信息
					saveBasicinfoFront(jsonEntity, staffInfo);
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
						changeStaffStatus(staffid, flag);
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
						changeStaffStatus(staffid, flag);
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
		} else if (TAppStaffCredentialsEnum.IDCARDBACK.intKey() == type) {//身份证背面
			jsonEntity = (USStaffJsonEntity) orderUSViewService.IDCardRecognitionBack(file, request, response);
			if (!Util.isEmpty(jsonEntity)) {
				if (jsonEntity.isSuccess()) {
					//获取用户的基本信息
					TAppStaffBasicinfoEntity staffInfo = dbDao.fetch(TAppStaffBasicinfoEntity.class,
							Cnd.where("id", "=", staffid));
					//保存身份证背面信息
					saveBasicinfoBack(jsonEntity, staffInfo);

					TAppStaffCredentialsEntity credentialEntity = dbDao.fetch(TAppStaffCredentialsEntity.class, Cnd
							.where("staffid", "=", staffid).and("type", "=", TAppStaffCredentialsEnum.IDCARD.intKey())
							.and("status", "=", AppPicturesTypeEnum.BACK.intKey()));
					if (!Util.isEmpty(credentialEntity)) {
						credentialEntity.setUrl(jsonEntity.getUrl());
						credentialEntity.setUpdatetime(new Date());
						credentialEntity.setStatus(AppPicturesTypeEnum.BACK.intKey());
						dbDao.update(credentialEntity);
						changeStaffStatus(staffid, flag);
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
						changeStaffStatus(staffid, flag);
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
		} else if (TAppStaffCredentialsEnum.NEWHUZHAO.intKey() == type) {//新护照
			passportJsonEntity = (USPassportJsonEntity) orderUSViewService.passportRecognitionBack(file, request,
					response);
			if (!Util.isEmpty(passportJsonEntity)) {
				if (passportJsonEntity.isSuccess()) {
					//获取用户的基本信息
					TAppStaffBasicinfoEntity staffInfo = dbDao.fetch(TAppStaffBasicinfoEntity.class,
							Cnd.where("id", "=", staffid));
					//获取用户的护照信息
					TAppStaffPassportEntity passportEntity = dbDao.fetch(TAppStaffPassportEntity.class,
							Cnd.where("staffid", "=", staffid));
					savePassport(passportJsonEntity, passportEntity, staffInfo);
					TAppStaffCredentialsEntity credentialEntity = dbDao.fetch(TAppStaffCredentialsEntity.class, Cnd
							.where("staffid", "=", staffid).and("type", "=", type));
					if (!Util.isEmpty(credentialEntity)) {
						credentialEntity.setType(type);
						credentialEntity.setUrl(passportJsonEntity.getUrl());
						credentialEntity.setUpdatetime(new Date());
						credentialEntity.setStaffid(staffid);
						dbDao.update(credentialEntity);
						changeStaffStatus(staffid, flag);
						try {
							simpleSendInfoWSHandler.sendMsg(new TextMessage("200"), sessionid);
						} catch (IOException e) {
							e.printStackTrace();
						}
						return 1;
					} else {
						credentialEntity = new TAppStaffCredentialsEntity();
						credentialEntity.setType(type);
						credentialEntity.setUrl(passportJsonEntity.getUrl());
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
						return 1;
					}
				} else {
					return 0;
				}
			}
		} else if (TAppStaffCredentialsEnum.MARRAY.intKey() == type) {//结婚证
			String url = mobileVisaService.uploadImage(file);
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
			return 1;
		} else {
			String url = mobileVisaService.uploadImage(file);
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
				return 1;
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
		return 1;

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
