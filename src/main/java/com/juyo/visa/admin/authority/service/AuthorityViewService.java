package com.juyo.visa.admin.authority.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.juyo.visa.admin.authority.form.DeptJobForm;
import com.juyo.visa.admin.authority.form.TAuthoritySqlForm;
import com.juyo.visa.entities.TDepartmentEntity;
import com.juyo.visa.entities.TFunctionEntity;
import com.uxuexi.core.common.util.BeanUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class AuthorityViewService extends BaseService<TDepartmentEntity> {
	private static final Log log = Logs.get();

	//列表分页数据
	public Object listData(TAuthoritySqlForm sqlForm) {
		/*TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();//得到公司的id
		sqlForm.setComId(companyId);*/
		return listPage4Datatables(sqlForm);
	}

	/**
	 * 新增部门职位或者修改部门职位时查询该公司的全部功能，如果传递了职位id，则查询职位的功能并设置选中
	 * @throws CloneNotSupportedException 
	 */
	public Map<String, Object> findCompanyFunctions(long jobId, final HttpSession session)
			throws CloneNotSupportedException {
		Map<String, Object> obj = new HashMap<String, Object>();

		List<TFunctionEntity> allModule = getComFuns(session);

		DeptJobForm deptJobForm = new DeptJobForm();
		if (jobId > 0) {
			allModule = getJobFuns(jobId, allModule);
			Sql sql = Sqls.create(sqlManager.get("authority_deptJob_select"));
			sql.params().set("jobId", jobId);
			deptJobForm = DbSqlUtil.fetchEntity(dbDao, DeptJobForm.class, sql);
		}
		obj.put("moduleList", allModule);
		obj.put("dept", deptJobForm);
		return obj;
	}

	//查询公司权限功能
	public List<TFunctionEntity> getComFuns(HttpSession session) {
		//查询该公司拥有的所有功能
		/*TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		int companyId = company.getId();*/
		int companyId = 5;
		Sql comFunSql = Sqls.fetchEntity(sqlManager.get("authority_com_fun"));
		comFunSql.params().set("comId", companyId);
		List<TFunctionEntity> allModule = DbSqlUtil.query(dbDao, TFunctionEntity.class, comFunSql);
		//排序functionMap
		Collections.sort(allModule, new Comparator<TFunctionEntity>() {
			@Override
			public int compare(TFunctionEntity tf1, TFunctionEntity tf2) {
				if (!Util.isEmpty(tf1.getSort()) && !Util.isEmpty(tf2.getSort())) {
					if (tf1.getSort() > tf2.getSort()) {
						return 1;
					} else if (tf1.getSort() == tf2.getSort()) {
						return 0;
					} else if (tf1.getSort() < tf2.getSort()) {
						return -1;
					}
				}
				return 0;
			}
		});
		return allModule;
	}

	//查询职位功能信息
	private List<TFunctionEntity> getJobFuns(long jobId, final List<TFunctionEntity> allModule) {
		Sql comFuncSql = Sqls.create(sqlManager.get("authority_job_fun"));
		comFuncSql.params().set("jobId", jobId);

		//每一个职位的功能不一样，每次必须是一个新的功能集合
		List<TFunctionEntity> newFunctions = new ArrayList<TFunctionEntity>();
		for (TFunctionEntity srcFunc : allModule) {
			TFunctionEntity dest = new TFunctionEntity();
			BeanUtil.copyProperties(srcFunc, dest);
			newFunctions.add(dest);
		}

		//根据职位查询关系
		List<TFunctionEntity> jobFuncs = DbSqlUtil.query(dbDao, TFunctionEntity.class, comFuncSql);
		//如果职位有功能
		if (!Util.isEmpty(jobFuncs)) {
			for (TFunctionEntity f : newFunctions) {
				if (jobFuncs.contains(f)) {
					f.setChecked("true");
				}
			}
		}
		return newFunctions;
	}

}