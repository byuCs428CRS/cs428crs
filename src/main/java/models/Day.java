package models;

/**
 * @author: Nick Humrich
 * @date: 1/17/14
 */
public enum Day {
  SUNDAY("Sunday"),
  MONDAY("Monday"),
  TUESDAY("Tuesday"),
  WEDNESDAY("Wednesday"),
  THURSDAY("Thursday"),
  FRIDAY("Friday"),
  SATURDAY("Saturday");

  private String name;
  Day(String s) {
    this.name = s;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
