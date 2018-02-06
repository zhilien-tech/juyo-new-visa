package com.juyo.visa.admin.bigcustomer.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.common.util.ExcelReader;
import com.juyo.visa.common.util.IpUtil;
import com.juyo.visa.entities.TAppStaffBasicinfoEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TAppStaffBasicinfoForm;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class BigCustomerViewService extends BaseService<TAppStaffBasicinfoEntity> {
	private static final Log log = Logs.get();

	private final static String TEMPLATE_EXCEL_URL = "download";
	private final static String TEMPLATE_EXCEL_NAME = "人员管理之模块.xlsx";

	public Object toList(HttpServletRequest request) {
		Map<String, Object> result = Maps.newHashMap();
		String ipAddress = IpUtil.getIpAddr(request);
		String downloadUrl = "http://" + ipAddress + ":8080/admin/bigCustomer/download.html";
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
			dbDao.insert(baseInfos);
		}

		return null;

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
		return null;

	}

}