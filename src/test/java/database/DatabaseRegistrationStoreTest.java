package database;

import models.Course;
import models.Department;
import models.Section;
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
        List<Department> departments = data.getAllDepartments();
        for (Department dept : departments) {
            System.out.println(dept.toString());
        }
    }

    @Test
    public void testGetAllCourses() throws Exception {
        List<Course> courses = data.getAllCourses();
        for (Course c : courses) {
            System.out.println(c.toString());
            List<Section> sections = c.getSections();
            for (Section s : sections) {
                System.out.println("\t\t" + s.toString());
            }
        }
    }

    @Test
    public void testAddDepartment() throws Exception {

    }

    @Test
    public void testGetRequirementsForMajor() throws Exception {

    }

    @Test
    public void testGetCourse() throws Exception {
        String[] courseIds = {"06527", "11784"};
        for (String id : courseIds) {
            Course course = data.getCourse(id);
            System.out.println(course.toString());
        }
    }

    @Test
    public void testGetCoursesForDepartment() throws Exception {
        String[] deptartments = {"C S", "HIST", "MMBIO"};
        for (String id : deptartments) {
            System.out.println(data.getCoursesForDepartment(id));
        }
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
