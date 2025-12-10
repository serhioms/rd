package com.scotiabank.maestro.utils.encryption;

import com.scm.common.services.AdminService;

public class EncryptionUtil implements IEncryptionUtil {
	
	@Override
	public String decrypt(String file, String key) {
		String result = "";

		System.setProperty("SCM_CONFIG_FILE", file);
		result = AdminService.getInstance().getProperty(key);
		
		return result;
	}
}
