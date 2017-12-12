/**
 * VisaJapanSimulateService.java
 * com.juyo.visa.admin.visajp.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.juyo.visa.admin.visajp.service;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.juyo.visa.common.base.impl.QiniuUploadServiceImpl;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TApplicantOrderJpEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年11月15日 	 
 */
@IocBean
public class VisaJapanSimulateService extends BaseService<TOrderJpEntity> {

	@Inject
	private DownLoadVisaFileService downLoadVisaFileService;
	@Inject
	private QiniuUploadServiceImpl qiniuUploadService;

	/**
	 * 发招宝、招宝变更、招宝取消状态
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid
	 * @param visastatus
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object sendInsurance(Integer orderid, Integer visastatus) {
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid.longValue());
		TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
		orderinfo.setStatus(visastatus);
		//orderjp.setVisastatus(visastatus);
		return dbDao.update(orderinfo);
	}

	/**
	 * 下载文件
	 * <p>
	 * TODO 签证页面文件下载
	 *
	 * @param orderid
	 * @param response
	 * @return TODO签证页面文件下载
	 */
	public Object downloadFile(Long orderid, HttpServletResponse response) {
		try {
			//查询日本订单
			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid);
			//获取打包的文件
			byte[] byteArray = downLoadVisaFileService.generateFile(orderjp).toByteArray();
			//获取订单信息，准备文件名称
			TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
			//主申请人姓名
			String mainapplicantname = "";
			//查询申请人信息
			List<TApplicantOrderJpEntity> jpapplicants = dbDao.query(TApplicantOrderJpEntity.class,
					Cnd.where("orderId", "=", orderid), null);
			for (TApplicantOrderJpEntity applicantjp : jpapplicants) {
				if (!Util.isEmpty(applicantjp.getIsMainApplicant()) && applicantjp.getIsMainApplicant().equals(1)) {
					TApplicantEntity applicat = dbDao.fetch(TApplicantEntity.class, applicantjp.getApplicantId()
							.longValue());
					mainapplicantname = applicat.getFirstName() + applicat.getLastName();
				}
			}
			String filename = "";
			filename += orderinfo.getOrderNum();
			//filename += ".zip";
			//新需求只下载PDF文件
			filename += mainapplicantname;
			filename += ".pdf";
			//将文件进行编码
			String fileName = URLEncoder.encode(filename, "UTF-8");
			//设置下载的响应头
			//			response.setContentType("application/zip");
			response.setContentType("application/octet-stream");
			response.setHeader("Set-Cookie", "fileDownload=true; path=/");
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);// 设置文件名
			//将字节流相应到浏览器（下载）
			IOUtils.write(byteArray, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();

		}
		return null;
	}

	public Object uploadVisaPic(File file, HttpServletRequest request) {
		Map<String, Object> result = qiniuUploadService.ajaxUploadImage(file);
		file.delete();
		result.put("data", CommonConstants.IMAGES_SERVER_ADDR + result.get("data"));
		return result;

	}

}
