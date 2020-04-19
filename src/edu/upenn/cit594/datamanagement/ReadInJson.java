package edu.upenn.cit594.datamanagement;

import java.io.File;



import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import edu.upenn.cit594.data.ParkingViolation;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadInJson implements ReadMethod{

	@Override
	public LinkedList<ParkingViolation> read(File f) {
		
		LinkedList<ParkingViolation> allParkingViolations = new LinkedList<>(); 
		
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

					identiferForThisViolation = p.get("ticket_number").toString();
					
					fine = Double.parseDouble(p.get("fine").toString());

					ZIPCode = Integer.parseInt(p.get("zip_code").toString());
					
					ParkingViolation v = new ParkingViolation(time, fine, description, identifierForVehicle, state, identiferForThisViolation, ZIPCode);

					allParkingViolations.add(v);
					
				} catch (ClassCastException e) {
					//ingore this object since it is not well formated
				} catch (NumberFormatException a) {
					
				}
			}
			return allParkingViolations;
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
		return allParkingViolations;
	}
	
	
	public static void main(String[] args) {
		File f = new File("parking.csv");
		
		ReadInJson a = new ReadInJson();
		
		LinkedList<ParkingViolation> b = a.read(f);
		
		System.out.println(b.size());
	}
	
	
	
	
}
