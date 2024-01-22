package com.example.recycleit.views.view.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.recycleit.databinding.ActivityStartBinding;
import com.example.recycleit.views.auth.SharedPreferenceManager;
import com.example.recycleit.views.auth.UserType;
import com.example.recycleit.views.view.auth.LoginActivity;
import com.example.recycleit.views.view.auth.ViewModelAuth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    private ActivityStartBinding binding;
    private static final String TAG = "StartActivity";
    private FirebaseAuth auth;
    ViewModelAuth viewModelAuth;
    SharedPreferenceManager sharedPreferenceManager=new SharedPreferenceManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModelAuth = new ViewModelProvider(
                this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModelAuth.class);

        auth=FirebaseAuth.getInstance();

        binding.loginBtn.setOnClickListener(view -> {
            startActivity(new Intent(StartActivity.this, LoginActivity.class));
        });

        binding.startBtn.setOnClickListener(view -> {

//            sharedPreferenceManager.setType(this,UserType.GUEST.getType());
//            Log.i(TAG, "onCreate:  "+UserType.GUEST.getType());
//           startActivity(new Intent(StartActivity.this, MainActivity.class));
            FirebaseUser currentUser = auth.getCurrentUser();
            viewModelAuth.signInAnonymously(currentUser);


        });
    }

    private void updateUI(FirebaseUser user) {
        // Implement UI update logic based on the authenticated user
        // This could involve showing/hiding elements, displaying user info, etc.
        if(user.isAnonymous())
            Toast.makeText(this, "Authentication done.", Toast.LENGTH_SHORT).show();

        else {
            Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();

        }
    }

}