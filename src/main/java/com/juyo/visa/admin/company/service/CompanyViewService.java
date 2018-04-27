package com.juyo.visa.admin.company.service;

import java.io.File;
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
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.common.access.AccessConfig;
import com.juyo.visa.common.access.sign.MD5;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.comstants.CommonConstants;
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
import com.juyo.visa.entities.TFunctionEntity;
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
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
public class CompanyViewService extends BaseService<TCompanyEntity> {
	private static final Log log = Logs.get();

	@Inject
	private UploadService qiniuUploadService;//文件上传

	//管理员所在的部门
	private static final String MANAGE_DEPART = "公司管理部";
	//管理员职位
	private static final String MANAGE_POSITION = "公司管理员";
	//公司管理员账号初始密码
	private static final String MANAGE_PASSWORD = "000000";

	public Object listData(TCompanyForm queryForm) {
		Map<String, Object> map = listPage4Datatables(queryForm);
		List<Record> records = (List<Record>) map.get("data");
		for (Record record : records) {
			int comtype = record.getInt("comtype");
			for (CompanyTypeEnum typeEnum : CompanyTypeEnum.values()) {
				if (comtype == typeEnum.intKey()) {
					if(comtype==CompanyTypeEnum.BIGCUSTOMER.intKey()) {
						record.set("comtype", "");
					}else {
						record.set("comtype", typeEnum.value());
					}
				}
			}
		}
		return map;
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
	public Object addCompany(TCompanyAddForm addForm, HttpSession session) {
		Integer comType = addForm.getComType();
		Date nowDate = DateUtil.nowDate();
		String adminLoginName = addForm.getAdminLoginName();//用户 名

		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		int opId = loginUser.getId();

		//公司管理员信息
		TUserEntity user = new TUserEntity();
		user.setMobile(Strings.trim(adminLoginName));//用户名
		user.setName(Strings.trim(adminLoginName));//用户名
		String password = MD5.sign(MANAGE_PASSWORD, AccessConfig.password_secret, AccessConfig.INPUT_CHARSET);//密码加密
		user.setPassword(password);//密码
		user.setCreateTime(nowDate);//创建时间
		user.setUpdateTime(nowDate);//上次更新时间
		user.setOpId(opId);//操作人
		user.setIsDisable(Integer.valueOf(IsYesOrNoEnum.YES.intKey()));//状态可用
		if (CompanyTypeEnum.SONGQIAN.intKey() == comType) {
			user.setUserType(UserLoginEnum.SQ_COMPANY_ADMIN.intKey());//送签社公司管理员
		} else if (CompanyTypeEnum.DIJI.intKey() == comType) {
			user.setUserType(UserLoginEnum.DJ_COMPANY_ADMIN.intKey());//地接社公司管理员
		} else if (CompanyTypeEnum.SONGQIANSIMPLE.intKey() == comType) {
			user.setUserType(UserLoginEnum.JJ_COMPANY_ADMIN.intKey());//送签社精简公司管理员
		} else if (CompanyTypeEnum.BIGCUSTOMER.intKey() == comType) {
			user.setUserType(UserLoginEnum.BIG_COMPANY_ADMIN.intKey());//大客户公司管理员
		}
		TUserEntity insertUser = dbDao.insert(user);

		//公司信息
		if (!Util.isEmpty(insertUser)) {
			Integer userId = insertUser.getId();
			addForm.setAdminId(userId);
		}
		addForm.setIsCustomer(IsYesOrNoEnum.NO.intKey());
		addForm.setOpId(opId);
		addForm.setCreateTime(nowDate);
		addForm.setUpdateTime(nowDate);
		TCompanyEntity company = this.add(addForm);

		Integer comId = company.getId();//公司id

		//经营范围信息
		String scopeStr = "";
		String businessScopes = addForm.getBusinessScopes();
		String designatedNum = addForm.getDesignatedNum();
		Map<String, Object> map = getScopeList(businessScopes, comId, "ADD");
		if (!Util.isEmpty(map)) {
			List<TComBusinessscopeEntity> scopeLists = (List<TComBusinessscopeEntity>) map.get("scopeList");
			for (TComBusinessscopeEntity comBSEntity : scopeLists) {
				Integer countryId = comBSEntity.getCountryId();
				if (Util.eq(countryId, BusinessScopesEnum.JAPAN.intKey())) {
					comBSEntity.setDesignatedNum(designatedNum);
				}
			}
			if (!Util.isEmpty(scopeLists)) {
				dbDao.insert(scopeLists);
			}
			scopeStr = (String) map.get("scopeStr");
		}

		//公司管理员所在的部门信息
		TDepartmentEntity depart = new TDepartmentEntity();
		depart.setComId(company.getId());
		depart.setOpId(opId);
		depart.setDeptName(MANAGE_DEPART);
		TDepartmentEntity department = dbDao.insert(depart);
		//公司管理员所在的职位信息
		TJobEntity jobEntity = new TJobEntity();
		jobEntity.setCreateTime(new Date());
		jobEntity.setDeptId(department.getId());
		jobEntity.setJobName(MANAGE_POSITION);
		jobEntity.setOpId(opId);
		TJobEntity job = dbDao.insert(jobEntity);

		//用户表更新部门职位
		insertUser.setComId(comId);
		insertUser.setJobId(job.getId());
		insertUser.setDepartmentId(department.getId());
		nutDao.update(insertUser);

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
			Record record = list.get(0);
			List<TComBusinessscopeEntity> comBusiness = dbDao.query(TComBusinessscopeEntity.class,
					Cnd.where("comId", "=", id), null);
			for (TComBusinessscopeEntity cbsEntity : comBusiness) {
				Integer countryId = cbsEntity.getCountryId();
				if (Util.eq(countryId, BusinessScopesEnum.JAPAN.intKey())) {
					record.set("designatedNum", cbsEntity.getDesignatedNum());
				}
			}
			record.set("comBusinesss", comBusiness);
			obj.put("company", record);
		}
		//用户名
		//obj.put("telephone", dbDao.fetch(TUserEntity.class, companyEntity.getAdminId()).getUserName());
		//公司类型
		obj.put("companyTypeEnum", EnumUtil.enum2(CompanyTypeEnum.class));
		return obj;
	}

