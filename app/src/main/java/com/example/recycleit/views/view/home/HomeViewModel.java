package com.example.recycleit.views.view.home;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recycleit.views.model.firebase.PostItem;
import com.example.recycleit.views.repository.HomeRepo;

import java.util.List;

public class HomeViewModel extends ViewModel {

    public void upload(PostItem postItem, Context context, View view){
        HomeRepo homeRepo = new HomeRepo();
        homeRepo.uploadPost(postItem, context, view);
    }
//    public List<PostItem> getItems(){
//        HomeRepo homeRepo = new HomeRepo();
//        return homeRepo.getHomeItems();
//    }

}
