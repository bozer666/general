package com;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.DatabaseMetaData;

/**
 * Created by 'ms.x' on 2017/8/9.
 */
@Component
@SuppressWarnings("all")
public class Properties {
	
	@Value("#{setting.url}")
	private String url;
	@Value("#{setting.username}")
	private String username;
	@Value("#{setting.password}")
	private String password;
	@Value("#{setting.driverClass}")
	private String driverClass;
	@Value("#{setting.database}")
	private String database;

	private DatabaseMetaData dbMetaData=null;
	
	
	@Value("#{setting.path}")
	private String path;
	@Value("#{setting.packagePath}")
	private String packagePath;
	@Value("#{setting.packageName}")
	private String packageName;
	@Value("#{setting.pageRoot}")
	private String pageRoot;
	@Value("#{setting.modelPackageName}")
	private String modelPackageName ;
	@Value("#{setting.mapperPackageName}")
	private String mapperPackageName;
	@Value("#{setting.mapperXmlPackageName}")
	private String mapperXmlPackageName;
	@Value("#{setting.serviceImplPackageName}")
	private String serviceImplPackageName;
	@Value("#{setting.servicePackageName}")
	private String servicePackageName;
	@Value("#{setting.controllerPackageName}")
	private String controllerPackageName;
	@Value("#{setting.pagePackageName}")
	private String pagePackageName;
	@Value("#{setting.modelPackagePath}")
	private String modelPackagePath;
	@Value("#{setting.mapperPackagePath}")
	private String mapperPackagePath;
	@Value("#{setting.mapperXmlPackagePath}")
	private String mapperXmlPackagePath;
	@Value("#{setting.serviceImplPackagePath}")
	private String serviceImplPackagePath;
	@Value("#{setting.servicePackagePath}")
	private String servicePackagePath;
	@Value("#{setting.controllerPackagePath}")
	private String controllerPackagePath;
	@Value("#{setting.controllerRestPackageName}")
	private String controllerRestPackageName;
	@Value("#{setting.controllerRestPackagePath}")
	private String controllerRestPackagePath;

	
	
	
	@Value("#{setting.selectByPrimaryKey}")
	private boolean selectByPrimaryKey;
	@Value("#{setting.updateByPrimaryKey}")
	private boolean updateByPrimaryKey;
	@Value("#{setting.deleteByPrimaryKey}")
	private boolean deleteByPrimaryKey;
	@Value("#{setting.insertSelective}")
	private boolean insertSelective;
	@Value("#{setting.page}")
	private boolean page;
	
	@Value("#{setting.xml}")
	private boolean xml;
	
	@Value("#{setting.mapper}")
	private boolean mapper;
	@Value("#{setting.service}")
	private boolean service;
	@Value("#{setting.serviceImpl}")
	private boolean serviceImpl;
	@Value("#{setting.model}")
	private boolean model;
	@Value("#{setting.controller}")
	private boolean controller;
	@Value("#{setting.restController}")
	private boolean restController;
	@Value("#{setting.pageIndex}")
	private boolean pageIndex;
	@Value("#{setting.pageList}")
	private boolean pageList;
	@Value("#{setting.pageAdd}")
	private boolean pageAdd;
	@Value("#{setting.pageUpdate}")
	private boolean pageUpdate;


	public String getPackagePath() {
		return packagePath;
	}

	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public boolean isRestController() {
		return restController;
	}

	public void setRestController(boolean restController) {
		this.restController = restController;
	}

	public String getControllerRestPackageName() {
		return StringUtils.isEmpty(controllerRestPackageName)?packageName+".controller":controllerRestPackageName;
	}

	public void setControllerRestPackageName(String controllerRestPackageName) {
		this.controllerRestPackageName = controllerRestPackageName;
	}

	public String getControllerRestPackagePath() {
		return StringUtils.isEmpty(controllerRestPackagePath)?packagePath:controllerRestPackagePath;
	}

	public void setControllerRestPackagePath(String controllerRestPackagePath) {
		this.controllerRestPackagePath = controllerRestPackagePath;
	}

	public String getModelPackagePath() {
		return StringUtils.isEmpty(modelPackagePath)?packagePath:modelPackagePath;
	}
	
	public void setModelPackagePath(String modelPackagePath) {
		this.modelPackagePath = modelPackagePath;
	}
	
	public String getMapperPackagePath() {
		return StringUtils.isEmpty(mapperPackagePath)?packagePath:mapperPackagePath;
	}
	
	public void setMapperPackagePath(String mapperPackagePath) {
		this.mapperPackagePath = mapperPackagePath;
	}
	
	public String getMapperXmlPackagePath() {
		return StringUtils.isEmpty(mapperXmlPackagePath)?packagePath:mapperXmlPackagePath;
	}
	
	public void setMapperXmlPackagePath(String mapperXmlPackagePath) {
		this.mapperXmlPackagePath = mapperXmlPackagePath;
	}
	
	public String getServiceImplPackagePath() {
		return StringUtils.isEmpty(serviceImplPackagePath)?packagePath:serviceImplPackagePath;
	}
	
	public void setServiceImplPackagePath(String serviceImplPackagePath) {
		this.serviceImplPackagePath = serviceImplPackagePath;
	}
	
