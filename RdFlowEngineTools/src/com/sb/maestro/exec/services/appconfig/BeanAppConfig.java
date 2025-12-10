package com.scotiabank.maestro.exec.services.appconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ca.mss.rd.flow.tools.exec.RdDbFunction;
import ca.mss.rd.flow.tools.exec.RdDbProcedure;

import com.scotiabank.maestro.exec.services.constants.IBeanIdConstants;
import com.scotiabank.maestro.exec.services.constants.IPropertiesConstants;
import com.scotiabank.maestro.exec.services.db.DbService;
import com.scotiabank.maestro.msg.workflow.condition.ConditionEvaluator;
import com.scotiabank.maestro.msg.workflow.condition.IConditionEvaluator;
import com.scotiabank.maestro.orch.services.workflow.definition.validation.WorkflowConditionValidator;
import com.scotiabank.maestro.orch.services.workflow.definition.validation.WorkflowPropertyValidator;
import com.scotiabank.maestro.orch.services.workflow.property.StageOutputPropertyScopeHelper;


@Configuration
public class BeanAppConfig implements IBeanIdConstants, IPropertiesConstants {

	@Bean
	@Scope("singleton")
	public StageOutputPropertyScopeHelper getStageOutputPropertyScopeHelper() {
		return new StageOutputPropertyScopeHelper();
	}

	@Bean
	@Scope("singleton")
	public DbService getDbService() {
		return new DbService();
	}

	@Bean
	@Scope("prototype")
	public IConditionEvaluator getConditionEvaluator() {
		return new ConditionEvaluator();
	}

	@Bean
	@Scope("singleton")
	public WorkflowConditionValidator getWorkflowConditionValidator() {
		return new WorkflowConditionValidator();
	}

	@Bean
	@Scope("singleton")
	public WorkflowPropertyValidator WorkflowPropertyValidator() {
		return new WorkflowPropertyValidator();
	}

	@Bean
	@Scope("prototype")
	public RdDbFunction getRdDbFunction() {
		return new RdDbFunction();
	}

	@Bean
	@Scope("prototype")
	public RdDbProcedure getRdDbProcedure() {
		return new RdDbProcedure();
	}

	@Value("${" + IPropertiesConstants.KEY_DATA_FORMAT_DATE + "}")
	private String dateFormat;
	
	@Value("${" + IPropertiesConstants.KEY_DATA_FORMAT_TIMESTAMP + "}")
	private String timestampFormat;

	@Value("${" + IPropertiesConstants.KEY_USER_DIR_IS_ROOT + "}")
	private Boolean userDirIsRoot;
	
	@Value("${" + IPropertiesConstants.KEY_ACTIVITI_EXPLORER_URL + "}")
	private String activitiExplorerUrl;
	
	@Value("${" + IPropertiesConstants.KEY_ACTIVITI_REST_PROTOCOL + "}://${" + IPropertiesConstants.KEY_ACTIVITI_REST_HOST + "}:${" + IPropertiesConstants.KEY_ACTIVITI_REST_PORT + "}")
	private String activitiRootRestUrl;
	
	/*
	 * mss: Sort converters in an order which guarantee proper de-serialization of any ACTIVITI response.
	 * 
	 * In case of wrong order of http message converters following exception would happen:
	 * 
	 * org.springframework.http.converter.HttpMessageNotReadableException: Could not read JSON: Unexpected character ('<' (code 60)): 
	 * expected a valid value (number, String, array, object, 'true', 'false' or 'null')
	 * *** 
	 */
//	private List<HttpMessageConverter<?>> sortSupportedMediatypes(List<HttpMessageConverter<?>> messageConverters){
//		
//		List<HttpMessageConverter<?>> sorted = new ArrayList<HttpMessageConverter<?>>(3);
//		
//		// First
//		for (HttpMessageConverter<?> converter: messageConverters ){
//			if( "[application/json]".equals(converter.getSupportedMediaTypes().toString())){
//				sorted.add(converter);
//			}
//		}
//
//		// Second
//		for (HttpMessageConverter<?> converter: messageConverters ){
//			if( "[text/plain;charset=ISO-8859-1, */*]".equals(converter.getSupportedMediaTypes().toString())){
//				sorted.add(converter);
//			}
//		}
//
//		// Third
//		for (HttpMessageConverter<?> converter: messageConverters ){
//			if( "[application/json;charset=UTF-8, application/*+json;charset=UTF-8]".equals(converter.getSupportedMediaTypes().toString())){
//				sorted.add(converter);
//			} 
//		}
//		
//		// All other converters added to the end of sorted list
//		for (HttpMessageConverter<?> converter: messageConverters ){
//			if( !sorted.contains(converter) ){
//				sorted.add(converter);
//			}
//		}
//		
//		
//		return sorted;
//	}

	
	@Value("${" + IPropertiesConstants.KEY_ACTIVITI_REST_HOST + "}")
	private String activitiHost;
	@Value("${" + IPropertiesConstants.KEY_ACTIVITI_REST_PORT + "}")
	private Integer activitiPort;
	@Value("${" + IPropertiesConstants.KEY_ACTIVITI_REST_USER + "}")
	private String activitiUser;
	@Value("${" + IPropertiesConstants.KEY_ACTIVITI_REST_PASSWORD + "}")
	private String activitiPassword;
	
}
