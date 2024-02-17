package com.example.recycleit.views.adapter;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recycleit.R;
import com.example.recycleit.databinding.CartItemBinding;
import com.example.recycleit.databinding.ItemFavoriteBinding;
import com.example.recycleit.views.model.Price;
import com.example.recycleit.views.model.PriceI;
import com.example.recycleit.views.model.firebase.PostItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class MyBagAdapter extends RecyclerView.Adapter<MyBagAdapter.Holder> {

    private ArrayList<PostItem> favoriteItem;
    private PriceI priceI;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    FirebaseFirestore store = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    public MyBagAdapter(ArrayList<PostItem> favoriteItem, PriceI priceI) {
        this.favoriteItem = favoriteItem;
        this.priceI = priceI;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(CartItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        if (position == 0){
            Price.totalPrice = 0;
        }
        holder.binding.itemName.setText(favoriteItem.get(position).getUserName());
        holder.binding.itemPrice.setText(favoriteItem.get(position).getPrice());
        Price.totalPrice = Price.totalPrice + Integer.parseInt(favoriteItem.get(position).getPrice().split("\\.")[0]);

        storage.getReference().child(favoriteItem.get(position).getPostId())
                .getDownloadUrl()
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
//                                        task.getResult().
                        if (task.isSuccessful() && task.getResult() != null) {
                            Glide
                                    .with(holder.itemView.getContext())
                                    .load(task.getResult())
                                    .placeholder(R.drawable.wwwww)
                                    .into(holder.binding.itemImage);
                        }
                    }
                });

        priceI.getPrice(Price.totalPrice);
        Log.i("MyBagFragment", "onBindViewHolder: " + favoriteItem.get(position).getPrice().split("\\.")[0]);
        Log.i("MyBagFragment", "onBindViewHolder: " + Price.totalPrice);
//        Log.i("MyBagFragment", "onBindViewHolder: " + favoriteItem.get(position).getPrice().split("\\.")[0]);

        holder.binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                store.collection("Recycle it database schema").document("Orders")
                        .collection(firebaseAuth.getCurrentUser().getUid()).document(favoriteItem.get(position).getItemName()
                                + favoriteItem.get(position).getPrice()
                                + favoriteItem.get(position).getDescription()
                        ).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(view.getContext(), "Item deleted successfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                favoriteItem.remove(position);
                notifyDataSetChanged();
            }
        });

//        Glide
//                .with(holder.itemView.getContext())
//                .load(favoriteItem.get(position).getItemImage())
//                .placeholder(R.drawable.ox)
//                .into(holder.binding.itemImage);
    }

    @Override
    public int getItemCount() {
        return favoriteItem.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        private CartItemBinding binding;

        public Holder(CartItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

