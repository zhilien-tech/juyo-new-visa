package com.juyo.visa.admin.myData.service;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.juyo.visa.common.enums.AlredyVisaTypeEnum;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TTouristtVisaInputEntity;
import com.juyo.visa.forms.TTouristtVisaInputAddForm;
import com.juyo.visa.forms.TTouristtVisaInputUpdateForm;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.MapUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * 签证录入Service
 * <p>
 *
 * @author   
 * @Date	 2018年02月06日 	 
 */
@IocBean
public class TouristVisaService extends BaseService<TOrderJpEntity> {

	public Object visaInput(int userid) {
		Map<String, Object> result = MapUtil.map();
		result.put("userId", userid);
		return result;
	}

	public Object getTouristVisaInput(int userid) {
		Map<String, Object> result = MapUtil.map();
		List<Map<String, Object>> visainputmaps = Lists.newArrayList();
		List<TTouristtVisaInputEntity> visaInputs = dbDao.query(TTouristtVisaInputEntity.class,
				Cnd.where("userId", "=", userid), null);
		for (TTouristtVisaInputEntity visaInputEntity : visaInputs) {
			DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
			Map<String, Object> visainputmap = Maps.newHashMap();
			if (!Util.isEmpty(visaInputEntity.getVisaDate())) {
				visainputmap.put("visadatestr", format.format(visaInputEntity.getVisaDate()));
			}
			if (!Util.isEmpty(visaInputEntity.getValidDate())) {
				visainputmap.put("validdatestr", format.format(visaInputEntity.getValidDate()));
			}
			String visatypestr = "";
			if (!Util.isEmpty(visaInputEntity.getVisaType())) {
				for (AlredyVisaTypeEnum typeEnum : AlredyVisaTypeEnum.values()) {
					if (visaInputEntity.getVisaType().equals(typeEnum.intKey())) {
						visatypestr = typeEnum.value();
					}
				}
			}
			visainputmap.put("visatypestr", visatypestr);
			visainputmap.putAll(obj2Map(visaInputEntity));
			visainputmaps.add(visainputmap);
		}
		result.put("visaInputData", visainputmaps);
		return result;
	}

	private static Map<String, String> obj2Map(Object obj) {

		Map<String, String> map = new HashMap<String, String>();
		// System.out.println(obj.getClass());  
		// 获取f对象对应类中的所有属性域  
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0, len = fields.length; i < len; i++) {
			String varName = fields[i].getName();
			varName = varName.toLowerCase();//将key置为小写，默认为对象的属性  
			try {
				// 获取原来的访问控制权限  
				boolean accessFlag = fields[i].isAccessible();
				// 修改访问控制权限  
				fields[i].setAccessible(true);
				// 获取在对象f中属性fields[i]对应的对象中的变量  
				Object o = fields[i].get(obj);
				if (o != null)
					map.put(varName, o.toString());
				// System.out.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);  
				// 恢复访问控制权限  
				fields[i].setAccessible(accessFlag);
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
		return map;
	}

	public Object visaInputAdd(int userid) {
		Map<String, Object> result = MapUtil.map();
		result.put("visadatatypeenum", EnumUtil.enum2(AlredyVisaTypeEnum.class));
		result.put("userId", userid);
		return result;
	}

	public Object addTouristVisainput(TTouristtVisaInputAddForm addForm) {
		TTouristtVisaInputEntity visaInput = new TTouristtVisaInputEntity();
		visaInput.setCreateTime(new Date());
		visaInput.setPicUrl(addForm.getPicUrl());
		visaInput.setStayDays(addForm.getStayDays());
		visaInput.setUpdateTime(new Date());
		visaInput.setUserId(addForm.getUserId());
		visaInput.setValidDate(addForm.getValidDate());
		visaInput.setVisaAddress(addForm.getVisaAddress());
		visaInput.setVisaCountry(addForm.getVisaCountry());
		visaInput.setVisaDate(addForm.getVisaDate());
		visaInput.setVisaNum(addForm.getVisaNum());
		visaInput.setVisaType(addForm.getVisaType());
		visaInput.setVisaYears(addForm.getVisaYears());
		dbDao.insert(visaInput);
		return null;
	}

	public Object visaInputUpdate(int id) {
		Map<String, Object> result = MapUtil.map();
		TTouristtVisaInputEntity visaInput = dbDao.fetch(TTouristtVisaInputEntity.class, id);
		result.put("visadatatypeenum", EnumUtil.enum2(AlredyVisaTypeEnum.class));
		result.put("applicantvisa", visaInput);
		result.put("userId", visaInput.getUserId());
		result.put("visainputId", id);
		return result;
	}

	public Object updateVisainput(TTouristtVisaInputUpdateForm updateForm, HttpSession session) {
		TTouristtVisaInputEntity fetch = dbDao.fetch(TTouristtVisaInputEntity.class, updateForm.getId());
		fetch.setStayDays(updateForm.getStayDays());
		fetch.setUpdateTime(new Date());
		fetch.setValidDate(updateForm.getValidDate());
		fetch.setVisaAddress(updateForm.getVisaAddress());
		fetch.setVisaCountry(updateForm.getVisaCountry());
		fetch.setVisaDate(updateForm.getVisaDate());
		fetch.setVisaNum(updateForm.getVisaNum());
		fetch.setVisaType(updateForm.getVisaType());
		fetch.setVisaYears(updateForm.getVisaYears());
		fetch.setPicUrl(updateForm.getPicUrl());
		return dbDao.update(fetch);
	}

	public Object deleteVisainput(int id) {
		TTouristtVisaInputEntity visainput = dbDao.fetch(TTouristtVisaInputEntity.class, id);
		dbDao.delete(visainput);
		return null;
	}

}
