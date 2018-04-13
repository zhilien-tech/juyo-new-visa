/**
 * FirstTrialJpModule.java
 * com.juyo.visa.admin.firstTrialJp.module
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.firstTrialJp.module;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.juyo.visa.admin.firstTrialJp.service.QualifiedApplicantViewService;

/**
 * 日本订单初审Module
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年11月9日 	 
 */

@IocBean
@At("/admin/qualifiedApplicant")
public class QualifiedApplicantModule {

	@Inject
	private QualifiedApplicantViewService qualifiedApplicantViewService;

	/**
	 * 合格申请人
	 */
	@At
	@POST
	public Object qualified(@Param("applicantId") Integer applicantId, @Param("orderid") Integer orderid,
			@Param("orderjpid") Integer orderjpid, @Param("infoType") int infoType, HttpSession session) {
		return qualifiedApplicantViewService.qualified(applicantId, orderid, orderjpid, infoType, session);
	}

	/**
	 * 不合格申请人
	 */
	@At
	@POST
	public Object unQualified(@Param("applicantId") Integer applicantId, @Param("orderid") Integer orderId,
			@Param("remarkStr") String remarkStr, @Param("infoType") int infoType, HttpSession session) {
		return qualifiedApplicantViewService.unQualified(applicantId, orderId, remarkStr, infoType, session);
	}

	/**
	 * 
	 * /**
	 * 判断申请人电话和邮箱是否填写
	 *
	 * @param applicantId 申请人id
	 * @return true表示：电话或者邮箱有未填写的  
	 */
	@At
	@POST
	public boolean isFieldContactInfoById(@Param("applicantId") Integer applicantId) {
		return qualifiedApplicantViewService.isFieldContactInfoById(applicantId);
	}
}
