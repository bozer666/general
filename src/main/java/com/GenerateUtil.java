package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenerateUtil {

    @Autowired
    private Properties properties;


    public void getService() {
        createFilter();
        createStringToNull();
        createPageUtil();
        createPage();
        createPaginator();
    }

    public void createFilter() {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + properties.getPackageName() + ".base" + ";\n\n");
        sb.append("import org.springframework.stereotype.Component;\n");
        sb.append("import org.springframework.web.bind.ServletRequestDataBinder;\n");
        sb.append("import org.springframework.web.bind.annotation.ExceptionHandler;\n");
        sb.append("import org.springframework.web.bind.annotation.InitBinder;\n");

        sb.append("import javax.servlet.http.HttpServletRequest;\n");
        sb.append("import javax.servlet.http.HttpServletResponse;\n");
        sb.append("import java.io.IOException;\n");

        sb.append("@Component\n");
        sb.append("@SuppressWarnings(\"all\")\n");
        sb.append("public abstract class BaseFilter{\n\n");

        sb.append("\t@InitBinder\n");
        sb.append("\tprotected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {\n");
        sb.append("\t\tInitStringToNull stringEditor = new InitStringToNull();\n");
        sb.append("\t\tbinder.registerCustomEditor(String.class, stringEditor);\n");
        sb.append("\t}\n\n");

        sb.append("\t@ExceptionHandler({Exception.class})\n");
        sb.append("\tpublic void handlerException(Exception e, HttpServletResponse response) throws IOException {\n");
        sb.append("\t\te.printStackTrace();\n");
        sb.append("\t\tresponseInfo(response, \"系统繁忙，请重试！...\");\n");
        sb.append("\t}\n");

        sb.append("\tprivate void responseInfo(HttpServletResponse response, String info) throws IOException {\n");
        sb.append("\t\tresponse.setContentType(\"text/html;charset=utf-8\");");
        sb.append("\t\tresponse.setHeader(\"Cache-Control\", \"no-cache\");\n");
        sb.append("\t\tresponse.getWriter().print(info);\n");
        sb.append("\t}\n");

        sb.append("}\n");
        Helper.outputToFile(Helper.getFilePath(properties.getPath() + properties.getPackagePath(), properties.getPackageName() + ".base"), "BaseFilter" + ".java", sb.toString(), false);
    }


    public void createStringToNull() {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + properties.getPackageName() + ".base" + ";\n\n");
        sb.append("import org.springframework.beans.propertyeditors.PropertiesEditor;\n");
        sb.append("public class InitStringToNull extends PropertiesEditor {\n\n");
        sb.append("\t@Override\n");
        sb.append("\tpublic void setAsText(String text) throws IllegalArgumentException {\n");
        sb.append("\t\tif (text == null || text.equals(\"\") || text.equals(\"null\")) {\n");
        sb.append("\t\t\ttext = null;\n");
        sb.append("\t\t}\n");
        sb.append("\t\tsetValue(text);\n");
        sb.append("\t }\n");
        sb.append("\t @Override\n");
        sb.append("\t public String getAsText() {\n");
        sb.append("\t\t return null;\n");
        sb.append("\t }\n");
        sb.append("}\n");
        Helper.outputToFile(Helper.getFilePath(properties.getPath() + properties.getPackagePath(), properties.getPackageName() + ".base"), "InitStringToNull" + ".java", sb.toString(), false);

    }

    public void createPageUtil() {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + properties.getPackageName() + ".util;\n" +
                "\n" +
                "import com.github.pagehelper.PageHelper;\n" +
                "\n" +
                "import javax.servlet.http.HttpServletRequest;\n" +
                "\n" +
                "/**\n" +
                " * Created by 'ms.x' on 2017/7/31.\n" +
                " */\n" +
                "public class PageUtil {\n" +
                "    /**\n" +
                "     * request 包含 pageNo，pageSize\n" +
                "     *\n" +
                "     * @param request\n" +
                "     */\n" +
                "    public static void doPage(HttpServletRequest request) {\n" +
                "        String pageNo = request.getParameter(\"pageNo\");\n" +
                "        String pageSize = request.getParameter(\"pageSize\");\n" +
                "\n" +
                "        if (pageNo == null || pageNo.equals(\"\")) {\n" +
                "            pageNo =\"1\";\n" +
                "        }\n" +
                "        if (pageSize == null || pageSize.equals(\"\")) {\n" +
                "\n" +
                "            pageSize = \"10\";\n" +
                "        }\n" +
                "        PageHelper.startPage(Integer.valueOf(pageNo), Integer.valueOf(pageSize));\n" +
                "    }\n" +
                "}\n" +
                "\n");
        Helper.outputToFile(Helper.getFilePath(properties.getPath() + properties.getPackagePath(), properties.getPackageName() + ".util"), "PageUtil" + ".java", sb.toString(), false);

    }

    public void createPage() {
        StringBuffer sb = new StringBuffer();
        sb.append("package com.github.pagehelper;\n" +
                "\n" +
                "import java.io.Closeable;\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.List;\n" +
                "\n" +
                "/**\n" +
                " * Mybatis - 分页对象\n" +
                " */\n" +
                "public class Page<E> extends ArrayList<E> implements Closeable {\n" +
                "    private static final long serialVersionUID = 1L;\n" +
                "\n" +
                "    private Paginator paginator;\n" +
                "    \n" +
                "    /**\n" +
                "     * 页码，从1开始\n" +
                "     */\n" +
                "    private int pageNum;\n" +
                "    /**\n" +
                "     * 页面大小\n" +
                "     */\n" +
                "    private int pageSize;\n" +
                "    /**\n" +
                "     * 起始行\n" +
                "     */\n" +
                "    private int startRow;\n" +
                "    /**\n" +
                "     * 末行\n" +
                "     */\n" +
                "    private int endRow;\n" +
                "    /**\n" +
                "     * 总数\n" +
                "     */\n" +
                "    private long total;\n" +
                "    /**\n" +
                "     * 总页数\n" +
                "     */\n" +
                "    private int pages;\n" +
                "    /**\n" +
                "     * 包含count查询\n" +
                "     */\n" +
                "    private boolean count = true;\n" +
                "    /**\n" +
                "     * 分页合理化\n" +
                "     */\n" +
                "    private Boolean reasonable;\n" +
                "    /**\n" +
                "     * 当设置为true的时候，如果pagesize设置为0（或RowBounds的limit=0），就不执行分页，返回全部结果\n" +
                "     */\n" +
                "    private Boolean pageSizeZero;\n" +
                "\n" +
                "    /**\n" +
                "     * 进行count查询的列名\n" +
                "     */\n" +
                "    private String countColumn;\n" +
                "    \n" +
                "    public Paginator getPaginator() {\n" +
                "        paginator = new Paginator(this.getPageNum(),\n" +
                "        this.getPageSize(),Long.valueOf(this.getTotal()).intValue());\n" +
                "        return paginator;\n" +
                "    }\n" +
                "    \n" +
                "    public Page() {\n" +
                "        super();\n" +
                "    }\n" +
                "\n" +
                "    public Page(int pageNum, int pageSize) {\n" +
                "        this(pageNum, pageSize, true, null);\n" +
                "    }\n" +
                "\n" +
                "    public Page(int pageNum, int pageSize, boolean count) {\n" +
                "        this(pageNum, pageSize, count, null);\n" +
                "    }\n" +
                "\n" +
                "    private Page(int pageNum, int pageSize, boolean count, Boolean reasonable) {\n" +
                "        super(0);\n" +
                "        if (pageNum == 1 && pageSize == Integer.MAX_VALUE) {\n" +
                "            pageSizeZero = true;\n" +
                "            pageSize = 0;\n" +
                "        }\n" +
                "        this.pageNum = pageNum;\n" +
                "        this.pageSize = pageSize;\n" +
                "        this.count = count;\n" +
                "        calculateStartAndEndRow();\n" +
                "        setReasonable(reasonable);\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * int[] rowBounds\n" +
                "     * 0 : offset\n" +
                "     * 1 : limit\n" +
                "     */\n" +
                "    public Page(int[] rowBounds, boolean count) {\n" +
                "        super(0);\n" +
                "        if (rowBounds[0] == 0 && rowBounds[1] == Integer.MAX_VALUE) {\n" +
                "            pageSizeZero = true;\n" +
                "            this.pageSize = 0;\n" +
                "        } else {\n" +
                "            this.pageSize = rowBounds[1];\n" +
                "            this.pageNum = rowBounds[1] != 0 ? (int) (Math.ceil(((double) rowBounds[0] + rowBounds[1]) / rowBounds[1])) : 0;\n" +
                "        }\n" +
                "        this.startRow = rowBounds[0];\n" +
                "        this.count = count;\n" +
                "        this.endRow = this.startRow + rowBounds[1];\n" +
                "    }\n" +
                "\n" +
                "    public List<E> getResult() {\n" +
                "        return this;\n" +
                "    }\n" +
                "\n" +
                "    public int getPages() {\n" +
                "        return pages;\n" +
                "    }\n" +
                "\n" +
                "    public Page<E> setPages(int pages) {\n" +
                "        this.pages = pages;\n" +
                "        return this;\n" +
                "    }\n" +
                "\n" +
                "    public int getEndRow() {\n" +
                "        return endRow;\n" +
                "    }\n" +
                "\n" +
                "    public Page<E> setEndRow(int endRow) {\n" +
                "        this.endRow = endRow;\n" +
                "        return this;\n" +
                "    }\n" +
                "\n" +
                "    public int getPageNum() {\n" +
                "        return pageNum;\n" +
                "    }\n" +
                "\n" +
                "    public Page<E> setPageNum(int pageNum) {\n" +
                "        //分页合理化，针对不合理的页码自动处理\n" +
                "        this.pageNum = ((reasonable != null && reasonable) && pageNum <= 0) ? 1 : pageNum;\n" +
                "        return this;\n" +
                "    }\n" +
                "\n" +
                "    public int getPageSize() {\n" +
                "        return pageSize;\n" +
                "    }\n" +
                "\n" +
                "    public Page<E> setPageSize(int pageSize) {\n" +
                "        this.pageSize = pageSize;\n" +
                "        return this;\n" +
                "    }\n" +
                "\n" +
                "    public int getStartRow() {\n" +
                "        return startRow;\n" +
                "    }\n" +
                "\n" +
                "    public Page<E> setStartRow(int startRow) {\n" +
                "        this.startRow = startRow;\n" +
                "        return this;\n" +
                "    }\n" +
                "\n" +
                "    public long getTotal() {\n" +
                "        return total;\n" +
                "    }\n" +
                "\n" +
                "    public void setTotal(long total) {\n" +
                "        this.total = total;\n" +
                "        if (total == -1) {\n" +
                "            pages = 1;\n" +
                "            return;\n" +
                "        }\n" +
                "        if (pageSize > 0) {\n" +
                "            pages = (int) (total / pageSize + ((total % pageSize == 0) ? 0 : 1));\n" +
                "        } else {\n" +
                "            pages = 0;\n" +
                "        }\n" +
                "        //分页合理化，针对不合理的页码自动处理\n" +
                "        if ((reasonable != null && reasonable) && pageNum > pages) {\n" +
                "            pageNum = pages;\n" +
                "            calculateStartAndEndRow();\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    public Boolean getReasonable() {\n" +
                "        return reasonable;\n" +
                "    }\n" +
                "\n" +
                "    public Page<E> setReasonable(Boolean reasonable) {\n" +
                "        if (reasonable == null) {\n" +
                "            return this;\n" +
                "        }\n" +
                "        this.reasonable = reasonable;\n" +
                "        //分页合理化，针对不合理的页码自动处理\n" +
                "        if (this.reasonable && this.pageNum <= 0) {\n" +
                "            this.pageNum = 1;\n" +
                "            calculateStartAndEndRow();\n" +
                "        }\n" +
                "        return this;\n" +
                "    }\n" +
                "\n" +
                "    public Boolean getPageSizeZero() {\n" +
                "        return pageSizeZero;\n" +
                "    }\n" +
                "\n" +
                "    public Page<E> setPageSizeZero(Boolean pageSizeZero) {\n" +
                "        if (pageSizeZero != null) {\n" +
                "            this.pageSizeZero = pageSizeZero;\n" +
                "        }\n" +
                "        return this;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 计算起止行号\n" +
                "     */\n" +
                "    private void calculateStartAndEndRow() {\n" +
                "        this.startRow = this.pageNum > 0 ? (this.pageNum - 1) * this.pageSize : 0;\n" +
                "        this.endRow = this.startRow + this.pageSize * (this.pageNum > 0 ? 1 : 0);\n" +
                "    }\n" +
                "\n" +
                "    public boolean isCount() {\n" +
                "        return this.count;\n" +
                "    }\n" +
                "\n" +
                "    public Page<E> setCount(boolean count) {\n" +
                "        this.count = count;\n" +
                "        return this;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 设置页码\n" +
                "     *\n" +
                "     * @param pageNum\n" +
                "     * @return\n" +
                "     */\n" +
                "    public Page<E> pageNum(int pageNum) {\n" +
                "        //分页合理化，针对不合理的页码自动处理\n" +
                "        this.pageNum = ((reasonable != null && reasonable) && pageNum <= 0) ? 1 : pageNum;\n" +
                "        return this;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 设置页面大小\n" +
                "     *\n" +
                "     * @param pageSize\n" +
                "     * @return\n" +
                "     */\n" +
                "    public Page<E> pageSize(int pageSize) {\n" +
                "        this.pageSize = pageSize;\n" +
                "        calculateStartAndEndRow();\n" +
                "        return this;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 是否执行count查询\n" +
                "     *\n" +
                "     * @param count\n" +
                "     * @return\n" +
                "     */\n" +
                "    public Page<E> count(Boolean count) {\n" +
                "        this.count = count;\n" +
                "        return this;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 设置合理化\n" +
                "     *\n" +
                "     * @param reasonable\n" +
                "     * @return\n" +
                "     */\n" +
                "    public Page<E> reasonable(Boolean reasonable) {\n" +
                "        setReasonable(reasonable);\n" +
                "        return this;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 当设置为true的时候，如果pagesize设置为0（或RowBounds的limit=0），就不执行分页，返回全部结果\n" +
                "     *\n" +
                "     * @param pageSizeZero\n" +
                "     * @return\n" +
                "     */\n" +
                "    public Page<E> pageSizeZero(Boolean pageSizeZero) {\n" +
                "        setPageSizeZero(pageSizeZero);\n" +
                "        return this;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 指定 count 查询列\n" +
                "     *\n" +
                "     * @param columnName\n" +
                "     * @return\n" +
                "     */\n" +
                "    public Page<E> countColumn(String columnName) {\n" +
                "        this.countColumn = columnName;\n" +
                "        return this;\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    /**\n" +
                "     * 转换为PageInfo\n" +
                "     *\n" +
                "     * @return\n" +
                "     */\n" +
                "    public PageInfo<E> toPageInfo() {\n" +
                "        PageInfo<E> pageInfo = new PageInfo<E>(this);\n" +
                "        return pageInfo;\n" +
                "    }\n" +
                "\n" +
                "    public <E> Page<E> doSelectPage(ISelect select) {\n" +
                "        select.doSelect();\n" +
                "        return (Page<E>) this;\n" +
                "    }\n" +
                "\n" +
                "    public <E> PageInfo<E> doSelectPageInfo(ISelect select) {\n" +
                "        select.doSelect();\n" +
                "        return (PageInfo<E>) this.toPageInfo();\n" +
                "    }\n" +
                "\n" +
                "    public long doCount(ISelect select) {\n" +
                "        this.pageSizeZero = true;\n" +
                "        this.pageSize = 0;\n" +
                "        select.doSelect();\n" +
                "        return this.total;\n" +
                "    }\n" +
                "\n" +
                "    public String getCountColumn() {\n" +
                "        return countColumn;\n" +
                "    }\n" +
                "\n" +
                "    public void setCountColumn(String countColumn) {\n" +
                "        this.countColumn = countColumn;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public String toString() {\n" +
                "        return \"Page{\" +\n" +
                "                \"count=\" + count +\n" +
                "                \", pageNum=\" + pageNum +\n" +
                "                \", pageSize=\" + pageSize +\n" +
                "                \", startRow=\" + startRow +\n" +
                "                \", endRow=\" + endRow +\n" +
                "                \", total=\" + total +\n" +
                "                \", pages=\" + pages +\n" +
                "                \", reasonable=\" + reasonable +\n" +
                "                \", pageSizeZero=\" + pageSizeZero +\n" +
                "                '}';\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void close() {\n" +
                "        PageHelper.clearPage();\n" +
                "    }\n" +
                "}\n" +
                "\n");
        Helper.outputToFile(Helper.getFilePath(properties.getPath() + properties.getPackagePath(), "com.github.pagehelper"), "Page" + ".java", sb.toString(), false);

    }
    public void createPaginator() {
        StringBuffer sb = new StringBuffer();
        sb.append("//\n" +
                "// Source code recreated from a .class file by IntelliJ IDEA\n" +
                "// (powered by Fernflower decompiler)\n" +
                "//\n" +
                "\n" +
                "package com.github.pagehelper;\n" +
                "\n" +
                "import java.io.Serializable;\n" +
                "import java.util.ArrayList;\n" +
                "\n" +
                "public class Paginator implements Serializable {\n" +
                "    private static final long serialVersionUID = 6089482156906595931L;\n" +
                "    private static final int DEFAULT_SLIDERS_COUNT = 7;\n" +
                "    private int limit;\n" +
                "    private int page;\n" +
                "    private int totalCount;\n" +
                "    public Paginator() {\n" +
                "\n" +
                "    }\n" +
                "    public Paginator(int page, int limit, int totalCount) {\n" +
                "        this.limit = limit;\n" +
                "        this.totalCount = totalCount;\n" +
                "        this.page = this.computePageNo(page);\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    public void setLimit(int limit) {\n" +
                "        this.limit = limit;\n" +
                "    }\n" +
                "\n" +
                "    public void setPage(int page) {\n" +
                "        this.page = page;\n" +
                "    }\n" +
                "\n" +
                "    public void setTotalCount(int totalCount) {\n" +
                "        this.totalCount = totalCount;\n" +
                "    }\n" +
                "\n" +
                "    public int getPage() {\n" +
                "        return this.page;\n" +
                "    }\n" +
                "\n" +
                "    public int getLimit() {\n" +
                "        return this.limit;\n" +
                "    }\n" +
                "\n" +
                "    public int getTotalCount() {\n" +
                "        return this.totalCount;\n" +
                "    }\n" +
                "\n" +
                "    public boolean isFirstPage() {\n" +
                "        return this.page <= 1;\n" +
                "    }\n" +
                "\n" +
                "    public boolean isLastPage() {\n" +
                "        return this.page >= this.getTotalPages();\n" +
                "    }\n" +
                "\n" +
                "    public int getPrePage() {\n" +
                "        return this.isHasPrePage()?this.page - 1:this.page;\n" +
                "    }\n" +
                "\n" +
                "    public int getNextPage() {\n" +
                "        return this.isHasNextPage()?this.page + 1:this.page;\n" +
                "    }\n" +
                "\n" +
                "    public boolean isDisabledPage(int page) {\n" +
                "        return page < 1 || page > this.getTotalPages() || page == this.page;\n" +
                "    }\n" +
                "\n" +
                "    public boolean isHasPrePage() {\n" +
                "        return this.page - 1 >= 1;\n" +
                "    }\n" +
                "\n" +
                "    public boolean isHasNextPage() {\n" +
                "        return this.page + 1 <= this.getTotalPages();\n" +
                "    }\n" +
                "\n" +
                "    public int getStartRow() {\n" +
                "        return this.getLimit() > 0 && this.totalCount > 0?(this.page > 0?(this.page - 1) * this.getLimit() + 1:0):0;\n" +
                "    }\n" +
                "\n" +
                "    public int getEndRow() {\n" +
                "        return this.page > 0?Math.min(this.limit * this.page, this.getTotalCount()):0;\n" +
                "    }\n" +
                "\n" +
                "    public int getOffset() {\n" +
                "        return this.page > 0?(this.page - 1) * this.getLimit():0;\n" +
                "    }\n" +
                "\n" +
                "    public int getTotalPages() {\n" +
                "        if(this.totalCount <= 0) {\n" +
                "            return 0;\n" +
                "        } else if(this.limit <= 0) {\n" +
                "            return 0;\n" +
                "        } else {\n" +
                "            int count = this.totalCount / this.limit;\n" +
                "            if(this.totalCount % this.limit > 0) {\n" +
                "                ++count;\n" +
                "            }\n" +
                "\n" +
                "            return count;\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    protected int computePageNo(int page) {\n" +
                "        return computePageNumber(page, this.limit, this.totalCount);\n" +
                "    }\n" +
                "\n" +
                "    public Integer[] getSlider() {\n" +
                "        return this.slider(7);\n" +
                "    }\n" +
                "\n" +
                "    public Integer[] slider(int slidersCount) {\n" +
                "        return generateLinkPageNumbers(this.getPage(), this.getTotalPages(), slidersCount);\n" +
                "    }\n" +
                "\n" +
                "    private static int computeLastPageNumber(int totalItems, int pageSize) {\n" +
                "        if(pageSize <= 0) {\n" +
                "            return 1;\n" +
                "        } else {\n" +
                "            int result = totalItems % pageSize == 0?totalItems / pageSize:totalItems / pageSize + 1;\n" +
                "            if(result <= 1) {\n" +
                "                result = 1;\n" +
                "            }\n" +
                "\n" +
                "            return result;\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    private static int computePageNumber(int page, int pageSize, int totalItems) {\n" +
                "        return page <= 1?1:(2147483647 != page && page <= computeLastPageNumber(totalItems, pageSize)?page:computeLastPageNumber(totalItems, pageSize));\n" +
                "    }\n" +
                "\n" +
                "    private static Integer[] generateLinkPageNumbers(int currentPageNumber, int lastPageNumber, int count) {\n" +
                "        int avg = count / 2;\n" +
                "        int startPageNumber = currentPageNumber - avg;\n" +
                "        if(startPageNumber <= 0) {\n" +
                "            startPageNumber = 1;\n" +
                "        }\n" +
                "\n" +
                "        int endPageNumber = startPageNumber + count - 1;\n" +
                "        if(endPageNumber > lastPageNumber) {\n" +
                "            endPageNumber = lastPageNumber;\n" +
                "        }\n" +
                "\n" +
                "        if(endPageNumber - startPageNumber < count) {\n" +
                "            startPageNumber = endPageNumber - count;\n" +
                "            if(startPageNumber <= 0) {\n" +
                "                startPageNumber = 1;\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        ArrayList result = new ArrayList();\n" +
                "\n" +
                "        for(int i = startPageNumber; i <= endPageNumber; ++i) {\n" +
                "            result.add(Integer.valueOf(i));\n" +
                "        }\n" +
                "\n" +
                "        return (Integer[])result.toArray(new Integer[result.size()]);\n" +
                "    }\n" +
                "\n" +
                "    public String toString() {\n" +
                "        StringBuilder sb = new StringBuilder();\n" +
                "        sb.append(\"Paginator\");\n" +
                "        sb.append(\"{page=\").append(this.page);\n" +
                "        sb.append(\", limit=\").append(this.limit);\n" +
                "        sb.append(\", totalCount=\").append(this.totalCount);\n" +
                "        sb.append('}');\n" +
                "        return sb.toString();\n" +
                "    }\n" +
                "}\n");
        Helper.outputToFile(Helper.getFilePath(properties.getPath() + properties.getPackagePath(), "com.github.pagehelper"), "Paginator" + ".java", sb.toString(), false);

    }

}
