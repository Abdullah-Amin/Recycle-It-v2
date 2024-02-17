package com.example.recycleit.views;

import static android.app.Activity.RESULT_OK;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.recycleit.databinding.FragmentCaptionBinding;
import com.example.recycleit.views.auth.Status;
import com.example.recycleit.views.model.firebase.PostItem;
import com.example.recycleit.views.model.local.User;
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
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;


public class CaptionFragment extends Fragment {

    private FragmentCaptionBinding binding;
    FirebaseStorage storage2 = FirebaseStorage.getInstance();
    private HomeViewModel viewModel;

//    ActivityResultLauncher<Intent> imagePickerLauncher = null;
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

            Log.i("abdo", "onActivityResult: path: " + bitmap.toString());

//            uploadPostToServer();
        }
    }


    private void uploadPostToServer() {
        String desc = binding.descriptionEt.getText().toString().trim();
        String price = binding.priceEt.getText().toString().trim();
        String name = binding.nameEt.getText().toString().trim();
        if (desc.isEmpty() || price.isEmpty() || name.isEmpty()) {
            Toast.makeText(requireContext(), "Caption or price can't be empty", Toast.LENGTH_SHORT).show();
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


                String postId = generateIdBasedOnSeconds() + "";
//                    Log.i(TAG, "uploadPostToServer: on event - " + new URL(imageUri.toString()));
                viewModel.upload(
                        new PostItem(user.getName(), userImage + "", auth.getCurrentUser().getUid(), postId, imageUri + "", name, price +".SR", desc + ""),
                        requireContext(),
                        requireView()
                );

                storageReference.child(postId)
                        .putFile(imageUri)
                        .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                Log.i(TAG, "onComplete: image uploaded");
                            }
                        });
            }
        });
        Log.i(TAG, "uploadPostToServer: end - " + imageUri);
    }

    private void requestPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
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

    public static long generateIdBasedOnSeconds() {
        long currentTimestampSeconds = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentTimestampSeconds = Instant.now().getEpochSecond();
        }

        // You can use additional logic to modify or manipulate the timestamp as needed
        // For simplicity, this example just returns the current timestamp as the ID
        return currentTimestampSeconds;
    }

}