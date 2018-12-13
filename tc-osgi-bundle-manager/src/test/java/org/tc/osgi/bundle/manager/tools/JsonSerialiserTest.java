package org.tc.osgi.bundle.manager.tools;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JsonSerialiserTest {

	private String data;
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Test
	public void test() {
		JsonSerialiser s=new JsonSerialiser();
		this.setData("maData");
		
		String actual =s.toJson(this);
		
		assertEquals("{\r\n" + 
				"  \"data\" : \"maData\"\r\n" + 
				"}", actual);
		
	}

}
