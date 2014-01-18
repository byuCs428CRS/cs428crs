package models;

import java.util.List;

/**
 * @autor: Nick Humrich
 * @date: 1/17/14
 */
public class Section {
  private String courseId;

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getSectionId() {
    return sectionId;
  }

  public void setSectionId(String sectionId) {
    this.sectionId = sectionId;
  }

  public String getProfessor() {
    return professor;
  }

  public void setProfessor(String professor) {
    this.professor = professor;
  }

  public List<Time> getTimes() {
    return times;
  }

  public void setTimes(List<Time> times) {
    this.times = times;
  }

  public int getClassSize() {
    return classSize;
  }

  public void setClassSize(int classSize) {
    this.classSize = classSize;
  }

  public List<Student> getRegisteredStudents() {
    return registeredStudents;
  }

  public void setRegisteredStudents(List<Student> registeredStudents) {
    this.registeredStudents = registeredStudents;
  }

  private String sectionId;
  private String professor;
  private List<Time> times;
  private int classSize;
  private List<Student> registeredStudents;
}
