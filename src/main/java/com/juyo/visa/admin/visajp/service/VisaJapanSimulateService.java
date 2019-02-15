/**
 * VisaJapanSimulateService.java
 * com.juyo.visa.admin.visajp.service
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
 */

package com.juyo.visa.admin.visajp.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.juyo.visa.admin.changePrincipal.service.ChangePrincipalViewService;
import com.juyo.visa.admin.login.util.LoginUtil;
import com.juyo.visa.admin.order.service.OrderJpViewService;
import com.juyo.visa.admin.visajp.util.TemplateUtil;
import com.juyo.visa.common.base.UploadService;
import com.juyo.visa.common.base.impl.QiniuUploadServiceImpl;
import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.enums.JPOrderProcessTypeEnum;
import com.juyo.visa.common.enums.JPOrderStatusEnum;
import com.juyo.visa.common.enums.PdfTypeEnum;
import com.juyo.visa.common.enums.SimpleVisaTypeEnum;
import com.juyo.visa.entities.TApplicantEntity;
import com.juyo.visa.entities.TApplicantOrderJpEntity;
import com.juyo.visa.entities.TCompanyEntity;
import com.juyo.visa.entities.TOrderEntity;
import com.juyo.visa.entities.TOrderJpEntity;
import com.juyo.visa.entities.TUserEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author 刘旭利
 * @Date 2017年11月15日
 */
@IocBean
public class VisaJapanSimulateService extends BaseService<TOrderJpEntity> {

	@Inject
	private DownLoadVisaFileService downLoadVisaFileService;
	@Inject
	private QiniuUploadServiceImpl qiniuUploadService;
	@Inject
	private OrderJpViewService orderJpViewService;
	@Inject
	private ChangePrincipalViewService changePrincipalViewService;
	@Inject
	private UploadService qiniuUpService;
	/** 辽宁万达下载文件 */
	@Inject
	private LiaoNingWanDaService liaoNingWanDaService;
	//寰宇文件下载
	@Inject
	private HuanyuService huanyuService;
	//辽宁万达文件下载
	@Inject
	private NewLiaoningWandaService newLiaoningWandaService;
	//金桥文件下载
	@Inject
	private JinqiaoService jinqiaoService;
	//康辉文件下载
	@Inject
	private KanghuiService kanghuiService;
	//河南中青旅文件下载
	@Inject
	private HenanZhongqinglvService henanZhongqinglvService;
	//神州文件下载
	@Inject
	private ShenzhouService shenzhouService;
	//风尚文件下载
	@Inject
	private FengshangService fengshangService;
	//上海百城文件下载
	@Inject
	private ShanghaiBaichengService shanghaiBaichengService;
	//上海宝狮文件下载
	@Inject
	private ShanghaiBaoshiService shanghaiBaoshiService;
	//上海中宝文件下载
	@Inject
	private ShanghaiZhongbaoService shanghaiZhongbaoService;
	//和平文件下载
	@Inject
	private HepingService hepingService;
	//安捷文件下载
	@Inject
	private AnjieService anjieService;
	//交通公社文件下载
	@Inject
	private JiaotonggongsheService jiaotonggongsheService;
	//一起游文件下载
	@Inject
	private YiqiyouService yiqiyouService;
	//重庆黄金假期文件下载
	@Inject
	private HuangjinjiaqiService huangjinjiaqiService;
	//浙江康泰文件下载
	@Inject
	private ZhejiangKangtaiService zhejiangKangtaiService;
	//湖北万达新航线文件下载
	@Inject
	private HubeiWandaxinhangxianService hubeiWandaxinhangxianService;
	//北京青旅文件下载
	@Inject
	private BeijingQinglvService beijingQinglvService;

	private static final Integer VISA_PROCESS = JPOrderProcessTypeEnum.VISA_PROCESS.intKey();

