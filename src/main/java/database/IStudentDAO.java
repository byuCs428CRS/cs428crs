package database;

import models.Course;
import models.Schedule;
import models.Section;
import models.Student;

public interface IStudentDAO {
    
    public void saveStudent(Student student);
    
    public void deleteStudent(Student student);
    
    public void addSection(Section section, Student student);
    
    public void removeSection(Section section, Student student);
    
    public Student getStudent(String id);
    
    public void addCourse(Course course, Student student);
    
    public void removeCourse(Course course,Student student);

    public void saveSchedule(Schedule schedule, Student student);
}