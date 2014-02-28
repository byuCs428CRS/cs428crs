package parser;
import com.mongodb.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;
import java.net.UnknownHostException;

public class Parser {
	
	public static int index;
	public static List<Section> sections = new ArrayList<>();
	public static Section curSection = new Section();

    public static void main(String[] args) throws UnknownHostException
    {
		index = 0;
		String fileName = System.getProperty("user.dir") + "/parser/TestCatalog.txt";
    	File file = new File(fileName);
    	try {
		
    		BufferedReader reader = new BufferedReader(new FileReader(file));
    		String line = "";
    		String temp;
    		while ((temp = reader.readLine()) != null) { // If the line doesn't have "#" in it, append the next line
    			
    			line += "\n" + temp;
    			while (line.contains("#")) {
    			
    				insert(line.substring(0, line.indexOf("#")));	
    				line = line.substring(line.indexOf("#") + 1);
    			}
    		}
    		reader.close();
		} 
    	catch (IOException e) {

			System.out.println("Error: FILE WAS NOT FOUND");
			e.printStackTrace();
		}
    	
    	// Print the whole list
    	
    	/*for (int i = 0; i < sections.size(); i++) {
    		
    		System.out.println("\t\t PRINTING ELEMENT #" + (i+1));
    		sections.get(i).print();
    	}*/
    	
    	System.out.println("Success in parsing!");
    	System.out.println("Total Sections Parsed: " + sections.size() + "\n");
    	
    	// Insert the courses into the database
    	if (sections.size() > 0) {
	    	
    		DB db = getDB();
	    	insertIntoDB(db);
	    	printCourses(db);
    	}
    	else
    		System.out.println("No Sections to insert!");
    }
    
    /* Print all of the documents in the "course" collection (from the db parameter database) */
    public static void printCourses(DB db) {
    	
    	// Print the count in sections
    	DBCollection courses = db.getCollection("course");
    	int count = courses.find().count();
    	
    	DBCursor docs = courses.find();
    	while (docs.hasNext()) {
    		
    		DBObject doc = docs.next();
    		System.out.println("Course ID: " + doc.get("courseID") + "\tName: " + doc.get("courseName") + 
    				/*"\tNTC: " + doc.get("newTitleCode") + */"\tdept: " + doc.get("department") 
    				/*+ "\tregType: " + doc.get("registrationType")*/);
    	}
    	System.out.println("\nYour DB has a total of " + (count) + " courses.");
    }
    
    // Insert all of the Sections
    public static void insertIntoDB(DB db) {
    	
    	Map<String, List<BasicDBObject>> courses = new HashMap<>();
    	Map<String, Course> courseInfo = new HashMap<>();
    	
    	// Format into courses
    	for (int i = 0; i < sections.size(); i++) {
    		
    		if (courses.containsKey(sections.get(i).courseID))
    			courses.get(sections.get(i).courseID).add(sections.get(i).getDBObject());
    		else {
    		
    			Section s = sections.get(i);
    			courses.put(s.courseID, new ArrayList<BasicDBObject>(Arrays.asList(s.getDBObject())));
    			courseInfo.put(s.courseID, new Course(s.courseID, s.courseName, s.newTitleCode, s.department, s.registrationType, s.courseNumber));
    		}
    	}
    	
    	// Create an ArrayList of BasicDBObjects (1 for each course) called courseObjects
    	List<BasicDBObject> courseObjects = new ArrayList<>();
    	for (Map.Entry<String, List<BasicDBObject>> curCourse : courses.entrySet()) {
    		
    		Course c = courseInfo.get(curCourse.getKey());
    		BasicDBObject newCourse = new BasicDBObject();
    		
    		newCourse.put("courseID", curCourse.getKey());
    		newCourse.put("courseName", c.courseName);
    		newCourse.put("newTitleCode", c.newTitleCode);
    		newCourse.put("department", c.department);
    		newCourse.put("registrationType", c.registrationType);
    		newCourse.put("courseNumber", c.courseNumber);
    		newCourse.put("sections", curCourse.getValue());
    		
    		courseObjects.add(newCourse);
    	}
    	
    	// Change format from ArrayList to an Array (for inserting)
    	BasicDBObject[] courseArray = new BasicDBObject[courseObjects.size()];
    	for (int i = 0; i < courseObjects.size(); i++)
    		courseArray[i] = courseObjects.get(i);
    		
    	// Insert into the "course" collection in our DB
		DBCollection courseCollection = db.getCollection("course");
		System.out.println("Start inserting into DB");
		courseCollection.insert(courseArray);
		System.out.println("Done inserting\n");
    }
    
    public static void insert(String nextElement) {
    	
    	curSection.insert(index, nextElement);
    	index++;
    	
    	if (index == 19) {
    	    		
    		sections.add(curSection);
    		curSection = new Section();
    		index = 0;
    	}
    }
    
    public static DB getDB() {
    	
        String dbUser = "classreg428";
        String dbPassword = "ad428min";

        // Connect to our database
        MongoClientURI uri = new MongoClientURI("mongodb://" + dbUser + ":" + dbPassword +
                "@ds063297.mongolab.com:63297/classreg");
        MongoClient client;
		try {
			client = new MongoClient(uri);
	        DB db = client.getDB(uri.getDatabase());
	        return db;
		} catch (UnknownHostException e) {

			System.out.println("COULDN\'T GET THE DB");
			e.printStackTrace();
		}
		return null;
    }
}