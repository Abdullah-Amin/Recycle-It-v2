package com.example.recycleit.views.view.myorder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.recycleit.databinding.ActivityMyOrderBinding;

public class MyOrderActivity extends AppCompatActivity {
ActivityMyOrderBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMyOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}