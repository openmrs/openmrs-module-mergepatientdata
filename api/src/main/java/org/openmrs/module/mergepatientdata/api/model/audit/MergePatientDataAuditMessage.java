package org.openmrs.module.mergepatientdata.api.model.audit;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.annotations.Persister;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.openmrs.BaseOpenmrsData;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

@Persister(impl = SingleTableEntityPersister.class)
public class MergePatientDataAuditMessage extends BaseOpenmrsData {
	
	private static final long serialVersionUID = 1L;
	
	private static final String date_Format = "yyyy-MM-dd HH:mm:ss";
	
	private int id;
	
	private String operation;
	
	private String resources;
	
	private Date timestamp;
	
	private String origin;
	
	private String status;
	
	private String failureDetails;
	
	private String jsonResourceCounterObject;
	
	public MergePatientDataAuditMessage() {
		
	}
	
	public MergePatientDataAuditMessage(String operation, String resources, Date timestamp, String origin, String status,
	    String failureDetails, String jsonResourceCounterObject) {
		this.operation = operation;
		this.resources = resources;
		this.timestamp = timestamp;
		this.origin = origin;
		this.status = status;
		this.failureDetails = failureDetails;
		this.jsonResourceCounterObject = jsonResourceCounterObject;
	}
	
	@Override
	public Integer getId() {
		return id;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getOperation() {
		return operation;
	}
	
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public String getResources() {
		return resources;
	}
	
	public void setResources(String resources) {
		this.resources = resources;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getFailureDetails() {
		return failureDetails;
	}
	
	public void setFailureDetails(String failureDetails) {
		this.failureDetails = failureDetails;
	}
	
	public String getJsonResourceCounterObject() {
		return jsonResourceCounterObject;
	}
	
	public void setJsonResourceCounterObject(String jsonResourceCountObject) {
		this.jsonResourceCounterObject = jsonResourceCountObject;
	}
	
	public static class SerializationHandler implements JsonSerializer<MergePatientDataAuditMessage> {
		
		@Override
		public JsonElement serialize(MergePatientDataAuditMessage src, Type type, JsonSerializationContext context) {
			JsonObject object = new JsonObject();
			DateFormat formatter = new SimpleDateFormat(date_Format);
			object.addProperty("uuid", src.getUuid());
			object.addProperty("operation", src.operation);
			object.addProperty("origin", src.origin);
			object.addProperty("time", formatter.format(src.timestamp));
			object.addProperty("status", src.status);
			return object;
		}
		
	}
}
