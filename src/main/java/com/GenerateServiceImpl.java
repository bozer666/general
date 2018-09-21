package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenerateServiceImpl {
	
	@Autowired
	private Properties properties;
	
	
    public void getService(String tableName,String primaryKey,String primaryKeyColumnType) {
	
	
	    String entity = Helper.getFormatString(tableName, true);
	    String entityObj = Helper.getFormatString(tableName, false);
	    String primaryKeyMethod = Helper.getFormatString(primaryKey, true);
	    String primaryKeyParam = Helper.getFormatString(primaryKey, false);
	    String columnType = Helper.getColumnType(primaryKeyColumnType);
	
	    
	    
	    
	    String mybatis = entity + "Mapper";
    		String service = entity + "Service";
    		String className = service + "Impl";
    		StringBuffer sb = new StringBuffer();
    		sb.append("package " + properties.getServiceImplPackageName() + ";\n\n");
    		sb.append("import " + properties.getModelPackageName() + "." + entity + ";\n");
    		sb.append("import " + properties.getMapperPackageName() + "." + mybatis + ";\n");
    		sb.append("import " + properties.getServicePackageName() + "." + service + ";\n");
    		sb.append("import org.slf4j.Logger;\n");
    		sb.append("import org.slf4j.LoggerFactory;\n");
    		sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");
    		sb.append("import org.springframework.stereotype.Service;\n");
    		sb.append("import java.util.List;\n");
    		sb.append("@Service\n");
    		sb.append("public class " + className + " implements "+ service +" {\n\n");
    		
    		sb.append("\tprivate Logger log = LoggerFactory.getLogger("+className+".class);\n");
    		String instance = mybatis.substring(0,1).toLowerCase() + mybatis.substring(1);
    		sb.append("\t@Autowired\n");
    		sb.append("\tprivate "+mybatis+" " + instance +";\n\n");
	
		    if (properties.isInsertSelective()){
			    sb.append("\t/**\n");
			    sb.append("\t * ").append("插入").append("\n");
			    sb.append("\t **/\n");
			    sb.append("\t@Override\n");
			    sb.append("\tpublic void " + "insert"+entity+"(" + entity + " "+entityObj+") throws Exception {\n");
			    sb.append("\t\ttry {\n");
			    sb.append("\t\t\tlog.debug(\"insert {}\",").append(entityObj).append(");\n");
			    sb.append("\t\t\t"+instance+".insert"+entity+"("+entityObj+");\n");
			    sb.append("\t\t} catch(Exception e) {\n");
			    sb.append("\t\t\tlog.error(\"insert"+entity+"异常 {}\",").append(entityObj).append(",e").append(");\n");
			    sb.append("\t\t\tthrow e;\n");
			    sb.append("\t\t}\n");
			    sb.append("\t}\n\n");
		    }
			    
		    if (properties.isSelectByPrimaryKey()){
			    sb.append("\t/**\n");
			    sb.append("\t * ").append("查询").append("\n");
			    sb.append("\t **/\n");
			    sb.append("\t@Override\n");
			    sb.append("\tpublic " + entity + " select"+entity+"By" + primaryKeyMethod + "(" + columnType + " "+primaryKeyParam+") {\n");
			    sb.append("\t\tlog.debug(\"select"+entity+"By"+primaryKeyMethod+"  {}\",").append(primaryKeyParam).append(");\n");
			    sb.append("\t\treturn "+instance+".select"+entity+"By" + primaryKeyMethod + "("+primaryKeyParam+");\n");
			    sb.append("\t}\n\n");
		    }
	    
	
	    if (properties.isDeleteByPrimaryKey()){
		    sb.append("\t/**\n");
		    sb.append("\t * ").append("删除").append("\n");
		    sb.append("\t **/\n");
		    sb.append("\t@Override\n");
		    sb.append("\tpublic void "+ "delete"+entity+"By" + primaryKeyMethod + "(" + columnType + " "+primaryKeyParam+")throws Exception {\n");
		    sb.append("\t\ttry {\n");
		    sb.append("\t\t\tlog.debug(\"delete"+entity+"By"+primaryKeyMethod+"  {}\",").append(primaryKeyParam).append(");\n");
		    sb.append("\t\t\tint result = "+instance+".delete"+entity+"By"+primaryKeyMethod+"("+primaryKeyParam+");\n");
		    sb.append("\t\t\tif (result < 1) {\n");
		    sb.append("\t\t\t\tthrow new Exception(\"delete"+entity+"By"+primaryKeyMethod+"失败\");\n");
		    sb.append("\t\t\t}\n");
		    sb.append("\t\t} catch(Exception e) {\n");
		    sb.append("\t\t\tlog.error(\"delete"+entity+"By"+primaryKeyMethod+"  {}\",").append(primaryKeyParam).append(",e").append(");\n");
		    sb.append("\t\t\tthrow e;\n");
		    sb.append("\t\t}\n");
		    sb.append("\t}\n");
	    }
	    
	
	    if (properties.isUpdateByPrimaryKey()){
		    sb.append("\t/**\n");
		    sb.append("\t * ").append("更新").append("\n");
		    sb.append("\t **/\n");
		    sb.append("\t@Override\n");
		    sb.append("\tpublic void "+ "update"+entity+"By" + primaryKeyMethod + "(" + entity + " "+entityObj+") throws Exception {\n");
		    sb.append("\t\ttry {\n");
		    sb.append("\t\t\tlog.debug(\"update"+entity+"By"+primaryKeyMethod+"  {}\",").append(entityObj).append(");\n");
		    sb.append("\t\t\tint result = "+instance+".update"+entity+"By"+primaryKeyMethod+"("+entityObj+");\n");
		    sb.append("\t\t\tif (result < 1) {\n");
		    sb.append("\t\t\t\tthrow new Exception(\"update"+entity+"By"+primaryKeyMethod+"失败\");\n");
		    sb.append("\t\t\t}\n");
		    sb.append("\t\t} catch(Exception e) {\n");
		    sb.append("\t\t\tlog.error(\"update"+entity+"By"+primaryKeyMethod+"  {}\",").append(entityObj).append(",e").append(");\n");
		    sb.append("\t\t\tthrow e;\n");
		    sb.append("\t\t}\n");
		    sb.append("\t}\n\n");
	    }
	
	    if (properties.isPage()){
		    sb.append("\t/**\n");
		    sb.append("\t * ").append("分页，这里建议使用插件（com.github.pagehelper.PageHelper）").append("\n");
		    sb.append("\t **/\n");
		    sb.append("\t@Override\n");
		    sb.append("\tpublic List<" + entity + "> find"+entity+"List(" + entity + " "+entityObj+") throws Exception {\n");
		    sb.append("\t\ttry {\n");
		    sb.append("\t\t\tlog.debug(\"find"+entity+"List  {}\",").append(entityObj).append(");\n");
		    sb.append("\t\t\treturn "+instance+".find"+entity+"List("+entityObj+");\n");
		    sb.append("\t\t} catch(Exception e) {\n");
		    sb.append("\t\t\tlog.error(\"find"+entity+"List  {}\",").append(entityObj).append(",e").append(");\n");
		    sb.append("\t\t\tthrow e;\n");
		    sb.append("\t\t}\n");
		    sb.append("\t}\n");
	    }
	    
	    sb.append("}\n");
        Helper.outputToFile(Helper.getFilePath(properties.getPath()+properties.getServiceImplPackagePath(),properties.getServiceImplPackageName()),className+".java", sb.toString());
    }
   
}
