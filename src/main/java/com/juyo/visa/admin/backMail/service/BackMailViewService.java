package com.juyo.visa.admin.backMail.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.changePrincipal.service.ChangePrincipalViewService;
import com.juyo.visa.admin.firstTrialJp.entity.BackMailInfoEntity;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.JPOrderProcessTypeEnum;
import com.juyo.visa.common.enums.MainBackMailSourceTypeEnum;
import com.juyo.visa.common.enums.MainBackMailTypeEnum;
import com.juyo.visa.entities.TApplicantBackmailJpEntity;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TApplicantOrderJpEntity;
import com.juyo.visa.entities.TUserEntity;
import com.juyo.visa.forms.TApplicantBackmailJpForm;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * 日本回邮信息Service
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年12月4日 	 
 */
@IocBean
public class BackMailViewService extends BaseService<TApplicantBackmailJpEntity> {

	private static final Integer AFTERMARKET_PROCESS = JPOrderProcessTypeEnum.AFTERMARKET_PROCESS.intKey();

	@Inject
	private ChangePrincipalViewService changePrincipalViewService;

	//回邮信息
	public Object backMailInfo(Integer applicantId, Integer orderId, Integer isAfterMarket) {
		Map<String, Object> result = Maps.newHashMap();
		//资料类型
		result.put("mainSourceTypeEnum", EnumUtil.enum2(MainBackMailSourceTypeEnum.class));
		//回邮方式
		result.put("mainBackMailTypeEnum", EnumUtil.enum2(MainBackMailTypeEnum.class));
		//申请人id
		result.put("applicantId", applicantId);
		//是否是售后操作
		result.put("isAfterMarket", isAfterMarket);
		//订单id
		result.put("orderId", orderId);

		return result;
	}

	//获取回邮信息
	public Object getBackMailInfo(Integer applicantId, HttpSession session) {

		Date nowDate = DateUtil.nowDate();

		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userid = loginUser.getId();
		TApplicantOrderJpEntity taoj = dbDao.fetch(TApplicantOrderJpEntity.class,
				Cnd.where("applicantId", "=", applicantId));

		Map<String, Object> result = Maps.newHashMap();
		String sqlStr = sqlManager.get("backmail_info_by_applicantId");
		Sql sql = Sqls.create(sqlStr);
		sql.setParam("applicantId", applicantId);
		Record backmailinfo = dbDao.fetch(sql);
		if (!Util.isEmpty(backmailinfo)) {
			result.put("backmailinfo", backmailinfo);
		} else {

			//获取申请人信息
			TApplicantEntity applicant = dbDao.fetch(TApplicantEntity.class, applicantId.longValue());
			String name = applicant.getFirstName() + applicant.getLastName();
			String mobile = applicant.getTelephone();

			BackMailInfoEntity backmail = new BackMailInfoEntity();
			backmail.setOpid(userid);
			backmail.setLinkman(name);
			if (!Util.isEmpty(mobile)) {
				backmail.setTelephone(mobile);
			}
			backmail.setSource(MainBackMailSourceTypeEnum.KUAIDI.intKey());
			backmail.setExpresstype(MainBackMailTypeEnum.KUAIDI.intKey());
			backmail.setCreatetime(nowDate);
			backmail.setUpdatetime(nowDate);
			backmail.setApplicantid(applicantId);
			backmail.setApplicantjpid(taoj.getId());

			result.put("backmailinfo", backmail);
		}

		return result;
	}

	//保存回邮信息
	public Object saveBackMailInfo(TApplicantBackmailJpForm form, HttpSession session) {
		Integer backmailId = form.getId();
		Integer orderId = form.getOrderId();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userid = loginUser.getId();

		List<TApplicantBackmailJpEntity> before = dbDao.query(TApplicantBackmailJpEntity.class,
				Cnd.where("id", "=", backmailId), null);
		List<TApplicantBackmailJpEntity> after = new ArrayList<TApplicantBackmailJpEntity>();

		TApplicantBackmailJpEntity backmail = new TApplicantBackmailJpEntity();
		backmail.setId(form.getId());
		backmail.setExpressType(form.getExpressType());
		backmail.setExpressNum(form.getExpressNum());
		backmail.setTeamName(form.getTeamName());
		backmail.setSource(form.getSource());
		backmail.setLinkman(form.getLinkman());
		backmail.setTelephone(form.getTelephone());
		backmail.setExpressAddress(form.getExpressAddress());
		backmail.setInvoiceContent(form.getInvoiceContent());
		backmail.setInvoiceHead(form.getInvoiceHead());
		backmail.setInvoiceMobile(form.getInvoiceMobile());
		backmail.setInvoiceAddress(form.getInvoiceAddress());
		backmail.setApplicantId(form.getApplicantJPId());
		backmail.setInvoiceHead(form.getInvoiceHead());
		backmail.setTaxNum(form.getTaxNum());
		backmail.setRemark(form.getRemark());
		backmail.setUpdateTime(DateUtil.nowDate());
		backmail.setOpId(userid);
		Integer isAfterMarket = form.getIsAfterMarket();
		if (Util.eq(isAfterMarket, IsYesOrNoEnum.YES.intKey())) {
			backmail.setBackSourceTime(DateUtil.nowDate());
		}
		after.add(backmail);

		dbDao.updateRelations(before, after);

		changePrincipalViewService.ChangePrincipal(orderId, AFTERMARKET_PROCESS, userid);

		return "BackMail Success";
	}
}
