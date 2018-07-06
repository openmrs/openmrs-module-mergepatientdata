package org.openmrs.module.mergepatientdata.api.model.audit;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.openmrs.module.mergepatientdata.enums.Operation;
import org.openmrs.module.mergepatientdata.enums.Status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class PaginatedAuditMessage {
	
	private static final String date_Format = "yyyy-MM-dd HH:mm:ss";
	
	private int id;
	
	private Operation operation;
	
	private Long itemsCount;
	
	private String uuid;
	
	private List<String> resources;
	
	private Date timeStamp;
	
	private String origin;
	
	private Status status;
	
	private boolean hasErrors;
	
	private String rows;
	
	private int page;
	
	private int pageSize;
	
	private List<String> failureDetails;
	
	private HashMap<String, Integer> resourceCount;
	
	public PaginatedAuditMessage() {
		this.timeStamp = new Date();
	}
	
	public PaginatedAuditMessage(Long itemCount, List<MergePatientDataAuditMessage> messages, int page, int pageSize) {
		Gson gson = new GsonBuilder().registerTypeAdapter(MergePatientDataAuditMessage.class,
		    new MergePatientDataAuditMessage.SerializationHandler()).create();
		this.rows = gson.toJson(messages);
		this.itemsCount = itemCount;
		this.page = page;
		this.pageSize = pageSize;
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Operation getOperation() {
		return operation;
	}
	
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
	public List<String> getResources() {
		return resources;
	}
	
	public void setResources(List<String> resources) {
		this.resources = resources;
	}
	
	public Date getTimeStamp() {
		return timeStamp;
	}
	
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public List<String> getFailureDetails() {
		return failureDetails;
	}
	
	public void setFailureDetails(List<String> failurDetails) {
		this.failureDetails = failurDetails;
	}
	
	public HashMap<String, Integer> getResourceCount() {
		return resourceCount;
	}
	
	public void setResourceCount(HashMap<String, Integer> resourceCount) {
		this.resourceCount = resourceCount;
	}
	
	public boolean isHasErrors() {
		return hasErrors;
	}
	
	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getRows() {
		return rows;
	}
	
	public void setRows(String rows) {
		this.rows = rows;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public Long getItemsCount() {
		return itemsCount;
	}
	
	public void setItemsCount(Long itemsCount) {
		this.itemsCount = itemsCount;
	}
	
	public static class AuditPageSerializationHandler implements JsonSerializer<PaginatedAuditMessage> {
		
		@Override
		public JsonElement serialize(PaginatedAuditMessage src, Type type, JsonSerializationContext context) {
			JsonObject object = new JsonObject();
			object.addProperty("rows", src.rows);
			object.addProperty("itemsCount", src.getItemsCount());
			return object;
		}
		
	}
}
