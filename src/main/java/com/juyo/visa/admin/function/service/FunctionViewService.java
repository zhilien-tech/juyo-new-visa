package com.juyo.visa.admin.function.service;

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

import com.juyo.visa.common.enums.BusinessScopesEnum;
import com.juyo.visa.common.enums.CompanyTypeEnum;
import com.juyo.visa.entities.TBusinessscopeFunctionEntity;
import com.juyo.visa.entities.TComFunctionEntity;
import com.juyo.visa.entities.TFunctionEntity;
import com.juyo.visa.forms.TFunctionAddForm;
import com.juyo.visa.forms.TFunctionForm;
import com.juyo.visa.forms.TFunctionUpdateForm;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class FunctionViewService extends BaseService<TFunctionEntity> {
	private static final Log log = Logs.get();
	private static final int ZNODE_ROOT = 0;

	//分页数据
	public Object listData(TFunctionForm queryForm) {
		return listPage4Datatables(queryForm);
	}

	//跳转到添加页面
	public Object toAddFunctionPage() {
		Map<String, Object> obj = MapUtil.map();
		List<TFunctionEntity> functionList = dbDao.query(TFunctionEntity.class, null, null);
		obj.put("functions", functionList);
		obj.put("businessScopes", EnumUtil.enum2(BusinessScopesEnum.class));
		obj.put("companyType", EnumUtil.enum2(CompanyTypeEnum.class));
		return obj;
	}

	//添加功能
	public Object addFunction(TFunctionAddForm addForm) {
		int comType = addForm.getComType();//公司类型
		int bScope = addForm.getBScope();//经营范围
		//功能信息
		Integer parentId = addForm.getParentId();
		if (Util.isEmpty(parentId)) {
			addForm.setParentId(ZNODE_ROOT);
		}
		Date nowDate = DateUtil.nowDate();
		addForm.setCreateTime(nowDate);
		addForm.setUpdateTime(nowDate);
		TFunctionEntity function = this.add(addForm);

		if (!Util.isEmpty(function)) {
			int funId = function.getId();

			//经营范围类型功能信息
			TBusinessscopeFunctionEntity bsFun = new TBusinessscopeFunctionEntity();
			bsFun.setCompType(comType);
			bsFun.setCountryId(bScope);
			bsFun.setFunId(funId);
			dbDao.insert(bsFun);

			//公司功能关联表
			String sqlString = sqlManager.get("function_company_cnd");
			Sql sql = Sqls.create(sqlString);
			Cnd cnd = Cnd.NEW();
			cnd.and("c.comType", "=", comType);
			cnd.and("cbs.countryId", "=", bScope);
			List<Record> comIdList = dbDao.query(sql, cnd, null);
			if (!Util.isEmpty(comIdList)) {
				for (Record record : comIdList) {
					TComFunctionEntity comFun = new TComFunctionEntity();
					String comId = record.getString("id");
					comFun.setComId(Integer.valueOf(comId));
					comFun.setFunId(funId);
					dbDao.insert(comFun);
				}
			}
		}

		return null;
	}

	//跳转到编辑页面
	public Object getFunctionById(long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		//上级功能选择的时候要排除自己
		obj.put("funList", dbDao.query(TFunctionEntity.class, Cnd.where("id", "!=", id), null));
		obj.put("businessScopes", EnumUtil.enum2(BusinessScopesEnum.class));
		obj.put("companyType", EnumUtil.enum2(CompanyTypeEnum.class));

		//功能信息
		String sqlString = sqlManager.get("function_by_Id");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(Cnd.where("f.id", "=", id));
		obj.put("function", dbDao.fetch(sql));
		return obj;
	}

	//编辑功能
	public Object updateFunction(TFunctionUpdateForm updateForm) {
		Date nowDate = DateUtil.nowDate();
		updateForm.setUpdateTime(nowDate);
		Integer parentId = updateForm.getParentId();
		if (Util.isEmpty(parentId)) {
			updateForm.setParentId(ZNODE_ROOT);
		}
		int update = this.update(updateForm);
		return update;
	}

	//统计功能
	public Object getFunctions() {
		Map<String, Object> obj = MapUtil.map();
		List<TFunctionEntity> functionList = dbDao.query(TFunctionEntity.class, null, null);
		obj.put("functions", functionList);
		return obj;
	}

	/**
	 * TODO 通过URL获取功能
	 * <p>
	 * TODO 通过URL获取功能
	 *
	 * @param requestPath
	 * @return TODO 访问的URl
	 */
	public TFunctionEntity findFuctionByRequestPath(String requestPath) {
		return dbDao.fetch(TFunctionEntity.class, Cnd.where("url", "LIKE", "%" + requestPath + "%"));

	}
}