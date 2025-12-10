package com.scotiabank.maestro.utils.appconfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.scotiabank.maestro.utils.encryption.IEncryptionUtil;

public class EncryptedPropertyHelper {
	private static final String ENCRYPTED_KEY_SUFFIX = ".key";
	private static final String ENCRYPTED_KEY_FILE_SUFFIX = ".key.file";

	private IEncryptionUtil encryptionUtil;
	private List<String> encryptedProperties;
	
	public EncryptedPropertyHelper() {
		encryptedProperties = new ArrayList<String>();
	}
	
	public void decryptProperties(Properties properties) {
		for (String encryptedProperty : encryptedProperties) {
			decryptProperty(properties, encryptedProperty);
		}
	}

	private void decryptProperty(Properties properties, String encryptedProperty) {
		String encryptedPropertyKey = properties.getProperty(encryptedProperty + ENCRYPTED_KEY_SUFFIX);
		String encryptedPropertyKeyFile = properties.getProperty(encryptedProperty + ENCRYPTED_KEY_FILE_SUFFIX);
		
		if (StringUtils.isBlank(encryptedPropertyKey)) {
			throw new IllegalStateException(String.format("Cannot find the encryption key for property %s", encryptedProperty));
		}
		
		if (StringUtils.isBlank(encryptedPropertyKeyFile)) {
			throw new IllegalStateException(String.format("Cannot find the encryption key file for property %s", encryptedProperty));
		}
		
		String clearValue = decryptProperty(encryptedPropertyKeyFile, encryptedPropertyKey);
		
		if (clearValue == null) {
			throw new IllegalStateException(String.format("Returned null result decrypting property %s", encryptedProperty));
		}
		
		properties.setProperty(encryptedProperty, clearValue);
		
		properties.remove(encryptedProperty + ENCRYPTED_KEY_SUFFIX);
		properties.remove(encryptedProperty + ENCRYPTED_KEY_FILE_SUFFIX);
	}

	private String decryptProperty(String encryptedPropertyKeyFile, String encryptedPropertyKey) {
		return getEncryptionUtil().decrypt(encryptedPropertyKeyFile, encryptedPropertyKey);
	}

	public IEncryptionUtil getEncryptionUtil() {
		return encryptionUtil;
	}

	public void setEncryptionUtil(IEncryptionUtil encryptionUtil) {
		this.encryptionUtil = encryptionUtil;
	}
	
	public List<String> getEncryptedProperties() {
		return encryptedProperties;
	}

	public void setEncryptedProperties(List<String> encryptedProperties) {
		this.encryptedProperties = encryptedProperties;
	}	
}
