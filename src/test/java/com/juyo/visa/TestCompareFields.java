/**
 * TestCompareFields.java
 * com.juyo.visa
 * Copyright (c) 2018, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nutz.dao.entity.Record;

import com.uxuexi.core.common.util.DateUtil;

/**
 * 测试 实体之间的属性值是否一致
 * 
 * @author   XHH
 * @Date	 2018年1月8日 	 
 */
public class TestCompareFields {
	public static void main(String[] args) {

		Date b1 = DateUtil.string2Date("2018-01-12");
		Date b2 = DateUtil.string2Date("2018-01-14");

		Record record = new Record();
		record.set("id", 3);
		record.set("name", "XHH");
		record.set("age", 16);
		record.set("city", "BEIJING");
		record.set("phonenum", "1671283131");
		record.set("birthday", b1);

		//record.toEntity();

		Student s = record.toPojo(Student.class);
		Student s1 = new Student(1, "张三", 22, "深圳", "1565123123", b1);
		Student s2 = new Student(2, "李四", 23, "深圳", "", b2);
		// 比较s1和s2不同的属性值，其中id忽略比较  
		Map<String, List<Object>> compareResult = compareFields(s, s2, new String[] { "id", "phonenum" });
		System.out.println("s1和s2共有" + compareResult.size() + "个属性值不同（不包括id）");
		System.out.println("其中：");
		Set<String> keySet = compareResult.keySet();
		for (String key : keySet) {
			List<Object> list = compareResult.get(key);
			System.out.println(">>>  s1的" + key + "为" + list.get(0) + "，s2的" + key + "为" + list.get(1));
		}
	}

	/** 
	 * 比较两个实体属性值，返回一个map以有差异的属性名为key，value为一个list分别存obj1,obj2此属性名的值 
	 * @param obj1 进行属性比较的对象1 
	 * @param obj2 进行属性比较的对象2 
	 * @param ignoreArr 选择忽略比较的属性数组 
	 * @return 属性差异比较结果map 
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, List<Object>> compareFields(Object obj1, Object obj2, String[] ignoreArr) {
		try {
			Map<String, List<Object>> map = new HashMap<String, List<Object>>();
			List<String> ignoreList = null;
			if (ignoreArr != null && ignoreArr.length > 0) {
				// array转化为list  
				ignoreList = Arrays.asList(ignoreArr);
			}
			if (obj1.getClass() == obj2.getClass()) {// 只有两个对象都是同一类型的才有可比性  
				Class clazz = obj1.getClass();
				// 获取object的属性描述  
				PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz, Object.class).getPropertyDescriptors();
				for (PropertyDescriptor pd : pds) {// 这里就是所有的属性了  
					String name = pd.getName();// 属性名  
					if (ignoreList != null && ignoreList.contains(name)) {// 如果当前属性选择忽略比较，跳到下一次循环  
						continue;
					}
					Method readMethod = pd.getReadMethod();// get方法  
					// 在obj1上调用get方法等同于获得obj1的属性值  
					Object o1 = readMethod.invoke(obj1);
					// 在obj2上调用get方法等同于获得obj2的属性值  
					Object o2 = readMethod.invoke(obj2);
					if (o1 instanceof Timestamp) {
						o1 = new Date(((Timestamp) o1).getTime());
					}
					if (o2 instanceof Timestamp) {
						o2 = new Date(((Timestamp) o2).getTime());
					}
					if (o1 == null && o2 == null) {
						continue;
					} else if (o1 == null && o2 != null) {
						List<Object> list = new ArrayList<Object>();
						list.add(o1);
						list.add(o2);
						map.put(name, list);
						continue;
					}
					if (!o1.equals(o2)) {// 比较这两个值是否相等,不等就可以放入map了  
						List<Object> list = new ArrayList<Object>();
						list.add(o1);
						list.add(o2);
						map.put(name, list);
					}
				}
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

/** 
 * 学生orm 
 * @author xhh
 */
class Student {
	private int id;
	private String name;
	private int age;
	private String city;
	private String phonenum;
	private Date birthday;

	public Student(int id, String name, int age, String city, String phonenum, Date birthday) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.city = city;
		this.phonenum = phonenum;
		this.birthday = birthday;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
