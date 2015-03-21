package models;

import packages.Courses;
import org.jongo.marshall.jackson.oid.Id;

/**
 * @author: Nick Humrich
 * @date: 1/17/14
 */
public class Semester
{

	private String name;

    @Id
    private int id;
	private Courses courses;

	public Semester(){}

	public Semester(int semesterID)
	{
		this.id = semesterID;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getID()
	{
		return id;
	}

	public void setID(int id)
	{
		this.id = id;
	}

	public Courses getCourses()
	{
		return courses;
	}

	public void setCourses(Courses courses)
	{
		this.courses = courses;
	}

    public void addCourses(Courses courses)
    {
        if (this.courses == null)
        {
            this.courses = courses;
        }
        else
        {
            this.courses.addCourses(courses);
        }
    }

    public void removeCourse(Course course)
    {
        this.courses.removeCourse(course);
    }
}