	/**
	 * 发招宝、招宝变更、招宝取消状态
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param orderid
	 * @param visastatus
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object sendInsurance(Integer orderid, Integer visastatus, HttpSession session) {
		TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid.longValue());

		if (orderjp.getVisaType() == 14) {
			String resultstr = "签证类型为普通五年多次时不能进行此操作";
			return resultstr;
		} else {
			TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
			orderinfo.setStatus(visastatus);
			if (!Util.isEmpty(visastatus) && visastatus.equals(JPOrderStatusEnum.BIANGENGZHONG.intKey())) {
				// orderjp.setVisastatus(visastatus);
				// 生成excel
				// 申请人信息
				Map<String, Object> tempdata = new HashMap<String, Object>();
				String applysqlstr = sqlManager.get("get_applyinfo_from_filedown_by_orderid_jp");
				Sql applysql = Sqls.create(applysqlstr);
				Cnd cnd = Cnd.NEW();
				cnd.and("taoj.orderId", "=", orderjp.getId());
				List<Record> applyinfo = dbDao.query(applysql, cnd, null);
				tempdata.put("applyinfo", applyinfo);
				// excel导出
				ByteArrayOutputStream excelExport = downLoadVisaFileService.excelExport(tempdata);
				// 生成excel临时文件
				TemplateUtil templateUtil = new TemplateUtil();
				File excelfile = templateUtil.createTempFile(excelExport);
				FileInputStream fileInputStream = null;
				try {
					fileInputStream = new FileInputStream(excelfile);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				String qiniuurl = qiniuUpService.uploadImage(fileInputStream, "xlsx", null);
				// 返回上传后七牛云的路径
				String fileqiniupath = CommonConstants.IMAGES_SERVER_ADDR + qiniuurl;
				// 保存生成的七牛excel路径
				orderjp.setExcelurl(fileqiniupath);
			}
			//更新发招宝时间
			orderjp.setZhaobaotime(new Date());
			dbDao.update(orderjp);

			// 添加日志
			//orderJpViewService.insertLogs(orderinfo.getId(), visastatus, session);
			// 订单负责人变更
			//TUserEntity loginuser = LoginUtil.getLoginUser(session);
			//Integer userId = loginuser.getId();
			//changePrincipalViewService.ChangePrincipal(orderid, JPOrderProcessTypeEnum.SALES_PROCESS.intKey(), userId);
			TUserEntity loginuser = LoginUtil.getLoginUser(session);
			Integer userId = loginuser.getId();
			//楽旅点击招宝变更或招宝取消处理,记录操作人为订单的原操作人，而不是楽旅
			if (Util.eq("lelv", loginuser.getName())) {
				orderinfo.setVisaOpid(orderinfo.getSalesOpid());
			} else {
				orderinfo.setVisaOpid(userId);
			}

			//orderinfo.setVisaOpid(userId);
			dbDao.update(orderinfo);
			return null;
		}

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
	public Object downloadFile(Long orderid, HttpServletResponse response, HttpSession session,
			HttpServletRequest request) {
		TUserEntity loginUser = LoginUtil.getLoginUser(session);
		Integer userId = loginUser.getId();
		TCompanyEntity loginCompany = LoginUtil.getLoginCompany(session);
		try {
			// 查询日本订单
			TOrderJpEntity orderjp = dbDao.fetch(TOrderJpEntity.class, orderid);
			// 获取打包的文件
			byte[] byteArray = null;
			int pdftype = 0;
			//根据PDF类型
			//先查询发招宝有没有选择地接社
			//如果有，则使用该公司的pdfType,如果没有则查询该登录公司有没有资质，有资质用自己的，否则用通用模板
			if (!Util.isEmpty(orderjp.getSendsignid())) {
				Integer sendsignid = orderjp.getSendsignid();
				TCompanyEntity pdfCom = dbDao.fetch(TCompanyEntity.class, sendsignid.longValue());
				//Integer pdftype = loginCompany.getPdftype();
				pdftype = pdfCom.getPdftype();

			} else {
				if (!Util.isEmpty(loginCompany.getCdesignNum())) {
					pdftype = loginCompany.getPdftype();
				} else {
					pdftype = PdfTypeEnum.UNIVERSAL_TYPE.intKey();
				}
			}

			if (pdftype == PdfTypeEnum.LIAONINGWANDA_TYPE.intKey()) {
				//辽宁万达模版
				//byteArray = liaoNingWanDaService.generateFile(orderjp).toByteArray();
				byteArray = newLiaoningWandaService.generateFile(orderjp, request).toByteArray();
			} else if (pdftype == PdfTypeEnum.UNIVERSAL_TYPE.intKey()) {
				//通用模版
				//byteArray = downLoadVisaFileService.generateFile(orderjp, request).toByteArray();
				byteArray = huanyuService.generateFile(orderjp, request).toByteArray();
				//byteArray = jinQiaoService.generateFile(orderjp).toByteArray();
			} else if (pdftype == PdfTypeEnum.HUANYU_TYPE.intKey()) {
				byteArray = huanyuService.generateFile(orderjp, request).toByteArray();
			} else if (pdftype == PdfTypeEnum.JINQIAO_TYPE.intKey()) {
				byteArray = jinqiaoService.generateFile(orderjp, request).toByteArray();
				//byteArray = huangjinjiaqiService.generateFile(orderjp, request).toByteArray();
			} else if (pdftype == PdfTypeEnum.SHENZHOU_TYPE.intKey()) {
				byteArray = shenzhouService.generateFile(orderjp, request).toByteArray();
			} else if (pdftype == PdfTypeEnum.FENGSHANG_TYPE.intKey()) {
				byteArray = fengshangService.generateFile(orderjp, request).toByteArray();
			} else if (pdftype == PdfTypeEnum.BAICHENG_TYPE.intKey()) {
				byteArray = shanghaiBaichengService.generateFile(orderjp, request).toByteArray();
			} else if (pdftype == PdfTypeEnum.HEPING_TYPE.intKey()) {
				byteArray = hepingService.generateFile(orderjp, request).toByteArray();
			} else if (pdftype == PdfTypeEnum.BAOSHI_TYPE.intKey()) {
				byteArray = shanghaiBaoshiService.generateFile(orderjp, request).toByteArray();
			} else if (pdftype == PdfTypeEnum.ANJIE_TYPE.intKey()) {
				byteArray = anjieService.generateFile(orderjp, request).toByteArray();
			} else if (pdftype == PdfTypeEnum.JIAOTONG_TYPE.intKey()) {
				byteArray = jiaotonggongsheService.generateFile(orderjp, request).toByteArray();
			} else if (pdftype == PdfTypeEnum.YIQIYOU_TYPE.intKey()) {
				byteArray = yiqiyouService.generateFile(orderjp, request).toByteArray();
			} else if (pdftype == PdfTypeEnum.ZHONGBAO_TYPE.intKey()) {
				byteArray = shanghaiZhongbaoService.generateFile(orderjp, request).toByteArray();
			} else if (pdftype == PdfTypeEnum.HENANZHONGQINGLV_TYPE.intKey()) {
				byteArray = henanZhongqinglvService.generateFile(orderjp, request).toByteArray();
			} else if (pdftype == PdfTypeEnum.KANGHUI_TYPE.intKey()) {
				byteArray = kanghuiService.generateFile(orderjp, request).toByteArray();
			} else if (pdftype == PdfTypeEnum.HUANGJINJIAQI_TYPE.intKey()) {
				byteArray = huangjinjiaqiService.generateFile(orderjp, request).toByteArray();
			} else if (pdftype == PdfTypeEnum.ZHEJIANGKANGTAI_TYPE.intKey()) {
				byteArray = zhejiangKangtaiService.generateFile(orderjp, request).toByteArray();
			} else if (pdftype == PdfTypeEnum.HUBEIWANDAXINHANGXIAN_TYPE.intKey()) {
				byteArray = hubeiWandaxinhangxianService.generateFile(orderjp, request).toByteArray();
			} else if (pdftype == PdfTypeEnum.BEIJINGQINGLV_TYPE.intKey()) {
				byteArray = beijingQinglvService.generateFile(orderjp, request).toByteArray();
			}

			// 获取订单信息，准备文件名称
			TOrderEntity orderinfo = dbDao.fetch(TOrderEntity.class, orderjp.getOrderId().longValue());
			// 主申请人姓名
			String mainapplicantname = "";
			String visaType = "";
			// 查询申请人信息
			List<TApplicantOrderJpEntity> jpapplicants = dbDao.query(TApplicantOrderJpEntity.class,
					Cnd.where("orderId", "=", orderid).orderBy("isMainApplicant", "DESC"), null);

			if (jpapplicants.size() > 0) {
				TApplicantEntity mainapply = dbDao.fetch(TApplicantEntity.class, jpapplicants.get(0).getApplicantId()
						.longValue());
				mainapplicantname = mainapply.getFirstName() + mainapply.getLastName();
			}

			for (TApplicantOrderJpEntity applicantjp : jpapplicants) {
				if (!Util.isEmpty(applicantjp.getIsMainApplicant()) && applicantjp.getIsMainApplicant().equals(1)) {
					TOrderJpEntity orderJp = new TOrderJpEntity();
					TApplicantEntity applicat = dbDao.fetch(TApplicantEntity.class, applicantjp.getApplicantId()
							.longValue());
					if (!Util.isEmpty(applicantjp.getOrderId())) {
						orderJp = dbDao
								.fetch(TOrderJpEntity.class, Cnd.where("orderid", "=", applicantjp.getOrderId()));
						Integer type = orderjp.getVisaType();
						//System.out.println(type);
						if (!Util.isEmpty(type)) {
							for (SimpleVisaTypeEnum visatype : SimpleVisaTypeEnum.values()) {
								if (type == visatype.intKey()) {
									visaType = visatype.value();
								}
							}
							/*if (type == 1) {
								visaType = "单次";
							}
							if (type == 2) {
								visaType = "冲绳东北六县三年多次";
							}
							if (type == 3) {
								visaType = "普通三年多次";
							}
							if (type == 4) {
								visaType = "普通五年多次";
							}*/
						}
					}
					//mainapplicantname = "  " + applicat.getFirstName() + applicat.getLastName();
				}
			}
			String filename = "";
			// 申请人姓名
			filename += mainapplicantname + " ";
			// 人数
			filename += jpapplicants.size() + "人" + " ";
			// 签证类型
			filename += visaType + " ";
			// 订单号
			filename += orderinfo.getOrderNum();
			// filename += ".zip";
			// 新需求只下载PDF文件
			filename += ".pdf";
			// 将文件进行编码
			String fileName = URLEncoder.encode(filename, "UTF-8");
			fileName = fileName.replace("+", " ");
			// 设置下载的响应头
			// response.setContentType("application/zip");
			//通过response.reset()刷新可能存在一些未关闭的getWriter(),避免可能出现未关闭的getWriter()
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Set-Cookie", "fileDownload=true; path=/");
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);// 设置文件名
			// 将字节流相应到浏览器（下载）
			IOUtils.write(byteArray, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 变更订单负责人
		//changePrincipalViewService.ChangePrincipal(orderid.intValue(), VISA_PROCESS, userId);
		return null;
	}

	public Object uploadVisaPic(File file, HttpServletRequest request) {
		Map<String, Object> result = qiniuUploadService.ajaxUploadImage(file);
		file.delete();
		result.put("data", CommonConstants.IMAGES_SERVER_ADDR + result.get("data"));
		return result;

	}

}
