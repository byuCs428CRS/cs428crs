package parser.catalog;

import com.mongodb.BasicDBObject;

import java.util.ArrayList;
import java.util.List;

public class Section {

	public String courseID;
	public String newTitleCode;
	public String department;
	public String registrationType;
	public String courseNumber;
	
	public String sectionID;
	public String sectionType;
	public String courseName;
	public String professor;
	public String creditHours;
	
	public List<String> days;
	public List<String> startTimes;
	public List<String> endTimes;
	public List<String> locations;
	public List<TimePlace> timePlaces;
	
	public List<String> notes;
	public String seatsAvailable;
	public String totalSeats;
	public String waitList;
	
	/**
	 * Sets one of this class's many variables to the element String
	 * Whichever variable is set is determined by the index i, handled by a switch statement
	 * @param i The index of the variable to be set (using switch)
	 * @param element The overall string to modify/set
	 */
	public void setElement(int i, String element) {
		
    	switch (i) {
    	
			case 0: courseID = element;
		    		break;
		    		
			case 1: newTitleCode = element;
					break;
					
			case 2: department = element;
					break;
					
			case 3: registrationType = element;
					break;
					
			case 4: courseNumber = element;
					break;
					
			case 5: /*if (element.length() > 0)
						System.out.println("BEFORE SEC NUMBER: " + element);*/
					break;
					
			case 6: sectionID = element;
					break;
					
			case 7: /*if (element.length() > 0)
						System.out.println("AFTER SEC NUMBER: " + element);*/
					break;
					
			case 8: sectionType = element;
					break;
					
			case 9: courseName = element;
					break;
					
			case 10: professor = element;
					 break;
					
			case 11: creditHours = element;
					 break;
					
			case 12: days = splitNewline(element);
					 break;
					
			case 13: startTimes = splitNewline(element);
					 break;
					
			case 14: endTimes = splitNewline(element);
					 break;
					
			case 15: locations = splitNewline(element);
					 fillRemainingFields();
					 makeTimePlaces();
					 break;
			
			case 16: notes = splitNewline(element);
					 break;
			
			case 17: seatsAvailable = element.substring(0, element.indexOf("/") - 1);
					 totalSeats = element.substring(element.indexOf("/") + 2);
					 break;
					 
			case 18: waitList = element;
					 break;
					
			default: System.out.println("ERROR: Your index isn't between 0 and 18!");
					 break;
    	}
	}
	
	/**
	 * Some courses have different numbers of days, times, and locations
	 * (e.g. search for "Beecher, Mark", his course has 3 days and times, but only one "TBA" for location)
	 * This method adds "TBA" to fields that are lacking (so we can create TimePlace objects)
	 */
	public void fillRemainingFields() {
		
		int maxSize = 0;
		if (days.size() > maxSize)
			maxSize = days.size();
		if (startTimes.size() > maxSize)
			maxSize = startTimes.size();
		if (endTimes.size() > maxSize)
			maxSize = endTimes.size();
		if (locations.size() > maxSize)
			maxSize = locations.size();
		
		while (days.size() < maxSize)
			days.add("TBA");
		while (startTimes.size() < maxSize)
			startTimes.add("TBA");
		while (endTimes.size() < maxSize)
			endTimes.add("TBA");
		while (locations.size() < maxSize)
			locations.add("TBA");
	}
	
	/**
	 * Split a string on newline chars 
	 * (Used for splitting the days/times/locations into lists)
	 * @param element the entire string to split on newline
	 * @return List<String> A list of remnant strings after splitting
	 */
	public List<String> splitNewline(String element) {
		
		String[] textStr = element.split("\\r?\\n");
		List<String> tempList = new ArrayList<>();
		for (int i = 0; i < textStr.length; i++)
			if (textStr[i].length() > 0)
				tempList.add(textStr[i]);
		return tempList;
	}
	
	/**
	 * Prints all of this Section's variables
	 */
	public void print() {
		
		System.out.println();
		System.out.println("CourseID:\t" + courseID);
		System.out.println("newTitleCode:\t" + newTitleCode);
		System.out.println("department:\t" + department);
		System.out.println("registrationType:\t" + registrationType);
		System.out.println("courseNumber:\t" + courseNumber);
		System.out.println("sectionID:\t" + sectionID);
		System.out.println("sectionType:\t" + sectionType);
		System.out.println("courseName:\t" + courseName);
		System.out.println("professor:\t" + professor);
		System.out.println("creditHours:\t" + creditHours);
		
		System.out.println("day/startTime/endTime/location:");
		for (int i = 0; i < timePlaces.size(); i++)
			timePlaces.get(i).print("\t");
			
		System.out.println("Notes:");
		for (int i = 0; i < notes.size(); i++)
			System.out.println("\t" + (i+1) + "- " + notes.get(i));
		
		System.out.println("Seats available: " + seatsAvailable + " (out of " + totalSeats + ")");
		System.out.println("waitList: " + waitList);
		System.out.println();
	}
	
	/**
	 * Create a BasicDBObject out of this Section Object and return it 
	 * @return BasicDBObject A database object that represents this section
	 */
	public BasicDBObject getDBObject() {
		
		BasicDBObject sectionObject = new BasicDBObject();
		
		sectionObject.put("sectionID", sectionID);
		sectionObject.put("type", sectionType);
		sectionObject.put("courseName", courseName);
		sectionObject.put("professor", professor);
		sectionObject.put("creditHours", creditHours);
		sectionObject.put("seatsAvailable", seatsAvailable);
		sectionObject.put("totalSeats", totalSeats);
		sectionObject.put("waitList", waitList);
		
		// Add the Notes by creating a List of DB Objects (one object for each note)
		List<BasicDBObject> notesObject = new ArrayList<>();
		for (int i = 0; i < notes.size(); i++) {
			
			BasicDBObject tempNote = new BasicDBObject();
			tempNote.put("note", notes.get(i));
			notesObject.add(tempNote);
		}
		sectionObject.put("notes", notesObject);
		
		// Add the TimePlaces by creating a DB Object for each one
		List<BasicDBObject> timeplaces = new ArrayList<>();
		for (int i = 0; i < timePlaces.size(); i++) {
			
			timeplaces.add(timePlaces.get(i).getDBObject());
		}
		sectionObject.put("timePlaces", timeplaces);
		
		return sectionObject;
	}

	/**
	 * With the current days/startTimes/endTimes/locations, create an object to hold them
	 */
	public void makeTimePlaces() {
		
		timePlaces = new ArrayList<>();
		for (int i = 0; i < days.size(); i++) {
			
			timePlaces.add(new TimePlace(days.get(i), startTimes.get(i), endTimes.get(i), locations.get(i)));
		}
	}
}