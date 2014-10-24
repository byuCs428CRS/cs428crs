package database;

import com.mongodb.*;
import exceptions.AccountAlreadyExistsException;
import models.*;
import models.requirements.Requirement;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Keith
 * Date: 3/7/14
 * Time: 4:19 PM
 */
public class DatabaseRegistrationStore implements RegistrationStore {
    private static RegistrationStore root = new DatabaseRegistrationStore();
    private static DB db;
    private final String COURSE_COLLECTION = "course";
    private final String USER_COLLECTION = "user";
    private final String SCHEDULE_COLLECTION = "schedule";
    private final String DEPARTMENT_COLLECTION = "department";

    public static RegistrationStore getInstance() {
        return root;
    }

    private DatabaseRegistrationStore() {
        db = getDB();
    }

	public static DB getDB() {

		String dbUser = "admin";
		String dbPassword = "ad428min";

		// Connect to our database
		MongoClientURI uri = new MongoClientURI("mongodb://" + dbUser + ":" + dbPassword +
				"@mongo.registerbyu.com/classreg");
		MongoClient client;
		try {
			client = new MongoClient(uri);
			DB db = client.getDB(uri.getDatabase());
			return db;
		} catch (UnknownHostException e) {

			System.out.println("COULDN\'T GET THE DB");
			e.printStackTrace();
		}
		return null;
	}

    //@Override
    public List<Course> getAllCourses() {
        List<Course> courseList = new ArrayList<>();
        DBCollection courseCollection = db.getCollection(COURSE_COLLECTION);
        for (DBObject dbObject : courseCollection.find()) {
            Course c = buildCourseObject(dbObject);
            //add course to the list
            courseList.add(c);
        }
        return courseList;
    }

    //@Override
    public List<Department> getAllDepartments() {

        List<Department> departmentList = new ArrayList<>();
        DBCollection courseCollection = db.getCollection(COURSE_COLLECTION);
        String dept = "HIST";
        System.out.println(courseCollection.getCount());
        for (String s : db.getCollectionNames()) {
            System.out.println(s);
        }
        return departmentList;
    }

    //@Override
    public void addDepartment(Department department) {
        BasicDBObject departmentObject = new BasicDBObject("title", department.getTitle())
                .append("short_code", department.getShortCode());

    }

    //@Override
    public List<Requirement> getRequirementsForMajor(String major) {
        List<Requirement> requirementList = new ArrayList<>();
        return requirementList;
    }

    //@Override
    public Course getCourse(String courseId) {
        Course course;
        BasicDBObject query = new BasicDBObject("courseID", courseId);
        DBObject response = db.getCollection(COURSE_COLLECTION).findOne(query);
        course = buildCourseObject(response);
        return course;
    }

    //@Override
    public List<Course> getCoursesForDepartment(String departmentId) {
        List<Course> courseList = new ArrayList<>();
        BasicDBObject query = new BasicDBObject("department", departmentId);
        for (DBObject dbObject : db.getCollection(COURSE_COLLECTION).find(query)) {
            courseList.add(buildCourseObject(dbObject));
        }

        return courseList;
    }

    //@Override
    public int addSchedule(int userId, Schedule schedule) {
        int successValue = 0;
        return successValue;
    }

    //@Override
    public List<Schedule> getAllSchedules(int userId) {
        List<Schedule> scheduleList = new ArrayList<>();
        return scheduleList;
    }

    //@Override
    public Schedule getSchedule(int scheduleId) {
        Schedule schedule = new Schedule();
        return schedule;
    }

