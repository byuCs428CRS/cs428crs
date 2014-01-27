package service;

import database.MemoryRegistrationStore;
import database.RegistrationStore;
import models.*;
import models.requirements.CountRequirementList;
import models.requirements.OrRequirementList;
import models.requirements.Requirement;
import models.requirements.RequirementList;
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
      is.setReqId("geIS");
      is.setTitle("The Individual and Society");
      List<RequirementList> reqList = new ArrayList<>();
      is.setFulfillments(reqList);

        //add sub requirements
        OrRequirementList amHer = new OrRequirementList();
        amHer.setTitle("American Heritage");
        amHer.setId("geIS1");
        reqList.add(amHer);
        List<Object> optionsList = new ArrayList<>();
        amHer.setFulfillments(optionsList);


          //add sub requirements
          OrRequirementList am11 = new OrRequirementList();
          am11.setId("geIS1.1");
          am11.setTitle("OPTION 1");

            //add classes
            List<Object> courseList = new ArrayList<>();
            courseList.add(createMockCourse("ahtg", "100"));
            courseList.add(createMockCourse("honrs", "240"));
          am11.setFulfillments(courseList);
          optionsList.add(am11);

          //add sub requirements
          CountRequirementList am12 = new CountRequirementList();
          am12.setId("geIS1.2");
          am12.setTitle("OPTION 2");

            //add classes
            courseList = new ArrayList<>();

          am12.setFulfillments(courseList);
          optionsList.add(am12);

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

    department.setCourses(createMockCourses(abbreviation).getCourses());

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
