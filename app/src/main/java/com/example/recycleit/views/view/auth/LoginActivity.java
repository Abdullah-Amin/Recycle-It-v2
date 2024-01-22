package com.example.recycleit.views.view.auth;

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
import com.example.recycleit.views.auth.Status;
import com.example.recycleit.views.repository.AuthRepo;
import com.example.recycleit.views.view.home.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        viewModelAuth=new ViewModelProvider(
                this,ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModelAuth.class);

        viewModelAuth.getUserData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser!=null)
                {
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
                viewModelAuth.signIn(email, pass);
//                if (Status.getInstance().state.equals("success") || !Status.getInstance().state.equals("")) {
//                    goToHomeScreen();
//                }


                viewModelAuth.getStatus().observe(LoginActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean){
                            goToHomeScreen();
                        }

                        else{
                            Toast.makeText(LoginActivity.this, "welcome to your app please wait >>> " , Toast.LENGTH_LONG).show();

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