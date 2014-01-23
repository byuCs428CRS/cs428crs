package database;

import models.Department;
import packages.Departments;

/**
 * @author: Nick Humrich
 * @date: 1/18/14
 */
public interface RegistrationStore {

  public Departments getAllDepartments();

  public void addDepartment(Department department);

}
