package packages;

import models.Schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Nick Humrich
 * @date: 3/5/14
 */
public class Schedules {
  private List<Schedule> schedules;

  public Schedules() {
    schedules = new ArrayList<>();
  }

  public List<Schedule> getSchedules() {
    return schedules;
  }

  public void setSchedules(List<Schedule> list) {
    schedules = list;
  }

  public void addSchedule(Schedule schedule) {
    schedules.add(schedule);
  }

}
