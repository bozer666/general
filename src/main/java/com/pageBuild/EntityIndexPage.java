package com.pageBuild;

import com.Helper;
import com.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

@Component
public class EntityIndexPage {
	
	@Autowired
	private Properties properties;
	
	
	public void getService(String tableName, CachedRowSet resultSetColumn, String primaryKey) throws SQLException {
	
	
	    String entityObj = Helper.getFormatString(tableName, false);
	    StringBuffer sb= new StringBuffer();
		sb.append("<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\"\n" +
			    "\tpageEncoding=\"UTF-8\"%>\n" +
			    "<%@include file=\"../layout/tablib.jsp\"%>\n" +
			    "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
			    "<html>\n" +
			    "<head>\n" +
			    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
			    "<title>Insert title here</title>\n" +
			    "\n" +
			    "</head>\n" +
			    "<body>\n" +
			    "\t\n" +
			    "\t\n" +
			    "\t<!--列表界面-->\n" +
			    "\t<div class=\"box\"> \n" +
			    "\t\t<section class = \"content\">\n" +
			    "\t\t\t<p class = \"detail-title\">\n" +
			    "\t\t\t\t<a class = \"setup\"  target=\"contentF\" onclick=\"toAddPage()\">新增</a>\n" +
			    "\t\t\t</p>\n" +
			    "\t\t<form action=\"${ctx }/rest/"+entityObj+"/list\" method=\"POST\" id=\"searchForm\"\n" +
			    "\t\t\t\tonsubmit=\"submitSearchRequest('searchForm','listDiv');return false;\">\n" +
			    "\t\t\t<ul class = \"userinfo row\">\n" +
			    "\t\t\t\t<li><span>select：</span>\n" +
			    "\t\t\t\t\t<select name=\"cityId\" id=\"cityId\" class=\"dropdown\">\n" +
			    "\t\t\t\t\t\t<option value=\"\" >全部</option>\n" +
			    "\t\t\t\t\t\t<c:forEach items=\"${citys}\" var=\"item\">\n" +
			    "\t\t\t\t\t\t<option value=\"${item.id}\" >${item.name}</option>\n" +
			    "\t\t\t\t\t\t</c:forEach>\n" +
			    "\t\t\t\t\t</select>\n" +
			    "\t\t\t\t</li>\n" +
			    "\t\t\t\t<li><span>手机号码：</span>\n" +
				"\t\t\t\t\t<input type=\"text\" name=\"phone\" id=\"phone\" placeholder=\"请输入手机号码\" /></li>\n" +
			    "\t\t\t\t</li>\n" +
			    "\t\t\t\t<li>\n" +
				"\t\t\t\t\t<input class = \"public_btn bg1\" type=\"submit\" name=\"inquery\" id=\"inquery\" value = \"查询\"/>\n" +
				"\t\t\t\t</li>\n" +
			    "\t\t\t</ul>\n" +
			    "\t\t\t</form>\n" +
			    "\t\t\t<div id=\"listDiv\"></div>\n" +
			    "\t\t</section>\n" +
			    "\t</div>\n" +
			    "\t\n" +
			    "\t\n" +
			    "\t<script type=\"text/javascript\">\n" +
			    "        function toAddPage(){\n" +
			    "            layer.open({\n" +
			    "                type: 2,\n" +
			    "                skin: 'layer-style',\n" +
			    "                area: ['500px','700px'],\n" +
			    "                shadeClose: false, //点击遮罩关闭\n" +
			    "                title:['新增'],\n" +
			    "                resize: false,\n" +
			    "                scrollbar:false,\n" +
			    "                content:['${ctx}/rest/"+entityObj+"/to_add', 'no']\n" +
			    "            });\n" +
			    "        }\n" +
			    "\t\t window.onload = function(){\n" +
			    "             $('#searchForm').submit();\n" +
			    "\t\t}\n" +
			    "\t</script>\n" +
				"\t<script type=\"text/javascript\" src=\"${ctx}/assets/scripts/layer/layer.js\"></script>\n"+
			    "</body>\n" +
			    "</html>");
		
		
        Helper.outputToFile(Helper.getFilePath(properties.getPageRoot(),properties.getPagePackageName())+"/"+entityObj,entityObj+"Index"+".jsp", sb.toString());
    }
   
}
