package com.example.recycleit.views.view.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.recycleit.databinding.ActivityResetPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.TimeUnit;

public class ResetPasswordActivity extends AppCompatActivity {

    ActivityResetPasswordBinding binding;
    FirebaseAuth auth= FirebaseAuth.getInstance();
    ViewModelAuth viewModel;

    private  String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(
                this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModelAuth.class);

        binding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ResetPasswordActivity.this.finish();
            }
        });
        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!TextUtils.isEmpty(binding.emailEt.getText()))
                {
                    email=binding.emailEt.getText().toString();
                    viewModel.resetPassword(email);
                    binding.lnHostBtContinue.setVisibility(View.INVISIBLE);

                    binding.progressPar.setVisibility(View.VISIBLE);
                    try {

                        //android.os.SystemClock.sleep(5000);
                        Thread.sleep(1000);

                    }
                    catch (Exception e)
                    {
                        binding.emailEt.setError("please fill Email Field");
                    }

                    ResetPasswordActivity.this.finish();
                }
                else
                {
                    binding.emailEt.setError("please fill Email Field");
                }
            }
        });
    }

}