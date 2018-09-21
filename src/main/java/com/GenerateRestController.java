package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenerateRestController {

    @Autowired
    private Properties properties;


    public void getService(String tableName, String primaryKey, String primaryKeyColumnType) {


        String entity = Helper.getFormatString(tableName, true);
        String entityObj = Helper.getFormatString(tableName, false);
        String primaryKeyMethod = Helper.getFormatString(primaryKey, true);
        String primaryKeyParam = Helper.getFormatString(primaryKey, false);
        String columnType = Helper.getColumnType(primaryKeyColumnType);


        String service = entity + "Service";
        String className = entity + "RestController";
        StringBuffer sb = new StringBuffer();
        sb.append("package " + properties.getControllerRestPackageName() + ";\n\n");
        sb.append("import " + properties.getModelPackageName() + "." + entity + ";\n");
        sb.append("import " + properties.getServicePackageName() + "." + service + ";\n");
        sb.append("import " + properties.getPackageName() + ".base.BaseFilter;\n");
        sb.append("import org.slf4j.Logger;\n");
        sb.append("import java.util.HashMap;\n");
        sb.append("import org.slf4j.LoggerFactory;\n");
        sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");
        sb.append("import org.springframework.web.bind.annotation.RestController;\n");
        sb.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
        sb.append("import org.springframework.web.bind.annotation.RequestBody;\n");
        sb.append("import com.github.pagehelper.Page;\n");
        sb.append("import com.github.pagehelper.Paginator;\n");


        sb.append("import java.util.Map;\n");
        sb.append("import " + properties.getPackageName() + ".util.PageUtil;\n");
        sb.append("import javax.servlet.http.HttpServletRequest;\n");
        sb.append("@RestController\n");
        sb.append("@SuppressWarnings(\"all\")\n");
        sb.append("@RequestMapping(\"/rest/" + entityObj + "/\")\n");
        sb.append("public class " + className + " extends BaseFilter  {\n\n");

        sb.append("\tprivate Logger log = LoggerFactory.getLogger(" + className + ".class);\n");
        String instance = service.substring(0, 1).toLowerCase() + service.substring(1) + "Impl";
        sb.append("\t@Autowired\n");
        sb.append("\tprivate " + service + " " + instance + ";\n\n");

        if (properties.isInsertSelective()) {
            sb.append("\t/**\n");
            sb.append("\t * ").append("新增").append("\n");
            sb.append("\t **/\n");
            sb.append("\t@RequestMapping(\"add\")\n");
            sb.append("\tpublic Object " + "add" + entity + "(@RequestBody " + entity + " " + "obj) throws Exception {\n");
            sb.append("\t\t Map<String,Object> map = new HashMap<>();\n");
            sb.append("\t\ttry {\n");
            sb.append("\t\t\tlog.info(\"add" + entity + " {}\",").append("obj").append(");\n");
            sb.append("\t\t\t" + instance + ".insert" + entity + "(obj);\n");
            sb.append("\t\t\tmap.put(\"state\",0);\n");
            sb.append("\t\t\tmap.put(\"msg\",\"success\");\n");
            sb.append("\t\t} catch(Exception e) {\n");
            sb.append("\t\t\tlog.error(\"add" + entity + "异常 {}\",").append("obj").append(",e").append(");\n");
            sb.append("\t\t\tmap.put(\"state\",-1);\n");
            sb.append("\t\t\tmap.put(\"msg\",\"fail\");\n");
            sb.append("\t\t}\n");
            sb.append("\t\treturn map;\n");
            sb.append("\t}\n\n");
        }

        if (properties.isSelectByPrimaryKey()) {
            sb.append("\t/**\n");
            sb.append("\t * ").append("查询").append("\n");
            sb.append("\t **/\n");
            sb.append("\t@RequestMapping(\"get\")\n");
            sb.append("\tpublic Object get" + entity + "By" + primaryKeyMethod + "(@RequestBody Map param) {\n");
            sb.append("\t\t Map<String,Object> map = new HashMap<>();\n");
            sb.append("\t\ttry {\n");
            sb.append("\t\t\t" + columnType + " " + primaryKeyParam + " =(" + columnType + ")param.get(\"" + primaryKeyParam + "\");\n");
            sb.append("\t\t\tlog.info(\"get" + entity + "By" + primaryKeyMethod + "  {}\",").append(primaryKeyParam).append(");\n");
            sb.append("\t\t\t" + entity + " " + entityObj + " = " + instance + ".select" + entity + "By" + primaryKeyMethod + "(" + primaryKeyParam + ");\n");
            sb.append("\t\t\tmap.put(\"state\",0);\n");
            sb.append("\t\t\tmap.put(\"msg\",\"success\");\n");
            sb.append("\t\t\tmap.put(\"data\"," + entityObj + ");\n");
            sb.append("\t\t} catch(Exception e) {\n");
            sb.append("\t\t\tlog.error(\"get" + entity + "异常 {}\",").append("map").append(",e").append(");\n");
            sb.append("\t\t\tmap.put(\"state\",-1);\n");
            sb.append("\t\t\tmap.put(\"msg\",\"fail\");\n");
            sb.append("\t\t}\n");
            sb.append("\t\treturn map;\n");
            sb.append("\t}\n\n");
        }

        if (properties.isUpdateByPrimaryKey()) {
            sb.append("\t/**\n");
            sb.append("\t * ").append("更新").append("\n");
            sb.append("\t **/\n");
            sb.append("\t@RequestMapping(\"update\")\n");
            sb.append("\tpublic Object " + "update" + entity + "By" + primaryKeyMethod + "(@RequestBody " + entity + " obj) throws Exception {\n");
            sb.append("\t\t Map<String,Object> map = new HashMap<>();\n");
            sb.append("\t\ttry {\n");
            sb.append("\t\t\tlog.info(\"update" + entity + "By" + primaryKeyMethod + "  {}\",").append("obj").append(");\n");
            sb.append("\t\t\t" + instance + ".update" + entity + "By" + primaryKeyMethod + "(obj);\n");
            sb.append("\t\t\tmap.put(\"state\",0);\n");
            sb.append("\t\t\tmap.put(\"msg\",\"success\");\n");
            sb.append("\t\t} catch(Exception e) {\n");
            sb.append("\t\t\tlog.error(\"update" + entity + "By" + primaryKeyMethod + "  {}\",").append("obj").append(",e").append(");\n");
            sb.append("\t\t\tmap.put(\"state\",-1);\n");
            sb.append("\t\t\tmap.put(\"msg\",\"fail\");\n");
            sb.append("\t\t}\n");
            sb.append("\t\treturn map;\n");
            sb.append("\t}\n\n");
        }

        if (properties.isPage()) {
            sb.append("\t/**\n");
            sb.append("\t * ").append("分页，这里建议使用插件（com.github.pagehelper.PageHelper）").append("\n");
            sb.append("\t **/\n");
            sb.append("\t@RequestMapping(\"list\")\n");
            sb.append("\tpublic Object find" + entity + "List(@RequestBody " + entity + "  obj, HttpServletRequest request) {\n");
            sb.append("\t\t Map<String,Object> map = new HashMap<>();\n");
            sb.append("\t\ttry {\n");
            sb.append("\t\t\tlog.info(\"find" + entity + "List  {}\",").append("obj").append(");\n");
            sb.append("\t\t\tPageUtil.doPage(request);\n");
            sb.append("\t\t\tPage<" + entity + "> list = (Page<" + entity + ">)" + instance + ".find" + entity + "List(obj);\n");


            sb.append("\t\t\tPaginator paginator=list.getPaginator();\n");
            sb.append("\t\t\tmap.put(\"state\",0);\n");
            sb.append("\t\t\tmap.put(\"msg\",\"success\");\n");
            sb.append("\t\t\tmap.put(\"data\",list);\n");
            sb.append("\t\t\tmap.put(\"paginator\",paginator);\n");

            sb.append("\t\t} catch(Exception e) {\n");
            sb.append("\t\t\tlog.error(\"find" + entity + "List  {}\",").append("obj").append(",e").append(");\n");
            sb.append("\t\t\tmap.put(\"state\",-1);\n");
            sb.append("\t\t\tmap.put(\"msg\",\"fail\");\n");
            sb.append("\t\t}\n");
            sb.append("\t\treturn map;\n");

            sb.append("\t}\n");
        }

        sb.append("}\n");
        Helper.outputToFile(Helper.getFilePath(properties.getPath() + properties.getControllerRestPackagePath(), properties.getControllerRestPackageName()), className + ".java", sb.toString());
    }

}
