package com.example.recycleit.views.view.courses;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.recycleit.views.model.WorkShop;
import com.example.recycleit.views.model.firebase.CourseB;
import com.example.recycleit.views.model.firebase.RegisterCourses;
import com.example.recycleit.views.repository.CourseRepo;
import com.example.recycleit.views.repository.WorkshopRepo;
import com.google.firebase.auth.FirebaseUser;

public class CourseViewModel extends AndroidViewModel {

    private CourseRepo repository;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private MutableLiveData<Boolean> status;


    public CourseViewModel(@NonNull Application application) {
        super(application);

        repository= new CourseRepo(application);
        firebaseUserMutableLiveData=repository.getFirebaseUserMutableLiveData();


    }
    public void loadcourse(CourseB courseB)
    {

        repository.uploadCourse(courseB);
    }
public void registerForCourse(RegisterCourses courses)
{
    repository.registerCourse(courses);
}

}



