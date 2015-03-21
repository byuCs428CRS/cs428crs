package models;

import org.jongo.marshall.jackson.oid.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @autor: Nick Humrich
 * @date: 1/17/14
 */
public class Schedule
{
    @Id
	private String id;
	private String name;
	private String semesterID;
	private List<Section> classes;


	public Schedule(String semesterID)
	{
		this();
		this.semesterID = semesterID;

	}

	public Schedule(){
		this.classes = new ArrayList<>();
	}

	public String getName()
	{
		return name;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getId()
	{
		return id;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<Section> getClasses()
	{
		return classes;
	}

	public void setClasses(List<Section> classes)
	{
		this.classes = classes;
	}

	public String getSemesterID()
	{
		return semesterID;
	}

	public void setSemesterID(String semesterID)
	{
		this.semesterID = semesterID;
	}

	public void addSection(Section section)
	{
		this.classes.add(section);
	}

	public void removeSection(Section section)
	{
		this.classes.remove(section);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(id, name, semesterID, classes);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null || getClass() != obj.getClass())
		{
			return false;
		}
		final Schedule other = (Schedule) obj;
		return Objects.equals(this.id, other.id)
				&& Objects.equals(this.name, other.name)
				&& Objects.equals(this.semesterID, other.semesterID)
				&& Objects.equals(this.classes, other.classes);
	}
}
