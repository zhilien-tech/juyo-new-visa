package com.juyo.visa.admin.company.service;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.juyo.visa.common.enums.CompanyTypeEnum;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.UserLoginEnum;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TCompanyAddForm;
import com.juyo.visa.forms.TCompanyForm;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class CompanyViewService extends BaseService<TCompanyEntity> {
	private static final Log log = Logs.get();

	/**
	 * 注入容器中的dbDao对象，用于数据库查询、持久操作
	 */
	@Inject
	private IDbDao dbDao;

	//管理员所在的部门
	private static final String MANAGE_DEPART = "公司管理部";
	//管理员职位
	private static final String MANAGE_POSITION = "公司管理员";
	//公司管理员账号初始密码
	private static final String MANAGE_PASSWORD = "000000";

	public Object listData(TCompanyForm queryForm) {
		return listPage4Datatables(queryForm);
	}

	//加载列表页
	public Object toListCompanyPage() {
		Map<String, Object> obj = MapUtil.map();
		obj.put("companyTypeEnum", EnumUtil.enum2(CompanyTypeEnum.class));
		return obj;
	}

	//跳转到添加页面
	public Object toAddCompanyPage() {
		Map<String, Object> obj = MapUtil.map();
		obj.put("companyTypeEnum", EnumUtil.enum2(CompanyTypeEnum.class));
		return obj;
	}

	public Object addCompany(TCompanyAddForm addForm) {
		TUserEntity user = new TUserEntity();
		String adminLoginName = addForm.getAdminLoginName();//用户名
		user.setMobile(adminLoginName);//用户名
		user.setPassword(MANAGE_PASSWORD);//密码
		user.setUserType(UserLoginEnum.SQ_COMPANY_ADMIN.intKey());//公司管理员
		user.setIsDisable(Integer.valueOf(IsYesOrNoEnum.YES.intKey()));//状态可用
		TUserEntity insertUser = dbDao.insert(user);
		if (!Util.isEmpty(insertUser)) {
			Integer userId = insertUser.getId();
		}
		return null;
	}

	//跳转到编辑页面
	public Map<String, Object> getCompanyPageInfo(long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		TCompanyEntity companyEntity = this.fetch(id);
		//公司数据
		obj.put("company", companyEntity);
		//用户名
		//obj.put("telephone", dbDao.fetch(TUserEntity.class, companyEntity.getAdminId()).getUserName());
		//公司类型
		obj.put("companyTypeEnum", EnumUtil.enum2(CompanyTypeEnum.class));
		return obj;
	}

}