package models.requirements;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor: Nick Humrich
 * @date: 1/17/14
 */
public class Requirement {
  private String id;
  private String title;
  private String description;
  private String parentId;
  private float requiredCount;
  private CountType countType;
  private List<String> children; //Id's of children requirements
  private List<String> courses; //Id's of children courses

  public Requirement() {
    children = new ArrayList<>();
    courses = new ArrayList<>();
  }

  public void addChild(Requirement req) {
    children.add(req.getId());
    req.setParentId(this.id);
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public List<String> getChildren() {
    return children;
  }

  public void setChildren(List<String> children) {
    this.children = children;
  }

  public CountType getCountType() {
    return countType;
  }

  public void setCountType(CountType countType) {
    this.countType = countType;
  }

  public float getRequiredCount() {
    return requiredCount;
  }

  public void setRequiredCount(float requiredCount) {
    this.requiredCount = requiredCount;
  }

  public List<String> getCourses() {
    return courses;
  }

  public void setCourses(List<String> courses) {
    this.courses = courses;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
