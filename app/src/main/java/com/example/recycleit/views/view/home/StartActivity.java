package com.example.recycleit.views.view.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.recycleit.databinding.ActivityStartBinding;
import com.example.recycleit.views.auth.SharedPreferenceManager;
import com.example.recycleit.views.auth.UserType;
import com.example.recycleit.views.model.local.User;
import com.example.recycleit.views.view.auth.LoginActivity;
import com.example.recycleit.views.view.auth.ViewModelAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class StartActivity extends AppCompatActivity {

    private ActivityStartBinding binding;
    private static final String TAG = "StartActivity";
    private FirebaseAuth auth;

    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    ViewModelAuth viewModelAuth;
    SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        SharedPreferenceManager manager = new SharedPreferenceManager();
//        manager.remove(this);

        viewModelAuth = new ViewModelProvider(
                this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModelAuth.class);

        auth = FirebaseAuth.getInstance();

        binding.loginBtn.setOnClickListener(view -> {
            if (!sharedPreferenceManager.getLoginStatus(this)){
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
            }else {
//                sharedPreferenceManager.setType(StartActivity.this, UserType.BUSINESS.getType());

                startActivity(new Intent(StartActivity.this, MainActivity.class));
            }
        });

        binding.startBtn.setOnClickListener(view -> {

            auth.signInWithEmailAndPassword("guest@gmail.com", "1234567890")
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                sharedPreferenceManager.setType(StartActivity.this, UserType.GUEST.getType());
                                store.collection("Recycle it database schema").document("users").collection("regular").
                                        document(auth.getCurrentUser().getUid())
                                        .set(new User("Guest", "Guest", "guest@gmail.com", "null", "0", UserType.GUEST.getType()))
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.i(TAG, "onComplete: data saved");
                                                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                                                } else {
                                                    Log.i(TAG, "onComplete: error to load data");
                                                }
                                            }
                                        });
                            }
                        }
                    });
//            Log.i(TAG, "onCreate: " + currentUser);
//            viewModelAuth.signInAnonymously(currentUser);
        });
    }
}