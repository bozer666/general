package com;

import org.apache.commons.lang3.StringUtils;

import java.io.*;

/**
 * Created by 'ms.x' on 2017/8/8.
 */

public class Helper {
	
	/**
	 * 处理字符串，去掉下划线“_”，并且把下划线的下一个字符变大写，flag为true，表示首字母要大写
	 * @param name
	 * @param flag
	 * @return
	 */
	public static String getFormatString(String name, boolean flag) {
		name = name.toLowerCase();
		String[] nameTemp = name.split("_");
		StringBuffer buffer = new StringBuffer();
		for(String str : nameTemp) {
			String head = str.substring(0, 1).toUpperCase();
			String tail = str.substring(1);
			buffer.append(head+tail);
		}
		StringBuffer result = null;
		if(!flag) {
			result = new StringBuffer();
			String head = buffer.substring(0, 1).toLowerCase();
			String tail = buffer.substring(1);
			result.append(head+tail);
			return result.toString();
		}
		return buffer.toString();
	}
	
	/**
	 * 把String内容写到文件
	 * @param fileName
	 * @param content
	 */
	public static void outputToFile(String path,String fileName, String content){
		outputToFile( path, fileName,  content,true);
	}




	public static void outputToFile(String path,String fileName, String content,Boolean recover) {
		File file1 = new File(path+fileName);
		if (!recover && file1.exists()){
			return;
		}
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
		OutputStream os = null;
		try {
			os = new FileOutputStream(path+"/"+fileName);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		byte[] b = content.getBytes();
		try {
			os.write(b);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getFilePath(String path,String packageName){
		String pathTemp = getPathFromPackageName(packageName);
		StringBuffer sb=new StringBuffer();
		sb.append(path);
		if (StringUtils.isNotEmpty(pathTemp)){
			sb.append(pathTemp);
		}
		return sb.toString();
	}
	
	public static String getPathFromPackageName(String packageName){
		if (StringUtils.isEmpty(packageName)){
			return packageName;
		}
		return packageName.replace(".","/");
	}


	public static String getDbColumnType(String columnType){
		if ("INT".equals(columnType) || "INT UNSIGNED".equals(columnType)){
			columnType="INTEGER";
		}else if ("BIGINT UNSIGNED".equals(columnType) || "BIGINT".equals(columnType)) {
			columnType = "BIGINT";
		}
		return columnType;
	}

	
	/**
	 * 数据库类型转为java类型
	 */
	public static String getColumnType(String column) {
		String COLUMN_TYPE = null;
		if("VARCHAR".equals(column)) {
			COLUMN_TYPE = "String";
		} else if("BIGINT".equals(column)) {
			COLUMN_TYPE = "Long";
		} else if("DATETIME".equals(column)) {
			COLUMN_TYPE = "Date";
		} else if("INT".equals(column) || "INT UNSIGNED".equals(column)) {
			COLUMN_TYPE = "Integer";
		} else if("BIGINT UNSIGNED".equals(column)) {
			COLUMN_TYPE = "Long";
		} else if("TINYINT UNSIGNED".equals(column)) {
			COLUMN_TYPE = "Short";
		} else if("DECIMAL".equals(column) || "FLOAT".equals(column) || "DOUBLE".equals(column)) {
			COLUMN_TYPE = "Double";
		} else if("TEXT".equals(column) || "MEDIUMTEXT".equals(column) || "LONGTEXT".equals(column)) {
			COLUMN_TYPE = "String";
		} else if("TIMESTAMP".equals(column) || "DATE".equals(column) || "DATETIME".equals(column)) {
			// TODO: 2017/9/20 这里时间用String格式
			//COLUMN_TYPE = "Date";
			COLUMN_TYPE = "String";
		} else if("TINYINT".equals(column)) {
			COLUMN_TYPE = "Short";
		} else if("DECIMAL UNSIGNED".equals(column)) {
			COLUMN_TYPE = "Double";
		} else if("SMALLINT".equals(column)) {
			COLUMN_TYPE = "Short";
		} else if("BIT".equals(column)) {
			COLUMN_TYPE = "Short";
		} else if("CHAR".equals(column)) {
			COLUMN_TYPE = "String";
		} else if("VARBINARY".equals(column)) {
			COLUMN_TYPE = "byte";
		} else if("BLOB".equals(column)) {
			COLUMN_TYPE = "byte";
		}
		return COLUMN_TYPE;
	}
	
}
