package org.openmrs.module.mergepatientdata.api.exceptions;

public class MPDException extends RuntimeException {
	
	public MPDException(String message) {
		super(message);
	}
	
	public MPDException(Throwable cause) {
		super(cause);
	}
	
	public MPDException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
