package com;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

@Component
public class GenerateMybatisXml {
	
	@Autowired
	private Properties properties;
	@Autowired
	private DBInit dbInit;
	
	/**
	 * @Description: 获取表对应的所有列
	 * @author: ppt
	 * @date: 2015-3-16 上午10:13:17
	 * @param tableName
	 * @return: void
	 */
	public void getService(String tableName, CachedRowSet resultSetColumn, String primaryKey,String primaryKeyType) {
		try {
			
			String entityPrimaryKey= Helper.getFormatString(primaryKey,false);
			String PrimaryKeyMethodName= Helper.getFormatString(primaryKey,true);
			String className = Helper.getFormatString(tableName, true);//表名
			String fileName = className + "Mapper";//文件名
			String thisFileName = className + "Mapper";//接口地址
			StringBuffer header = new StringBuffer(
					"<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
			header.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
			header.append("<mapper namespace=\"");
			header.append(properties.getMapperPackageName()+"."+thisFileName);
			header.append("\">\n\n");
			
			
			
			StringBuffer resultMap = new StringBuffer(" \t<resultMap id=\"BaseResultMap\" type=\""+properties.getModelPackageName()+"."+className+"\" >\n");
			StringBuffer sqlMap = new StringBuffer("\t<sql id=\"select_option\">\n");
			StringBuffer baseMap = new StringBuffer("\t<sql id=\"Base_Column_List\">\n\t\t");
			StringBuffer updateMap=new StringBuffer("\t<sql id=\"update_option\">\n");
			StringBuffer insertColumnMap=new StringBuffer();
			StringBuffer insertValueMap=new StringBuffer();
			
			while (resultSetColumn.next()) {
				String columnName = resultSetColumn.getString("COLUMN_NAME");
				String columnType = resultSetColumn.getString("TYPE_NAME");
				String entityColumn= Helper.getFormatString(columnName,false);
				columnType=Helper.getDbColumnType(columnType);
				if (columnName.equals(primaryKey)){
					resultMap.append("\t\t<id column=\""+columnName+"\" property=\""+entityColumn+"\" jdbcType=\""+ columnTypeToMyBatis(columnType)+"\" />\n");
				}else{
					resultMap.append("\t\t<result column=\""+columnName+"\" property=\""+entityColumn+"\" jdbcType=\""+ columnTypeToMyBatis(columnType)+"\" />\n");
				}
				
				if (resultSetColumn.isLast()){
					baseMap.append(columnName);
				}else{
					baseMap.append(columnName).append(",");
				}
				
				
				sqlMap.append("\t\t<if test=\"").append(entityColumn).append(" != null\">\n").append("\t\t\t and ")
						.append(getCondition(columnName,entityColumn, columnTypeToMyBatis(columnType)))
						.append("\n").append("\t\t</if>\n");
				
				
				
				if (columnName.equals(primaryKey)){
					continue;
				}
				updateMap.append("\t\t<if test=\"").append(entityColumn).append(" != null\">\n").append("\t\t\t")
						.append(getCondition(columnName,entityColumn, columnTypeToMyBatis(columnType)))
						.append(",\n").append("\t\t</if>\n");
				insertColumnMap.append("\t\t\t<if test=\"").append(entityColumn).append(" != null\">\n").append("\t\t\t\t")
						.append(columnName).append(",\n").append("\t\t\t</if>\n");
				insertValueMap.append("\t\t\t<if test=\"").append(entityColumn).append(" != null\">\n").append("\t\t\t\t")
						.append(insertCondition(entityColumn, columnTypeToMyBatis(columnType)))
						.append(",\n").append("\t\t\t</if>\n");
				
			}
			
			resultMap.append("\t</resultMap>\n\n");
			baseMap.append("\n\t</sql>\n\n");
			sqlMap.append("\t</sql>\n\n");
			updateMap.append("\t</sql>\n\n");
			StringBuffer select=null;
			StringBuffer delete=null;
			StringBuffer update=null;
			if (properties.isSelectByPrimaryKey()) {
				select=new StringBuffer("\t<select id=\"select"+className+"By").append(PrimaryKeyMethodName).append("\" ")
						.append("resultMap=\"BaseResultMap\">\n");
				select.append("\t\tselect <include refid=\"Base_Column_List\" />")
						.append(" from ").append(tableName).append("\n\t\t where ").append(primaryKey)
						.append("= #{").append(entityPrimaryKey).append("}\n").append("\t</select>\n\n");

			}
			
			if (properties.isDeleteByPrimaryKey()) {
				delete=new StringBuffer("\t<delete id=\"delete"+className+"By").append(PrimaryKeyMethodName).append("\" ")
						.append(">\n");
				delete.append("\t\tdelete")
						.append(" from ").append(tableName).append("\n\t\t where ").append(primaryKey)
						.append("= #{").append(entityPrimaryKey).append("}\n").append("\t</delete>\n\n");
			}
			
			if (properties.isUpdateByPrimaryKey()) {
				update=new StringBuffer("\t<update id=\"update"+className+"By").append(PrimaryKeyMethodName).append("\" ")
						.append(">\n");
				update.append("\t\tupdate "+tableName+" \n").append("\t\t<set>\n").append("\t\t<include refid=\"update_option\"/>\n")
						
						.append("\t\t</set>\n").append("\n\t\t where ").append(primaryKey)
						.append("= #{").append(entityPrimaryKey).append("}\n").append("\t</update>\n\n");
			}
			StringBuffer insert=null;
			if (properties.isInsertSelective()){
				insert=new StringBuffer("\t<insert id=\"insert"+className+"\" ").append("parameterType=\"")
						.append(properties.getModelPackageName()+"."+className).append("\"").append(">\n")
						.append("\t\tinsert into ").append(tableName)
						.append("\n\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >\n");
				
				insert.append(insertColumnMap).append("\t\t</trim>\n");
				insert.append("\t\tvalues\n");
				insert.append("\t\t <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >\n");
				insert.append(insertValueMap).append("\t\t</trim>\n").append("\t</insert>\n\n");
			}
			StringBuffer page=null;
			if (properties.isPage()){
				page=new StringBuffer("\t<select id=\"find"+className+"List\" ").append("parameterType=\"")
						.append(properties.getModelPackageName()+"."+className).append("\" ").append("resultMap=\"BaseResultMap\"").append(">\n")
						.append("\t\tselect ").append("<include refid=\"Base_Column_List\" />").append(" from ").append(tableName)
						.append("\n\t\twhere \n")
						.append("1=1")
						.append("\t\t<include refid=\"select_option\" />\n").append("\t</select>\n\n");
			}
			
			
			header.append(resultMap);
			header.append(baseMap);
			header.append(select);
			header.append(delete);
			header.append(update);
			header.append(page);
			header.append(insert);
			header.append(updateMap);
			header.append(sqlMap);
			header.append("</mapper>");
			 Helper.outputToFile(Helper.getFilePath(properties.getPath()+properties.getMapperXmlPackagePath(),properties.getMapperXmlPackageName()),fileName+".xml", header.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  id = #{id,jdbcType=INTEGER}
	 * @param columnName 列名
	 * @param entityColumn 列实体名
	 * @param columnType 类型
	 * @return
	 */
	private StringBuffer getCondition(String columnName,String entityColumn,String columnType){
    	StringBuffer str = new StringBuffer();
    	str.append(columnName).append("= #{").
			    append(entityColumn).append(",jdbcType=")
			    .append(columnType).append("}");
    	return str;
	
    }
	
	
	private StringBuffer insertCondition(String entityColumn,String columnType){
		StringBuffer str = new StringBuffer();
		str.append("#{").
				append(entityColumn).append(",jdbcType=")
				.append(columnType).append("}");
		return str;
		
	}
    
    
    private String columnTypeToMyBatis(String columnType){
		if (columnType.equals("DATETIME")){
			return "TIMESTAMP";
		}if (columnType.equals("TEXT")){
		    return "LONGVARCHAR";
		}
		
		else {
			return columnType;
		}
    }
    
}
