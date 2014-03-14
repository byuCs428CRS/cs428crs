package database;

import com.mongodb.*;
import exceptions.AccountAlreadyExistsException;
import models.Department;
import models.Schedule;
import models.UserCredentials;
import models.Course;
import models.Section;
import models.TimePlace;
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
public class DatabaseRegistrationStore implements RegistrationStore{
    private static RegistrationStore root = new DatabaseRegistrationStore();
    private static DB db;

    public static RegistrationStore getInstance() {
        return root;
    }

    private DatabaseRegistrationStore() {
        db = getDB();
    }

    private static DB getDB() {

        String dbUser = "classreg428";
        String dbPassword = "ad428min";

        // Connect to our database
        MongoClientURI uri = new MongoClientURI("mongodb://" + dbUser + ":" + dbPassword +
                "@ds063297.mongolab.com:63297/classreg");
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

        List<Course> courseList = new ArrayList<Course>();
        DBCollection courseCollection = db.getCollection("course");

        for(DBObject dbObject : courseCollection.find()){
            Course c = new Course();

            //Set the fields here
            c.setCourseID(          dbObject.get("courseID").toString());
            c.setCourseName(        dbObject.get("courseName").toString());
            c.setCourseNumber(      dbObject.get("courseNumber").toString());
            c.setDepartment(        dbObject.get("department").toString());
            c.setNewTitleCode(      dbObject.get("newTitleCode").toString());
            c.setRegistrationType(  dbObject.get("registrationType").toString());

            //SET THE SECTIONS LIST
            BasicDBList sections = (BasicDBList) dbObject.get("sections");
            List<Section> sectionList = new ArrayList<Section>();

            for(Object dboSection : sections){
                Section s = new Section();
                BasicDBObject dbSection = (BasicDBObject) dboSection;

                s.setCourseID(          dbObject.get("courseID").toString());
                s.setTotalSeats(        dbSection.get("totalSeats").toString());
                s.setCourseName(        dbSection.get("courseName").toString());
                s.setCreditHours(       dbSection.get("creditHours").toString());
                s.setProfessor(         dbSection.get("professor").toString());
                s.setSeatsAvailable(    dbSection.get("seatsAvailable").toString());
                s.setSectionID(         dbSection.get("sectionID").toString());
                s.setWaitList(          dbSection.get("waitList").toString());

                //TODO - CHECK THESE SECTION VARS AND SEE IF THEY NEED TO BE FILLED OUT
//NOT sure                  s.setSectionType(       dbSection.get("sectionType").toString());
//Course                    s.setRegistrationType(  dbSection.get("registrationType").toString());
//Course                    s.setDepartment(        dbSection.get("department").toString());
//NOT sure                  s.setNotes(             dbSection.get("totalSeats").toString());
//Course                    s.setNewTitleCode(      dbSection.get("newTitleCode").toString());
//Course                    s.setCourseNumber(      dbObject.get("courseNumber").toString());



                //SET THE TIME_PLACE LIST
                BasicDBList timePlaces = (BasicDBList) dbSection.get("timePlaces");
                List<TimePlace> timePlaceList = new ArrayList<TimePlace>();

                for(Object dboTimePlace : timePlaces){
                    BasicDBObject dbTimePlace = (BasicDBObject) dboTimePlace;
                    TimePlace tp = new TimePlace();
                    tp.setDay(          dbTimePlace.get("day").toString());
                    tp.setEndTime(      dbTimePlace.get("endTime").toString());
                    tp.setLocation(     dbTimePlace.get("location").toString());
                    tp.setStartTime(    dbTimePlace.get("startTime").toString());

                    timePlaceList.add(tp);
                }
                s.setTimePlaces(timePlaceList);

                sectionList.add(s);
            }

            //add sections to course
            c.setSections(sectionList);

            //add course to the list
            courseList.add(c);
        }

        return courseList;
    }

    //@Override
    public List<Department> getAllDepartments() {

        DBCollection courseCollection = db.getCollection("course");
        String dept = "HIST";

        System.out.println(courseCollection.getCount());


        for (String s : db.getCollectionNames()){
            System.out.println(s);
        }



        return null;
    }

    //@Override
    public void addDepartment(Department department) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    //@Override
    public List<Requirement> getRequirementsForMajor(String major) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    //@Override
    public Course getCourse(String courseId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    //@Override
    public List<Course> getCoursesForDepartment(String departmentId) {
        return null;
    }

    //@Override
    public int addSchedule(int userId, Schedule schedule) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    //@Override
    public List<Schedule> getAllSchedules(int userId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    //@Override
    public Schedule getSchedule(int scheduleId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    //@Override
    public UserCredentials getCredentials(String username) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    //@Override
    public int addUser(UserCredentials user) throws AccountAlreadyExistsException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    //@Override
    public int getUserId(String username) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }


}
