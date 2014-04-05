package parser.catalog;

import java.util.List;

public class Course {

	public String courseID; //BYU id -
	public String courseName; //Title
	public String newTitleCode; // TitleCode
	public String department;   // Department name & short code
    public String departmentCode; // Department short code
	public String registrationType; //
	public String courseNumber;  //CourseId  //BIO "100"
    public List<String> outcomes;    // Description ?



    public List<Section> sections;

    public Course(String courseID, String courseName, List<String> outcomes, String newTitleCode, String department, String departmentCode, String registrationType, String courseNumber) {

        this.courseID = courseID;
        this.courseName = courseName;
        this.outcomes = outcomes;
        this.newTitleCode = newTitleCode;
        this.department = department;
        this.departmentCode = departmentCode;
        this.registrationType = registrationType;
        this.courseNumber = courseNumber;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }



    /*
      private String title;


  private String description;
  private String byuId;

  private float credits;
  private List<Section> sections;
     */



}