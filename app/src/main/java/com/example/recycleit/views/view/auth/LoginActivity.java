package com.example.recycleit.views.view.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.recycleit.databinding.ActivityLoginBinding;
import com.example.recycleit.views.auth.SharedPreferenceManager;
import com.example.recycleit.views.repository.AuthRepo;
import com.example.recycleit.views.view.home.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String Uid;
    ActivityLoginBinding binding;
    private ViewModelAuth viewModelAuth;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferenceManager manager = new SharedPreferenceManager();
        manager.remove(this);

        viewModelAuth = new ViewModelProvider(
                this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModelAuth.class);

//        viewModelAuth.getUserData().observe(this, new Observer<FirebaseUser>() {
//            @Override
//            public void onChanged(FirebaseUser firebaseUser) {
//                if (firebaseUser != null) {
//                    //  goToHomeScreen();
//                }
//            }
//        });
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   goToHomeScreen();
                String email = (binding.emailEt.getText()).toString().trim();
                String pass = (binding.passwordEt.getText()).toString().trim();

//                Log.i(TAG, "onClick: + before " + firebaseAuth.getCurrentUser().getUid() == null ? firebaseAuth.getCurrentUser().getUid() : "null");
                viewModelAuth.signIn(LoginActivity.this, email, pass);
//                if (Status.getInstance().state.equals("success") || !Status.getInstance().state.equals("")) {
//                    goToHomeScreen();
//                }


                viewModelAuth.getStatus().observe(LoginActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean) {
                            goToHomeScreen();
                        } else {
                            Toast.makeText(LoginActivity.this, "welcome to your app please wait >>> ", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        binding.forgotPasswordTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToForgetScreen();
            }
        });

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignUpScreen();
            }
        });

        //email: abdul@gmail.com
        //password: A_a1234567
    }

    private void goToSignUpScreen() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        // finish();
    }

    private void goToHomeScreen() {

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void goToForgetScreen() {

        Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
        startActivity(intent);
        finish();


    }
}