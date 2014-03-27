package parser.instructor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class InstructorParser {

    public static void main(String[] args) {
    	
    	String jsonString = "";
    	
    	// Read in the JSON file
		String file = System.getProperty("user.dir") + "/professors.json";
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				
				jsonString += line;
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    	
		// Use gson to parse the json String into a list of Instructor objects
    	Gson gson = new Gson();
    	Type stringStringMap = new TypeToken<List<Instructor>>(){}.getType();
    	List<Instructor> listInstructors = gson.fromJson(jsonString, stringStringMap);
    	
    	// Take the contents of the list and insert it into a map (for faster searching by instructor name)
    	Map<String, String> mapInstructors = new HashMap<>();
    	for (int i = 0; i < listInstructors.size(); i++) {
    		
    		if (mapInstructors.containsKey(listInstructors.get(i)))
    			System.out.println("DUPLICATE PROFESSOR: " + listInstructors.get(i));
    		else
    			mapInstructors.put(listInstructors.get(i).teacherName, listInstructors.get(i).teacherID);
    	}
    	
    	// Print map contents
    	for (String key : mapInstructors.keySet()) {
    		
    		System.out.println("Key:\t" + key + "\tValue:\t" + mapInstructors.get(key));
    	}
    	
    	// TODO Iterate through db, find every instructor for every class, and assign a corresponding Instructor ID
    }
    
    public class Instructor {
    	
    	public String teacherID;
    	public String teacherName;
    }
}