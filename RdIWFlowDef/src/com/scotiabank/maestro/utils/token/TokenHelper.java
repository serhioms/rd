package com.scotiabank.maestro.utils.token;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import ca.mss.rd.flow.prop.RdProp;
import static java.lang.String.format;

public final class TokenHelper {
	public static final String DEFAULT_REPLACE_VALUE = "1";
	
	//A valid token is one that starts with ${ followed by one or more  alphanumeric or _ or - or . characters, which is followed by } brace. 
	private static final String VALID_TOKEN_VARIABLE_REGEX = "\\$\\{[\\#?a-zA-Z0-9_\\-\\.]+}";
	private static final Pattern VALID_TOKEN_VARIABLE_PATTERN = Pattern.compile(VALID_TOKEN_VARIABLE_REGEX);
	
	private static final String PREFIX = "${";
	private static final String SUFFIX = "}";
	
	private TokenHelper() {		
	}
	
	public static String applyTokensWithDefaultValue(String input, String defaultValue) {
		String result = input;
		if (input != null) {
			result = input.replaceAll(VALID_TOKEN_VARIABLE_REGEX, Matcher.quoteReplacement(defaultValue));
		}
		return result;
	}
	
	public static List<String> getInvalidTokenVariables(String input) {
		List<String> invalidTokenVariables = new ArrayList<>();
		if (input != null) {
			// Remove all valid Tokens
			String adjustedInput = input.replaceAll(VALID_TOKEN_VARIABLE_REGEX, DEFAULT_REPLACE_VALUE);

			String invalidTokenVariable = null;
			do {
				invalidTokenVariable = StringUtils.substringBetween(adjustedInput, PREFIX, SUFFIX);
				if (invalidTokenVariable != null) {
					invalidTokenVariables.add(format("%s%s%s", PREFIX, invalidTokenVariable, SUFFIX));

					String regex = convertKeyToRegex(invalidTokenVariable);
					adjustedInput = adjustedInput.replaceAll(regex, DEFAULT_REPLACE_VALUE);
				}

			} while (invalidTokenVariable != null);
		}

		return invalidTokenVariables;

	}
	
	public static List<String> getValidTokenVariables(String input) {
		List<String> validTokenVariables = new ArrayList<String>();

		if (StringUtils.isNotBlank(input)) {
			Matcher matcher = VALID_TOKEN_VARIABLE_PATTERN.matcher(input);
			while (matcher.find()) {
				validTokenVariables.add(input.substring(matcher.start(), matcher.end()));
			}
		}

		return validTokenVariables;
	}

	public static String applyTokens(String input, Map<String, RdProp> tokenMap) {
		return applyTokens(input, tokenMap, new ArrayList<String>());
	}
	
	protected static String applyTokens(String input, Map<String, RdProp> tokenMap, List<String> visitedTokens) {
		if(input==null) {
			return null;
		}		
		
		List<String> variablesFromInput = getValidTokenVariables(input);		
		
		String result = input;
		
		for (String tokenVariableWithBraces : variablesFromInput) {
			String tokenVariableNoBraces = StringUtils.substringBetween(tokenVariableWithBraces, PREFIX, SUFFIX);

			List<String> visited = new ArrayList<String>();
			visited.addAll(visitedTokens);		
			
			if (visited.contains(tokenVariableNoBraces)) {
				String errorMessageApplyTokens = generateErrorMessage(input, visited, tokenVariableNoBraces);								
				throw new IllegalStateException("cycle, data: " + errorMessageApplyTokens);
			}
			
			visited.add(tokenVariableNoBraces);
			
			RdProp value = null;
			
			// KE - if the tokenMap has a value for the token, but that value is null, substitute empty string
			if (tokenMap.containsKey(tokenVariableNoBraces)) {
				value = tokenMap.get(tokenVariableNoBraces);
			}
			
			String dereferencedValue = applyTokens(value==null?"":value.getValue(), tokenMap, visited);			
			if(value != null) {
				result = result.replace(tokenVariableWithBraces, dereferencedValue);				
			}
		}	
		
		if (result.contains(PREFIX)) {
			return input;
		}
		
		return result;
		
	}			
	
	protected static String generateErrorMessage(String input, List<String> visited, String tokenVariableNoBraces) {	
		String inputLogString = String.format("[input=%s]", input);
		String tokenVariableNoBracesLogString = String.format("[tokenVariableNoBraces=%s]", tokenVariableNoBraces);
		StringBuilder sb = new StringBuilder();
		if(visited == null || visited.isEmpty()) {
			sb.append("n/a");
		} else {
			sb.append(visited.toString());
		}
		String visitedLogString = String.format("[visited=[%s]]", sb.toString());
		return inputLogString + tokenVariableNoBracesLogString + visitedLogString; 
	}

	protected static String convertKeyToRegex(String key) {
		Character[] charactersToEscape = { '.', '-', '$', '{' };
		String newKey = key;
		for (Character character : charactersToEscape) {
			newKey = newKey.replaceAll("\\" + character, "\\\\" + character);
		}
		return "\\$\\{" + newKey + "\\}";
	}
	
	public static String getRawTokenVariable(String tokenVariableWithPrefixAndSuffix) {
		return StringUtils.substringBetween(tokenVariableWithPrefixAndSuffix, PREFIX, SUFFIX);
	}
	
}


