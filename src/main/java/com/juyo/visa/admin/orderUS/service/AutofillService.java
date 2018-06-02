/**
 * AutofillService.java
 * com.juyo.visa.admin.orderUS.service
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.orderUS.service;

import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.juyo.visa.common.enums.MarryStatusEnum;
import com.juyo.visa.entities.TAppStaffFormerspouseEntity;
import com.juyo.visa.entities.TAppStaffOrderUsEntity;
import com.juyo.visa.entities.TOrderUsEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class AutofillService extends BaseService<TOrderUsEntity> {

	public Object getData(int orderid) {
		Map<String, Object> result = Maps.newHashMap();
		TAppStaffOrderUsEntity stafforderus = dbDao.fetch(TAppStaffOrderUsEntity.class,
				Cnd.where("orderid", "=", orderid));
		Integer staffid = stafforderus.getStaffid();
		//查询所需数据（除了一对多）
		String sqlStr = sqlManager.get("getAutofilldata");
		Sql infosql = Sqls.create(sqlStr);
		Cnd cnd = Cnd.NEW();
		cnd.and("tou.id", "=", orderid);
		infosql.setCondition(cnd);
		Record info = dbDao.fetch(infosql);
		//根据API封装数据（从内向外）
		Map<String, Object> InforMation = Maps.newHashMap();

		//BaseInfo
		Map<String, Object> BaseInfo = Maps.newHashMap();
		if (!Util.isEmpty(info.get("sex"))) {
			BaseInfo.put("sex", info.get("sex"));
		}
		if (!Util.isEmpty(info.get("birthday"))) {
			BaseInfo.put("data_of_birth", info.get("birthday"));
		}
		BaseInfo.put("nationality", "CHN");
		if (!Util.isEmpty(info.get("cardId"))) {
			BaseInfo.put("ic", info.get("cardId"));
		}
		if (!Util.isEmpty(info.get("telephone"))) {
			BaseInfo.put("phone", info.get("telephone"));
		}
		if (!Util.isEmpty(info.get("email"))) {
			BaseInfo.put("email", info.get("email"));
		}
		if (!Util.isEmpty(info.get("marrystatus"))) {
			int status = (int) info.get("marrystatus");
			for (MarryStatusEnum marrystatus : MarryStatusEnum.values()) {
				if (status == marrystatus.intKey()) {
					BaseInfo.put("Marriage", marrystatus.value());
				}
			}
		}

		//NameInfo
		Map<String, Object> NameInfo = Maps.newHashMap();
		if (!Util.isEmpty(info.get("telecodefirstname"))) {
			NameInfo.put("surnames_code_cn", info.get("telecodefirstname"));
		} else {
			NameInfo.put("surnames_code_cn", "");
		}
		if (!Util.isEmpty(info.get("telecodelastname"))) {
			NameInfo.put("given_names_code_cn", info.get("telecodelastname"));
		} else {
			NameInfo.put("given_names_code_cn", "");
		}
		if (!Util.isEmpty(info.get("firstname"))) {
			NameInfo.put("surnames_cn", info.get("firstname"));
		}
		if (!Util.isEmpty(info.get("firstnameen"))) {
			NameInfo.put("surnames_en", info.get("firstnameen"));
		}
		if (!Util.isEmpty(info.get("lastname"))) {
			NameInfo.put("given_names_cn", info.get("lastname"));
		}
		if (!Util.isEmpty(info.get("lastnameen"))) {
			NameInfo.put("given_names_en", info.get("lastnameen"));
		}
		if (!Util.isEmpty(info.get("otherfirstname"))) {
			NameInfo.put("old_surnames_cn", info.get("otherfirstname"));
		} else {
			NameInfo.put("old_surnames_cn", "");
		}
		if (!Util.isEmpty(info.get("otherfirstnameen"))) {
			NameInfo.put("old_surnames_en", info.get("otherfirstnameen"));
		} else {
			NameInfo.put("old_surnames_en", "");
		}
		if (!Util.isEmpty(info.get("otherlastname"))) {
			NameInfo.put("old_given_names_cn", info.get("otherlastname"));
		} else {
			NameInfo.put("old_given_names_cn", "");
		}
		if (!Util.isEmpty(info.get("otherlastnameen"))) {
			NameInfo.put("old_given_names_en", info.get("otherlastnameen"));
		} else {
			NameInfo.put("old_given_names_en", "");
		}

		BaseInfo.put("NameInfo", NameInfo);

		//BirthplaceInfo
		Map<String, Object> BirthplaceInfo = Maps.newHashMap();
		if (!Util.isEmpty(info.get("cardcity"))) {
			BirthplaceInfo.put("city", info.get("cardcity"));
		}
		if (!Util.isEmpty(info.get("cardprovince"))) {
			BirthplaceInfo.put("province", info.get("cardprovince"));
		}
		BirthplaceInfo.put("country", "CHN");

		BaseInfo.put("BirthplaceInfo", BirthplaceInfo);

		InforMation.put("BaseInfo", BaseInfo);

		//FamilyInfo
		Map<String, Object> FamilyInfo = Maps.newHashMap();
		//备用电话  不清楚

		//AddressInfo
		Map<String, Object> AddressInfo = Maps.newHashMap();
		if (!Util.isEmpty(info.get("address"))) {
			AddressInfo.put("street", info.get("address"));
		}
		if (!Util.isEmpty(info.get("city"))) {
			AddressInfo.put("city", info.get("city"));
		}
		if (!Util.isEmpty(info.get("province"))) {
			AddressInfo.put("province", info.get("province"));
		}
		AddressInfo.put("country", "CHN");
		//邮政编码  没有
		FamilyInfo.put("AdderssInfo", AddressInfo);

		//FatherInfo
		Map<String, Object> FatherInfo = Maps.newHashMap();

		//NameInfo
		Map<String, Object> FatherNameInfo = Maps.newHashMap();
		if (!Util.isEmpty(info.get("fatherfirstname"))) {
			FatherNameInfo.put("surnames_cn", info.get("fatherfirstname"));
		} else {
			FatherNameInfo.put("surnames_cn", "");
		}
		if (!Util.isEmpty(info.get("fatherfirstnameen"))) {
			FatherNameInfo.put("surnames_en", info.get("fatherfirstnameen"));
		} else {
			FatherNameInfo.put("surnames_en", "");
		}
		if (!Util.isEmpty(info.get("fatherlastname"))) {
			FatherNameInfo.put("given_names_cn", info.get("fatherlastname"));
		} else {
			FatherNameInfo.put("given_names_cn", "");
		}
		if (!Util.isEmpty(info.get("fatherlastnameen"))) {
			FatherNameInfo.put("given_names_en", info.get("fatherlastnameen"));
		} else {
			FatherNameInfo.put("given_names_en", "");
		}

		FatherInfo.put("NameInfo", FatherNameInfo);

		if (!Util.isEmpty(info.get("fatherbirthday"))) {
			FatherInfo.put("date_of_birth", info.get("fatherbirthday"));
		} else {
			FatherInfo.put("date_of_birth", "");
		}

		FamilyInfo.put("FatherInfo", FatherInfo);

		//MotherInfo
		Map<String, Object> MotherInfo = Maps.newHashMap();

		//NameInfo
		Map<String, Object> MotherNameInfo = Maps.newHashMap();
		if (!Util.isEmpty(info.get("motherfirstname"))) {
			MotherNameInfo.put("surnames_cn", info.get("motherfirstname"));
		} else {
			MotherNameInfo.put("surnames_cn", "");
		}
		if (!Util.isEmpty(info.get("motherfirstnameen"))) {
			MotherNameInfo.put("surnames_en", info.get("motherfirstnameen"));
		} else {
			MotherNameInfo.put("surnames_en", "");
		}
		if (!Util.isEmpty(info.get("motherlastname"))) {
			MotherNameInfo.put("given_names_cn", info.get("motherlastname"));
		} else {
			MotherNameInfo.put("given_names_cn", "");
		}
		if (!Util.isEmpty(info.get("motherlastnameen"))) {
			MotherNameInfo.put("given_names_en", info.get("motherlastnameen"));
		} else {
			MotherNameInfo.put("given_names_en", "");
		}

		MotherInfo.put("NameInfo", MotherNameInfo);

		if (!Util.isEmpty(info.get("motherbirthday"))) {
			MotherInfo.put("date_of_birth", info.get("motherbirthday"));
		} else {
			MotherInfo.put("date_of_birth", "");
		}

		FamilyInfo.put("MotherInfo", MotherInfo);

		//SpouseInfo
		Map<String, Object> SpouseInfo = Maps.newHashMap();

		//NameInfo
		Map<String, Object> SpouseNameInfo = Maps.newHashMap();
		if (!Util.isEmpty(info.get("spousefirstname"))) {
			SpouseNameInfo.put("surnames_cn", info.get("spousefirstname"));
		}
		if (!Util.isEmpty(info.get("spousefirstnameen"))) {
			SpouseNameInfo.put("surnames_en", info.get("spousefirstnameen"));
		}
		if (!Util.isEmpty(info.get("spouselastname"))) {
			SpouseNameInfo.put("given_names_cn", info.get("spouselastname"));
		}
		if (!Util.isEmpty(info.get("spouselastnameen"))) {
			SpouseNameInfo.put("given_names_en", info.get("spouselastnameen"));
		}

		SpouseInfo.put("NameInfo", SpouseNameInfo);

		//BirthplaceInfo
		Map<String, Object> SpouseBirthplaceInfo = Maps.newHashMap();
		if (!Util.isEmpty(info.get("spousecity"))) {
			SpouseBirthplaceInfo.put("city", info.get("spousecity"));
		} else {
			SpouseBirthplaceInfo.put("city", "");
		}
		//省份  没有
		if (!Util.isEmpty(info.get("spousecountry"))) {
			SpouseBirthplaceInfo.put("country", info.get("spousecountry"));
		} else {
			SpouseBirthplaceInfo.put("country", "");
		}

		SpouseInfo.put("BirthplaceInfo", SpouseBirthplaceInfo);

		if (!Util.isEmpty(info.get("spousebirthday"))) {
			SpouseInfo.put("date_of_birth", info.get("spousebirthday"));
		}
		if (!Util.isEmpty(info.get("spousenationality"))) {
			SpouseInfo.put("nationality", info.get("spousenationality"));
		}
		//查询前妻数据
		TAppStaffFormerspouseEntity formerspouse = dbDao.fetch(TAppStaffFormerspouseEntity.class,
				Cnd.where("staffid", "=", staffid));
		if (!Util.isEmpty(formerspouse) && !Util.isEmpty(formerspouse.getMarrieddate())) {
			SpouseInfo.put("start_date", formerspouse.getMarrieddate());
		} else {
			SpouseInfo.put("start_date", "");
		}
		if (!Util.isEmpty(formerspouse) && !Util.isEmpty(formerspouse.getDivorcedate())) {
			SpouseInfo.put("end_date", formerspouse.getDivorcedate());
		} else {
			SpouseInfo.put("end_date", "");
		}
		if (!Util.isEmpty(formerspouse) && !Util.isEmpty(formerspouse.getDivorce())) {
			SpouseInfo.put("divorced_country", formerspouse.getDivorce());
		}
		if (!Util.isEmpty(formerspouse) && !Util.isEmpty(formerspouse.getDivorceexplain())) {
			SpouseInfo.put("divorced_reason", formerspouse.getDivorceexplain());
		}
		if (!Util.isEmpty(info.get("spouseaddress,"))) {
			SpouseInfo.put("address_type", info.get("tasf.spouseaddress,"));
		}

		//BirthplaceInfo
		Map<String, Object> SpouseAddressInfo = Maps.newHashMap();
		if (!Util.isEmpty(info.get("firstaddress"))) {
			SpouseAddressInfo.put("street", info.get("firstaddress"));
		}
		if (!Util.isEmpty(info.get("city"))) {
			SpouseAddressInfo.put("city", info.get("city"));
		}
		if (!Util.isEmpty(info.get("province"))) {
			SpouseAddressInfo.put("province", info.get("province"));
		}
		if (!Util.isEmpty(info.get("country"))) {
			SpouseAddressInfo.put("country", info.get("country"));
		}
		if (!Util.isEmpty(info.get("zipcode"))) {
			SpouseAddressInfo.put("zip_code", info.get("zipcode"));
		}

		SpouseInfo.put("AdderssInfo", SpouseAddressInfo);

		FamilyInfo.put("SpouseInfo", SpouseInfo);

		if (!Util.isEmpty(info.get("telecodelastname"))) {
			NameInfo.put("given_names_code_cn", info.get("telecodelastname"));
		} else {
			NameInfo.put("given_names_code_cn", "");
		}
		if (!Util.isEmpty(info.get("telecodelastname"))) {
			NameInfo.put("given_names_code_cn", info.get("telecodelastname"));
		} else {
			NameInfo.put("given_names_code_cn", "");
		}
		if (!Util.isEmpty(info.get("telecodelastname"))) {
			NameInfo.put("given_names_code_cn", info.get("telecodelastname"));
		} else {
			NameInfo.put("given_names_code_cn", "");
		}
		if (!Util.isEmpty(info.get("telecodelastname"))) {
			NameInfo.put("given_names_code_cn", info.get("telecodelastname"));
		} else {
			NameInfo.put("given_names_code_cn", "");
		}
		if (!Util.isEmpty(info.get("telecodelastname"))) {
			NameInfo.put("given_names_code_cn", info.get("telecodelastname"));
		} else {
			NameInfo.put("given_names_code_cn", "");
		}
		if (!Util.isEmpty(info.get("telecodelastname"))) {
			NameInfo.put("given_names_code_cn", info.get("telecodelastname"));
		} else {
			NameInfo.put("given_names_code_cn", "");
		}
		if (!Util.isEmpty(info.get("telecodelastname"))) {
			NameInfo.put("given_names_code_cn", info.get("telecodelastname"));
		} else {
			NameInfo.put("given_names_code_cn", "");
		}
		if (!Util.isEmpty(info.get("telecodelastname"))) {
			NameInfo.put("given_names_code_cn", info.get("telecodelastname"));
		} else {
			NameInfo.put("given_names_code_cn", "");
		}
		if (!Util.isEmpty(info.get("telecodelastname"))) {
			NameInfo.put("given_names_code_cn", info.get("telecodelastname"));
		} else {
			NameInfo.put("given_names_code_cn", "");
		}
		if (!Util.isEmpty(info.get("telecodelastname"))) {
			NameInfo.put("given_names_code_cn", info.get("telecodelastname"));
		} else {
			NameInfo.put("given_names_code_cn", "");
		}
		if (!Util.isEmpty(info.get("telecodelastname"))) {
			NameInfo.put("given_names_code_cn", info.get("telecodelastname"));
		} else {
			NameInfo.put("given_names_code_cn", "");
		}
		if (!Util.isEmpty(info.get("telecodelastname"))) {
			NameInfo.put("given_names_code_cn", info.get("telecodelastname"));
		} else {
			NameInfo.put("given_names_code_cn", "");
		}
		if (!Util.isEmpty(info.get("telecodelastname"))) {
			NameInfo.put("given_names_code_cn", info.get("telecodelastname"));
		} else {
			NameInfo.put("given_names_code_cn", "");
		}

		return result;
	}
}
