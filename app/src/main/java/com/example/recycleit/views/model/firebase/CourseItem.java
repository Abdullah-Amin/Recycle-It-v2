package com.example.recycleit.views.model.firebase;


import java.util.List;

public class CourseItem {
    List<UserItem> users;

    public List<UserItem> getUsers() {
        return users;
    }

    public void setUsers(List<UserItem> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "CourseItem{" +
                "users=" + users +
                '}';
    }
}
