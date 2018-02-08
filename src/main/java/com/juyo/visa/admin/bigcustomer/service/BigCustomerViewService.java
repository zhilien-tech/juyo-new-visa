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
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.common.enums.ApplicantInfoTypeEnum;
import com.juyo.visa.common.enums.BoyOrGirlEnum;
import com.juyo.visa.common.enums.PassportTypeEnum;
import com.juyo.visa.common.util.ExcelReader;
import com.juyo.visa.entities.TAppStaffBasicinfoEntity;
import com.juyo.visa.entities.TAppStaffPassportEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TAppStaffBasicinfoAddForm;
import com.juyo.visa.forms.TAppStaffBasicinfoForm;
import com.juyo.visa.forms.TAppStaffBasicinfoUpdateForm;
import com.juyo.visa.forms.TAppStaffPassportUpdateForm;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
public class BigCustomerViewService extends BaseService<TAppStaffBasicinfoEntity> {
	private static final Log log = Logs.get();

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
	 * 大客户 人员管理列表页
	 *
	 * @param queryForm
	 * @param session
	 * @return
	 */
	public Object listData(TAppStaffBasicinfoForm queryForm, HttpSession session) {

		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		Integer comId = loginCompany.getId();//当前登录公司id
		queryForm.setComId(comId);

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
		Integer comId = loginCompany.getId();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();

		Date nowDate = DateUtil.nowDate();

		//基本信息
		addForm.setComId(comId);
		addForm.setUserId(userId);
		addForm.setOpId(userId);
		addForm.setCreateTime(nowDate);
		addForm.setUpdateTime(nowDate);
		TAppStaffBasicinfoEntity staffInfo = add(addForm);

		//护照信息
		Integer staffId = staffInfo.getId();
		TAppStaffPassportEntity staffPassport = new TAppStaffPassportEntity();
		staffPassport.setStaffId(staffId);
		staffPassport.setOpId(userId);
		staffPassport.setCreateTime(nowDate);
		staffPassport.setUpdateTime(nowDate);
		dbDao.insert(staffPassport);

		return JsonResult.success("添加成功");
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

		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		if (!Util.isEmpty(staffInfo.getBirthday())) {
			Date birthday = staffInfo.getBirthday();
			String birthdayStr = sdf.format(birthday);
			result.put("birthday", birthdayStr);
		}
		if (!Util.isEmpty(staffInfo.getValidStartDate())) {
			Date validStartDate = staffInfo.getValidStartDate();
			String validStartDateStr = sdf.format(validStartDate);
			result.put("validStartDate", validStartDateStr);
		}
		if (!Util.isEmpty(staffInfo.getValidEndDate())) {
			Date validEndDate = staffInfo.getValidEndDate();
			String validEndDateStr = sdf.format(validEndDate);
			result.put("validEndDate", validEndDateStr);
		}

		if (!Util.isEmpty(staffInfo.getFirstNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(staffInfo.getFirstNameEn());
			result.put("firstNameEn", sb.toString());
		}
		if (!Util.isEmpty(staffInfo.getOtherFirstNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(staffInfo.getOtherFirstNameEn());
			result.put("otherFirstNameEn", sb.toString());
		}

		if (!Util.isEmpty(staffInfo.getLastNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(staffInfo.getLastNameEn());
			result.put("lastNameEn", sb.toString());
		}

		if (!Util.isEmpty(staffInfo.getOtherLastNameEn())) {
			StringBuffer sb = new StringBuffer();
			sb.append("/").append(staffInfo.getOtherLastNameEn());
			result.put("otherLastNameEn", sb.toString());
		}

		result.put("boyOrGirlEnum", EnumUtil.enum2(BoyOrGirlEnum.class));
		result.put("applicant", staffInfo);
		result.put("infoType", ApplicantInfoTypeEnum.BASE.intKey());
		result.put("staffId", staffId);
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
			staffInfo.setOpId(userId);
			staffInfo.setUpdateTime(nowDate);
			staffInfo.setCardFront(updateForm.getCardFront());
			staffInfo.setCardBack(updateForm.getCardBack());
			staffInfo.setAddress(updateForm.getAddress());
			staffInfo.setBirthday(updateForm.getBirthday());
			if (!Util.isEmpty(updateForm.getCardProvince())) {
				staffInfo.setCardProvince(updateForm.getCardProvince());
			}
			if (!Util.isEmpty(updateForm.getCardCity())) {
				staffInfo.setCardCity(updateForm.getCardCity());
			}
			staffInfo.setCardId(updateForm.getCardId());
			staffInfo.setCity(updateForm.getCity());
			staffInfo.setDetailedAddress(updateForm.getDetailedAddress());
			staffInfo.setEmail(updateForm.getEmail());
			staffInfo.setFirstName(updateForm.getFirstName());
			staffInfo.setFirstNameEn(updateForm.getFirstNameEn().substring(1));
			staffInfo.setIssueOrganization(updateForm.getIssueOrganization());
			staffInfo.setLastName(updateForm.getLastName());
			staffInfo.setLastNameEn(updateForm.getLastNameEn().substring(1));
			staffInfo.setOtherFirstName(updateForm.getOtherFirstName());
			if (!Util.isEmpty(updateForm.getOtherFirstNameEn())) {
				staffInfo.setOtherFirstNameEn(updateForm.getOtherFirstNameEn().substring(1));
			}
			staffInfo.setOtherLastName(updateForm.getOtherLastName());
			if (!Util.isEmpty(updateForm.getOtherLastNameEn())) {
				staffInfo.setOtherLastNameEn(updateForm.getOtherLastNameEn().substring(1));
			}
			staffInfo.setNation(updateForm.getNation());
			staffInfo.setNationality(updateForm.getNationality());
			staffInfo.setProvince(updateForm.getProvince());
			staffInfo.setSex(updateForm.getSex());
			staffInfo.setHasOtherName(updateForm.getHasOtherName());
			staffInfo.setHasOtherNationality(updateForm.getHasOtherNationality());

			staffInfo.setTelephone(updateForm.getTelephone());
			if (!Util.isEmpty(updateForm.getAddressIsSameWithCard())) {
				staffInfo.setAddressIsSameWithCard(updateForm.getAddressIsSameWithCard());
			}
			staffInfo.setEmergencyLinkman(updateForm.getEmergencyLinkman());
			staffInfo.setEmergencyTelephone(updateForm.getEmergencyTelephone());
			staffInfo.setValidEndDate(updateForm.getValidEndDate());
			staffInfo.setValidStartDate(updateForm.getValidStartDate());

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
				baseInfo.setFirstName(row[1]);
				baseInfo.setFirstNameEn(row[2]);
				baseInfo.setLastName(row[3]);
				baseInfo.setLastNameEn(row[4]);
				if (!Util.isEmpty(row[5])) {
					BigDecimal db = new BigDecimal(row[5]);
					baseInfo.setTelephone(db.toPlainString());
				}
				baseInfo.setEmail(row[6]);
				baseInfo.setDepartment(row[7]);
				baseInfo.setJob(row[8]);
				baseInfo.setComId(comId);
				baseInfo.setUserId(userId);
				baseInfo.setOpId(userId);
				baseInfo.setCreateTime(nowDate);
				baseInfo.setUpdateTime(nowDate);
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
						passportEntity.setStaffId(staffId);
						passportEntity.setOpId(userId);
						passportEntity.setCreateTime(nowDate);
						passportEntity.setUpdateTime(nowDate);
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
	 * 获取护照信息 TODO
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
		passportSql.setParam("tasp.id", passportId);
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
		result.put("infoType", ApplicantInfoTypeEnum.PASSPORT.intKey());
		result.put("passportType", EnumUtil.enum2(PassportTypeEnum.class));
		return result;
	}

	public Object saveEditPassport(TAppStaffPassportUpdateForm passportForm, HttpSession session) {

		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		Date nowDate = DateUtil.nowDate();
		long passortId = passportForm.getId();
		if (!Util.isEmpty(passortId)) {

			TAppStaffPassportEntity passport = dbDao.fetch(TAppStaffPassportEntity.class,
					Cnd.where("id", "=", passortId));

			passport.setOpId(loginUser.getId());
			passport.setPassportUrl(passportForm.getPassportUrl());
			passport.setOCRline1(passportForm.getOCRline1());
			passport.setOCRline2(passportForm.getOCRline2());
			passport.setBirthAddress(passportForm.getBirthAddress());
			passport.setBirthAddressEn(passportForm.getBirthAddressEn());
			passport.setBirthday(passportForm.getBirthday());
			passport.setIssuedDate(passportForm.getIssuedDate());
			passport.setIssuedOrganization(passportForm.getIssuedOrganization());
			passport.setIssuedOrganizationEn(passportForm.getIssuedOrganizationEn());
			passport.setIssuedPlace(passportForm.getIssuedPlace());
			passport.setIssuedPlaceEn(passportForm.getIssuedPlaceEn());
			passport.setPassport(passportForm.getPassport());
			passport.setSex(passportForm.getSex());
			passport.setSexEn(passportForm.getSexEn());
			passport.setType(passportForm.getType());
			passport.setValidEndDate(passportForm.getValidEndDate());
			passport.setValidStartDate(passportForm.getValidStartDate());
			passport.setValidType(passportForm.getValidType());
			passport.setUpdateTime(new Date());
			dbDao.update(passport);
		}

		return JsonResult.success("更新成功");
	}

}