package database;

import models.Course;
import models.Department;
import models.requirements.Requirement;

import java.util.List;

/**
 * @author: Nick Humrich
 * @date: 1/18/14
 */
public interface RegistrationStore {

  public List<Department> getAllDepartments();

  public void addDepartment(Department department);

  public List<Requirement> getRequirementsForMajor(String major);

  public Course getCourse(String courseId);

}
