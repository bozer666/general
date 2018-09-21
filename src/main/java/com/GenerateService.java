package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenerateService {
	@Autowired
	private Properties properties;

	
	
	
    
    public void getService(String tableName, String primaryKey,String primaryKeyColumnType) {
	
	    String entity = Helper.getFormatString(tableName, true);
	    String entityObj = Helper.getFormatString(tableName, false);
	    String primaryKeyMethod = Helper.getFormatString(primaryKey, true);
	    String primaryKeyParam = Helper.getFormatString(primaryKey, false);
    	String columnType = Helper.getColumnType(primaryKeyColumnType);
    		String className = entity + "Service";
    		StringBuffer sb = new StringBuffer();
    		sb.append("package " + properties.getServicePackageName() + ";\n\n");
    		sb.append("import " + properties.getModelPackageName() + "." + entity + ";\n");
    		sb.append("import java.util.List;\n");
    		sb.append("public interface " + className + " {\n\n");
	    
	
	    if (properties.isSelectByPrimaryKey()) {
		    sb.append("\t/**\n");
		    sb.append("\t * ").append("主键查询").append("\n");
		    sb.append("\t **/\n");
		    sb.append("\t" + entity + " select"+entity+"By" + primaryKeyMethod + "(" + columnType + " "+primaryKeyParam+");\n\n");
	    }
	
	    if (properties.isDeleteByPrimaryKey()) {
		    sb.append("\t/**\n");
		    sb.append("\t * ").append("主键删除").append("\n");
		    sb.append("\t **/\n");
		    sb.append("\tvoid "+ "delete"+entity+"By" + primaryKeyMethod + "(" + columnType + " "+primaryKeyParam+") throws Exception;\n\n");
	    }
	
	    if (properties.isUpdateByPrimaryKey()) {
		    sb.append("\t/**\n");
		    sb.append("\t * ").append("主键更新").append("\n");
		    sb.append("\t **/\n");
		    sb.append("\tvoid " + "update"+entity+"By" + primaryKeyMethod + "(" + entity + " "+entityObj+") throws Exception;\n\n");
	    }
	    if (properties.isInsertSelective()){
		    sb.append("\t/**\n");
		    sb.append("\t * ").append("插入").append("\n");
		    sb.append("\t **/\n");
		    sb.append("\tvoid " + "insert"+entity+"(" + entity + " "+entityObj+") throws Exception;\n\n");
		
	    }
	    if (properties.isPage()){
		    sb.append("\t/**\n");
		    sb.append("\t * ").append("分页，这里建议使用插件（com.github.pagehelper.PageHelper）").append("\n");
		    sb.append("\t **/\n");
		    sb.append("\tList<" + entity + "> find"+entity+"List(" + entity + " "+entityObj+") throws Exception;\n\n");
	    }
        sb.append("}\n");
        Helper.outputToFile(Helper.getFilePath(properties.getPath()+properties.getServicePackagePath(),properties.getServicePackageName()),className+".java", sb.toString());
    }
	
}
