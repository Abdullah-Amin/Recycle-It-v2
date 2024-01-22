package com.example.recycleit.views.model.firebase;

public class Address {

    String firstname,SecondName,phone,country,city,street,post_id,regin;


    public Address(String firstname, String secondName, String phone, String country, String city, String street, String post_id,String regin) {
        this.firstname = firstname;
        SecondName = secondName;
        this.phone = phone;
        this.country = country;
        this.city = city;
        this.street = street;
        this.post_id = post_id;
        this.regin=regin;
    }
public Address(){}
    public String getRegin() {
        return regin;
    }

    public void setRegin(String regin) {
        this.regin = regin;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondName() {
        return SecondName;
    }

    public void setSecondName(String secondName) {
        SecondName = secondName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }
}
