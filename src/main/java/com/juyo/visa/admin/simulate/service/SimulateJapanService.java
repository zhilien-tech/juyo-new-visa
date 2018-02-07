/**
 * SimulateJapanModule.java
 * com.juyo.visa.admin.simulate.service
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.simulate.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.simulate.enums.ErrorCodeEnum;
import com.juyo.visa.admin.simulate.form.JapanSimulateErrorForm;
import com.juyo.visa.admin.simulate.form.JapanSimulatorForm;
import com.juyo.visa.common.base.impl.QiniuUploadServiceImpl;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.BusinessScopesEnum;
import com.juyo.visa.common.enums.IsYesOrNoEnum;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.util.ResultObject;
import com.juyo.visa.entities.TComBusinessscopeEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TOrderTripJpEntity;
import com.juyo.visa.entities.TOrderTripMultiJpEntity;
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
		//查询是否有需要自动填表的订单
		String sqlstring = sqlManager.get("select_simulate_jp_order");
		Sql sql = Sqls.create(sqlstring);
		List<Record> orderjplist = dbDao.query(sql,
				Cnd.where("tr.status", "=", JPOrderStatusEnum.READYCOMMING.intKey()), null);
		if (!Util.isEmpty(orderjplist) && orderjplist.size() > 0) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			//获取第一条
			Record record = orderjplist.get(0);
			//用来存放信息
			Map<String, Object> map = Maps.newTreeMap();
			TOrderTripJpEntity ordertripjp = dbDao.fetch(TOrderTripJpEntity.class,
					Cnd.where("orderId", "=", record.get("orderjpid")));
			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, Cnd.where("id", "=", record.get("orderjpid")));
			if (!Util.isEmpty(ordertripjp)) {
				if (ordertripjp.getTripType().equals(1)) {
					Date goDate = ordertripjp.getGoDate();
					if (!Util.isEmpty(goDate)) {
						map.put("entryDate", dateFormat.format(goDate));
					}
					Date returnDate = ordertripjp.getReturnDate();
					if (!Util.isEmpty(returnDate)) {
						map.put("leaveDate", dateFormat.format(returnDate));
					}
				} else {
					//多程
					List<TOrderTripMultiJpEntity> multitripjp = dbDao.query(TOrderTripMultiJpEntity.class,
							Cnd.where("tripid", "=", ordertripjp.getId()), null);
					if (!Util.isEmpty(multitripjp)) {
						//第一程为出发日期
						Date departureDate = multitripjp.get(0).getDepartureDate();
						if (!Util.isEmpty(departureDate)) {
							map.put("entryDate", dateFormat.format(departureDate));
						}
						//最后一程为返回日期
						Date leavedate = multitripjp.get(multitripjp.size() - 1).getDepartureDate();
						if (!Util.isEmpty(leavedate)) {
							map.put("leaveDate", dateFormat.format(leavedate));
						}
					}
				}
				TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
				map.put("ordernum", orderinfo.getOrderNum());
			}
			//登录的用户名密码
			map.put("visaAccount", "1507-001");
			map.put("visaPasswd", "kintsu0821");
			//指定番号
			String agentNo = "";
			TCompanyEntity company = dbDao.fetch(TCompanyEntity.class, Integer.valueOf(record.getString("sendsignid"))
					.longValue());
			if (company.getIsCustomer().equals(IsYesOrNoEnum.YES.intKey())) {
				agentNo = company.getCdesignNum();
			} else {
				TComBusinessscopeEntity comBusiness = dbDao.fetch(
						TComBusinessscopeEntity.class,
						Cnd.where("comId", "=", company.getId()).and("countryId", "=",
								BusinessScopesEnum.JAPAN.intKey()));
				if (!Util.isEmpty(comBusiness)) {
					agentNo = comBusiness.getDesignatedNum();
				}
			}
			if (!Util.isEmpty(orderjp)) {
				String visaCounty = orderjp.getVisaCounty();
				if (!Util.isEmpty(visaCounty)) {
					if (visaCounty.indexOf("冲绳") != -1) {
						map.put("VISA_STAY_PREF_47", true);
					} else {
						map.put("VISA_STAY_PREF_47", false);
					}
					if (visaCounty.indexOf("青森") != -1) {
						map.put("VISA_STAY_PREF_2", true);
					} else {
						map.put("VISA_STAY_PREF_2", false);
					}
					if (visaCounty.indexOf("岩手") != -1) {
						map.put("VISA_STAY_PREF_3", true);
					} else {
						map.put("VISA_STAY_PREF_3", false);
					}
					if (visaCounty.indexOf("宫城") != -1) {
						map.put("VISA_STAY_PREF_4", true);
					} else {
						map.put("VISA_STAY_PREF_4", false);
					}
					if (visaCounty.indexOf("秋田") != -1) {
						map.put("VISA_STAY_PREF_5", true);
					} else {
						map.put("VISA_STAY_PREF_5", false);
					}
					if (visaCounty.indexOf("山形") != -1) {
						map.put("VISA_STAY_PREF_6", true);
					} else {
						map.put("VISA_STAY_PREF_6", false);
					}
					if (visaCounty.indexOf("福岛") != -1) {
						map.put("VISA_STAY_PREF_7", true);
					} else {
						map.put("VISA_STAY_PREF_7", false);
					}
				} else {
					map.put("VISA_STAY_PREF_2", false);
					map.put("VISA_STAY_PREF_3", false);
					map.put("VISA_STAY_PREF_4", false);
					map.put("VISA_STAY_PREF_5", false);
					map.put("VISA_STAY_PREF_6", false);
					map.put("VISA_STAY_PREF_7", false);
					map.put("VISA_STAY_PREF_47", false);
				}
			}
			map.put("agentNo", agentNo);
			map.put("visaType1", record.get("visatype"));

			String applysqlstr = sqlManager.get("get_applicantinfo_simulate_from_order");
			Sql applysql = Sqls.create(applysqlstr);
			applysql.setParam("orderid", record.get("orderjpid"));
			List<Record> applicants = dbDao.query(applysql, null, null);
			if (!Util.isEmpty(applicants)) {
				Record applicantrecord = applicants.get(0);
				String fullname = "";
				String fullnameen = "";
				if (!Util.isEmpty(applicantrecord.get("firstname"))) {
					fullname += applicantrecord.getString("firstname");
				}
				if (!Util.isEmpty(applicantrecord.get("lastname"))) {
					fullname += applicantrecord.getString("lastname");
				}
				if (!Util.isEmpty(applicantrecord.get("firstnameen"))) {
					fullnameen += applicantrecord.getString("firstnameen");
				}
				if (!Util.isEmpty(applicantrecord.get("lastnameen"))) {
					fullnameen += applicantrecord.getString("lastnameen");
				}
				map.put("proposerNameCN", fullname);
				map.put("proposerNameEN", fullnameen);
				map.put("applicantCnt", (applicants.size() - 1) + "");//除申请人之外的人数
			}
			map.put("excelUrl", record.get("excelurl"));//excel地址
			//放置Map
			if (!Util.isEmpty(map)) {
				ResultObject result = ResultObject.success(map);
				result.addAttribute("oid", record.get("orderjpid"));
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
		if (Util.isEmpty(orderjp)) {
			return ResultObject.fail("任务不存在！");
		}
		TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
		try {
			Integer status = orderinfo.getStatus();
			if (Util.isEmpty(status) || JPOrderStatusEnum.READYCOMMING.intKey() != status) {
				return ResultObject.fail("准备提交大使馆的任务方可提交");
			}
			//将订单设置为提交中
			orderinfo.setStatus(JPOrderStatusEnum.COMMITING.intKey());
			dbDao.update(orderinfo);
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
		TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, order.getOrderId().longValue());
		String visaFile = null;
		try {
			InputStream inputStream = new FileInputStream(file);
			String originalFilename = file.getName();
			String suffix = Files.getSuffix(originalFilename);
			if (Util.isEmpty(suffix) || suffix.length() < 2) {
				return ResultObject.fail("文件名错误");
			}
			Integer status = orderinfo.getStatus();
			//验证提交状态
			if (Util.isEmpty(status) || JPOrderStatusEnum.COMMITING.intKey() != status) {
				return ResultObject.fail("已提交的任务方可进行文件上传！");
			}
			suffix = suffix.substring(1);
			visaFile = CommonConstants.IMAGES_SERVER_ADDR + qiniuUploadService.uploadImage(inputStream, suffix, null);

			//为客户设置文件地址，签证状态改为'已提交'
			orderinfo.setStatus(JPOrderStatusEnum.AUTO_FILL_FORM_ED.intKey());
			dbDao.update(orderinfo);
			order.setReturnHomeFileUrl(visaFile);
			dbDao.update(order);
		} catch (Exception e) {
			return ResultObject.fail("文件上传失败,请稍后重试！");
		}
		return ResultObject.success(visaFile);

	}

	public Object japanErrorHandle(JapanSimulateErrorForm form, Long cid) {
		if (!Util.isEmpty(cid) && cid > 0) {
			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, cid);
			int errorCode = form.getErrorCode();
			String errorMsg = form.getErrorMsg();
			orderjp.setErrorcode(errorCode);
			orderjp.setErrormsg(errorMsg);
			dbDao.update(orderjp);
			TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
			//提交失败
			if (errorCode == ErrorCodeEnum.completedNumberFail.intKey()) {
				orderinfo.setStatus(JPOrderStatusEnum.COMMINGFAIL.intKey());
			} else if (errorCode == ErrorCodeEnum.persionNameList.intKey()) {
				//个人名簿生成失败
				orderinfo.setStatus(JPOrderStatusEnum.AUTO_FILL_FORM_FAILED.intKey());
			} else if (errorCode == ErrorCodeEnum.comeReport.intKey()) {
				//归国报告上传失败
				orderinfo.setStatus(JPOrderStatusEnum.JAPANREPORTFAIL.intKey());
			} else {
				orderinfo.setStatus(JPOrderStatusEnum.AUTO_FILL_FORM_FAILED.intKey());
			}
			orderinfo.setUpdateTime(new Date());
			//更新订单状态为发招保失败
			dbDao.update(orderinfo);
		}
		return null;
	}

	public Object agentDownload(String excelurl, HttpServletResponse response) {
		InputStream is = null;
		OutputStream out = null;
		try {
			URL url = new URL(excelurl);
			URLConnection conn = url.openConnection();
			is = conn.getInputStream();

			String filename = excelurl.substring(excelurl.lastIndexOf('/') + 1);
			if (Util.isEmpty(filename)) {//如果获取不到文件名称 
				for (int i = 0;; i++) {
					String mine = conn.getHeaderField(i);
					if (mine == null)
						break;
					if ("content-disposition".equals(conn.getHeaderFieldKey(i).toLowerCase())) {
						Matcher m = Pattern.compile(".*filename=(.*)").matcher(mine.toLowerCase());
						if (m.find()) {
							filename = m.group(1);
						}
					}
				}
			}
			if (Util.isEmpty(filename)) {
				filename = UUID.randomUUID() + ".tmp";//默认取一个文件名	
			}
			out = response.getOutputStream();
			response.reset();
			response.setContentType("application/octet-stream");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", filename));
			byte[] buffer = new byte[2048];
			int count = 0;
			while ((count = is.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}
			out.flush();
			response.flushBuffer();
			out.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (!Util.isEmpty(is)) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (!Util.isEmpty(out)) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}//end of outter try
		return null;
	}

}