	//编辑公司
	public Object updateCompany(TCompanyUpdateForm updateForm, HttpSession session) {

		Date nowDate = DateUtil.nowDate();
		long comId = updateForm.getId(); //公司id
		Integer comType = updateForm.getComType(); //公司类型

		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		int opId = loginUser.getId();

		//编辑管理员信息
		Integer adminId = updateForm.getAdminId();
		TUserEntity user = dbDao.fetch(TUserEntity.class, Long.valueOf(adminId));
		if (!Util.isEmpty(user)) {
			String adminLoginName = updateForm.getAdminLoginName();
			if (!Util.isEmpty(adminLoginName)) {
				user.setMobile(Strings.trim(adminLoginName));
				user.setName(Strings.trim(adminLoginName));
				user.setOpId(opId);
			}
			user.setName(updateForm.getAdminLoginName());
			if (CompanyTypeEnum.SONGQIAN.intKey() == comType) {
				user.setUserType(UserLoginEnum.SQ_COMPANY_ADMIN.intKey());//送签社公司管理员
			} else if (CompanyTypeEnum.DIJI.intKey() == comType) {
				user.setUserType(UserLoginEnum.DJ_COMPANY_ADMIN.intKey());//地接社公司管理员
			} else if (CompanyTypeEnum.SONGQIANSIMPLE.intKey() == comType) {
				user.setUserType(UserLoginEnum.JJ_COMPANY_ADMIN.intKey());//地接社公司管理员
			}
			user.setUpdateTime(nowDate);
			dbDao.update(user);
		}

		//公司信息
		long compId = updateForm.getId();
		String name = updateForm.getName();
		String shortName = updateForm.getShortName();
		String linkman = updateForm.getLinkman();
		String mobile = updateForm.getMobile();
		String email = updateForm.getEmail();
		String address = updateForm.getAddress();
		String license = updateForm.getLicense();
		String seal = updateForm.getSeal();
		TCompanyEntity company = dbDao.fetch(TCompanyEntity.class, Cnd.where("id", "=", compId));
		if (!Util.isEmpty(company)) {
			company.setComType(comType);
			company.setName(name);
			company.setShortName(shortName);
			company.setLinkman(linkman);
			company.setMobile(mobile);
			company.setEmail(email);
			company.setAddress(address);
			company.setLicense(license);
			company.setSeal(seal);
			company.setIsCustomer(IsYesOrNoEnum.NO.intKey());
			company.setUpdateTime(nowDate);
			dbDao.update(company);
		}

		//公司经营范围
		List<TComBusinessscopeEntity> comScopesBefore = dbDao.query(TComBusinessscopeEntity.class,
				Cnd.where("comId", "=", comId), null);
		String scopeStr = "";
		Integer comIdInt = Integer.valueOf(comId + "");
		String businessScopes = updateForm.getBusinessScopes();
		String designatedNum = updateForm.getDesignatedNum();
		Map<String, Object> map = getScopeList(businessScopes, comIdInt, "UPDATE");
		if (!Util.isEmpty(map)) {
			List<TComBusinessscopeEntity> comScopesAfter = (List<TComBusinessscopeEntity>) map.get("scopeList");
			for (TComBusinessscopeEntity comBSEntity : comScopesAfter) {
				Integer countryId = comBSEntity.getCountryId();
				if (Util.eq(countryId, BusinessScopesEnum.JAPAN.intKey())) {
					comBSEntity.setDesignatedNum(designatedNum);
				}
			}
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
	public Map<String, Object> getScopeList(String businessScopes, Integer comId, String type) {
		Map<String, Object> map = Maps.newHashMap();
		Map<String, String> scopeMap = EnumUtil.enum2(BusinessScopesEnum.class);
		String[] scopes = businessScopes.split(",");
		String scopeStr = "";//经营范围","拼接
		List<TComBusinessscopeEntity> scopeList = new ArrayList<TComBusinessscopeEntity>();
		if (!Util.isEmpty(scopes)) {
			for (String scope : scopes) {
				TComBusinessscopeEntity scopeEntity = new TComBusinessscopeEntity();
				for (Map.Entry<String, String> scopeEnum : scopeMap.entrySet()) {
					String value = scopeEnum.getValue();
					String key = scopeEnum.getKey();
					if (Util.eq("ADD", type)) {
						if (Util.eq(scope, value)) {
							scopeStr += key + ",";
							scopeEntity.setComId(comId);
							scopeEntity.setCountryId(Integer.valueOf(key));
							scopeList.add(scopeEntity);
						}
					} else if (Util.eq("UPDATE", type)) {
						if (Util.eq(scope, key)) {
							scopeStr += key + ",";
							scopeEntity.setComId(comId);
							scopeEntity.setCountryId(Integer.valueOf(key));
							scopeList.add(scopeEntity);
						}
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

	/**
	 * TODO 获取公司所有的权限
	 * <p>
	 * TODO 通过公司id获取公司拥有的权限
	 *
	 * @param comId
	 * @return TODO comid 公司id
	 */
	public List<TFunctionEntity> getCompanyFunctions(int comId) {
		String sqlstr = sqlManager.get("get_company_functions_list");
		Sql sql = Sqls.create(sqlstr);
		sql.setParam("comId", comId);
		return DbSqlUtil.query(dbDao, TFunctionEntity.class, sql);
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
		/*request.setCharacterEncoding(CommonConstants.CHARACTER_ENCODING_PROJECT);//字符编码为utf-8
		response.setCharacterEncoding(CommonConstants.CHARACTER_ENCODING_PROJECT);
		Uploader uploader = new Uploader(request, qiniuUploadService);
		uploader.upload();
		String url = CommonConstants.IMAGES_SERVER_ADDR + uploader.getUrl();
		return url;*/

		Map<String, Object> map = qiniuUploadService.ajaxUploadImage(file);
		file.delete();
		map.put("data", CommonConstants.IMAGES_SERVER_ADDR + map.get("data"));
		return map;

	}

	/**
	 * 
	 * 校验用户名唯一性
	 * <p>
	 *
	 * @param loginName
	 * @param session
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object checkLoginNameExist(String loginName, String adminId) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		if (Util.isEmpty(adminId)) {
			//add
			count = nutDao.count(TUserEntity.class, Cnd.where("mobile", "=", loginName));
		} else {
			//update
			count = nutDao.count(TUserEntity.class, Cnd.where("mobile", "=", loginName).and("id", "!=", adminId));
		}
		map.put("valid", count <= 0);
		return map;
	}

	/**
	 * 
	 * 校验公司全称唯一性
	 * <p>
	 *
	 * @param loginName
	 * @param session
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object checkCompanyNameExist(String companyName, String adminId) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		if (Util.isEmpty(adminId)) {
			//add
			count = nutDao.count(TCompanyEntity.class,
					Cnd.where("name", "=", companyName).and("isCustomer", "=", IsYesOrNoEnum.NO.intKey()));
		} else {
			//update
			Cnd cnd = Cnd.NEW();
			cnd.and("name", "=", companyName);
			cnd.and("adminId", "!=", adminId);
			cnd.and("isCustomer", "=", IsYesOrNoEnum.NO.intKey());
			count = nutDao.count(TCompanyEntity.class, cnd);
		}

		map.put("valid", count <= 0);
		return map;
	}

}
