package com.scotiabank.maestro.exec.services.appconfig;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.scotiabank.maestro.exec.services.constants.IBeanIdConstants;

@Configuration
@Import({
	PropertyExecServicesAppConfig.class,	
	DataSourcesAppConfig.class,
	BeanAppConfig.class
})
public class ExecServicesAppConfig implements IBeanIdConstants {		
	
	@Autowired
	@Qualifier(BEAN_ID_DATA_SOURCE_EXEC)
	private DataSource execServicesDataSource;
	
//	@Autowired
//	private TransactionInterceptor transactionInterceptor;
	
	@Bean(name="txManager")
	public DataSourceTransactionManager getDataSourceTransactionManager() {
		DataSourceTransactionManager result = new DataSourceTransactionManager();
		result.setDataSource(execServicesDataSource);
//		transactionInterceptor.setTransactionManagerBeanName("txManager");
		return result;
	}

}
