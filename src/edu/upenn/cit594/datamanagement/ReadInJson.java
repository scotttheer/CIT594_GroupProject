package edu.upenn.cit594.datamanagement;

import java.io.File;

import java.io.FileReader;
//import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import edu.upenn.cit594.data.ParkingViolation;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class ReadInJson implements ReadMethod{

	@Override
	public LinkedList<ParkingViolation> read(File f) {
		LinkedList<ParkingViolation> allParkingViolations = new LinkedList<>();
		JSONParser parser = new JSONParser();
		
		JSONArray in = null;
		try {
			in = (JSONArray)parser.parse(new FileReader(f));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Iterator<JSONObject> iter = in.iterator();
		while(iter.hasNext()) {
			JSONObject p = iter.next();

			String time = p.get("date").toString();	
			String description = p.get("violation").toString();
			int identifierForVehicle = Integer.parseInt(p.get("plate_id").toString());
			String state = p.get("state").toString();
			int identiferForThisViolation = Integer.parseInt(p.get("ticket_number").toString());
			double fine = Double.parseDouble(p.get("fine").toString());
			if(p.get("zip_code").toString().equals("")) {
				continue;
			}
			int ZIPCode = Integer.parseInt(p.get("zip_code").toString());

			ParkingViolation v = new ParkingViolation(time, fine, description, identifierForVehicle, state, identiferForThisViolation, ZIPCode);
			allParkingViolations.add(v);
		}

		return allParkingViolations;
	}
	
	
}