    //@Override
    public void editSchedule(int scheduleId, Schedule scheduleEdit) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    //@Override
    public void removeSchedule(int scheduleId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    //@Override
    public int getOwningUserForSchedule(int scheduleId) {
        int userId = 0;
        return userId;
    }

    //@Override
    public UserCredentials getCredentials(String username) {
        DBCollection courseCollection = db.getCollection(COURSE_COLLECTION);
        UserCredentials credentials = new UserCredentials();
        return null;
    }

    //@Override
    public int addUser(UserCredentials user) throws AccountAlreadyExistsException {
        int successValue = 0;
        DBCollection userCollection = db.getCollection(USER_COLLECTION);
       BasicDBObject userObject = buildUserCredentialObject(user);
        return successValue;
    }

    //@Override
    public int getUserId(String username) {
        int userId = 0;
        return userId;
    }

    private Course buildCourseObject(DBObject courseObject){
        if(courseObject == null){
            return null;
        }
        Course course = new Course();

        //Set the fields here
        course.setCourseID(courseObject.get("courseID").toString());
        course.setCourseName(courseObject.get("courseName").toString());
        course.setCourseNumber(courseObject.get("courseNumber").toString());
	    Object dept = courseObject.get("department");
	    if( dept != null )
	        course.setDepartment(dept.toString());
        course.setDepartmentCode(courseObject.get("departmentCode").toString());
        course.setNewTitleCode(courseObject.get("newTitleCode").toString());
        course.setRegistrationType(courseObject.get("registrationType").toString());

        //SET THE OUTCOMES LIST
        BasicDBList outcomes = (BasicDBList) courseObject.get("outcomes");
        List<String> outcomeList = new ArrayList<String>();
        for(Object outcome : outcomes){
            outcomeList.add(outcome.toString());
        }
        course.setOutcomes(outcomeList);

        //SET THE SECTIONS LIST
        BasicDBList sections = (BasicDBList) courseObject.get("sections");
        List<Section> sectionList = new ArrayList<Section>();

        for (Object dboSection : sections) {
            Section s = new Section();
            BasicDBObject dbSection = (BasicDBObject) dboSection;

            s.setCourseID(courseObject.get("courseID").toString());
            s.setTotalSeats(dbSection.get("totalSeats").toString());
//            s.setCourseName(dbSection.get("courseName").toString());
            s.setCredits(dbSection.get("creditHours").toString());
            s.setProfessor(dbSection.get("professor").toString());
            s.setPid(dbSection.get("pid").toString());
            s.setSeatsAvailable(dbSection.get("seatsAvailable").toString());
            s.setSectionID(dbSection.get("sectionID").toString());
            s.setWaitList(dbSection.get("waitList").toString());

            //TODO - CHECK THESE SECTION VARS AND SEE IF THEY NEED TO BE FILLED OUT
//NOT sure                  s.setSectionType(       dbSection.get("sectionType").toString());
//Course                    s.setRegistrationType(  dbSection.get("registrationType").toString());
//Course                    s.setDepartment(        dbSection.get("department").toString());
//NOT sure                  s.setNotes(             dbSection.get("totalSeats").toString());
//Course                    s.setNewTitleCode(      dbSection.get("newTitleCode").toString());
//Course                    s.setCourseNumber(      courseObject.get("courseNumber").toString());


            //SET THE TIME_PLACE LIST
            BasicDBList timePlaces = (BasicDBList) dbSection.get("timePlaces");
            List<TimePlace> timePlaceList = new ArrayList<TimePlace>();
            for (Object dboTimePlace : timePlaces) {
                BasicDBObject dbTimePlace = (BasicDBObject) dboTimePlace;
                timePlaceList.add(buildTimePlaceObject(dbTimePlace));
            }
            s.setTimePlaces(timePlaceList);
            sectionList.add(s);
        }

        //add sections to course
        course.setSections(sectionList);
        return course;
    }

    private TimePlace buildTimePlaceObject(DBObject dbTimePlace){
        TimePlace tp = new TimePlace();
        tp.setDay(dbTimePlace.get("day").toString());
        tp.setEndTime(dbTimePlace.get("endTime").toString());
        tp.setLocation(dbTimePlace.get("location").toString());
        tp.setStartTime(dbTimePlace.get("startTime").toString());
        return tp;
    }

    private BasicDBObject buildUserCredentialObject(UserCredentials user){
        BasicDBObject userObject = new BasicDBObject();
        userObject.append("username",user.getUsername());
        userObject.append("password",user.getPass());
        userObject.append("salt",user.getSalt());
        return userObject;
    }
}
