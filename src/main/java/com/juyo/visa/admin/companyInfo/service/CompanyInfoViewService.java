package com.juyo.visa.admin.companyInfo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.juyo.visa.admin.companyInfo.form.TCompanyCustomerForm;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.common.enums.BusinessScopesEnum;
import com.juyo.visa.common.enums.CompanyTypeEnum;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TCompanyOfCustomerEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TCompanyAddForm;
import com.juyo.visa.forms.TCompanyForm;
import com.juyo.visa.forms.TCompanyOfCustomerForm;
import com.juyo.visa.forms.TCompanyUpdateForm;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.JsonUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class CompanyInfoViewService extends BaseService<TCompanyEntity> {
	private static final Log log = Logs.get();
	private String name;

	//公司管理中的 公司信息列表 
	//	public Object getCompanyCustomerList(TCompanyCustomerForm form, HttpSession session) {
	//		TUserEntity loginUser = LoginUtil.getLoginUser(session);
	//		Integer userid = loginUser.getId();
	//		form.setAdminId(userid);
	//		Map<String, Object> result = Maps.newHashMap();
	//		Sql sql = form.sql(sqlManager);
	//		//分页
	//		Integer pageNumber = form.getPageNumber();
	//		Integer pageSize = form.getPageSize();
	//		Pager pager = new OffsetPager((pageNumber - 1) * pageSize, pageSize);
	//		pager.setRecordCount((int) Daos.queryCount(nutDao, sql.toString()));
	//		sql.setPager(pager);
	//		sql.setCallback(Sqls.callback.records());
	//		nutDao.execute(sql);
	//
	//		List<Record> list = (List<Record>) sql.getResult();
	//		result.put("companyInfoData", list);
	//		result.put("pageTotal", pager.getPageCount());
	//		result.put("pageListCount", list.size());
	//
	//		return result;
	//	}
	/*
	 * 查询客户公司下所添加的送签社公司
	 * */

	public Object getCompanyInfoList(TCompanyOfCustomerForm form, HttpSession session) {
		//获取当前公司
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		form.setComid(loginCompany.getId());
		Map<String, Object> result = Maps.newHashMap();
		Sql sql = form.sql(sqlManager);
		//分页
		Integer pageNumber = form.getPageNumber();
		Integer pageSize = form.getPageSize();
		Pager pager = new OffsetPager((pageNumber - 1) * pageSize, pageSize);
		pager.setRecordCount((int) Daos.queryCount(nutDao, sql.toString()));
		sql.setPager(pager);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		List<Record> list = (List<Record>) sql.getResult();
		result.put("companyInfoData", list);
		result.put("pageTotal", pager.getPageCount());
		result.put("pageListCount", list.size());

		return result;
	}

	//获取管理员公司信息
	public Object getAdminCompany(HttpSession session) {
		Map<String, Object> request = new HashMap<String, Object>();
		//当前登录用户
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		//获取管理员所在的公司信息
		String sqlString = sqlManager.get("companyInfo_admin_company");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("c.adminId", "=", userId.longValue());
		cnd.and("c.isCustomer", "=", IsYesOrNoEnum.NO.intKey());
		sql.setCondition(cnd);
		Record adminComInfo = dbDao.fetch(sql);
		if (!Util.isEmpty(adminComInfo)) {
			int comtype = adminComInfo.getInt("comType");
			for (CompanyTypeEnum type : CompanyTypeEnum.values()) {
				if (!Util.isEmpty(comtype) && comtype == type.intKey()) {
					adminComInfo.put("comType", type.value());
				}
			}
			String scopes = adminComInfo.getString("scopes");
			if (scopes.length() >= 1) {
				String scopesStr = "";
				String[] split = scopes.split(",");
				for (String scope : split) {
					for (BusinessScopesEnum bsEnum : BusinessScopesEnum.values()) {
						if (!Util.isEmpty(scope) && scope.equals(String.valueOf(bsEnum.intKey()))) {
							String scopeValue = bsEnum.value();
							scopesStr += scopeValue.substring(0, 1);
						}
					}
				}
				adminComInfo.set("scopes", scopesStr);

			}

		}

		request.put("adminComInfo", adminComInfo);
		return request;
	}


	//添加公司(公司管理)
	public Object addCompany(TCompanyAddForm addForm, HttpSession session) {
		Date nowDate = DateUtil.nowDate();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userid = loginUser.getId();
		TCompanyEntity company = new TCompanyEntity();
		company.setAdminId(userid);
		company.setName(addForm.getName());
		company.setShortName(addForm.getShortName());
		company.setCdesignNum(addForm.getCdesignNum());
		company.setLinkman(addForm.getLinkman());
		company.setMobile(addForm.getMobile());
		company.setEmail(addForm.getEmail());
		company.setAddress(addForm.getAddress());
		company.setSeal(addForm.getSeal());
		company.setComType(CompanyTypeEnum.SONGQIAN.intKey());
		company.setIsCustomer(IsYesOrNoEnum.YES.intKey());
		company.setOpId(userid);
		company.setCreateTime(nowDate);
		company.setUpdateTime(nowDate);
		dbDao.insert(company);

		return "ADD SUCCESS";
	}
	/***
	 * 添加送签社公司
	 * @param addForm
	 * @param session
	 * @return
	 */
	public Object addSongQian(TCompanyForm addForm, HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);

		//获取当前登陆公司ID
		Integer loginCompanyId = loginCompany.getId();
		//获取送签社公司ID
		Integer sendcomid = addForm.getId();

		//更新到t_company_of_customer中
		TCompanyOfCustomerEntity comOfCust = new TCompanyOfCustomerEntity();

		//当前登陆公司ID
		if(!Util.isEmpty(loginCompanyId)) {
			comOfCust.setComid(loginCompanyId);
		}

		//送签社公司ID
		if(!Util.isEmpty(loginCompanyId)) {
			comOfCust.setSendcomid(sendcomid);
		}
		dbDao.insert(comOfCust);
		return "ADD SUCCESS";
	}
	/***
	 * 查询公司列表
	 * @param addForm
	 * @param session
	 * @return
	 */
	public Object getCompanyList(String companyName,final HttpSession session) {
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		//获取当前登陆公司ID
		Integer loginCompanyId = loginCompany.getId();
		//模糊查询公司
		String sqlString = sqlManager.get("companyInfo_select2_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("tc.name", "like", "%"+companyName.trim()+"%");
		cnd.and("tc.id", "!=", loginCompanyId);
		cnd.and("tcb.designatedNum", "!=", "");
		cnd.orderBy("tc.updateTime", "DESC");
		Pager pager = new OffsetPager(0, 5);
		List<Record> comList = dbDao.query(sql, cnd, pager);
		//当前登陆公司ID
		return comList;
	}


	//根据id 获取公司（公司管理）
	public Object getSelectdCompany(Integer comid) {
		String sqlString = sqlManager.get("companyInfo_select2_list");
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("tc.id", "=", comid);
		cnd.and("tcb.designatedNum","!=","");
		sql.setCondition(cnd);
		Record company = dbDao.fetch(sql);
		return company;
	}
	public Object getCompanyById(Integer cocid) {
		String sqlString = sqlManager.get("companyInfo_list_sendcompany");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(Cnd.where("tcoc.id", "=", cocid));
		Record sendComInfo = dbDao.fetch(sql);
		List<TCompanyEntity> companylist = dbDao.query(TCompanyEntity.class, null, null);
		sendComInfo.put("companylist", companylist);
		return sendComInfo;
	}

	//更新公司（公司管理）
	//	public Object updateCompany(TCompanyUpdateForm updateForm, HttpSession session) {
	//		Date nowDate = DateUtil.nowDate();
	//		TUserEntity loginUser = LoginUtil.getLoginUser(session);
	//		Integer userid = loginUser.getId();
	//
	//		Long comId = updateForm.getId();
	//		TCompanyEntity company = dbDao.fetch(TCompanyEntity.class, Cnd.where("id", "=", comId));
	//		if (!Util.isEmpty(company)) {
	//			company.setName(updateForm.getName());
	//			company.setShortName(updateForm.getShortName());
	//			company.setCdesignNum(updateForm.getCdesignNum());
	//			company.setLinkman(updateForm.getLinkman());
	//			company.setMobile(updateForm.getMobile());
	//			company.setEmail(updateForm.getEmail());
	//			company.setAddress(updateForm.getAddress());
	//			company.setSeal(updateForm.getSeal());
	//			company.setUpdateTime(nowDate);
	//			dbDao.update(company);
	//		}
	//
	//		return "UPDATE SUCCESS";
	//	}
	public Object updateCompany(Integer sendid, Integer cocid) {
		TCompanyOfCustomerEntity coc = new TCompanyOfCustomerEntity();
		if(!Util.isEmpty(cocid)) {
			coc = dbDao.fetch(TCompanyOfCustomerEntity.class, Cnd.where("id", "=", cocid));
		}
		coc.setSendcomid(sendid);
		dbDao.update(coc);
		return "UPDATE SUCCESS";
	}

	//检验公司全称唯一性
	public Object checkCompanyNameExist(String companyName, String adminId) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		if (Util.isEmpty(adminId)) {
			//add
			count = nutDao.count(TCompanyEntity.class,
					Cnd.where("name", "=", companyName).and("isCustomer", "=", IsYesOrNoEnum.YES.intKey()));
		} else {
			//update
			Cnd cnd = Cnd.NEW();
			cnd.and("name", "=", companyName);
			cnd.and("adminId", "!=", adminId);
			cnd.and("isCustomer", "=", IsYesOrNoEnum.YES.intKey());
			count = nutDao.count(TCompanyEntity.class, cnd);
		}

		map.put("valid", count <= 0);
		return map;
	}
	/**
	 * 删除选中的公司
	 * */
	public Object deleteSelectedCompany(Integer cocid) {
		TCompanyOfCustomerEntity coc = dbDao.fetch(TCompanyOfCustomerEntity.class, Cnd.where("id","=",cocid));
		if(!Util.isEmpty(coc)){
			dbDao.delete(coc);
		}
		return JsonUtil.toJson("删除成功");
	}

	//添加公司信息
	/*public Object add(TCompanyOfCustomerForm addForm, HttpSession session) {

		Date nowDate = DateUtil.nowDate();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userid = loginUser.getId();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		Integer comid = loginCompany.getId();

		TCompanyOfCustomerEntity companyInfo = new TCompanyOfCustomerEntity();
		companyInfo.setComid(comid);
		companyInfo.setFullname(addForm.getFullname());
		companyInfo.setShortname(addForm.getShortname());
		companyInfo.setDesignatedNum(addForm.getDesignatedNum());
		companyInfo.setLinkman(addForm.getLinkman());
		companyInfo.setMobile(addForm.getMobile());
		companyInfo.setEmail(addForm.getEmail());
		companyInfo.setAddress(addForm.getAddress());
		companyInfo.setOpId(userid);
		companyInfo.setCreateTime(nowDate);
		companyInfo.setUpdateTime(nowDate);

		boolean addSuccess = false;
		if (!Util.isEmpty(companyInfo)) {
			TCompanyOfCustomerEntity entity = dbDao.insert(companyInfo);
			Integer companyid = entity.getId();
			if (!Util.isEmpty(companyid)) {
				addSuccess = true;
			}
		}
		return addSuccess;
	}
	 */
	//根据id 获取公司信息
	/*public Object getCompanyInfoById(Integer comid) {
		TCompanyOfCustomerEntity company = new TCompanyOfCustomerEntity();
		if (!Util.isEmpty(comid)) {
			company = dbDao.fetch(TCompanyOfCustomerEntity.class, comid.longValue());
		}
		return company;
	}*/

	//更新公司信息
	/*public Object update(TCompanyOfCustomerForm updateForm, HttpSession session) {
		Integer comInfoId = updateForm.getId();
		TCompanyOfCustomerEntity company = dbDao.fetch(TCompanyOfCustomerEntity.class, Cnd.where("id", "=", comInfoId));
		if (!Util.isEmpty(company)) {
			Date nowDate = DateUtil.nowDate();
			company.setFullname(updateForm.getFullname());
			company.setShortname(updateForm.getShortname());
			company.setDesignatedNum(updateForm.getDesignatedNum());
			company.setLinkman(updateForm.getLinkman());
			company.setMobile(updateForm.getMobile());
			company.setEmail(updateForm.getEmail());
			company.setAddress(updateForm.getAddress());
			company.setUpdateTime(nowDate);
			dbDao.update(company);
		}

		return null;
	}*/

	//校验公司全称唯一性
	/*public Object checkCompanyNameExist(String companyName, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		if (Util.isEmpty(id)) {
			//add
			count = nutDao.count(TCompanyOfCustomerEntity.class, Cnd.where("fullname", "=", companyName));
		} else {
			//update
			count = nutDao.count(TCompanyOfCustomerEntity.class,
					Cnd.where("fullname", "=", companyName).and("id", "!=", id));
		}

		map.put("valid", count <= 0);
		return map;
	}*/
}
