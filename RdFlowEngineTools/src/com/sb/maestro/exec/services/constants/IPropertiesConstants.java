package com.scotiabank.maestro.exec.services.constants;

/**
 * Keys for properties that can be overridden.
 *
 */
public interface IPropertiesConstants {
	
	public static final String KEY_DB_JDBC_DRIVER_CLASS="db.jdbc.driver.class";
	public static final String KEY_DB_JDBC_URL="db.jdbc.url";
	public static final String KEY_DB_USERNAME="db.username";
	public static final String KEY_DB_PASSWORD="db.password";
	
	/**
	 * Fully qualified path to the sql loader application.
	 */
	public static final String KEY_EXEC_SQL_LOADER_APP = "exec.sql.loader.app";
	
	/**
	 * SQL loader format for userid in format userid/password@x
	 */
	public static final String KEY_EXEC_SQL_LOADER_USERID = "exec.sql.loader.userid";
	public static final String KEY_EXEC_SQL_LOADER_PASSWORD = "exec.sql.loader.password";
	public static final String KEY_EXEC_SQL_LOADER_DBSID = "exec.sql.loader.dbsid";
	
	public static final String KEY_JMS_JAVA_NAMING_SECURITY_CREDENTIAL = "jms.java.naming.security.credential";
	public static final String KEY_JMS_JAVA_NAMING_SECURITY_PRINCIPAL = "jms.java.naming.security.principal";
	public static final String KEY_JMS_JAVA_NAMING_PROVIDER_URL = "jms.java.naming.provider.url";
	public static final String KEY_JMS_JAVA_NAMING_FACTORY_INITIAL = "jms.java.naming.factory.initial";
	
	/*
	 * Activiti keys
	 */
	public static final String KEY_ACTIVITI_REST_PROTOCOL = "activiti.rest.protocol";
	public static final String KEY_ACTIVITI_REST_HOST = "activiti.rest.host";
	public static final String KEY_ACTIVITI_REST_PORT = "activiti.rest.port";
	public static final String KEY_ACTIVITI_REST_USER = "activiti.rest.user";
	public static final String KEY_ACTIVITI_REST_PASSWORD = "activiti.rest.password";
	public static final String KEY_ACTIVITI_EXPLORER_URL = "activiti.explorer.url";
			
	/*
	 * Database connection keys
	 */
	public static final String KEY_BSP_PROPERTY_FILE="bsp.property.file";
	
	public static final String KEY_SQL_LOADER_BUFFER_SIZE="sqlloader.buffer.size";
	
	/*
	 * Database connection keys
	 */
	public static final String KEY_DB_CONNECTION_INITIAL_SIZE = "db.connection.initialSize";
	public static final String KEY_DB_CONNECTION_MAX_ACTIVE = "db.connection.maxActive";
	public static final String KEY_DB_CONNECTION_MAX_WAIT = "db.connection.maxWait";
	
	public static final String KEY_USER_DIR_IS_ROOT = "user.dir.is.root";

	
	/*
	 * BSP prop
	 */
	public static final String KEY_BSP_NAME = "bsp.name";
	public static final String KEY_BSP_START_TIMESTAMP_IN_MILLISECONDS = "bsp.start.timestamp";

	
	/*
	 * Date Formats
	 */
	public static final String KEY_DATA_FORMAT_TIMESTAMP = "data.format.timestamp";
	public static final String KEY_DATA_FORMAT_DATE = "data.format.date";
	
}
