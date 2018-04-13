/**
 * SpringContextUtil.java
 * com.visa.companyzlr.util
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年9月28日 	 
 */
public class SpringContextUtil implements ApplicationContextAware {

	// Spring应用上下文环境  
	private static ApplicationContext applicationContext;

	/** 
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境 
	 *  
	 * @param applicationContext 
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextUtil.applicationContext = applicationContext;
	}

	/** 
	 * @return ApplicationContext 
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/** 
	 * 获取对象 
	 * 这里重写了bean方法，起主要作用 
	 * @param name 
	 * @return Object 一个以所给名字注册的bean的实例 
	 * @throws BeansException 
	 */
	public static Object getBean(String name, Class<?> clazz) throws BeansException {
		return applicationContext.getBean(name, clazz);
	}
}
