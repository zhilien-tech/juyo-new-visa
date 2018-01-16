/**
 * Test.java
 * com.linyun.airline.common.util
 * Copyright (c) 2017, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.common.util;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月15日 	 
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;

/**
 * @功能描述 POI 读取 Excel 转 HTML 支持 03xls 和 07xlsx 版本  包含样式
 * @author Devil
 * @创建时间 2015/4/19 21:34
 */
public class POIReadExcelToHtml {

	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {

		String path = "E://56.xlsx";//E://Microsoft Excel 工作表.xlsx
		InputStream is = null;
		String htmlExcel = null;
		try {
			File sourcefile = new File(path);
			is = new FileInputStream(sourcefile);
			Workbook wb = WorkbookFactory.create(is);//此WorkbookFactory在POI-3.10版本中使用需要添加dom4j
			if (wb instanceof XSSFWorkbook) {
				XSSFWorkbook xWb = (XSSFWorkbook) wb;
				htmlExcel = POIReadExcelToHtml.getExcelInfo(xWb, true);
			} else if (wb instanceof HSSFWorkbook) {
				HSSFWorkbook hWb = (HSSFWorkbook) wb;
				htmlExcel = POIReadExcelToHtml.getExcelInfo(hWb, true);
			}
			System.out.println(htmlExcel);
			writeFile(htmlExcel, "E://123.html");
			HtmlToPdf.htmlConvertToPdf("E://123.html", "E://123.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void excelConvertToPdf(String inputPath, String outputPath) {
		//E://Microsoft Excel 工作表.xlsx
		InputStream is = null;
		String htmlExcel = null;
		try {

			URL url = new URL(inputPath);// 生成url对象
			URLConnection urlConnection = url.openConnection();
			is = urlConnection.getInputStream();
			Workbook wb = WorkbookFactory.create(is);//此WorkbookFactory在POI-3.10版本中使用需要添加dom4j
			if (wb instanceof XSSFWorkbook) {
				XSSFWorkbook xWb = (XSSFWorkbook) wb;
				htmlExcel = POIReadExcelToHtml.getExcelInfo(xWb, true);
			} else if (wb instanceof HSSFWorkbook) {
				HSSFWorkbook hWb = (HSSFWorkbook) wb;
				htmlExcel = POIReadExcelToHtml.getExcelInfo(hWb, true);
			}
			//System.out.println(htmlExcel);
			writeFile(htmlExcel, outputPath);
			HtmlToPdf htmlToPdf = new HtmlToPdf();
			/*htmlToPdf.htmlConvertToPdf("E://123.html", "E://123.pdf");*/
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 程序入口方法
	 * @param filePath 文件的路径
	 * @param isWithStyle 是否需要表格样式 包含 字体 颜色 边框 对齐方式
	 * @return <table>...</table> 字符串
	 */
	public String readExcelToHtml(String filePath, boolean isWithStyle) {

		InputStream is = null;
		String htmlExcel = null;
		try {
			File sourcefile = new File(filePath);
			is = new FileInputStream(sourcefile);
			Workbook wb = WorkbookFactory.create(is);
			if (wb instanceof XSSFWorkbook) {
				XSSFWorkbook xWb = (XSSFWorkbook) wb;
				htmlExcel = POIReadExcelToHtml.getExcelInfo(xWb, isWithStyle);
			} else if (wb instanceof HSSFWorkbook) {
				HSSFWorkbook hWb = (HSSFWorkbook) wb;
				htmlExcel = POIReadExcelToHtml.getExcelInfo(hWb, isWithStyle);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return htmlExcel;
	}

	public static String getExcelInfo(Workbook wb, boolean isWithStyle) {

		StringBuffer sb = new StringBuffer();
		Sheet sheet = wb.getSheetAt(0);//获取第一个Sheet的内容
		int lastRowNum = sheet.getLastRowNum();
		Map<String, String> map[] = getRowSpanColSpanMap(sheet);
		sb.append("<table style='border-collapse:collapse;' width='100%'>");
		Row row = null; //兼容
		Cell cell = null; //兼容

		for (int rowNum = sheet.getFirstRowNum(); rowNum <= lastRowNum; rowNum++) {
			row = sheet.getRow(rowNum);
			if (row == null) {
				sb.append("<tr><td > &nbsp;</td></tr>");
				continue;
			}
			sb.append("<tr>");
			int lastColNum = row.getLastCellNum();
			for (int colNum = 0; colNum < lastColNum; colNum++) {
				cell = row.getCell(colNum);
				if (cell == null) { //特殊情况 空白的单元格会返回null
					sb.append("<td>&nbsp;</td>");
					continue;
				}

				String stringValue = getCellValue(cell);
				if (map[0].containsKey(rowNum + "," + colNum)) {
					String pointString = map[0].get(rowNum + "," + colNum);
					map[0].remove(rowNum + "," + colNum);
					int bottomeRow = Integer.valueOf(pointString.split(",")[0]);
					int bottomeCol = Integer.valueOf(pointString.split(",")[1]);
					int rowSpan = bottomeRow - rowNum + 1;
					int colSpan = bottomeCol - colNum + 1;
					sb.append("<td rowspan= '" + rowSpan + "' colspan= '" + colSpan + "' ");
				} else if (map[1].containsKey(rowNum + "," + colNum)) {
					map[1].remove(rowNum + "," + colNum);
					continue;
				} else {
					sb.append("<td ");
				}

				//判断是否需要样式
				if (isWithStyle) {
					dealExcelStyle(wb, sheet, cell, sb);//处理单元格样式
				}

				sb.append(">");
				if (stringValue == null || "".equals(stringValue.trim())) {
					sb.append(" &nbsp; ");
				} else {
					// 将ascii码为160的空格转换为html下的空格（&nbsp;）
					sb.append(stringValue.replace(String.valueOf((char) 160), "&nbsp;"));
				}
				sb.append("</td>");
			}
			sb.append("</tr>");
		}

		sb.append("</table>");
		return sb.toString();
	}

	private static Map<String, String>[] getRowSpanColSpanMap(Sheet sheet) {

		Map<String, String> map0 = new HashMap<String, String>();
		Map<String, String> map1 = new HashMap<String, String>();
		int mergedNum = sheet.getNumMergedRegions();
		CellRangeAddress range = null;
		for (int i = 0; i < mergedNum; i++) {
			range = sheet.getMergedRegion(i);
			int topRow = range.getFirstRow();
			int topCol = range.getFirstColumn();
			int bottomRow = range.getLastRow();
			int bottomCol = range.getLastColumn();
			map0.put(topRow + "," + topCol, bottomRow + "," + bottomCol);
			// System.out.println(topRow + "," + topCol + "," + bottomRow + "," + bottomCol);
			int tempRow = topRow;
			while (tempRow <= bottomRow) {
				int tempCol = topCol;
				while (tempCol <= bottomCol) {
					map1.put(tempRow + "," + tempCol, "");
					tempCol++;
				}
				tempRow++;
			}
			map1.remove(topRow + "," + topCol);
		}
		Map[] map = { map0, map1 };
		return map;
	}

	/**
	 * 获取表格单元格Cell内容
	 * @param cell
	 * @return
	 */
	private static String getCellValue(Cell cell) {

		String result = new String();
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:// 数字类型  
			if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
				SimpleDateFormat sdf = null;
				if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
					sdf = new SimpleDateFormat("HH:mm");
				} else {// 日期  
					sdf = new SimpleDateFormat("yyyy-MM-dd");
				}
				Date date = cell.getDateCellValue();
				result = sdf.format(date);
			} else if (cell.getCellStyle().getDataFormat() == 58) {
				// 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				double value = cell.getNumericCellValue();
				Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
				result = sdf.format(date);
			} else {
				double value = cell.getNumericCellValue();
				CellStyle style = cell.getCellStyle();
				DecimalFormat format = new DecimalFormat();
				String temp = style.getDataFormatString();
				// 单元格设置成常规  
				if (temp.equals("General")) {
					format.applyPattern("#");
				}
				result = format.format(value);
			}
			break;
		case Cell.CELL_TYPE_STRING:// String类型  
			result = cell.getRichStringCellValue().toString();
			break;
		case Cell.CELL_TYPE_BLANK:
			result = "";
			break;
		default:
			result = "";
			break;
		}
		return result;
	}

	/**
	 * 处理表格样式
	 * @param wb
	 * @param sheet
	 * @param cell
	 * @param sb
	 */
	private static void dealExcelStyle(Workbook wb, Sheet sheet, Cell cell, StringBuffer sb) {

		CellStyle cellStyle = cell.getCellStyle();
		if (cellStyle != null) {
			short alignment = cellStyle.getAlignment();
			sb.append("align='" + convertAlignToHtml(alignment) + "' ");//单元格内容的水平对齐方式
			short verticalAlignment = cellStyle.getVerticalAlignment();
			sb.append("valign='" + convertVerticalAlignToHtml(verticalAlignment) + "' ");//单元格中内容的垂直排列方式

			if (wb instanceof XSSFWorkbook) {

				XSSFFont xf = ((XSSFCellStyle) cellStyle).getFont();
				short boldWeight = xf.getBoldweight();
				sb.append("style='");
				sb.append("font-weight:" + boldWeight + ";"); // 字体加粗
				sb.append("font-size: " + xf.getFontHeight() / 2 + "%;"); // 字体大小
				int columnWidth = sheet.getColumnWidth(cell.getColumnIndex());
				sb.append("width:" + columnWidth + "px;");

				XSSFColor xc = xf.getXSSFColor();
				if (xc != null && !"".equals(xc)) {
					sb.append("color:#" + xc.getARGBHex().substring(2) + ";"); // 字体颜色
				}

				XSSFColor bgColor = (XSSFColor) cellStyle.getFillForegroundColorColor();
				//System.out.println("************************************");
				//System.out.println("BackgroundColorColor: "+cellStyle.getFillBackgroundColorColor());
				//System.out.println("ForegroundColor: "+cellStyle.getFillForegroundColor());//0
				//System.out.println("BackgroundColorColor: "+cellStyle.getFillBackgroundColorColor());
				//System.out.println("ForegroundColorColor: "+cellStyle.getFillForegroundColorColor());
				//String bgColorStr = bgColor.getARGBHex();
				//System.out.println("bgColorStr: "+bgColorStr);
				if (bgColor != null && !"".equals(bgColor)) {
					sb.append("background-color:#" + bgColor.getARGBHex().substring(2) + ";"); // 背景颜色
				}
				sb.append(getBorderStyle(0, cellStyle.getBorderTop(),
						((XSSFCellStyle) cellStyle).getTopBorderXSSFColor()));
				sb.append(getBorderStyle(1, cellStyle.getBorderRight(),
						((XSSFCellStyle) cellStyle).getRightBorderXSSFColor()));
				sb.append(getBorderStyle(2, cellStyle.getBorderBottom(),
						((XSSFCellStyle) cellStyle).getBottomBorderXSSFColor()));
				sb.append(getBorderStyle(3, cellStyle.getBorderLeft(),
						((XSSFCellStyle) cellStyle).getLeftBorderXSSFColor()));

			} else if (wb instanceof HSSFWorkbook) {

				HSSFFont hf = ((HSSFCellStyle) cellStyle).getFont(wb);
				short boldWeight = hf.getBoldweight();
				short fontColor = hf.getColor();
				sb.append("style='");
				HSSFPalette palette = ((HSSFWorkbook) wb).getCustomPalette(); // 类HSSFPalette用于求的颜色的国际标准形式
				HSSFColor hc = palette.getColor(fontColor);
				sb.append("font-weight:" + boldWeight + ";"); // 字体加粗
				sb.append("font-size: " + hf.getFontHeight() / 2 + "%;"); // 字体大小
				String fontColorStr = convertToStardColor(hc);
				if (fontColorStr != null && !"".equals(fontColorStr.trim())) {
					sb.append("color:" + fontColorStr + ";"); // 字体颜色
				}
				int columnWidth = sheet.getColumnWidth(cell.getColumnIndex());
				sb.append("width:" + columnWidth + "px;");
				short bgColor = cellStyle.getFillForegroundColor();
				hc = palette.getColor(bgColor);
				String bgColorStr = convertToStardColor(hc);
				if (bgColorStr != null && !"".equals(bgColorStr.trim())) {
					sb.append("background-color:" + bgColorStr + ";"); // 背景颜色
				}
				sb.append(getBorderStyle(palette, 0, cellStyle.getBorderTop(), cellStyle.getTopBorderColor()));
				sb.append(getBorderStyle(palette, 1, cellStyle.getBorderRight(), cellStyle.getRightBorderColor()));
				sb.append(getBorderStyle(palette, 3, cellStyle.getBorderLeft(), cellStyle.getLeftBorderColor()));
				sb.append(getBorderStyle(palette, 2, cellStyle.getBorderBottom(), cellStyle.getBottomBorderColor()));
			}

			sb.append("' ");
		}
	}

	/**
	 * 单元格内容的水平对齐方式
	 * @param alignment
	 * @return
	 */
	private static String convertAlignToHtml(short alignment) {

		String align = "left";
		switch (alignment) {
		case CellStyle.ALIGN_LEFT:
			align = "left";
			break;
		case CellStyle.ALIGN_CENTER:
			align = "center";
			break;
		case CellStyle.ALIGN_RIGHT:
			align = "right";
			break;
		default:
			break;
		}
		return align;
	}

	/**
	 * 单元格中内容的垂直排列方式
	 * @param verticalAlignment
	 * @return
	 */
	private static String convertVerticalAlignToHtml(short verticalAlignment) {

		String valign = "middle";
		switch (verticalAlignment) {
		case CellStyle.VERTICAL_BOTTOM:
			valign = "bottom";
			break;
		case CellStyle.VERTICAL_CENTER:
			valign = "center";
			break;
		case CellStyle.VERTICAL_TOP:
			valign = "top";
			break;
		default:
			break;
		}
		return valign;
	}

	private static String convertToStardColor(HSSFColor hc) {

		StringBuffer sb = new StringBuffer("");
		if (hc != null) {
			if (HSSFColor.AUTOMATIC.index == hc.getIndex()) {
				return null;
			}
			sb.append("#");
			for (int i = 0; i < hc.getTriplet().length; i++) {
				sb.append(fillWithZero(Integer.toHexString(hc.getTriplet()[i])));
			}
		}

		return sb.toString();
	}

	private static String fillWithZero(String str) {
		if (str != null && str.length() < 2) {
			return "0" + str;
		}
		return str;
	}

	static String[] bordesr = { "border-top:", "border-right:", "border-bottom:", "border-left:" };
	static String[] borderStyles = { "solid ", "solid ", "solid ", "solid ", "solid ", "solid ", "solid ", "solid ",
			"solid ", "solid", "solid", "solid", "solid", "solid" };

	private static String getBorderStyle(HSSFPalette palette, int b, short s, short t) {

		if (s == 0)
			return bordesr[b] + borderStyles[s] + "#d0d7e5 1px;";
		;
		String borderColorStr = convertToStardColor(palette.getColor(t));
		borderColorStr = borderColorStr == null || borderColorStr.length() < 1 ? "#000000" : borderColorStr;
		return bordesr[b] + borderStyles[s] + borderColorStr + " 1px;";

	}

	private static String getBorderStyle(int b, short s, XSSFColor xc) {

		if (s == 0)
			return bordesr[b] + borderStyles[s] + "#d0d7e5 1px;";
		;
		if (xc != null && !"".equals(xc)) {
			String borderColorStr = xc.getARGBHex();//t.getARGBHex();
			borderColorStr = borderColorStr == null || borderColorStr.length() < 1 ? "#000000" : borderColorStr
					.substring(2);
			return bordesr[b] + borderStyles[s] + borderColorStr + " 1px;";
		}

		return "";
	}

	/**
	 * 使用jsoup将html页面更加格式化
	 * @param content 原html页面
	 * @param path 格式化后的html页面
	 */
	public static void writeFile(String content, String path) {

		FileOutputStream fos = null;
		BufferedWriter bw = null;
		org.jsoup.nodes.Document doc = Jsoup.parse(content);
		content = doc.html();
		System.out.println(content);
		// 将所有字体改变成simsum
		/*Pattern pattern = Pattern.compile("font-family:['\\w|\\s|\u4e00-\u9fa5]+;");
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			content = matcher.replaceAll("font-family:SimSun;");
		} else {
			content = content.replaceAll("</style>", "body{font-family: SimSun;} </style>");
		}
		// 去除nbsp;
		Pattern pattern1 = Pattern.compile("&nbsp;");
		Matcher matcher1 = pattern1.matcher(content);
		content = matcher1.replaceAll(" ");

		// 修改边距[A-Za-z0-9.\\s]表示大写字母小写字母小数点或空格中的任意一个
		Pattern pattern2 = Pattern.compile("margin[a-z\\-]{0,}:[A-Za-z0-9.\\s]+;");
		Matcher matcher2 = pattern2.matcher(content);
		content = matcher2.replaceAll("margin:auto;");*/
		// System.out.println(content);
		try {
			File file = new File(path);
			fos = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(fos, "Unicode"));
			bw.write(content);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fos != null)
					fos.close();
			} catch (IOException ie) {
			}
		}
	}
	/*	}*/
}
