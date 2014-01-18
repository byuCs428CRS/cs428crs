package packages;

import models.Major;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor: Nick Humrich
 * @date: 1/17/14
 */
public class Majors {
  private List<Major> majors;



  public Majors() {
    majors = new ArrayList<>();
  }

  public void setMajors(List<Major> list) {
    majors = list;
  }

  public void addMajor(Major major) {
    majors.add(major);
  }

  public List<Major> getMajors() {
    return majors;
  }
}
