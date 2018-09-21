package com;

import com.pageBuild.EntityAddPage;
import com.pageBuild.EntityIndexPage;
import com.pageBuild.EntityListPage;
import com.pageBuild.EntityUpdatePage;
import com.sun.rowset.CachedRowSetImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 'ms.x' on 2017/8/9.
 */
@Component
public class GeneralMain {
	@Autowired
	private DBInit dbInit;
	@Autowired
	private Properties properties;
	@Autowired
	private GenerateEntity generateEntity;
	@Autowired
	private GenerateMybatisRepository generateMybatisRepository;
	@Autowired
	private GenerateMybatisXml generateMybatisXml;
	@Autowired
	private GenerateService generateService;
	@Autowired
	private GenerateServiceImpl generateServiceImpl;
	@Autowired
	private GenerateController generateController;
	@Autowired
	private GenerateRestController generateRestController;
	@Autowired
	private EntityListPage entityListPage;
	@Autowired
	private EntityIndexPage entityIndexPage;
	@Autowired
	private EntityAddPage entityAddPage;
	@Autowired
	private EntityUpdatePage entityUpdatePage;
	@Autowired
	private GenerateUtil generateUtil;

	
	public void general(String tableName) throws Exception {

		ResultSet primaryKeyResultSet = dbInit.dbMetaData.getPrimaryKeys(null,null,tableName);
		ResultSet resultSetColumn = dbInit.dbMetaData.getColumns(null, null,
				tableName, null);
		StringBuffer sb = new StringBuffer("Select table_name,")
				.append("TABLE_COMMENT ").append("from INFORMATION_SCHEMA.TABLES ")
				.append("Where table_schema = '").append(properties.getDatabase()).append("'")
				.append(" AND table_name LIKE '").append(tableName).append("'");
		ResultSet tableResultSet = dbInit.conn.createStatement().executeQuery(sb.toString());
		String primaryKey=null;
		String primaryKeyColumnType=null;
		CachedRowSet resultSet = new CachedRowSetImpl();
		resultSet.populate(resultSetColumn);
		
		CachedRowSet resultSet1= resultSet.createCopy();
		String tableComment = null;
		while (tableResultSet.next()) {
			tableComment=tableResultSet.getString("TABLE_COMMENT");
		}

		while (primaryKeyResultSet.next()){
			//String dsfe = primaryKeyResultSet.getString("REMARKS");
			primaryKey=primaryKeyResultSet.getString("COLUMN_NAME");
			while (resultSet1.next()){
				if (primaryKey.equals(resultSet1.getString("COLUMN_NAME"))){
					primaryKeyColumnType=resultSet1.getString("TYPE_NAME");
				}
			}
		}
		if (primaryKey==null){
			throw new Exception("没有主键");
		}
		
		CachedRowSet resultSet2 = resultSet.createCopy();
		
		CachedRowSet primaryKeyCachedRowSet = new CachedRowSetImpl();
		primaryKeyCachedRowSet.populate(primaryKeyResultSet);
		CachedRowSet resultSet3 = resultSet.createCopy();
		CachedRowSet resultSet4 = resultSet.createCopy();
		CachedRowSet resultSet5 = resultSet.createCopy();
		generateUtil.getService();
		if (properties.isXml()){
			generateMybatisXml.getService(tableName,resultSet,primaryKey,primaryKeyColumnType);
		}if (properties.isMapper()){
			generateMybatisRepository.getService(tableName,primaryKey,primaryKeyColumnType);
		}if (properties.isModel()){
			generateEntity.getService(tableName,tableComment,resultSet2);
		}if (properties.isService()){
			generateService.getService(tableName,primaryKey,primaryKeyColumnType);
		}if (properties.isServiceImpl()){
			generateServiceImpl.getService(tableName,primaryKey,primaryKeyColumnType);
		}if (properties.isController()){
			generateController.getService(tableName,tableComment,primaryKey,primaryKeyColumnType);
		}if (properties.isRestController()){
			generateRestController.getService(tableName,primaryKey,primaryKeyColumnType);
		}if (properties.isPageList()){
			entityListPage.getService(tableName,resultSet2,primaryKey);
		}if (properties.isPageIndex()){
			entityIndexPage.getService(tableName,resultSet3,primaryKey);
		}if (properties.isPageAdd()){
			entityAddPage.getService(tableName,resultSet4,primaryKey);
		}
		if (properties.isPageUpdate()){
			entityUpdatePage.getService(tableName,resultSet5,primaryKey);
		}
	}
	
	
	public void start() throws Exception {
		List<String> list = getAllTableList();
		for (String str: list){
			general(str);
		}
		
	}
	
	
	
	/**
	 * @Description: 获取所有的表
	 * @author: ppt
	 * @date: 2015-3-16 上午10:12:57
	 * @return: void
	 */
	public List<String> getAllTableList() {
		List<String> tableList = new ArrayList<String>();
		try {
			String[] types = { "TABLE" };
			ResultSet rs = dbInit.dbMetaData.getTables(null, null, "%", types);
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");  //表名
				tableList.add(tableName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tableList;
	}
	
}
