/**
 * WordUtil.java
 * com.juyo.visa.admin.visajp.util
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.admin.visajp.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.enmus.ExcelType;
import org.jeecgframework.poi.excel.entity.params.ExcelExportEntity;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.sun.star.uno.RuntimeException;
import com.uxuexi.core.common.util.Util;

import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年11月16日 	 
 */
public class TemplateUtil {
	private static final Map<String, String> defaultApplyMap = new HashMap<String, String>();

	/**
	 * 处理Word模板
	 * <p>
	 * TODO(这里描述这个方法详情– 可选)
	 *
	 * @param inputStream
	 * @param map
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public ByteArrayOutputStream toWordStream(InputStream inputStream, Map<String, Object> map) {

		try {
			// 1) Load ODT file and set Velocity template engine and cache it to the registry
			IXDocReport report = XDocReportRegistry.getRegistry().loadReport(inputStream, TemplateEngineKind.Velocity);
			// 2) Create Java model context
			IContext context = report.createContext();
			Set<Entry<String, Object>> entrySet = map.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				context.put(entry.getKey(), entry.getValue());
			}

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			report.process(context, out);
			return out;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * pdf模板
	 */
	public ByteArrayOutputStream pdfTemplateStream(URL url, Map<String, String> map) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {

			TtfClassLoader ttf = new TtfClassLoader();
			//1 不进行密码验证
			PdfReader.unethicalreading = true;
			//2 读入pdf表单
			PdfReader reader = new PdfReader(url);
			//3 根据表单生成一个新的pdf
			PdfStamper ps = new PdfStamper(reader, stream);
			//4 获取pdf表单
			AcroFields fields = ps.getAcroFields();
			fields.removeXfa();//必须执行否则配偶职业和曾用名field名称重复
			Set<Entry<String, Item>> entrySet = fields.getFields().entrySet();
			for (Entry<String, Item> entry : entrySet) {
				String key = entry.getKey();
			}
			//5给表单添加中文字体 这里采用系统字体。不设置的话，中文可能无法显示
			fields.addSubstitutionFont(ttf.getBaseFont());
			//6遍历pdf表单表格，同时给表格赋值
			Iterator<Map.Entry<String, AcroFields.Item>> iterator = fields.getFields().entrySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next().getKey();
				if (map.containsKey(key)) {
					fields.setField(key, map.get(key));
				}
			}
			ps.setFormFlattening(true); // 这句不能少
			ps.close();
			reader.close();
			IOUtils.closeQuietly(stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stream;
	}

	/**
	 * 处理excel模板
	 */
	public ByteArrayOutputStream createExcel(List<ExcelExportEntity> entity, List<Map<String, String>> list) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			ExportParams params = new ExportParams(null, "名单");
			params.setType(ExcelType.XSSF);
			Workbook workbook = ExcelExportUtil.exportExcel(params, entity, list);
			workbook.write(stream);
			workbook.close();
			IOUtils.closeQuietly(stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stream;
	}

	//生成临时文件
	public File createTempFile(ByteArrayOutputStream out) {
		try {
			File tmp = new File(FileUtils.getTempDirectory() + "/" + UUID.randomUUID().toString() + ".tmp");
			FileUtils.writeByteArrayToFile(tmp, out.toByteArray());
			IOUtils.closeQuietly(out);
			return tmp;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("临时文件输出异常!");
		}
	}

	//多个PDF合并功能
	public ByteArrayOutputStream mergePdf(List<ByteArrayOutputStream> list) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Document document = new Document(new PdfReader(list.get(0).toByteArray()).getPageSize(1));
			PdfCopy copy = new PdfCopy(document, out);
			document.open();
			for (int i = 0; i < list.size(); i++) {
				ByteArrayOutputStream byteArrayOutputStream = list.get(i);
				if (!Util.isEmpty(byteArrayOutputStream)) {

					PdfReader reader = new PdfReader(list.get(i).toByteArray());
					int n = reader.getNumberOfPages();
					for (int j = 1; j <= n; j++) {
						document.newPage();
						PdfImportedPage page = copy.getImportedPage(reader, j);
						copy.addPage(page);
					}
				}
			}
			document.close();
			IOUtils.closeQuietly(out);
			return out;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("PDF合并异常!");
		}
	}

	//打包成为ZIP文件
	public static ByteArrayOutputStream mergeToZip(Map<String, File> fileMap) {
		//合并输出为一个压缩包
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ZipArchiveOutputStream zip = new ZipArchiveOutputStream(out);
			Iterator<Map.Entry<String, File>> set = fileMap.entrySet().iterator();
			while (set.hasNext()) {
				Map.Entry<String, File> entry = set.next();
				File file = entry.getValue();
				ZipArchiveEntry zipEntry = new ZipArchiveEntry(entry.getKey());
				zipEntry.setSize(file.length());
				zip.putArchiveEntry(zipEntry);
				FileInputStream in = new FileInputStream(file);
				IOUtils.copy(in, zip);
				IOUtils.closeQuietly(in);
				FileUtils.deleteQuietly(file);
			}
			zip.closeArchiveEntry();
			IOUtils.closeQuietly(zip);
			IOUtils.closeQuietly(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}
}
