/**
 * QrCodeUtil.java
 * com.juyo.visa.common.util
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import lombok.Data;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.uxuexi.core.common.util.RandomUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年12月9日 	 
 */
@Data
public class QrCodeUtil {

	private String fileContextPath;

	/**
	 * 生成二维码
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param content 二维码的内容
	 * @param filePath临时文件的路径
	 */
	public void encodeQrCode(String content, String filePath) {
		try {
			String fileName = RandomUtil.uu16() + ".png";
			int width = 300; // 图像宽度  
			int height = 300; // 图像高度  
			String format = "png";// 图像类型  
			Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵  
			Path path = FileSystems.getDefault().getPath(filePath, fileName);
			this.fileContextPath = path.toString();
			MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像  
			//System.out.println("输出成功.");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/** 
	 * 解析二维码 
	 */
	public void decodeQrCode(String filePath) {
		BufferedImage image;
		try {
			image = ImageIO.read(new File(filePath));
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
			Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码  
			System.out.println("图片中内容：  ");
			System.out.println("author： " + result.getText());
			System.out.println("图片中格式：  ");
			System.out.println("encode： " + result.getBarcodeFormat());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
