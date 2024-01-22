package com.example.recycleit.views.model.firebase;

public class Item {
    String name;
    String description ;
    double pirce;
     int Quality;

    public Item(String name, String description, double pirce, int quality) {
        this.name = name;
        this.description = description;
        this.pirce = pirce;
        Quality = quality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPirce() {
        return pirce;
    }

    public void setPirce(double pirce) {
        this.pirce = pirce;
    }

    public int getQuality() {
        return Quality;
    }

    public void setQuality(int quality) {
        Quality = quality;
    }
}
