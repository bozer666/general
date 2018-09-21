package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenerateMybatisRepository {
	
	@Autowired
	private Properties properties;
	
	
	public void getService(String tableName, String primaryKey,String primaryKeyType) {
		
		String entity = Helper.getFormatString(tableName, true);
		String entityObj = Helper.getFormatString(tableName, false);
		primaryKey = Helper.getFormatString(primaryKey, true);
		String primaryKeyTypeObj = Helper.getColumnType(primaryKeyType);
		String className = entity + "Mapper";
		String dto = entity;
		StringBuffer sb = new StringBuffer();
		sb.append("package " + properties.getMapperPackageName() + ";\n\n");
		sb.append("import " + properties.getModelPackageName() + "." + dto + ";\n");
		sb.append("import java.util.List;\n");
		sb.append("\npublic interface " + className + " {\n\n");
		
		if (properties.isSelectByPrimaryKey()) {
			sb.append("\t" + entity + " select"+entity+"By" + primaryKey + "(" + primaryKeyTypeObj + " "+entityObj+");\n\n");
		}
		
		if (properties.isDeleteByPrimaryKey()) {
			sb.append("\tint "+ "delete"+entity+"By" + primaryKey + "(" + primaryKeyTypeObj + " "+entityObj+");\n\n");
		}
		
		if (properties.isUpdateByPrimaryKey()) {
			sb.append("\tint " + "update"+entity+"By" + primaryKey + "(" + entity + " "+entityObj+");\n\n");
		}
		if (properties.isInsertSelective()){
			sb.append("\tint " + "insert"+entity+"(" + entity + " "+entityObj+");\n\n");
			
		}
		if (properties.isPage()){
			sb.append("\tList<" + entity + "> find"+entity+"List(" + entity + " "+entityObj+");\n\n");
		}
		
		
		sb.append("}\n");
		Helper.outputToFile(Helper.getFilePath(properties.getPath()+properties.getMapperPackagePath(),properties.getMapperPackageName()), className + ".java", sb.toString());
	}
	
}
