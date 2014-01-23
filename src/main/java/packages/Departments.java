package packages;

import models.Department;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor: Nick Humrich
 * @date: 1/17/14
 */
public class Departments {
  private List<Department> departments;



  public Departments() {
    departments = new ArrayList<>();
  }

  public void setDepartments(List<Department> list) {
    departments = list;
  }

  public void addMajor(Department department) {
    departments.add(department);
  }

  public List<Department> getDepartments() {
    return departments;
  }
}
