package packages;

import models.Schedule;
import models.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Nick Humrich
 * @date: 3/5/14
 */
public class Schedules
{
	private List<Schedule> schedules;

	public Schedules()
	{
		schedules = new ArrayList<>();
	}

	public List<Schedule> getSchedules()
	{
		return schedules;
	}

	public void setSchedules(List<Schedule> list)
	{
		schedules = list;
	}

	public void addSchedule(Schedule schedule)
	{
		schedules.add(schedule);
	}

	public boolean contains(Section section)
	{
		for (Schedule schedule : schedules)
		{
			if(schedule.getClasses().contains(section))
			{
				return true;
			}
		}

		return false;
	}

	public int size()
	{
		return this.schedules.size();
	}

	public boolean isEmpty()
	{
		return this.schedules.isEmpty();
	}
	
	public Schedule getSemester(String semesterID)
	{
		for (Schedule s : schedules)
			if(s.getSemesterID().equals(semesterID))
				return s;
		
		return null;
		
	}

	public void setSemester(String semesterID, Schedule schedule)
	{
		for (int i = 0; i < schedules.size(); i++)
		{
			Schedule s = schedules.get(i);
			if (s.getSemesterID().equals(semesterID))
			{
				schedules.set(i, schedule);
				return;
			}
		}

		// This semester not yet added
		schedules.add(schedule);
	}
}
