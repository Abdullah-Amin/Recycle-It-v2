package com.example.recycleit.views.model.firebase;

public class Courses {

    String titile;
        String description;
        String time;
        String Address;
        String Material;

    public Courses(String titile, String description, String time, String address, String material) {
        this.titile = titile;
        this.description = description;
        this.time = time;
        Address = address;
        Material = material;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }
}
