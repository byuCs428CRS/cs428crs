package service;

import models.*;
import packages.Courses;
import packages.Majors;
import packages.Sections;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Nick Humrich
 * @date: 1/18/14
 */
public class PublicWebService {

  public PublicWebService() {

  }

  public Majors getAllMajors() {
    //ToDo: Fix once database is functional
    return createMockMajors();
  }

  /*
  * ToDo
  * NOTE: These create mock functions should be moved to tests once the database is fully functional
  *
   */
  public Majors createMockMajors() {
    Majors majors = new Majors();
    majors.addMajor(createMockMajor("Computer Science", "CS"));
    majors.addMajor(createMockMajor("English", "EN"));
    majors.addMajor(createMockMajor("Mathematics", "MA"));

    return majors;
  }

  public Major createMockMajor(String title, String abbreviation) {
    Major major = new Major(title, abbreviation);

    major.setCourses(createMockCourses(abbreviation).getCourses());

    return major;
  }

  public Courses createMockCourses(String majorAbbrev) {
    Courses courses = new Courses();

    courses.addCourse(createMockCourse(majorAbbrev, "101"));
    courses.addCourse(createMockCourse(majorAbbrev, "140"));
    courses.addCourse(createMockCourse(majorAbbrev, "256"));

    return courses;
  }

  public Course createMockCourse(String majorAbbrev, String num) {
    String courseId = majorAbbrev + num;
    Course course = new Course("TestCourse " + courseId, courseId);
    course.setDescription("Test Course " + num + " for " + majorAbbrev);

    course.setSections(createMockSections(courseId).getSections());
    return course;
  }

  public Sections createMockSections(String courseId) {
    Sections sections = new Sections();

    //section 1
    Section section1 = new Section(courseId, "1", 30);
    section1.setProfessor("Dr. Apple");
    List<Time> times1 = new ArrayList<>();
    times1.add(new Time(Day.TUESDAY, "2:00", "3:15"));
    times1.add(new Time(Day.THURSDAY, "2:00", "3:15"));
    section1.setTimes(times1);

    //section 2
    Section section2 = new Section(courseId, "2", 23);
    section2.setProfessor("Dr. Pear");
    List<Time> times2 = new ArrayList<>();
    times2.add(new Time(Day.MONDAY, "11:00", "11:50"));
    times2.add(new Time(Day.WEDNESDAY, "11:00", "11:50"));
    times2.add(new Time(Day.FRIDAY, "11:00", "11:50"));

    sections.addSection(section1);
    sections.addSection(section2);

    return sections;
  }
}
