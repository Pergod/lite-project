package com.sinolife.util;

public class FileUtil {
	public static String[] EXCEL_FILE =new String[]{"xlsx","xls"};
	public static final String EXCEL_DIR ="E:/upload/";
	public static final String DOMAIN="http://127.0.0.1:8080/";
	
	
//	public static final String EXCEL_DIR ="/lucify/tomcat/excels/";
//	public static final String DOMAIN="https://luxi81.com/";
	
	/**
	 * 检查是否是Excel文件
	 * @param extName
	 * @return
	 */
	public static boolean isExcel(String extName) {
		for (String ext : EXCEL_FILE) {
			if (extName.equalsIgnoreCase(ext)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取文件后缀名
	 * @param originFileName
	 * @return
	 */
	public static String getExtName(String originFileName) {
		int dotPos = originFileName.lastIndexOf(".");
		if (dotPos < 0) {
			return null;
		}
		return originFileName.substring(dotPos + 1);
	}
}
