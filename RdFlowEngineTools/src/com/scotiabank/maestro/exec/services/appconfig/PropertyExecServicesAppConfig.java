package com.scotiabank.maestro.exec.services.appconfig;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import ca.mss.rd.util.Logger;

import com.scotiabank.maestro.exec.services.constants.IPropertiesConstants;
import com.scotiabank.maestro.utils.appconfig.EncryptedPropertyHelper;
import com.scotiabank.maestro.utils.appconfig.MaskingLoggingPropertyPlaceholderConfigurer;
import com.scotiabank.maestro.utils.encryption.EncryptionUtil;
import com.scotiabank.maestro.utils.encryption.IEncryptionUtil;


@Configuration
public class PropertyExecServicesAppConfig implements IPropertiesConstants {

	private static final String[] BSP_PROPERTIES_ENCRYPTED_PROPERTY_KEYS = new String[] {
		"db.password",
		"exec.sql.loader.password",
		"jms.java.naming.security.credential",
		"activiti.rest.password"
	};
	
	public PropertyExecServicesAppConfig() {		
	}
	
	
	/**
	 * According to Spring, this method must be static or else you will get inconsistent startup errors.
	 * @return
	 */
	@Bean
	public static PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer() {
		MaskingLoggingPropertyPlaceholderConfigurer loggingPropertyPlaceholderConfigurer = new MaskingLoggingPropertyPlaceholderConfigurer();
		
		Properties bspPropertiesLocation = getBspProperties();
		loggingPropertyPlaceholderConfigurer.setPropertiesToMask(Arrays.asList(BSP_PROPERTIES_ENCRYPTED_PROPERTY_KEYS));
		loggingPropertyPlaceholderConfigurer.setProperties(bspPropertiesLocation);
		loggingPropertyPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(false);
		loggingPropertyPlaceholderConfigurer.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);						
		return loggingPropertyPlaceholderConfigurer;
	}
	
	public static Properties getBspProperties() {
		String bspAbsolutePathLocation = System.getProperty(KEY_BSP_PROPERTY_FILE);
		if(StringUtils.isBlank(bspAbsolutePathLocation)) {
			throw new IllegalStateException(String.format("Unable to find configuration file according to key[%s]", KEY_BSP_PROPERTY_FILE));
		}
		assert( Logger.PROP.isOn? Logger.PROP.printf("Loading property file: " + bspAbsolutePathLocation): true);
		FileSystemResource fileSystemResource = new FileSystemResource(bspAbsolutePathLocation);
		Properties properties;
		try {
			properties = PropertiesLoaderUtils.loadProperties(fileSystemResource);		
		} catch (IOException e) {
			throw new IllegalStateException("Unable to load properties.", e);
		}
		
		EncryptedPropertyHelper encryptedPropertyHelper = getEncryptedPropertyHelper(Arrays.asList(BSP_PROPERTIES_ENCRYPTED_PROPERTY_KEYS));
		// encryptedPropertyHelper.decryptProperties(properties);
		
		setupDefaultProperties(properties);
		return properties;
	}

	/**
	 * Some properties are setup on startup but not via properties files.  These are allowable for defaults.
	 * @param properties
	 */
	private static void setupDefaultProperties(Properties properties) {
		String propertyStart = System.getProperty(KEY_BSP_START_TIMESTAMP_IN_MILLISECONDS);
		if(StringUtils.isBlank(propertyStart)) {
			Date bspStartTimestamp = new Date();
			properties.setProperty(KEY_BSP_START_TIMESTAMP_IN_MILLISECONDS, String.valueOf(bspStartTimestamp.getTime()));			
		}
		
		String propertyBspName = System.getProperty(KEY_BSP_NAME);
		if(StringUtils.isBlank(propertyBspName)) {			
			properties.setProperty(KEY_BSP_NAME, "NO-BSP-NAME");			
		}
	}	
	
	private static EncryptedPropertyHelper getEncryptedPropertyHelper(List<String> encryptedProperties) {
		EncryptedPropertyHelper helper = new EncryptedPropertyHelper();
		helper.setEncryptedProperties(encryptedProperties);
		helper.setEncryptionUtil(getEncryptionUtil());
		return helper;
	}

	private static IEncryptionUtil getEncryptionUtil() {
		return new EncryptionUtil();
	}
}
