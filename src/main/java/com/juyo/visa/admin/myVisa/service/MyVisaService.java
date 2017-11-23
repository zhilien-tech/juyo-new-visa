package com.juyo.visa.admin.myVisa.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.myVisa.form.MyVisaListDataForm;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.page.OffsetPager;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * 我的签证 service
 */
@IocBean
public class MyVisaService extends BaseService<TOrderJpEntity> {

	//申请人办理中的签证
	public Object myVisaListData(MyVisaListDataForm form, HttpSession session) {

		Map<String, Object> result = Maps.newHashMap();

		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		form.setUserid(userId);
		Sql sql = form.sql(sqlManager);

		Integer pageNumber = form.getPageNumber();
		Integer pageSize = form.getPageSize();
		Pager pager = new OffsetPager((pageNumber - 1) * pageSize, pageSize);

		pager.setRecordCount((int) Daos.queryCount(nutDao, sql.toString()));
		sql.setPager(pager);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);

		@SuppressWarnings("unchecked")
		List<Record> records = (List<Record>) sql.getResult();
		//格式化日期
		DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
		for (Record record : records) {
			//资料类型
			Integer ostatus = (Integer) record.get("orderstatus");
			for (JPOrderStatusEnum jpos : JPOrderStatusEnum.values()) {
				if (!Util.isEmpty(ostatus) && ostatus.equals(jpos.intKey())) {
					record.put("orderstatus", jpos.value());
				}
			}
			if (!Util.isEmpty(record.get("sendvisadate"))) {
				Date sendVisaDate = (Date) record.get("sendvisadate");
				record.put("sendvisadate", format.format(sendVisaDate));
			}
			if (!Util.isEmpty(record.get("outvisadate"))) {
				Date outVisaDate = (Date) record.get("outvisadate");
				record.put("outvisadate", format.format(outVisaDate));
			}
		}
		result.put("myVisaData", records);

		return result;
	}
}
