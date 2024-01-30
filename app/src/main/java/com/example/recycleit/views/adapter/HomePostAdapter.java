package com.example.recycleit.views.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recycleit.R;
import com.example.recycleit.databinding.PostItemBinding;
import com.example.recycleit.views.auth.SharedPreferenceManager;
import com.example.recycleit.views.auth.Status;
import com.example.recycleit.views.auth.UserType;
import com.example.recycleit.views.model.firebase.PostItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomePostAdapter extends RecyclerView.Adapter<HomePostAdapter.Holder> {

    private static final String TAG = "HomePostAdapter";
    private ArrayList<PostItem> items=new ArrayList<>();
    StorageReference reference = FirebaseStorage.getInstance().getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    FirebaseFirestore store = FirebaseFirestore.getInstance();

    public HomePostAdapter(ArrayList<PostItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(PostItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        PostItemBinding binding = holder.binding;

        Log.i(TAG, "onBindViewHolder: " + items);

        SharedPreferenceManager manager = new SharedPreferenceManager();

        binding.itemName.setText(items.get(position).getCaption());
        binding.itemPrice.setText(items.get(position).getPrice());
        binding.nameTxt.setText(items.get(position).getUserName());

//        reference.child("profileImages")
//                .child(firebaseAuth.getUid())
//                .getDownloadUrl()
//                .addOnCompleteListener(new OnCompleteListener<Uri>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Uri> task) {
//                        if (task.isSuccessful() && task.getResult() != null) {
                            Glide.with(holder.itemView.getContext())
                                    .load(items.get(position).getItemImage())
                                    .placeholder(R.drawable.ox).into(binding.itemImage);
//                        }
//                    }
//                });

//        if (items.get(position).getItemImage() != null) {
////            try {
////                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
////                        holder.itemView.getContext().getContentResolver(), items.get(position).getItemImage());
//            Glide
//                    .with(holder.itemView.getContext())
//                    .load(items.get(position).getItemImage())
//                    .into(binding.itemImage);
////            } catch (IOException e) {
////                throw new RuntimeException(e);
////            }
//        }

//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        storage.getReference().child("profileImage").child("posts")
//                .getDownloadUrl()
//                .addOnCompleteListener(new OnCompleteListener<Uri>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Uri> task) {
////                                        task.getResult().
//                        if (task.isSuccessful() && task.getResult() != null) {
//                            Glide.with(holder.itemView.getContext())
//                                    .load(items.get(position).getItemImage())
//                                    .placeholder(R.drawable.ox).into(binding.itemImage);
//                        }
//                    }
//                });

        if (manager.getType(holder.itemView.getContext()) == UserType.GUEST.getType()) {
            binding.addOrderBtn.setVisibility(View.INVISIBLE);
            binding.favouriteImage.setVisibility(View.INVISIBLE);
            return;
        }

        binding.addOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                store.collection("Recycle it database schema").document("Orders")
//                        .collection(firebaseAuth.getCurrentUser().getUid()).document(items.get(position).getCaption()
//                                + items.get(position).getPrice()
//                                + items.get(position).getDescription()
//                        ).set(items.get(position))
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()){
//                                    Toast.makeText(view.getContext(), "Added to cart successfully", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
            }
        });

        binding.favouriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                store.collection("Recycle it database schema").document("Favorites")
                        .collection(firebaseAuth.getCurrentUser().getUid()).document(items.get(position).getCaption()
                                + items.get(position).getPrice()
                                + items.get(position).getDescription()
                        ).set(items.get(position))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(view.getContext(), "Added to favorites successfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        binding.imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              manager.setPostItem(v.getContext(), items.get(position));

                Navigation.findNavController(v).navigate(R.id.action_navigation_homeFragment_to_postFragment2);

            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private PostItemBinding binding;

        public Holder(@NonNull PostItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public void Search(ArrayList<PostItem>searchList) {
        this.items=searchList;
        notifyDataSetChanged();
    }

}
