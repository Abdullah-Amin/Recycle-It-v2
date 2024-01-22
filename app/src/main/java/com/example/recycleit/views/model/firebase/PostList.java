package com.example.recycleit.views.model.firebase;

import java.util.ArrayList;

public class PostList {

    private ArrayList<PostItem> postItems;

    public PostList(ArrayList<PostItem> postItems) {
        this.postItems = postItems;
    }

    public PostList() {
    }

    public ArrayList<PostItem> getPostItems() {
        return postItems;
    }

    public void setPostItems(ArrayList<PostItem> postItems) {
        this.postItems = postItems;
    }
}
