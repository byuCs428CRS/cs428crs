package database;

import models.Course;
import org.junit.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Keith
 * Date: 3/7/14
 * Time: 4:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseRegistrationStoreTest {
    RegistrationStore data = DatabaseRegistrationStore.getInstance();

    public void setUp() throws Exception {


    }

    @Test
    public void testGetAllDepartments() throws Exception {
       // data.getAllDepartments();
    }

    @Test
    public void testGetAllCourses() throws Exception {
        List<Course> courses = data.getAllCourses();
        for(Course c : courses){
            System.out.println(c.toString());
        }

    }

    public void testAddDepartment() throws Exception {

    }

    public void testGetRequirementsForMajor() throws Exception {

    }

    public void testGetCourse() throws Exception {

    }

    public void testGetCoursesForDepartment() throws Exception {

    }



    public void testAddSchedule() throws Exception {

    }

    public void testGetAllSchedules() throws Exception {

    }

    public void testGetSchedule() throws Exception {

    }

    public void testEditSchedule() throws Exception {

    }

    public void testRemoveSchedule() throws Exception {

    }

    public void testGetOwningUserForSchedule() throws Exception {

    }

    public void testGetCredentials() throws Exception {

    }

    public void testAddUser() throws Exception {

    }

    public void testGetUserId() throws Exception {

    }
}
