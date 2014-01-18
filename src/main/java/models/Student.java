package models;

import java.util.List;
import java.util.Map;

/**
 * @autor: Nick Humrich
 * @date: 1/17/14
 */
public class Student {

  private String firstName;
  private String lastName;
  private String studentId;
  private List<Schedule> schedules;
  private Map<Course, Grade> history;
  private List<Course> plannedCourses;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getStudentId() {
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  public List<Schedule> getSchedules() {
    return schedules;
  }

  public void setSchedules(List<Schedule> schedules) {
    this.schedules = schedules;
  }

  public Map<Course, Grade> getHistory() {
    return history;
  }

  public void setHistory(Map<Course, Grade> history) {
    this.history = history;
  }

  public List<Course> getPlannedCourses() {
    return plannedCourses;
  }

  public void setPlannedCourses(List<Course> plannedCourses) {
    this.plannedCourses = plannedCourses;
  }
}
