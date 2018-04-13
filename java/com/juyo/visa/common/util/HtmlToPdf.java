package com.juyo.visa.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.pdf.BaseFont;

public class HtmlToPdf {

	public static void htmlConvertToPdf(String inputFile, String outputFile) throws Exception {
		//inputFile = "E:\\12.html";
		String url = new File(inputFile).toURI().toURL().toString();
		//outputFile = "E:\\1.pdf";
		System.out.println(url);
		OutputStream os = new FileOutputStream(outputFile);
		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocument(url);

		// 解决中文支持问题
		ITextFontResolver fontResolver = renderer.getFontResolver();

		//		URL url123 = html.getClass().getResource("/simsun.ttc");
		//		String rootPath = url123.toString();*/
		/*URL url11 = HtmlToPdf.class.getResource("");*/
		String path = HtmlToPdf.class.getClassLoader().getResource("").getPath();
		/*String rootPath = HtmlToPdf.class.getResource("").toString();*/
		fontResolver.addFont(path + "simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

		// 解决图片的相对路径问题
		// renderer.getSharedContext().setBaseURL("file:/D:/z/temp/");

		renderer.layout();
		renderer.createPDF(os);

		os.close();
	}
}