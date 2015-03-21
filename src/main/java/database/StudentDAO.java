package database;

import com.mongodb.DB;
import com.mongodb.WriteResult;
import exceptions.DatabaseException;
import models.Course;
import models.Schedule;
import models.Section;
import models.Student;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

/**
 * Created by sean on 2/17/15.
 */
public class StudentDAO implements IStudentDAO
{
    private DB Db;
    private MongoCollection students;
    private final String studentIdQuery = "{ _id: #}";
    private static String collectionID = "students";

    public StudentDAO(DB Db)
    {
        this.Db = Db;
        Jongo jongo = new Jongo(this.Db);
        this.students = jongo.getCollection(collectionID);
    }



    /**
     * Add one student to the database
     * @param student
     */
    @Override
    public void saveStudent(Student student)
    {
        WriteResult result = this.students.save(student);
        DBValidator.validate(result);
    }

    /**
     * Delete one student from the database
     * @param student
     */
    @Override
    public void deleteStudent(Student student)
    {
        WriteResult result = this.students.remove(studentIdQuery, student.getStudentId());
        DBValidator.validate(result);
    }

    /**
     * Add a section to the student in the database
     * @param section
     */
    @Override
    public void addSection(Section section, Student student)
    {
        student.addSection(section);
        saveStudent(student);
    }

    /**
     * Remove a section from the student in the database
     * @param section
     */
    @Override
    public void removeSection(Section section,Student student)
    {
        student.removeSection(section);
        saveStudent(student);
    }

    @Override
    public void saveSchedule(Schedule schedule, Student student)
    {
        student.setSchedule(schedule);
        saveStudent(student);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Student getStudent(String id)
    {
        Student student = this.students.findOne(studentIdQuery, id).as(Student.class);
        if(student == null)
        {
            throw new DatabaseException("Could not find student");
        }

        return student;
    }

    /**
     * Add a list of courses to the database
     * @param course
     */
    @Override
    public void addCourse(Course course, Student student)
    {
        student.addPlannedCourse(course);
        this.saveStudent(student);

    }

    @Override
    public void removeCourse(Course course, Student student)
    {
        student.removePlannedCourse(course);
        this.saveStudent(student);
    }

    public static String getCollectionID()
    {
        return collectionID;
    }

}
