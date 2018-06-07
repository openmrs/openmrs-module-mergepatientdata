package org.openmrs.module.mergepatientdata.sync;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MPDClientClassBuilder {
	
	public MPDClient client;

	@Autowired
	public void setClient(MPDClient client) {
		this.client = client;
	}
	
	public MPDClient getClient() {
		return client;
	}

	
	public MPDClientClassBuilder() {}
	
	@Test
	public void clientTest() {
		System.out.println("Client in class helper "+ client);
	}
	
}
