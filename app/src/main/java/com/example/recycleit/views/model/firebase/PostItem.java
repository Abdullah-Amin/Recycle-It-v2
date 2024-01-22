package com.example.recycleit.views.model.firebase;

import android.net.Uri;

public class PostItem {

    private String userName;
    private String userImage;
    private String itemImage;
    private String caption;
    private String price;
    private String description;

    public PostItem(String userName, String userImage, String itemImage, String caption, String price, String description) {
        this.userName = userName;
        this.userImage = userImage;
        this.itemImage = (itemImage);
        this.caption = caption;
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

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
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
        return "PostItem{" +
                "userName='" + userName + '\'' +
                ", userImage='" + userImage + '\'' +
                ", itemImage='" + itemImage + '\'' +
                ", caption='" + caption + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
