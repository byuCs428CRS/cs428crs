package models;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor: Nick Humrich
 * @date: 1/17/14
 */
public class Section {
  private String courseId;
  private String sectionId;
  private String professor;
  private List<Time> times;
  private int classSize;
  private List<String> registeredStudents; //student Id's of registered students


  public Section(String courseId, String sectionId, int classSize) {
    this.courseId = courseId;
    this.sectionId = sectionId;
    this.classSize = classSize;
    times = new ArrayList<>();
    registeredStudents = new ArrayList<>();
  }

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

  public List<String> getRegisteredStudents() {
    return registeredStudents;
  }

  public void setRegisteredStudents(List<String> registeredStudents) {
    this.registeredStudents = registeredStudents;
  }
}
