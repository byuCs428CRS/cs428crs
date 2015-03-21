package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private String credit;

    public Course() {
    	sections = new ArrayList<Section>();
    	outcomes = new ArrayList<String>();
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

    public void setCredit(String tmp) { this.credit = tmp;}

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

    public String getCredit() { return credit; }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }
    
    public void addSection(Section s)
    {
    	sections.add(s);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(courseID, courseName, newTitleCode, department, departmentCode, registrationType, courseNumber, outcomes, sections);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Course other = (Course) obj;
        return Objects.equals(this.courseID, other.courseID)
                && Objects.equals(this.courseName, other.courseName)
                && Objects.equals(this.newTitleCode, other.newTitleCode)
                && Objects.equals(this.department, other.department)
                && Objects.equals(this.departmentCode, other.departmentCode)
                && Objects.equals(this.registrationType, other.registrationType)
                && Objects.equals(this.courseNumber, other.courseNumber)
                && Objects.equals(this.outcomes, other.outcomes)
                && Objects.equals(this.sections, other.sections);
    }
}
