package models;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Keith
 * Date: 3/14/14
 * Time: 3:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class Section {
    private String courseID;
    private String newTitleCode;
    private String department;
    private String registrationType;
    private String courseNumber;

    private String sectionID;
    private String sectionType;
    private String courseName;
    private String professor;
    private String creditHours;

    private List<String> days;
    private List<String> startTimes;
    private List<String> endTimes;
    private List<String> locations;
    private List<TimePlace> timePlaces;

    private List<String> notes;
    private String seatsAvailable;
    private String totalSeats;
    private String waitList;

    public Section(){

    }

    public String toString(){
        String output = "Section: " + sectionID + "\t" + sectionType + "\t" + courseName + "\t" + professor + "\t" + creditHours;
        for(TimePlace t : timePlaces){
            output += "\n\t\t\t" + t.toString();
        }

        return output;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getNewTitleCode() {
        return newTitleCode;
    }

    public void setNewTitleCode(String newTitleCode) {
        this.newTitleCode = newTitleCode;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public String getSectionType() {
        return sectionType;
    }

    public void setSectionType(String sectionType) {
        this.sectionType = sectionType;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(String creditHours) {
        this.creditHours = creditHours;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public List<String> getStartTimes() {
        return startTimes;
    }

    public void setStartTimes(List<String> startTimes) {
        this.startTimes = startTimes;
    }

    public List<String> getEndTimes() {
        return endTimes;
    }

    public void setEndTimes(List<String> endTimes) {
        this.endTimes = endTimes;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public List<TimePlace> getTimePlaces() {
        return timePlaces;
    }

    public void setTimePlaces(List<TimePlace> timePlaces) {
        this.timePlaces = timePlaces;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public String getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(String seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public String getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(String totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getWaitList() {
        return waitList;
    }

    public void setWaitList(String waitList) {
        this.waitList = waitList;
    }
}
