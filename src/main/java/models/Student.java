package models;

import org.jongo.marshall.jackson.oid.Id;
import packages.Courses;
import packages.Schedules;
//import java.util.Map;

/**
 * @autor: Nick Humrich
 * @date: 1/17/14
 */
public class Student
{

	@Id
	private String studentId;
	private String firstName;
	private String lastName;
	private Schedules schedules;
	//private Map<Course, Grade> history;
	private Courses plannedCourses;

	public Student()
	{
		schedules = new Schedules();
		plannedCourses = new Courses();
	}

	public Student(String studentId)
	{
		this.studentId = studentId;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getStudentId()
	{
		return studentId;
	}

	public void setStudentId(String studentId)
	{
		this.studentId = studentId;
	}

	public Schedules getSchedules()
	{
		return schedules;
	}

	/**
	 * Gets the schedule for the given semesterID
	 * @param semesterID the index of the schedule
	 * @return
	 */
	public Schedule getSchedule(String semesterID)
	{
		return schedules.getSemester(semesterID);
	}

	public void setSchedules(Schedules schedules)
	{
		this.schedules = schedules;
	}

//  public Map<Course, Grade> getHistory() {
//    return history;
//  }
//
//  public void setHistory(Map<Course, Grade> history) {
//    this.history = history;
//  }

	public void setSchedule(Schedule schedule)
	{
		this.schedules.setSemester(schedule.getSemesterID(), schedule);
	}

	public Courses getPlannedCourses()
	{
		return plannedCourses;
	}

	/**
	 * Sets the plannedCourses to this courses object
	 * @param plannedCourses
	 */
	public void setPlannedCourses(Courses plannedCourses)
	{
		this.plannedCourses = plannedCourses;
	}

	/**
	 * Adds more courses to the planned courses
	 * @param courses
	 */
	public void addPlannedCourses(Courses courses)
	{
		if (this.plannedCourses == null)
		{
			this.plannedCourses = courses;
		}
		else
		{
			this.plannedCourses.addCourses(courses);
		}
	}

	public void removePlannedCourses(Courses courses)
	{
		this.plannedCourses.removeCourses(courses);
	}

	public void removePlannedCourse(Course course)
	{
		this.plannedCourses.removeCourse(course);
	}

	public void addPlannedCourse(Course course)
	{
		this.plannedCourses.addCourse(course);
	}


	/**
	 * Adds a section to the student
	 * @param section
	 */
	public void addSection(Section section)
	{
		// Check if any schedules exist
		if(this.schedules.isEmpty())
		{
			Schedule schedule = new Schedule(section.getSemesterID());
			schedule.addSection(section);
			this.schedules.addSchedule(schedule);
		}
		else
		{
			this.schedules.getSemester(section.getSemesterID()).addSection(section);
		}
	}

	/**
	 * removes a section from this student
	 * @param section
	 */
	public void removeSection(Section section)
	{
		if(section == null)
		{
			return;
		}

		Schedule schedule =this.schedules.getSemester(section.getSemesterID());
		if(schedule != null)
		{
			schedule.removeSection(section);
		}

	}

}
