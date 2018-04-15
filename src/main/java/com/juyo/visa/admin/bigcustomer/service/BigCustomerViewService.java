package com.juyo.visa.admin.bigcustomer.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.ApplicantInfoTypeEnum;
import com.juyo.visa.common.enums.BoyOrGirlEnum;
import com.juyo.visa.common.enums.IsHasOrderOrNotEnum;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.PassportTypeEnum;
import com.juyo.visa.common.enums.USMarryStatusEnEnum;
import com.juyo.visa.common.enums.USMarryStatusEnum;
import com.juyo.visa.common.enums.AppPictures.AppCredentialsTypeEnum;
import com.juyo.visa.common.enums.AppPictures.AppPicturesTypeEnum;
import com.juyo.visa.common.enums.visaProcess.ContactPointRelationshipStatusEnum;
import com.juyo.visa.common.enums.visaProcess.ImmediateFamilyMembersRelationshipEnum;
import com.juyo.visa.common.enums.visaProcess.TAppStaffCredentialsEnum;
import com.juyo.visa.common.enums.visaProcess.TimeUnitStatusEnum;
import com.juyo.visa.common.enums.visaProcess.TravelCompanionRelationshipEnum;
import com.juyo.visa.common.enums.visaProcess.VisaCareersEnum;
import com.juyo.visa.common.enums.visaProcess.VisaCitizenshipEnum;
import com.juyo.visa.common.enums.visaProcess.VisaFamilyInfoEnum;
import com.juyo.visa.common.enums.visaProcess.VisaSpouseContactAddressEnum;
import com.juyo.visa.common.enums.visaProcess.VisaUSStatesEnum;
import com.juyo.visa.common.enums.visaProcess.YesOrNoEnum;
import com.juyo.visa.common.util.ExcelReader;
import com.juyo.visa.entities.TAppStaffBasicinfoEntity;
import com.juyo.visa.entities.TAppStaffBeforeeducationEntity;
import com.juyo.visa.entities.TAppStaffBeforeworkEntity;
import com.juyo.visa.entities.TAppStaffCompanioninfoEntity;
import com.juyo.visa.entities.TAppStaffConscientiousEntity;
import com.juyo.visa.entities.TAppStaffContactpointEntity;
import com.juyo.visa.entities.TAppStaffCredentialsEntity;
import com.juyo.visa.entities.TAppStaffDriverinfoEntity;
import com.juyo.visa.entities.TAppStaffFamilyinfoEntity;
import com.juyo.visa.entities.TAppStaffGocountryEntity;
import com.juyo.visa.entities.TAppStaffGousinfoEntity;
import com.juyo.visa.entities.TAppStaffImmediaterelativesEntity;
import com.juyo.visa.entities.TAppStaffLanguageEntity;
import com.juyo.visa.entities.TAppStaffOrganizationEntity;
import com.juyo.visa.entities.TAppStaffPassportEntity;
import com.juyo.visa.entities.TAppStaffPrevioustripinfoEntity;
import com.juyo.visa.entities.TAppStaffTravelcompanionEntity;
import com.juyo.visa.entities.TAppStaffWorkEducationTrainingEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TAppStaffBasicinfoAddForm;
import com.juyo.visa.forms.TAppStaffBasicinfoForm;
import com.juyo.visa.forms.TAppStaffBasicinfoUpdateForm;
import com.juyo.visa.forms.TAppStaffCredentialsAddForm;
import com.juyo.visa.forms.TAppStaffPassportUpdateForm;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
public class BigCustomerViewService extends BaseService<TAppStaffBasicinfoEntity> {
	private static final Log log = Logs.get();

	/**日/月/年格式*/
	public static final String FORMAT_DD_MM_YYYY = "dd/MM/yyyy";

	@Inject
	private UploadService qiniuUploadService;//文件上传

	@Inject
	private PcVisaViewService pcVisaViewService;

	@Inject
	private AppEventsViewService appEventsViewService;

	private final static String TEMPLATE_EXCEL_URL = "download";
	private final static String TEMPLATE_EXCEL_NAME = "人员管理之模块.xlsx";

	private final static Integer DEFAULT_IS_NO = YesOrNoEnum.NO.intKey();
	private final static Integer DEFAULT_SELECT = IsYesOrNoEnum.NO.intKey();

	private final static Integer US_YUSHANG_COMID = 65;

	/**
	 * 
	 * 跳转到 人员管理列表页
	 *
	 * @param request
	 * @return 
	 */
	public Object staffList(HttpServletRequest request) {
		Map<String, Object> result = Maps.newHashMap();
		String localAddr = request.getServerName();
		int localPort = request.getServerPort();
		String downloadUrl = "http://" + localAddr + ":" + localPort + "/admin/bigCustomer/download.html";
		result.put("downloadurl", downloadUrl);
		return result;
	}

	/**
	 * 
	 * 跳转到签证信息页
	 *
	 * @param staffId
	 * @param session
	 * @return 
	 */
	public Object updateVisaInfo(Integer staffId, Integer isDisable, HttpSession session) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("isDisable", isDisable);//页面是否可编辑
		//旅伴信息---与你的关系
		result.put("TravelCompanionRelationshipEnum", EnumUtil.enum2(TravelCompanionRelationshipEnum.class));
		//以前的美国旅游信息---州枚举
		result.put("VisaUSStatesEnum", EnumUtil.enum2(VisaUSStatesEnum.class));
		//以前的美国旅游信息---时间单位枚举
		result.put("TimeUnitStatusEnum", EnumUtil.enum2(TimeUnitStatusEnum.class));
		//美国联络点---与你的关系
		result.put("ContactPointRelationshipStatusEnum", EnumUtil.enum2(ContactPointRelationshipStatusEnum.class));
		//家庭信息---身份状态
		result.put("VisaFamilyInfoEnum", EnumUtil.enum2(VisaFamilyInfoEnum.class));
		//直系亲属---与你的关系
		result.put("ImmediateRelationshipEnum", EnumUtil.enum2(ImmediateFamilyMembersRelationshipEnum.class));
		//配偶信息---国籍
		result.put("VisaCitizenshipEnum", EnumUtil.enum2(VisaCitizenshipEnum.class));
		//配偶信息---配偶联系地址
		result.put("VisaSpouseContactAddressEnum", EnumUtil.enum2(VisaSpouseContactAddressEnum.class));
		//工作/教育/培训信息---主要职业
		result.put("VisaCareersEnum", EnumUtil.enum2(VisaCareersEnum.class));

