package com.banking.entities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Properties {
	private HashMap<String, String> properties;
	private String location;
	
	public Properties(String location) throws IOException {
		this.location = location;
		properties = new HashMap<String, String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(location));
			String line;
			while((line = reader.readLine()) != null) {
				properties.put(line.split("=")[0], line.split("=")[1]);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Properties file not found.");
		} catch(IOException e) {
			throw new IOException("Error reading properties file.");
		}
	}
	public HashMap<String, String> getProperties() {
		return properties;
	}
	public void setProperties(HashMap<String, String> properties) {
		this.properties = properties;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
