package com.juyo.visa.admin.companyInfo.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.common.enums.BusinessScopesEnum;
import com.juyo.visa.common.enums.CompanyTypeEnum;
import com.juyo.visa.entities.TCompanyOfCustomerEntity;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class CompanyInfoViewService extends BaseService<TCompanyOfCustomerEntity> {
	private static final Log log = Logs.get();

	//到列表页
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
}
