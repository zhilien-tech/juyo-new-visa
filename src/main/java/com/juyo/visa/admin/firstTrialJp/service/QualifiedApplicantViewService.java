package com.juyo.visa.admin.firstTrialJp.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.entity.Record;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;

import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.order.service.OrderJpViewService;
import com.juyo.visa.common.enums.ApplicantInfoTypeEnum;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.enums.TrialApplicantStatusEnum;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TApplicantUnqualifiedEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * 日本订单初审申请人 合格 OR 不合格 Service
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年11月9日 	 
 */
@IocBean
public class QualifiedApplicantViewService extends BaseService<TApplicantEntity> {

	@Inject
	private OrderJpViewService orderJpViewService;

	@Inject
	private FirstTrialJpViewService firstTrialJpViewService;

	//合格申请人
	public Object qualified(Integer applicantId, Integer orderid, Integer orderjpid, int infoType, HttpSession session) {

		if (Util.eq(infoType, ApplicantInfoTypeEnum.BASE.intKey())) {
			//基本信息
			dbDao.update(TApplicantUnqualifiedEntity.class, Chain.make("isBase", IsYesOrNoEnum.NO.intKey()),
					Cnd.where("applicantId", "=", applicantId));
			dbDao.update(TApplicantUnqualifiedEntity.class, Chain.make("baseRemark", ""),
					Cnd.where("applicantId", "=", applicantId));

		} else if (Util.eq(infoType, ApplicantInfoTypeEnum.PASSPORT.intKey())) {
			//护照信息
			dbDao.update(TApplicantUnqualifiedEntity.class, Chain.make("isPassport", IsYesOrNoEnum.NO.intKey()),
					Cnd.where("applicantId", "=", applicantId));
			dbDao.update(TApplicantUnqualifiedEntity.class, Chain.make("passRemark", ""),
					Cnd.where("applicantId", "=", applicantId));

		} else if (Util.eq(infoType, ApplicantInfoTypeEnum.VISA.intKey())) {
			//签证信息
			dbDao.update(TApplicantUnqualifiedEntity.class, Chain.make("isVisa", IsYesOrNoEnum.NO.intKey()),
					Cnd.where("applicantId", "=", applicantId));
			dbDao.update(TApplicantUnqualifiedEntity.class, Chain.make("visaRemark", ""),
					Cnd.where("applicantId", "=", applicantId));
		}

		Boolean isQualified = isQualifiedByApplicantId(applicantId);
		if (isQualified) {
			int update = dbDao.update(TApplicantEntity.class,
					Chain.make("status", TrialApplicantStatusEnum.qualified.intKey()),
					Cnd.where("id", "=", applicantId));
			if (update > 0) {
				//清空不合格信息
				TApplicantUnqualifiedEntity unqualifiedInfo = dbDao.fetch(TApplicantUnqualifiedEntity.class,
						Cnd.where("applicantId", "=", applicantId));
				if (!Util.isEmpty(unqualifiedInfo)) {
					dbDao.delete(unqualifiedInfo);
				}
			}
		}

		Boolean qualified = isQualifiedByOrderId(orderjpid);
		Date nowDate = DateUtil.nowDate();
		if (!qualified) {
			//只要一个不合格，订单状态为初审
			int firsttrialstatus = JPOrderStatusEnum.FIRSTTRIAL_ORDER.intKey();
			dbDao.update(TOrderEntity.class, Chain.make("status", firsttrialstatus), Cnd.where("id", "=", orderid));
			dbDao.update(TOrderEntity.class, Chain.make("updateTime", nowDate), Cnd.where("id", "=", orderid));
		} else {
			//全合格，订单状态为合格
			int qualifiedstatus = JPOrderStatusEnum.QUALIFIED_ORDER.intKey();
			orderJpViewService.insertLogs(orderid, qualifiedstatus, session);
			dbDao.update(TOrderEntity.class, Chain.make("status", qualifiedstatus), Cnd.where("id", "=", orderid));
			dbDao.update(TOrderEntity.class, Chain.make("updateTime", nowDate), Cnd.where("id", "=", orderid));
		}

		try {
			//发送合格消息
			firstTrialJpViewService.sendApplicantVerifySMS(applicantId, orderid, "applicant_qualified_sms.txt");
			//发送合格邮件
			firstTrialJpViewService.sendApplicantVerifyEmail(applicantId, orderid, "applicant_qualified_mail.html");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return qualified;
	}

	//不合格申请人
	public Object unQualified(Integer applicantId, Integer orderId, String remakStr, int infoType, HttpSession session) {

		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();

		int yes = IsYesOrNoEnum.YES.intKey();
		Date now = DateUtil.nowDate();

		TApplicantUnqualifiedEntity fetch = dbDao.fetch(TApplicantUnqualifiedEntity.class,
				Cnd.where("applicantId", "=", applicantId));
		if (Util.isEmpty(fetch)) {
			TApplicantUnqualifiedEntity unq = new TApplicantUnqualifiedEntity();
			unq.setApplicantId(applicantId);
			if (Util.eq(infoType, ApplicantInfoTypeEnum.BASE.intKey())) {
				//基本信息
				unq.setIsBase(yes);
				unq.setBaseRemark(remakStr);

			} else if (Util.eq(infoType, ApplicantInfoTypeEnum.PASSPORT.intKey())) {
				//护照信息
				unq.setIsPassport(yes);
				unq.setPassRemark(remakStr);

			} else if (Util.eq(infoType, ApplicantInfoTypeEnum.VISA.intKey())) {
				//签证信息
				unq.setIsVisa(yes);
				unq.setVisaRemark(remakStr);
			}
			unq.setOpId(userId);
			unq.setCreateTime(now);
			unq.setUpdateTime(now);
			dbDao.insert(unq);
		} else {
			//更新
			fetch.setApplicantId(applicantId);
			if (Util.eq(infoType, ApplicantInfoTypeEnum.BASE.intKey())) {
				//基本信息
				fetch.setIsBase(yes);
				fetch.setBaseRemark(remakStr);

			} else if (Util.eq(infoType, ApplicantInfoTypeEnum.PASSPORT.intKey())) {
				//护照信息
				fetch.setIsPassport(yes);
				fetch.setPassRemark(remakStr);

			} else if (Util.eq(infoType, ApplicantInfoTypeEnum.VISA.intKey())) {
				//签证信息
				fetch.setIsVisa(yes);
				fetch.setVisaRemark(remakStr);
			}
			fetch.setOpId(userId);
			fetch.setUpdateTime(now);
			nutDao.update(fetch);
		}

		Boolean isQualified = isQualifiedByApplicantId(applicantId);
		if (!isQualified) {
			//更改申请人状态为不合格
			dbDao.update(TApplicantEntity.class, Chain.make("status", TrialApplicantStatusEnum.unqualified.intKey()),
					Cnd.where("id", "=", applicantId));
		}

		//更改订单状态为初审
		int firsttrialstatus = JPOrderStatusEnum.FIRSTTRIAL_ORDER.intKey();
		dbDao.update(TOrderEntity.class, Chain.make("status", firsttrialstatus), Cnd.where("id", "=", orderId));
		dbDao.update(TOrderEntity.class, Chain.make("updateTime", now), Cnd.where("id", "=", orderId));

		//记录日志
		orderJpViewService.insertLogs(orderId, firsttrialstatus, session);

		try {
			//发送不合格消息
			firstTrialJpViewService.sendApplicantVerifySMS(applicantId, orderId, "applicant_unqualified_sms.txt");
			//发送不合格邮件
			firstTrialJpViewService.sendApplicantVerifyEmail(applicantId, orderId, "applicant_unqualified_mail.html");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Json.toJson("success");
	}

	//判断订单下申请人是否合格
	public Boolean isQualifiedByOrderId(Integer orderjpid) {
		List<Record> applicants = firstTrialJpViewService.getApplicantByOrderid(orderjpid);
		boolean isQualified = true;
		if (!Util.isEmpty(applicants)) {
			for (Record record : applicants) {
				String status = (String) record.get("applicantstatus");
				if (!Util.eq("合格", status)) {
					isQualified = false;
				}
			}
		}
		return isQualified;
	}

	//判断申请人是否合格
	public Boolean isQualifiedByApplicantId(Integer applicantId) {
		boolean isQualified = true;
		int YES = IsYesOrNoEnum.YES.intKey();
		TApplicantUnqualifiedEntity fetch = dbDao.fetch(TApplicantUnqualifiedEntity.class,
				Cnd.where("applicantId", "=", applicantId));
		if (!Util.isEmpty(fetch)) {
			Integer isBase = fetch.getIsBase();
			Integer isPassport = fetch.getIsPassport();
			Integer isVisa = fetch.getIsVisa();

			if (Util.eq(isBase, YES) || Util.eq(isPassport, YES) || Util.eq(isVisa, YES)) {
				isQualified = false;
			}
		}
		return isQualified;
	}
}
