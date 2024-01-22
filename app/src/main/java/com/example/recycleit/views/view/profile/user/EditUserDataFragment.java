package com.example.recycleit.views.view.profile.user;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.recycleit.databinding.FragmentEdditUserDatakBinding;
import com.example.recycleit.views.auth.SharedPreferenceManager;
import com.example.recycleit.views.auth.UserType;
import com.example.recycleit.views.model.local.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;


public class EditUserDataFragment extends Fragment {

    FragmentEdditUserDatakBinding binding;
    SharedPreferenceManager sharedPreferenceManage=new SharedPreferenceManager();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    StorageReference storage = FirebaseStorage.getInstance().getReference();
    private static final String TAG = "EditUserDataFragment";
    User user = new User("", "", "", "", "", "type");
    private ViewModelUser viewModelUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEdditUserDatakBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModelUser = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(ViewModelUser.class);

        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageCropper();
            }
        });
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.etdName.getText().toString().trim();
                String userName = binding.etdUsername.getText().toString().trim();
                String email = binding.etdEmail.getText().toString().trim();
                String password = binding.etdPassword.getText().toString().trim();

                if (name.isEmpty() ) {

                    binding.etdName.setError("insert your name");
                    Toast.makeText(requireContext(), "all data filed Required", Toast.LENGTH_LONG).show();
                    return;
                }
                if (userName.isEmpty() ) {
                    binding.etdUsername.setError("insert your userName");
                    Toast.makeText(requireContext(), "all data filed Required", Toast.LENGTH_LONG).show();
                    return;
                }
                if (email.isEmpty())
                {
                    binding.etdEmail.setError("insert your email");
                    Toast.makeText(requireContext(), "all data filed Required", Toast.LENGTH_LONG).show();
                    return;
                }

                    if (password.isEmpty())
                    {
                        binding.etdPassword.setError("insert your password");
                        Toast.makeText(requireContext(), "all data filed Required", Toast.LENGTH_LONG).show();
                        return;
                    }

                 else {
                    user.setName(name);
                    user.setSirname(userName);
                    user.setEmail(email);
                    user.setPassword(password);
                    Log.i(TAG, "onClick: " + user.toString());
                       viewModelUser.updateUserData(user);

                    }
            }
        });

        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
              sharedPreferenceManage.remove(requireActivity());

            }
        });

    }

//    private void updateUserData() {
//        HashMap<String, Object> userHashMap = new HashMap<>();
//        userHashMap.put(Uid, user);
//        firebaseFirestore.collection("Recycle it database schema")
//                .document("users").collection("regular").document(Uid).update(userHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.i(TAG, "onComplete:  yes updated");
//                            Toast.makeText(requireContext(), "Your data updated. ", Toast.LENGTH_LONG).show();
//
//                        } else {
//                            Log.i(TAG, "onComplete: " + task.getException().getLocalizedMessage().toString());
//                        }
//                    }
//                });
//    }

    private void imageCropper() {
        CropImage.activity()
                .start(getContext(), this);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                binding.profileImage.setImageURI(resultUri);

                uploadImage(resultUri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void uploadImage(Uri imageUri) {
        storage.child("images profiles").child(firebaseAuth.getUid()).putFile(imageUri).addOnCompleteListener
                (new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(requireContext(), "the image saved", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onComplete:the image saved ");
                    getImageUrl();

                } else {
                    String error = task.getException().getLocalizedMessage().toString();
                    Toast.makeText(requireContext(), "the problem happen when upload " + error, Toast.LENGTH_LONG).show();

                    Log.i(TAG, "onComplete:the problem happen when upload " + error);
                }
            }
        });
    }

    private void getImageUrl() {
        storage.child("Image profile").child(firebaseAuth.getUid()).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    String imageUrl=task.getResult().toString();
                    Log.i(TAG, "onComplete: "+imageUrl);
                    uploadImageurl(imageUrl);
                }
            }
        });
    }

    private void uploadImageurl(String imageUrl) {
        HashMap<String,Object>image=new HashMap<>();
        image.put("Image profile",imageUrl);

        firebaseFirestore.collection("image profile").document(firebaseAuth.getUid()).update(image).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
           if(task.isSuccessful())
           {
               Toast.makeText(requireContext(), "image updated" , Toast.LENGTH_LONG).show();
               Log.i(TAG, "onComplete:image updated " );

           }
           else {
               Toast.makeText(requireContext(), "problem" +task.getException().getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
               Log.i(TAG, "onComplete:image problem "+task.getException().getLocalizedMessage().toString() );
           }
            }
        });
    }


}