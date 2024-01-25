package com.example.recycleit.views.model.local;

import com.google.firebase.firestore.DocumentId;

public class User {

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", sirname='" + sirname + '\'' +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                ", company_number='" + company_number + '\'' +
                ", password='" + password + '\'' +
                ", user_id='" + userId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User() {

    }

    private  String name;
    @DocumentId
    private String userId;

    private String sirname,email,company,company_number, password, imageUrl="",type;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPassword() {
        return password;
    }

    public User(String name, String sirname, String email, String company, String company_number, String type) {
        this.name = name;
        this.email = email;
        this.sirname=sirname;
        this.company = company;
        this.company_number = company_number;
        this.type = type;
    }





    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSirname() {
        return sirname;
    }

    public void setSirname(String sirname) {
        this.sirname = sirname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany_number() {
        return company_number;
    }

    public void setCompany_number(String company_number) {
        this.company_number = company_number;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }








}
