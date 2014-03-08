package models;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor: Nick Humrich
 * @date: 1/17/14
 */
public class Course implements Comparable<Course> {

  private String title;
  private String owningDepartment; //shortCode of owning department
  private String courseId;  //number of id does NOT include departments short code
  private String description;
  private float credits;
  private List<Section> sections;
  private List<String> fulfillments; //reqID's of Requirements it fulfills
  private List<String> prereqs; //courseId's of courses needed as a prereq


  public Course() {

  }

  public Course(String title, String courseId) {
    this.title = title;
    this.courseId = courseId;
    sections = new ArrayList<>();
    fulfillments = new ArrayList<>();
    prereqs = new ArrayList<>();
  }



    public String getFullId() {
    return owningDepartment + " " + courseId;
  }
  public float getCredits() {
    return credits;
  }

  public void setCredits(float credits) {
    this.credits = credits;
  }

  public String getOwningDepartment() {
    return owningDepartment;
  }

  public void setOwningDepartment(String owningDepartment) {
    this.owningDepartment = owningDepartment;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Section> getSections() {
    return sections;
  }

  public void setSections(List<Section> sections) {
    this.sections = sections;
  }

  public List<String> getFulfillments() {
    return fulfillments;
  }

  public void setFulfillments(List<String> fulfillments) {
    this.fulfillments = fulfillments;
  }

  public List<String> getPrereqs() {
    return prereqs;
  }

  public void setPrereqs(List<String> prereqs) {
    this.prereqs = prereqs;
  }

  @Override
  public String toString(){

    /*
      private String title;
  private String owningDepartment; //shortCode of owning department
  private String courseId;  //number of id does NOT include departments short code
  private String description;
  private float credits;
  private List<Section> sections;
  private List<String> fulfillments; //reqID's of Requirements it fulfills
  private List<String> prereqs; //courseId's of courses needed as a prereq
     */
    String s =  "Id: " + courseId + "\tCredits: " + credits +"\tDept: " + owningDepartment + "\t\tDescription: " + description + "\t\tTitle: " + title;

/*
      s += "\nSections:\t";
      for(Section sec : sections){
          s += " " + sec.toString();
      }
      s += "\nFulfillments:\t";
      for(String str : fulfillments){
          s += " " + str;
      }
      s += "\nPrereqs:\t";
      for(String str : prereqs){
          s += " " + str;
      }
*/


    return s;
  }

  @Override
  public int compareTo(Course o) {
    if (this != null && o != null) {
      if (courseId != null && o.courseId != null) {
        return courseId.compareTo(o.courseId);
      }
    }
    return 0;
  }
}
