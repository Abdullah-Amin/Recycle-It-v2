package com.example.recycleit.views.view.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.recycleit.databinding.ActivityLoginBinding;
import com.example.recycleit.views.ResetPasswordActivity;
import com.example.recycleit.views.auth.SharedPreferenceManager;
import com.example.recycleit.views.auth.Status;
import com.example.recycleit.views.auth.UserType;
import com.example.recycleit.views.model.local.User;
import com.example.recycleit.views.repository.AuthRepo;
import com.example.recycleit.views.view.home.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String Uid;
    ActivityLoginBinding binding;
    private ViewModelAuth viewModelAuth;
    private AuthRepo auth;
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

        viewModelAuth.getUserData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    //  goToHomeScreen();
                }
            }
        });
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
//        FirebaseFirestore store = FirebaseFirestore.getInstance();
////        DocumentReference documentRef = store.collection("Recycle it database schema")
////                .document("users").collection("regular")
////                .document(firebaseAuth.getUid());
//
//        DocumentReference usersCollection =
//                store.collection("Recycle it database schema")
//                        .document("users").collection("regular")
//                        .document(firebaseAuth.getUid());
//
//        usersCollection.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                @Override
//                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                    if (error != null) {
//                        Toast.makeText(LoginActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//                        Log.i(TAG, "onEvent: " + error.getLocalizedMessage());
//                        return;
//                    }
//                    Log.i(TAG, "getUser1: " + value.toObject(User.class));
//                        SharedPreferenceManager manager = new SharedPreferenceManager();
//                        Log.i(TAG, "getUser2: " + manager.getType(LoginActivity.this));
//                        Log.i(TAG, "onComplete:  success3");
//                        if (value.toObject(User.class).getType() == UserType.BUSINESS.getType()) {
//                            manager.setType(LoginActivity.this, UserType.BUSINESS.getType());
//                        } else if (value.toObject(User.class).getType() == UserType.REGULAR.getType()) {
//                            manager.setType(LoginActivity.this, UserType.REGULAR.getType());
//                        } else {
//                            manager.setType(LoginActivity.this, UserType.GUEST.getType());
//                        }
//                }
//            });

//        documentRef.get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                Log.i(TAG, "getUser1: " + documentSnapshot.toObject(User.class));
//                        SharedPreferenceManager manager = new SharedPreferenceManager();
//                        Log.i(TAG, "getUser2: " + manager.getType(LoginActivity.this));
//                        Log.i(TAG, "onComplete:  success3");
//                        if (documentSnapshot.toObject(User.class).getType() == UserType.BUSINESS.getType()) {
//                            manager.setType(LoginActivity.this, UserType.BUSINESS.getType());
//                        } else if (documentSnapshot.toObject(User.class).getType() == UserType.REGULAR.getType()) {
//                            manager.setType(LoginActivity.this, UserType.REGULAR.getType());
//                        } else {
//                            manager.setType(LoginActivity.this, UserType.GUEST.getType());
//                        }
//                    }
//                });


//        Log.i(TAG, "getUser: 1   " + firebaseAuth.getCurrentUser().getUid());
//        store.collection("Recycle it database schema").document("users").collection("regular").document(firebaseAuth.getCurrentUser().getUid()).get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        Log.i(TAG, "getUser1: " + task.getResult());
//                        SharedPreferenceManager manager = new SharedPreferenceManager();
//                        Log.i(TAG, "getUser2: " + manager.getType(LoginActivity.this));
//                        if (task.isSuccessful()) {
//                            Log.i(TAG, "onComplete:  success3");
//                            if (task.getResult().toObject(User.class).getType() == UserType.BUSINESS.getType()) {
//                                manager.setType(LoginActivity.this, UserType.BUSINESS.getType());
//                            } else if (task.getResult().toObject(User.class).getType() == UserType.REGULAR.getType()) {
//                                manager.setType(LoginActivity.this, UserType.REGULAR.getType());
//                            } else {
//                                manager.setType(LoginActivity.this, UserType.GUEST.getType());
//                            }
//                        } else {
//                            Log.i(TAG, "onComplete:  errorrrrrrr");
//                            Toast.makeText(LoginActivity.this, "errorrrrrrr", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void goToForgetScreen() {

        Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
        startActivity(intent);
        finish();


    }

//    private void login(String email, String pass) {
//        firebaseAuth.signInWithEmailAndPassword(email, pass)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(LoginActivity.this, "Welcome  " + task.getResult().getUser().getDisplayName(), Toast.LENGTH_LONG).show();
//                            Log.i(TAG, "onComplete: " + task.getResult().getUser().getUid());
//                            SignInMethodQueryResult result = (SignInMethodQueryResult) task.getResult();
//                            if (result != null && result.getSignInMethods() != null && result.getSignInMethods().isEmpty()) {
//                                String errorMessage = Objects.requireNonNull(task.getException()).getLocalizedMessage();
//                                Log.i(TAG, "onComplete: " + errorMessage);
//                            } else {
//                                Log.i(TAG, "onComplete: everything Done");
//                            }
//
//
//                        goToHomeScreen();
//                    } else
//
//                    {
//                        String errorMessage = Objects.requireNonNull(task.getException()).getLocalizedMessage();
//                        Log.i(TAG, "onComplete: " + errorMessage);
//                        Log.i(TAG, "dear develop note that  When a user updates their email, password or resets their password." +
//                                " Firebase Auth backend revokes their tokens requiring that they reauthenticate or try to sign in again." +
//                                " This is a security feature." +
//                                " For example a user may reset their password if their account was compromised. All other sessions must reauthenticate.");
//                        Toast.makeText(LoginActivity.this, "error  " + task.getException().getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
//
//                    }
//                }
//    });
//}

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//     //   firebaseAuth.signOut();
//        return;
//    }
}