package edu.upenn.cit594.datamanagement;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import edu.upenn.cit594.data.AllParkingViolations;
import edu.upenn.cit594.data.ParkingViolation;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadInJson implements ReadMethod{

	@Override
	public AllParkingViolations read(File f) {
		ArrayList<ParkingViolation> allParkingViolations = new ArrayList<>();
		JSONParser parser = new JSONParser();

		try {
			JSONArray inputData = (JSONArray) parser.parse(new FileReader(f));

			Iterator<JSONObject> iter = inputData.iterator();

			while(iter.hasNext()) {
				JSONObject p = iter.next();

				String time;
				double fine;
				String description;
				String identifierForVehicle;
				String state;
				String identiferForThisViolation;
				int ZIPCode; 

				//error handeling mechanism for JSON

				try {

					time = (String) p.get("date");	

					description = (String) p.get("violation");

					identifierForVehicle = (String) p.get("plate_id");

					state = (String) p.get("state");

					identiferForThisViolation = (String) p.get("ticket_number");

					fine = (double) p.get("fine");

					ZIPCode = (int) p.get("zip_code");

					ParkingViolation v = new ParkingViolation(time, fine, description, identifierForVehicle, state, identiferForThisViolation, ZIPCode);

					allParkingViolations.add(v);

				} catch (ClassCastException e) {
					//ingore this object since it is not well formated
				}
			}
			AllParkingViolations allParkingViolationsInfo = new AllParkingViolations();
			allParkingViolationsInfo.setAllParkingViolations(allParkingViolations);
			return allParkingViolationsInfo;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
