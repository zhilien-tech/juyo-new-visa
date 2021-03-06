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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;

import com.google.common.collect.Maps;
import com.juyo.visa.admin.orderUS.service.OrderUSViewService;
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
import com.juyo.visa.entities.TOrderFillformLogEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TOrderLogsEntity;
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
	@Inject
	private OrderUSViewService orderUSViewService;

	private Lock lock = new ReentrantLock();

	/*private VisaInfoWSHandler visaInfoWSHandler = (VisaInfoWSHandler) SpringContextUtil.getBean("myVisaInfoHander",
			VisaInfoWSHandler.class);*/

	/**
	 * 查看是否有可以执行的订单
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResultObject fetchJapanOrder() {
		//获取需要发、变更、取消招宝的所有订单
		Map<String, Object> map = fetchAllSendOrders();
		if (!Util.isEmpty(map)) {
			Record record = (Record) map.get("record");

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
			//东北六县和过去是否访问处理
			sixCountyHandler(map, record);
			map.put("agentNo", agentNo);
			map.put("visaType1", record.get("visatype"));
			map.put("visaVisitType1", record.get("isvisit"));

			//申请人信息处理
			applysHandler(map, record);

			map.put("excelUrl", record.get("excelurl"));//excel地址
			//放置Map
			ResultObject result = ResultObject.success(map);
			result.addAttribute("oid", record.get("orderjpid"));
			return result;

		}

		return ResultObject.fail("暂无可执行的任务");
	}

	/**
	 * 申请人信息处理放到map中
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param map
	 * @param record
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object applysHandler(Map<String, Object> map, Record record) {
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
		return null;
	}

	/**
	 * 获取需要发、变更、取消招宝的所有订单，更改状态为提交中
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Map<String, Object> fetchAllSendOrders() {

		lock.lock();

		//用来存放信息
		Map<String, Object> map = Maps.newTreeMap();
		try {
			//long start = System.currentTimeMillis();
			//查询是否有需要自动填表的订单
			String sqlstring = sqlManager.get("select_simulate_jp_order");
			Sql sql = Sqls.create(sqlstring);
			//查询发招宝、招宝变更中、招宝取消中的订单
			Integer[] orderstatus = { JPOrderStatusEnum.READYCOMMING.intKey(),
					JPOrderStatusEnum.BIANGENGZHONG.intKey(), JPOrderStatusEnum.QUXIAOZHONG.intKey(),
					JPOrderStatusEnum.AUTO_FILL_FORM_ING.intKey() };
			List<Record> orderjplist = dbDao.query(sql,
					Cnd.where("tr.status", "in", orderstatus).orderBy("toj.zhaobaotime", "ASC"), null);
			/*List<Record> orderjplist = dbDao.query(sql,
							Cnd.where("tr.status", "=", JPOrderStatusEnum.READYCOMMING.intKey()), null);*/
			if (!Util.isEmpty(orderjplist) && orderjplist.size() > 0) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				//获取第一条
				Record record = orderjplist.get(0);

				System.out.println(record.get("orderjpid") + "--------");

				map.put("record", record);

				TOrderTripJpEntity ordertripjp = dbDao.fetch(TOrderTripJpEntity.class,
						Cnd.where("orderId", "=", record.get("orderjpid")));
				TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class,
						Cnd.where("id", "=", record.get("orderjpid")));
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
					Integer status = orderinfo.getStatus();
					map.put("orderstatus", status);
					//将订单设置为提交中
					if (JPOrderStatusEnum.READYCOMMING.intKey() == status
							|| JPOrderStatusEnum.AUTO_FILL_FORM_ING.intKey() == status) {
						//如果是发招宝状态，将订单状态变为提交中
						orderinfo.setStatus(JPOrderStatusEnum.COMMITING.intKey());
					} else if (JPOrderStatusEnum.BIANGENGZHONG.intKey() == status) {
						//如果是招宝变更状态变为网站变更中
						orderinfo.setStatus(JPOrderStatusEnum.WANGZHANBIANGENGZHONG.intKey());
					} else if (JPOrderStatusEnum.QUXIAOZHONG.intKey() == status) {
						//如果是招宝取消状态，变为网站招宝取消中
						orderinfo.setStatus(JPOrderStatusEnum.WANGZHANQUXIAOZHONG.intKey());
					}

					//更新发招宝时间
					if (!Util.isEmpty(orderjp)) {
						orderjp.setZhaobaotime(new Date());
						dbDao.update(orderjp);
					}

					dbDao.update(orderinfo);
					map.put("ordernum", orderinfo.getOrderNum());
				}
			}
			//long endTime = System.currentTimeMillis();
		} catch (Exception e) {

		} finally {
			lock.unlock();
		}

		//System.out.println("走完了=====" + (endTime - start));
		return map;
	}

	/**
	 * 东北六县和是否访问过城市处理放到map中
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param map
	 * @param record
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object sixCountyHandler(Map<String, Object> map, Record record) {
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, Cnd.where("id", "=", record.get("orderjpid")));
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
			map.put("acceptdesign", orderjp.getAcceptDesign());

			String threeCounty = orderjp.getThreeCounty();
			if (!Util.isEmpty(threeCounty)) {
				if (threeCounty.indexOf("岩手") != -1) {
					map.put("VISA_VISIT_PREF_3", true);
				} else {
					map.put("VISA_VISIT_PREF_3", false);
				}
				if (threeCounty.indexOf("宫城") != -1) {
					map.put("VISA_VISIT_PREF_4", true);
				} else {
					map.put("VISA_VISIT_PREF_4", false);
				}
				if (threeCounty.indexOf("福岛") != -1) {
					map.put("VISA_VISIT_PREF_7", true);
				} else {
					map.put("VISA_VISIT_PREF_7", false);
				}
			} else {
				map.put("VISA_VISIT_PREF_3", false);
				map.put("VISA_VISIT_PREF_4", false);
				map.put("VISA_VISIT_PREF_7", false);
			}
		}
		return null;
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
			//记录发招宝成功状态
			orderinfo.setZhaobaocomplete(IsYesOrNoEnum.YES.intKey());
			dbDao.update(orderinfo);
			order.setReturnHomeFileUrl(visaFile);
			dbDao.update(order);
		} catch (Exception e) {
			return ResultObject.fail("文件上传失败,请稍后重试！");
		}
		/*//消息通知
		try {
			visaInfoWSHandler.broadcast(new TextMessage(""));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return ResultObject.success(visaFile);

	}

	//发送短信
	public Object sendSMS(String ordernum, String orderstatus) throws IOException {
		List<String> readLines = IOUtils.readLines(getClass().getClassLoader().getResourceAsStream(
				"messagetmp/autofilljp_sms.txt"));
		StringBuilder tmp = new StringBuilder();
		for (String line : readLines) {
			tmp.append(line);
		}
		String telephone = "15600027715";
		String telephone2 = "18612131435";
		String result = "";
		String smsContent = tmp.toString();
		smsContent = smsContent.replace("${ordernum}", ordernum).replace("${orderstatus}", orderstatus);
		System.out.println("短信分享内容：" + smsContent);
		result = orderUSViewService.sendSMS(telephone, smsContent);
		result = orderUSViewService.sendSMS(telephone2, smsContent);

		return result;

	}

	public Object japanErrorHandle(JapanSimulateErrorForm form, Long cid) {
		int errorCode = form.getErrorCode();
		int orderstatus = form.getOrderstatus();
		String errorMsg = form.getErrorMsg();
		System.out.println("cid:" + cid);
		System.out.println("orderstatus:" + orderstatus);
		System.out.println("errorCode:" + errorCode);
		System.out.println("errorMsg============:" + errorMsg);

		TOrderEntity orderinfo = null;
		TOrderJpEntity orderjp = null;
		//订单号正常的情况下，查询出对应的order和orderjp
		if (!Util.isEmpty(cid) && cid > 0) {
			orderjp = dbDao.fetch(TOrderJpEntity.class, cid);
			orderjp.setErrorcode(errorCode);
			orderjp.setErrormsg(errorMsg);
			dbDao.update(orderjp);
			orderinfo = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
			//如果cookie过期，直接返回，等待定时任务获取
			if (Util.eq("cookie expired", errorMsg)) {
				System.out.println("cookie超时！！！");
				try {
					sendSMS(orderinfo.getOrderNum(), "cookie过期了!!!");
				} catch (IOException e) {

					// TODO Auto-generated catch block
					e.printStackTrace();

				}
				return null;
			}
			if (errorMsg.contains("applicant_count")) {
				try {
					sendSMS(orderinfo.getOrderNum(), "人数出错了!!!");
				} catch (IOException e) {

					// TODO Auto-generated catch block
					e.printStackTrace();

				}
				return null;
			}

		}
		//订单号为-1时，errorMsg为订单号，依此可查询出对应的order和orderjp
		if (Util.eq(-1, cid)) {
			if (!Util.isEmpty(errorMsg)) {
				orderinfo = dbDao.fetch(TOrderEntity.class, Cnd.where("orderNum", "=", errorMsg));
				orderjp = dbDao.fetch(TOrderJpEntity.class, Cnd.where("orderId", "=", orderinfo.getId()));

				orderjp.setErrorcode(errorCode);
				orderjp.setErrormsg(errorMsg);
				dbDao.update(orderjp);
			}
		}

		//发招宝时出现错误，errorCode为1或10时直接重新跑，如果为2时说明有受付番号，这时走16，省略前边一部分步骤
		if (orderstatus == JPOrderStatusEnum.READYCOMMING.intKey()) {//发招宝失败 18
			if (errorCode == 10) {//cid=1
				System.out.println("发招宝时cid==-1");
				orderinfo.setStatus(JPOrderStatusEnum.READYCOMMING.intKey());

				if (!Util.isEmpty(orderjp)) {
					orderjp.setZhaobaotime(new Date());
					dbDao.update(orderjp);
				}
			} else if (errorCode == 1) {//没有收付番号，发招宝按钮依然亮
				System.out.println("不仅失败了，收付番号也没有");
				orderinfo.setStatus(JPOrderStatusEnum.READYCOMMING.intKey());

				if (!Util.isEmpty(orderjp)) {
					orderjp.setZhaobaotime(new Date());
					dbDao.update(orderjp);
				}
			} else {
				System.out.println("虽然失败了，但收付番号还是有的");
				//orderinfo.setZhaobaocomplete(IsYesOrNoEnum.YES.intKey());
				//orderinfo.setStatus(JPOrderStatusEnum.AUTO_FILL_FORM_ING.intKey());

				//有收付番号按成功算
				orderinfo.setZhaobaocomplete(IsYesOrNoEnum.YES.intKey());
				orderinfo.setZhaobaoupdate(IsYesOrNoEnum.YES.intKey());
				orderinfo.setStatus(JPOrderStatusEnum.AUTO_FILL_FORM_ED.intKey());

				Integer visaOpid = orderinfo.getVisaOpid();
				if (!Util.isEmpty(visaOpid)) {
					//插入日志
					TOrderLogsEntity logs = new TOrderLogsEntity();
					logs.setCreateTime(new Date());
					logs.setOpId(visaOpid);
					logs.setOrderId(orderinfo.getId());
					logs.setOrderStatus(orderinfo.getStatus());
					logs.setUpdateTime(new Date());
					dbDao.insert(logs);
				}

			}

		}
		//招宝变更时出错，如果errorCode为10直接重新跑，为1时没有受付番号重新发招宝，为2时有受付番号走16流程
		else if (orderstatus == JPOrderStatusEnum.BIANGENGZHONG.intKey()) {//招宝变更失败21
			if (errorCode == 10) {
				System.out.println("变更时cid==-1");
				orderinfo.setZhaobaocomplete(IsYesOrNoEnum.NO.intKey());
				orderinfo.setZhaobaoupdate(IsYesOrNoEnum.NO.intKey());
				orderinfo.setReceptionOpid(1);
				orderinfo.setStatus(JPOrderStatusEnum.BIANGENGZHONG.intKey());

				if (!Util.isEmpty(orderjp)) {
					orderjp.setZhaobaotime(new Date());
					dbDao.update(orderjp);
				}
			} else if (errorCode == 1) {//没有收付番号，发招宝按钮依然亮
				System.out.println("变更失败，受付番号也没有");
				orderinfo.setZhaobaocomplete(IsYesOrNoEnum.NO.intKey());
				orderinfo.setZhaobaoupdate(IsYesOrNoEnum.NO.intKey());
				orderinfo.setReceptionOpid(1);
				orderinfo.setStatus(JPOrderStatusEnum.READYCOMMING.intKey());

				if (!Util.isEmpty(orderjp)) {
					orderjp.setZhaobaotime(new Date());
					dbDao.update(orderjp);
				}
			} else {
				System.out.println("虽然变更失败了，但收付番号还是有的");

				/*orderinfo.setZhaobaocomplete(IsYesOrNoEnum.YES.intKey());
				orderinfo.setZhaobaoupdate(IsYesOrNoEnum.NO.intKey());
				orderinfo.setReceptionOpid(1);
				orderinfo.setStatus(JPOrderStatusEnum.AUTO_FILL_FORM_ING.intKey());*/

				//有收付番号按成功算
				orderinfo.setZhaobaocomplete(IsYesOrNoEnum.YES.intKey());
				orderinfo.setZhaobaoupdate(IsYesOrNoEnum.YES.intKey());
				orderinfo.setStatus(JPOrderStatusEnum.YIBIANGENG.intKey());

				Integer visaOpid = orderinfo.getVisaOpid();
				if (!Util.isEmpty(visaOpid)) {
					//插入日志
					TOrderLogsEntity logs = new TOrderLogsEntity();
					logs.setCreateTime(new Date());
					logs.setOpId(visaOpid);
					logs.setOrderId(orderinfo.getId());
					logs.setOrderStatus(orderinfo.getStatus());
					logs.setUpdateTime(new Date());
					dbDao.insert(logs);
				}
			}

		} else if (orderstatus == JPOrderStatusEnum.AUTO_FILL_FORM_ING.intKey()) {//失败重发时再失败
			System.out.println("失败重发之后又失败了，继续发");
			orderinfo.setZhaobaocomplete(IsYesOrNoEnum.YES.intKey());
			orderinfo.setStatus(JPOrderStatusEnum.AUTO_FILL_FORM_ING.intKey());
			if (!Util.isEmpty(orderjp)) {
				orderjp.setZhaobaotime(new Date());
				dbDao.update(orderjp);
			}
		} else if (orderstatus == JPOrderStatusEnum.QUXIAOZHONG.intKey()) {//招宝取消失败 24
			orderinfo.setStatus(JPOrderStatusEnum.QUXIAOZHONG.intKey());
			if (!Util.isEmpty(orderjp)) {
				orderjp.setZhaobaotime(new Date());
				dbDao.update(orderjp);
			}
		}

		//出现指定错误时，直接返回失败
		if (errorMsg.contains("氏名") || errorMsg.contains("居住地域") || errorMsg.contains("半角英数字")
				|| errorMsg.contains("停牌")) {
			//orderinfo.setZhaobaocomplete(IsYesOrNoEnum.YES.intKey());
			if (Util.isEmpty(orderinfo.getReceptionOpid())) {
				orderinfo.setStatus(JPOrderStatusEnum.AUTO_FILL_FORM_FAILED.intKey());
			} else {
				orderinfo.setStatus(JPOrderStatusEnum.BIANGENGSHIBAI.intKey());
			}
		}
		int count = 0;
		if (errorMsg.contains("登録可能")) {
			count++;
			//orderinfo.setZhaobaocomplete(IsYesOrNoEnum.YES.intKey());
			if (count > 3) {
				if (Util.isEmpty(orderinfo.getReceptionOpid())) {
					orderinfo.setStatus(JPOrderStatusEnum.AUTO_FILL_FORM_FAILED.intKey());
				} else {
					orderinfo.setStatus(JPOrderStatusEnum.BIANGENGSHIBAI.intKey());
				}
			} else {
				orderinfo.setStatus(JPOrderStatusEnum.AUTO_FILL_FORM_ING.intKey());
				if (!Util.isEmpty(orderjp)) {
					orderjp.setZhaobaotime(new Date());
					dbDao.update(orderjp);
				}
			}
		}

		orderinfo.setUpdateTime(new Date());
		//更新订单状态为发招保失败
		dbDao.update(orderinfo);
		System.out.println("捕捉到错误后改变订单状态为正确状态");
		if (orderinfo.getStatus() == JPOrderStatusEnum.AUTO_FILL_FORM_FAILED.intKey()
				|| orderinfo.getStatus() == JPOrderStatusEnum.BIANGENGSHIBAI.intKey()) {
			Integer visaOpid = orderinfo.getVisaOpid();
			if (!Util.isEmpty(visaOpid)) {
				//添加日志
				TOrderLogsEntity logs = new TOrderLogsEntity();
				logs.setCreateTime(new Date());
				logs.setOpId(visaOpid);
				logs.setOrderId(orderinfo.getId());
				logs.setOrderStatus(orderinfo.getStatus());
				logs.setUpdateTime(new Date());
				dbDao.insert(logs);
			}
		}

		/*if (orderinfo.getStatus() == JPOrderStatusEnum.AUTO_FILL_FORM_FAILED.intKey()) {
				//消息通知
				try {
					visaInfoWSHandler.broadcast(new TextMessage(""));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}*/

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

	/**
	 * 更新受付番号
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateAcceptanceNumber(JapanSimulatorForm form) {
		System.out.println("更新收付番号CID:" + form.getCid());
		if (Util.isEmpty(form.getCid())) {
			return ResultObject.fail("任务id不能为空！");
		}

		TOrderJpEntity order = dbDao.fetch(TOrderJpEntity.class, form.getCid());

		if (Util.isEmpty(order)) {
			return ResultObject.fail("任务不存在！");
		}
		if (!Util.isEmpty(form.getAcceptanceNumber())) {
			order.setAcceptDesign(form.getAcceptanceNumber());
		}
		dbDao.update(order);
		return ResultObject.success(order);
	}

	/**
	 * 添加日志
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object insertLog(JapanSimulatorForm form) {
		TOrderFillformLogEntity filllog = new TOrderFillformLogEntity();
		filllog.setOrderid(form.getCid().intValue());
		filllog.setLogdate(new Date());
		filllog.setContent(form.getContent());
		dbDao.insert(filllog);
		return null;
	}

	/**
	 * 更新为已发招宝
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateYifa(JapanSimulatorForm form) {
		System.out.println("成功之后更新状态的CID:" + form.getCid());
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, form.getCid());
		TOrderEntity order = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
		//order.setStatus(JPOrderStatusEnum.AUTO_FILL_FORM_ED.intKey());
		if (JPOrderStatusEnum.READYCOMMING.intKey() == form.getOrderstatus()
				|| JPOrderStatusEnum.COMMITING.intKey() == form.getOrderstatus()) {
			//记录发招宝成功状态
			order.setZhaobaocomplete(IsYesOrNoEnum.YES.intKey());
			//要收费啦
			order.setZhaobaoupdate(IsYesOrNoEnum.YES.intKey());
			if (Util.isEmpty(order.getReceptionOpid())) {
				System.out.println("发招宝成功了，我要记录了");
				order.setStatus(JPOrderStatusEnum.AUTO_FILL_FORM_ED.intKey());
			} else {
				System.out.println("招宝变更失败之后受付番号没有获取到重新发招宝，变更成功了");
				order.setStatus(JPOrderStatusEnum.YIBIANGENG.intKey());
			}
		} else if (JPOrderStatusEnum.BIANGENGZHONG.intKey() == form.getOrderstatus()) {
			System.out.println("招宝更新成功了");
			order.setStatus(JPOrderStatusEnum.YIBIANGENG.intKey());
			order.setZhaobaocomplete(IsYesOrNoEnum.YES.intKey());
			//要收费啦
			order.setZhaobaoupdate(IsYesOrNoEnum.YES.intKey());
		} else if (JPOrderStatusEnum.QUXIAOZHONG.intKey() == form.getOrderstatus()) {
			System.out.println("招宝取消成功了");
			//记录招宝取消状态
			order.setZhaobaocomplete(IsYesOrNoEnum.NO.intKey());
			if (!Util.isEmpty(order.getReceptionOpid())) {
				order.setReceptionOpid(null);
			}
			//取消收费记录
			order.setZhaobaoupdate(IsYesOrNoEnum.NO.intKey());
			order.setStatus(JPOrderStatusEnum.YIQUXIAO.intKey());
		} else if (JPOrderStatusEnum.AUTO_FILL_FORM_ING.intKey() == form.getOrderstatus()) {
			order.setZhaobaocomplete(IsYesOrNoEnum.YES.intKey());
			//要收费啦
			order.setZhaobaoupdate(IsYesOrNoEnum.YES.intKey());
			if (Util.isEmpty(order.getReceptionOpid())) {
				System.out.println("失败之后重新发招宝成功了");
				order.setStatus(JPOrderStatusEnum.AUTO_FILL_FORM_ED.intKey());
			} else {
				System.out.println("失败之后重新招宝变更成功了");
				order.setStatus(JPOrderStatusEnum.YIBIANGENG.intKey());
			}
		}
		dbDao.update(order);
		Integer visaOpid = order.getVisaOpid();
		if (!Util.isEmpty(visaOpid)) {
			//添加日志
			TOrderLogsEntity logs = new TOrderLogsEntity();
			logs.setCreateTime(new Date());
			logs.setOpId(visaOpid);
			logs.setOrderId(order.getId());
			logs.setOrderStatus(order.getStatus());
			logs.setUpdateTime(new Date());
			dbDao.insert(logs);
		}

		/*//消息通知
		try {
			visaInfoWSHandler.broadcast(new TextMessage(""));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return null;
	}

	/**
	 * 招宝变更失败
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object updateZhaobao(JapanSimulatorForm form) {
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, form.getCid());
		TOrderEntity order = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
		order.setStatus(JPOrderStatusEnum.BIANGENGSHIBAI.intKey());
		dbDao.update(order);
		/*//消息通知
		try {
			visaInfoWSHandler.broadcast(new TextMessage(""));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return null;
	}

	/**
	 * 招宝取消失败
	 * TODO(这里用一句话描述这个方法的作用)
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param form
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object cancelZhaobao(JapanSimulatorForm form) {
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, form.getCid());
		TOrderEntity order = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
		order.setStatus(JPOrderStatusEnum.QUXIAOSHIBAI.intKey());
		dbDao.update(order);
		/*//消息通知
		try {
			visaInfoWSHandler.broadcast(new TextMessage(""));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return null;
	}

	public Object deleteAcceptDesign(JapanSimulatorForm form) {
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, form.getCid());
		orderjp.setAcceptDesign(null);
		dbDao.update(orderjp);
		return null;
	}

}
