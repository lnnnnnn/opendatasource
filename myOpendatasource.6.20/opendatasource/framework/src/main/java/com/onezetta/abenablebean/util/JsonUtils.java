package com.onezetta.abenablebean.util;

import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class JsonUtils {
	public static boolean isBadJson(String json) {
		return !isGoodJson(json);
	}

	public static boolean isGoodJson(String json) {
		if (StringUtils.isBlank(json)) {
			return false;
		}
		try {
			new JsonParser().parse(json);
			return true;
		} catch (JsonParseException e) {
			return false;
		}
	} 
}
