/**
 * SimulateJapanModule.java
 * com.juyo.visa.admin.simulate.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.simulate.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.simulate.form.JapanSimulatorForm;
import com.juyo.visa.common.base.impl.QiniuUploadServiceImpl;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.enums.JapanVisaStatusEnum;
import com.juyo.visa.common.util.ResultObject;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TOrderTripJpEntity;
import com.uxuexi.core.common.util.DateUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年11月9日 	 
 */
@IocBean
public class SimulateJapanService extends BaseService<TOrderJpEntity> {

	@Inject
	private QiniuUploadServiceImpl qiniuUploadService;

	/**
	 * 查看是否有可以执行的订单
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResultObject fetchJapanOrder() {

		//日本订单信息
		List<TOrderJpEntity> orderlist = dbDao.query(TOrderJpEntity.class,
				Cnd.where("visastatus", "=", JPOrderStatusEnum.AUTO_FILL_FORM_ING.intKey()), null);
		if (!Util.isEmpty(orderlist) && orderlist.size() > 0) {
			DateFormat dateFormat = new SimpleDateFormat(DateUtil.FORMAT_YYYY_MM_DD);
			TOrderJpEntity jporder = orderlist.get(0);
			//日本订单id
			Integer orderjpid = jporder.getId();
			Integer orderId = jporder.getOrderId();
			//用来存放信息
			Map<String, Object> map = Maps.newTreeMap();
			//订单信息
			TOrderEntity orderinfo = null;
			if (!Util.isEmpty(orderId)) {
				orderinfo = dbDao.fetch(TOrderEntity.class, orderId.longValue());
				//日本出行信息
				TOrderTripJpEntity ordertripjp = dbDao.fetch(TOrderTripJpEntity.class,
						Cnd.where("orderId", "=", orderjpid));
				if (!Util.isEmpty(ordertripjp)) {
					Date goDate = ordertripjp.getGoDate();
					if (!Util.isEmpty(goDate)) {
						map.put("entryDate", dateFormat.format(goDate));
					}
					Date returnDate = ordertripjp.getReturnDate();
					if (!Util.isEmpty(returnDate)) {
						map.put("leaveDate", dateFormat.format(returnDate));
					}
				}
			}
			map.put("visaAccount", "1507-001");
			map.put("visaPasswd", "kintsu0821");
			//指定番号
			map.put("agentNo", "");
			if (!Util.isEmpty(jporder)) {
				map.put("visaType1", jporder.getVisaType() + "");
				map.put("VISA_STAY_PREF_2", false);
				map.put("VISA_STAY_PREF_3", false);
				map.put("VISA_STAY_PREF_4", false);
				map.put("VISA_STAY_PREF_5", false);
				map.put("VISA_STAY_PREF_6", false);
				map.put("VISA_STAY_PREF_7", false);
				map.put("VISA_STAY_PREF_47", false);
				//东六县未做
			}
			//放置Map
			if (!Util.isEmpty(map)) {
				ResultObject result = ResultObject.success(map);
				result.addAttribute("oid", orderjpid);
				return result;
			}
		}
		return ResultObject.fail("暂无可执行的任务");
	}

	/**
	 * 将准备提交的任务修改为提交中
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param cid
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object ds160Japan(Integer cid) {

		if (Util.isEmpty(cid)) {
			return ResultObject.fail("任务id不能为空！");
		}
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, cid.longValue());
		if (!Util.isEmpty(orderjp)) {
			return ResultObject.fail("任务不存在！");
		}

		try {
			Integer visastatus = orderjp.getVisastatus();
			if (!Util.isEmpty(visastatus) || JapanVisaStatusEnum.ZHAOBAOZHONG.intKey() != visastatus) {
				return ResultObject.fail("准备提交大使馆的任务方可提交");
			}
			dbDao.update(TOrderJpEntity.class, Chain.make("visastatus", JapanVisaStatusEnum.YIFAZHAOBAO.intKey()),
					Cnd.where("id", "=", cid));
		} catch (Exception e) {
			return ResultObject.fail("提交失败，请稍后重试");
		}
		return ResultObject.success(orderjp);

	}

	/**
	 * 上传归国报告
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param cid
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object uploadJapan(Integer cid, JapanSimulatorForm form, File file) {
		if (Util.isEmpty(cid)) {
			return ResultObject.fail("任务id不能为空！");
		}

		TOrderJpEntity order = dbDao.fetch(TOrderJpEntity.class, cid.longValue());

		if (Util.isEmpty(order)) {
			return ResultObject.fail("任务不存在！");
		}
		//保存受付番号
		order.setAcceptDesign(form.getAcceptanceNumber());
		dbDao.update(order);

		String visaFile = null;
		try {
			InputStream inputStream = new FileInputStream(file);
			String originalFilename = file.getName();
			String suffix = Files.getSuffix(originalFilename);
			if (Util.isEmpty(suffix) || suffix.length() < 2) {
				return ResultObject.fail("文件名错误");
			}
			Integer status = order.getVisastatus();
			//验证提交状态
			if (Util.isEmpty(status) || JapanVisaStatusEnum.ZHAOBAOZHONG.intKey() != status) {
				return ResultObject.fail("已提交的任务方可进行文件上传！");
			}
			suffix = suffix.substring(1);
			visaFile = CommonConstants.IMAGES_SERVER_ADDR + qiniuUploadService.uploadImage(inputStream, suffix, null);

			//为客户设置文件地址，签证状态改为'已提交'
			order.setVisastatus(JapanVisaStatusEnum.YIFAZHAOBAO.intKey());
			order.setReturnHomeFileUrl(visaFile);
			dbDao.update(order);
		} catch (Exception e) {
			return ResultObject.fail("文件上传失败,请稍后重试！");
		}
		return ResultObject.success(visaFile);

	}

}
