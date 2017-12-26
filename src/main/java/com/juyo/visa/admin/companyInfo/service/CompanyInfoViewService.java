package com.juyo.visa.admin.companyInfo.service;

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

import com.google.common.collect.Maps;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.common.enums.BusinessScopesEnum;
import com.juyo.visa.common.enums.CompanyTypeEnum;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TCompanyOfCustomerEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TCompanyOfCustomerForm;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class CompanyInfoViewService extends BaseService<TCompanyOfCustomerEntity> {
	private static final Log log = Logs.get();

	//公司信息列表
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

	//添加公司信息
	public Object add(TCompanyOfCustomerForm addForm, HttpSession session) {

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

	//根据id 获取公司信息
	public Object getCompanyInfoById(Integer comid) {
		TCompanyOfCustomerEntity company = new TCompanyOfCustomerEntity();
		if (!Util.isEmpty(comid)) {
			company = dbDao.fetch(TCompanyOfCustomerEntity.class, comid.longValue());
		}
		return company;
	}

	//更新公司信息
	public Object update(TCompanyOfCustomerForm updateForm, HttpSession session) {
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
	}

	//校验公司全称唯一性
	public Object checkCompanyNameExist(String companyName, String id) {
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
	}
}
