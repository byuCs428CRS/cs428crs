package database;

import exceptions.AccountAlreadyExistsException;
import exceptions.ResourceNotFoundException;
import models.*;
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
	private Map<String, UserCredentials> userLogins;
	private Map<Integer, UserCredentials> userList;
	private Map<String, Integer> idLookup;
  private Map<Integer, Schedule> schedules;
  private Map<Integer, Integer> scheduleUserMappings;
	private int userIdCount;
  private int scheduleIdCount;


	private MemoryRegistrationStore() {
		departments = new ArrayList<>();
		majors = new HashMap<>();
		userLogins = new HashMap<>();
		userList = new HashMap<>();
		idLookup = new HashMap<>();
    schedules = new HashMap<>();
    scheduleUserMappings = new HashMap<>();
		userIdCount = 1;
    scheduleIdCount = 1;
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
    if (m == null) {
      return new ArrayList<>();
    }
    return m.getRequirements();
  }

	@Override
	public Course getCourse(String courseId) {
		return null;
	}

	@Override
	public List<Course> getCoursesForDepartment(String departmentId) {
		return null;
	}

	@Override
	public List<Course> getAllCourses() {
		return null;
	}

  @Override
  public int addSchedule(int userId, Schedule schedule) {
    int id = scheduleIdCount++;
    schedules.put(id, schedule);
    scheduleUserMappings.put(id, userId);
    return id;
  }

  @Override
  public List<Schedule> getAllSchedules(int userId) {
    List<Schedule> listOfSchedules = new ArrayList<>();
    for (Map.Entry<Integer, Integer> e : scheduleUserMappings.entrySet()) {
      if (e.getValue() == userId) {
        Schedule s = schedules.get(e.getKey());
        s.setId("" + e.getKey());
        listOfSchedules.add(s);
      }
    }
    return listOfSchedules;
  }

  @Override
  public Schedule getSchedule(int scheduleId) {
    if (!schedules.containsKey(scheduleId)) {
      throw new ResourceNotFoundException("No schedule by id: " + scheduleId + " found.");
    }
    return schedules.get(scheduleId);
  }

  @Override
  public void editSchedule(int scheduleId, Schedule scheduleEdit) {
    if (!schedules.containsKey(scheduleId)) {
      throw new ResourceNotFoundException("No schedule by id: " + scheduleId + " found.");
    }
    schedules.put(scheduleId, scheduleEdit);
  }

  @Override
  public void removeSchedule(int scheduleId) {
    if (!schedules.containsKey(scheduleId)) {
      throw new ResourceNotFoundException("No schedule by id: " + scheduleId + " found.");
    }
    schedules.remove(scheduleId);
    scheduleUserMappings.remove(scheduleId);
  }

  @Override
  public int getOwningUserForSchedule(int scheduleId) {
    if (!schedules.containsKey(scheduleId)) {
      throw new ResourceNotFoundException("No schedule by id: " + scheduleId + " found.");
    }
    return scheduleUserMappings.get(scheduleId);
  }


  @Override
	public UserCredentials getCredentials(String username) {
		UserCredentials user = new UserCredentials();
		user.setUsername(username);
		user.setPass("xD");
		user.setSalt("fakeSalt");

		if (userLogins.containsKey(username)) {
			user = userLogins.get(username);
		}
		return user;
	}

	@Override
	public int addUser(UserCredentials user) throws AccountAlreadyExistsException {
		if (userLogins.containsKey(user.getUsername())) {
			throw new AccountAlreadyExistsException("Username already in use");
		}
		int id = userIdCount++;
		userLogins.put(user.getUsername(), user);
		userList.put(id, user);
		idLookup.put(user.getUsername(), id);
		return id;
	}

	public int getUserId(String username) {
		int i = idLookup.get(username);
		return i;
	}

}
