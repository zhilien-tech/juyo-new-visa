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
import com.juyo.visa.common.enums.PassportTypeEnum;
import com.juyo.visa.common.enums.USMarryStatusEnum;
import com.juyo.visa.common.enums.AppPictures.AppCredentialsTypeEnum;
import com.juyo.visa.common.enums.visaProcess.ContactPointRelationshipStatusEnum;
import com.juyo.visa.common.enums.visaProcess.ImmediateFamilyMembersRelationshipEnum;
import com.juyo.visa.common.enums.visaProcess.TimeUnitStatusEnum;
import com.juyo.visa.common.enums.visaProcess.TravelCompanionRelationshipEnum;
import com.juyo.visa.common.enums.visaProcess.VisaCareersEnum;
import com.juyo.visa.common.enums.visaProcess.VisaCitizenshipEnum;
import com.juyo.visa.common.enums.visaProcess.VisaFamilyInfoEnum;
import com.juyo.visa.common.enums.visaProcess.VisaSpouseContactAddressEnum;
import com.juyo.visa.common.enums.visaProcess.VisaUSStatesEnum;
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

	@Inject
	private UploadService qiniuUploadService;//文件上传

	@Inject
	private PcVisaViewService pcVisaViewService;

	private final static String TEMPLATE_EXCEL_URL = "download";
	private final static String TEMPLATE_EXCEL_NAME = "人员管理之模块.xlsx";

	/**
	 * 
	 * 跳转到 人员管理列表页
	 *
	 * @param request
	 * @return 
	 */
	public Object staffList(HttpServletRequest request) {
		Map<String, Object> result = Maps.newHashMap();
		String localAddr = request.getLocalAddr();
		int localPort = request.getLocalPort();
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
	public Object updateVisaInfo(Integer staffId, HttpSession session) {
		Map<String, Object> result = Maps.newHashMap();

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
		List<TAppStaffCompanioninfoEntity> companionList = dbDao.query(TAppStaffCompanioninfoEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		travelCompanionInfo.put("companionList", companionList);
		result.put("travelCompanionInfo", travelCompanionInfo);

		//以前的美国旅游信息
		String sqlStrp = sqlManager.get("pcVisa_previousTrip");
		Sql sqlp = Sqls.create(sqlStrp);
		sqlp.setParam("staffid", staffId);
		Record previUSTripInfo = dbDao.fetch(sqlp);
		//---去过美国信息集合
		List<TAppStaffGousinfoEntity> gousList = dbDao.query(TAppStaffGousinfoEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		previUSTripInfo.put("gousList", gousList);
		//---美国驾照集合
		List<TAppStaffDriverinfoEntity> driverList = dbDao.query(TAppStaffDriverinfoEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		previUSTripInfo.put("driverList", driverList);
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
		//---直属亲戚信息集合
		List<TAppStaffImmediaterelativesEntity> zhiFamilyList = dbDao.query(TAppStaffImmediaterelativesEntity.class,
				Cnd.where("staffid", "=", staffId), null);
		familyInfo.put("zhiFamilyList", zhiFamilyList);
		result.put("familyInfo", familyInfo);

		//工作/教育/培训信息 
		String sqlStrw = sqlManager.get("bigCustomer_order_applicant_list");
		Sql sqlw = Sqls.create(sqlStrw);
		sqlw.setParam("staffid", staffId);
		Record workEducationInfo = dbDao.fetch(sqlw);
		//---以前工作信息集合
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
		workEducationInfo.put("organizationList", organizationList);

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

		//基本信息
		//addForm.setComid(comId);
		//addForm.setUserid(userId);
		//addForm.setOpid(userId);
		addForm.setCreatetime(nowDate);
		addForm.setUpdatetime(nowDate);

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

		Map<String, String> map = JsonResult.success("添加成功");
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
		dbDao.insert(travelCompanionInfo);
		//以前的美国旅游信息
		TAppStaffPrevioustripinfoEntity previUSTripInfo = new TAppStaffPrevioustripinfoEntity();
		previUSTripInfo.setStaffid(staffId);
		dbDao.insert(previUSTripInfo);
		//美国联络点
		TAppStaffContactpointEntity contactPointInfo = new TAppStaffContactpointEntity();
		contactPointInfo.setStaffid(staffId);
		dbDao.insert(contactPointInfo);
		//家庭信息
		TAppStaffFamilyinfoEntity familyInfo = new TAppStaffFamilyinfoEntity();
		familyInfo.setStaffid(staffId);
		dbDao.insert(familyInfo);
		//工作/教育/培训信息 
		TAppStaffWorkEducationTrainingEntity workEducationInfo = new TAppStaffWorkEducationTrainingEntity();
		workEducationInfo.setStaffid(staffId);
		dbDao.insert(workEducationInfo);

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
	public Object getStaffInfo(Integer staffId, HttpSession session) {

		TAppStaffBasicinfoEntity staffInfo = dbDao.fetch(TAppStaffBasicinfoEntity.class, Cnd.where("id", "=", staffId));

		Map<String, Object> result = Maps.newHashMap();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userType = loginUser.getUserType();
		result.put("userType", userType);

		result.put("marryStatus", staffInfo.getMarrystatus());
		result.put("marryStatusEnum", EnumUtil.enum2(USMarryStatusEnum.class));

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
			staffInfo.setAddress(updateForm.getAddress());
			staffInfo.setBirthday(updateForm.getBirthday());
			if (!Util.isEmpty(updateForm.getCardprovince())) {
				staffInfo.setCardprovince(updateForm.getCardprovince());
			}
			if (!Util.isEmpty(updateForm.getCardcity())) {
				staffInfo.setCardcity(updateForm.getCardcity());
			}
			staffInfo.setCardId(updateForm.getCardId());
			staffInfo.setCity(updateForm.getCity());
			staffInfo.setDetailedaddress(updateForm.getDetailedaddress());
			staffInfo.setEmail(updateForm.getEmail());
			staffInfo.setFirstname(updateForm.getFirstname());
			if (!Util.isEmpty(updateForm.getFirstnameen())) {
				staffInfo.setFirstnameen(updateForm.getFirstnameen().substring(1));
			}
			staffInfo.setIssueorganization(updateForm.getIssueorganization());
			staffInfo.setLastname(updateForm.getLastname());
			if (!Util.isEmpty(updateForm.getLastnameen())) {
				staffInfo.setLastnameen(updateForm.getLastnameen().substring(1));
			}
			staffInfo.setOtherfirstname(updateForm.getOtherfirstname());
			if (!Util.isEmpty(updateForm.getOtherfirstnameen())) {
				staffInfo.setOtherfirstnameen(updateForm.getOtherfirstnameen().substring(1));
			}
			staffInfo.setOtherlastname(updateForm.getOtherlastname());
			if (!Util.isEmpty(updateForm.getOtherlastnameen())) {
				staffInfo.setOtherlastnameen(updateForm.getOtherlastnameen().substring(1));
			}
			staffInfo.setNation(updateForm.getNation());
			staffInfo.setNationality(updateForm.getNationality());
			staffInfo.setProvince(updateForm.getProvince());
			staffInfo.setSex(updateForm.getSex());
			staffInfo.setHasothername(updateForm.getHasothername());
			staffInfo.setHasothernationality(updateForm.getHasothernationality());

			staffInfo.setTelephone(updateForm.getTelephone());
			if (!Util.isEmpty(updateForm.getAddressIssamewithcard())) {
				staffInfo.setAddressIssamewithcard(updateForm.getAddressIssamewithcard());
			}
			staffInfo.setEmergencylinkman(updateForm.getEmergencylinkman());
			staffInfo.setEmergencytelephone(updateForm.getEmergencytelephone());
			staffInfo.setValidenddate(updateForm.getValidenddate());
			staffInfo.setValidstartdate(updateForm.getValidstartdate());

			updateNum = dbDao.update(staffInfo);
		}

		return updateNum;
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
	public Object getPassportInfo(Integer passportId, HttpSession session) {

		Map<String, Object> result = MapUtil.map();

		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userType = loginUser.getUserType();
		result.put("userType", userType);

		String passportSqlstr = sqlManager.get("bigCustomer_staff_passport");
		Sql passportSql = Sqls.create(passportSqlstr);
		Cnd cnd = Cnd.NEW();
		cnd.and("tasp.id", "=", passportId);
		passportSql.setCondition(cnd);
		Record passport = dbDao.fetch(passportSql);

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

		result.put("passport", passport);
		result.put("infotype", ApplicantInfoTypeEnum.PASSPORT.intKey());
		result.put("passporttype", EnumUtil.enum2(PassportTypeEnum.class));
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
			staffBase.setFirstname(passportForm.getFirstname());
			staffBase.setLastname(passportForm.getLastname());
			passport.setOpid(userId);
			passport.setFirstname(passportForm.getFirstname());
			if (!Util.isEmpty(passportForm.getFirstnameen())) {
				passport.setFirstnameen(passportForm.getFirstnameen());
				staffBase.setFirstnameen(passportForm.getFirstnameen().substring(1));
			}
			passport.setLastname(passportForm.getLastname());
			if (!Util.isEmpty(passportForm.getLastnameen())) {
				passport.setLastnameen(passportForm.getLastnameen());
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

}
