package com.example.recycleit.views.model;

public class WorkShop {

    String workshopName,workshopDate,workshopLocation,workshopGoal;

    public WorkShop(String workshopName, String workshopDate, String workshopLocation, String workshopGoal) {
        this.workshopName = workshopName;
        this.workshopDate = workshopDate;
        this.workshopLocation = workshopLocation;
        this.workshopGoal = workshopGoal;
    }
    public WorkShop(){}

    public String getWorkshopName() {
        return workshopName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

    public String getWorkshopDate() {
        return workshopDate;
    }

    public void setWorkshopDate(String workshopDate) {
        this.workshopDate = workshopDate;
    }

    public String getWorkshopLocation() {
        return workshopLocation;
    }

    public void setWorkshopLocation(String workshopLocation) {
        this.workshopLocation = workshopLocation;
    }

    public String getWorkshopGoal() {
        return workshopGoal;
    }

    public void setWorkshopGoal(String workshopGoal) {
        this.workshopGoal = workshopGoal;
    }
}
