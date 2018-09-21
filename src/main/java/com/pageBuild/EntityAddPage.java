package com.pageBuild;

import com.Helper;
import com.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

@Component
public class EntityAddPage {
	
	@Autowired
	private Properties properties;
	
	
	public void getService(String tableName, CachedRowSet resultSetColumn, String primaryKey) throws SQLException {
		
		
		
		String entityObj = Helper.getFormatString(tableName, false);
		String entity = Helper.getFormatString(tableName, true);
		StringBuffer entityName= new StringBuffer();
		while(resultSetColumn.next()) {
			String columnName1 = resultSetColumn.getString("COLUMN_NAME");
			;
			if (columnName1.equals(Helper.getFormatString(primaryKey,false)) ){
				continue;
			}
			columnName1 = Helper.getFormatString(columnName1, false);
			entityName.append("            <li><span>"+columnName1+"：</span>\n" +
					"               <input type=\"text\" name=\""+columnName1+"\" id=\""+columnName1+"\" ß/>\n" +
					"            </li>\n");
			
		}
		
		
	    StringBuffer sb= new StringBuffer();
		sb.append("<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\"\n" +
				"         pageEncoding=\"UTF-8\" %>\n" +
				"<%@include file=\"../layout/tablib.jsp\" %>\n" +
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
				"<html>\n" +
				"<head>\n" +
				"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
				"    <title>Insert title here</title>\n" +
				"    <script type=\"text/javascript\">\n" +
				"    </script>\n" +
				"\n" +
				"</head>\n" +
				"<body>\n" +
				"<section class=\"content\" style=\"border:none;\">\n" +
				"    <form action=\"${ctx}/rest/"+entityObj+"/add\" method=\"post\" id=\"dataForm\">\n" +
				"        <ul class=\"userinfo row\">\n" +
				"            <input type=\"hidden\" name=\"id\" id=\"id\" value=\"${item.id}\">\n");
				sb.append(entityName);
				sb.append(
				"            <li>\n" +
				"                <span></span>\n" +
				"                <a target=\"contentF\" class=\"public_btn bg2\" id=\"save\" onclick=\"updateData()\">更新</a>\n" +
				"                <a style=\"margin-left: 20px\" class=\"public_btn bg3\" id=\"cancel\" onclick=\"closeWin();\">取消</a>\n" +
				"            </li>\n" +
				"        </ul>\n" +
				"\n" +
				"    </form>\n" +
				"</section>\n" +
				"\n" +
				"<script type=\"text/javascript\">\n" +
				"    //保存数据\n" +
				"    function updateData() {\n" +
				"        var data = $(\"#dataForm\").serialize();\n" +
				"        debugger;\n" +
				"        $.ajax({\n" +
				"            type: \"post\",\n" +
				"            url: \"${ctx}/rest/"+entityObj+"/add\",\n" +
				"            async: false, // 此处必须同步\n" +
				"            dataType: \"json\",\n" +
				"            data: data,\n" +
				"            success: function (data) {\n" +
				"                if (data.state == 0) {\n" +
				"                    layer.msg(\"保存成功！！！\", {icon: 1});\n" +
				"                    $('#save').removeAttr(\"onclick\");\n" +
				"                    setTimeout(function () {\n" +
				"                        parent.location.reload();\n" +
				"                    }, 1000);\n" +
				"\n" +
				"                } else {\n" +
				"                    layer.msg(\"保存失败！\", {icon: 2});\n" +
				"                }\n" +
				"            },\n" +
				"            error: function () {\n" +
				"                layer.msg(\"保存失败！\", {icon: 2});\n" +
				"            }\n" +
				"        });\n" +
				"    }\n" +
				"\n" +
				"    //取消\n" +
				"    function closeWin() {\n" +
				"        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引\n" +
				"        parent.layer.close(index);\n" +
				"    }\n" +
				"</script>\n" +
				"</body>\n" +
				"</html>");
		
		
        Helper.outputToFile(Helper.getFilePath(properties.getPageRoot(),properties.getPagePackageName())+"/"+entityObj,"add"+entity+".jsp", sb.toString());
    }
   
}
