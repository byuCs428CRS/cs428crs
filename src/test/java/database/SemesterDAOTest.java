package database;

import com.mongodb.DB;
import models.Course;
import models.Semester;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import packages.Courses;
import parser.catalog.CatalogParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SemesterDAOTest {
	private SemesterDAO dao;
	private final int testSemesterID = 20158;
	private final String[] credits = {"1", "2", "3", "3V", "9V"};

	@Before
	public void setUp()
	{
		// Start with a clean database before each test
		DB db = TestDatabase.getDB();
		MongoCollection collection = (new Jongo(db)).getCollection(SemesterDAO.getCollectionID());
		TestDatabase.dropCollection(collection);

		dao = new SemesterDAO(db);

	}

	@Test
	public void testGetFakeSemester() throws Exception
	{
		int semesterId = 20157;
		Semester s = null;
		try{
			s = dao.getSemester(semesterId);
			Assert.fail();
		}
		catch(Exception e){
			Assert.assertEquals("Semester is null", null, s);
		}
		Assert.assertEquals("Semester is null", null, s);
	}
	
	@Test
	public void saveFakeSemester() throws Exception
	{
		int semesterId = 20158;
		String fakeSemester = "Fake Semester";
		Semester s = new Semester();
		s.setID(semesterId);
		s.setName(fakeSemester);
		dao.saveSemester(s);
		Semester s2 = dao.getSemester(semesterId);
		Assert.assertEquals("Semester id is the same", s.getID(), s2.getID());
		Assert.assertEquals("Semester name is the same", s.getName(),s2.getName());
	}
	
	@Test
	public void getF2015Semester() throws Exception
	{
		int semesterId = 20155;
		Semester sem = new Semester();
		sem.setID(semesterId);
		dao.saveSemester(sem);

		Semester s = dao.getSemester(semesterId);
		Assert.assertEquals("Semester id is the same", s.getID(), semesterId);
	}

	@Test
	public void getCoursesByCreditTest()
	{
		Semester semester = getTestSemester();
		dao.saveSemester(semester);
		for (String credit : credits)
		{
			ArrayList<Course> courses = dao.getCoursesByCredit(credit, semester.getID());
			for(Course course : courses)
			{
				Assert.assertEquals(credit, course.getCredit());
			}
		}
	}



	private Semester getTestSemester()
	{
		Courses courses;

		try {
			courses = CatalogParser.parseCourses(new File("./TestCatalog.txt"));
		} catch (IOException e) {
			return null;
		}

		Semester semester = new Semester(testSemesterID);
		semester.setCourses(courses);
		return semester;
	}
}
