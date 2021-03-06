package com.sinolife.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class ExcelResolve {

	/**
	 * 解析Excel文件
	 */
	public static List<Map<String, Object>> readExcel(File file) throws Exception {
		String fileName = file.getName();
		String extName = FileUtil.getExtName(fileName);
		Workbook book = null;
		if (FileUtil.isExcel(extName)) {
			book = new XSSFWorkbook(file);
		}else {
			return null;
		}
		//获取Excel下Sheets数目
		int numberOfSheets = book.getNumberOfSheets();
		List<Map<String, Object>> businesses = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < numberOfSheets; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			Sheet sheet = book.getSheetAt(i);
			String shetName = book.getSheetName(i);
			//获取条线名
			map.put("business", shetName);
			List<Map<String, String>> requirements = new ArrayList<Map<String, String>>();
			//循环获取每个条线的需求详情
			JSONArray jsonArray = read(sheet, book);
			for(int j=0;j<jsonArray.size();j++){
				JSONObject json = jsonArray.getJSONObject(j);
				Map<String, String> temp = new HashMap<String, String>();
				temp.put("jira_no",(String)json.get("需求号"));
				temp.put("jira_desc", (String)json.get("主题"));
				temp.put("jira_type", (String)json.get("需求类型"));
				temp.put("state", (String)json.get("需求状态"));
				temp.put("state", (String)json.get("需求状态"));
				temp.put("modification", (String)json.get("功能点"));
				temp.put("developer", (String)json.get("开发人员"));
				temp.put("need_review", (String)json.get("是否需要代码评审"));
				temp.put("reporter", (String)json.get("需求提出人"));
				temp.put("tester", (String)json.get("测试人"));
				requirements.add(temp);
			}
			map.put(shetName, requirements);
			businesses.add(map);
		}
		return businesses;
	}

	/**
	 * 解析数据 sheet 表格sheet对象 book 用于流关闭
	 */
	public static JSONArray read(Sheet sheet, Workbook book) throws IOException {
		int rowStart = sheet.getFirstRowNum(); // 首行下标
		int rowEnd = sheet.getLastRowNum(); // 尾行下标
		// 如果首行与尾行相同，表明只有一行，直接返回空数组
		if (rowStart == rowEnd) {
			book.close();
			return new JSONArray();
		}
		// 获取第一行JSON对象键
		Row firstRow = sheet.getRow(rowStart);
		int cellStart = firstRow.getFirstCellNum();
		int cellEnd = firstRow.getLastCellNum();
		Map<Integer, String> keyMap = new HashMap<Integer, String>();
		for (int j = cellStart; j < cellEnd; j++) {
			keyMap.put(j, getValue(firstRow.getCell(j), rowStart, j, book, true));
		}
		// 获取每行JSON对象的值
		JSONArray array = new JSONArray();
		for (int i = rowStart + 1; i <= rowEnd; i++) {
			Row eachRow = sheet.getRow(i);
			JSONObject obj = new JSONObject();
			StringBuffer sb = new StringBuffer();
			for (int k = cellStart; k < cellEnd; k++) {
				if (eachRow != null) {
					String val = getValue(eachRow.getCell(k), i, k, book, false);
					sb.append(val); // 所有数据添加到里面，用于判断该行是否为空
					obj.put(keyMap.get(k), val);
				}
			}
			if (sb.toString().length() > 0) {
				array.add(obj);
			}
		}
		book.close();
		return array;
	}

	/**
	 * 获取每个单元格的数据 cell 单元格对象 rowNum 第几行 index 该行第几个 book 主要用于关闭流 isKey
	 * 是否为键：true-是，false-不是。 如果解析Json键，值为空时报错；如果不是Json键，值为空不报错
	 */
	@SuppressWarnings("deprecation")
	public static String getValue(Cell cell, int rowNum, int index, Workbook book, boolean isKey) throws IOException {
		// 空白或空
		if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			if (isKey) {
				book.close();
				throw new NullPointerException(String.format("the key on row %s index %s is null ", ++rowNum, ++index));
			} else {
				return "";
			}
		}

		// 0. 数字 类型
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return df.format(date);
			}
			String val = cell.getNumericCellValue() + "";
			val = val.toUpperCase();
			if (val.contains("E")) {
				val = val.split("E")[0].replace(".", "");
			}
			return val;
		}

		// 1. String类型
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			String val = cell.getStringCellValue();
			if (val == null || val.trim().length() == 0) {
				if (book != null) {
					book.close();
				}
				return "";
			}
			return val.trim();
		}

		// 2. 公式 CELL_TYPE_FORMULA
		if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			return cell.getStringCellValue();
		}

		// 4. 布尔值 CELL_TYPE_BOOLEAN
		if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			return cell.getBooleanCellValue() + "";
		}
		// 5. 错误 CELL_TYPE_ERROR
		return "";
	}
}
