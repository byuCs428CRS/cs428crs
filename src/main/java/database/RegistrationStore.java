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

	public List<Department> getAllDepartments();

	@Deprecated
	public void addDepartment(Department department);

	@Deprecated
	public List<Requirement> getRequirementsForMajor(String major);

	public Course getCourse(String courseId);

	public List<Course> getCoursesForDepartment(String departmentId);

	public List<Course> getAllCourses();

	public int addSchedule(int userId, Schedule schedule);

	public List<Schedule> getAllSchedules(int userId);

  public Schedule getSchedule(int scheduleId);

  public void editSchedule(int scheduleId, Schedule scheduleEdit);

  public void removeSchedule(int scheduleId);

  public int getOwningUserForSchedule(int scheduleId);

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
