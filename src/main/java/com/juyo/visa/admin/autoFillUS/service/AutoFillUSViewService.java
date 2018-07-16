/**
 * AutoFillUSViewService.java
 * com.juyo.visa.admin.autoFillUS.service
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.autoFillUS.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.springframework.web.socket.TextMessage;

import com.google.common.collect.Maps;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.MonthsEnum;
import com.juyo.visa.common.util.ResultObject;
import com.juyo.visa.common.util.SpringContextUtil;
import com.juyo.visa.entities.TAppStaffBasicinfoEntity;
import com.juyo.visa.entities.TAppStaffBeforeeducationEntity;
import com.juyo.visa.entities.TAppStaffBeforeworkEntity;
import com.juyo.visa.entities.TAppStaffCompanioninfoEntity;
import com.juyo.visa.entities.TAppStaffConscientiousEntity;
import com.juyo.visa.entities.TAppStaffContactpointEntity;
import com.juyo.visa.entities.TAppStaffDriverinfoEntity;
import com.juyo.visa.entities.TAppStaffFamilyinfoEntity;
import com.juyo.visa.entities.TAppStaffGocountryEntity;
import com.juyo.visa.entities.TAppStaffGousinfoEntity;
import com.juyo.visa.entities.TAppStaffImmediaterelativesEntity;
import com.juyo.visa.entities.TAppStaffLanguageEntity;
import com.juyo.visa.entities.TAppStaffOrderUsEntity;
import com.juyo.visa.entities.TAppStaffOrganizationEntity;
import com.juyo.visa.entities.TAppStaffPassportEntity;
import com.juyo.visa.entities.TAppStaffPrevioustripinfoEntity;
import com.juyo.visa.entities.TAppStaffTravelcompanionEntity;
import com.juyo.visa.entities.TAppStaffVcodeEntity;
import com.juyo.visa.entities.TAppStaffWorkEducationTrainingEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TOrderUsEntity;
import com.juyo.visa.entities.TOrderUsTravelinfoEntity;
import com.juyo.visa.websocket.VcodeWSHandler;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   闫腾
 * @Date	 2018年3月26日 	 
 */
@IocBean
public class AutoFillUSViewService extends BaseService<TOrderJpEntity> {
	@Inject
	private UploadService qiniuUploadService;//文件上传

	private VcodeWSHandler visainfowebsocket = (VcodeWSHandler) SpringContextUtil.getBean("myVcodeHander",
			VcodeWSHandler.class);

	//连接websocket的地址
	private static final String BASIC_WEBSPCKET_ADDR = "vcodewebsocket";

