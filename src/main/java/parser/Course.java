package parser;

public class Course {

	public String courseID;
	public String courseName;
	public String newTitleCode;
	public String department;
	public String registrationType;
	public String courseNumber;
	
	public Course(String courseID, String courseName, String newTitleCode, String department, String registrationType, String courseNumber) {
		
		this.courseID = courseID;
		this.courseName = courseName;
		this.newTitleCode = newTitleCode;
		this.department = department;
		this.registrationType = registrationType;
		this.courseNumber = courseNumber;
	}
}