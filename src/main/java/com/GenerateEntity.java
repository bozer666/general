package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

@Component
public class GenerateEntity {

    @Autowired
    private Properties properties;


    /**
     * @param tableName
     * @Description: 获取表对应的所有列
     * @author: ppt
     * @date: 2015-3-16 上午10:13:17
     * @return: void
     */
    public void getService(String tableName, String tableComment, CachedRowSet resultSet) {
        try {
            CachedRowSet resultSet1 = resultSet.createCopy();
            CachedRowSet resultSet2 = resultSet.createCopy();
            CachedRowSet resultSet3 = resultSet.createCopy();
            String ACCESS_DOMAIN = "private";
            while (resultSet3.next()) {
                String className = Helper.getFormatString(tableName, true);
                StringBuffer header = new StringBuffer("package " + properties.getModelPackageName() + ";\n\n");
                StringBuffer footer = new StringBuffer();
                StringBuffer contentBuffer = new StringBuffer();
                
                contentBuffer.append("public class " + className + "{\n");


                boolean importDateFlag = true;

                while (resultSet1.next()) {

                    String columnType = resultSet1.getString("TYPE_NAME");
                    String COLUMN_TYPE = Helper.getColumnType(columnType);
                    if ("Date".equals(COLUMN_TYPE) && importDateFlag) {
                        importDateFlag = false;
                        header.append("import java.util.Date;\n");
                    }
                    String remark = resultSet1.getString("REMARKS");

                    contentBuffer.append("\t/**\n");
                    contentBuffer.append("\t * ").append(remark).append("\n");
                    contentBuffer.append("\t **/\n");
                    contentBuffer.append("\t" + ACCESS_DOMAIN + " ");
                    contentBuffer.append(COLUMN_TYPE + " ");
                    String columnName = resultSet1.getString("COLUMN_NAME");
                    columnName = Helper.getFormatString(columnName, false);
                    footer.append(getSetGeneral(columnName, COLUMN_TYPE));
                    contentBuffer.append(columnName + ";\n");
                }
                contentBuffer.append("\n\n" + footer);
                contentBuffer.append(toString(resultSet2, className));
                contentBuffer.append("}");
                header.append("\n");
                header.append(contentBuffer);
                Helper.outputToFile(Helper.getFilePath(properties.getPath() + properties.getModelPackagePath(), properties.getModelPackageName()), className + ".java", header.toString());
            }
        } catch (SQLException e) {

        }
    }


    private String getSetGeneral(String columnName, String columnType) {
        StringBuffer sb = new StringBuffer();
        sb.append("\tpublic " + columnType + " get" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1, columnName.length()) + "() {\n");
        sb.append("\t\treturn " + columnName + ";\n");
        sb.append("\t}\n\n");
        sb.append("\tpublic void set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1, columnName.length()));
        sb.append("(" + columnType + " " + columnName + ") {\n");
        sb.append("\t\tthis." + columnName + " = " + columnName + ";\n");
        sb.append("\t}\n\n");
        return sb.toString();
    }

    private String toString(CachedRowSet resultSet2, String entity) throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append("\t@Override\n").append("\tpublic String toString() {\n")
                .append("\t\t final StringBuffer sb=new StringBuffer(\"").append(entity).append("{\");\n");
        while (resultSet2.next()) {
            String columnName = resultSet2.getString("COLUMN_NAME");
            columnName = Helper.getFormatString(columnName, false);
            sb.append("\t\tif (").append(columnName).append(" != null) {\n")
                    .append("\t\t\tsb.append(\", \\\"").append(columnName).append("\\\":\\\"").append("\")")
                    .append(".append(").append(columnName).append(").append(\"").append("\\\"\");\n").append("\t\t}\n");
        }
        sb.append("\t\tsb.append(").append("\"}\"").append(");\n");
        sb.append("\t\treturn sb.toString();\n");
        sb.append("\t}\n");
        return sb.toString();
    }


}
