/**
 * AftermarketService.java
 * com.juyo.visa.admin.aftermarket.service
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.aftermarket.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.aftermarket.form.AftermarketListForm;
import com.juyo.visa.admin.changePrincipal.service.ChangePrincipalViewService;
import com.juyo.visa.admin.firstTrialJp.service.FirstTrialJpViewService;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.mail.service.MailService;
import com.juyo.visa.common.base.JuYouResult;
import com.juyo.visa.common.enums.JPOrderProcessTypeEnum;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.util.RegExpUtil;
import com.juyo.visa.entities.TApplicantBackmailJpEntity;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TApplicantOrderJpEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年12月4日 	 
 */
@IocBean
public class AftermarketService extends BaseService<TOrderEntity> {

	private static final String AFTERMARKET_EMAIL_URL = "mailtemp/aftermarket_mail_temp.html";
	private static final String AFTERMARKET_MESSAGE_URL = "messagetmp/aftermarket_message_tmp.txt";
	private static final Integer AFTERMARKET_PROCESS = JPOrderProcessTypeEnum.AFTERMARKET_PROCESS.intKey();
	@Inject
	private MailService mailService;
	@Inject
	private FirstTrialJpViewService firstTrialJpViewService;

	@Inject
	private ChangePrincipalViewService changePrincipalViewService;

	/**
	 * 获取售后列表数据
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @param session
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object aftermarketListData(AftermarketListForm form, HttpSession session) {

		//获取当前公司
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		//获取当前用户
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userid = loginUser.getId();
		form.setUserid(userid);
		form.setCompanyid(loginCompany.getId());
		form.setAdminId(loginCompany.getAdminId());
		Map<String, Object> result = Maps.newHashMap();
		Sql sql = form.sql(sqlManager);

		Integer pageNumber = form.getPageNumber();
		Integer pageSize = form.getPageSize();

		Pager pager = new OffsetPager((pageNumber - 1) * pageSize, pageSize);
		pager.setRecordCount((int) Daos.queryCount(nutDao, sql.toString()));
		sql.setPager(pager);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		//主sql数据
		List<Record> list = (List<Record>) sql.getResult();
		for (Record record : list) {
			int orderstatus = (int) record.get("orderstatus");
			for (JPOrderStatusEnum orderStatus : JPOrderStatusEnum.values()) {
				if (orderstatus == orderStatus.intKey()) {
					record.put("orderstatus", orderStatus.value());
				}
			}
			String sqlstring = sqlManager.get("get_aftermarket_list_applicat_data");
			Sql applicatsql = Sqls.create(sqlstring);
			Cnd applicatcnd = Cnd.NEW();
			applicatcnd.and("taoj.orderid", "=", record.get("orderjpid"));
			List<Record> applicats = dbDao.query(applicatsql, applicatcnd, null);
			for (Record applicantrecord : applicats) {
				if (Util.isEmpty(applicantrecord.get("linkman"))) {
					applicantrecord.put("linkman",
							applicantrecord.getString("firstname") + applicantrecord.getString("lastname"));
				}
				if (Util.isEmpty(applicantrecord.get("backtelephone"))) {
					applicantrecord.put("backtelephone", applicantrecord.getString("telephone"));
				}
			}
			record.put("applicats", applicats);
		}
		result.put("pagetotal", pager.getPageCount());
		result.put("aftermarketData", list);
		return result;

	}

	/**
	 * 售后发送邮件和短信
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param applicantid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object sendMailAndMessage(Integer applicantid, HttpServletRequest request) {
		HttpSession session = request.getSession();
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		//日本申请人信息
		TApplicantOrderJpEntity applicantjp = dbDao.fetch(TApplicantOrderJpEntity.class, applicantid.longValue());
		//申请人信息
		TApplicantEntity applicant = dbDao.fetch(TApplicantEntity.class, applicantjp.getApplicantId().longValue());
		//日本订单信息
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, applicantjp.getOrderId().longValue());
		//订单信息
		TOrderEntity order = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
		Integer orderId = order.getId();
		//获取邮件
		String email = applicant.getEmail();
		//获取手机号
		String telephone = applicant.getTelephone();
		boolean mobileLegal = RegExpUtil.isMobileLegal(telephone);
		if (!mobileLegal) {
			return JuYouResult.build(500, "手机号格式错误");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("${name}", applicant.getFirstName() + applicant.getLastName());
		if ("男".equals(applicant.getSex())) {
			map.put("${sex}", "先生");
		} else {
			map.put("${sex}", "女士");
		}
		map.put("${ordernum}", order.getOrderNum());
		//手机端页面链接
		String mobileUrl = "http://" + request.getLocalAddr() + ":" + request.getLocalPort()
				+ "/mobile/backEmailInfo.html?applicantId=" + applicant.getId();
		//转换长连接为短地址
		mobileUrl = firstTrialJpViewService.getEncryptlink(mobileUrl, request);
		map.put("${mobileUrl}", mobileUrl);
		//电子邮件链接
		String emailurl = "http://" + request.getLocalAddr() + ":" + request.getLocalPort() + "/tlogin.html";
		map.put("${emailurl}", emailurl);
		//发送邮件
		mailService.sendHtml(email, map, AFTERMARKET_EMAIL_URL, "售后通知");
		//发短信
		mailService.sendMessageByMap(telephone, map, AFTERMARKET_MESSAGE_URL);
		//发送后游客进度为资料已寄出
		TApplicantBackmailJpEntity backmail = dbDao.fetch(TApplicantBackmailJpEntity.class,
				Cnd.where("applicantId", "=", applicantjp.getId()));
		backmail.setBackSourceTime(DateUtil.nowDate());
		dbDao.update(backmail);
		changePrincipalViewService.ChangePrincipal(orderId, AFTERMARKET_PROCESS, userId);
		return JuYouResult.ok();
	}
}
