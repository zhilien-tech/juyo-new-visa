/**
 * ExcelReader.java
 * cn.wa.lottery
 * Copyright (c) 2014, 北京聚智未来科技有限公司版权所有.
*/

package com.juyo.visa.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 操作Excel表格的功能类
 */
public class ExcelReader {
	/**
	 * 读取Excel表格表头的内容
	 * @param InputStream
	 * @return String 表头内容的数组
	 */
	public String[] readExcelTitle(InputStream is) {
		try {
			//这种方式 Excel 2003/2007/2010 都是可以处理的  
			Workbook workbook = WorkbookFactory.create(is);
			Sheet sheet = workbook.getSheetAt(0);
			Row row = sheet.getRow(0);
			// 标题总列数
			int colNum = row.getPhysicalNumberOfCells();
			System.out.println("colNum:" + colNum);
			String[] title = new String[colNum];
			for (int i = 0; i < colNum; i++) {
				//title[i] = getStringCellValue(row.getCell((short) i));
				title[i] = getCellFormatValue(row.getCell((short) i));
			}
			return title;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 读取Excel数据内容
	 * @param InputStream
	 * @return Map 包含单元格数据内容的Map对象
	 */
	public Map<Integer, String[]> readExcelContent(InputStream is) {
		Map<Integer, String[]> content = new HashMap<Integer, String[]>();
		try {
			Workbook workBook = WorkbookFactory.create(is);
			Sheet sheet = workBook.getSheetAt(0);//第一个sheet

			// 得到总行数
			int rowNum = sheet.getLastRowNum();
			Row row = sheet.getRow(0);
			int colNum = row.getPhysicalNumberOfCells();

			// 正文内容应该从第二行开始,第一行为表头的标题
			for (int rowIndex = 1; rowIndex <= rowNum; rowIndex++) {
				row = sheet.getRow(rowIndex);
				String[] colValues = new String[colNum];

				int j = 0;
				while (j < colNum) {
					//每一行的内容放到数组中
					colValues[j] = getCellFormatValue(row.getCell((short) j)).trim();
					j++;
				}
				content.put(rowIndex, colValues);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 根据HSSFCell类型设置数据
	 * @param cell
	 * @return
	 */
	private String getCellFormatValue(Cell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case Cell.CELL_TYPE_NUMERIC:
			case Cell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				if (DateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式

					//方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
					//cellvalue = cell.getDateCellValue().toLocaleString();

					//方法2：这样子的data格式是不带带时分秒的：2011-10-12
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);
				}
				// 如果是纯数字
				else {
					// 取得当前Cell的数值
					cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			// 如果当前Cell的Type为STRING
			case Cell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			// 默认的Cell值
			default:
				cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;
	}

	public static void main(String[] args) {
		try {
			// 对读取Excel表格标题测试
			File excelFile = new File("d:\\kaifa\\导入Excel.xlsx");
			InputStream is = new FileInputStream(excelFile);
			ExcelReader excelReader = new ExcelReader();
			String[] title = excelReader.readExcelTitle(is);
			System.out.println("获得Excel表格的标题:");
			for (String s : title) {
				System.out.print(s + " ");
			}

			// 对读取Excel表格内容测试
			InputStream is2 = new FileInputStream(excelFile);
			Map<Integer, String[]> map = excelReader.readExcelContent(is2);
			for (int i = 1; i <= map.size(); i++) {
				System.out.println(map.get(i));
				String[] ssss = map.get(i);
				for (String str : ssss) {
					System.out.print(str + " ");
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("未找到指定路径的文件!");
			e.printStackTrace();
		}
	}
}
