package com.example.recycleit.views.model.firebase;

import java.util.List;

public class UserItem {

    List<CourseB> courses;

    public List<CourseB> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseB> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "UserItem{" +
                "courses=" + courses +
                '}';
    }
}
