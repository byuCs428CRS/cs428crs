package catalogData;

import database.DatabaseRegistrationStore;
import database.RegistrationStore;
import models.Course;
import models.Section;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Keith
 * Date: 3/15/14
 * Time: 8:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class CourseRequests {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

        String fileName = "CourseData.txt";
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");


        RegistrationStore data = DatabaseRegistrationStore.getInstance();
        List<Course> courses = data.getAllCourses();

        System.out.println("Obtained all courses");

        for(Course c : courses){
            for(Section s : c.getSections())  {

                httpRequest.getCourseData(writer, s, c);

            }

        }


        writer.close();
        httpRequest.removeHtml(fileName);





    }
}
