package database;

import exceptions.AccountAlreadyExistsException;
import models.Course;
import models.Department;
import models.Schedule;
import models.UserCredentials;
import models.requirements.Requirement;

import java.util.List;

/**
 * @author: Nick Humrich
 * @date: 1/18/14
 */
public interface RegistrationStore {
  /**
   * Gets a list of all departments. Departments do NOT contain courses. Just
   * all meta data about each department
   * @return A list of departments
   */
	public List<Department> getAllDepartments();

  /**
   * We can un-deprecate this is the parser uses this method.
   * @param department
   */
	@Deprecated
	public void addDepartment(Department department);

  /**
   * RE
   * Requirements are no longer a part of our scope
   */
	@Deprecated
	public List<Requirement> getRequirementsForMajor(String major);

  /*
        ****   COURSES CODE
   */

  /**
   *  Gets a specific course based on id
   * @param courseId id of course
   * @return course
   */
	public Course getCourse(String courseId);

  /**
   * Returns a list of courses which are "owned" by the given department
   * @param departmentId id of department
   * @return list of courses
   */
	public List<Course> getCoursesForDepartment(String departmentId);

  /**
   * Returns a fully embedded list of all courses in the system.
   * @return
   */
	public List<Course> getAllCourses();

  /*
         *****   SCHEDULE STUFF
         * All schedules are associated to a user by a userId.
         * Lookup should be possible by schedule id as well as userId
   */
  /**
   * Adds a schedule for the given user.
   * @param userId userId of user
   * @param schedule schedule to save
   * @return unique id of schedule
   */
	public int addSchedule(int userId, Schedule schedule);

  /**
   * Returns a list of all schedules associated with the given user
   * @param userId userId of user
   * @return list of schedules
   */
	public List<Schedule> getAllSchedules(int userId);

  /**
   * Returns the schedule for the given schedule id.
   * @param scheduleId id of schedule requested
   * @return Schedule
   */
  public Schedule getSchedule(int scheduleId);

  /**
   * Updates the schedule in the database with the new schedule information
   * @param scheduleId id of the schedule being updated
   * @param scheduleEdit new schedule edits
   */
  public void editSchedule(int scheduleId, Schedule scheduleEdit);

  /**
   * Removes the schedule from the database.
   * @param scheduleId id of schedule to be removed
   */
  public void removeSchedule(int scheduleId);

  /**
   * Gives the userId of the user who owns the schedule
   * @param scheduleId id of schedule requested
   * @return userId of owning user
   */
  public int getOwningUserForSchedule(int scheduleId);


  /*
           ****    Authentication Code
   */

	/**
	 * Gets the credentials for a given username. If username is invalid, dummy credentials may be returned.
	 * Username, Pass, and Salt should be returned. No exception for account not found should be returned.
	 * @param username username
	 * @return user credentials
	 */
	public UserCredentials getCredentials(String username);

	/**
	 * Registers a user. The user credentials are added to the database. The username, pass, and salt
	 * should be stored. The pepper should not be stored.
	 * @param user user credentials
	 * @throws exceptions.AccountAlreadyExistsException if username is already taken
	 */
	public int addUser(UserCredentials user) throws AccountAlreadyExistsException;

	/**
	 * Returns the id for the given username
	 * @param username
	 * @return id
	 */
	public int getUserId(String username);


}
