package database;

import models.*;
import packages.Courses;

import java.util.*;

public interface ISemesterDAO {
    
    public void saveSemester(Semester semester);
    
    public void deleteSemester(Semester semester);
    
    public Semester getSemester(int sem_id);

    public void addCourses(Courses courses, int semID);

    public ArrayList<Semester> getSetOfSemester();
    
    public ArrayList<Course> getCoursesByCredit(String credit, int semID);
    
    //public ArrayList<Course> getCoursesByProfessor(Professor professor); // We don't have a model for professor
    
    public ArrayList<Course> getCoursesByTimeOfDay(TimePlace timeplace, int semID);

    //public void addSection(Course course,Section section);

    //public void removeSection(Section section);
}