package com.juyo.visa.admin.changePrincipal.service;

import java.util.Date;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;

import com.juyo.visa.common.enums.JPOrderProcessTypeEnum;
import com.juyo.visa.entities.TOrderEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * 负责人变更Service
 * <p>
 *
 * @author   彭辉
 * @Date	 2018年01月11日 	 
 */
@IocBean
public class ChangePrincipalViewService extends BaseService<TOrderEntity> {

	//订单主流程
	private static final Integer SALES_PROCESS = JPOrderProcessTypeEnum.SALES_PROCESS.intKey();
	private static final Integer FIRSTTRIAL_PROCESS = JPOrderProcessTypeEnum.FIRSTTRIAL_PROCESS.intKey();
	private static final Integer RECEPTION_PROCESS = JPOrderProcessTypeEnum.RECEPTION_PROCESS.intKey();
	private static final Integer VISA_PROCESS = JPOrderProcessTypeEnum.VISA_PROCESS.intKey();
	private static final Integer AFTERMARKET_PROCESS = JPOrderProcessTypeEnum.AFTERMARKET_PROCESS.intKey();

	/**
	 * 
	 * 变更订单负责人
	 * <p>
	 *
	 * @param orderId 订单id
	 * @param orderProcessType 订单主流程枚举
	 * @param userId 用户id
	 * @return 
	 */
	public Object ChangePrincipal(Integer orderId, Integer orderProcessType, Integer userId) {
		int updatenum = 0;
		Date nowDate = DateUtil.nowDate();
		//获取对应的字段
		String fieldStr = getprincipalField(orderProcessType);
		if (!Util.isEmpty(fieldStr)) {
			TOrderEntity order = dbDao.fetch(TOrderEntity.class, Cnd.where("id", "=", orderId));
			if (!Util.isEmpty(order)) {
				//更新订单对应的负责人
				updatenum = dbDao.update(TOrderEntity.class, Chain.make(fieldStr, userId).add("updateTime", nowDate),
						Cnd.where("id", "=", orderId));
			}
		}

		return updatenum;
	}

	//获取 负责人操作的对应字段
	public String getprincipalField(Integer orderProcessType) {
		String principalField = "";
		if (orderProcessType == SALES_PROCESS) {
			//销售
			principalField = "salesOpid";
		} else if (orderProcessType == FIRSTTRIAL_PROCESS) {
			//初审
			principalField = "trialOpid";
		} else if (orderProcessType == RECEPTION_PROCESS) {
			//前台
			principalField = "receptionOpid";
		} else if (orderProcessType == VISA_PROCESS) {
			// 签证 
			principalField = "visaOpid";
		} else if (orderProcessType == AFTERMARKET_PROCESS) {
			//售后
			principalField = "aftermarketOpid";
		}
		return principalField;
	}
}
