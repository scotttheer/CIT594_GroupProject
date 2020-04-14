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

				String time = (String) p.get("date");

				//if the field is empty, what will be returned with casting
				double fine = (double) p.get("fine");

				String description = (String) p.get("violation");

				String identifierForVehicle = (String) p.get("plate_id");

				String state = (String) p.get("state");

				String identiferForThisViolation = (String) p.get("ticket_number");

				int ZIPCode = (int) p.get("zip_code");

				//how to filter through invalid row of input
				if(!time.equals(null) && fine != -1 && !description.equals(null) && !identifierForVehicle.equals(null)
						&& !state.equals(null) && !identiferForThisViolation.equals(null) && ZIPCode != -1) {
					ParkingViolation v = new ParkingViolation(time, fine, description, identifierForVehicle, state, identiferForThisViolation, ZIPCode);
					allParkingViolations.add(v);
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
