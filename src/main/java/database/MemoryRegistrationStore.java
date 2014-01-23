package database;

import models.Department;
import packages.Departments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: Nick Humrich
 * @date: 1/21/14
 */
public class MemoryRegistrationStore implements RegistrationStore {
  private static MemoryRegistrationStore root = new MemoryRegistrationStore();
  private List<Department> departments;

  private MemoryRegistrationStore() {
    departments = new ArrayList<>();
  }

  public static MemoryRegistrationStore getInstance() {
    return root;
  }

  public static void reset() {
    root = new MemoryRegistrationStore();
  }

  @Override
  public Departments getAllDepartments() {
    Departments d = new Departments();
    d.setDepartments(departments);
    return d;
  }

  @Override
  public void addDepartment(Department department) {
    departments.add(department);
    Collections.sort(departments);
  }
}
