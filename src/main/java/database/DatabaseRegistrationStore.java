package database;

import com.mongodb.*;
import exceptions.AccountAlreadyExistsException;
import models.Course;
import models.Department;
import models.Schedule;
import models.UserCredentials;
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
    private static DatabaseRegistrationStore root = new DatabaseRegistrationStore();
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

    @Override
    public List<Course> getAllCourses() {

        List<Course> courseList = new ArrayList<Course>();
        DBCollection courseCollection = db.getCollection("course");

        for(DBObject dbObject : courseCollection.find()){

            //System.out.println(dbObject.toString());
            Course c = new Course();

           /* TODO - These are the fields we need to set
            private String title;
            private String owningDepartment; //shortCode of owning department
            private String courseId;  //number of id does NOT include departments short code
            private String description;
            private float credits;
            private List<Section> sections;
            private List<String> fulfillments; //reqID's of Requirements it fulfills
            private List<String> prereqs; //courseId's of courses needed as a prereq
            */

            //Set the fields here
            c.setTitle(             dbObject.get("courseName").toString());
            c.setOwningDepartment(dbObject.get("department").toString());
            c.setCourseId(dbObject.get("courseNumber").toString());
            c.setTitleCode(dbObject.get("newTitleCode").toString());
            c.setByuId(             dbObject.get("courseID").toString());
            c.setDescription(       "NONE"); //TODO - Parse a file with Descriptions
          
            //write a getSection() method, then store them here


            courseList.add(c);
        }

        return courseList;
    }

    @Override
    public List<Department> getAllDepartments() {

        DBCollection courseCollection = db.getCollection("course");
        String dept = "HIST";

        System.out.println(courseCollection.getCount());


        for (String s : db.getCollectionNames()){
            System.out.println(s);
        }



        return null;
    }

    @Override
    public void addDepartment(Department department) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Requirement> getRequirementsForMajor(String major) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Course getCourse(String courseId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Course> getCoursesForDepartment(String departmentId) {
        return null;
    }

    @Override
    public int addSchedule(int userId, Schedule schedule) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Schedule> getAllSchedules(int userId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Schedule getSchedule(int scheduleId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void editSchedule(int scheduleId, Schedule scheduleEdit) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeSchedule(int scheduleId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getOwningUserForSchedule(int scheduleId) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public UserCredentials getCredentials(String username) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int addUser(UserCredentials user) throws AccountAlreadyExistsException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getUserId(String username) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }


}
