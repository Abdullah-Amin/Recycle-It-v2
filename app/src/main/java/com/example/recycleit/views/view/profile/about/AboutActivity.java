package com.example.recycleit.views.view.profile.about;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.recycleit.databinding.ActivityAboutBinding;
import com.example.recycleit.views.view.home.HomeFragment;
import com.example.recycleit.views.view.home.MainActivity;
import com.example.recycleit.views.view.home.StartActivity;
import com.example.recycleit.views.view.profile.user.ProfileActivity;

public class AboutActivity extends AppCompatActivity {
    ActivityAboutBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}