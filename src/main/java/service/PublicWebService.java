package service;

import database.*;
import models.Schedule;
import models.Student;
import packages.Courses;
import packages.Departments;
import packages.Schedules;

/**
 * @author: Nick Humrich
 * @date: 1/18/14
 */
public class PublicWebService
{
	private RegistrationStore tempRegistrationStore;
	private RegistrationStore registrationStore;
	private ISemesterDAO semesterDAO;
	private IStudentDAO studentDAO;

	public PublicWebService()
	{
		//ToDo: replace below to read from config and use a factory to choose the store
		semesterDAO = new SemesterDAO(DatabaseRegistrationStore.getDB());
		studentDAO = new StudentDAO(DatabaseRegistrationStore.getDB());
	}

	public Courses getCourses(String sem_id)
	{
		int semesterID = Integer.parseInt(sem_id);
		try{
			return semesterDAO.getSemester(semesterID).getCourses();
		}catch(Exception e){
			//semester does not exist
			return new Courses();//empty set
		}
	}

	public Schedules getAllSchedulesForUser(String uid)
	{
		Student student = studentDAO.getStudent(uid);
		return student.getSchedules();
	}


	public Schedule getSchedule(String uid, String sid)
	{
		return studentDAO.getStudent(uid).getSchedules().getSemester(sid);
	}

	public void saveSchedule(String uid, Schedule schedule)
	{
        Student student = studentDAO.getStudent(uid);
        studentDAO.saveSchedule(schedule, student);
	}


	public Departments getAllDepartments()
	{
		return null;
	}


}
