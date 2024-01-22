package com.example.recycleit.views.model.firebase;

public class RegisterCourses {

    String firstName,lastName,Email,Country;

    public RegisterCourses() {
    }

    public RegisterCourses(String firstName, String lastName, String email, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        Email = email;
        Country = country;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }
}
