/**
 * QrCodeService.java
 * com.juyo.visa.common.base
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.common.base;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.juyo.visa.common.comstants.CommonConstants;
import com.juyo.visa.common.util.QrCodeUtil;

/**
 * 生成二维码上传到七牛云并返回路径
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年12月9日 	 
 */
@IocBean
public class QrCodeService {

	@Inject
	private UploadService qiniuUpService;

	/**
	 * 生成二维码并返回七牛云路径
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param request
	 * @param content
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public String encodeQrCode(HttpServletRequest request, String content) {
		QrCodeUtil qrCodeUtil = new QrCodeUtil();
		//生成二维码的临时路径
		String filepath = request.getContextPath();
		//生成二维码
		qrCodeUtil.encodeQrCode(content, filepath);
		//获取二维码临时文件的路径
		String fileContextPath = qrCodeUtil.getFileContextPath();
		System.out.println("二维码临时路径：" + fileContextPath);
		//获取到二维码文件
		File file = new File(fileContextPath);
		//上传到七牛云
		Map<String, Object> map = qiniuUpService.ajaxUploadImage(file);
		file.delete();
		//返回上传后七牛云的路径
		String fileqiniupath = CommonConstants.IMAGES_SERVER_ADDR + (String) map.get("data");
		System.out.println(fileqiniupath);
		return fileqiniupath;
	}

	public String encodeQrCode2(HttpServletRequest request, String content) {
		QrCodeUtil qrCodeUtil = new QrCodeUtil();
		//生成二维码的临时路径
		String filepath = request.getContextPath();
		//生成二维码
		qrCodeUtil.encodeQrCode(content, filepath);
		//获取二维码临时文件的路径
		String fileContextPath = qrCodeUtil.getFileContextPath();
		System.out.println("二维码临时路径：" + fileContextPath);
		return fileContextPath;
	}
}
