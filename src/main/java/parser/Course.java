package parser;

import com.mongodb.DBObject;

public class Course {

	public String courseID;
	public String courseName;
	public String newTitleCode;
	public String department;
	public String registrationType;
	public String courseNumber;

    public Course(DBObject dbObject){
        this.courseID = dbObject.get("courseID").toString();
        this.courseName = dbObject.get("courseName").toString();
        this.newTitleCode = dbObject.get("newTitleCode").toString();
        this.department = dbObject.get("department").toString();
        this.registrationType = dbObject.get("registrationType").toString();
        this.courseNumber = dbObject.get("courseNumber").toString();
    }
	
	public Course(String courseID, String courseName, String newTitleCode, String department, String registrationType, String courseNumber) {
		
		this.courseID = courseID;
		this.courseName = courseName;
		this.newTitleCode = newTitleCode;
		this.department = department;
		this.registrationType = registrationType;
		this.courseNumber = courseNumber;
	}
}