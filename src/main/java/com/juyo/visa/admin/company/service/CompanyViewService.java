package com.juyo.visa.admin.company.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.collect.Maps;
import com.juyo.visa.common.enums.BusinessScopesEnum;
import com.juyo.visa.common.enums.CompanyTypeEnum;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.UserLoginEnum;
import com.juyo.visa.entities.TBusinessscopeFunctionEntity;
import com.juyo.visa.entities.TComBusinessscopeEntity;
import com.juyo.visa.entities.TComFunctionEntity;
import com.juyo.visa.entities.TComJobEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TDepartmentEntity;
import com.juyo.visa.entities.TJobEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.entities.TUserJobEntity;
import com.juyo.visa.forms.TCompanyAddForm;
import com.juyo.visa.forms.TCompanyForm;
import com.juyo.visa.forms.TCompanyUpdateForm;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
public class CompanyViewService extends BaseService<TCompanyEntity> {
	private static final Log log = Logs.get();

	//管理员所在的部门
	private static final String MANAGE_DEPART = "公司管理部";
	//管理员职位
	private static final String MANAGE_POSITION = "公司管理员";
	//公司管理员账号初始密码
	private static final String MANAGE_PASSWORD = "000000";

	public Object listData(TCompanyForm queryForm) {
		return listPage4Datatables(queryForm);
	}

	//调整到列表页
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

	//添加公司
	public Object addCompany(TCompanyAddForm addForm) {
		Integer comType = addForm.getComType();
		Date nowDate = DateUtil.nowDate();
		String adminLoginName = addForm.getAdminLoginName();//用户 名

		//公司管理员信息
		TUserEntity user = new TUserEntity();
		user.setMobile(adminLoginName);//用户名
		user.setPassword(MANAGE_PASSWORD);//密码
		user.setCreateTime(nowDate);//创建时间
		user.setUpdateTime(nowDate);//上次更新时间
		//TODO
		//user.setOpId();//操作人
		user.setIsDisable(Integer.valueOf(IsYesOrNoEnum.YES.intKey()));//状态可用
		if (CompanyTypeEnum.SONGQIAN.intKey() == comType) {
			user.setUserType(UserLoginEnum.SQ_COMPANY_ADMIN.intKey());//送签社公司管理员
		} else if (CompanyTypeEnum.DIJI.intKey() == comType) {
			user.setUserType(UserLoginEnum.DJ_COMPANY_ADMIN.intKey());//地接社公司管理员
		}
		TUserEntity insertUser = dbDao.insert(user);

		//公司信息
		if (!Util.isEmpty(insertUser)) {
			Integer userId = insertUser.getId();
			addForm.setAdminId(userId);
		}
		addForm.setCreateTime(nowDate);
		addForm.setUpdateTime(nowDate);
		TCompanyEntity company = this.add(addForm);

		Integer comId = company.getId();//公司id

		//经营范围信息
		String scopeStr = "";
		String businessScopes = addForm.getBusinessScopes();
		Map<String, Object> map = getScopeList(businessScopes, comId);
		if (!Util.isEmpty(map)) {
			List<TComBusinessscopeEntity> scopeLists = (List<TComBusinessscopeEntity>) map.get("scopeList");
			if (!Util.isEmpty(scopeLists)) {
				dbDao.insert(scopeLists);
			}
			scopeStr = (String) map.get("scopeStr");
		}

		//公司管理员所在的部门信息
		TDepartmentEntity depart = new TDepartmentEntity();
		depart.setComId(company.getId());
		depart.setDeptName(MANAGE_DEPART);
		TDepartmentEntity department = dbDao.insert(depart);
		//公司管理员所在的职位信息
		TJobEntity jobEntity = new TJobEntity();
		jobEntity.setCreateTime(new Date());
		jobEntity.setDeptId(department.getId());
		jobEntity.setJobName(MANAGE_POSITION);
		TJobEntity job = dbDao.insert(jobEntity);
		//管理员的公司职位信息
		TComJobEntity companyJobEntity = new TComJobEntity();
		companyJobEntity.setComId(company.getId());
		companyJobEntity.setJobId(job.getId());
		TComJobEntity companyJob = dbDao.insert(companyJobEntity);
		//添加用户就职表
		TUserJobEntity userJobEntity = new TUserJobEntity();
		userJobEntity.setComJobId(companyJob.getId());
		userJobEntity.setHireDate(new Date());
		userJobEntity.setStatus(user.getIsDisable());
		userJobEntity.setEmpId(user.getId());
		dbDao.insert(userJobEntity);

		//公司功能权限
		List<TComFunctionEntity> comFunctions = getComFunctionList(comId, comType, scopeStr);
		if (!Util.isEmpty(comFunctions)) {
			dbDao.insert(comFunctions);
		}

		return JsonResult.success("添加成功");
	}

