package models;

/**
 * @autor: Nick Humrich
 * @date: 1/17/14
 */
public class Time {

  private Day day;
  private String startTime;
  private String endTime;

  public Time(Day day, String startTime, String endTime) {
    this.day = day;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public Day getDay() {
    return day;
  }

  public void setDay(Day day) {
    this.day = day;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }
}
