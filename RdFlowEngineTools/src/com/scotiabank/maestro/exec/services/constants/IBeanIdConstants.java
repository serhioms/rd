package com.scotiabank.maestro.exec.services.constants;

/**
 * Constants for bean ids for configuration setup.
 */
public interface IBeanIdConstants {

	public static final String BEAN_ID_JNDI_TEMPLATE_JMS = "JndiTemplateJMS";
	
	/*
	 * Data sources and other JDBC stuff.
	 */
	public static final String BEAN_ID_DATA_SOURCE_EXEC = "DataSourceExec";
	public static final String BEAN_ID_JDBC_TEMPLATE_EXEC = "JdbcTemplateExec";
	
	/*
	 * IStageTaskExecutors
	 */
	public static final String BEAN_ID_DB_FUNCTION_EXECUTOR = "DbFunctionExeuctor";
	public static final String BEAN_ID_DB_PROCEDURE_EXECUTOR = "DbProcedureExeuctor";
	public static final String BEAN_ID_CALL_EXTERNAL_PROCESS_EXECUTOR = "CallExternalProcessExecutor";
	public static final String BEAN_ID_CALL_ACTIVITI_EXECUTOR = "CallActivitiExecutor";
	public static final String BEAN_ID_SQL_LOADER_EXECUTOR = "SqlLoaderExecutor";
	public static final String BEAN_ID_NO_OP_EXECUTOR="NoOpExecutor";
	public static final String BEAN_ID_POLLING_FILE_WATCHER_EXECUTOR = "PollingFileWatcherExecutor";
	
	/*
	 * Checksum Handlers
	 */
	public static final String BEAN_ID_MD5_CHECKSUM_HANDLER = "MD5ChecksumHandler";
	public static final String BEAN_ID_SHA1_CHECKSUM_HANDLER = "SHA1ChecksumHandler";
	public static final String BEAN_ID_SHA256_CHECKSUM_HANDLER = "SHA256ChecksumHandler";
	
}
