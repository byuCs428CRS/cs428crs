package models;

import java.util.List;

/**
 * @autor: Nick Humrich
 * @date: 1/17/14
 */
public class Department implements Comparable<Department> {

  private String title;
  private String shortCode;
  private List<String> courses;

  public Department() {

  }

  public Department(String title, String shortCode) {
    this.title = title;
    this.shortCode = shortCode;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getShortCode() {
    return shortCode;
  }

  public void setShortCode(String shortCode) {
    this.shortCode = shortCode;
  }

  public List<String> getCourses() {
    return courses;
  }

  public void setCourses(List<String> courses) {
    this.courses = courses;
  }

  public void addCourse(String courseId) {
    courses.add(courseId);
  }

  @Override
  public int compareTo(Department o) {
    if (this != null && o != null) {
      if (shortCode != null && o.shortCode != null) {
        return shortCode.compareTo(o.getShortCode());
      }
    }
    return 0;
  }
}
