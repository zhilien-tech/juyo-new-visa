package com.juyo.visa.admin.bigcustomer.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.collect.Lists;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.common.util.ExcelReader;
import com.juyo.visa.entities.TAppStaffBasicinfoEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TAppStaffBasicinfoForm;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class BigCustomerViewService extends BaseService<TAppStaffBasicinfoEntity> {
	private static final Log log = Logs.get();

	public Object listData(TAppStaffBasicinfoForm queryForm) {
		return listPage4Datatables(queryForm);
	}

	//人员管理Excel信息导入
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
			SimpleDateFormat FORMAT_DEFAULT_DATE = new SimpleDateFormat("yyyy-MM-dd");
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
				baseInfo.setTelephone(row[5]);
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
			dbDao.insert(baseInfos);
		}

		return null;

	}

}