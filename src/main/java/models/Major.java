package models;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor: Nick Humrich
 * @date: 1/17/14
 */
public class Major {

  private String title;
  private String abreviation;
  private List<Course> courses;

  public Major() {
    this.courses = new ArrayList<Course>();
    Course course = new Course();
    courses.add(course);
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAbreviation() {
    return abreviation;
  }

  public void setAbreviation(String abreviation) {
    this.abreviation = abreviation;
  }

  public List<Course> getCourses() {
    return courses;
  }

  public void setCourses(List<Course> courses) {
    this.courses = courses;
  }
}
