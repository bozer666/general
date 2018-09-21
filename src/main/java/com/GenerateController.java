package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenerateController {

    @Autowired
    private Properties properties;


    public void getService(String tableName, String tableComment, String primaryKey, String primaryKeyColumnType) {


        String entity = Helper.getFormatString(tableName, true);
        String entityObj = Helper.getFormatString(tableName, false);
        String primaryKeyMethod = Helper.getFormatString(primaryKey, true);
        String primaryKeyParam = Helper.getFormatString(primaryKey, false);
        String columnType = Helper.getColumnType(primaryKeyColumnType);


        String service = entity + "Service";
        String className = entity + "Controller";
        StringBuffer sb = new StringBuffer();
        sb.append("package " + properties.getControllerPackageName() + ";\n\n");
        sb.append("import " + properties.getModelPackageName() + "." + entity + ";\n");
        sb.append("import " + properties.getServicePackageName() + "." + service + ";\n");
        sb.append("import "+properties.getPackageName()+".base.BaseFilter;\n");
        sb.append("import org.slf4j.Logger;\n");
        sb.append("import java.util.Map;\n");
        sb.append("import java.util.HashMap;\n");

        sb.append("import org.slf4j.LoggerFactory;\n");
        sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");
        sb.append("import org.springframework.stereotype.Controller;\n");
        sb.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
        sb.append("import org.springframework.web.bind.annotation.ResponseBody;\n");
        sb.append("import com.github.pagehelper.Page;\n");
        sb.append("import com.github.pagehelper.Paginator;\n");


        sb.append("import java.util.List;\n");
        sb.append("import "+properties.getPackageName()+".util.PageUtil;\n");
        sb.append("import org.springframework.ui.Model;\n");
        sb.append("import javax.servlet.http.HttpServletRequest;\n");
        sb.append("/**\n");
        sb.append(" * ").append(tableComment).append("\n");
        sb.append(" **/\n");
        sb.append("@Controller\n");
        sb.append("@SuppressWarnings(\"all\")\n");
        sb.append("@RequestMapping(\"/" + entityObj + "/\")\n");
        sb.append("public class " + className + " extends BaseFilter  {\n\n");

        sb.append("\tprivate Logger log = LoggerFactory.getLogger(" + className + ".class);\n");
        String instance = service.substring(0, 1).toLowerCase() + service.substring(1) + "Impl";
        sb.append("\t@Autowired\n");
        sb.append("\tprivate " + service + " " + instance + ";\n\n");


        sb.append("\t@RequestMapping(\"index\")\n");
        sb.append("\tpublic String " + entityObj + "Index(" + entity + " " + entityObj + ",Model model) throws Exception {\n");

        sb.append("\t\treturn \"").append(entityObj).append("/").append(entityObj).append("Index").append("\";\n");
        sb.append("\t}\n\n");

        if (properties.isInsertSelective()) {
            sb.append("\t/**\n");
            sb.append("\t * ").append("").append("\n");
            sb.append("\t **/\n");
            sb.append("\t@RequestMapping(\"to_add\")\n");
            sb.append("\tpublic String toAdd" + entity + "(" + entity + " " + entityObj + ",Model model) throws Exception {\n");

            sb.append("\t\treturn \"").append(entityObj).append("/add").append(entity).append("\";\n");
            sb.append("\t}\n\n");
        }


        if (properties.isInsertSelective()) {
            sb.append("\t/**\n");
            sb.append("\t * ").append("新增").append("\n");
            sb.append("\t **/\n");
            sb.append("\t@RequestMapping(\"add\")\n");
            sb.append("\t@ResponseBody\n");
            sb.append("\tpublic Object " + "add" + entity + "(" + entity + " " + entityObj + ") throws Exception {\n");
            sb.append("\t\tMap<String,Object> map = new HashMap<>();\n");
            sb.append("\t\ttry {\n");
            sb.append("\t\t\tlog.info(\"add" + entity + " {}\",").append(entityObj).append(");\n");
            sb.append("\t\t\t" + instance + ".insert" + entity + "(" + entityObj + ");\n");
            sb.append("\t\t\tmap.put(\"state\",0);\n");
            sb.append("\t\t} catch(Exception e) {\n");
            sb.append("\t\t\tlog.error(\"add" + entity + "异常 {}\",").append(entityObj).append(",e").append(");\n");
            sb.append("\t\t\tmap.put(\"state\",-1);\n");
            sb.append("\t\t}\n");
            sb.append("\t\treturn map;\n");
            sb.append("\t}\n\n");
        }

        if (properties.isSelectByPrimaryKey()) {
            sb.append("\t/**\n");
            sb.append("\t * ").append("查询").append("\n");
            sb.append("\t **/\n");
            sb.append("\t@RequestMapping(\"get_" + entityObj + "\")\n");
            sb.append("\tpublic String get" + entity + "By" + primaryKeyMethod + "(" + columnType + " " + primaryKeyParam + ",Model model) {\n");
            sb.append("\t\tlog.info(\"get" + entity + "By" + primaryKeyMethod + "  {}\",").append(primaryKeyParam).append(");\n");
            sb.append("\t\t" + entity + " result = " + instance + ".select" + entity + "By" + primaryKeyMethod + "(" + primaryKeyParam + ");\n");
            sb.append("\t\tmodel.addAttribute(\"item\",result);\n");
            sb.append("\t\treturn \"").append(entityObj).append("/").append("update").append(entity).append("\";");
            sb.append("\t}\n\n");
        }


        if (properties.isDeleteByPrimaryKey()) {
            sb.append("\t/**\n");
            sb.append("\t * ").append("删除").append("\n");
            sb.append("\t **/\n");
            sb.append("\t@RequestMapping(\"delete\")\n");
            sb.append("\t@ResponseBody\n");

            sb.append("\tpublic Object " + "delete" + entity + "By" + primaryKeyMethod + "(" + columnType + " " + primaryKeyParam + ")throws Exception {\n");
            sb.append("\t\tMap<String,Object> map = new HashMap<>();\n");
            sb.append("\t\ttry {\n");
            sb.append("\t\t\tlog.info(\"delete" + entity + "By" + primaryKeyMethod + "  {}\",").append(primaryKeyParam).append(");\n");
            sb.append("\t\t\t" + instance + ".delete" + entity + "By" + primaryKeyMethod + "(" + primaryKeyParam + ");\n");
            sb.append("\t\t\tmap.put(\"state\",0);\n");
            sb.append("\t\t} catch(Exception e) {\n");
            sb.append("\t\t\tlog.error(\"delete" + entity + "By" + primaryKeyMethod + "  {}\",").append(primaryKeyParam).append(",e").append(");\n");
            sb.append("\t\t\tmap.put(\"state\",-1);\n");
            sb.append("\t\t}\n");
            sb.append("\t\treturn map;\n");
            sb.append("\t}\n");
        }

        if (properties.isUpdateByPrimaryKey()) {
            sb.append("\t/**\n");
            sb.append("\t * ").append("").append("\n");
            sb.append("\t **/\n");
            sb.append("\t@RequestMapping(\"to_update\")\n");
            sb.append("\tpublic String toUpdate" + entity + "("+columnType+" id,Model model) throws Exception {\n");
            sb.append("\t\t" + entity + " result = " + instance + ".select" + entity + "By" + primaryKeyMethod + "(" + primaryKeyParam + ");\n");
            sb.append("\t\tmodel.addAttribute(\"item\",result);\n");
            sb.append("\t\treturn \"").append(entityObj).append("/update").append(entity).append("\";\n");
            sb.append("\t}\n\n");
        }
        if (properties.isUpdateByPrimaryKey()) {
            sb.append("\t/**\n");
            sb.append("\t * ").append("更新").append("\n");
            sb.append("\t **/\n");
            sb.append("\t@RequestMapping(\"update\")\n");
            sb.append("\t@ResponseBody\n");
            sb.append("\tpublic Object " + "update" + entity + "By" + primaryKeyMethod + "(" + entity + " " + entityObj + ") throws Exception {\n");
            sb.append("\t\tMap<String,Object> map = new HashMap<>();\n");
            sb.append("\t\ttry {\n");
            sb.append("\t\t\tlog.info(\"update" + entity + "By" + primaryKeyMethod + "  {}\",").append(entityObj).append(");\n");
            sb.append("\t\t\t" + instance + ".update" + entity + "By" + primaryKeyMethod + "(" + entityObj + ");\n");
            sb.append("\t\t\tmap.put(\"state\",0);\n");
            sb.append("\t\t} catch(Exception e) {\n");
            sb.append("\t\t\tlog.error(\"update" + entity + "By" + primaryKeyMethod + "  {}\",").append(entityObj).append(",e").append(");\n");
            sb.append("\t\t\tmap.put(\"state\",-1);\n");
            sb.append("\t\t}\n");
            sb.append("\t\treturn map;\n");
            sb.append("\t}\n\n");
        }

        if (properties.isPage()) {
            sb.append("\t/**\n");
            sb.append("\t * ").append("分页，这里建议使用插件（com.github.pagehelper.PageHelper）").append("\n");
            sb.append("\t **/\n");
            sb.append("\t@RequestMapping(\"list\")\n");
            sb.append("\tpublic String find" + entity + "List(" + entity + " " + entityObj + ",Model model, HttpServletRequest request) {\n");
            sb.append("\t\ttry {\n");
            sb.append("\t\t\tlog.info(\"find" + entity + "List  {}\",").append(entityObj).append(");\n");
            sb.append("\t\t\tPageUtil.doPage(request);\n");
            sb.append("\t\t\tPage<" + entity + "> result = (Page<" + entity + ">)" + instance + ".find" + entity + "List(" + entityObj + ");\n");


            sb.append("\t\t\tPaginator paginator=result.getPaginator();\n");
            sb.append("\t\t\tmodel.addAttribute(\"paginator\",paginator);\n");
            sb.append("\t\t\tmodel.addAttribute(\"items\",result);\n");

            sb.append("\t\t} catch(Exception e) {\n");
            sb.append("\t\t\tlog.error(\"find" + entity + "List  {}\",").append(entityObj).append(",e").append(");\n");
            sb.append("\t\t\tmodel.addAttribute(\"paginator\",null);\n");

            sb.append("\t\t\tmodel.addAttribute(\"items\",null);\n");
            sb.append("\t\t}\n");
            sb.append("\t\treturn \"").append(entityObj).append("/").append(entityObj).append("List").append("\";\n");

            sb.append("\t}\n");
        }

        sb.append("}\n");
        Helper.outputToFile(Helper.getFilePath(properties.getPath() + properties.getControllerPackagePath(), properties.getControllerPackageName()), className + ".java", sb.toString());
    }

}
