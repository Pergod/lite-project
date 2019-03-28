package com.sinolife.util;

public class FileUtil {
	public static String[] EXCEL_FILE =new String[]{"xlsx"};
	public static final String EXCEL_DIR ="E:/upload/";
	public static final String DOMAIN="http://127.0.0.1:8080/";
	
	public static boolean isExcel(String extName) {
		for (String ext : EXCEL_FILE) {
			if (extName.equalsIgnoreCase(ext)) {
				return true;
			}
		}
		return false;
	}
}
