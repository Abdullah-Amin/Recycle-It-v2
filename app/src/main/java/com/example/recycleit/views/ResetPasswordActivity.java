package com.example.recycleit.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.recycleit.R;
import com.example.recycleit.databinding.ActivityResetPasswordBinding;

public class ResetPasswordActivity extends AppCompatActivity {

    ActivityResetPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetPasswordActivity.this.finish();
            }
        });
    }
}