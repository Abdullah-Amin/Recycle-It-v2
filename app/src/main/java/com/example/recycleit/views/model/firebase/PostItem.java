package com.example.recycleit.views.model.firebase;

import android.net.Uri;

public class PostItem {

    private String userName;
    private String userImage;
    private String uid;
    private String itemImage;
    private String itemName;
    private String price;
    private String description;

    public PostItem(String userName, String userImage, String uid, String itemImage, String itemName, String price, String description) {
        this.userName = userName;
        this.userImage = userImage;
        this.uid = uid;
        this.itemImage = (itemImage);
        this.itemName = itemName;
        this.price = price;
        this.description = description;
    }

    public PostItem() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "{" +
                "userName='" + userName + '\'' +
                ", userImage='" + userImage + '\'' +
                ", uid='" + uid + '\'' +
                ", itemImage='" + itemImage + '\'' +
                ", itemName='" + itemName + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
