package com.example.recycleit.views.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.MutableLiveData;

import com.example.recycleit.views.auth.SharedPreferenceManager;
import com.example.recycleit.views.auth.Status;
import com.example.recycleit.views.auth.UserType;
import com.example.recycleit.views.model.firebase.PostItem;
import com.example.recycleit.views.model.firebase.UserItem;
import com.example.recycleit.views.model.local.User;
import com.example.recycleit.views.view.auth.LoginActivity;
import com.example.recycleit.views.view.auth.ResetPasswordActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class AuthRepo {
    private static final String TAG = "AuthRepo";
    private Application application;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private MutableLiveData<Boolean> status;
    private MutableLiveData<CheckBox> check = getCheck();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    private DatabaseReference root = db.getReference().child("Users");

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public MutableLiveData<Boolean> getStatus() {
        return status;
    }

    public MutableLiveData<CheckBox> getCheck() {
        return check;
    }

    public AuthRepo(Application application) {
        this.application = application;
        firebaseUserMutableLiveData = new MutableLiveData<>();
        status = new MutableLiveData<>();
        check = new MutableLiveData<>();


        if (auth.getCurrentUser() != null) {
            firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
        }
    }

    public void register(String email, String pass) {

        status.postValue(false);
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
                            Toast.makeText(application, "user created", Toast.LENGTH_LONG).show();
                            //callback to navigate to login
                            //       auth2.navigate();

                            //       auth2.navigate();

                            status.postValue(true);
                        } else {
                            Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Status.getInstance().setState("error");
                        }
                    }
                });
    }

    public void login(Context context, String email, String pass) {

        if (!email.isEmpty() && !pass.isEmpty()) {
            status.postValue(false);
            auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.i(TAG, "onComplete login :  success1");
                                getUser(context, email);
                                SharedPreferenceManager manager = new SharedPreferenceManager();
                                manager.setLoginStatus(context, true);
                                Log.i(TAG, "onComplete login :  success2");
                            } else {
                                status.postValue(false);
                                Log.i(TAG, "onFailure: --- " + task.getException().getLocalizedMessage());
                                Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                Status.getInstance().setState("error");
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i(TAG, "onFailure: " + e.getLocalizedMessage());
                        }
                    });
        } else {
            Toast.makeText(application, "please fill your data ", Toast.LENGTH_LONG).show();
        }
    }

    private void getUser(Context context, String email) {
        Log.i(TAG, "getUser: kkkkkkkkkkkkkk");
        store.collection("Recycle it database schema")
                .document("users")
                .collection("regular")
                .document(auth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Log.i(TAG, "onComplete: " + task.getResult().toObject(User.class));

                        if (task.isSuccessful()) {
                            status.postValue(true);
                            firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
                            Status.getInstance().setState("success");
                            Log.i(TAG, "onComplete: successssssssss");
                            User user = task.getResult().toObject(User.class);
                            Log.i(TAG, "onComplete: user "+ user);
                            SharedPreferenceManager manager = new SharedPreferenceManager();
                            Log.i(TAG, "getUser2: " + manager.getType(context));
                            Log.i(TAG, "onComplete:  success3");
                            if (Objects.equals(user.getType(), "BUSINESS")) {
                                Log.i(TAG, "onComplete: 1");
                                manager.setType(context, UserType.BUSINESS.getType());
                            } else if (Objects.equals(user.getType(),"REGULAR")) {
                                Log.i(TAG, "onComplete: 2");
                                manager.setType(context, UserType.REGULAR.getType());
                            } else {
                                Log.i(TAG, "onComplete: 3");
                                manager.setType(context, UserType.GUEST.getType());
                            }
                        } else {
                            Log.i(TAG, "onComplete: error");
                        }
                    }
                });
        store.collection("Recycle it database schema").document("users")
                .collection("regular")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "onFailure: " + e.getLocalizedMessage());
                    }
                });
    }

    public void signOut() {
        auth.signOut();
        status.postValue(false);
    }

    public static boolean is_Valid_Password(String password) {

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

    public void upload(User user) {

        store.collection("Recycle it database schema").document("users").collection("regular").document(auth.getCurrentUser().getUid()).set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "onComplete: data saved");
                        } else {
                            Log.i(TAG, "onComplete: error to load data");
                        }
                    }
                });
    }

    private void displayConstrain(ConstraintLayout constraintLayout) {
        if (check.getValue().isChecked()) {
            constraintLayout.setVisibility(View.VISIBLE);

        } else {
            constraintLayout.setVisibility(View.GONE);
            constraintLayout.setVisibility(View.INVISIBLE);
        }

    }

    public Boolean checkField(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            editText.setError("Error");
            return false;
        } else return true;

    }

    public void signInAnonymously() {
        auth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInAnonymously:success");
                    FirebaseUser user = auth.getCurrentUser();
                    upload(new User("Guest", "", "anonymous", "", "", UserType.GUEST.getType()));
                } else {
                    Log.w(TAG, "signInAnonymously:failure", task.getException());
                    Toast.makeText(application, "Authentication failed.", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    public void resetPassword(String email) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(application,"Rest password can you check your email please ",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(application, e.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }
    public void code(String email)
    {
        auth.verifyPasswordResetCode(email).addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                Log.i(TAG, "onComplete: verifyPasswordResetCode ");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, "onFailure: verifyPasswordResetCode ");
            }
        });
    }


}
