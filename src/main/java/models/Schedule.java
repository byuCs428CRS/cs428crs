package models;

import java.util.List;

/**
 * @autor: Nick Humrich
 * @date: 1/17/14
 */
public class Schedule {
    private String name;
    private Semester semester;
    private List<Section> classes;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public List<Section> getClasses() {
    return classes;
  }

  public void setClasses(List<Section> classes) {
    this.classes = classes;
  }
}
