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

    private String sectionID;
    private String sectionType;
    private String professor;

    private List<TimePlace> timePlaces;

    private String seatsAvailable;
    private String totalSeats;
    private String waitList;

    public Section(){

    }

    public String toString(){
        String output = "Section: " + sectionID + "\t" + sectionType + "\t" + professor + "\t";
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

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public List<TimePlace> getTimePlaces() {
        return timePlaces;
    }

    public void setTimePlaces(List<TimePlace> timePlaces) {
        this.timePlaces = timePlaces;
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
