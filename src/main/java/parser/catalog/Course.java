package catalog;

import java.util.List;

public class Course {

	public String courseID;
	public String courseName;
	public String newTitleCode;
	public String department;
	public String registrationType;
	public String courseNumber;
    public List<Section> sections;

    public Course(String courseID, String courseName, String newTitleCode, String department, String registrationType, String courseNumber) {

        this.courseID = courseID;
        this.courseName = courseName;
        this.newTitleCode = newTitleCode;
        this.department = department;
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