	public String getServicePackagePath() {
		return StringUtils.isEmpty(servicePackagePath)?packagePath:servicePackagePath;
	}
	
	public void setServicePackagePath(String servicePackagePath) {
		this.servicePackagePath = servicePackagePath;
	}
	
	public String getControllerPackagePath() {
		return StringUtils.isEmpty(controllerPackagePath)?packagePath:controllerPackagePath;
	}
	
	public void setControllerPackagePath(String controllerPackagePath) {
		this.controllerPackagePath = controllerPackagePath;
	}
	
	public boolean isModel() {
		return model;
	}
	
	public void setModel(boolean model) {
		this.model = model;
	}
	
	public boolean isXml() {
		return xml;
	}
	
	public void setXml(boolean xml) {
		this.xml = xml;
	}
	
	public boolean isMapper() {
		return mapper;
	}
	
	public void setMapper(boolean mapper) {
		this.mapper = mapper;
	}
	
	public boolean isService() {
		return service;
	}
	
	public void setService(boolean service) {
		this.service = service;
	}
	
	public boolean isServiceImpl() {
		return serviceImpl;
	}
	
	public void setServiceImpl(boolean serviceImpl) {
		this.serviceImpl = serviceImpl;
	}
	
	public boolean isController() {
		return controller;
	}
	
	public void setController(boolean controller) {
		this.controller = controller;
	}
	
	public boolean isPageIndex() {
		return pageIndex;
	}
	
	public void setPageIndex(boolean pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	public boolean isPageList() {
		return pageList;
	}
	
	public void setPageList(boolean pageList) {
		this.pageList = pageList;
	}
	
	public boolean isPageAdd() {
		return pageAdd;
	}
	
	public void setPageAdd(boolean pageAdd) {
		this.pageAdd = pageAdd;
	}
	
	public boolean isPageUpdate() {
		return pageUpdate;
	}
	
	public void setPageUpdate(boolean pageUpdate) {
		this.pageUpdate = pageUpdate;
	}
	
	public String getPageRoot() {
		return pageRoot;
	}
	
	public void setPageRoot(String pageRoot) {
		this.pageRoot = pageRoot;
	}
	
	public String getPagePackageName() {
		return pagePackageName;
	}
	
	public void setPagePackageName(String pagePackageName) {
		this.pagePackageName = pagePackageName;
	}
	
	public String getControllerPackageName() {
		return StringUtils.isEmpty(controllerPackageName)?packageName+".controller":controllerPackageName;
	}
	
	public void setControllerPackageName(String controllerPackageName) {
		this.controllerPackageName = controllerPackageName;
	}
	
	public String getMapperXmlPackageName() {
		return StringUtils.isEmpty(mapperXmlPackageName)?packageName+".dao.xml":mapperXmlPackageName;
	}
	
	public void setMapperXmlPackageName(String mapperXmlPackageName) {
		this.mapperXmlPackageName = mapperXmlPackageName;
	}

	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getModelPackageName() {
		return StringUtils.isEmpty(modelPackageName)?packageName+".model":modelPackageName;
	}
	
	public void setModelPackageName(String modelPackageName) {
		this.modelPackageName = modelPackageName;
	}
	
	public String getMapperPackageName() {
		return StringUtils.isEmpty(mapperPackageName)?packageName+".dao":mapperPackageName;
	}
	
	public void setMapperPackageName(String mapperPackageName) {
		this.mapperPackageName = mapperPackageName;
	}
	
	public String getServiceImplPackageName() {
		return StringUtils.isEmpty(serviceImplPackageName)?packageName+".service.impl":serviceImplPackageName;
	}
	
	public void setServiceImplPackageName(String serviceImplPackageName) {
		this.serviceImplPackageName = serviceImplPackageName;
	}
	
	public String getServicePackageName() {
		return StringUtils.isEmpty(servicePackageName)?packageName+".service":servicePackageName;
	}
	
	public void setServicePackageName(String servicePackageName) {
		this.servicePackageName = servicePackageName;
	}







	public boolean isSelectByPrimaryKey() {
		return selectByPrimaryKey;
	}

	public void setSelectByPrimaryKey(boolean selectByPrimaryKey) {
		this.selectByPrimaryKey = selectByPrimaryKey;
	}

	public boolean isUpdateByPrimaryKey() {
		return updateByPrimaryKey;
	}

	public void setUpdateByPrimaryKey(boolean updateByPrimaryKey) {
		this.updateByPrimaryKey = updateByPrimaryKey;
	}

	public boolean isDeleteByPrimaryKey() {
		return deleteByPrimaryKey;
	}

	public void setDeleteByPrimaryKey(boolean deleteByPrimaryKey) {
		this.deleteByPrimaryKey = deleteByPrimaryKey;
	}

	public boolean isInsertSelective() {
		return insertSelective;
	}

	public void setInsertSelective(boolean insertSelective) {
		this.insertSelective = insertSelective;
	}

	public boolean isPage() {
		return page;
	}

	public void setPage(boolean page) {
		this.page = page;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public DatabaseMetaData getDbMetaData() {
		return dbMetaData;
	}

	public void setDbMetaData(DatabaseMetaData dbMetaData) {
		this.dbMetaData = dbMetaData;
	}
}
