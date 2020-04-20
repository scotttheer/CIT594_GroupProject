package edu.upenn.cit594.data;

import java.util.ArrayList;

public class AllProperties {
	private ArrayList<Property> allProperties;

	public AllProperties() {
		allProperties = new ArrayList<Property>();
	}


	public ArrayList<Property> getAllProperties() {
		return allProperties;
	}


	public void setAllProperties(ArrayList<Property> allProperties) {
		this.allProperties = allProperties;
	}


}