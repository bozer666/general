package com.pageBuild;

import com.Helper;
import com.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

@Component
public class EntityListPage {
	
	@Autowired
	private Properties properties;
	
	
	public void getService(String tableName, CachedRowSet resultSetColumn, String primaryKey) throws SQLException {
	
	
	    String entityObj = Helper.getFormatString(tableName, false);
	    String primaryKeyParam = Helper.getFormatString(primaryKey, false);
	    StringBuffer entityName= new StringBuffer();
	    StringBuffer entityValue= new StringBuffer();
		while(resultSetColumn.next()) {
			String columnName = resultSetColumn.getString("COLUMN_NAME");
			columnName = Helper.getFormatString(columnName, false);
			if (columnName.equals(Helper.getFormatString(primaryKey,false)) ){
				continue;
			}
			entityName.append("\t\t\t\t<th style=\"width: 8%\">"+columnName+"</th>\n");
			entityValue.append("\t\t\t\t\t\t<td>${item."+columnName +"}</td>\n");
			
		}
		
    		StringBuffer sb = new StringBuffer();
    		sb.append("<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\"\n" +
				    "         pageEncoding=\"UTF-8\" %>\n" +
				    "<%@include file=\"../layout/tablib.jsp\" %>\n" +
				    "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
				    "<html>\n" +
				    "<head>\n" +
				    "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
				    "    <title>Insert title here</title>\n" +
				    "</head>\n" +
				    "<body>\n" +
				    "\n" +
				    "\n" +
				    "<div class=\"table-responsive m40\">\n" +
				    "    <table class=\"table table-bordered table-add\">\n" +
				    "        <thead>\n" +
				    "            <tr>\n");
    		
    		sb.append(entityName);
		    sb.append("                <th style=\"width: 10%\">操作</th>\n" +
		            "            </tr>\n" +
				    "        </thead>\n" +
				    "        <tbody>\n" +
				    "        <c:choose>\n" +
				    "            <c:when test=\"${!empty requestScope.items }\">\n" +
				    "                <c:forEach items=\"${requestScope.items }\" var=\"item\" varStatus=\"xh\">\n" +
				    "                    <tr>\n");
    		sb.append("\t\t\t\t\t\t<input type=\"hidden\" id=\""+primaryKeyParam+"\" value=\"${item."+primaryKeyParam+"}\"/>\n");
    		sb.append(entityValue);
    		sb.append("\t\t\t\t\t\t<td>\n" +
				    "                            <a target=\"contentF\" onclick=\"to_edit(${item."+primaryKeyParam+" })\">编辑</a>\n" +
				    "                            <a target=\"contentF\" onclick=\"delete1(${item."+primaryKeyParam+" })\">删除</a>\n" +
				    "                        </td>\n" +
				    "                    </tr>\n" +
				    "                </c:forEach>\n" +
				    "            </c:when>\n" +
				    "            <c:otherwise>\n" +
				    "                <tr>\n" +
				    "                    <td colspan=\"14\">没有找到数据!</td>\n" +
				    "                </tr>\n" +
				    "            </c:otherwise>\n" +
				    "        </c:choose>\n" +
				    "        </tbody>\n" +
				    "    </table>\n" +
				    "</div>\n" +
				    "<!-- 分页 -->\n" +
				    "<c:if test=\"${!empty paginator}\">\n" +
				    "    <c:set var=\"paginator\" value=\"${paginator}\"/>\n" +
				    "    <c:set var=\"target\" value=\"listDiv\"/>\n" +
				    "    <%@include file=\"../layout/pagination.jsp\" %>\n" +
				    "</c:if>\n" +
				    "\n" +
				    "<script type=\"text/javascript\">\n" +
				    "\n" +
				    "    function delete1(id){\n" +
				    "        $.ajax({\n" +
				    "            type: \"post\",\n" +
				    "            url: \"${ctx}/rest/"+entityObj+"/update?"+primaryKeyParam+"=\"+id+\"&isDelete=1\",\n" +
				    "            async: false, // 此处必须同步\n" +
				    "            dataType: \"json\",\n" +
				    "\n" +
				    "            success: function (data) {\n" +
				    "                if (data.state==0){\n" +
				    "                    $('#searchForm').submit();\n" +
				    "                }\n" +
				    "            },\n" +
				    "            error: function () {\n" +
				    "                layer.msg(\"操作失败！\", {icon: 2});\n" +
				    "            }\n" +
				    "        })\n" +
				    "    }\n" +
				    "\n" +
				    "    function to_edit(id){\n" +
				    "        layer.open({\n" +
				    "            type: 2,\n" +
				    "            title: ['修改'],\n" +
				    "            shade: 0.3,\n" +
				    "            area: ['500px', '700px'],\n" +
				    "            content: ['${ctx}/rest/"+entityObj+"/to_update?"+primaryKeyParam+"='+id,'no']\n" +
				    "        });\n" +
				    "    }\n" +
				    "\n" +
				    "\n" +
				    "</script>\n" +
				    "</body>\n" +
				    "</html>");
		
        Helper.outputToFile(Helper.getFilePath(properties.getPageRoot(),properties.getPagePackageName())+"/"+entityObj,entityObj+"List"+".jsp", sb.toString());
    }
   
}
