package com.example.recycleit.views.adapter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recycleit.databinding.PostItemBinding;
import com.example.recycleit.views.model.firebase.PostItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomePostAdapter extends RecyclerView.Adapter<HomePostAdapter.Holder> {

    private static final String TAG = "HomePostAdapter";
    private ArrayList<PostItem> items;
    StorageReference reference = FirebaseStorage.getInstance().getReference();
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    public HomePostAdapter(ArrayList<PostItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(PostItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        PostItemBinding binding = holder.binding;

        Log.i(TAG, "onBindViewHolder: " + items);


        reference.child("profileImages")
                .child(firebaseAuth.getUid())
                .getDownloadUrl()
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful() && task.getResult() != null){
                            Glide.with(holder.itemView.getContext()).load(task.getResult()).into(binding.itemImage);
                        }
                    }
                });

        if (items.get(position).getItemImage() != null){
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
//                        holder.itemView.getContext().getContentResolver(), items.get(position).getItemImage());
                Glide
                        .with(holder.itemView.getContext())
                        .load(items.get(position).getItemImage())
                        .into(binding.itemImage);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        }

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storage.getReference().child("profileImage").child("posts")
                        .getDownloadUrl()
                                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
//                                        task.getResult().
                                        if (task.isSuccessful() && task.getResult() != null){
                                            Glide
                                                    .with(holder.itemView.getContext())
                                                    .load(task.getResult())
                                                    .into(binding.itemImage);
                                        }
                                    }
                                });

        binding.itemName.setText(items.get(position).getCaption());
        binding.itemPrice.setText(items.get(position).getPrice());
        binding.nameTxt.setText(items.get(position).getUserName());
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

}
