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

  public List<Major> getMajors() {
    return majors;
  }

  public Majors() {
    majors = new ArrayList<Major>();
    Major major = new Major();
    majors.add(major);
  }
}
