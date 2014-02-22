package database;

import exceptions.AccountAlreadyExistsException;
import models.Course;
import models.Department;
import models.Major;
import models.UserCredentials;

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
	private int userIdCount;


	private MemoryRegistrationStore() {
		departments = new ArrayList<>();
		majors = new HashMap<>();
		userLogins = new HashMap<>();
		userList = new HashMap<>();
		idLookup = new HashMap<>();
		userIdCount = 1;
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

//  @Override
//  public List<Requirement> getRequirementsForMajor(String major) {
//    Major m = majors.get(major);
//    if (m == null) {
//      return new ArrayList<>();
//    }
//    return m.getRequirements();
//  }

	@Override
	public Course getCourse(String courseId) {
		return null;
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
