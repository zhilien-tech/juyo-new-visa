/**
 * AutofillService.java
 * com.juyo.visa.admin.orderUS.service
 * Copyright (c) 2018, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.orderUS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.juyo.visa.common.enums.MarryStatusEnum;
import com.juyo.visa.entities.TAppStaffBeforeeducationEntity;
import com.juyo.visa.entities.TAppStaffBeforeworkEntity;
import com.juyo.visa.entities.TAppStaffCompanioninfoEntity;
import com.juyo.visa.entities.TAppStaffFormerspouseEntity;
import com.juyo.visa.entities.TAppStaffLanguageEntity;
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
