package com.juyo.visa.admin.authority.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.juyo.visa.admin.authority.form.DeptJobForm;
import com.juyo.visa.admin.authority.form.JobDto;
import com.juyo.visa.admin.authority.form.JobZnode;
import com.juyo.visa.admin.authority.form.TAuthoritySqlForm;
import com.juyo.visa.admin.authority.form.ZTreeNode;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.entities.TComFunctionEntity;
import com.juyo.visa.entities.TComJobEntity;
import com.juyo.visa.entities.TComfunctionJobEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TDepartmentEntity;
import com.juyo.visa.entities.TFunctionEntity;
import com.juyo.visa.entities.TJobEntity;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.common.util.BeanUtil;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.chain.support.JsonResult;

@IocBean
public class AuthorityViewService extends BaseService<TDepartmentEntity> {
	private static final Log log = Logs.get();

	//列表分页数据
	public Object listData(TAuthoritySqlForm sqlForm, HttpSession session) {
		TCompanyEntity company = LoginUtil.getLoginCompany(session);
		int companyId = company.getId();
		sqlForm.setComId(companyId);
		return listPage4Datatables(sqlForm);
	}

	/**
	 * 新增部门职位或者修改部门职位时查询该公司的全部功能，如果传递了职位id，则查询职位的功能并设置选中
	 * @throws CloneNotSupportedException 
	 */
	public Map<String, Object> findCompanyFunctions(long jobId, HttpSession session) throws CloneNotSupportedException {
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

	//保存数据
	@Aop("txDb")
	public Map<String, String> saveDeptJobData(DeptJobForm addForm, final HttpSession session) {
		//通过session获取公司的id
		TCompanyEntity company = LoginUtil.getLoginCompany(session);
		int companyId = company.getId();
		//当前登陆用户
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		int userId = loginUser.getId();

		String jobJson = addForm.getJobJson();

		//1,先添加部门，拿到部门id
		Sql sql1 = Sqls.create(sqlManager.get("authority_com_dep"));
		sql1.params().set("deptName", addForm.getDeptName());
		sql1.params().set("comId", companyId);
		TDepartmentEntity newDept = DbSqlUtil.fetchEntity(dbDao, TDepartmentEntity.class, sql1);
		if (Util.isEmpty(newDept)) {
			newDept = new TDepartmentEntity();
			newDept.setComId(companyId);
			newDept.setOpId(userId);
			newDept.setDeptName(addForm.getDeptName());
			newDept.setCreateTime(DateUtil.nowDate());
			newDept = dbDao.insert(newDept);
		}
		//获取到部门id
		int deptId = newDept.getId();
		if (deptId > 0) {
			JobDto[] jobJsonArray = Json.fromJsonAsArray(JobDto.class, jobJson);

			if (!Util.isEmpty(jobJsonArray)) {
				for (JobDto jobDto : jobJsonArray) {
					int jobId = 0;
					saveOrUpdateSingleJob(userId, deptId, jobId, companyId, jobDto.getJobName(),
							jobDto.getFunctionIds());
				}
			}
		}

		return JsonResult.success("添加成功!");
	}

	//保存或更新职位
	private void saveOrUpdateSingleJob(int userId, int deptId, int jobId, int companyId, String jobName,
			String functionIds) {
		//1，判断操作类型，执行职位新增或者修改
		if (Util.isEmpty(jobId) || jobId <= 0) {
			//添加操作
			Sql sql = Sqls.create(sqlManager.get("authority_com_job"));
			sql.params().set("comId", companyId);
			sql.params().set("jobName", jobName);
			TJobEntity newJob = DbSqlUtil.fetchEntity(dbDao, TJobEntity.class, sql);

			if (Util.isEmpty(newJob)) {
				//职位不存在则新增
				newJob = new TJobEntity();
				newJob.setJobName(jobName);
				newJob.setDeptId(deptId);
				newJob.setOpId(userId);
				newJob.setCreateTime(DateUtil.nowDate());
				newJob = dbDao.insert(newJob);
				jobId = newJob.getId();//得到职位id
				//该公司添加新的职位
				TComJobEntity newComJob = new TComJobEntity();
				newComJob.setComId(companyId);
				newComJob.setJobId(jobId);
				dbDao.insert(newComJob);
			} else {
				//如果职位名称已存在
				throw new IllegalArgumentException("该公司此职位已存在,无法添加,职位名称:" + jobName);
			}

		} else {
			//更新操作
			TJobEntity newJob = dbDao.fetch(TJobEntity.class, Cnd.where("id", "=", jobId));
			if (Util.isEmpty(newJob)) {
				throw new IllegalArgumentException("欲更新的职位不存在,jobId:" + jobId);
			}

			//判断该公司是否存在同名的其他职位
			Sql sql = Sqls.create(sqlManager.get("authoritymanage_companyJob_update"));
			sql.params().set("comId", companyId);
			sql.params().set("jobName", jobName);
			sql.params().set("jobId", jobId);
			TJobEntity existsJob = DbSqlUtil.fetchEntity(dbDao, TJobEntity.class, sql);

			if (!Util.isEmpty(existsJob)) {
				//如果职位名称已存在
				throw new IllegalArgumentException("该公司此职位已存在,无法修改,职位名称:" + jobName);
			}

			dbDao.update(TJobEntity.class, Chain.make("name", jobName), Cnd.where("id", "=", newJob.getId()));
		}

		//2，截取功能模块id，根据功能id和公司id查询出公司功能id，用公司功能id和职位id往公司功能职位表添加数据
		if (!Util.isEmpty(functionIds)) {
			Iterable<String> funcIdIter = Splitter.on(",").omitEmptyStrings().split(functionIds);
			String funcIds = Joiner.on(",").join(funcIdIter);

			List<TComfunctionJobEntity> before = dbDao.query(TComfunctionJobEntity.class,
					Cnd.where("jobId", "=", jobId), null);
			List<TComFunctionEntity> comFucs = dbDao.query(TComFunctionEntity.class, Cnd.where("comId", "=", companyId)
					.and("funId", "IN", funcIds), null);
			//欲更新为
			List<TComfunctionJobEntity> after = Lists.newArrayList();
			for (TComFunctionEntity cf : comFucs) {
				TComfunctionJobEntity newComFun = new TComfunctionJobEntity();
				newComFun.setJobId(jobId);
				if (!Util.isEmpty(cf)) {
					newComFun.setComFunId(cf.getId());
				}
				after.add(newComFun);
			}
			dbDao.updateRelations(before, after);
		}
	}

	//回显部门职位和职位功能
	public Object loadJobJosn(final Long deptId, HttpSession session) {
		Map<String, Object> map = Maps.newHashMap();

		//校验
		int deptCnt = nutDao.count(TDepartmentEntity.class, Cnd.where("id", "=", deptId));
		if (deptCnt <= 0) {
			return JsonResult.error("部门不存在!");
		}

		List<TJobEntity> jobList = dbDao.query(TJobEntity.class, Cnd.where("deptId", "=", deptId), null);
		List<TFunctionEntity> allModule = getComFuns(session);

		List<JobZnode> JobZnodes = Lists.newArrayList();
		for (TJobEntity job : jobList) {
			List<TFunctionEntity> functions = getJobFuns(job.getId(), allModule);

			JobZnode jn = new JobZnode();
			jn.setJobName(job.getJobName());
			jn.setJobId(job.getId());

			List<ZTreeNode> znodes = Lists.transform(functions, new Function<TFunctionEntity, ZTreeNode>() {
				//将TFunctionEntity转换为ZTreeNode
				@Override
				public ZTreeNode apply(TFunctionEntity f) {
					ZTreeNode n = new ZTreeNode();
					n.setId(f.getId());
					n.setPId(f.getParentId());
					n.setOpen(true);
					n.setName(f.getFunName());
					n.setChecked(f.getChecked());
					return n;
				}
			});//end of tansform
			String jsonNodes = Json.toJson(znodes);
			jn.setZnodes(jsonNodes);
			JobZnodes.add(jn);
		}

		TDepartmentEntity dept = dbDao.fetch(TDepartmentEntity.class, Cnd.where("id", "=", deptId));
		map.put("dept", dept);
		map.put("list", JobZnodes);
		map.put("zNodes", allModule);
		return map;
	}

	//查询公司权限功能
	public List<TFunctionEntity> getComFuns(HttpSession session) {
		//查询该公司拥有的所有功能
		TCompanyEntity company = LoginUtil.getLoginCompany(session);
		int companyId = company.getId();
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

	//校验部门名称唯一性
	public Object checkDeptNameExist(final String deptName, final Long deptId, final HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		//通过session获取公司的id
		TCompanyEntity company = LoginUtil.getLoginCompany(session);
		int companyId = company.getId();
		int count = 0;
		if (Util.isEmpty(deptId)) {
			//add
			count = nutDao.count(TDepartmentEntity.class,
					Cnd.where("deptName", "=", deptName).and("comId", "=", companyId));
		} else {
			//update
			count = nutDao.count(TDepartmentEntity.class, Cnd.where("deptName", "=", deptName).and("id", "!=", deptId)
					.and("comId", "=", companyId));
		}
		map.put("valid", count <= 0);
		return map;
	}

	//校验职位名称唯一性
	public Object checkJobNameExist(final String jobName, final Long jobId) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		if (Util.isEmpty(jobId)) {
			//add
			count = nutDao.count(TJobEntity.class, Cnd.where("name", "=", jobName));
		} else {
			//update
			count = nutDao.count(TJobEntity.class, Cnd.where("name", "=", jobName).and("id", "!=", jobId));
		}
		map.put("valid", count <= 0);
		return map;
	}

}