/**
 * AutofillService.java
 * com.juyo.visa.admin.orderUS.service
 * Copyright (c) 2018, 北京科技有限公司版权所有.
 */

package com.juyo.visa.admin.orderUS.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.juyo.visa.entities.TAppStaffBeforeeducationEntity;
import com.juyo.visa.entities.TAppStaffBeforeworkEntity;
import com.juyo.visa.entities.TAppStaffCompanioninfoEntity;
import com.juyo.visa.entities.TAppStaffDriverinfoEntity;
import com.juyo.visa.entities.TAppStaffFormerspouseEntity;
import com.juyo.visa.entities.TAppStaffGocountryEntity;
import com.juyo.visa.entities.TAppStaffGousinfoEntity;
import com.juyo.visa.entities.TAppStaffImmediaterelativesEntity;
import com.juyo.visa.entities.TAppStaffLanguageEntity;
import com.juyo.visa.entities.TAppStaffOrganizationEntity;
import com.juyo.visa.entities.TAppStaffWorkEducationTrainingEntity;
import com.juyo.visa.entities.TCityEntity;
import com.juyo.visa.entities.TCountryRegionEntity;
import com.juyo.visa.entities.TOrderUsEntity;
import com.juyo.visa.entities.TStateUsEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class AutofillService extends BaseService<TOrderUsEntity> {

	public Map<String, Object> getData(int orderid, int staffid) {
		//最终接收数据Map(包括所需数据和错误信息)
		Map<String, Object> result = Maps.newHashMap();
		//所需数据Map
		Map<String, Object> resultData = Maps.newHashMap();
		//错误信息
		String errorMsg = "";
		//格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
		//性别，从护照信息里取
		if (!Util.isEmpty(info.get("taspsex"))) {
			if (Util.eq("男", info.get("taspsex"))) {
				BaseInfo.put("sex", 0);
			} else {
				BaseInfo.put("sex", 1);
			}
		}
		//出生日期，从护照信息里取
		//计算年龄，未满14周岁的不需要面签，不用具体离开美国的日期
		String date2String = sdf.format(new Date());
		int yearsBetween = 0;

		if (!Util.isEmpty(info.get("taspbirthday"))) {
			BaseInfo.put("date_of_birth", sdf.format(info.get("taspbirthday")));
			yearsBetween = DateUtil.yearsBetween(sdf.format(info.get("taspbirthday")), date2String);
		} else {
			errorMsg += "护照出生日期,";
		}
		System.out.println("yearsBetween:" + yearsBetween);
		//国籍，默认为中国
		//BaseInfo.put("nationality", "CHIN");
		if (!Util.isEmpty(info.get("tasbnationality"))) {
			BaseInfo.put("nationality", getCountrycode((String) info.get("tasbnationality")));
		} else {
			BaseInfo.put("nationality", "CHIN");
		}

		//身份证号
		if (!Util.isEmpty(info.get("cardId"))) {
			BaseInfo.put("ic", info.get("cardId"));
		} else {
			errorMsg += "身份证号,";
		}
		//手机号(基本信息)
		if (!Util.isEmpty(info.get("tasbtelephone"))) {
			BaseInfo.put("phone", info.get("tasbtelephone"));
		} else {
			errorMsg += "手机号,";
		}
		//邮箱(基本信息)
		if (!Util.isEmpty(info.get("tasbemail"))) {
			BaseInfo.put("email", info.get("tasbemail"));
		} else {
			errorMsg += "电子邮箱,";
		}
		//婚姻状况(基本信息)，数据根据接口所需来处理成对应String
		if (!Util.isEmpty(info.get("marrystatus"))) {
			int status = (int) info.get("marrystatus");
			if (status == 1) {
				BaseInfo.put("Marriage", "M");
			}
			if (status == 2) {
				BaseInfo.put("Marriage", "D");
			}
			if (status == 3) {
				BaseInfo.put("Marriage", "W");
			}
			if (status == 4) {
				BaseInfo.put("Marriage", "S");
			}
			/*for (MarryStatusEnum marrystatus : MarryStatusEnum.values()) {
				if (status == marrystatus.intKey()) {
					BaseInfo.put("Marriage", marrystatus.value());
				}
			}*/
		} else {
			errorMsg += "婚姻状况,";
		}

		//NameInfo
		Map<String, Object> NameInfo = Maps.newHashMap();
		//电子姓(基本信息)
		if (!Util.isEmpty(info.get("telecodefirstname"))) {
			NameInfo.put("surnames_code_cn", info.get("telecodefirstname"));
		} else {
			NameInfo.put("surnames_code_cn", "");
		}
		//电子名(基本信息)
		if (!Util.isEmpty(info.get("telecodelastname"))) {
			NameInfo.put("given_names_code_cn", info.get("telecodelastname"));
		} else {
			NameInfo.put("given_names_code_cn", "");
		}
		//姓(基本信息)
		if (!Util.isEmpty(info.get("tasbfirstname"))) {
			NameInfo.put("surnames_cn", info.get("tasbfirstname"));
		} else {
			errorMsg += "姓中文,";
		}
		//姓 拼音(基本信息)
		if (!Util.isEmpty(info.get("tasbfirstnameen"))) {
			NameInfo.put("surnames_en", info.get("tasbfirstnameen"));
		} else {
			errorMsg += "姓英文,";
		}
		//名(基本信息)
		if (!Util.isEmpty(info.get("tasblastname"))) {
			NameInfo.put("given_names_cn", info.get("tasblastname"));
		} else {
			errorMsg += "名中文";
		}
		//名 拼音(基本信息)
		if (!Util.isEmpty(info.get("tasblastnameen"))) {
			NameInfo.put("given_names_en", info.get("tasblastnameen"));
		} else {
			errorMsg += "名英文";
		}
		//曾用姓(基本信息)
		if (!Util.isEmpty(info.get("otherfirstname"))) {
			NameInfo.put("old_surnames_cn", info.get("otherfirstname"));
		} else {
			NameInfo.put("old_surnames_cn", "");
		}
		//曾用姓 拼音(基本信息)
		if (!Util.isEmpty(info.get("otherfirstnameen"))) {
			NameInfo.put("old_surnames_en", info.get("otherfirstnameen"));
		} else {
			NameInfo.put("old_surnames_en", "");
		}
		//曾用名(基本信息)
		if (!Util.isEmpty(info.get("otherlastname"))) {
			NameInfo.put("old_given_names_cn", info.get("otherlastname"));
		} else {
			NameInfo.put("old_given_names_cn", "");
		}
		//曾用名 拼音(基本信息)
		if (!Util.isEmpty(info.get("otherlastnameen"))) {
			NameInfo.put("old_given_names_en", info.get("otherlastnameen"));
		} else {
			NameInfo.put("old_given_names_en", "");
		}

		BaseInfo.put("NameInfo", NameInfo);

		//BirthplaceInfo
		Map<String, Object> BirthplaceInfo = Maps.newHashMap();
		//出生城市(基本信息)
		if (!Util.isEmpty(info.get("cardcityen"))) {
			BirthplaceInfo.put("city", info.get("cardcityen"));
		} else {
			errorMsg += "出生城市,";
		}
		//出生省份(基本信息)
		if (!Util.isEmpty(info.get("cardprovinceen"))) {
			BirthplaceInfo.put("province", info.get("cardprovinceen"));
		} else {
			errorMsg += "出生省份,";
		}
		//出生国家,默认为中国
		if (!Util.isEmpty(info.get("birthcountry"))) {
			BirthplaceInfo.put("country", getCountrycode((String) info.get("birthcountry")));
		} else {
			//errorMsg += "出生国家,";
			BirthplaceInfo.put("country", "CHIN");
		}
		//BirthplaceInfo.put("country", "CHIN");

		BaseInfo.put("BirthplaceInfo", BirthplaceInfo);

		InforMation.put("BaseInfo", BaseInfo);

		//FamilyInfo
		Map<String, Object> FamilyInfo = Maps.newHashMap();
		//备用电话  不清楚  测试为空
		FamilyInfo.put("family_phone", "");
		//AddressInfo
		Map<String, Object> AddressInfo = Maps.newHashMap();
		//详细地址(基本信息)
		if (!Util.isEmpty(info.get("detailedaddressen"))) {
			AddressInfo.put("street", info.get("detailedaddressen"));
		} else {
			errorMsg += "街道,";
		}
		//现居住城市(基本信息)
		if (!Util.isEmpty(info.get("tasbcityen"))) {
			AddressInfo.put("city", info.get("tasbcityen"));
		} else {
			errorMsg += "城市,";
		}
		//现居住省份(基本信息)
		if (!Util.isEmpty(info.get("tasbprovinceen"))) {
			AddressInfo.put("province", info.get("tasbprovinceen"));
		} else {
			errorMsg += "省份,";
		}
		//现居住国家，默认中国
		if (!Util.isEmpty(info.get("nowcountry"))) {
			AddressInfo.put("country", getCountrycode((String) info.get("nowcountry")));
		} else {
			AddressInfo.put("country", "CHIN");
		}
		//邮政编码  没有  测试为空
		AddressInfo.put("zip_code", "");
		FamilyInfo.put("AdderssInfo", AddressInfo);

		//FatherInfo
		Map<String, Object> FatherInfo = Maps.newHashMap();

		//NameInfo
		Map<String, Object> FatherNameInfo = Maps.newHashMap();
		//父亲的姓(签证信息)
		if (!Util.isEmpty(info.get("fatherfirstname"))) {
			FatherNameInfo.put("surnames_cn", info.get("fatherfirstname"));
		} else {
			FatherNameInfo.put("surnames_cn", "");
		}
		//父亲的姓 拼音(签证信息)
		if (!Util.isEmpty(info.get("fatherfirstnameen"))) {
			FatherNameInfo.put("surnames_en", info.get("fatherfirstnameen"));
		} else {
			FatherNameInfo.put("surnames_en", "");
		}
		//父亲的名(签证信息)
		if (!Util.isEmpty(info.get("fatherlastname"))) {
			FatherNameInfo.put("given_names_cn", info.get("fatherlastname"));
		} else {
			FatherNameInfo.put("given_names_cn", "");
		}
		//父亲的名 拼音(签证信息)
		if (!Util.isEmpty(info.get("fatherlastnameen"))) {
			FatherNameInfo.put("given_names_en", info.get("fatherlastnameen"));
		} else {
			FatherNameInfo.put("given_names_en", "");
		}

		FatherInfo.put("NameInfo", FatherNameInfo);

		//父亲的出生日期(签证信息)
		if (!Util.isEmpty(info.get("fatherbirthday"))) {
			FatherInfo.put("date_of_birth", sdf.format(info.get("fatherbirthday")));
		} else {
			FatherInfo.put("date_of_birth", "");
		}

		FamilyInfo.put("FatherInfo", FatherInfo);

		//MotherInfo
		Map<String, Object> MotherInfo = Maps.newHashMap();

		//NameInfo
		Map<String, Object> MotherNameInfo = Maps.newHashMap();
		//母亲的姓(签证信息)
		if (!Util.isEmpty(info.get("motherfirstname"))) {
			MotherNameInfo.put("surnames_cn", info.get("motherfirstname"));
		} else {
			MotherNameInfo.put("surnames_cn", "");
		}
		//母亲的姓 拼音(签证信息)
		if (!Util.isEmpty(info.get("motherfirstnameen"))) {
			MotherNameInfo.put("surnames_en", info.get("motherfirstnameen"));
		} else {
			MotherNameInfo.put("surnames_en", "");
		}
		//母亲的名(签证信息)
		if (!Util.isEmpty(info.get("motherlastname"))) {
			MotherNameInfo.put("given_names_cn", info.get("motherlastname"));
		} else {
			MotherNameInfo.put("given_names_cn", "");
		}
		//母亲地名 拼音(签证信息)
		if (!Util.isEmpty(info.get("motherlastnameen"))) {
			MotherNameInfo.put("given_names_en", info.get("motherlastnameen"));
		} else {
			MotherNameInfo.put("given_names_en", "");
		}

		MotherInfo.put("NameInfo", MotherNameInfo);

		//母亲的生日(签证信息)
		if (!Util.isEmpty(info.get("motherbirthday"))) {
			MotherInfo.put("date_of_birth", sdf.format(info.get("motherbirthday")));
		} else {
			MotherInfo.put("date_of_birth", "");
		}

		FamilyInfo.put("MotherInfo", MotherInfo);

		//SpouseInfo
		Map<String, Object> SpouseInfo = Maps.newHashMap();

		//NameInfo
		Map<String, Object> SpouseNameInfo = Maps.newHashMap();
		//配偶的姓(签证信息)
		if (!Util.isEmpty(info.get("spousefirstname"))) {
			SpouseNameInfo.put("surnames_cn", info.get("spousefirstname"));
		} else {
			errorMsg += "配偶姓中文,";
		}
		//配偶的姓 拼音(签证信息)
		if (!Util.isEmpty(info.get("spousefirstnameen"))) {
			SpouseNameInfo.put("surnames_en", info.get("spousefirstnameen"));
		} else {
			errorMsg += "配偶姓英文,";
		}
		//配偶的名(签证信息)
		if (!Util.isEmpty(info.get("spouselastname"))) {
			SpouseNameInfo.put("given_names_cn", info.get("spouselastname"));
		} else {
			errorMsg += "配偶名中文,";
		}
		//配偶的名 拼音(签证信息)
		if (!Util.isEmpty(info.get("spouselastnameen"))) {
			SpouseNameInfo.put("given_names_en", info.get("spouselastnameen"));
		} else {
			errorMsg += "配偶名英文,";
		}

		SpouseInfo.put("NameInfo", SpouseNameInfo);

		//BirthplaceInfo
		Map<String, Object> SpouseBirthplaceInfo = Maps.newHashMap();

		//配偶的出生城市(签证信息)
		if (!Util.isEmpty(info.get("spousecityen"))) {
			SpouseBirthplaceInfo.put("city", info.get("spousecityen"));
		} else {
			//SpouseBirthplaceInfo.put("city", "");
			errorMsg += "配偶出生省份,";
		}
		//省份  没有
		SpouseBirthplaceInfo.put("province", "");
		//配偶的出生国家(签证信息)
		if (!Util.isEmpty(info.get("spousecountry"))) {
			String country = getCountry((int) info.get("spousecountry"));
			SpouseBirthplaceInfo.put("country", country);
		} else {
			errorMsg += "配偶出生国家,";
		}

		SpouseInfo.put("BirthplaceInfo", SpouseBirthplaceInfo);

		//配偶的出生日期(签证信息)
		if (!Util.isEmpty(info.get("spousebirthday"))) {
			SpouseInfo.put("date_of_birth", sdf.format(info.get("spousebirthday")));
		} else {
			errorMsg += "配偶出生日期,";
		}
		//配偶的国籍(签证信息)
		if (!Util.isEmpty(info.get("spousenationality"))) {
			String country = getCountry((int) info.get("spousenationality"));
			SpouseInfo.put("nationality", country);
		} else {
			errorMsg += "配偶国籍,";
		}
		//查询前妻数据
		TAppStaffFormerspouseEntity formerspouse = dbDao.fetch(TAppStaffFormerspouseEntity.class,
				Cnd.where("staffid", "=", staffid));
		//离异时，需要结婚和离婚日期
		if (info.getInt("marrystatus") == 2) {
			//结婚日期(家庭信息)
			if (!Util.isEmpty(info.get("marrieddate"))) {
				SpouseInfo.put("start_date", sdf.format(info.get("marrieddate")));
			} else {
				errorMsg += "结婚日期,";
			}
			//离婚日期(家庭信息)
			if (!Util.isEmpty(info.get("divorcedate"))) {
				SpouseInfo.put("end_date", sdf.format(info.get("divorcedate")));
			} else {
				errorMsg += "离婚日期,";
			}
		}
		//SpouseInfo.put("start_date", "");
		/*if (!Util.isEmpty(formerspouse) && !Util.isEmpty(formerspouse.getMarrieddate())) {
			SpouseInfo.put("start_date", sdf.format(formerspouse.getMarrieddate()));
		} else {
			SpouseInfo.put("start_date", "");
		}*/
		//离婚日期(签证信息)
		//SpouseInfo.put("end_date", "");
		/*if (!Util.isEmpty(formerspouse) && !Util.isEmpty(formerspouse.getDivorcedate())) {
			SpouseInfo.put("end_date", sdf.format(formerspouse.getDivorcedate()));
		} else {
			SpouseInfo.put("end_date", "");
		}*/

		//离婚国家(家庭信息)
		if (!Util.isEmpty(info.get("divorcecountry"))) {
			SpouseInfo.put("divorced_country", getCountrycode((String) info.get("divorcecountry")));
		} else {
			SpouseInfo.put("divorced_country", "CHIN");
		}

		//SpouseInfo.put("divorced_country", "CHIN");
		/*if (!Util.isEmpty(formerspouse) && !Util.isEmpty(formerspouse.getDivorce())) {
			SpouseInfo.put("divorced_country", formerspouse.getDivorce());
		} else {
			errorMsg += "离异国家,";
		}*/

		//离婚原因(家庭信息)
		SpouseInfo.put("divorced_reason", "Incompatibility of temperament");
		/*if (!Util.isEmpty(formerspouse) && !Util.isEmpty(formerspouse.getDivorceexplain())) {
			SpouseInfo.put("divorced_reason", formerspouse.getDivorceexplain());
		} else {
			errorMsg += "离异原因,";
		}*/
		//配偶的联系地址(家庭信息)
		if (!Util.isEmpty(info.get("spouseaddressen")) && !Util.eq(0, info.get("spouseaddressen"))) {
			int spouseAddress = (int) info.get("spouseaddress");
			if (spouseAddress == 1) {
				SpouseInfo.put("address_type", "H");
			}
			if (spouseAddress == 2) {
				SpouseInfo.put("address_type", "M");
			}
			if (spouseAddress == 3) {
				SpouseInfo.put("address_type", "C");
			}
			if (spouseAddress == 4) {
				SpouseInfo.put("address_type", "D");
			}
			if (spouseAddress == 5) {
				SpouseInfo.put("address_type", "O");
			}
		} else {
			errorMsg += "配偶的联系地址,";
		}

		//BirthplaceInfo
		Map<String, Object> SpouseAddressInfo = Maps.newHashMap();
		//街道(签证信息)

		if (!Util.isEmpty(info.get("detailedaddressen"))) {
			SpouseAddressInfo.put("street", info.get("detailedaddressen"));
		} else {
			errorMsg += "街道,";
		}
		//现居住城市(基本信息)
		if (!Util.isEmpty(info.get("tasbcityen"))) {
			SpouseAddressInfo.put("city", info.get("tasbcityen"));
		} else {
			errorMsg += "城市,";
		}
		//现居住省份(基本信息)
		if (!Util.isEmpty(info.get("tasbprovinceen"))) {
			SpouseAddressInfo.put("province", info.get("tasbprovinceen"));
		} else {
			errorMsg += "省份,";
		}
		SpouseAddressInfo.put("country", "CHIN");
		//邮政编码  没有
		SpouseAddressInfo.put("zip_code", "");

		//SpouseAddressInfo.put("street", "");
		/*if (!Util.isEmpty(info.get("firstaddress"))) {
			SpouseAddressInfo.put("street", info.get("firstaddress"));
		} else {
			errorMsg += "街道,";
		}*/
		//城市(签证信息)
		//SpouseAddressInfo.put("city", "");
		/*if (!Util.isEmpty(info.get("tasfcity"))) {
			SpouseAddressInfo.put("city", info.get("tasfcity"));
		} else {
			errorMsg += "城市,";
		}*/
		//省份(签证信息)
		//SpouseAddressInfo.put("province", "");
		/*if (!Util.isEmpty(info.get("tasfprovince"))) {
			SpouseAddressInfo.put("province", info.get("tasfprovince"));
		} else {
			errorMsg += "省份,";
		}*/
		//国家(签证信息)
		//SpouseAddressInfo.put("country", "");
		/*if (!Util.isEmpty(info.get("tasfcountry")) && !Util.eq(0, info.get("tasfcountry"))) {
			String country = getCountry((int) info.get("tasfcountry"));
			SpouseAddressInfo.put("country", country);
		} else {
			errorMsg += "国家,";
		}*/
		//邮编(签证信息)
		//SpouseAddressInfo.put("zip_code", "");
		/*if (!Util.isEmpty(info.get("tasfzipcode"))) {
			SpouseAddressInfo.put("zip_code", info.get("tasfzipcode"));
		} else {
			errorMsg += "邮编,";
		}*/

		SpouseInfo.put("AdderssInfo", SpouseAddressInfo);

		FamilyInfo.put("SpouseInfo", SpouseInfo);

		InforMation.put("FamilyInfo", FamilyInfo);

		//WorkEducation
		Map<String, Object> WorkEducation = Maps.newHashMap();

		//职业(签证信息)
		if (!Util.isEmpty(info.get("occupation")) && !Util.eq(0, info.get("occupation"))) {
			int status = (int) info.get("occupation");
			WorkEducation.put("current_status", getOccupation(status));
		} else {
			errorMsg += "主要职业,";
		}
		//请说明  没有
		WorkEducation.put("describe", "SAGG");
		//Works
		ArrayList<Object> works = new ArrayList<>();

		Map<String, Object> nowWork = Maps.newHashMap();
		TAppStaffWorkEducationTrainingEntity nowWorkinfo = dbDao.fetch(TAppStaffWorkEducationTrainingEntity.class,
				Cnd.where("staffid", "=", staffid));

		//单位名称(签证信息)
		if (!Util.isEmpty(nowWorkinfo.getUnitname())) {
			nowWork.put("unit_name_cn", nowWorkinfo.getUnitname());
		} else {
			errorMsg += "现工作单位名称中文,";
		}
		//单位名称 拼音(签证信息)
		if (!Util.isEmpty(nowWorkinfo.getUnitnameen())) {
			nowWork.put("unit_name_en", nowWorkinfo.getUnitnameen());
		} else {
			errorMsg += "现工作单位名称英文,";
		}
		//单位电话(签证信息)
		if (!Util.isEmpty(nowWorkinfo.getTelephone())) {
			nowWork.put("phone", nowWorkinfo.getTelephone());
		} else {
			errorMsg += "现工作单位电话,";
		}
		//职务(签证信息)
		if (!Util.isEmpty(nowWorkinfo.getPositionen())) {
			nowWork.put("job_title", nowWorkinfo.getPositionen());
		} else {
			errorMsg += "现工作职务名称,";
		}
		//职责(签证信息)
		if (!Util.isEmpty(nowWorkinfo.getDutyen())) {
			nowWork.put("job_description", nowWorkinfo.getDutyen());
		} else {
			errorMsg += "现工作职务描述,";
		}
		//月收入 
		if (!Util.isEmpty(nowWorkinfo.getSalary())) {
			nowWork.put("monthly_income", nowWorkinfo.getSalary().intValue());
		} else {
			errorMsg += "现工作月收入,";
		}
		//入职时间(签证信息)
		if (!Util.isEmpty(nowWorkinfo.getWorkstartdate())) {
			nowWork.put("start_date", sdf.format(nowWorkinfo.getWorkstartdate()));
		} else {
			nowWork.put("start_date", "");
		}
		nowWork.put("end_date", "");

		//AddressInfo
		Map<String, Object> nowaddressinfo = Maps.newHashMap();
		//雇主街道(签证信息)
		if (!Util.isEmpty(nowWorkinfo.getAddressen())) {
			nowaddressinfo.put("street", nowWorkinfo.getAddressen());
		} else {
			errorMsg += "现工作街道,";
		}
		//雇主城市(签证信息)
		if (!Util.isEmpty(nowWorkinfo.getCityen())) {
			nowaddressinfo.put("city", nowWorkinfo.getCityen());
		} else {
			errorMsg += "现工作城市,";
		}
		//雇主省份(签证信息)
		if (!Util.isEmpty(nowWorkinfo.getProvinceen())) {
			nowaddressinfo.put("province", nowWorkinfo.getProvinceen());
		} else {
			errorMsg += "现工作省份,";
		}
		//雇主国家(签证信息)
		if (!Util.isEmpty(nowWorkinfo.getCountry())) {
			String country = getCountry(nowWorkinfo.getCountry());
			nowaddressinfo.put("country", country);
		} else {
			errorMsg += "现工作国家,";
		}
		//雇主邮政编码(签证信息)
		if (!Util.isEmpty(nowWorkinfo.getZipcode())) {
			nowaddressinfo.put("zip_code", nowWorkinfo.getZipcode());
		} else {
			nowaddressinfo.put("zip_code", "");
		}

		nowWork.put("AdderssInfo", nowaddressinfo);

		//directorsname
		Map<String, Object> nowdirectorsname = Maps.newHashMap();

		nowdirectorsname.put("surnames_cn", "");
		nowdirectorsname.put("surnames_en", "");
		nowdirectorsname.put("given_names_cn", "");
		nowdirectorsname.put("given_names_en", "");

		nowWork.put("DirectorsName", nowdirectorsname);

		works.add(nowWork);

		Map<String, Object> work = Maps.newHashMap();
		Map<String, Object> preaddressinfo = Maps.newHashMap();
		Map<String, Object> directorsname = Maps.newHashMap();
		if (info.getInt("isemployed") == 1) {
			//以前的工作
			TAppStaffBeforeworkEntity workEntity = dbDao.fetch(TAppStaffBeforeworkEntity.class,
					Cnd.where("staffid", "=", staffid));
			//单位名称(签证信息)
			if (!Util.isEmpty(workEntity.getEmployername())) {
				work.put("unit_name_cn", workEntity.getEmployername());
			} else {
				errorMsg += "单位名称中文,";
			}
			//单位名称 拼音(签证信息)
			if (!Util.isEmpty(workEntity.getEmployernameen())) {
				work.put("unit_name_en", workEntity.getEmployernameen());
			} else {
				errorMsg += "单位名称英文,";
			}
			//单位电话(签证信息)
			if (!Util.isEmpty(workEntity.getEmployertelephone())) {
				work.put("phone", workEntity.getEmployertelephone());
			} else {
				errorMsg += "单位电话,";
			}
			//职务(签证信息)
			if (!Util.isEmpty(workEntity.getJobtitleen())) {
				work.put("job_title", workEntity.getJobtitleen());
			} else {
				errorMsg += "职务名称,";
			}
			//职责(签证信息)
			if (!Util.isEmpty(workEntity.getPreviousdutyen())) {
				work.put("job_description", workEntity.getPreviousdutyen());
			} else {
				errorMsg += "职务描述,";
			}
			//月收入 没有
			work.put("monthly_income", "20000");
			//入职时间(签证信息)
			if (!Util.isEmpty(workEntity.getEmploystartdate())) {
				work.put("start_date", sdf.format(workEntity.getEmploystartdate()));
			} else {
				work.put("start_date", "");
			}
			//离职时间(签证信息)
			if (!Util.isEmpty(workEntity.getEmployenddate())) {
				work.put("end_date", sdf.format(workEntity.getEmployenddate()));
			} else {
				work.put("end_date", "");
			}

			//AddressInfo

			//雇主街道(签证信息)
			if (!Util.isEmpty(workEntity.getEmployeraddressen())) {
				preaddressinfo.put("street", workEntity.getEmployeraddressen());
			} else {
				errorMsg += "街道,";
			}
			//雇主城市(签证信息)
			if (!Util.isEmpty(workEntity.getEmployercityen())) {
				preaddressinfo.put("city", workEntity.getEmployercityen());
			} else {
				errorMsg += "城市,";
			}
			//雇主省份(签证信息)
			if (!Util.isEmpty(workEntity.getEmployerprovinceen())) {
				preaddressinfo.put("province", workEntity.getEmployerprovinceen());
			} else {
				preaddressinfo.put("province", "");
			}
			//雇主国家(签证信息)
			if (!Util.isEmpty(workEntity.getEmployercountry())) {
				String country = getCountry(workEntity.getEmployercountry());
				preaddressinfo.put("country", country);
			} else {
				preaddressinfo.put("country", "");
			}
			//雇主邮政编码(签证信息)
			if (!Util.isEmpty(workEntity.getEmployerzipcode())) {
				preaddressinfo.put("zip_code", workEntity.getEmployerzipcode());
			} else {
				preaddressinfo.put("zip_code", "");
			}

			//directorsname

			//主管的姓(签证信息)
			if (!Util.isEmpty(workEntity.getSupervisorfirstname())) {
				directorsname.put("surnames_cn", workEntity.getSupervisorfirstname());
			} else {
				directorsname.put("surnames_cn", "");
			}
			//主管的姓 拼音(签证信息)
			if (!Util.isEmpty(workEntity.getSupervisorfirstnameen())) {
				directorsname.put("surnames_en", workEntity.getSupervisorfirstnameen());
			} else {
				directorsname.put("surnames_en", "");
			}
			//主管的名(签证信息)
			if (!Util.isEmpty(workEntity.getSupervisorlastname())) {
				directorsname.put("given_names_cn", workEntity.getSupervisorlastname());
			} else {
				directorsname.put("given_names_cn", "");
			}
			//主管的名 拼音(签证信息)
			if (!Util.isEmpty(workEntity.getSupervisorlastnameen())) {
				directorsname.put("given_names_en", workEntity.getSupervisorlastnameen());
			} else {
				directorsname.put("given_names_en", "");
			}

		} else {
			preaddressinfo.put("country", "");
			preaddressinfo.put("province", "");
			preaddressinfo.put("city", "");
			preaddressinfo.put("street", "");
			preaddressinfo.put("zip_code", "");

			directorsname.put("surnames_en", "");
			directorsname.put("surnames_cn", "");
			directorsname.put("given_names_cn", "");
			directorsname.put("given_names_en", "");
		}
		work.put("AdderssInfo", preaddressinfo);
		work.put("DirectorsName", directorsname);
		works.add(work);

		WorkEducation.put("Works", works);

		//Educations
		ArrayList<Object> educations = new ArrayList<>();

		//以前的教育信息
		List<TAppStaffBeforeeducationEntity> beforeeducations = dbDao.query(TAppStaffBeforeeducationEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		for (TAppStaffBeforeeducationEntity educationEntity : beforeeducations) {
			Map<String, Object> education = Maps.newHashMap();
			//机构名称(签证信息)
			if (!Util.isEmpty(educationEntity.getInstitution())) {
				education.put("unit_name_cn", educationEntity.getInstitution());
			} else {
				errorMsg += "学校名称中文,";
			}
			//机构名称 英文(签证信息)
			if (!Util.isEmpty(educationEntity.getInstitutionen())) {
				education.put("unit_name_en", educationEntity.getInstitutionen());
			} else {
				errorMsg += "学校名称英文,";
			}
			//学科(签证信息)
			if (!Util.isEmpty(educationEntity.getCourseen())) {
				education.put("major", educationEntity.getCourseen());
			} else {
				errorMsg += "专业,";
			}
			//参加课程开始时间(签证信息)
			if (!Util.isEmpty(educationEntity.getCoursestartdate())) {
				education.put("start_date", sdf.format(educationEntity.getCoursestartdate()));
			} else {
				errorMsg += "开始时间,";
			}
			//结束时间(签证信息)
			if (!Util.isEmpty(educationEntity.getCourseenddate())) {
				education.put("end_date", sdf.format(educationEntity.getCourseenddate()));
			} else {
				errorMsg += "结束时间,";
			}

			//addressinfo
			Map<String, Object> addressinfo = Maps.newHashMap();
			//街道地址(签证信息)
			if (!Util.isEmpty(educationEntity.getInstitutionaddressen())) {
				addressinfo.put("street", educationEntity.getInstitutionaddressen());
			} else {
				errorMsg += "教育信息详细地址,";
			}
			//市(签证信息)
			if (!Util.isEmpty(educationEntity.getInstitutioncityen())) {
				addressinfo.put("city", educationEntity.getInstitutioncityen());
			} else {
				errorMsg += "教育信息学校地址(市),";
			}
			//省(签证信息)
			if (!Util.isEmpty(educationEntity.getInstitutionprovinceen())) {
				addressinfo.put("province", educationEntity.getInstitutionprovinceen());
			} else {
				addressinfo.put("province", "");
			}
			//国家(签证信息)
			if (!Util.isEmpty(educationEntity.getInstitutioncountry())) {
				String country = getCountry(educationEntity.getInstitutioncountry());
				addressinfo.put("country", country);
			} else {
				errorMsg += "教育信息所在国家,";
			}
			//邮政编码(签证信息)
			if (!Util.isEmpty(educationEntity.getInstitutionzipcode())) {
				addressinfo.put("zip_code", educationEntity.getInstitutionzipcode());
			} else {
				addressinfo.put("zip_code", "");
			}
			education.put("AdderssInfo", addressinfo);

			educations.add(education);
		}

		WorkEducation.put("Education", educations);

		InforMation.put("WorkEducation", WorkEducation);

		//ExitInfo
		Map<String, Object> ExitInfo = Maps.newHashMap();

		//oldpassport 没有
		ArrayList<Object> oldPassport = new ArrayList<>();
		ExitInfo.put("OldPassport", oldPassport);
		//langeuages
		ArrayList<Object> languages = new ArrayList<>();
		List<TAppStaffLanguageEntity> beforelanguages = dbDao.query(TAppStaffLanguageEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		for (TAppStaffLanguageEntity languageEntity : beforelanguages) {
			Map<String, Object> language = Maps.newHashMap();
			if (!Util.isEmpty(languageEntity.getLanguagename())) {
				language.put("name", languageEntity.getLanguagename());
			}
			languages.add(language);
		}

		ExitInfo.put("Language", languages);

		InforMation.put("ExitInfo", ExitInfo);

		//TravelInfo
		Map<String, Object> travelInfo = Maps.newHashMap();
		//航班号，第一行(订单详情)
		if (!Util.isEmpty(info.get("goFlightNum"))) {
			travelInfo.put("airways", info.get("goFlightNum"));
		} else {
			travelInfo.put("airways", "");
		}
		//出行目的(订单详情)
		if (!Util.isEmpty(info.get("travelpurpose"))) {
			String purpose = (String) info.get("travelpurpose");
			travelInfo.put("purpose", getPurpose(purpose));
		} else {
			errorMsg += "出行目的,";
		}
		//目的说明  
		if (!Util.isEmpty(info.get("specify")) && info.getInt("specify") == 1) {
			travelInfo.put("purpose_explanation", "B1-B2");
		} else {
			travelInfo.put("purpose_explanation", "B1-B2");
		}
		travelInfo.put("go_country", "USA");
		int hastripplan = info.getInt("hastripplan");
		//有具体的旅行计划
		if (hastripplan == 1) {
			//抵达城市(订单详情)
			if (!Util.isEmpty(info.get("goArrivedCity"))) {
				int cityid = (int) info.get("goArrivedCity");
				travelInfo.put("in_street", getUSState(cityid));
			} else {
				errorMsg += "抵达城市,";
			}
			//出发城市，第二行(订单详情)
			if (!Util.isEmpty(info.get("returnDepartureCity"))) {
				int cityid = (int) info.get("returnDepartureCity");
				travelInfo.put("leave_street", getUSState(cityid));
			} else {
				errorMsg += "离开城市,";
			}
			//离开美国日期(订单详情)
			//未满14岁，不需要离开时间，14岁以上出现是否有具体的旅行计划
			if (yearsBetween < 14) {
				travelInfo.put("leave_date", "");
			} else {
				if (!Util.isEmpty(info.get("toutleavedate"))) {
					travelInfo.put("leave_date", sdf.format(info.get("toutleavedate")));
				} else {
					errorMsg += "离开美国日期,";
				}
			}
		} else {
			travelInfo.put("in_street", "");
			travelInfo.put("leave_street", "");
			travelInfo.put("leave_date", "");
		}
		travelInfo.put("first_country", "USA");
		//抵达美国日期(订单详情)
		if (!Util.isEmpty(info.get("toutarrivedate"))) {
			travelInfo.put("arrival_date", sdf.format(info.get("toutarrivedate")));
		} else {
			errorMsg += "抵达时间,";
		}

		//航班号，第二行(订单详情)
		if (!Util.isEmpty(info.get("returnFlightNum"))) {
			travelInfo.put("leave_airways", info.get("returnFlightNum"));
		} else {
			travelInfo.put("leave_airways", "");
		}
		//团队名称(签证信息)
		if (!Util.isEmpty(info.get("groupname"))) {
			travelInfo.put("travel_agency", info.get("groupname"));
		} else {
			travelInfo.put("travel_agency", "");
			//errorMsg += "团队名称,";?????
		}

		//passport
		Map<String, Object> passport = Maps.newHashMap();

		//签发机构(护照信息)
		passport.put("country", "CHIN");
		//护照类型(护照信息)
		if (!Util.isEmpty(info.get("passporttype"))) {
			String passporttype = info.getString("passporttype");
			if (Util.eq("P", passporttype)) {
				passport.put("passport_type", "R");
			} else {
				passport.put("passport_type", passporttype);
			}
		} else {
			passport.put("passport_type", "R");
		}
		//护照号(护照信息)
		if (!Util.isEmpty(info.get("passport"))) {
			passport.put("passport_no", info.get("passport"));
		} else {
			errorMsg += "护照号,";
		}
		//签发日期(护照信息)
		if (!Util.isEmpty(info.get("issueddate"))) {
			passport.put("issuance_date", sdf.format(info.get("issueddate")));
		} else {
			errorMsg += "签发日期,";
		}
		//有效期至(护照信息)
		if (!Util.isEmpty(info.get("validenddate"))) {
			passport.put("expiration_date", sdf.format(info.get("validenddate")));
		} else {
			errorMsg += "有效期,";
		}

		//passportissuance
		Map<String, Object> passportissuance = Maps.newHashMap();//没有 ???

		//签发地点(护照信息)
		if (!Util.isEmpty(info.get("issuedplaceen"))) {
			String issuedplace = (String) info.get("issuedplaceen");
			passportissuance.put("province", issuedplace);
			passportissuance.put("city", issuedplace);
		} else {
			errorMsg += "签发地省份,";
		}
		passportissuance.put("country", "CHIN");
		passport.put("PassportIssuance", passportissuance);
		travelInfo.put("Passport", passport);

		//peer
		ArrayList<Object> peers = new ArrayList<>();

		//旅伴信息
		List<TAppStaffCompanioninfoEntity> companions = dbDao.query(TAppStaffCompanioninfoEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		for (TAppStaffCompanioninfoEntity companioninfoEntity : companions) {
			Map<String, Object> peer = Maps.newHashMap();
			Map<String, Object> nameinfo = Maps.newHashMap();
			//与你的关系(签证信息)
			if (!Util.isEmpty(companioninfoEntity.getRelationship())) {
				int relation = companioninfoEntity.getRelationship();
				if (relation == 1) {
					peer.put("relationship", "P");
				}
				if (relation == 2) {
					peer.put("relationship", "S");
				}
				if (relation == 3) {
					peer.put("relationship", "C");
				}
				if (relation == 4) {
					peer.put("relationship", "R");
				}
				if (relation == 5) {
					peer.put("relationship", "F");
				}
				if (relation == 6) {
					peer.put("relationship", "B");
				}
				if (relation == 7) {
					peer.put("relationship", "O");
				}
				//peer.put("relationship", companioninfoEntity.getRelationship());
			} else {
				errorMsg += "与你的关系,";
			}
			peer.put("date_of_birth", "");
			//同伴姓(签证信息)
			if (!Util.isEmpty(companioninfoEntity.getFirstname())) {
				nameinfo.put("surnames_cn", companioninfoEntity.getFirstname());
			} else {
				nameinfo.put("surnames_cn", "");
			}
			//同伴姓 拼音(签证信息)
			if (!Util.isEmpty(companioninfoEntity.getFirstnameen())) {
				nameinfo.put("surnames_en", companioninfoEntity.getFirstnameen());
			} else {
				nameinfo.put("surnames_en", "");
			}
			//同伴名(签证信息)
			if (!Util.isEmpty(companioninfoEntity.getLastname())) {
				nameinfo.put("given_names_cn", companioninfoEntity.getLastname());
			} else {
				nameinfo.put("given_names_cn", "");
			}
			//同伴名 拼音(签证信息)
			if (!Util.isEmpty(companioninfoEntity.getLastnameen())) {
				nameinfo.put("given_names_en", companioninfoEntity.getLastnameen());
			} else {
				nameinfo.put("given_names_en", "");
			}

			peer.put("NameInfo", nameinfo);

			peers.add(peer);

		}

		travelInfo.put("Peer", peers);

		InforMation.put("TravelInfo", travelInfo);

		//AmericaInfo
		Map<String, Object> AmericaInfo = Maps.newHashMap();

		//工作电话 没有？？？ 测试为空
		AmericaInfo.put("work_phone_number", "");
		//===============
		//领区(订单详情)
		if (!Util.isEmpty(info.get("cityid"))) {
			int cityid = (int) info.get("cityid");
			if (cityid == 1) {
				AmericaInfo.put("application_site", "BEJ");
			}
			if (cityid == 2) {
				AmericaInfo.put("application_site", "SHG");
			}
			if (cityid == 3) {
				AmericaInfo.put("application_site", "SNY");
			}
			if (cityid == 4) {
				AmericaInfo.put("application_site", "CHE");
			}
			if (cityid == 5) {
				AmericaInfo.put("application_site", "GUZ");
			}
		} else {
			errorMsg += "领区,";
		}
		ArrayList<Object> otherNationalitys = new ArrayList<>();
		ArrayList<Object> greencards = new ArrayList<>();

		Map<String, Object> nationality = Maps.newHashMap();
		Map<String, Object> greencard = Maps.newHashMap();

		//曾有的或另有的国籍(基本信息)
		if (!Util.isEmpty(info.get("nationality"))) {
			nationality.put("country", info.get("nationality"));
		} else {
			nationality.put("country", "");
		}
		nationality.put("passport", "");
		otherNationalitys.add(nationality);

		AmericaInfo.put("OtherNationality", otherNationalitys);

		//上述国家以外的永久居民(基本信息)
		if (!Util.isEmpty(info.get("othercountry"))) {
			greencard.put("country", info.get("othercountry"));
		} else {
			greencard.put("country", "");
		}
		greencards.add(greencard);

		AmericaInfo.put("GreenCard", greencards);

		//国家识别号码(基本信息)
		if (!Util.isEmpty(info.get("nationalidentificationnumber"))) {
			AmericaInfo.put("national_identification_number", info.get("nationalidentificationnumber"));
		} else {
			AmericaInfo.put("national_identification_number", "");
		}
		//美国社会安全号(基本信息)
		if (!Util.isEmpty(info.get("socialsecuritynumber"))) {
			AmericaInfo.put("us_social_security_number", info.get("socialsecuritynumber"));
		} else {
			AmericaInfo.put("us_social_security_number", "");
		}
		//美国纳税人证件号(基本信息)
		if (!Util.isEmpty(info.get("taxpayernumber"))) {
			AmericaInfo.put("us_taxpayerid_number", info.get("taxpayernumber"));
		} else {
			AmericaInfo.put("us_taxpayerid_number", "");
		}

		//RelativeUS
		Map<String, Object> RelativeUS = Maps.newHashMap();

		//father
		Map<String, Object> father = Maps.newHashMap();

		//父亲是否在美国(签证信息)
		if (!Util.isEmpty(info.get("isfatherinus"))) {
			int fatherstatus = (int) info.get("isfatherinus");
			if (fatherstatus == 1) {
				father.put("in_the_us", 1);
				//父亲在美国的身份(基本信息)
				if (!Util.isEmpty(info.get("fatherstatus"))) {
					int status = (int) info.get("fatherstatus");
					if (status == 1) {
						father.put("status", "S");
					}
					if (status == 2) {
						father.put("status", "C");
					}
					if (status == 3) {
						father.put("status", "P");
					}
					if (status == 4) {
						father.put("status", "O");
					}
				} else {
					errorMsg += "父亲在美国的身份,";
				}
			} else {
				father.put("in_the_us", 0);
			}
		}

		RelativeUS.put("Father", father);

		//mother
		Map<String, Object> mother = Maps.newHashMap();

		//母亲是否在美国(签证信息)
		if (!Util.isEmpty(info.get("ismotherinus"))) {
			int motherstatus = (int) info.get("ismotherinus");
			if (motherstatus == 1) {
				mother.put("in_the_us", 1);
				//母亲在美国的身份(签证信息)
				if (!Util.isEmpty(info.get("motherstatus"))) {
					int status = (int) info.get("motherstatus");
					if (status == 1) {
						mother.put("status", "S");
					}
					if (status == 2) {
						mother.put("status", "C");
					}
					if (status == 3) {
						mother.put("status", "P");
					}
					if (status == 4) {
						mother.put("status", "O");
					}
				} else {
					errorMsg += "母亲在美国的身份,";
				}
			} else {
				mother.put("in_the_us", 0);
			}
		}

		RelativeUS.put("Mother", mother);

		//是否有其他在美亲属(签证信息)
		if (!Util.isEmpty(info.get("hasotherrelatives"))) {
			int status = (int) info.get("hasotherrelatives");
			if (status == 1) {
				RelativeUS.put("other_in_us", true);
			} else {
				RelativeUS.put("other_in_us", false);
			}
		} else {
			errorMsg += "是否有其他在美亲属,";
		}

		//Immediate
		ArrayList<Object> Immediates = new ArrayList<>();

		Map<String, Object> Immediate = Maps.newHashMap();

		//其他直系亲属
		if (info.getInt("hasimmediaterelatives") == 1) {
			TAppStaffImmediaterelativesEntity relativesEntity = dbDao.fetch(TAppStaffImmediaterelativesEntity.class,
					Cnd.where("staffid", "=", staffid));
			Map<String, Object> nameinfo = Maps.newHashMap();
			//亲属的姓(签证信息)
			if (!Util.isEmpty(relativesEntity.getRelativesfirstname())) {
				nameinfo.put("surnames_cn", relativesEntity.getRelativesfirstname());
			} else {
				errorMsg += "直系亲属的姓,";
			}
			//亲属的姓 拼音(签证信息)
			if (!Util.isEmpty(relativesEntity.getRelativesfirstnameen())) {
				nameinfo.put("surnames_en", relativesEntity.getRelativesfirstnameen());
			} else {
				errorMsg += "直系亲属的姓英文,";
			}
			//亲属的名(签证信息)
			if (!Util.isEmpty(relativesEntity.getRelativeslastname())) {
				nameinfo.put("given_names_cn", relativesEntity.getRelativeslastname());
			} else {
				errorMsg += "直系亲属的名,";
			}
			//亲属的名 拼音(签证信息)
			if (!Util.isEmpty(relativesEntity.getRelativeslastnameen())) {
				nameinfo.put("given_names_en", relativesEntity.getRelativeslastnameen());
			} else {
				errorMsg += "直系亲属的名英文,";
			}
			Immediate.put("NameInfo", nameinfo);

			//与你的关系(签证信息)
			if (relativesEntity.getRelationship() != 0 && !Util.isEmpty(relativesEntity.getRelationship())) {
				Integer relationship = relativesEntity.getRelationship();
				if (relationship == 1) {
					Immediate.put("relationship", "S");
				}
				if (relationship == 2) {
					Immediate.put("relationship", "F");
				}
				if (relationship == 3) {
					Immediate.put("relationship", "C");
				}
				if (relationship == 4) {
					Immediate.put("relationship", "B");
				}
			} else {
				errorMsg += "直系亲属与你的关系,";
			}

			//在美国的身份(签证信息)
			if (relativesEntity.getRelativesstatus() != 0 && !Util.isEmpty(relativesEntity.getRelativesstatus())) {
				Integer status = relativesEntity.getRelativesstatus();
				if (status == 1) {
					Immediate.put("status", "S");
				}
				if (status == 2) {
					Immediate.put("status", "C");
				}
				if (status == 3) {
					Immediate.put("status", "P");
				}
				if (status == 4) {
					Immediate.put("status", "O");
				}
			} else {
				errorMsg += "直系亲属在美国的身份,";
			}

			Immediates.add(Immediate);
		}

		RelativeUS.put("Immediate", Immediates);

		AmericaInfo.put("RelativesUS", RelativeUS);

		//ResidentialInfo 住宅信息
		Map<String, Object> ResidentialInfo = Maps.newHashMap();

		//在美国地址 
		if (!Util.isEmpty(info.get("plancityen"))) {
			ResidentialInfo.put("city", info.get("plancityen"));
		} else {
			errorMsg += "计划去美国的城市,";
		}
		if (!Util.isEmpty(info.get("planaddressen"))) {
			ResidentialInfo.put("street", info.get("planaddressen"));
		} else {
			errorMsg += "计划去美国的地址,";
		}
		ResidentialInfo.put("province", getUSState(info.getInt("planstate")));
		//ResidentialInfo.put("country", "CN");
		ResidentialInfo.put("zip_code", "");
		//===========
		AmericaInfo.put("ResidentialInfo", ResidentialInfo);

		//Contacts 美国联络人
		Map<String, Object> Contacts = Maps.newHashMap();

		//contactnameinfo
		Map<String, Object> contactnameinfo = Maps.newHashMap();

		//姓(签证信息)
		if (!Util.isEmpty(info.get("tascfirstname"))) {
			contactnameinfo.put("surnames_cn", info.get("tascfirstname"));
		} else {
			contactnameinfo.put("surnames_cn", "");
		}
		//姓 拼音(签证信息)
		if (!Util.isEmpty(info.get("tascfirstnameen"))) {
			contactnameinfo.put("surnames_en", info.get("tascfirstnameen"));
		} else {
			contactnameinfo.put("surnames_en", "");
		}
		//名(签证信息)
		if (!Util.isEmpty(info.get("tasclastname"))) {
			contactnameinfo.put("given_names_cn", info.get("tasclastname"));
		} else {
			contactnameinfo.put("given_names_cn", "");
		}
		//名 拼音(签证信息)
		if (!Util.isEmpty(info.get("tasclastnameen"))) {
			contactnameinfo.put("given_names_en", info.get("tasclastnameen"));
		} else {
			contactnameinfo.put("given_names_en", "");
		}

		Contacts.put("NameInfo", contactnameinfo);

		//组织名称(签证信息)
		Contacts.put("organization", "FSFSG");
		/*if (!Util.isEmpty(info.get("organizationname"))) {
			Contacts.put("organization", info.get("organizationname"));
		} else {
			Contacts.put("organization", "");
		}*/
		//与你的关系(签证信息)
		Contacts.put("relationship", "B");
		/*if (!Util.isEmpty(info.get("ralationship"))) {
			int relation = (int) info.get("ralationship");
			if (relation == 1) {
				Contacts.put("relationship", "R");
			}
			if (relation == 2) {
				Contacts.put("relationship", "S");
			}
			if (relation == 3) {
				Contacts.put("relationship", "C");
			}
			if (relation == 4) {
				Contacts.put("relationship", "B");
			}
			if (relation == 5) {
				Contacts.put("relationship", "P");
			}
			if (relation == 6) {
				Contacts.put("relationship", "H");
			}
			if (relation == 7) {
				Contacts.put("relationship", "O");
			}
		} else {
			//errorMsg += "与你的关系,";
			Contacts.put("relationship", "");
		}*/
		//电话号码(签证信息)
		Contacts.put("phone", "14654165");
		/*if (!Util.isEmpty(info.get("tasctelephone"))) {
			Contacts.put("phone", info.get("tasctelephone"));
		} else {
			Contacts.put("phone", "");
		}*/
		//邮箱
		if (!Util.isEmpty(info.get("tascemail"))) {
			Contacts.put("email", info.get("tascemail"));
		} else {
			Contacts.put("email", "");
		}

		//addressinfo
		Map<String, Object> addressinfo = Maps.newHashMap();

		//街道地址(签证信息)
		addressinfo.put("street", "SGGEWGEG");
		/*if (!Util.isEmpty(info.get("tascaddress"))) {
			addressinfo.put("street", info.get("tascaddress"));
		} else {
			//errorMsg += "街道地址,";
			addressinfo.put("street", "");
		}*/
		//城市(签证信息)
		addressinfo.put("city", "SDFE");
		/*if (!Util.isEmpty(info.get("tasccity"))) {
			addressinfo.put("city", info.get("tasccity"));
		} else {
			//errorMsg += "城市,";
			addressinfo.put("city", "");
		}*/
		//省份(签证信息)
		addressinfo.put("province", "AL");
		/*if (!Util.isEmpty(info.get("tascstate")) && !Util.eq(0, info.get("tascstate"))) {
			int state = (int) info.get("tascstate");
			addressinfo.put("province", getStatecode(state));
		} else {
			addressinfo.put("province", "");
		}*/
		addressinfo.put("country", "USA");
		//邮编(签证信息)
		if (!Util.isEmpty(info.get("zipcode"))) {
			addressinfo.put("zip_code", info.get("tasczipcode"));
		} else {
			addressinfo.put("zip_code", "");
		}

		Contacts.put("AdderssInfo", addressinfo);

		AmericaInfo.put("Contacts", Contacts);

		//StayCity
		Map<String, Object> StayCity = Maps.newHashMap();

		if (!Util.isEmpty(info.get("plancityen"))) {
			StayCity.put("location", info.get("plancityen"));
		}

		ArrayList<Object> staycitys = new ArrayList<>();
		staycitys.add(StayCity);

		/*List<TAppStaffGocountryEntity> gocountrys = dbDao.query(TAppStaffGocountryEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		for (TAppStaffGocountryEntity gocountryEntity : gocountrys) {
			Map<String, Object> staycity = Maps.newHashMap();
			if (!Util.isEmpty(gocountryEntity.getTraveledcountry())) {
				staycity.put("location", gocountryEntity.getTraveledcountry());
			} else {
				staycity.put("location", "");
			}
			staycitys.add(staycity);
		}*/

		AmericaInfo.put("StayCity", staycitys);
		//============

		//ResidenceTime 
		Map<String, Object> ResidenceTime = Maps.newHashMap();
		ResidenceTime.put("number", info.get("staydays"));
		ResidenceTime.put("date_type", "D");
		AmericaInfo.put("ResidenceTime", ResidenceTime);
		//ResidenceTime 没有？？？============

		//EverGoToAmerica
		Map<String, Object> EverGoToAmerica = Maps.newHashMap();

		//informationUSVisit
		ArrayList<Object> informationUSVisits = new ArrayList<>();

		//去过美国信息
		List<TAppStaffGousinfoEntity> gousinfos = dbDao.query(TAppStaffGousinfoEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		for (TAppStaffGousinfoEntity gousinfoEntity : gousinfos) {
			Map<String, Object> gousinfo = Maps.newHashMap();
			Map<String, Object> residencetime = Maps.newHashMap();
			//抵达日期(签证信息)
			if (!Util.isEmpty(gousinfoEntity.getArrivedate())) {
				gousinfo.put("date", sdf.format(gousinfoEntity.getArrivedate()));
			} else {
				errorMsg += "抵达日期,";
			}
			//停留时间(签证信息)
			if (!Util.isEmpty(gousinfoEntity.getStaydays())) {
				residencetime.put("number", String.valueOf(gousinfoEntity.getStaydays()));
			} else {
				errorMsg += "停留时间,";
			}
			//停留时间单位(签证信息)
			if (!Util.isEmpty(gousinfoEntity.getDateunit())) {
				Integer dateunit = gousinfoEntity.getDateunit();
				if (dateunit == 1) {
					residencetime.put("date_type", "Y");
				}
				if (dateunit == 2) {
					residencetime.put("date_type", "M");
				}
				if (dateunit == 3) {
					residencetime.put("date_type", "W");
				}
				if (dateunit == 4) {
					residencetime.put("date_type", "D");
				}
				if (dateunit == 5) {
					residencetime.put("date_type", "H");
				}
			} else {
				errorMsg += "停留时间单位,";
			}
			gousinfo.put("ResidenceTime", residencetime);
			informationUSVisits.add(gousinfo);
		}

		EverGoToAmerica.put("InformationUSVisit", informationUSVisits);

		//USDriverLicens
		ArrayList<Object> USDriverLicens = new ArrayList<>();

		//美国驾照
		List<TAppStaffDriverinfoEntity> drivers = dbDao.query(TAppStaffDriverinfoEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		for (TAppStaffDriverinfoEntity driverinfoEntity : drivers) {
			Map<String, Object> USDriverLicen = Maps.newHashMap();
			//驾照号(签证信息)
			if (!Util.isEmpty(driverinfoEntity.getDriverlicensenumber())) {
				USDriverLicen.put("number", driverinfoEntity.getDriverlicensenumber());
			} else {
				USDriverLicen.put("number", "");
			}
			//哪个州的驾照(签证信息)
			if (!Util.isEmpty(driverinfoEntity.getWitchstateofdriver())
					&& (driverinfoEntity.getWitchstateofdriver() != 0)) {
				USDriverLicen.put("state", getStatecode(driverinfoEntity.getWitchstateofdriver()));
			} else {
				USDriverLicen.put("state", "");
			}
			USDriverLicens.add(USDriverLicen);
		}

		EverGoToAmerica.put("USDriverLicense", USDriverLicens);

		//LastUSVisa
		Map<String, Object> LastUSVisa = Maps.newHashMap();

		int isissuedvisa = info.getInt("isissuedvisa");
		if (isissuedvisa == 1) {
			//最后一次签证的签发日期(签证信息)
			if (!Util.isEmpty(info.get("tasptissueddate"))) {
				LastUSVisa.put("date", sdf.format(info.get("tasptissueddate")));
			} else {
				errorMsg += "签发日期,";
			}
			//签证号(签证信息)
			if (!Util.isEmpty(info.get("visanumber"))) {
				LastUSVisa.put("number", info.get("visanumber"));
			} else {
				//LastUSVisa.put("number", "");
				errorMsg += "签证号,";
			}
		} else {
			LastUSVisa.put("date", "");
			LastUSVisa.put("number", "");
		}
		//是否申请同类签证(签证信息)
		if (Util.eq(1, info.get("isapplyingsametypevisa"))) {
			LastUSVisa.put("same_visa", 1);
		} else {
			LastUSVisa.put("same_visa", 0);
		}
		//和上一次签证是否是同一地点(签证信息)
		if (Util.eq(1, info.get("issamecountry"))) {
			LastUSVisa.put("same_place", 1);
		} else {
			LastUSVisa.put("same_place", 0);
		}
		//是否采集过指纹(签证信息)
		if (Util.eq(1, info.get("istenprinted"))) {
			LastUSVisa.put("finger_fingerprint", 1);
		} else {
			LastUSVisa.put("finger_fingerprint", 0);
		}

		//LostOrStolen
		Map<String, Object> LostOrStolen = Maps.newHashMap();

		//丢失年份(签证信息)
		LostOrStolen.put("year", 0);
		/*if (!Util.isEmpty(info.get("lostyear"))) {
		LostOrStolen.put("year", info.get("lostyear"));
		} else {
		errorMsg += "丢失年份,";
		}*/
		//说明(签证信息)
		LostOrStolen.put("explain", "");
		/*if (!Util.isEmpty(info.get("lostexplain"))) {
		LostOrStolen.put("explain", info.get("lostexplain"));
		} else {
		errorMsg += "说明,";
		}*/

		LastUSVisa.put("LostOrStolen", LostOrStolen);

		//是否被撤销(签证信息)
		if (!Util.isEmpty(info.get("cancelexplainen"))) {
			LastUSVisa.put("revoked", info.get("cancelexplainen"));
		} else {
			LastUSVisa.put("revoked", "");
		}
		//}

		EverGoToAmerica.put("LastUSVisa", LastUSVisa);

		AmericaInfo.put("EverGoToAmerica", EverGoToAmerica);

		//是否曾被拒绝入境(签证信息)
		if (!Util.isEmpty(info.get("refusedexplainen"))) {
			AmericaInfo.put("refuse_entry", info.get("refusedexplainen"));
		} else {
			AmericaInfo.put("refuse_entry", "");
		}
		//现在或者曾经是美国合法公民(签证信息)
		if (!Util.isEmpty(info.get("permanentresidentexplainen"))) {
			AmericaInfo.put("united_states_citizen", info.get("permanentresidentexplainen"));
		} else {
			AmericaInfo.put("united_states_citizen", "");
		}
		//曾经有人为您申请移民(签证信息)
		if (!Util.isEmpty(info.get("immigrantpetitionexplain"))) {
			AmericaInfo.put("apply_for_emigrant", info.get("immigrantpetitionexplain"));
		} else {
			AmericaInfo.put("apply_for_emigrant", "");
		}
		AmericaInfo.put("esta", "");
		AmericaInfo.put("sevis_id", "");
		AmericaInfo.put("principal_applicant_sevis_id", "");
		AmericaInfo.put("program_number", "");
		AmericaInfo.put("school_in_america", "");

		//MailingAddress
		Map<String, Object> MailingAddress = Maps.newHashMap();

		//邮寄地址跟居住地址一样
		if (info.getInt("ismailsamewithlive") == 1) {
			MailingAddress.put("country", "");
			MailingAddress.put("province", "");
			MailingAddress.put("city", "");
			MailingAddress.put("street", "");
			MailingAddress.put("zip_code", "");
		} else {
			//邮寄国家
			if (!Util.isEmpty(info.get("mailcountry"))) {
				MailingAddress.put("country", getCountrycode((String) info.get("mailcountry")));
			} else {
				errorMsg += "邮寄地址国家,";
			}

			//邮寄省份
			if (!Util.isEmpty(info.get("mailprovinceen"))) {
				MailingAddress.put("province", info.get("mailprovinceen"));
			} else {
				errorMsg += "邮寄地址省份,";
			}

			//邮寄城市
			if (!Util.isEmpty(info.get("mailcityen"))) {
				MailingAddress.put("city", info.get("mailcityen"));
			} else {
				errorMsg += "邮寄地址城市,";
			}

			//邮寄地址
			if (!Util.isEmpty(info.get("mailaddressen"))) {
				MailingAddress.put("street", info.get("mailaddressen"));
			} else {
				errorMsg += "邮寄地址,";
			}

			//邮政编码  没有  测试为空
			MailingAddress.put("zip_code", "");

		}

		/*MailingAddress.put("street", info.get("detailedaddressen"));
		//现居住城市(基本信息)
		MailingAddress.put("city", info.get("tasbcityen"));
		//现居住省份(基本信息)
		MailingAddress.put("province", info.get("tasbprovinceen"));
		MailingAddress.put("country", "CHIN");
		//邮政编码  没有  测试为空
		MailingAddress.put("zip_code", "");*/

		/*if (!Util.isEmpty(info.get("address"))) {
			MailingAddress.put("street", info.get("address"));
		}
		if (!Util.isEmpty(info.get("city"))) {
			MailingAddress.put("city", info.get("city"));
		}
		if (!Util.isEmpty(info.get("province"))) {
			MailingAddress.put("province", info.get("province"));
		}*/
		/*MailingAddress.put("street", "ERHERHFWWEG");
		MailingAddress.put("city", "CHENGDE");
		MailingAddress.put("province", "HEBEI");
		MailingAddress.put("country", "CHIN");
		MailingAddress.put("zip_code", "");*/

		AmericaInfo.put("MailingAddress", MailingAddress);
		//==========

		//PayParty
		//待完善
		//PayParty 没有？？？
		Map<String, Object> PayParty = Maps.newHashMap();
		//PayerInfo
		Map<String, Object> PayerInfo = Maps.newHashMap();
		//PayParty.put("PayerInfo",PayerInfo);

		//PayerAddressInfo
		Map<String, Object> PayerAddressInfo = Maps.newHashMap();
		PayerAddressInfo.put("province", "");
		PayerAddressInfo.put("city", "");
		PayerAddressInfo.put("country", "");
		PayerAddressInfo.put("street", "");
		PayerAddressInfo.put("zip_code", "");
		PayerInfo.put("AdderssInfo", PayerAddressInfo);

		//PayerNameInfo
		Map<String, Object> PayerNameInfo = Maps.newHashMap();
		PayerInfo.put("NameInfo", PayerNameInfo);

		PayerInfo.put("relationship", "");
		PayerInfo.put("phone", "");
		PayerInfo.put("organization", "");
		PayerInfo.put("email", "");

		PayParty.put("PayerInfo", PayerInfo);

		if (!Util.isEmpty(info.getInt("costpayer"))) {
			int costpayer = info.getInt("costpayer");
			if (costpayer == 1) {
				PayParty.put("payer", "S");
			}
			if (costpayer == 2) {
				PayParty.put("payer", "O");
			}
			if (costpayer == 3) {
				PayParty.put("payer", "C");
			}
		}

		AmericaInfo.put("PayParty", PayParty);

		//Supplement
		Map<String, Object> Supplement = Maps.newHashMap();

		//部落名称(签证信息)
		if (!Util.isEmpty(info.get("clanname"))) {
			Supplement.put("religion", info.get("clanname"));
		} else {
			Supplement.put("religion", "");
		}

		//VisitedCountry
		ArrayList<Object> VisitedCountrys = new ArrayList<>();

		//访问过的国家(签证信息)
		List<TAppStaffGocountryEntity> gos = dbDao.query(TAppStaffGocountryEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		for (TAppStaffGocountryEntity gocountryEntity : gos) {
			Map<String, Object> VisitedCountry = Maps.newHashMap();
			if (!Util.isEmpty(gocountryEntity.getTraveledcountry())) {
				String country = gocountryEntity.getTraveledcountry();
				VisitedCountry.put("country", getCountrycode(country));
			} else {
				VisitedCountry.put("country", "");
			}
			VisitedCountrys.add(VisitedCountry);
		}

		Supplement.put("VisitedCountry", VisitedCountrys);

		//Charitable
		ArrayList<Object> Charitables = new ArrayList<>();

		//是否属于、致力于、或为任何专业、社会或慈善组织而工作，组织名称(签证信息)
		List<TAppStaffOrganizationEntity> organizations = dbDao.query(TAppStaffOrganizationEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		for (TAppStaffOrganizationEntity organizationEntity : organizations) {
			Map<String, Object> Charitable = Maps.newHashMap();
			if (!Util.isEmpty(organizationEntity.getOrganizationname())) {
				Charitable.put("organization", organizationEntity.getOrganizationname());
			} else {
				Charitable.put("organization", "");
			}
			Charitables.add(Charitable);
		}

		Supplement.put("Charitable", Charitables);

		//特殊技能说明(签证信息)
		if (!Util.isEmpty(info.get("skillexplain"))) {
			Supplement.put("special_skills", info.get("skillexplain"));
		} else {
			Supplement.put("special_skills", "");
		}

		//MilitaryService
		Map<String, Object> MilitaryService = Maps.newHashMap();
		MilitaryService.put("country", "");
		MilitaryService.put("armed_services", "");
		MilitaryService.put("level", "");
		MilitaryService.put("specialty", "");
		MilitaryService.put("start_date", "");
		MilitaryService.put("end_date", "");

		Supplement.put("MilitaryService", MilitaryService);
		Supplement.put("paramilitary", "");

		AmericaInfo.put("Supplement", Supplement);

		//Security
		Map<String, Object> Security = Maps.newHashMap();
		for (int i = 1; i < 30; i++) {
			Security.put("q" + i, "");
		}
		AmericaInfo.put("Security", Security);

		resultData.put("InforMation", InforMation);
		resultData.put("AmericaInfo", AmericaInfo);

		if (!Util.isEmpty(errorMsg)) {
			errorMsg = errorMsg.substring(0, errorMsg.length() - 1);
			errorMsg += "不能为空";
		}
		result.put("errMsg", errorMsg);
		result.put("resultData", resultData);
		return result;
	}

	//根据国家名称查询国籍代码
	public String getCountrycode(String countryname) {
		String countrycode = "";
		TCountryRegionEntity fetch = dbDao
				.fetch(TCountryRegionEntity.class, Cnd.where("chinesename", "=", countryname));
		if (!Util.isEmpty(fetch)) {
			countrycode = fetch.getInternationalcode();
		}
		return countrycode;
	}

	//根据城市Id查询国际代码
	public String getCountry(int id) {
		TCountryRegionEntity countryRegion = dbDao.fetch(TCountryRegionEntity.class, id);
		return countryRegion.getInternationalcode();
	}

	//出行信息  根据城市id查询城市名
	public String getCityName(int id) {
		TCityEntity gocity = dbDao.fetch(TCityEntity.class, Cnd.where("id", "=", id));
		return gocity.getCity();
	}

	//根据id查询美国州
	public String getUSState(int id) {
		TStateUsEntity fetch = dbDao.fetch(TStateUsEntity.class, id);
		return fetch.getCode();
	}

	//根据id查询美国所在州的代码
	public String getStatecode(int id) {
		TStateUsEntity stateEntity = dbDao.fetch(TStateUsEntity.class, id);
		return stateEntity.getCode();
	}

	public String getOccupation(int status) {
		String result = "";
		if (status == 1) {
			result = "A";
		}
		if (status == 2) {
			result = "AP";
		}
		if (status == 3) {
			result = "B";
		}
		if (status == 4) {
			result = "CM";
		}
		if (status == 5) {
			result = "CS";
		}
		if (status == 6) {
			result = "C";
		}
		if (status == 7) {
			result = "ED";
		}
		if (status == 8) {
			result = "EN";
		}
		if (status == 9) {
			result = "G";
		}
		if (status == 10) {
			result = "H";
		}
		if (status == 11) {
			result = "LP";
		}
		if (status == 12) {
			result = "MH";
		}
		if (status == 13) {
			result = "M";
		}
		if (status == 14) {
			result = "NS";
		}
		if (status == 15) {
			result = "N";
		}
		if (status == 16) {
			result = "PS";
		}
		if (status == 17) {
			result = "RV";
		}
		if (status == 18) {
			result = "R";
		}
		if (status == 19) {
			result = "RT";
		}
		if (status == 20) {
			result = "SS";
		}
		if (status == 21) {
			result = "S";
		}
		if (status == 22) {
			result = "O";
		}
		return result;
	}

	//出行目的
	public String getPurpose(String type) {
		String result = "";
		if (Util.eq("TEMP. BUSINESS PLEASURE VISITOR(B)", type)) {
			result = "B";
		}
		if (Util.eq("EXCHANGE VISITOR(J)", type)) {
			result = "J";
		}
		if (Util.eq("ACADEMIC OR LANGUAGE STUDENT(F)", type)) {
			result = "F";
		}
		return result;
	}
}
