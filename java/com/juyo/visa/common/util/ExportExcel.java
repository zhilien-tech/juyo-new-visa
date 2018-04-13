/**
 * ExportExcel.java
 * com.linyun.airline.admin.customneeds.util
 * Copyright (c) 2016, 北京直立人科技有限公司版权所有.
*/

package com.juyo.visa.common.util;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.uxuexi.core.common.util.DateUtil;

/**
 * 导出Excel
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2016年12月1日 	 
 */
public class ExportExcel {

	//显示的导出表的标题  
	private String title;
	//导出表的列名  
	private String[] rowName;
	//内容
	private List<Object[]> dataList = new ArrayList<Object[]>();

	HttpServletResponse response;

	/**
	 * 
	 * 创建 ExportExcel对象.
	 *
	 * @param title Excel表格标题
	 * @param rowName 列标题
	 * @param dataList 数据（从第二行开始）
	 * @param response 
	 */
	public ExportExcel(String title, String[] rowName, List<Object[]> dataList, HttpServletResponse response) {
		this.title = title;
		this.rowName = rowName;
		this.dataList = dataList;
		this.response = response;
	}

	public ExportExcel() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getRowName() {
		return rowName;
	}

	public void setRowName(String[] rowName) {
		this.rowName = rowName;
	}

	public List<Object[]> getDataList() {
		return dataList;
	}

	public void setDataList(List<Object[]> dataList) {
		this.dataList = dataList;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/* 
	 * 导出数据 
	 * */
	public void export() throws Exception {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象  
			HSSFSheet sheet = workbook.createSheet(title); // 创建工作表  

			// 产生表格标题行  
			//HSSFRow rowm = sheet.createRow(0);
			//HSSFCell cellTiltle = rowm.createCell(0);

			//sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】  
			HSSFCellStyle columnTopStyle = this.getStyle(workbook);//获取列头样式对象  
			HSSFCellStyle style = this.getStyle(workbook); //单元格样式对象  

			/*sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length - 1)));
			cellTiltle.setCellStyle(columnTopStyle);
			cellTiltle.setCellValue(title);*/

			// 定义所需列数  
			int columnNum = rowName.length;
			//HSSFRow rowRowName = sheet.createRow(2); // 在索引2的位置创建行(最顶端的行开始的第二行)  
			HSSFRow rowRowName = sheet.createRow(0); // 在索引2的位置创建行(最顶端的行开始的第二行)  

			// 将列头设置到sheet的单元格中  
			for (int n = 0; n < columnNum; n++) {
				HSSFCell cellRowName = rowRowName.createCell(n); //创建列头对应个数的单元格  
				cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); //设置列头单元格的数据类型  
				HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
				cellRowName.setCellValue(text); //设置列头单元格的值  
				cellRowName.setCellStyle(columnTopStyle); //设置列头单元格样式  
			}

			//将查询出的数据设置到sheet对应的单元格中  
			for (int i = 0; i < dataList.size(); i++) {

				Object[] obj = dataList.get(i);//遍历每个对象  
				//HSSFRow row = sheet.createRow(i + 3);//创建所需的行数  
				HSSFRow row = sheet.createRow(i + 1);//创建所需的行数  

				for (int j = 0; j < obj.length; j++) {
					HSSFCell cell = null; //设置单元格的数据类型  
					//注释掉的是第一列为序号
					/*if (j == 0) {
						cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(i + 1);
					} else {*/
					cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
					if (!"".equals(obj[j]) && obj[j] != null) {
						cell.setCellValue(obj[j].toString()); //设置单元格的值  
					}
					//}
					cell.setCellStyle(style); //设置单元格样式  
				}
			}
			//让列宽随着导出的列长自动适应  
			//widthAdaptive(sheet, columnNum);

			if (workbook != null) {
				OutputStream out = null;
				try {
					//String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
					DateFormat format = new SimpleDateFormat(DateUtil.FORMAT_YYYYMMDD);
					String fileName = this.getTitle() + "-" + format.format(new Date()) + ".xls";
					String headStr = "attachment; filename=\"" + fileName + "\"";
					response = getResponse();
					response.setContentType("APPLICATION/OCTET-STREAM");
					//response.setHeader("Content-Disposition", headStr);
					response.addHeader("Content-Disposition",
							"attachment;filename="
									+ new String(fileName.replaceAll(" ", "").getBytes("utf-8"), "iso8859-1"));
					out = response.getOutputStream();
					workbook.write(out);
				} catch (IOException e) {

				} finally {
					out.close();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * 设置Excel表格列宽度为自适应
	 * <p>
	 * 设置Excel表格列宽度为自适应
	 *
	 * @param sheet ：对应的Excel表格对象
	 * @param columnNum 列数
	 */
	public void widthAdaptive(HSSFSheet sheet, int columnNum) {
		//让列宽随着导出的列长自动适应  
		for (int colNum = 0; colNum < columnNum; colNum++) {
			int columnWidth = sheet.getColumnWidth(colNum) / 256;
			for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
				HSSFRow currentRow;
				//当前行未被使用过  
				if (sheet.getRow(rowNum) == null) {
					currentRow = sheet.createRow(rowNum);
				} else {
					currentRow = sheet.getRow(rowNum);
				}
				if (currentRow.getCell(colNum) != null) {
					HSSFCell currentCell = currentRow.getCell(colNum);
					if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
						int length = currentCell.getStringCellValue().getBytes().length;
						if (columnWidth < length) {
							columnWidth = length;
						}
					}
				}
			}
			if (colNum == 0) {
				sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
			} else {
				sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
			}
		}
	}

	/*   
	 * 列数据信息单元格样式 
	 */
	public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
		// 设置字体  
		//HSSFFont font = workbook.createFont();
		//设置字体大小  
		//font.setFontHeightInPoints((short)10);  
		//字体加粗  
		//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
		//设置字体名字   
		//font.setFontName("Courier New");
		//设置样式;   
		HSSFCellStyle style = workbook.createCellStyle();
		/*//设置底边框;   
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		//设置底边框颜色;    
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		//设置左边框;     
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		//设置左边框颜色;   
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		//设置右边框;   
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		//设置右边框颜色;   
		style.setRightBorderColor(HSSFColor.BLACK.index);
		//设置顶边框;   
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		//设置顶边框颜色;    
		style.setTopBorderColor(HSSFColor.BLACK.index);
		//在样式用应用设置的字体;    
		style.setFont(font);
		//设置自动换行;   
		style.setWrapText(false);*/
		//设置水平对齐的样式为居中对齐;    
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//设置垂直对齐的样式为居中对齐;   
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		return style;
	}
}
