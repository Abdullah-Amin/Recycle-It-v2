package com.example.recycleit.views;

import static android.app.Activity.RESULT_OK;

import static androidx.browser.customtabs.CustomTabsClient.getPackageName;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.recycleit.R;
import com.example.recycleit.databinding.FragmentCaptionBinding;
import com.example.recycleit.views.auth.Status;
import com.example.recycleit.views.model.firebase.PostItem;
import com.example.recycleit.views.model.local.User;
import com.example.recycleit.views.view.courses.CourseViewModel;
import com.example.recycleit.views.view.home.HomeViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class CaptionFragment extends Fragment {

    private FragmentCaptionBinding binding;
    FirebaseStorage storage2 = FirebaseStorage.getInstance();
    private HomeViewModel viewModel;
    private Bitmap bitmap;
    private Uri imageUri;
    private Uri userImage;

    private static final String TAG = "CaptionFragment";
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference usersCollection = db.collection("Recycle it database schema").document("users").collection("regular").document(auth.getUid());
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCaptionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(HomeViewModel.class);


        image();
        binding.buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPostToServer();
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });
        binding.uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

//        binding.image1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                imageUri = Uri.parse("android.resource://" + "com.example.recycleit" + "/" + R.drawable.download1);
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                binding.itemImage.setImageBitmap(bitmap);
//            }
//        });
//        binding.image2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                imageUri = Uri.parse("android.resource://" + "com.example.recycleit" + "/" + R.drawable.download2);
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                binding.itemImage.setImageBitmap(bitmap);
//            }
//        });
//        binding.image3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                imageUri = Uri.parse("android.resource://" + "com.example.recycleit" + "/" + R.drawable.download3);
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                binding.itemImage.setImageBitmap(bitmap);
//            }
//        });
//        binding.image4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                imageUri = Uri.parse("android.resource://" + "com.example.recycleit" + "/" + R.drawable.images);
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                binding.itemImage.setImageBitmap(bitmap);
//            }
//        });
    }

    private void pickImage() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermission();
            return;
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            imageUri = data.getData();
            try {
                Log.i(TAG, "onActivityResult:  --- " + data.getData());
                bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), data.getData());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            binding.itemImage.setImageBitmap(bitmap);
//            storageReference.child("profileImage").child("posts")
//                    .putFile(data.getData())
//                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                            imageUri = task.getResult().getUploadSessionUri();
//                            Log.i(TAG, "onComplete: image uploaded");
//                        }
//                    });
            Log.i("abdo", "onActivityResult: path: " + bitmap.toString());

//            uploadPostToServer();
        }
    }


    private void uploadPostToServer() {
        String description = binding.descriptionEt.getText().toString().trim();
        String price = binding.priceEt.getText().toString().trim();
        String name = binding.nameEt.getText().toString().trim();
        if (name.isEmpty() || price.isEmpty() || description.isEmpty()) {
            Toast.makeText(requireContext(), "Fields can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        usersCollection.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(requireContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onEvent: " + error.getLocalizedMessage());
                    return;
                }

                User user = value.toObject(User.class);
//                user.setImageUrl(userImage+"");
                Log.i(TAG, "uploadPostToServer: on event - " + imageUri);


//                    Log.i(TAG, "uploadPostToServer: on event - " + new URL(imageUri.toString()));
                viewModel.upload(
                        new PostItem(user.getName(), userImage + "", auth.getCurrentUser().getUid(), imageUri + "", name, price +".SR", description)
                );

                if (Status.getInstance().state.equals("success")) {
                    Toast.makeText(requireContext(), "Posted successfully", Toast.LENGTH_SHORT).show();
                    NavController navController = Navigation.findNavController(requireView());
                    navController.navigate(R.id.action_caption_fragment_to_navigation_homeFragment);
                }
            }
        });
        Log.i(TAG, "uploadPostToServer: end - " + imageUri);
    }

    private void requestPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 1);
//            imagePickerLauncher.launch(intent);
        }
//        else {
//            Toast.makeText(this, "Required a photo to continue !!", Toast.LENGTH_SHORT).show();
//        }
    }
    private void image() {

        storage2.getReference().child("images profiles").child(auth.getCurrentUser().getUid())
                .getDownloadUrl()
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
//                                        task.getResult().
                        if (task.isSuccessful() && task.getResult() != null) {

                            userImage = task.getResult();
                            Glide
                                    .with(requireActivity())
                                    .load(task.getResult())
                                    .into(binding.imPerson);
                        }
                    }
                });
    }

}