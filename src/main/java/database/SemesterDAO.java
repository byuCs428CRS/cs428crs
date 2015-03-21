package database;

import com.mongodb.DB;
import com.mongodb.WriteResult;
import exceptions.DatabaseException;
import models.Course;
import models.Semester;
import models.TimePlace;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import packages.Courses;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sean on 2/17/15.
 */
public class SemesterDAO implements ISemesterDAO
{
    private DB db;
    //private DBCollection collection;
    private final String semesterIDQuery = "{ _id : #}";
    private MongoCollection semesters;
    private static final String collectionID = "semester";

    public SemesterDAO(DB db){
        this.db = db;
        Jongo jongo = new Jongo(this.db);
        semesters = jongo.getCollection(collectionID);
        //collection = db.getCollection("semester");
    }

    @Override
    public void saveSemester(Semester semester)
    {
        WriteResult result = semesters.save(semester);
        DBValidator.validate(result);
    }

    @Override
    public void deleteSemester(Semester semester)
    {
        WriteResult result = this.semesters.remove(semesterIDQuery, semester.getID());
        try{
        	DBValidator.validate(result);
        }
        catch(Exception e){
        	System.out.println("Failed to delete semester from database, it may not exist. "+e.getMessage());
        }
    }

    @Override
    public Semester getSemester(int semID)
    {
        Semester semester = semesters.findOne(semesterIDQuery, semID).as(Semester.class);
        if(semester == null)
        {
            throw new DatabaseException("Semester not found");
        }
        return semester;
    }

    @Override
    public void addCourses(Courses course, int semID)
    {
        Semester semester = semesters.findOne(semesterIDQuery, semID).as(Semester.class);
        semester.addCourses(course);
        this.saveSemester(semester);
    }


    public void removeCourse(Course course, int semID)
    {
        Semester semester = semesters.findOne(semesterIDQuery, semID).as(Semester.class);
        semester.removeCourse(course);
        this.saveSemester(semester);
    }

    @Override
    public ArrayList<Semester> getSetOfSemester()
    {
        ArrayList<Semester> allSemesters = new ArrayList<Semester>();
        for (int n = 0; n < semesters.count(); n++)
        {
            allSemesters.add(getSemester(n));
        }
        return allSemesters;
    }

    @Override
    public ArrayList<Course> getCoursesByCredit(String credit, int semID)
    {
        Semester semester = semesters.findOne(semesterIDQuery, semID).as(Semester.class);
        Courses courses = semester.getCourses();
        List<Course> allCourses = courses.getCourses();

        ArrayList<Course> matchedCourses = new ArrayList<Course>();
        for(int n = 0; n < courses.size(); n++)
        {
            if(allCourses.get(n).getCredit().equals(credit))
            {
                matchedCourses.add(allCourses.get(n));
            }
        }
        return matchedCourses;
    }

    @Override
    public ArrayList<Course> getCoursesByTimeOfDay(TimePlace timeplace, int semID)
    {
        Semester semester = semesters.findOne(semesterIDQuery, semID).as(Semester.class);
        Courses courses = semester.getCourses();
        List<Course> allCourses = courses.getCourses();

        ArrayList<Course> matchedCourses = new ArrayList<Course>();
        for(int n = 0; n < courses.size(); n++)
        {
            //Section has TimePlace, discuss if Section or this feature is required?
            /*if(allCourses.get(n).getTimePlace() == credit)
            {
                matchedCourses.add(allCourses.get(n));
            }*/
        }
        return matchedCourses;
    }

    public static String getCollectionID()
    {
        return collectionID;
    }

}
