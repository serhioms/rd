package com.scotiabank.maestro.orch.services.workflow.property;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.stereotype.Service;

@Service("Bean")
public class StageOutputPropertyScopeHelper {

	public static final String GLOBAL_SCOPE_SYMBOL = "#";

	public String resolveStagePropertyKey(String runStageXmlId, String propertyKey) {
		if (isStagePropertyGlobalScope(propertyKey)) {
			return propertyKey;
		}
		return String.format("%s.%s", runStageXmlId, propertyKey);
	}

	public boolean isStagePropertyGlobalScope(String propertyKey) {
		return StringUtils.isNotEmpty(propertyKey) && propertyKey.startsWith(GLOBAL_SCOPE_SYMBOL);
	}

}
