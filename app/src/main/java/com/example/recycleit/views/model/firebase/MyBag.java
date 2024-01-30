package com.example.recycleit.views.model.firebase;

import java.util.ArrayList;

public  class MyBag {

    ArrayList<PostItem>myBag;

    public MyBag(ArrayList<PostItem> myBag) {
        this.myBag = myBag;
    }
public MyBag(){}

    public ArrayList<PostItem> getMyBag() {
        return myBag;
    }

    public void setMyBag(ArrayList<PostItem> myBag) {
        this.myBag = myBag;
    }
}
