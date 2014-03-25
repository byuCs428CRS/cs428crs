package service;

import database.DatabaseRegistrationStore;
import database.MemoryRegistrationStore;
import database.RegistrationStore;
import exceptions.ForbiddenException;
import models.*;
import models.requirements.CountType;
import models.requirements.Requirement;
import packages.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Nick Humrich
 * @date: 1/18/14
 */
public class PublicWebService {
  private RegistrationStore tempRegistrationStore;
  private RegistrationStore registrationStore;

  public PublicWebService() {
    //ToDo: replace below to read from config and use a factory to choose the store
    tempRegistrationStore = MemoryRegistrationStore.getInstance();
    registrationStore = DatabaseRegistrationStore.getInstance();
  }

  public Courses getAllCourses() {
    String semester = getCurrentSemester();
    return getAllCourses(semester);
  }

  public Courses getAllCourses(String semester) {
    Courses courses = new Courses();
    //ToDo: edit below to reflect semester
    List<Course> list = registrationStore.getAllCourses();
    courses.setCourses(list);
    return courses;
  }

  public String getCurrentSemester() {
    return "Spring2014"; //ToDo: change this
  }

    public void handleRegistration(String courseInfo, String ticket) {
        HttpsURLConnection connection = null;
        try {
            URL url = new URL("https://gamma.byu.edu/ry/ae/prod/registration/cgi/regOfferings.cgi");
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("BYU-Web-Session", courseInfo);
            int b = courseInfo.getBytes().length;
            connection.setRequestProperty("Content-Length", "" + b);

            DataOutputStream wr = new DataOutputStream (connection.getOutputStream());

            wr.writeBytes(courseInfo);
            wr.flush();
            wr.close();

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            //return response.toString();



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  public Schedules getAllSchedulesForUser(String uid) {
    Schedules schedules = new Schedules();
    int userId = AuthenticationService.decodeId(uid);

    List<Schedule> list = tempRegistrationStore.getAllSchedules(userId);
    for (Schedule s : list) {
      s.setId(AuthenticationService.encodeId( Integer.parseInt(s.getId())));
    }

    schedules.setSchedules(list);
    return schedules;
  }


  public Schedule getSchedule(String uid, String sid) {
    int scheduleId = AuthenticationService.decodeId(sid);
    int userId = AuthenticationService.decodeId(uid);

    int ownerId = tempRegistrationStore.getOwningUserForSchedule(scheduleId);

    if (ownerId != userId) {
      throw new ForbiddenException("Not allowed to retrieve this schedule");
    }
    Schedule schedule = tempRegistrationStore.getSchedule(scheduleId);
    schedule.setId(sid);
    return schedule;
  }

  public void addSchedule(String uid, Schedule schedule) {
    int userId = AuthenticationService.decodeId(uid);

    tempRegistrationStore.addSchedule(userId, schedule);
  }

  public void editSchedule(String uid, String sid, Schedule schedule) {
    int userId = AuthenticationService.decodeId(uid);
    int scheduleId = AuthenticationService.decodeId(sid);

    int ownerId = tempRegistrationStore.getOwningUserForSchedule(scheduleId);

    if (ownerId != userId) {
      throw new ForbiddenException("Not allowed to edit this schedule");
    }

    tempRegistrationStore.editSchedule(scheduleId, schedule);
  }

  public void removeSchedule(String uid, String sid) {
    int userId = AuthenticationService.decodeId(uid);
    int scheduleId = AuthenticationService.decodeId(sid);

    int ownerId = tempRegistrationStore.getOwningUserForSchedule(scheduleId);

    if (ownerId != userId) {
      throw new ForbiddenException("Not allowed to remove this schedule");
    }

    tempRegistrationStore.removeSchedule(scheduleId);

  }

  public Departments getAllDepartments() {
    Departments departments = new Departments();
    List<Department> departmentList = tempRegistrationStore.getAllDepartments();
    departments.setDepartments(departmentList);

    return departments;
  }

  public String test() {
    return null;
  }

  public Requirements getRequirements(String major) {
    Requirements reqs = new Requirements();

//    List<Requirement> requirementList = tempRegistrationStore.getRequirementsForMajor(major);
//    reqs.setRequirements(requirementList);
//
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
//      courseList.add(createMockCourse("ahtg", "100").getFullId());
//      courseList.add(createMockCourse("honrs", "240").getFullId());
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
      //department.addCourse(course.getCourseId());
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
//    Course course = new Course("TestCourse " + courseId, num);
//    course.setDepartment(majorAbbrev);
 //   course.setCredits(3.0f);
 //   course.setDescription("Test Course " + num + " for " + majorAbbrev);

 //   course.setSections(createMockSections(courseId).getSections());
//    return course;
      return null;
  }

 /* public Sections createMockSections(String courseId) {
    Sections sections = new Sections();

    //section 1
    Section section1 = new Section(courseId, "1", 30);
    section1.setProfessor("Dr. Apple");
//    List<Time> times1 = new ArrayList<>();
//    times1.add(new Time(Day.TUESDAY, "2:00", "3:15"));
//    times1.add(new Time(Day.THURSDAY, "2:00", "3:15"));
//    section1.setTimes(times1);
 //   section1.setLocation("RB 324");

    //section 2
    Section section2 = new Section(courseId, "2", 23);
    section2.setProfessor("Dr. Pear");
//    List<Time> times2 = new ArrayList<>();
//    times2.add(new Time(Day.MONDAY, "11:00", "11:50"));
  //  times2.add(new Time(Day.WEDNESDAY, "11:00", "11:50"));
 //   times2.add(new Time(Day.FRIDAY, "11:00", "11:50"));
 //   section2.setLocation("TMCB 1143");

    sections.addSection(section1);
    sections.addSection(section2);

    return sections;
  }
  */
}
