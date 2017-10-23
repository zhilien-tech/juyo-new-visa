package com.juyo.visa.common.actionfilter;

import javax.servlet.http.HttpServletRequest;

import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;

import com.juyo.visa.common.annotation.NoFilter;
import com.uxuexi.core.common.util.Util;

public abstract class BaseActionFilter implements ActionFilter {

	/**
	 * 判断是否为ajax请求
	 * @param request
	 * @return
	 */
	protected boolean isAjax(HttpServletRequest request) {
		String xHeader = request.getHeader("X-Requested-With");
		return (xHeader != null && "XMLHttpRequest".equals(xHeader.toString()));
	}

	/**
	 * 该请求是否需要进行权限管理
	 * @param actionContext 
	 * @return 布尔值
	 */
	protected boolean isNeedValidate(final ActionContext actionContext) {
		final NoFilter noFilter = actionContext.getMethod().getAnnotation(NoFilter.class);
		return Util.isEmpty(noFilter);
	}

}
