package com.example.recycleit.views.view.profile.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.recycleit.R;
import com.example.recycleit.databinding.FragmentUserDetailsBinding;
import com.example.recycleit.views.view.auth.LoginActivity;
import com.example.recycleit.views.model.local.User;
import com.example.recycleit.views.view.courses.CourseViewModel;
import com.example.recycleit.views.view.myorder.MyOrderActivity;
import com.example.recycleit.views.view.profile.post.MyPostActivity;
import com.example.recycleit.views.view.profile.about.AboutActivity;
import com.example.recycleit.views.view.profile.address.AddressActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class UserDetailsFragment extends Fragment {
    private FragmentUserDetailsBinding binding;
    private static final String TAG = "UserDetailsFragment";
    private ViewModelUser viewModel;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage2 = FirebaseStorage.getInstance();

    private DocumentReference usersCollection = db.collection("Recycle it database schema").document("users").collection("regular").document(auth.getUid());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(ViewModelUser.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        image();
        // binding.txName.setText("Hello, "+viewModel.getUserName());


        binding.cardMyPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(requireActivity(), "you didn't create any payment method yet.", Toast.LENGTH_LONG).show();
            }
        });
        usersCollection.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(requireContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onEvent: " + error.getLocalizedMessage());
                    return;
                }

                Log.i(TAG, "onEvent: " + value);
                binding.txName.setText("Hello, " + value.toObject(User.class).getName());
                //   binding.txName.setText("Hello, "+viewModel.getUserName());

            }
        });

        binding.cardNumberOsPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.imSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoEdit();

            }
        });
        binding.cardAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigation.findNavController(requireView()).navigate(R.id.action_aboutFragment3_to_navigation_userDetailsFragment);
                goToAbout2();
            }
        });
        binding.cardAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddress();
            }
        });
        binding.cardMyPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMyPost();

            }
        });
        binding.cardMyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMyOrder();


            }
        });


    }


    private void gotoMyOrder() {
        Intent intent = new Intent(requireContext(), MyOrderActivity.class);
        startActivity(intent);
    }

    private void gotoMyPost() {
        Intent intent = new Intent(requireContext(), MyPostActivity.class);
        startActivity(intent);
        //       Navigation.findNavController(requireView()).navigate(R.id.action_navigation_userDetailsFragment_to_myPostFragment);
    }

    private void gotoAbout() {


    }


    private void gotoAddress() {
        Intent intent = new Intent(requireContext(), AddressActivity.class);
        startActivity(intent);
    }


    private void gotoEdit() {
        Intent intent = new Intent(requireContext(), ProfileActivity.class);
        startActivity(intent);
    }


    private void goToLogin() {
        Intent intent = new Intent(requireContext(), LoginActivity.class);
        startActivity(intent);
        Log.i(TAG, "goToLogin: you did logout");
    }

    private void goToAbout2() {
        Intent intent = new Intent(requireContext(), AboutActivity.class);
        startActivity(intent);
    }

    private void image() {

        storage2.getReference().child("images profiles").child(auth.getCurrentUser().getUid())
                .getDownloadUrl()
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
//                                        task.getResult().
                        if (task.isSuccessful() && task.getResult() != null) {
                            Glide
                                    .with(requireActivity())
                                    .load(task.getResult())
                                    .into(binding.profileImage);
                        }
                    }
                });
    }
}