package edu.upenn.cit594.datamanagement;

import java.io.File;

import edu.upenn.cit594.data.AllParkingViolations;

public interface ReadMethod {
	public AllParkingViolations read(File f);
}
