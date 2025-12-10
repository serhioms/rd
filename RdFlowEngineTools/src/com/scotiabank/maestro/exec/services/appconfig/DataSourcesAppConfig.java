package com.scotiabank.maestro.exec.services.appconfig;

import java.util.Properties;

import javax.naming.Context;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jndi.JndiTemplate;

import com.scotiabank.maestro.exec.services.constants.IBeanIdConstants;
import com.scotiabank.maestro.exec.services.constants.IPropertiesConstants;

@Configuration
public class DataSourcesAppConfig implements IBeanIdConstants, IPropertiesConstants {
	
	private @Value("${"+ KEY_DB_JDBC_DRIVER_CLASS + "}") String jdbcDriverClass;
	private @Value("${"+ KEY_DB_JDBC_URL + "}") String jdbcUrl;
	private @Value("${"+ KEY_DB_USERNAME + "}") String username;
	private @Value("${"+ KEY_DB_PASSWORD + "}") String password;
	
	private @Value("${"+ KEY_JMS_JAVA_NAMING_FACTORY_INITIAL + "}") String jmsJavaNamingFactoryInitial;
	private @Value("${" + KEY_JMS_JAVA_NAMING_PROVIDER_URL + "}") String jmsJavaNamingProviderUrl;
	private @Value("${" + KEY_JMS_JAVA_NAMING_SECURITY_PRINCIPAL + "}") String jmsJavaNamingSecurityPrincipal;
	private @Value("${" + KEY_JMS_JAVA_NAMING_SECURITY_CREDENTIAL + "}") String jmsJavaNamingSecurityCredential;
	
	private @Value("${" + KEY_DB_CONNECTION_INITIAL_SIZE +"}") Integer dataSourceConnectionsInitialSize;
	private @Value("${" + KEY_DB_CONNECTION_MAX_ACTIVE +"}") Integer dataSourceConnectionsMaxActive;
	private @Value("${" + KEY_DB_CONNECTION_MAX_WAIT +"}") Long dataSourceConnectionMaxWait;

	private static final int DEFAULT_DB_CONNECTION_INITIAL_SIZE = 1;
	private static final int DEFAULT_DB_CONNECTION_MAX_ACTIVE = 10;
	private static final long DEFAULT_DB_CONNECTION_MAX_WAIT = 60000L;
	
	@Bean(name=BEAN_ID_JNDI_TEMPLATE_JMS)
	public JndiTemplate getJndiTemplateJms() {
		JndiTemplate jndiTemplate = new JndiTemplate();
		Properties environment = new Properties(); 
		
		environment.setProperty(Context.INITIAL_CONTEXT_FACTORY, jmsJavaNamingFactoryInitial);
		environment.setProperty(Context.PROVIDER_URL, jmsJavaNamingProviderUrl);
		environment.setProperty(Context.SECURITY_PRINCIPAL, jmsJavaNamingSecurityPrincipal);
		environment.setProperty(Context.SECURITY_CREDENTIALS, jmsJavaNamingSecurityCredential);
		
		jndiTemplate.setEnvironment(environment);
		
		return jndiTemplate;
	}
	
	@Bean(name=BEAN_ID_DATA_SOURCE_EXEC)
	public DataSource getDataSourceExec(){
		BasicDataSource dataSource = new BasicDataSource();
		
		dataSource.setDriverClassName(jdbcDriverClass);
		dataSource.setUrl(jdbcUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setInitialSize(dataSourceConnectionsInitialSize == null ? DEFAULT_DB_CONNECTION_INITIAL_SIZE : dataSourceConnectionsInitialSize);
		dataSource.setMaxActive(dataSourceConnectionsMaxActive == null ? DEFAULT_DB_CONNECTION_MAX_ACTIVE : dataSourceConnectionsMaxActive);
		dataSource.setMaxWait(dataSourceConnectionMaxWait == null ? DEFAULT_DB_CONNECTION_MAX_WAIT : dataSourceConnectionMaxWait);
		
		return dataSource;
	}
	
	@Bean(name=BEAN_ID_JDBC_TEMPLATE_EXEC)	
	public JdbcTemplate getJdbcTemplateExec(){
		return new JdbcTemplate(getDataSourceExec());
	}
}
