package models;

import java.util.List;

/**
 * @autor: Nick Humrich
 * @date: 1/17/14
 */
public class Course {

  private String title;
  private String abreviation;
  private String courseId;
  private String description;
  private List<Section> sections;
  private List<String> fulfillments; //reqID's of Requirements it fulfills
  private List<String> prereqs; //courseId's of courses needed as a prereq

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
}
