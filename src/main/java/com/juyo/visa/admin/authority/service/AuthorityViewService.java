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
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
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
import com.juyo.visa.common.base.MobileResult;
import com.juyo.visa.common.enums.UserJobStatusEnum;
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
public class AuthorityViewService extends BaseService<DeptJobForm> {
	private static final Log log = Logs.get();

	//列表分页数据
	public Object listData(TAuthoritySqlForm sqlForm, HttpSession session) {
		TCompanyEntity company = LoginUtil.getLoginCompany(session);
		int companyId = company.getId();
		sqlForm.setComId(companyId);
		Map<String, Object> listPage4Datatables = listPage4Datatables(sqlForm);
		List<Record> records = (List<Record>) listPage4Datatables.get("data");
		if (!Util.isEmpty(records)) {
			for (Record record : records) {
				String moduleName = record.getString("modulename");
				if (!Util.isEmpty(moduleName)) {
					moduleName = moduleName.replaceAll("公司管理,|销售,|初审,|前台,|签证,|售后,|,公司管理", "");
				}
				record.put("modulename", moduleName);
			}
			listPage4Datatables.put("data", records);
		}
		return listPage4Datatables;
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
					String functionIds = jobDto.getFunctionIds();
					if (!Util.isEmpty(functionIds)) {
						saveOrUpdateSingleJob(userId, deptId, jobId, companyId, jobDto.getJobName(), functionIds);
					}
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
			Sql sql = Sqls.create(sqlManager.get("authority_com_job_update"));
			sql.params().set("comId", companyId);
			sql.params().set("jobName", jobName);
			sql.params().set("jobId", jobId);
			TJobEntity existsJob = DbSqlUtil.fetchEntity(dbDao, TJobEntity.class, sql);

			if (!Util.isEmpty(existsJob)) {
				//如果职位名称已存在
				throw new IllegalArgumentException("该公司此职位已存在,无法修改,职位名称:" + jobName);
			}

			dbDao.update(TJobEntity.class, Chain.make("jobName", jobName), Cnd.where("id", "=", newJob.getId()));
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

	/**更新部门职位权限的数据信息*/
	@Aop("txDb")
	public Object updateJobFunctions(DeptJobForm updateForm, Long deptId, final HttpSession session) {

		//通过session获取公司的id
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		int companyId = loginCompany.getId();

		//当前登陆用户
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		int userId = loginUser.getId();

		//校验
		TDepartmentEntity dept = dbDao.fetch(TDepartmentEntity.class, Cnd.where("id", "=", deptId));
		if (Util.isEmpty(dept)) {
			return JsonResult.error("部门不存在!");
		}

		//修改部门名称
		dbDao.update(TDepartmentEntity.class, Chain.make("deptName", updateForm.getDeptName()),
				Cnd.where("id", "=", updateForm.getDeptId()));

		String jobJson = updateForm.getJobJson();
		JobDto[] jobJsonArray = Json.fromJsonAsArray(JobDto.class, jobJson);
		//根据部门id查出此部门下面职位之前的数据
		String jobIds = "";
		List<TJobEntity> beforeJob = dbDao.query(TJobEntity.class, Cnd.where("deptId", "=", deptId), null);
		for (TJobEntity job : beforeJob) {
			int jobId = job.getId();
			jobIds += String.valueOf(jobId) + ",";
		}
		if (!Util.isEmpty(jobIds)) {
			jobIds = jobIds.substring(0, jobIds.length() - 1);
		}
		//职位表欲更新为
		String afterjobIds = "";
		if (jobJsonArray.length >= 1) {
			for (JobDto jobDto : jobJsonArray) {
				long jobId = jobDto.getJobId();
				afterjobIds += String.valueOf(jobId) + ",";
			}
		}
		if (!Util.isEmpty(afterjobIds)) {
			afterjobIds = afterjobIds.substring(0, afterjobIds.length() - 1);
		}
		List<TJobEntity> afterJob = null;
		if (!Util.isEmpty(afterjobIds)) {
			afterJob = dbDao.query(TJobEntity.class, Cnd.where("id", "in", afterjobIds), null);
		}

		//根据职位id查询出公司功能职位表之前的数据
		List<TComfunctionJobEntity> beforeComfunPos = dbDao.query(TComfunctionJobEntity.class,
				Cnd.where("jobId", "in", jobIds), null);
		//公司功能职位表欲更新为
		List<TComfunctionJobEntity> afterComfunPos = dbDao.query(TComfunctionJobEntity.class,
				Cnd.where("jobId", "in", afterjobIds), null);
		dbDao.updateRelations(beforeComfunPos, afterComfunPos);
		List<TComJobEntity> beforeComJob = dbDao.query(TComJobEntity.class,
				Cnd.where("comId", "=", companyId).and("jobId", "in", jobIds), null);
		//欲更新为
		List<TComJobEntity> afterComJob = dbDao.query(TComJobEntity.class,
				Cnd.where("comId", "=", companyId).and("jobId", "in", afterjobIds), null);
		dbDao.updateRelations(beforeComJob, afterComJob);

		//更新职位
		dbDao.updateRelations(beforeJob, afterJob);

		if (!Util.isEmpty(jobJsonArray)) {
			for (JobDto jobDto : jobJsonArray) {
				int depId = dept.getId();
				int jobId = Integer.valueOf(jobDto.getJobId() + "");
				saveOrUpdateSingleJob(userId, depId, jobId, companyId, jobDto.getJobName(), jobDto.getFunctionIds());
			}
		}

		return JsonResult.success("更新成功");
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

	/**
	 * @param jobId
	 * @param session
	 * 删除职位,若此职位下面没有用户则可以删除;
	 * 若是有用户使用则不可能删除，需提示有哪个用户在使用
	 */
	@Aop("txDb")
	public Object deleteJob(final Long jobId, final HttpSession session) {
		//通过session获取公司的id
		TCompanyEntity company = LoginUtil.getLoginCompany(session);
		int companyId = company.getId();

		//查询出职位id,并查出该职位是否有用户正在使用
		Sql sql = Sqls.create(sqlManager.get("authority_delete_job"));
		sql.params().set("jobId", jobId);
		sql.params().set("jobStatus", UserJobStatusEnum.ON.intKey());
		sql.params().set("companyId", companyId);
		List<TUserEntity> listUser = DbSqlUtil.query(dbDao, TUserEntity.class, sql);
		//校验,若是该职位无用户使用，则可删除，反之亦然
		if (Util.isEmpty(listUser)) {
			//删除职位
			nutDao.clear(TComJobEntity.class, Cnd.where("jobId", "=", jobId));
			nutDao.clear(TComfunctionJobEntity.class, Cnd.where("jobId", "=", jobId));
			nutDao.delete(TJobEntity.class, jobId);
			return JsonResult.success("删除成功");
		} else {
			//返回此职位下的用户信息，提示不能删除
			return MobileResult.error("删除失败", listUser);
		}

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
	public Object checkJobNameExist(final String jobName, final Long jobId, HttpSession session) {
		//通过session获取公司的id
		TCompanyEntity company = LoginUtil.getLoginCompany(session);
		int comId = company.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		Sql sql = Sqls.create(sqlManager.get("authority_jobName_count"));
		if (Util.isEmpty(jobId)) {
			//add
			Cnd cnd = Cnd.NEW();
			cnd.and("j.jobName", "=", jobName);
			cnd.and("d.comId", "=", comId);
			sql.setCondition(cnd);
			//count = nutDao.count(TDepartmentEntity.class,Cnd.where("deptName", "=", deptName).and("comId", "=", companyId));
		} else {
			//update
			Cnd cnd = Cnd.NEW();
			cnd.and("j.jobName", "=", jobName);
			cnd.and("d.comId", "=", comId);
			cnd.and("j.id", "!=", jobId);
			sql.setCondition(cnd);
			//count = nutDao.count(TJobEntity.class, Cnd.where("jobName", "=", jobName).and("id", "!=", jobId));
		}
		count = (int) Daos.queryCount(nutDao, sql.toString());
		map.put("valid", count <= 0);
		return map;
	}

}