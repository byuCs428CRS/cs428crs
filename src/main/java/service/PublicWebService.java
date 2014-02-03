package service;

import database.MemoryRegistrationStore;
import database.RegistrationStore;
import models.*;
import models.requirements.CountType;
import models.requirements.Requirement;
import packages.Courses;
import packages.Departments;
import packages.Requirements;
import packages.Sections;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Nick Humrich
 * @date: 1/18/14
 */
public class PublicWebService {
  private RegistrationStore registrationStore;

  public PublicWebService() {
    //ToDo: replace below to read from config and use a factory to choose the store
    registrationStore = MemoryRegistrationStore.getInstance();
  }

  public Departments getAllDepartments() {
    Departments departments = new Departments();
    List<Department> departmentList = registrationStore.getAllDepartments();
    departments.setDepartments(departmentList);

    return departments;
  }

  public String test() {
    return null;
  }

  public Requirements getRequirements(String major) {
    Requirements reqs = new Requirements();

    List<Requirement> requirementList = registrationStore.getRequirementsForMajor(major);
    reqs.setRequirements(requirementList);

    return reqs;
  }

  public Requirements getMockRequirements(String major) {
    return createMockRequirements(major);
  }

  public Departments getMockDepartments() {
    return createMockDepartments();
  }

  /*
  * ToDo
  * NOTE: These "create mock" functions should be moved to tests once the database is fully functional
  *
   */

  public Requirements createMockRequirements(String major) {
    Requirements reqs = new Requirements();
    if (major.equals("none")) {
      //add base requirement
      Requirement is = new Requirement();
      reqs.addRequirement(is);
      is.setId("geIS");
      is.setTitle("The Individual and Society");
      is.setCountType(CountType.COURSES);
      is.setRequiredCount(1.0f);

      //add sub requirements
      Requirement amHer = new Requirement();
      amHer.setTitle("American Heritage");
      amHer.setId("geIS1");
      amHer.setCountType(CountType.COURSES);
      amHer.setRequiredCount(1.0f);
      is.addChild(amHer);
      reqs.addRequirement(amHer);


      //add sub requirements
      Requirement am11 = new Requirement();
      am11.setId("geIS1.1");
      am11.setTitle("OPTION 1");
      am11.setCountType(CountType.COURSES);
      am11.setRequiredCount(1.0f);
      reqs.addRequirement(am11);
      amHer.addChild(am11);

      //add classes
      List<String> courseList = new ArrayList<>();
      courseList.add(createMockCourse("ahtg", "100").getFullId());
      courseList.add(createMockCourse("honrs", "240").getFullId());
      am11.setCourses(courseList);

      //add sub requirements
      Requirement am12 = new Requirement();
      am12.setId("geIS1.2");
      am12.setTitle("OPTION 2");
      am12.setRequiredCount(2.0f);
      am12.setCountType(CountType.COURSES);
      reqs.addRequirement(am12);

      //add classes
      courseList = new ArrayList<>();

      am12.setCourses(courseList);
      amHer.addChild(am12);
    }


    return reqs;
  }

  public Departments createMockDepartments() {
    Departments departments = new Departments();
    departments.addMajor(createMockDepartment("Computer Science", "CS"));
    departments.addMajor(createMockDepartment("English", "EN"));
    departments.addMajor(createMockDepartment("Mathematics", "MA"));

    return departments;
  }

  public Department createMockDepartment(String title, String abbreviation) {
    Department department = new Department(title, abbreviation);

    List<Course> courses = createMockCourses(abbreviation).getCourses();

    for (Course course : courses) {
      department.addCourse(course.getCourseId());
    }
    return department;
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
    Course course = new Course("TestCourse " + courseId, num);
    course.setOwningDepartment(majorAbbrev);
    course.setCredits(3.0f);
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
    section1.setLocation("RB 324");

    //section 2
    Section section2 = new Section(courseId, "2", 23);
    section2.setProfessor("Dr. Pear");
    List<Time> times2 = new ArrayList<>();
    times2.add(new Time(Day.MONDAY, "11:00", "11:50"));
    times2.add(new Time(Day.WEDNESDAY, "11:00", "11:50"));
    times2.add(new Time(Day.FRIDAY, "11:00", "11:50"));
    section2.setLocation("TMCB 1143");

    sections.addSection(section1);
    sections.addSection(section2);

    return sections;
  }
}
