package com.example.recycleit.views.model.firebase;

public class CourseB {

    String courseName,courseDate,courseGoals, courseState;

    public CourseB(String courseName, String courseDate, String courseState, String courseGoals) {
        this.courseName = courseName;
        this.courseDate = courseDate;
        this.courseState = courseState;
        this.courseGoals = courseGoals;
    }
public CourseB(){}

    public String getCourseState() {
        return courseState;
    }

    public void setCourseState(String courseState) {
        this.courseState = courseState;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(String courseDate) {
        this.courseDate = courseDate;
    }

    public String getCourseGoals() {
        return courseGoals;
    }

    public void setCourseGoals(String courseGoals) {
        this.courseGoals = courseGoals;
    }

    @Override
    public String toString() {
        return "CourseB{" +
                "courseName='" + courseName + '\'' +
                ", courseDate='" + courseDate + '\'' +
                ", courseGoals='" + courseGoals + '\'' +
                ", courseState='" + courseState + '\'' +
                '}';
    }
}
