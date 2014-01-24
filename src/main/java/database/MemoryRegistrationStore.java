package database;

import models.Department;
import models.Major;
import models.requirements.Requirement;

import java.util.*;

/**
 * @author: Nick Humrich
 * @date: 1/21/14
 */
public class MemoryRegistrationStore implements RegistrationStore {
  private static MemoryRegistrationStore root = new MemoryRegistrationStore();
  private List<Department> departments;
  private Map<String, Major> majors;

  private MemoryRegistrationStore() {
    departments = new ArrayList<>();
    majors = new HashMap<>();
  }

  public static MemoryRegistrationStore getInstance() {
    return root;
  }

  public static void reset() {
    root = new MemoryRegistrationStore();
  }

  @Override
  public List<Department> getAllDepartments() {
    return departments;
  }

  @Override
  public void addDepartment(Department department) {
    departments.add(department);
    Collections.sort(departments);
  }

  @Override
  public List<Requirement> getRequirementsForMajor(String major) {
    Major m = majors.get(major);
    return m.getRequirements();
  }
}
