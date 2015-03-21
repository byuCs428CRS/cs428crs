package service;

import database.DatabaseRegistrationStore;
import database.IStudentDAO;
import database.StudentDAO;
import models.Student;

/**
 * @author: Nick Humrich
 * @date: 2/21/14
 */
public class AuthenticationService {
	private IStudentDAO studentDAO;

	public AuthenticationService() {
		studentDAO = new StudentDAO(DatabaseRegistrationStore.getDB());
	}


	public Student loginViaService(String id)
	{
		Student student = studentDAO.getStudent(id);
		if(student == null)
		{
			student = new Student(id);
			studentDAO.saveStudent(student);
		}

		return student;
	}

}
