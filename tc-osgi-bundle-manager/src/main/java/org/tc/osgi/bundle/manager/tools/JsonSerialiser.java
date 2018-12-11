package org.tc.osgi.bundle.manager.tools;

import org.tc.osgi.bundle.manager.module.service.LoggerServiceProxy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerialiser {

	
	public String toJson(Object o)
	{
		String result="Error in serialisation object "+ o;
		try {
			return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(o);
		} catch (JsonProcessingException e) {
			LoggerServiceProxy.getInstance().getLogger(JsonSerialiser.class).error(result,e);
		}
		return result;
	}
}
