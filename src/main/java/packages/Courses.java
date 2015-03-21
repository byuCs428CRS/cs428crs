package packages;

import models.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Nick Humrich
 * @date: 1/18/14
 */
public class Courses
{
	private List<Course> courses;

	public Courses()
	{
		courses = new ArrayList<>();
	}

	public Courses(List<Course> courses)
	{
		this.courses = courses;
	}

	public List<Course> getCourses()
	{
		return courses;
	}

	public void setCourses(List<Course> list)
	{
		courses = list;
	}

	public void addCourse(Course course)
	{
		courses.add(course);
	}

	public void addCourses(Courses courses)
	{
		this.courses.addAll(courses.getCourses());
	}

	public void removeCourses(Courses courses)
	{
		this.courses.removeAll(courses.getCourses());
	}

	public void removeCourse(Course course)
	{
		this.courses.remove(course);
	}

	public boolean contains(Course course)
	{
		return this.courses.contains(course);
	}

	public int size()
	{
		return courses.size();
	}
}
