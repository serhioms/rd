package com.scotiabank.maestro.utils.appconfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import ca.mss.rd.util.Logger;

public class MaskingLoggingPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	
	private List<String> propertiesToMask;
	
	public MaskingLoggingPropertyPlaceholderConfigurer() {
		propertiesToMask = new ArrayList<String>();
	}

	//@Override
	public void afterPropertiesSet() throws IOException {
		Properties mergedProperties = mergeProperties();
		Set<Object> propertyKeySet = mergedProperties.keySet();
		for (Object propertyKey : propertyKeySet) {
			@SuppressWarnings("deprecation")
			String parsedValue = parseStringValue(DEFAULT_PLACEHOLDER_PREFIX + propertyKey + DEFAULT_PLACEHOLDER_SUFFIX, mergedProperties, new HashSet<Object>());
			if( Logger.ERROR.isOn ) Logger.ERROR.printf(String.format("Property %s=%s", propertyKey, maskValueIfNecessary(propertyKey.toString(), parsedValue)));
		}
	}

	
	private String maskValueIfNecessary(String propertyKey, String parsedValue) {
		if (propertiesToMask.contains(propertyKey)) {
			return "********";
		}
		
		return parsedValue;
	}

	public List<String> getPropertiesToMask() {
		return propertiesToMask;
	}

	public void setPropertiesToMask(List<String> propertiesToMask) {
		this.propertiesToMask = propertiesToMask;
	}		
}
