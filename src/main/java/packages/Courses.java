package packages;

import models.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Nick Humrich
 * @date: 1/18/14
 */
public class Courses {
  private List<Course> courses;

  public Courses() {
    courses = new ArrayList<>();
  }

  public List<Course> getCourses() { return courses;}

  public void setCourses(List<Course> list) {
    courses = list;
  }

  public void addCourse(Course course) {
    courses.add(course);
  }
}
