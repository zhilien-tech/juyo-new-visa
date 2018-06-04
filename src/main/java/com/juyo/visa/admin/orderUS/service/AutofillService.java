/**
 * AutofillService.java
 * com.juyo.visa.admin.orderUS.service
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.orderUS.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.juyo.visa.entities.TAppStaffOrderUsEntity;
import com.juyo.visa.entities.TAppStaffOrganizationEntity;
import com.juyo.visa.entities.TCountryRegionEntity;
import com.juyo.visa.entities.TOrderUsEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class AutofillService extends BaseService<TOrderUsEntity> {

	public Map<String, Object> getData(int orderid) {
		Map<String, Object> result = Maps.newHashMap();
		String errorMsg = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
			if (Util.eq("男", info.get("sex"))) {
				BaseInfo.put("sex", 0);
			} else {
				BaseInfo.put("sex", 1);
			}
		}
		if (!Util.isEmpty(info.get("birthday"))) {
			BaseInfo.put("data_of_birth", sdf.format(info.get("birthday")));
		} else {
			errorMsg += "出生日期,";
		}
		BaseInfo.put("nationality", "CHIN");
		if (!Util.isEmpty(info.get("cardId"))) {
			BaseInfo.put("ic", info.get("cardId"));
		} else {
			errorMsg += "身份证号,";
		}
		if (!Util.isEmpty(info.get("telephone"))) {
			BaseInfo.put("phone", info.get("telephone"));
		} else {
			errorMsg += "手机号,";
		}
		if (!Util.isEmpty(info.get("email"))) {
			BaseInfo.put("email", info.get("email"));
		} else {
			errorMsg += "电子邮箱,";
		}
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
		} else {
			errorMsg += "姓中文,";
		}
		if (!Util.isEmpty(info.get("firstnameen"))) {
			NameInfo.put("surnames_en", info.get("firstnameen"));
		} else {
			errorMsg += "姓英文,";
		}
		if (!Util.isEmpty(info.get("lastname"))) {
			NameInfo.put("given_names_cn", info.get("lastname"));
		} else {
			errorMsg += "名中文";
		}
		if (!Util.isEmpty(info.get("lastnameen"))) {
			NameInfo.put("given_names_en", info.get("lastnameen"));
		} else {
			errorMsg += "名英文";
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
		} else {
			errorMsg += "出生城市,";
		}
		if (!Util.isEmpty(info.get("cardprovince"))) {
			BirthplaceInfo.put("province", info.get("cardprovince"));
		} else {
			errorMsg += "出生省份,";
		}
		BirthplaceInfo.put("country", "CHIN");

		BaseInfo.put("BirthplaceInfo", BirthplaceInfo);

		InforMation.put("BaseInfo", BaseInfo);

		//FamilyInfo
		Map<String, Object> FamilyInfo = Maps.newHashMap();
		//备用电话  不清楚
		FamilyInfo.put("family_phone", "");
		//AddressInfo
		Map<String, Object> AddressInfo = Maps.newHashMap();
		if (!Util.isEmpty(info.get("address"))) {
			AddressInfo.put("street", info.get("address"));
		} else {
			errorMsg += "街道,";
		}
		if (!Util.isEmpty(info.get("city"))) {
			AddressInfo.put("city", info.get("city"));
		} else {
			errorMsg += "城市,";
		}
		if (!Util.isEmpty(info.get("province"))) {
			AddressInfo.put("province", info.get("province"));
		} else {
			errorMsg += "省份,";
		}
		AddressInfo.put("country", "CHIN");
		//邮政编码  没有
		AddressInfo.put("zip_code", "");
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
			FatherInfo.put("date_of_birth", sdf.format(info.get("fatherbirthday")));
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
			MotherInfo.put("date_of_birth", sdf.format(info.get("motherbirthday")));
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
		} else {
			errorMsg += "配偶姓中文,";
		}
		if (!Util.isEmpty(info.get("spousefirstnameen"))) {
			SpouseNameInfo.put("surnames_en", info.get("spousefirstnameen"));
		} else {
			errorMsg += "配偶姓英文,";
		}
		if (!Util.isEmpty(info.get("spouselastname"))) {
			SpouseNameInfo.put("given_names_cn", info.get("spouselastname"));
		} else {
			errorMsg += "配偶名中文,";
		}
		if (!Util.isEmpty(info.get("spouselastnameen"))) {
			SpouseNameInfo.put("given_names_en", info.get("spouselastnameen"));
		} else {
			errorMsg += "配偶名英文,";
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
		SpouseBirthplaceInfo.put("province", "");
		if (!Util.isEmpty(info.get("spousecountry"))) {
			String country = getCountry((int) info.get("spousecountry"));
			SpouseBirthplaceInfo.put("country", country);
		} else {
			errorMsg += "配偶出生国家,";
		}

		SpouseInfo.put("BirthplaceInfo", SpouseBirthplaceInfo);

		if (!Util.isEmpty(info.get("spousebirthday"))) {
			SpouseInfo.put("date_of_birth", sdf.format(info.get("spousebirthday")));
		} else {
			errorMsg += "配偶出生日期,";
		}
		if (!Util.isEmpty(info.get("spousenationality"))) {
			String country = getCountry((int) info.get("spousenationality"));
			SpouseInfo.put("nationality", country);
		} else {
			errorMsg += "配偶国籍,";
		}
		//查询前妻数据
		TAppStaffFormerspouseEntity formerspouse = dbDao.fetch(TAppStaffFormerspouseEntity.class,
				Cnd.where("staffid", "=", staffid));
		if (!Util.isEmpty(formerspouse) && !Util.isEmpty(formerspouse.getMarrieddate())) {
			SpouseInfo.put("start_date", sdf.format(formerspouse.getMarrieddate()));
		} else {
			SpouseInfo.put("start_date", "");
		}
		if (!Util.isEmpty(formerspouse) && !Util.isEmpty(formerspouse.getDivorcedate())) {
			SpouseInfo.put("end_date", sdf.format(formerspouse.getDivorcedate()));
		} else {
			SpouseInfo.put("end_date", "");
		}
		if (!Util.isEmpty(formerspouse) && !Util.isEmpty(formerspouse.getDivorce())) {
			SpouseInfo.put("divorced_country", formerspouse.getDivorce());
		} else {

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

		InforMation.put("FamilyInfo", FamilyInfo);

		//WorkEducation
		Map<String, Object> WorkEducation = Maps.newHashMap();

		if (!Util.isEmpty(info.get("occupation"))) {
			WorkEducation.put("current_status", info.get("occupation"));
		}
		//请说明  没有

		//Works
		ArrayList<Object> works = new ArrayList<>();

		//以前的工作
		List<TAppStaffBeforeworkEntity> beforeWorks = dbDao.query(TAppStaffBeforeworkEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		for (TAppStaffBeforeworkEntity workEntity : beforeWorks) {
			Map<String, Object> work = Maps.newHashMap();
			if (!Util.isEmpty(workEntity.getEmployername())) {
				work.put("unit_name_cn", workEntity.getEmployername());
			}
			if (!Util.isEmpty(workEntity.getEmployernameen())) {
				work.put("unit_name_en", workEntity.getEmployernameen());
			}
			if (!Util.isEmpty(workEntity.getEmployertelephone())) {
				work.put("phone", workEntity.getEmployertelephone());
			}
			if (!Util.isEmpty(workEntity.getEmployertelephone())) {
				work.put("job_title", workEntity.getJobtitle());
			}
			if (!Util.isEmpty(workEntity.getPreviousduty())) {
				work.put("job_description", workEntity.getPreviousduty());
			}
			//月收入 没有
			if (!Util.isEmpty(workEntity.getEmploystartdate())) {
				work.put("start_date", workEntity.getEmploystartdate());
			}
			if (!Util.isEmpty(workEntity.getEmployenddate())) {
				work.put("end_date", workEntity.getEmployenddate());
			} else {
				work.put("end_date", "");
			}

			//AddressInfo
			Map<String, Object> addressinfo = Maps.newHashMap();
			if (!Util.isEmpty(workEntity.getEmployeraddress())) {
				addressinfo.put("street", workEntity.getEmployeraddress());
			}
			if (!Util.isEmpty(workEntity.getEmployeraddress())) {
				addressinfo.put("city", workEntity.getEmployercity());
			}
			if (!Util.isEmpty(workEntity.getEmployeraddress())) {
				addressinfo.put("province", workEntity.getEmployerprovince());
			} else {
				addressinfo.put("province", "");
			}
			if (!Util.isEmpty(workEntity.getEmployeraddress())) {
				addressinfo.put("country", workEntity.getEmployercountry());
			} else {
				addressinfo.put("country", "");
			}
			if (!Util.isEmpty(workEntity.getEmployeraddress())) {
				addressinfo.put("zip_code", workEntity.getEmployerzipcode());
			} else {
				addressinfo.put("zip_code", "");
			}

			if (!Util.isEmpty(addressinfo)) {
				work.put("AdderssInfo", addressinfo);
			}

			//directorsname
			Map<String, Object> directorsname = Maps.newHashMap();

			if (!Util.isEmpty(workEntity.getSupervisorfirstname())) {
				directorsname.put("surnames_cn", workEntity.getSupervisorfirstname());
			} else {
				directorsname.put("surnames_cn", "");
			}
			if (!Util.isEmpty(workEntity.getSupervisorfirstnameen())) {
				directorsname.put("surnames_en", workEntity.getSupervisorfirstnameen());
			} else {
				directorsname.put("surnames_en", "");
			}
			if (!Util.isEmpty(workEntity.getSupervisorlastname())) {
				directorsname.put("given_names_cn", workEntity.getSupervisorlastname());
			} else {
				directorsname.put("given_names_cn", "");
			}
			if (!Util.isEmpty(workEntity.getSupervisorlastnameen())) {
				directorsname.put("given_names_en", workEntity.getSupervisorlastnameen());
			} else {
				directorsname.put("given_names_en", "");
			}

			if (!Util.isEmpty(directorsname)) {
				work.put("DirectorsName", directorsname);
			}
			works.add(work);
		}

		if (!Util.isEmpty(works)) {
			WorkEducation.put("Works", works);
		}

		//Educations
		ArrayList<Object> educations = new ArrayList<>();

		//以前的教育信息
		List<TAppStaffBeforeeducationEntity> beforeeducations = dbDao.query(TAppStaffBeforeeducationEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		for (TAppStaffBeforeeducationEntity educationEntity : beforeeducations) {
			Map<String, Object> education = Maps.newHashMap();
			if (!Util.isEmpty(educationEntity.getInstitution())) {
				education.put("unit_name_cn", educationEntity.getInstitution());
			}
			if (!Util.isEmpty(educationEntity.getInstitutionen())) {
				education.put("unit_name_en", educationEntity.getInstitutionen());
			}
			if (!Util.isEmpty(educationEntity.getCourse())) {
				education.put("major", educationEntity.getCourse());
			}
			if (!Util.isEmpty(educationEntity.getCoursestartdate())) {
				education.put("start_date", educationEntity.getCoursestartdate());
			}
			if (!Util.isEmpty(educationEntity.getCourseenddate())) {
				education.put("end_date", educationEntity.getCourseenddate());
			}

			//addressinfo
			Map<String, Object> addressinfo = Maps.newHashMap();
			if (!Util.isEmpty(educationEntity.getInstitutionaddress())) {
				addressinfo.put("street", educationEntity.getInstitutionaddress());
			}
			if (!Util.isEmpty(educationEntity.getInstitutioncity())) {
				addressinfo.put("city", educationEntity.getInstitutioncity());
			}
			if (!Util.isEmpty(educationEntity.getInstitutionprovince())) {
				addressinfo.put("province", educationEntity.getInstitutionprovince());
			} else {
				addressinfo.put("province", "");
			}
			if (!Util.isEmpty(educationEntity.getInstitutioncountry())) {
				addressinfo.put("country", educationEntity.getInstitutioncountry());
			}
			if (!Util.isEmpty(educationEntity.getInstitutionzipcode())) {
				addressinfo.put("zip_code", educationEntity.getInstitutionzipcode());
			} else {
				addressinfo.put("zip_code", "");
			}

			if (!Util.isEmpty(addressinfo)) {
				education.put("AdderssInfo", addressinfo);
			}

			educations.add(education);
		}

		if (!Util.isEmpty(educations)) {
			WorkEducation.put("Education", educations);
		}

		InforMation.put("WorkEducation", WorkEducation);

		//ExitInfo
		Map<String, Object> ExitInfo = Maps.newHashMap();

		//oldpassport 没有

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

		if (!Util.isEmpty(languages)) {
			ExitInfo.put("Language", languages);
		}

		InforMation.put("ExitInfo", ExitInfo);

		//TravelInfo
		Map<String, Object> travelInfo = Maps.newHashMap();
		if (!Util.isEmpty(info.get("goFlightNum"))) {
			travelInfo.put("airways", info.get("goFlightNum"));
		} else {
			travelInfo.put("airways", "");
		}
		if (!Util.isEmpty(info.get("travelpurpose"))) {
			travelInfo.put("purpose", info.get("travelpurpose"));
		}
		//目的说明  没有？？？
		travelInfo.put("go_country", "美国");
		if (!Util.isEmpty(info.get("goArrivedCity"))) {
			travelInfo.put("in_street", info.get("goArrivedCity"));
		}
		if (!Util.isEmpty(info.get("returnDepartureCity"))) {
			travelInfo.put("leave_street", info.get("returnDepartureCity"));
		}
		travelInfo.put("first_country", "美国");
		if (!Util.isEmpty(info.get("arrivedate"))) {
			travelInfo.put("arrival_date", info.get("arrivedate"));
		}
		if (!Util.isEmpty(info.get("leavedate"))) {
			travelInfo.put("leave_date", info.get("leavedate"));
		} else {
			travelInfo.put("leave_date", "");
		}
		if (!Util.isEmpty(info.get("returnFlightNum"))) {
			travelInfo.put("leave_airways", info.get("returnFlightNum"));
		} else {
			travelInfo.put("leave_airways", "");
		}
		if (!Util.isEmpty(info.get("groupname"))) {
			travelInfo.put("travel_agency", info.get("groupname"));
		}

		//passport
		Map<String, Object> passport = Maps.newHashMap();

		if (!Util.isEmpty(info.get("issuedplace"))) {//签发机构？？？
			passport.put("country", info.get("issuedplace"));
		}
		if (!Util.isEmpty(info.get("type"))) {
			passport.put("passport_type", info.get("type"));
		}
		if (!Util.isEmpty(info.get("passport"))) {
			passport.put("passport_no", info.get("passport"));
		}
		if (!Util.isEmpty(info.get("issueddate"))) {
			passport.put("issuance_date", info.get("issueddate"));
		}
		if (!Util.isEmpty(info.get("validenddate"))) {
			passport.put("expiration_date", info.get("validenddate"));
		}

		//passportissuance
		Map<String, Object> passportissuance = Maps.newHashMap();//没有 ???

		passport.put("PassportIssuance", passportissuance);

		travelInfo.put("Passport", passport);

		//peer
		ArrayList<Object> peers = new ArrayList<>();

		List<TAppStaffCompanioninfoEntity> companions = dbDao.query(TAppStaffCompanioninfoEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		for (TAppStaffCompanioninfoEntity companioninfoEntity : companions) {
			Map<String, Object> peer = Maps.newHashMap();
			Map<String, Object> nameinfo = Maps.newHashMap();
			if (!Util.isEmpty(companioninfoEntity.getRelationship())) {
				peer.put("relationship", companioninfoEntity.getRelationship());
			}
			peer.put("date_of_birth", "");
			if (!Util.isEmpty(companioninfoEntity.getFirstname())) {
				nameinfo.put("surnames_cn", companioninfoEntity.getFirstname());
			} else {
				nameinfo.put("surnames_cn", "");
			}
			if (!Util.isEmpty(companioninfoEntity.getFirstnameen())) {
				nameinfo.put("surnames_en", companioninfoEntity.getFirstnameen());
			} else {
				nameinfo.put("surnames_en", "");
			}
			if (!Util.isEmpty(companioninfoEntity.getLastname())) {
				nameinfo.put("given_names_cn", companioninfoEntity.getLastname());
			} else {
				nameinfo.put("given_names_cn", "");
			}
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

		//工作电话,申请地点  没有？？？
		ArrayList<Object> otherNationalitys = new ArrayList<>();
		ArrayList<Object> greencards = new ArrayList<>();

		Map<String, Object> nationality = Maps.newHashMap();
		Map<String, Object> greencard = Maps.newHashMap();

		if (!Util.isEmpty(info.get("nationality"))) {
			nationality.put("country", info.get("nationality"));
		} else {
			nationality.put("country", "");
		}
		nationality.put("passport", "");
		otherNationalitys.add(nationality);

		AmericaInfo.put("OtherNationality", otherNationalitys);

		if (!Util.isEmpty(info.get("othercountry"))) {
			greencard.put("country", info.get("othercountry"));
		} else {
			greencard.put("country", "");
		}
		greencards.add(greencard);

		AmericaInfo.put("GreenCard", greencards);

		if (!Util.isEmpty(info.get("nationalidentificationnumber"))) {
			AmericaInfo.put("national_identification_number", info.get("nationalidentificationnumber"));
		} else {
			AmericaInfo.put("national_identification_number", "");
		}
		if (!Util.isEmpty(info.get("socialsecuritynumber"))) {
			AmericaInfo.put("us_social_security_number", info.get("socialsecuritynumber"));
		} else {
			AmericaInfo.put("us_social_security_number", "");
		}
		if (!Util.isEmpty(info.get("taxpayernumber"))) {
			AmericaInfo.put("us_taxpayerid_number", info.get("taxpayernumber"));
		} else {
			AmericaInfo.put("us_taxpayerid_number", "");
		}

		//RelativeUS
		Map<String, Object> RelativeUS = Maps.newHashMap();

		//father
		Map<String, Object> father = Maps.newHashMap();

		if (!Util.isEmpty(info.get("isfatherinus"))) {
			father.put("in_the_us", info.get("isfatherinus"));
		}
		if (!Util.isEmpty(info.get("fatherstatus"))) {
			father.put("status", info.get("fatherstatus"));
		}

		RelativeUS.put("Father", father);

		//mother
		Map<String, Object> mother = Maps.newHashMap();

		if (!Util.isEmpty(info.get("ismotherinus"))) {
			mother.put("in_the_us", info.get("ismotherinus"));
		}
		if (!Util.isEmpty(info.get("motherstatus"))) {
			mother.put("status", info.get("motherstatus"));
		}

		RelativeUS.put("Mother", mother);

		if (!Util.isEmpty(info.get("hasotherrelatives"))) {
			RelativeUS.put("other_in_us", info.get("hasotherrelatives"));
		}

		//Immediate
		ArrayList<Object> Immediates = new ArrayList<>();

		List<TAppStaffImmediaterelativesEntity> immediaterelatives = dbDao.query(
				TAppStaffImmediaterelativesEntity.class, Cnd.where("staffid", "=", staffid), null);
		for (TAppStaffImmediaterelativesEntity relativesEntity : immediaterelatives) {
			Map<String, Object> Immediate = Maps.newHashMap();
			Map<String, Object> nameinfo = Maps.newHashMap();
			if (!Util.isEmpty(relativesEntity.getRelativesfirstname())) {
				nameinfo.put("surnames_cn", relativesEntity.getRelativesfirstname());
			} else {
				nameinfo.put("surnames_cn", "");
			}
			if (!Util.isEmpty(relativesEntity.getRelativesfirstnameen())) {
				nameinfo.put("surnames_en", relativesEntity.getRelativesfirstnameen());
			} else {
				nameinfo.put("surnames_en", "");
			}
			if (!Util.isEmpty(relativesEntity.getRelativeslastname())) {
				nameinfo.put("given_names_cn", relativesEntity.getRelativeslastname());
			} else {
				nameinfo.put("given_names_cn", "");
			}
			if (!Util.isEmpty(relativesEntity.getRelativesfirstname())) {
				nameinfo.put("given_names_en", relativesEntity.getRelativeslastname());
			} else {
				nameinfo.put("given_names_en", "");
			}
			Immediate.put("NameInfo", nameinfo);

			if (!Util.isEmpty(relativesEntity.getRelativesstatus())) {
				Immediate.put("status", relativesEntity.getRelativesstatus());
			}
			if (!Util.isEmpty(relativesEntity.getRelationship())) {
				Immediate.put("relationship", relativesEntity.getRelationship());
			}

			Immediates.add(Immediate);
		}

		RelativeUS.put("Immediate", Immediates);

		AmericaInfo.put("RelativesUS", RelativeUS);

		//ResidentialInfo
		Map<String, Object> ResidentialInfo = Maps.newHashMap();
		//在美国地址  没有？？？
		AmericaInfo.put("ResidentialInfo", ResidentialInfo);

		//Contacts
		Map<String, Object> Contacts = Maps.newHashMap();

		//contactnameinfo
		Map<String, Object> contactnameinfo = Maps.newHashMap();

		if (!Util.isEmpty(info.get("firstname"))) {
			contactnameinfo.put("surnames_cn", info.get("firstname"));
		} else {
			contactnameinfo.put("surnames_cn", "");
		}
		if (!Util.isEmpty(info.get("firstnameen"))) {
			contactnameinfo.put("surnames_en", info.get("firstnameen"));
		} else {
			contactnameinfo.put("surnames_en", "");
		}
		if (!Util.isEmpty(info.get("lastname"))) {
			contactnameinfo.put("given_names_cn", info.get("lastname"));
		} else {
			contactnameinfo.put("given_names_cn", "");
		}
		if (!Util.isEmpty(info.get("lastnameen"))) {
			contactnameinfo.put("given_names_en", info.get("lastnameen"));
		} else {
			contactnameinfo.put("given_names_en", "");
		}

		Contacts.put("NameInfo", contactnameinfo);

		if (!Util.isEmpty(info.get("organizationname"))) {
			Contacts.put("organization", info.get("organizationname"));
		} else {
			Contacts.put("organization", "");
		}
		if (!Util.isEmpty(info.get("ralationship"))) {
			Contacts.put("relationship", info.get("ralationship"));
		}
		if (!Util.isEmpty(info.get("telephone"))) {
			Contacts.put("phone", info.get("telephone"));
		} else {
			Contacts.put("phone", "");
		}
		if (!Util.isEmpty(info.get("email"))) {
			Contacts.put("email", info.get("email"));
		} else {
			Contacts.put("email", "");
		}

		//addressinfo
		Map<String, Object> addressinfo = Maps.newHashMap();

		if (!Util.isEmpty(info.get("address"))) {
			addressinfo.put("street", info.get("address"));
		}
		if (!Util.isEmpty(info.get("city"))) {
			addressinfo.put("city", info.get("city"));
		}
		if (!Util.isEmpty(info.get("state"))) {
			addressinfo.put("province", info.get("state"));
		}
		addressinfo.put("country", "美国");
		if (!Util.isEmpty(info.get("zipcode"))) {
			addressinfo.put("zip_code", info.get("zipcode"));
		} else {
			addressinfo.put("zip_code", "");
		}

		Contacts.put("AdderssInfo", addressinfo);

		AmericaInfo.put("Contacts", Contacts);

		//StayCity
		ArrayList<Object> staycitys = new ArrayList<>();

		List<TAppStaffGocountryEntity> gocountrys = dbDao.query(TAppStaffGocountryEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		for (TAppStaffGocountryEntity gocountryEntity : gocountrys) {
			Map<String, Object> staycity = Maps.newHashMap();
			if (!Util.isEmpty(gocountryEntity.getTraveledcountry())) {
				staycity.put("location", gocountryEntity.getTraveledcountry());
			} else {
				staycity.put("location", "");
			}
			staycitys.add(staycity);
		}

		AmericaInfo.put("StayCity", staycitys);

		//ResidenceTime
		Map<String, Object> ResidenceTime = Maps.newHashMap();
		//ResidenceTime 没有？？？
		AmericaInfo.put("ResidenceTime", ResidenceTime);

		//EverGoToAmerica
		Map<String, Object> EverGoToAmerica = Maps.newHashMap();

		//informationUSVisit
		ArrayList<Object> informationUSVisits = new ArrayList<>();

		List<TAppStaffGousinfoEntity> gousinfos = dbDao.query(TAppStaffGousinfoEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		for (TAppStaffGousinfoEntity gousinfoEntity : gousinfos) {
			Map<String, Object> gousinfo = Maps.newHashMap();
			Map<String, Object> residencetime = Maps.newHashMap();
			if (!Util.isEmpty(gousinfoEntity.getArrivedate())) {
				gousinfo.put("date", gousinfoEntity.getArrivedate());
			}
			if (!Util.isEmpty(gousinfoEntity.getStaydays())) {
				residencetime.put("number", gousinfoEntity.getStaydays());
			}
			if (!Util.isEmpty(gousinfoEntity.getDateunit())) {
				residencetime.put("date_type", gousinfoEntity.getDateunit());
			}
			gousinfo.put("ResidenceTime", residencetime);
			informationUSVisits.add(gousinfo);
		}

		EverGoToAmerica.put("InformationUSVisit", informationUSVisits);

		//USDriverLicens
		ArrayList<Object> USDriverLicens = new ArrayList<>();

		List<TAppStaffDriverinfoEntity> drivers = dbDao.query(TAppStaffDriverinfoEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		for (TAppStaffDriverinfoEntity driverinfoEntity : drivers) {
			Map<String, Object> USDriverLicen = Maps.newHashMap();
			if (!Util.isEmpty(driverinfoEntity.getDriverlicensenumber())) {
				USDriverLicen.put("number", driverinfoEntity.getDriverlicensenumber());
			} else {
				USDriverLicen.put("number", "");
			}
			if (!Util.isEmpty(driverinfoEntity.getWitchstateofdriver())) {
				USDriverLicen.put("state", driverinfoEntity.getWitchstateofdriver());
			}
			USDriverLicens.add(USDriverLicen);
		}

		EverGoToAmerica.put("USDriverLicense", USDriverLicens);

		//LastUSVisa
		Map<String, Object> LastUSVisa = Maps.newHashMap();

		if (!Util.isEmpty(info.get("issueddate"))) {
			LastUSVisa.put("date", info.get("issueddate"));
		}
		if (!Util.isEmpty(info.get("visanumber"))) {
			LastUSVisa.put("number", info.get("visanumber"));
		}
		if (!Util.isEmpty(info.get("isapplyingsametypevisa"))) {
			LastUSVisa.put("same_visa", info.get("isapplyingsametypevisa"));
		}
		if (!Util.isEmpty(info.get("issamecountry"))) {
			LastUSVisa.put("same_place", info.get("issamecountry"));
		}
		if (!Util.isEmpty(info.get("istenprinted"))) {
			LastUSVisa.put("finger_fingerprint", info.get("istenprinted"));
		}

		//LostOrStolen
		Map<String, Object> LostOrStolen = Maps.newHashMap();

		if (!Util.isEmpty(info.get("lostyear"))) {
			LostOrStolen.put("year", info.get("lostyear"));
		}
		if (!Util.isEmpty(info.get("lostexplain"))) {
			LostOrStolen.put("explain", info.get("lostexplain"));
		}

		LastUSVisa.put("LostOrStolen", LostOrStolen);

		if (!Util.isEmpty(info.get("iscancelled"))) {
			LastUSVisa.put("revoked", info.get("iscancelled"));
		} else {
			LastUSVisa.put("revoked", "");
		}

		EverGoToAmerica.put("LastUSVisa", LastUSVisa);

		AmericaInfo.put("EverGoToAmerica", EverGoToAmerica);

		if (!Util.isEmpty(info.get("isrefused"))) {
			AmericaInfo.put("refuse_entry", info.get("isrefused"));
		} else {
			AmericaInfo.put("refuse_entry", "");
		}
		if (!Util.isEmpty(info.get("islegalpermanentresident"))) {
			AmericaInfo.put("united_states_citizen", info.get("islegalpermanentresident"));
		} else {
			AmericaInfo.put("united_states_citizen", "");
		}
		if (!Util.isEmpty(info.get("isfiledimmigrantpetition"))) {
			AmericaInfo.put("apply_for_emigrant", info.get("isfiledimmigrantpetition"));
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

		if (!Util.isEmpty(info.get("address"))) {
			MailingAddress.put("street", info.get("address"));
		}
		if (!Util.isEmpty(info.get("city"))) {
			MailingAddress.put("city", info.get("city"));
		}
		if (!Util.isEmpty(info.get("province"))) {
			MailingAddress.put("province", info.get("province"));
		}
		MailingAddress.put("country", "CHN");
		MailingAddress.put("zip_code", "");

		AmericaInfo.put("MailingAddress", MailingAddress);

		//PayParty
		Map<String, Object> PayParty = Maps.newHashMap();
		//PayParty 没有？？？

		AmericaInfo.put("PayParty", PayParty);

		//Supplement
		Map<String, Object> Supplement = Maps.newHashMap();

		if (!Util.isEmpty(info.get("clanname"))) {
			Supplement.put("religion", info.get("clanname"));
		} else {
			Supplement.put("religion", "");
		}

		//VisitedCountry
		ArrayList<Object> VisitedCountrys = new ArrayList<>();

		List<TAppStaffGocountryEntity> gos = dbDao.query(TAppStaffGocountryEntity.class,
				Cnd.where("staffid", "=", staffid), null);
		for (TAppStaffGocountryEntity gocountryEntity : gos) {
			Map<String, Object> VisitedCountry = Maps.newHashMap();
			if (!Util.isEmpty(gocountryEntity.getTraveledcountry())) {
				VisitedCountry.put("country", gocountryEntity.getTraveledcountry());
			} else {
				VisitedCountry.put("country", "");
			}
			VisitedCountrys.add(VisitedCountry);
		}

		Supplement.put("VisitedCountry", VisitedCountrys);

		//Charitable
		ArrayList<Object> Charitables = new ArrayList<>();

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

		if (!Util.isEmpty(info.get("skillexplain"))) {
			Supplement.put("specail_skills", info.get("skillexplain"));
		} else {
			Supplement.put("specail_skills", "");
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

		AmericaInfo.put("Security", Security);

		InforMation.put("AmericaInfo", AmericaInfo);

		result.put("InforMation", InforMation);
		return result;
	}

	public String getCountry(int id) {
		TCountryRegionEntity countryRegion = dbDao.fetch(TCountryRegionEntity.class, id);
		return countryRegion.getInternationalcode();
	}
}