	/**
	 * 查看是否有订单可以执行，如果有则返回数据
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object fetchUSOrder() {
		//查询是否有需要自动填表的订单(因为目前没有订单状态，所以暂时先搁置)

		//订单信息
		List<TOrderUsEntity> orderList = dbDao.query(TOrderUsEntity.class, Cnd.where("status", "=", 3), null);
		if (!Util.isEmpty(orderList) && orderList.size() > 0) {
			//用来存放信息
			Map<String, Object> map = Maps.newTreeMap();
			//获取第一条信息
			TOrderUsEntity orderUS = orderList.get(0);

			//订单id
			map.put("orderid", orderUS.getId());
			//领区
			map.put("cityid", orderUS.getCityid());

			TAppStaffOrderUsEntity appStaffOrderUsEntity = dbDao.fetch(TAppStaffOrderUsEntity.class,
					Cnd.where("orderid", "=", orderUS.getId()));
			//基本信息
			TAppStaffBasicinfoEntity staffBase = dbDao.fetch(TAppStaffBasicinfoEntity.class, appStaffOrderUsEntity
					.getStaffid().longValue());
			//护照信息
			TAppStaffPassportEntity staffPassport = dbDao.fetch(TAppStaffPassportEntity.class,
					Cnd.where("staffid", "=", staffBase.getId()));
			String fullname = "";
			if (!Util.isEmpty(staffBase.getFirstname())) {
				fullname += staffBase.getFirstname();
			}
			if (!Util.isEmpty(staffBase.getLastname())) {
				fullname += staffBase.getLastname();
			}

			//个人信息第一页
			//姓
			map.put("firstname", staffBase.getFirstnameen());
			//名
			map.put("lastname", staffBase.getLastnameen());
			//全名
			map.put("fullname", fullname);
			//全名是否适用
			if (!Util.isEmpty(staffBase.getFirstname()) && !Util.isEmpty(staffBase.getLastname())) {
				map.put("fullnameapply", true);
			} else {
				map.put("fullnameapply", false);
			}
			//是否有曾用名
			if (Util.eq(staffBase.getHasothername(), 1)) {
				map.put("hasothername", true);
				map.put("otherfirstname", staffBase.getOtherfirstnameen());
				map.put("otherlastname", staffBase.getOtherlastnameen());
			} else {
				map.put("hasothername", false);
			}
			//是否有电子代码
			if (Util.eq(staffBase.getHastelecode(), 1)) {
				map.put("hastelecode", true);
				map.put("telecodefirstname", staffBase.getTelecodefirstname());
				map.put("telecodelastname", staffBase.getTelecodelastname());
			} else {
				map.put("hastelecode", false);
			}
			//性别
			if (Util.eq(staffPassport.getSex(), "男")) {
				map.put("sex", true);
			} else {
				map.put("sex", false);
			}
			//婚姻状况(需要key还是value?)
			map.put("marrystatus", staffBase.getMarrystatus());
			//如果是其他多一个说明
			map.put("marryexplain", staffBase.getMarryexplainen());
			//出生地点和日期
			//年月日
			Date birthday = staffPassport.getBirthday();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String str = format.format(birthday);
			if (!Util.isEmpty(str)) {
				map.put("birthyear", str.subSequence(0, 4));
				map.put("birthday", str.subSequence(8, 10));
				String monthStr = str.substring(5, 7);
				if (monthStr.contains("0")) {
					monthStr = monthStr.substring(1);
				}
				for (MonthsEnum monthsEnum : MonthsEnum.values()) {
					if (Util.eq(monthStr, monthsEnum.intKey())) {
						map.put("birthmonth", monthsEnum.value());
					}
				}
			}
			//市
			map.put("city", staffBase.getCityen());
			//省
			map.put("province", staffBase.getProvinceen());
			//省是否适用
			map.put("isprovinceapply", false);
			//国家
			map.put("country", "CHIN");

			//个人信息第二页
			//国籍
			map.put("region", "CHIN");
			//是否有其他国籍
			if (Util.eq(staffBase.getHasothernationality(), 1)) {
				map.put("hasothernation", true);
				//其他国籍(?)
				map.put("othernation", staffBase.getNationality());
				//是否有该国家护照
				if (Util.eq(staffBase.getHasotherpassport(), 1)) {
					map.put("hasotherpassport", true);
					map.put("otherpassport", "其他国家护照");//原型没有该字段??
				} else {
					map.put("hasotherpassport", false);
				}
			} else {
				map.put("hasothernation", false);
			}
			//是否是上述国家以外的永久居民
			if (Util.eq(staffBase.getIsothercountrypermanentresident(), 1)) {
				map.put("isothercountrypermanentresident", true);
				map.put("othercountrypermanentresident", "其他国家永久居民");//原型没有该字段??
			} else {
				map.put("isothercountrypermanentresident", false);
			}
			//国家注册号码
			map.put("nationalidentificationnumber", staffBase.getNationalidentificationnumberen());
			//国家注册码是否适用
			if (Util.eq(staffBase.getIsidentificationnumberapply(), 1)) {
				map.put("isidentificationnumberapply", true);
			} else {
				map.put("isidentificationnumberapply", false);
			}
			//美国社会安全码
			String socialsecuritynumber = staffBase.getSocialsecuritynumber();
			if (!Util.isEmpty(socialsecuritynumber) && socialsecuritynumber.length() == 9) {
				map.put("socialsecuritynumber1", socialsecuritynumber.substring(0, 3));
				map.put("socialsecuritynumber2", socialsecuritynumber.substring(3, 5));
				map.put("socialsecuritynumber3", socialsecuritynumber.substring(5, 9));
			}
			//美国纳税人证件号
			map.put("taxpayernumber", staffBase.getTaxpayernumber());
			//美国纳税人证件号是否适用
			if (Util.eq(staffBase.getIstaxpayernumberapply(), 1)) {
				map.put("istaxpayernumberapply", true);
			} else {
				map.put("istaxpayernumberapply", false);
			}

			//地址和电话信息
			//街道地址(Line 1)
			map.put("detailedaddress", staffBase.getDetailedaddressen());
			//市、省、国家同个人信息第一页
			//邮政编码(基本信息里没有??)
			//邮政编码是否适用
			map.put("iszipcodeapply", true);
			//邮寄地址和家庭住址是否一致
			map.put("issameaddress", true);
			//电话号码
			map.put("telephone", staffBase.getTelephone());
			//次要电话号码是否适用
			map.put("issecondtelephoneapply", true);
			//工作电话是否适用
			map.put("isworktelephoneapply", true);
			//邮箱
			map.put("email", staffBase.getEmailen());

			//基本信息
			map.put("staffBase", staffBase);

			//护照信息
			map.put("passport", staffPassport);

			//出行信息
			TOrderUsTravelinfoEntity travelinfo = dbDao.fetch(TOrderUsTravelinfoEntity.class,
					Cnd.where("orderid", "=", orderUS.getId()));
			map.put("travelinfo", travelinfo);

			//旅伴信息
			TAppStaffTravelcompanionEntity travelcompanion = dbDao.fetch(TAppStaffTravelcompanionEntity.class,
					Cnd.where("staffid", "=", staffBase.getId()));
			map.put("travelcompanion", travelcompanion);
			//同伴信息
			List<TAppStaffCompanioninfoEntity> companioninfoList = dbDao.query(TAppStaffCompanioninfoEntity.class,
					Cnd.where("staffid", "=", staffBase.getId()), null);
			map.put("companioninfo", companioninfoList);

			//以前的美国旅游信息
			TAppStaffPrevioustripinfoEntity previoustripinfo = dbDao.fetch(TAppStaffPrevioustripinfoEntity.class,
					Cnd.where("staffid", "=", staffBase.getId()));
			map.put("previoustripinfo", previoustripinfo);
			//以前去过美国的信息
			List<TAppStaffGousinfoEntity> gousinfoList = dbDao.query(TAppStaffGousinfoEntity.class,
					Cnd.where("staffid", "=", staffBase.getId()), null);
			map.put("gousinfo", gousinfoList);
			//美国驾照信息
			List<TAppStaffDriverinfoEntity> driverinfoList = dbDao.query(TAppStaffDriverinfoEntity.class,
					Cnd.where("staffid", "=", staffBase.getId()), null);
			map.put("driverinfo", driverinfoList);

			//美国联络点
			TAppStaffContactpointEntity contactpoint = dbDao.fetch(TAppStaffContactpointEntity.class,
					Cnd.where("staffid", "=", staffBase.getId()));
			map.put("contactpoint", contactpoint);

			//家庭信息
			TAppStaffFamilyinfoEntity familyinfo = dbDao.fetch(TAppStaffFamilyinfoEntity.class,
					Cnd.where("staffid", "=", staffBase.getId()));
			map.put("familyinfo", familyinfo);
			//其他直系亲属
			List<TAppStaffImmediaterelativesEntity> immediaterelativesList = dbDao.query(
					TAppStaffImmediaterelativesEntity.class, Cnd.where("staffid", "=", staffBase.getId()), null);
			map.put("immediaterelatives", immediaterelativesList);

			//工作、教育、培训信息
			TAppStaffWorkEducationTrainingEntity workEducationTraining = dbDao.fetch(
					TAppStaffWorkEducationTrainingEntity.class, Cnd.where("staffid", "=", staffBase.getId()));
			map.put("workEducationTraining", workEducationTraining);
			//以前的工作信息
			List<TAppStaffBeforeworkEntity> beforeworkList = dbDao.query(TAppStaffBeforeworkEntity.class,
					Cnd.where("staffid", "=", staffBase.getId()), null);
			map.put("beforework", beforeworkList);
			//以前的教育信息
			List<TAppStaffBeforeeducationEntity> beforeeducationList = dbDao.query(
					TAppStaffBeforeeducationEntity.class, Cnd.where("staffid", "=", staffBase.getId()), null);
			map.put("beforeeducation", beforeeducationList);
			//使用的语言
			List<TAppStaffLanguageEntity> languageList = dbDao.query(TAppStaffLanguageEntity.class,
					Cnd.where("staffid", "=", staffBase.getId()), null);
			map.put("language", languageList);
			//去过的国家
			List<TAppStaffGocountryEntity> gocountryList = dbDao.query(TAppStaffGocountryEntity.class,
					Cnd.where("staffid", "=", staffBase.getId()), null);
			map.put("gocountry", gocountryList);
			//组织名称
			List<TAppStaffOrganizationEntity> organizationList = dbDao.query(TAppStaffOrganizationEntity.class,
					Cnd.where("staffid", "=", staffBase.getId()), null);
			map.put("organization", organizationList);
			//服兵役信息
			List<TAppStaffConscientiousEntity> conscientiousList = dbDao.query(TAppStaffConscientiousEntity.class,
					Cnd.where("staffid", "=", staffBase.getId()), null);
			map.put("conscientious", conscientiousList);

			return map;
		}
		return ResultObject.fail("暂无可执行的任务");
	}

	public Object getVcode() {
		List<TAppStaffVcodeEntity> query = dbDao.query(TAppStaffVcodeEntity.class, null, null);
		TAppStaffVcodeEntity tAppStaffVcodeEntity = query.get(0);
		String vcode = tAppStaffVcodeEntity.getVcode();
		return vcode;
	}

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
}
