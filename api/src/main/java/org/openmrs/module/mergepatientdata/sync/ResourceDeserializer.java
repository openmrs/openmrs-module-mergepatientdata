package org.openmrs.module.mergepatientdata.sync;

import java.lang.reflect.Type;

import org.openmrs.module.mergepatientdata.Resource;
import org.openmrs.module.mergepatientdata.resource.Location;
import org.openmrs.module.mergepatientdata.resource.Patient;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class ResourceDeserializer implements JsonDeserializer<Resource> {

	@Override
	public Resource deserialize(JsonElement json, Type type, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		if (jsonObject != null) {
			return context.deserialize(jsonObject, getClassInstance(jsonObject.getAsString()));
		}
		return null;
	}
	
	private Class getClassInstance(String className) {
		try {
			return (Class)Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new JsonParseException(e.getMessage());
		}
	}

}
