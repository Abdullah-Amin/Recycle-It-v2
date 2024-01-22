package com.example.recycleit.views.view.profile.post;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.recycleit.databinding.ActivityMyPostBinding;
import com.example.recycleit.views.view.home.MainActivity;

public class MyPostActivity extends AppCompatActivity {
    ActivityMyPostBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          binding= ActivityMyPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imGobackMypost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPostActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}