	//跳转到编辑页面
	public Map<String, Object> getCompanyPageInfo(long id) {
		Map<String, Object> obj = new HashMap<String, Object>();

		String sqlString = sqlManager.get("platformCompany_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("c.id", "=", id);
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		dbDao.execute(sql);
		List<Record> list = (List<Record>) sql.getResult();
		if (!Util.isEmpty(list)) {
			//公司数据
			obj.put("company", list.get(0));
		}
		//用户名
		//obj.put("telephone", dbDao.fetch(TUserEntity.class, companyEntity.getAdminId()).getUserName());
		//公司类型
		obj.put("companyTypeEnum", EnumUtil.enum2(CompanyTypeEnum.class));
		return obj;
	}

	//编辑公司
	public Object updateCompany(TCompanyUpdateForm updateForm) {

		Date nowDate = DateUtil.nowDate();
		long comId = updateForm.getId(); //公司id
		Integer comType = updateForm.getComType(); //公司类型

		//编辑管理员信息
		Integer adminId = updateForm.getAdminId();
		TUserEntity user = dbDao.fetch(TUserEntity.class, Long.valueOf(adminId));
		if (!Util.isEmpty(user)) {
			user.setMobile(updateForm.getAdminLoginName());
			if (CompanyTypeEnum.SONGQIAN.intKey() == comType) {
				user.setUserType(UserLoginEnum.SQ_COMPANY_ADMIN.intKey());//送签社公司管理员
			} else if (CompanyTypeEnum.DIJI.intKey() == comType) {
				user.setUserType(UserLoginEnum.DJ_COMPANY_ADMIN.intKey());//地接社公司管理员
			}
			user.setUpdateTime(nowDate);
			dbDao.update(user);
		}

		//公司信息
		TCompanyEntity company = dbDao.fetch(TCompanyEntity.class, Cnd.where("id", "=", updateForm.getId()));
		if (!Util.isEmpty(company)) {
			company.setComType(comType);
			company.setUpdateTime(nowDate);
			dbDao.update(company);
		}

		//公司经营范围
		List<TComBusinessscopeEntity> comScopesBefore = dbDao.query(TComBusinessscopeEntity.class,
				Cnd.where("comId", "=", comId), null);
		String scopeStr = "";
		Integer comIdInt = Integer.valueOf(comId + "");
		String businessScopes = updateForm.getBusinessScopes();
		Map<String, Object> map = getScopeList(businessScopes, comIdInt);
		if (!Util.isEmpty(map)) {
			List<TComBusinessscopeEntity> comScopesAfter = (List<TComBusinessscopeEntity>) map.get("scopeList");
			if (!Util.isEmpty(comScopesAfter)) {
				dbDao.updateRelations(comScopesBefore, comScopesAfter);
			}
			scopeStr = (String) map.get("scopeStr");
		}

		//公司权限
		List<TComFunctionEntity> comFunctionsBefore = dbDao.query(TComFunctionEntity.class,
				Cnd.where("comId", "=", comId), null);
		List<TComFunctionEntity> comFunctionsAfter = getComFunctionList(comIdInt, comType, scopeStr);
		if (!Util.isEmpty(comFunctionsAfter)) {
			dbDao.updateRelations(comFunctionsBefore, comFunctionsAfter);
		}
		return JsonResult.success("修改成功");
	}

	//获取经营范围
	public Map<String, Object> getScopeList(String businessScopes, Integer comId) {
		Map<String, Object> map = Maps.newHashMap();
		Map<String, String> scopeMap = EnumUtil.enum2(BusinessScopesEnum.class);
		String[] scopes = businessScopes.split("、");
		String scopeStr = "";//经营范围","拼接
		List<TComBusinessscopeEntity> scopeList = new ArrayList<TComBusinessscopeEntity>();
		if (!Util.isEmpty(scopes)) {
			for (String scope : scopes) {
				TComBusinessscopeEntity scopeEntity = new TComBusinessscopeEntity();
				for (Map.Entry<String, String> scopeEnum : scopeMap.entrySet()) {
					String value = scopeEnum.getValue();
					String key = scopeEnum.getKey();
					if (Util.eq(scope, value)) {
						scopeStr += key + ",";
						scopeEntity.setComId(comId);
						scopeEntity.setCountryId(Integer.valueOf(key));
						scopeList.add(scopeEntity);
					}
				}
			}

		}
		map.put("scopeList", scopeList);
		map.put("scopeStr", scopeStr);
		return map;
	}

	//获取公司权限
	public List<TComFunctionEntity> getComFunctionList(Integer comId, Integer comType, String scopeStr) {
		int strLength = scopeStr.length();
		if (strLength > 1) {
			scopeStr = scopeStr.substring(0, strLength - 1);
		}
		Cnd cnd = Cnd.NEW();
		cnd.and("compType", "=", comType);
		cnd.and("countryId", "in", scopeStr);
		List<TBusinessscopeFunctionEntity> scopeFunctions = dbDao.query(TBusinessscopeFunctionEntity.class, cnd, null);//经营范围类型功能集合
		List<TComFunctionEntity> comFunctionList = new ArrayList<TComFunctionEntity>();
		if (!Util.isEmpty(scopeFunctions)) {
			for (TBusinessscopeFunctionEntity sFunction : scopeFunctions) {
				TComFunctionEntity comFunction = new TComFunctionEntity();
				Integer companyType = sFunction.getCompType();
				Integer funId = sFunction.getFunId();
				if (Util.eq(comType, companyType)) {
					comFunction.setComId(comId);
					comFunction.setFunId(funId);
					comFunctionList.add(comFunction);
				}
			}
		}

		return comFunctionList;
	}

}