package models;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Keith
 * Date: 3/14/14
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */

public class Course {
    private String courseID;
    private String courseName;
    private String newTitleCode;
    private String department;
    private String departmentCode;
    private String registrationType;
    private String courseNumber;
    private List<String> outcomes;
    private List<Section> sections;

    public Course() {

    }

    public String toString(){

        String output =  "Course: " + courseID + " \t " + courseName + " \t " + newTitleCode + " \t " + department + " \t " + registrationType + " \t " + courseNumber + " \t " + outcomes.toString();
        output += "\n\t" + sections.toString();

        return output;

    }

    public List<String> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<String> outcomes) {
        this.outcomes = outcomes;
    }
    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setNewTitleCode(String newTitleCode) {
        this.newTitleCode = newTitleCode;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }


    public String getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getNewTitleCode() {
        return newTitleCode;
    }

    public String getDepartment() {
        return department;
    }

    public String getRegistrationType() {
        return registrationType;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public List<Section> getSections() {
        return sections;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }
}
