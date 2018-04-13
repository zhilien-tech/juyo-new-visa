package com.juyo.visa.admin.baoying.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.juyo.visa.admin.baoying.form.TAppStaffMixInfoForm;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.enums.visaProcess.YesOrNoEnum;
import com.juyo.visa.entities.TAppStaffBasicinfoEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class BaoYingViewService extends BaseService<TAppStaffBasicinfoEntity> {
	private static final Log log = Logs.get();

	/**日/月/年格式*/
	public static final String FORMAT_DD_MM_YYYY = "dd/MM/yyyy";

	@Inject
	private UploadService qiniuUploadService;//文件上传


	private final static Integer DEFAULT_IS_NO = YesOrNoEnum.NO.intKey();
	private final static Integer US_YUSHANG_COMID = 65;

	

	/**
	 * 
	 * 葆婴 人员管理列表页
	 *
	 * @param queryForm
	 * @param session
	 * @return
	 */
	public Object listData(TAppStaffMixInfoForm queryForm, HttpSession session) {

		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		
		queryForm.setComid(US_YUSHANG_COMID);

		Map<String, Object> map = listPage4Datatables(queryForm);
		return map;
	}

}