		//---同伴信息
		List<TAppStaffCompanioninfoEntity> companionList = dbDao.query(TAppStaffCompanioninfoEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		result.put("companionList", companionList);
		//---去过美国信息集合
		List<TAppStaffGousinfoEntity> gousList = dbDao.query(TAppStaffGousinfoEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		result.put("gousList", gousList);
		//---美国驾照集合
		List<TAppStaffDriverinfoEntity> driverList = dbDao.query(TAppStaffDriverinfoEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		result.put("driverList", driverList);

		//---直属亲戚信息集合
		List<TAppStaffImmediaterelativesEntity> zhiFamilyList = dbDao.query(TAppStaffImmediaterelativesEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		result.put("zhiFamilyList", zhiFamilyList);

		//---以前工作信息集合
		List<TAppStaffBeforeworkEntity> beforeWorkList = dbDao.query(TAppStaffBeforeworkEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		result.put("beforeWorkList", beforeWorkList);
		//---以前教育信息集合 
		List<TAppStaffBeforeeducationEntity> beforeEducationList = dbDao.query(TAppStaffBeforeeducationEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		result.put("beforeEducationList", beforeEducationList);
		//---服兵役信息集合
		List<TAppStaffConscientiousEntity> conscientiousList = dbDao.query(TAppStaffConscientiousEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		result.put("conscientiousList", conscientiousList);
		//使用过的语言集合
		List<TAppStaffLanguageEntity> languageList = dbDao.query(TAppStaffLanguageEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		result.put("languageList", languageList);
		//去过的国家集合
		List<TAppStaffGocountryEntity> gocountryList = dbDao.query(TAppStaffGocountryEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		result.put("gocountryList", gocountryList);
		//参加过的慈善组织集合
		List<TAppStaffOrganizationEntity> organizationList = dbDao.query(TAppStaffOrganizationEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		result.put("organizationList", organizationList);

		//人员id
		result.put("staffId", staffId);

		return result;

	}

	/**
	 * 
	 * 获取签证信息数据
	 *
	 * @param staffId
	 * @param session
	 * @return
	 */
	@At
	@POST
	public Object getVisaInfos(Integer staffId, HttpSession session) {
		Map<String, Object> result = Maps.newHashMap();
		//旅伴信息
		String sqlStrt = sqlManager.get("pcVisa_travelCompanion");
		Sql sqlt = Sqls.create(sqlStrt);
		sqlt.setParam("staffid", staffId);
		Record travelCompanionInfo = dbDao.fetch(sqlt);
		//---同伴信息
		/*List<TAppStaffCompanioninfoEntity> companionList = dbDao.query(TAppStaffCompanioninfoEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		travelCompanionInfo.put("companionList", companionList);*/
		result.put("travelCompanionInfo", travelCompanionInfo);

		//以前的美国旅游信息
		String sqlStrp = sqlManager.get("pcVisa_previousTrip");
		Sql sqlp = Sqls.create(sqlStrp);
		sqlp.setParam("staffid", staffId);
		Record previUSTripInfo = dbDao.fetch(sqlp);
		//最后一次签证的签发日期
		String issueddate = previUSTripInfo.getString("issueddate");
		issueddate = formatDateStr(issueddate, FORMAT_DD_MM_YYYY);
		previUSTripInfo.set("issueddate", issueddate);

		//---去过美国信息集合
		/*List<TAppStaffGousinfoEntity> gousList = dbDao.query(TAppStaffGousinfoEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		previUSTripInfo.put("gousList", gousList);*/
		//---美国驾照集合
		/*List<TAppStaffDriverinfoEntity> driverList = dbDao.query(TAppStaffDriverinfoEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		previUSTripInfo.put("driverList", driverList);*/
		result.put("previUSTripInfo", previUSTripInfo);

		//美国联络点
		TAppStaffContactpointEntity contactPointInfo = dbDao.fetch(TAppStaffContactpointEntity.class,
				Cnd.where("staffid", "=", staffId));
		result.put("contactPointInfo", contactPointInfo);

		//家庭信息
		String sqlStrf = sqlManager.get("pcVisa_familyInfo");
		Sql sqlf = Sqls.create(sqlStrf);
		sqlf.setParam("staffid", staffId);
		Record familyInfo = dbDao.fetch(sqlf);
		//格式化配偶生日
		String spousebirthday = familyInfo.getString("spousebirthday");
		spousebirthday = formatDateStr(spousebirthday, FORMAT_DD_MM_YYYY);
		familyInfo.set("spousebirthday", spousebirthday);

		//---直属亲戚信息集合
		/*List<TAppStaffImmediaterelativesEntity> zhiFamilyList = dbDao.query(TAppStaffImmediaterelativesEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		familyInfo.put("zhiFamilyList", zhiFamilyList);*/
		result.put("familyInfo", familyInfo);

		//工作/教育/培训信息 
		String sqlStrw = sqlManager.get("pcVisa_word_education_training_list");
		Sql sqlw = Sqls.create(sqlStrw);
		sqlw.setParam("staffid", staffId);
		Record workEducationInfo = dbDao.fetch(sqlw);
		//格式化工作开始日期
		String workstartdate = workEducationInfo.getString("workstartdate");
		workstartdate = formatDateStr(workstartdate, FORMAT_DD_MM_YYYY);
		workEducationInfo.set("workstartdate", workstartdate);

		/*//---以前工作信息集合
		List<TAppStaffBeforeworkEntity> beforeWorkList = dbDao.query(TAppStaffBeforeworkEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		workEducationInfo.put("beforeWorkList", beforeWorkList);
		//---以前教育信息集合 
		List<TAppStaffBeforeeducationEntity> beforeEducationList = dbDao.query(TAppStaffBeforeeducationEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		workEducationInfo.put("beforeEducationList", beforeEducationList);
		//---服兵役信息集合
		List<TAppStaffConscientiousEntity> conscientiousList = dbDao.query(TAppStaffConscientiousEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		workEducationInfo.put("conscientiousList", conscientiousList);
		//使用过的语言集合
		List<TAppStaffLanguageEntity> languageList = dbDao.query(TAppStaffLanguageEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		workEducationInfo.put("languageList", languageList);
		//去过的国家集合
		List<TAppStaffGocountryEntity> gocountryList = dbDao.query(TAppStaffGocountryEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		workEducationInfo.put("gocountryList", gocountryList);
		//参加过的慈善组织集合
		List<TAppStaffOrganizationEntity> organizationList = dbDao.query(TAppStaffOrganizationEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		workEducationInfo.put("organizationList", organizationList);*/

		result.put("workEducationInfo", workEducationInfo);

		//人员id
		result.put("staffId", staffId);

		return result;
	}

	/**
	 * 
	 * TODO  跳转到其他证件页面
	 * <p>
	 *
	 * @param staffId 人员Id
	 * @param session
	 * @return 
	 */
	public Object otherSredentials(Integer staffId, HttpSession session) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("staffId", staffId);
		result.put("AppCredentialsType", EnumUtil.enum2(AppCredentialsTypeEnum.class));
		return result;
	}

	/**
	 * 
	 * 大客户 人员管理列表页
	 *
	 * @param queryForm
	 * @param session
	 * @return
	 */
	public Object listData(TAppStaffBasicinfoForm queryForm, HttpSession session) {

		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		Integer comId = loginCompany.getId();//当前登录公司id
		queryForm.setComid(comId);

		Map<String, Object> map = listPage4Datatables(queryForm);
		return map;
	}

	/**
	 * 
	 * 人员管理 添加
	 * <p>
	 *
	 * @param TAppStaffBasicinfoForm addForm
	 * @param session
	 * @return 
	 */
	public Object addStaff(TAppStaffBasicinfoAddForm addForm, HttpSession session) {

		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		//Integer comId = loginCompany.getId();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		//Integer userId = loginUser.getId();

		Date nowDate = DateUtil.nowDate();

		Map<String, String> map = new HashMap<String, String>();
		//基本信息
		addForm.setComid(US_YUSHANG_COMID);
		//addForm.setUserid(userId);
		//addForm.setOpid(userId);
		addForm.setIsidentificationnumberapply(IsYesOrNoEnum.YES.intKey());
		addForm.setIsidentificationnumberapplyen(IsYesOrNoEnum.YES.intKey());
		addForm.setIssecuritynumberapply(IsYesOrNoEnum.YES.intKey());
		addForm.setIssecuritynumberapplyen(IsYesOrNoEnum.YES.intKey());
		addForm.setIstaxpayernumberapply(IsYesOrNoEnum.YES.intKey());
		addForm.setIstaxpayernumberapplyen(IsYesOrNoEnum.YES.intKey());
		addForm.setCreatetime(nowDate);
		addForm.setUpdatetime(nowDate);
		String telephone = addForm.getTelephone();

		//根据手机号查询这个人是否注册过

		TAppStaffBasicinfoEntity userInfo = dbDao.fetch(TAppStaffBasicinfoEntity.class,
				Cnd.where("telephone", "=", telephone));
		if (!Util.isEmpty(userInfo)) {
			//将openid插入
			userInfo.setWechattoken(addForm.getWechattoken());
			userInfo.setFirstname(addForm.getFirstname());
			userInfo.setLastname(addForm.getLastname());
			userInfo.setEmail(addForm.getEmail());
			//执行更新操作
			dbDao.update(userInfo);
			System.out.println("updateUserInfo");
			map.put("flag", "1");
		} else {
			TAppStaffBasicinfoEntity staffInfo = add(addForm);

			Integer staffId = staffInfo.getId();

			//护照信息
			TAppStaffPassportEntity staffPassport = new TAppStaffPassportEntity();
			staffPassport.setStaffid(staffId);
			//		staffPassport.setOpid(userId);
			staffPassport.setCreatetime(nowDate);
			staffPassport.setUpdatetime(nowDate);
			staffPassport.setFirstname(addForm.getFirstname());
			staffPassport.setLastname(addForm.getLastname());
			TAppStaffPassportEntity passportEntity = dbDao.insert(staffPassport);
			Integer passportId = passportEntity.getId();

			map = JsonResult.success("添加成功");
			if (!Util.isEmpty(passportId)) {
				map.put("passportId", String.valueOf(passportId));
			} else {
				map.put("passportId", String.valueOf(""));
			}
			map.put("staffId", String.valueOf(staffId));

			//签证信息的添加
			//旅伴信息
			TAppStaffTravelcompanionEntity travelCompanionInfo = new TAppStaffTravelcompanionEntity();
			travelCompanionInfo.setStaffid(staffId);
			travelCompanionInfo.setIspart(DEFAULT_IS_NO);
			travelCompanionInfo.setIstravelwithother(DEFAULT_IS_NO);
			travelCompanionInfo.setIsparten(DEFAULT_IS_NO);
			travelCompanionInfo.setIstravelwithotheren(DEFAULT_IS_NO);
			dbDao.insert(travelCompanionInfo);
			//以前的美国旅游信息
			TAppStaffPrevioustripinfoEntity previUSTripInfo = new TAppStaffPrevioustripinfoEntity();
			previUSTripInfo.setStaffid(staffId);
			previUSTripInfo.setHasbeeninus(DEFAULT_IS_NO); //是否去过美国
			previUSTripInfo.setHasdriverlicense(DEFAULT_IS_NO);//是否有美国驾照
			previUSTripInfo.setIsissuedvisa(DEFAULT_IS_NO);
			previUSTripInfo.setIsapplyingsametypevisa(DEFAULT_IS_NO);
			previUSTripInfo.setIssamecountry(DEFAULT_IS_NO);
			previUSTripInfo.setIslost(DEFAULT_IS_NO);
			previUSTripInfo.setIstenprinted(DEFAULT_IS_NO);
			previUSTripInfo.setIscancelled(DEFAULT_IS_NO);
			previUSTripInfo.setIsrefused(DEFAULT_IS_NO);
			previUSTripInfo.setIslegalpermanentresident(DEFAULT_IS_NO);
			previUSTripInfo.setIsfiledimmigrantpetition(DEFAULT_IS_NO);

			previUSTripInfo.setHasbeeninusen(DEFAULT_IS_NO);
			previUSTripInfo.setHasdriverlicenseen(DEFAULT_IS_NO);
			previUSTripInfo.setIsissuedvisaen(DEFAULT_IS_NO);
			previUSTripInfo.setIsapplyingsametypevisaen(DEFAULT_IS_NO);
			previUSTripInfo.setIssamecountryen(DEFAULT_IS_NO);
			previUSTripInfo.setIslosten(DEFAULT_IS_NO);
			previUSTripInfo.setIstenprinteden(DEFAULT_IS_NO);
			previUSTripInfo.setIscancelleden(DEFAULT_IS_NO);
			previUSTripInfo.setIsrefuseden(DEFAULT_IS_NO);
			previUSTripInfo.setIslegalpermanentresidenten(DEFAULT_IS_NO);
			previUSTripInfo.setIsfiledimmigrantpetitionen(DEFAULT_IS_NO);
			dbDao.insert(previUSTripInfo);

			//美国联络点
			TAppStaffContactpointEntity contactPointInfo = new TAppStaffContactpointEntity();
			contactPointInfo.setStaffid(staffId);
			contactPointInfo.setRalationship(DEFAULT_SELECT);
			contactPointInfo.setState(DEFAULT_SELECT);
			contactPointInfo.setRalationshipen(DEFAULT_SELECT);
			contactPointInfo.setStateen(DEFAULT_SELECT);
			dbDao.insert(contactPointInfo);

			//家庭信息
			TAppStaffFamilyinfoEntity familyInfo = new TAppStaffFamilyinfoEntity();
			familyInfo.setStaffid(staffId);
			familyInfo.setIsfatherinus(DEFAULT_IS_NO);
			familyInfo.setIsmotherinus(DEFAULT_IS_NO);
			familyInfo.setHasimmediaterelatives(DEFAULT_IS_NO);
			familyInfo.setHasotherrelatives(DEFAULT_IS_NO);
			familyInfo.setIsknowspousecity(DEFAULT_IS_NO);

			familyInfo.setFatherstatus(DEFAULT_SELECT);
			familyInfo.setMotherstatus(DEFAULT_SELECT);
			familyInfo.setFatherstatusen(DEFAULT_SELECT);
			familyInfo.setMotherstatusen(DEFAULT_SELECT);

			familyInfo.setIsfatherinusen(DEFAULT_IS_NO);
			familyInfo.setIsmotherinusen(DEFAULT_IS_NO);
			familyInfo.setHasimmediaterelativesen(DEFAULT_IS_NO);
			familyInfo.setHasotherrelativesen(DEFAULT_IS_NO);
			familyInfo.setIsknowspousecityen(DEFAULT_IS_NO);

			familyInfo.setSpousenationality(DEFAULT_SELECT);
			familyInfo.setSpousenationalityen(DEFAULT_SELECT);
			familyInfo.setSpousecountry(DEFAULT_SELECT);
			familyInfo.setSpousecountryen(DEFAULT_SELECT);
			familyInfo.setSpouseaddress(DEFAULT_SELECT);
			familyInfo.setSpouseaddressen(DEFAULT_SELECT);

			dbDao.insert(familyInfo);

			//工作/教育/培训信息 
			TAppStaffWorkEducationTrainingEntity workEducationInfo = new TAppStaffWorkEducationTrainingEntity();
			workEducationInfo.setStaffid(staffId);

			workEducationInfo.setOccupation(DEFAULT_SELECT);
			workEducationInfo.setOccupationen(DEFAULT_SELECT);
			workEducationInfo.setCountry(DEFAULT_SELECT);
			workEducationInfo.setCountryen(DEFAULT_SELECT);

			workEducationInfo.setIsemployed(DEFAULT_IS_NO);
			workEducationInfo.setIssecondarylevel(DEFAULT_IS_NO);
			workEducationInfo.setIsclan(DEFAULT_IS_NO);
			workEducationInfo.setIstraveledanycountry(DEFAULT_IS_NO);
			workEducationInfo.setIsworkedcharitableorganization(DEFAULT_IS_NO);
			workEducationInfo.setHasspecializedskill(DEFAULT_IS_NO);
			workEducationInfo.setHasservedinmilitary(DEFAULT_IS_NO);
			workEducationInfo.setIsservedinrebelgroup(DEFAULT_IS_NO);

			workEducationInfo.setIsemployeden(DEFAULT_IS_NO);
			workEducationInfo.setIssecondarylevelen(DEFAULT_IS_NO);
			workEducationInfo.setIsclanen(DEFAULT_IS_NO);
			workEducationInfo.setIstraveledanycountryen(DEFAULT_IS_NO);
			workEducationInfo.setIsworkedcharitableorganizationen(DEFAULT_IS_NO);
			workEducationInfo.setHasspecializedskillen(DEFAULT_IS_NO);
			workEducationInfo.setHasservedinmilitaryen(DEFAULT_IS_NO);
			workEducationInfo.setIsservedinrebelgroupen(DEFAULT_IS_NO);
			dbDao.insert(workEducationInfo);

		}
		return map;

	}

	/**
	 * 
	 * 根据人员Id获取人员信息
	 * <p>
	 *
	 * @param staffId
	 * @return 
	 */
	public Object getStaffInfo(Integer staffId, Integer isDisable, HttpSession session) {

		TAppStaffBasicinfoEntity staffInfo = dbDao.fetch(TAppStaffBasicinfoEntity.class, Cnd.where("id", "=", staffId));

		Map<String, Object> result = Maps.newHashMap();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userType = loginUser.getUserType();
		result.put("userType", userType);

		result.put("isDisable", isDisable);//页面是否可编辑
		result.put("marryStatus", staffInfo.getMarrystatus());
		result.put("marryStatusEn", staffInfo.getMarrystatusen());
		result.put("marryStatusEnum", EnumUtil.enum2(USMarryStatusEnum.class));
		result.put("marryStatusEnEnum", EnumUtil.enum2(USMarryStatusEnEnum.class));

		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		if (!Util.isEmpty(staffInfo.getBirthday())) {
			Date birthday = staffInfo.getBirthday();
			String birthdayStr = sdf.format(birthday);
			result.put("birthday", birthdayStr);
		}
		if (!Util.isEmpty(staffInfo.getValidstartdate())) {
			Date validStartDate = staffInfo.getValidstartdate();
			String validStartDateStr = sdf.format(validStartDate);
			result.put("validStartDate", validStartDateStr);
		}
		if (!Util.isEmpty(staffInfo.getValidenddate())) {
			Date validEndDate = staffInfo.getValidenddate();
			String validEndDateStr = sdf.format(validEndDate);
			result.put("validEndDate", validEndDateStr);
		}

		if (!Util.isEmpty(staffInfo.getFirstnameen())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(staffInfo.getFirstnameen());
			result.put("firstNameEn", sb.toString());
		}
		if (!Util.isEmpty(staffInfo.getOtherfirstnameen())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(staffInfo.getOtherfirstnameen());
			result.put("otherFirstNameEn", sb.toString());
		}

		if (!Util.isEmpty(staffInfo.getLastnameen())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(staffInfo.getLastnameen());
			result.put("lastNameEn", sb.toString());
		}

		if (!Util.isEmpty(staffInfo.getOtherlastnameen())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(staffInfo.getOtherlastnameen());
			result.put("otherLastNameEn", sb.toString());
		}

		if (Util.isEmpty(staffInfo.getHasothername())) {
			//是否有曾用名
			staffInfo.setHasothername(IsHasOrderOrNotEnum.NO.intKey());
		}

		if (Util.isEmpty(staffInfo.getHasothernationality())) {
			//是否另有国籍
			staffInfo.setHasothernationality(IsHasOrderOrNotEnum.NO.intKey());
		}

		if (Util.isEmpty(staffInfo.getIsothercountrypermanentresident())) {
			//是否是其他国家永久居民
			staffInfo.setIsothercountrypermanentresident(IsHasOrderOrNotEnum.NO.intKey());
		}

		//获取护照id
		TAppStaffPassportEntity passportEntity = dbDao.fetch(TAppStaffPassportEntity.class,
				Cnd.where("staffId", "=", staffId));
		Integer passportId = passportEntity.getId();

		result.put("boyOrGirlEnum", EnumUtil.enum2(BoyOrGirlEnum.class));
		result.put("applicant", staffInfo);
		result.put("infoType", ApplicantInfoTypeEnum.BASE.intKey());
		result.put("staffId", staffId);
		result.put("passportId", passportId);
		//获取身份证、2寸照片
		TAppStaffCredentialsEntity front = dbDao.fetch(
				TAppStaffCredentialsEntity.class,
				Cnd.where("staffid", "=", staffId).and("type", "=", TAppStaffCredentialsEnum.IDCARD.intKey())
						.and("status", "=", AppPicturesTypeEnum.FRONT.intKey()));
		TAppStaffCredentialsEntity back = dbDao.fetch(
				TAppStaffCredentialsEntity.class,
				Cnd.where("staffid", "=", staffId).and("type", "=", TAppStaffCredentialsEnum.IDCARD.intKey())
						.and("status", "=", AppPicturesTypeEnum.BACK.intKey()));
		TAppStaffCredentialsEntity twoinch = dbDao.fetch(TAppStaffCredentialsEntity.class,
				Cnd.where("staffid", "=", staffId).and("type", "=", TAppStaffCredentialsEnum.TWOINCHPHOTO.intKey()));
		result.put("front", front);
		result.put("back", back);
		result.put("twoinch", twoinch);
		//电话、邮箱
		if (!Util.isEmpty(staffInfo.getTelephone()) && Util.isEmpty(staffInfo.getTelephoneen())) {
			result.put("telephoneen", staffInfo.getTelephone());
		} else {
			result.put("telephoneen", staffInfo.getTelephoneen());
		}
		if (!Util.isEmpty(staffInfo.getEmail()) && Util.isEmpty(staffInfo.getEmailen())) {
			result.put("emailen", staffInfo.getEmail());
		} else {
			result.put("emailen", staffInfo.getEmailen());
		}
		return result;
	}

	/**
	 * 
	 * 更新基本信息
	 *
	 * @param updateForm
	 * @param session
	 * @return 
	 */
	public Object updateStaffInfo(TAppStaffBasicinfoUpdateForm updateForm, HttpSession session) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		Date nowDate = DateUtil.nowDate();

		int updateNum = 0;
		long staffId = updateForm.getId();
		TAppStaffBasicinfoEntity staffInfo = dbDao.fetch(TAppStaffBasicinfoEntity.class, Cnd.where("id", "=", staffId));
		if (!Util.isEmpty(staffInfo)) {
			staffInfo.setOpid(userId);
			staffInfo.setUpdatetime(nowDate);
			staffInfo.setCardfront(updateForm.getCardfront());
			staffInfo.setCardback(updateForm.getCardback());
			staffInfo.setTwoinchphoto(updateForm.getTwoinchphoto());
			staffInfo.setAddress(updateForm.getAddress());
			staffInfo.setAddressen(updateForm.getAddressen());
			staffInfo.setAddressIssamewithcard(updateForm.getAddressIssamewithcard());
			staffInfo.setAddressIssamewithcarden(updateForm.getAddressIssamewithcarden());
			staffInfo.setBirthday(updateForm.getBirthday());
			insertImage(updateForm.getCardfront(), TAppStaffCredentialsEnum.IDCARD.intKey(), (int) staffId,
					AppPicturesTypeEnum.FRONT.intKey());
			insertImage(updateForm.getCardback(), TAppStaffCredentialsEnum.IDCARD.intKey(), (int) staffId,
					AppPicturesTypeEnum.BACK.intKey());
			insertImage(updateForm.getTwoinchphoto(), TAppStaffCredentialsEnum.TWOINCHPHOTO.intKey(), (int) staffId, -1);
			if (!Util.isEmpty(updateForm.getCardprovince())) {
				staffInfo.setCardprovince(updateForm.getCardprovince());
			}
			if (!Util.isEmpty(updateForm.getCardcity())) {
				staffInfo.setCardcity(updateForm.getCardcity());
			}
			staffInfo.setCardId(updateForm.getCardId());
			staffInfo.setCardIden(updateForm.getCardIden());
			staffInfo.setCardnum(updateForm.getCardnum());
			staffInfo.setCardnumen(updateForm.getCardnumen());
			staffInfo.setCity(updateForm.getCity());
			staffInfo.setCityen(updateForm.getCityen());
			staffInfo.setDetailedaddress(updateForm.getDetailedaddress());
			staffInfo.setDetailedaddressen(updateForm.getDetailedaddressen());
			staffInfo.setEmail(updateForm.getEmail());
			staffInfo.setEmailen(updateForm.getEmailen());
			staffInfo.setIssueorganization(updateForm.getIssueorganization());
			staffInfo.setOtherfirstname(updateForm.getOtherfirstname());
			if (!Util.isEmpty(updateForm.getOtherfirstnameen())) {
				staffInfo.setOtherfirstnameen(updateForm.getOtherfirstnameen());
			}
			staffInfo.setOtherlastname(updateForm.getOtherlastname());
			if (!Util.isEmpty(updateForm.getOtherlastnameen())) {
				staffInfo.setOtherlastnameen(updateForm.getOtherlastnameen());
			}
			staffInfo.setNation(updateForm.getNation());
			staffInfo.setNationen(updateForm.getNationen());
			staffInfo.setNationality(updateForm.getNationality());
			staffInfo.setNationalityen(updateForm.getNationalityen());
			staffInfo.setProvince(updateForm.getProvince());
			staffInfo.setProvinceen(updateForm.getProvinceen());
			staffInfo.setSex(updateForm.getSex());
			staffInfo.setHasothername(updateForm.getHasothername());
			staffInfo.setHasothernameen(updateForm.getHasothernameen());
			staffInfo.setHasothernationality(updateForm.getHasothernationality());
			staffInfo.setHasothernationalityen(updateForm.getHasothernationalityen());
			staffInfo.setHasotherpassport(updateForm.getHasotherpassport());
			staffInfo.setHasotherpassporten(updateForm.getHasotherpassporten());
			staffInfo.setTelephoneen(updateForm.getTelephoneen());
			staffInfo.setTelephone(updateForm.getTelephone());
			if (!Util.isEmpty(updateForm.getAddressIssamewithcard())) {
				staffInfo.setAddressIssamewithcard(updateForm.getAddressIssamewithcard());
			}
			if (!Util.isEmpty(updateForm.getAddressIssamewithcarden())) {
				staffInfo.setAddressIssamewithcarden(updateForm.getAddressIssamewithcarden());
			}
			staffInfo.setMarrystatus(updateForm.getMarrystatus());
			staffInfo.setMarrystatusen(updateForm.getMarrystatusen());
			staffInfo.setMarryexplainen(updateForm.getMarryexplainen());
			staffInfo.setMarryexplain(updateForm.getMarryexplain());
			staffInfo.setNationalidentificationnumber(updateForm.getNationalidentificationnumber());
			staffInfo.setNationalidentificationnumberen(updateForm.getNationalidentificationnumberen());
			staffInfo.setSocialsecuritynumber(updateForm.getSocialsecuritynumber());
			staffInfo.setSocialsecuritynumberen(updateForm.getSocialsecuritynumberen());
			staffInfo.setTaxpayernumber(updateForm.getTaxpayernumber());
			staffInfo.setTaxpayernumberen(updateForm.getTaxpayernumberen());
			staffInfo.setIstaxpayernumberapply(updateForm.getIstaxpayernumberapply());
			staffInfo.setIstaxpayernumberapplyen(updateForm.getIstaxpayernumberapplyen());
			staffInfo.setIsidentificationnumberapply(updateForm.getIsidentificationnumberapply());
			staffInfo.setIsidentificationnumberapplyen(updateForm.getIsidentificationnumberapplyen());
			staffInfo.setIsothercountrypermanentresident(updateForm.getIsothercountrypermanentresident());
			staffInfo.setIsothercountrypermanentresidenten(updateForm.getIsothercountrypermanentresidenten());
			staffInfo.setOthercountry(updateForm.getOthercountry());
			staffInfo.setOthercountryen(updateForm.getOthercountryen());
			staffInfo.setIssecuritynumberapply(updateForm.getIssecuritynumberapply());
			staffInfo.setIssecuritynumberapplyen(updateForm.getIssecuritynumberapplyen());
			staffInfo.setEmergencylinkman(updateForm.getEmergencylinkman());
			staffInfo.setEmergencytelephone(updateForm.getEmergencytelephone());
			staffInfo.setValidenddate(updateForm.getValidenddate());
			staffInfo.setValidstartdate(updateForm.getValidstartdate());

			updateNum = dbDao.update(staffInfo);
			appEventsViewService.addLoginUser(staffInfo);
		}

		return updateNum;
	}

	/**
	 * 更新签证信息 TODO
	 */
	public Object updateVisaInfos(String data, HttpServletRequest request) {

		Map<String, Object> fromJson = JsonUtil.fromJson(data, Map.class);

		//人员id
		Integer staffId = (Integer) fromJson.get("staffId");

		//旅伴信息
		String travelcompanionJson = Json.toJson(fromJson.get("travelCompanionInfo"));
		TAppStaffTravelcompanionEntity travelcompanion = JsonUtil.fromJson(travelcompanionJson,
				TAppStaffTravelcompanionEntity.class);
		dbDao.update(travelcompanion);

		//以前的美国旅游信息
		String previUSTripInfoJson = Json.toJson(fromJson.get("previUSTripInfo"));
		TAppStaffPrevioustripinfoEntity previUSTrip = JsonUtil.fromJson(previUSTripInfoJson,
				TAppStaffPrevioustripinfoEntity.class);
		dbDao.update(previUSTrip);

		//美国联络点
		String contactPointJson = Json.toJson(fromJson.get("contactPointInfo"));
		TAppStaffContactpointEntity contactPoint = JsonUtil.fromJson(contactPointJson,
				TAppStaffContactpointEntity.class);
		dbDao.update(contactPoint);

		//家庭信息
		String familyInfoJson = Json.toJson(fromJson.get("familyInfo"));
		TAppStaffFamilyinfoEntity familyInfo = JsonUtil.fromJson(familyInfoJson, TAppStaffFamilyinfoEntity.class);
		dbDao.update(familyInfo);

		//工作/教育/培训信息
		String workEducationJson = Json.toJson(fromJson.get("workEducationInfo"));
		TAppStaffWorkEducationTrainingEntity workEducationTraining = JsonUtil.fromJson(workEducationJson,
				TAppStaffWorkEducationTrainingEntity.class);
		dbDao.update(workEducationTraining);

		//同伴信息
		String companionListJson = Json.toJson(fromJson.get("companionList"));
		List<TAppStaffCompanioninfoEntity> companionList_old = dbDao.query(TAppStaffCompanioninfoEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		List<TAppStaffCompanioninfoEntity> companionList_New = JsonUtil.fromJsonAsList(
				TAppStaffCompanioninfoEntity.class, companionListJson);
		dbDao.updateRelations(companionList_old, companionList_New);

		//去过美国信息
		String gousListJson = Json.toJson(fromJson.get("gousList"));
		List<TAppStaffGousinfoEntity> gousList_old = dbDao.query(TAppStaffGousinfoEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		List<TAppStaffGousinfoEntity> gousList_New = JsonUtil.fromJsonAsList(TAppStaffGousinfoEntity.class,
				gousListJson);
		dbDao.updateRelations(gousList_old, gousList_New);

		//美国驾照信息
		String driverListJson = Json.toJson(fromJson.get("driverList"));
		List<TAppStaffDriverinfoEntity> driverList_old = dbDao.query(TAppStaffDriverinfoEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		List<TAppStaffDriverinfoEntity> driverList_New = JsonUtil.fromJsonAsList(TAppStaffDriverinfoEntity.class,
				driverListJson);
		dbDao.updateRelations(driverList_old, driverList_New);

		//直系亲属信息 
		String directListJson = Json.toJson(fromJson.get("directList"));
		List<TAppStaffImmediaterelativesEntity> directList_old = dbDao.query(TAppStaffImmediaterelativesEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		List<TAppStaffImmediaterelativesEntity> directList_New = JsonUtil.fromJsonAsList(
				TAppStaffImmediaterelativesEntity.class, directListJson);
		dbDao.updateRelations(directList_old, directList_New);

		//以前工作信息
		String beforeWorkListJson = Json.toJson(fromJson.get("beforeWorkList"));
		List<TAppStaffBeforeworkEntity> beforeWorkList_old = dbDao.query(TAppStaffBeforeworkEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		List<TAppStaffBeforeworkEntity> beforeWorkList_New = JsonUtil.fromJsonAsList(TAppStaffBeforeworkEntity.class,
				beforeWorkListJson);
		dbDao.updateRelations(beforeWorkList_old, beforeWorkList_New);

		//以前教育信息
		String beforeEducationListJson = Json.toJson(fromJson.get("beforeEducationList"));
		List<TAppStaffBeforeeducationEntity> beforeEducationList_old = dbDao.query(
				TAppStaffBeforeeducationEntity.class, Cnd.where("staffid", "=", staffId), null);
		List<TAppStaffBeforeeducationEntity> beforeEducationList_New = JsonUtil.fromJsonAsList(
				TAppStaffBeforeeducationEntity.class, beforeEducationListJson);
		dbDao.updateRelations(beforeEducationList_old, beforeEducationList_New);

		//使用过的语言信息
		String languageListJson = Json.toJson(fromJson.get("languageList"));
		List<TAppStaffLanguageEntity> languageList_old = dbDao.query(TAppStaffLanguageEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		List<TAppStaffLanguageEntity> languageList_New = JsonUtil.fromJsonAsList(TAppStaffLanguageEntity.class,
				languageListJson);
		dbDao.updateRelations(languageList_old, languageList_New);

		//过去五年去过的国家信息
		String countryListJson = Json.toJson(fromJson.get("countryList"));
		List<TAppStaffGocountryEntity> countryList_old = dbDao.query(TAppStaffGocountryEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		List<TAppStaffGocountryEntity> countryList_New = JsonUtil.fromJsonAsList(TAppStaffGocountryEntity.class,
				countryListJson);
		dbDao.updateRelations(countryList_old, countryList_New);

		//参加过的组织信息
		String organizationListJson = Json.toJson(fromJson.get("organizationList"));
		List<TAppStaffOrganizationEntity> organizationList_old = dbDao.query(TAppStaffOrganizationEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		List<TAppStaffOrganizationEntity> organizationList_New = JsonUtil.fromJsonAsList(
				TAppStaffOrganizationEntity.class, organizationListJson);
		dbDao.updateRelations(organizationList_old, organizationList_New);

		//服兵役信息
		String militaryInfoListJson = Json.toJson(fromJson.get("militaryInfoList"));
		List<TAppStaffConscientiousEntity> militaryInfoList_old = dbDao.query(TAppStaffConscientiousEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		List<TAppStaffConscientiousEntity> militaryInfoList_New = JsonUtil.fromJsonAsList(
				TAppStaffConscientiousEntity.class, militaryInfoListJson);
		dbDao.updateRelations(militaryInfoList_old, militaryInfoList_New);

		return JsonResult.success("保存成功");
	}

	/**
	 * 
	 * 人员管理Excel信息导入
	 *
	 * @param file
	 * @param request
	 * @return
	 * @throws 
	 */
	public Object importExcel(File file, HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		//当前登录公司
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		Integer comId = loginCompany.getId();
		//当前登录用户
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();

		//当前时间
		Date nowDate = DateUtil.nowDate();
		//字符编码为utf-8
		request.setCharacterEncoding("UTF-8");

		if (file != null) {
			InputStream is = new FileInputStream(file);
			ExcelReader excelReader = new ExcelReader();
			//获取Excel模板第二行之后的数据
			Map<Integer, String[]> map = excelReader.readExcelContent(is);

			List<TAppStaffBasicinfoEntity> baseInfos = Lists.newArrayList();
			for (int i = map.size(); i > 0; i--) {
				TAppStaffBasicinfoEntity baseInfo = new TAppStaffBasicinfoEntity();
				String[] row = map.get(i);
				baseInfo.setFirstname(row[1]);
				baseInfo.setFirstnameen(row[2]);
				baseInfo.setLastname(row[3]);
				baseInfo.setLastnameen(row[4]);
				if (!Util.isEmpty(row[5])) {
					BigDecimal db = new BigDecimal(row[5]);
					baseInfo.setTelephone(db.toPlainString());
				}
				baseInfo.setEmail(row[6]);
				baseInfo.setDepartment(row[7]);
				baseInfo.setJob(row[8]);
				baseInfo.setComid(comId);
				baseInfo.setUserid(userId);
				baseInfo.setOpid(userId);
				baseInfo.setCreatetime(nowDate);
				baseInfo.setUpdatetime(nowDate);
				baseInfos.add(baseInfo);
			}
			//批量添加基本信息
			List<TAppStaffBasicinfoEntity> baseInfoLists = dbDao.insert(baseInfos);

			//批量添加护照信息
			if (!Util.isEmpty(baseInfoLists)) {
				List<TAppStaffPassportEntity> passportInfos = Lists.newArrayList();
				for (TAppStaffBasicinfoEntity baseEntity : baseInfoLists) {
					if (!Util.isEmpty(baseEntity)) {
						Integer staffId = baseEntity.getId();
						TAppStaffPassportEntity passportEntity = new TAppStaffPassportEntity();
						passportEntity.setStaffid(staffId);
						passportEntity.setOpid(userId);
						passportEntity.setCreatetime(nowDate);
						passportEntity.setUpdatetime(nowDate);
						passportInfos.add(passportEntity);
					}
				}
				dbDao.insert(passportInfos);
			}
		}

		return JsonResult.success("添加成功");
	}

	/**
	 * 
	 * 下载Excel导入模板
	 *
	 * @param request
	 * @param response
	 * @return 下载Excel导入模板
	 * @throws Exception 
	 */
	public Object downloadTemplate(HttpServletRequest request, HttpServletResponse response) {
		OutputStream os = null;
		try {
			String filepath = request.getServletContext().getRealPath(TEMPLATE_EXCEL_URL);
			String path = filepath + File.separator + TEMPLATE_EXCEL_NAME;
			File file = new File(path);// path是根据日志路径和文件名拼接出来的
			String filename = file.getName();// 获取日志文件名称
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			response.reset();
			// 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.replaceAll(" ", "").getBytes("utf-8"), "iso8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			response.setContentType("application/octet-stream");
			os = new BufferedOutputStream(response.getOutputStream());
			os.write(buffer);// 输出文件
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return JsonResult.success("下载成功");
	}

	/**
	 * 
	 * 获取护照信息 
	 *
	 * @param passportId 护照id
	 * @param session
	 * @return 
	 */
	public Object getPassportInfo(Integer passportId, Integer isDisable, HttpSession session) {

		Map<String, Object> result = MapUtil.map();

		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userType = loginUser.getUserType();
		result.put("userType", userType);
		result.put("isDisable", isDisable);//页面是否可编辑
		String passportSqlstr = sqlManager.get("bigCustomer_staff_passport");
		Sql passportSql = Sqls.create(passportSqlstr);
		Cnd cnd = Cnd.NEW();
		cnd.and("tasp.id", "=", passportId);
		passportSql.setCondition(cnd);
		Record passport = dbDao.fetch(passportSql);
		int staffid = (int) passport.get("staffId");

		//格式化日期
		DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		if (!Util.isEmpty(passport.get("birthday"))) {
			Date goTripDate = (Date) passport.get("birthday");
			passport.put("birthday", format.format(goTripDate));
		}
		if (!Util.isEmpty(passport.get("validEndDate"))) {
			Date goTripDate = (Date) passport.get("validEndDate");
			passport.put("validEndDate", format.format(goTripDate));
		}
		if (!Util.isEmpty(passport.get("issuedDate"))) {
			Date goTripDate = (Date) passport.get("issuedDate");
			passport.put("issuedDate", format.format(goTripDate));
		}

		//姓名拼音处理
		if (!Util.isEmpty(passport.get("firstnameen"))) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(passport.get("firstnameen"));
			result.put("firstnameen", sb.toString());
		}
		if (!Util.isEmpty(passport.get("lastnameen"))) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(passport.get("lastnameen"));
			result.put("lastnameen", sb.toString());
		}

		result.put("passport", passport);
		result.put("infotype", ApplicantInfoTypeEnum.PASSPORT.intKey());
		result.put("passporttype", EnumUtil.enum2(PassportTypeEnum.class));
		//护照照片
		TAppStaffCredentialsEntity passurl = dbDao.fetch(TAppStaffCredentialsEntity.class,
				Cnd.where("staffid", "=", staffid).and("type", "=", TAppStaffCredentialsEnum.NEWHUZHAO.intKey()));
		result.put("passurl", passurl);
		return result;
	}

	/**
	 * 
	 * 更新护照信息
	 *
	 * @param passportForm
	 * @param session
	 * @return 
	 */
	public Object saveEditPassport(TAppStaffPassportUpdateForm passportForm, HttpSession session) {

		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		Date nowDate = DateUtil.nowDate();
		long passortId = passportForm.getId();
		if (!Util.isEmpty(passortId)) {

			TAppStaffPassportEntity passport = dbDao.fetch(TAppStaffPassportEntity.class,
					Cnd.where("id", "=", passortId));
			TAppStaffBasicinfoEntity staffBase = dbDao.fetch(TAppStaffBasicinfoEntity.class, passport.getStaffid()
					.longValue());
			insertImage(passportForm.getPassporturl(), TAppStaffCredentialsEnum.NEWHUZHAO.intKey(),
					passport.getStaffid(), -1);
			staffBase.setFirstname(passportForm.getFirstname());
			staffBase.setLastname(passportForm.getLastname());
			passport.setOpid(userId);
			passport.setFirstname(passportForm.getFirstname());
			if (!Util.isEmpty(passportForm.getFirstnameen())) {
				passport.setFirstnameen(passportForm.getFirstnameen().substring(1));
				staffBase.setFirstnameen(passportForm.getFirstnameen().substring(1));
			}
			passport.setLastname(passportForm.getLastname());
			if (!Util.isEmpty(passportForm.getLastnameen())) {
				passport.setLastnameen(passportForm.getLastnameen().substring(1));
				staffBase.setLastnameen(passportForm.getLastnameen().substring(1));
			}
			dbDao.update(staffBase);
			passport.setPassporturl(passportForm.getPassporturl());
			passport.setOCRline1(passportForm.getOCRline1());
			passport.setOCRline2(passportForm.getOCRline2());
			passport.setBirthaddress(passportForm.getBirthaddress());
			passport.setBirthaddressen(passportForm.getBirthaddressen());
			passport.setBirthday(passportForm.getBirthday());
			passport.setIssueddate(passportForm.getIssueddate());
			passport.setIssuedorganization(passportForm.getIssuedorganization());
			passport.setIssuedorganizationen(passportForm.getIssuedorganizationen());
			passport.setIssuedplace(passportForm.getIssuedplace());
			passport.setIssuedplaceen(passportForm.getIssuedplaceen());
			passport.setPassport(passportForm.getPassport());
			passport.setSex(passportForm.getSex());
			passport.setSexen(passportForm.getSexen());
			passport.setType(passportForm.getType());
			passport.setValidenddate(passportForm.getValidenddate());
			passport.setValidstartdate(passportForm.getValidstartdate());
			passport.setValidtype(passportForm.getValidtype());
			passport.setUpdatetime(nowDate);
			dbDao.update(passport);
		}

		return JsonResult.success("更新成功");
	}

	/**
	 * 
	 * 删除基本信息
	 *
	 * @param staffId 基本信息id
	 * @return 
	 */
	public Object deleteStaffById(long staffId, HttpSession session) {

		//当前登录公司
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		Integer comId = loginCompany.getId();

		TAppStaffBasicinfoEntity staffInfo = dbDao.fetch(TAppStaffBasicinfoEntity.class, Cnd.where("id", "=", staffId));
		Integer staffComId = staffInfo.getComid();

		if (Util.eq(comId, staffComId)) {
			//删除护照信息
			TAppStaffPassportEntity passport = dbDao.fetch(TAppStaffPassportEntity.class,
					Cnd.where("staffId", "=", staffId));
			if (!Util.isEmpty(passport)) {
				dbDao.delete(passport);
			}

			//删除基本信息
			if (!Util.isEmpty(staffInfo)) {
				dbDao.delete(passport);
			}

			return JsonResult.success("删除成功");
		} else {
			return JsonResult.error("权限不足");
		}

	}

	/**
	 * 
	 * 护照号 唯一性校验
	 *
	 * @param passport 护照号
	 * @param session
	 * @return 
	 */
	public Object checkPassport(String passport, Integer passportId, HttpSession session) {
		Map<String, Object> result = MapUtil.map();

		//当前登录公司
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		Integer comId = loginCompany.getId();

		String passportSqlstr = sqlManager.get("bigCustomer_staff_checkPassport");
		Sql passportSql = Sqls.create(passportSqlstr);
		Cnd cnd = Cnd.NEW();
		cnd.and("tasb.comid", "=", comId);
		cnd.and("tasp.passport", "=", passport);

		if (!Util.isEmpty(passportId)) {
			cnd.and("tasp.id", "!=", passportId);
		}
		List<Record> passportInfo = dbDao.query(passportSql, cnd, null);
		result.put("valid", passportInfo.size() <= 0);

		return result;
	}

	/**
	 * 
	 * 保存App拍摄资料
	 *
	 * @param addForm
	 * @param session
	 * @return
	 */
	public Object saveAppFile(TAppStaffCredentialsAddForm addForm, HttpSession session) {

		if (!Util.isEmpty(addForm)) {
			TAppStaffCredentialsEntity credentials = new TAppStaffCredentialsEntity();
			Integer staffId = addForm.getStaffId();
			if (!Util.isEmpty(staffId)) {
				//人员id
				credentials.setStaffid(staffId);
			}
			Integer mainId = addForm.getMainId();
			if (!Util.isEmpty(mainId)) {
				//主证件id
				credentials.setMainid(mainId);
			}
			String url = addForm.getUrl();
			if (!Util.isEmpty(url)) {
				//证件Url
				credentials.setUrl(url);
			}
			Integer type = addForm.getType();
			if (!Util.isEmpty(type)) {
				//证件类型
				credentials.setType(type);
			}
			Integer status = addForm.getStatus();
			if (!Util.isEmpty(status)) {
				//证件状态
				credentials.setStatus(status);
			}
			Integer sequence = addForm.getSequence();
			if (!Util.isEmpty(sequence)) {
				//证件序号
				credentials.setSequence(sequence);
			}
			String pageElementId = addForm.getPageElementId();
			if (!Util.isEmpty(pageElementId)) {
				//页面元素id
				credentials.setPageelementid(pageElementId);
			}

			//当前时间
			Date nowDate = DateUtil.nowDate();
			credentials.setCreatetime(nowDate);
			credentials.setUpdatetime(nowDate);

			TAppStaffCredentialsEntity entity = dbDao.insert(credentials);
			return JsonUtil.toJson(entity);
		}

		return null;
	}

	/**
	 * 
	 * 根据 人员id 和 证件类型 查询对应的证件集合
	 *
	 * @param staffId 人员id
	 * @param credentialType 证件类型
	 * @return 符合条件的证件集合
	 */
	public Object getAppFileByCondition(@Param("staffId") Integer staffId,
			@Param("credentialType") Integer credentialType) {

		List<TAppStaffCredentialsEntity> list = new ArrayList<TAppStaffCredentialsEntity>();
		if (!Util.isEmpty(staffId) || !Util.isEmpty(credentialType)) {
			Cnd cnd = Cnd.NEW();
			cnd.and("staffId", "=", staffId);
			cnd.and("type", "=", credentialType);

			list = dbDao.query(TAppStaffCredentialsEntity.class, cnd, null);
		}

		return list;
	}

	/**
	 * 
	 * 七牛云 上传文件
	 * <p>
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public Object uploadFile(File file, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = qiniuUploadService.ajaxUploadImage(file);
		file.delete();
		map.put("data", CommonConstants.IMAGES_SERVER_ADDR + map.get("data"));
		return map;

	}

	/**
	 * 把身份证、护照的照片放到人员证件信息表中
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param imgStr 照片地址
	 * @param imgType 是身份证还是护照
	 * @param staffid 人员id
	 * @param imgStatus 身份证正面还是反面
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object insertImage(String imgStr, int imgType, int staffid, int imgStatus) {
		TAppStaffCredentialsEntity credentials = null;
		if (Util.eq(imgStatus, -1)) {
			credentials = dbDao.fetch(TAppStaffCredentialsEntity.class,
					Cnd.where("staffid", "=", staffid).and("type", "=", imgType));
		} else {
			credentials = dbDao.fetch(TAppStaffCredentialsEntity.class,
					Cnd.where("staffid", "=", staffid).and("type", "=", imgType).and("status", "=", imgStatus));
		}
		if (!Util.isEmpty(credentials)) {
			credentials.setUrl(imgStr);
			dbDao.update(credentials);
		} else {
			TAppStaffCredentialsEntity credentialsEntity = new TAppStaffCredentialsEntity();
			credentialsEntity.setType(imgType);
			credentialsEntity.setUrl(imgStr);
			credentialsEntity.setStaffid(staffid);
			credentialsEntity.setStatus(imgStatus);
			credentialsEntity.setCreatetime(new Date());
			credentialsEntity.setUpdatetime(new Date());
			dbDao.insert(credentialsEntity);
		}
		return null;
	}

	//格式化日期 
	public String formatDateStr(String dateStr, String formatPattern) {
		if (!Util.isEmpty(dateStr)) {
			dateStr = DateUtil.format(dateStr, formatPattern);
		}
		return dateStr;
	}

	public Object addOrderStaff(TAppStaffBasicinfoAddForm addForm, HttpSession session) {

		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		//Integer comId = loginCompany.getId();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		//Integer userId = loginUser.getId();

		Date nowDate = DateUtil.nowDate();

		Map<String, String> map = new HashMap<String, String>();
		//基本信息
		addForm.setComid(US_YUSHANG_COMID);
		//addForm.setUserid(userId);
		//addForm.setOpid(userId);
		addForm.setIsidentificationnumberapply(IsYesOrNoEnum.YES.intKey());
		addForm.setIsidentificationnumberapplyen(IsYesOrNoEnum.YES.intKey());
		addForm.setIssecuritynumberapply(IsYesOrNoEnum.YES.intKey());
		addForm.setIssecuritynumberapplyen(IsYesOrNoEnum.YES.intKey());
		addForm.setIstaxpayernumberapply(IsYesOrNoEnum.YES.intKey());
		addForm.setIstaxpayernumberapplyen(IsYesOrNoEnum.YES.intKey());
		addForm.setCreatetime(nowDate);
		addForm.setUpdatetime(nowDate);
		String telephone = addForm.getTelephone();

		//根据手机号查询这个人是否注册过

		TAppStaffBasicinfoEntity staffInfo = add(addForm);

		Integer staffId = staffInfo.getId();

		//护照信息
		TAppStaffPassportEntity staffPassport = new TAppStaffPassportEntity();
		staffPassport.setStaffid(staffId);
		//		staffPassport.setOpid(userId);
		staffPassport.setCreatetime(nowDate);
		staffPassport.setUpdatetime(nowDate);
		staffPassport.setFirstname(addForm.getFirstname());
		staffPassport.setLastname(addForm.getLastname());
		TAppStaffPassportEntity passportEntity = dbDao.insert(staffPassport);
		Integer passportId = passportEntity.getId();

		map = JsonResult.success("添加成功");
		if (!Util.isEmpty(passportId)) {
			map.put("passportId", String.valueOf(passportId));
		} else {
			map.put("passportId", String.valueOf(""));
		}
		map.put("staffId", String.valueOf(staffId));

		//签证信息的添加
		//旅伴信息
		TAppStaffTravelcompanionEntity travelCompanionInfo = new TAppStaffTravelcompanionEntity();
		travelCompanionInfo.setStaffid(staffId);
		travelCompanionInfo.setIspart(DEFAULT_IS_NO);
		travelCompanionInfo.setIstravelwithother(DEFAULT_IS_NO);
		travelCompanionInfo.setIsparten(DEFAULT_IS_NO);
		travelCompanionInfo.setIstravelwithotheren(DEFAULT_IS_NO);
		dbDao.insert(travelCompanionInfo);
		//以前的美国旅游信息
		TAppStaffPrevioustripinfoEntity previUSTripInfo = new TAppStaffPrevioustripinfoEntity();
		previUSTripInfo.setStaffid(staffId);
		previUSTripInfo.setHasbeeninus(DEFAULT_IS_NO); //是否去过美国
		previUSTripInfo.setHasdriverlicense(DEFAULT_IS_NO);//是否有美国驾照
		previUSTripInfo.setIsissuedvisa(DEFAULT_IS_NO);
		previUSTripInfo.setIsapplyingsametypevisa(DEFAULT_IS_NO);
		previUSTripInfo.setIssamecountry(DEFAULT_IS_NO);
		previUSTripInfo.setIslost(DEFAULT_IS_NO);
		previUSTripInfo.setIstenprinted(DEFAULT_IS_NO);
		previUSTripInfo.setIscancelled(DEFAULT_IS_NO);
		previUSTripInfo.setIsrefused(DEFAULT_IS_NO);
		previUSTripInfo.setIslegalpermanentresident(DEFAULT_IS_NO);
		previUSTripInfo.setIsfiledimmigrantpetition(DEFAULT_IS_NO);

		previUSTripInfo.setHasbeeninusen(DEFAULT_IS_NO);
		previUSTripInfo.setHasdriverlicenseen(DEFAULT_IS_NO);
		previUSTripInfo.setIsissuedvisaen(DEFAULT_IS_NO);
		previUSTripInfo.setIsapplyingsametypevisaen(DEFAULT_IS_NO);
		previUSTripInfo.setIssamecountryen(DEFAULT_IS_NO);
		previUSTripInfo.setIslosten(DEFAULT_IS_NO);
		previUSTripInfo.setIstenprinteden(DEFAULT_IS_NO);
		previUSTripInfo.setIscancelleden(DEFAULT_IS_NO);
		previUSTripInfo.setIsrefuseden(DEFAULT_IS_NO);
		previUSTripInfo.setIslegalpermanentresidenten(DEFAULT_IS_NO);
		previUSTripInfo.setIsfiledimmigrantpetitionen(DEFAULT_IS_NO);
		dbDao.insert(previUSTripInfo);

		//美国联络点
		TAppStaffContactpointEntity contactPointInfo = new TAppStaffContactpointEntity();
		contactPointInfo.setStaffid(staffId);
		contactPointInfo.setRalationship(DEFAULT_SELECT);
		contactPointInfo.setState(DEFAULT_SELECT);
		contactPointInfo.setRalationshipen(DEFAULT_SELECT);
		contactPointInfo.setStateen(DEFAULT_SELECT);
		dbDao.insert(contactPointInfo);

		//家庭信息
		TAppStaffFamilyinfoEntity familyInfo = new TAppStaffFamilyinfoEntity();
		familyInfo.setStaffid(staffId);
		familyInfo.setIsfatherinus(DEFAULT_IS_NO);
		familyInfo.setIsmotherinus(DEFAULT_IS_NO);
		familyInfo.setHasimmediaterelatives(DEFAULT_IS_NO);
		familyInfo.setHasotherrelatives(DEFAULT_IS_NO);
		familyInfo.setIsknowspousecity(DEFAULT_IS_NO);

		familyInfo.setFatherstatus(DEFAULT_SELECT);
		familyInfo.setMotherstatus(DEFAULT_SELECT);
		familyInfo.setFatherstatusen(DEFAULT_SELECT);
		familyInfo.setMotherstatusen(DEFAULT_SELECT);

		familyInfo.setIsfatherinusen(DEFAULT_IS_NO);
		familyInfo.setIsmotherinusen(DEFAULT_IS_NO);
		familyInfo.setHasimmediaterelativesen(DEFAULT_IS_NO);
		familyInfo.setHasotherrelativesen(DEFAULT_IS_NO);
		familyInfo.setIsknowspousecityen(DEFAULT_IS_NO);

		familyInfo.setSpousenationality(DEFAULT_SELECT);
		familyInfo.setSpousenationalityen(DEFAULT_SELECT);
		familyInfo.setSpousecountry(DEFAULT_SELECT);
		familyInfo.setSpousecountryen(DEFAULT_SELECT);
		familyInfo.setSpouseaddress(DEFAULT_SELECT);
		familyInfo.setSpouseaddressen(DEFAULT_SELECT);

		dbDao.insert(familyInfo);

		//工作/教育/培训信息 
		TAppStaffWorkEducationTrainingEntity workEducationInfo = new TAppStaffWorkEducationTrainingEntity();
		workEducationInfo.setStaffid(staffId);

		workEducationInfo.setOccupation(DEFAULT_SELECT);
		workEducationInfo.setOccupationen(DEFAULT_SELECT);
		workEducationInfo.setCountry(DEFAULT_SELECT);
		workEducationInfo.setCountryen(DEFAULT_SELECT);

		workEducationInfo.setIsemployed(DEFAULT_IS_NO);
		workEducationInfo.setIssecondarylevel(DEFAULT_IS_NO);
		workEducationInfo.setIsclan(DEFAULT_IS_NO);
		workEducationInfo.setIstraveledanycountry(DEFAULT_IS_NO);
		workEducationInfo.setIsworkedcharitableorganization(DEFAULT_IS_NO);
		workEducationInfo.setHasspecializedskill(DEFAULT_IS_NO);
		workEducationInfo.setHasservedinmilitary(DEFAULT_IS_NO);
		workEducationInfo.setIsservedinrebelgroup(DEFAULT_IS_NO);

		workEducationInfo.setIsemployeden(DEFAULT_IS_NO);
		workEducationInfo.setIssecondarylevelen(DEFAULT_IS_NO);
		workEducationInfo.setIsclanen(DEFAULT_IS_NO);
		workEducationInfo.setIstraveledanycountryen(DEFAULT_IS_NO);
		workEducationInfo.setIsworkedcharitableorganizationen(DEFAULT_IS_NO);
		workEducationInfo.setHasspecializedskillen(DEFAULT_IS_NO);
		workEducationInfo.setHasservedinmilitaryen(DEFAULT_IS_NO);
		workEducationInfo.setIsservedinrebelgroupen(DEFAULT_IS_NO);
		dbDao.insert(workEducationInfo);

		return map;

	}

}
