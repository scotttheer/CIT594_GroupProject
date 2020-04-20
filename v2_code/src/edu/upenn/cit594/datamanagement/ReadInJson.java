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
	public ArrayList<ParkingViolation> read(File f) {
		ArrayList<ParkingViolation> allParkingViolations = new ArrayList<>();
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
	
	public static void main(String[] args) {
		ReadInJson rdr = new ReadInJson();
		File violationsFile = new File("parking.json");
		ArrayList<ParkingViolation> violations = new ArrayList<ParkingViolation>();
		violations = rdr.read(violationsFile);
		for(ParkingViolation v: violations) {
			System.out.println(v.getZIPCode()+": "+ v.getFine());
		}
	}
}
