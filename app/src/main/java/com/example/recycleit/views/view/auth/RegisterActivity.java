package com.example.recycleit.views.view.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.recycleit.databinding.ActivityRegisterBinding;
import com.example.recycleit.views.Auth;
import com.example.recycleit.views.auth.SharedPreferenceManager;
import com.example.recycleit.views.auth.UserType;
import com.example.recycleit.views.view.home.MainActivity;
import com.example.recycleit.views.model.local.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity implements Auth {
    private ActivityRegisterBinding binding;
    private static final String TAG = "RegisterActivity";
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    SharedPreferenceManager sharedPreferenceManager=new SharedPreferenceManager();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private Boolean ischeck = false;


    private ViewModelAuth viewModelAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModelAuth = new ViewModelProvider(
                this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ViewModelAuth.class);


        binding.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxLogic(isChecked);

            }
        });

        binding.btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fetchData();

            }
        });

    }

    private void checkBoxLogic(Boolean isChecked) {
         if (isChecked) {
             binding.constraintlayoutCompany.setVisibility(View.VISIBLE);
                    if (binding.etdCompany.getText().toString().isEmpty()) {
                        binding.etdCompany.setError("Error");

                        Toast.makeText(RegisterActivity.this, "please Fill your company Name is mandatory", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onCheckedChanged: please Fill your company Name is mandatory");
                    }
                    if (binding.etdCompanyNumber.getText().toString().isEmpty()) {
                        binding.etdCompanyNumber.setError("Error");
                        Toast.makeText(RegisterActivity.this, "please Fill your company Number ID is mandatory", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onCheckedChanged: please Fill your company Number ID is mandatory ");
                        return;
                    }

                    String companyName = binding.etdCompany.getText().toString().trim();
                    String companyNumber = binding.etdCompanyNumber.getText().toString().trim();
                } else {
             binding.constraintlayoutCompany.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "take care this is regular user", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onCheckedChanged: take care this is regular user ");

                }

    }



    private void fetchData() {

        String name=binding.etdName.getText().toString().trim();
        String password =binding.etdPassword.getText().toString().trim();
        String email =binding.etdEmail.getText().toString().trim();
        String userName=binding.etdUsername.getText().toString().trim();

        String companyName=binding.etdCompany.getText().toString().trim();
        String companyNumber=binding.etdCompanyNumber.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email!!", Toast.LENGTH_LONG).show();

            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password!!", Toast.LENGTH_LONG).show();
            return;
        }
        if (!is_Valid_Password(password)) {
            Toast.makeText(this, "the password invalid", Toast.LENGTH_SHORT).show();
            return;
        }

        viewModelAuth.register(email, password);
        viewModelAuth.getStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean status) {
                if (status) {
                    User user = new User();

                    if(binding.checkbox.isChecked())
                    {
                       user.setType(UserType.BUSINESS.getType());
                    }
                    else
                    {
                        user.setType(UserType.REGULAR.getType());

                    }
                    user.setName(name);
                    user.setEmail(email);
                    user.setPassword(password);
                    viewModelAuth.loadUserData(user);
                    sharedPreferenceManager.setType(RegisterActivity.this,user.getType());

                    navigate();
                    Log.i(TAG, "onChanged: navigated");

                } else {
                    Toast.makeText(RegisterActivity.this, "please wait until to featch data ... ", Toast.LENGTH_LONG).show();

                }
            }
        });


    }


 //  private void register() {
//        user.setName(binding.etdName.getText().toString().trim());
//        user.setPassword(binding.etdPassword.getText().toString().trim());
//        user.setEmail(binding.etdEmail.getText().toString().trim());
//        user.setImageUrl("");
//        user.setCompany(binding.etdCompany.getText().toString().trim());
//        user.setCompany_number(binding.tvCompanyNumber.getText().toString().trim());
//        user.setSirname(binding.etdUsername.getText().toString().trim());
//        if (user.getEmail().isEmpty() || user.getPassword().isEmpty() || user.getName().isEmpty()
//                || user.getName().isEmpty() || user.getSirname().isEmpty() || user.getCompany().isEmpty()
//                || user.getCompany_number().isEmpty()
//        ) {
//            Toast.makeText(this, "please fill your data please ", Toast.LENGTH_LONG).show();
//
//        } else {
//
//            Log.i(TAG, "register: " + user.getPassword());
//            if (is_Valid_Password(user.getPassword())) {
//                createUserByEmailandPassword(user.getEmail(), user.getPassword());
//                goToHomeScreen();
//            } else {
//                Log.i(TAG, "the password invalid : ");
//
//            }
//
//        }
//    }

//    private void createUserByEmailandPassword(String email, String pass) {
//        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(RegisterActivity.this, "account created", Toast.LENGTH_LONG).show();
//                    goToLogin();
//
//                    user.setUser_id(task.getResult().getUser().getUid().trim());
//                    String id = user.getUser_id().toString();
  //               uploadUserData(id);
//                    Log.i(TAG, "onComplete:  user isSuccessful login");
//                } else {
//                    String error = task.getException().getLocalizedMessage().toString();
//                    Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_LONG).show();
//                    Log.i(TAG, "onComplete: " + error);
//
//                }
//
//            }
//        });
//    }

    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

//    public boolean isValidPassword(String password) {
//
//        Pattern pattern;
//        Matcher matcher;
//
//        final String PASSWORD_PATTERN = "^" +
//                "(?=.*[0-9])" +
//                "(?=.*[a-z])" +
//                "(?=.*[A-Z])" +
//                "(?=.*[@#$_!%^&+=-])" +
//                "(?=\\S+$).{8,}$";
//
//        pattern = Pattern.compile(PASSWORD_PATTERN);
//        matcher = pattern.matcher(password);
//
//        return matcher.matches();
//    }

    public static boolean is_Valid_Password(@NonNull String password) {

        if (password.length() < 8) return false;

        int charCount = 0;
        int numCount = 0;
        for (int i = 0; i < password.length(); i++) {

            char ch = password.charAt(i);

            if (is_Numeric(ch)) numCount++;
            else if (is_Letter(ch)) charCount++;
            else return false;
        }


        return (charCount >= 2 && numCount >= 2);
    }

    public static boolean is_Letter(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }
    public static boolean is_Numeric(char ch) {

        return (ch >= '0' && ch <= '9');
    }

    private void showCompanyData()
    {
        if (binding.checkbox.isChecked())
        {

            binding.constraintlayoutCompany.setVisibility(View.VISIBLE);
        }
        else {
            binding.constraintlayoutCompany.setVisibility(View.GONE);

        }

    }

    @Override
    public void navigate() {
        goToLogin();
    }
}