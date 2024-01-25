package com.example.recycleit.views.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recycleit.R;
import com.example.recycleit.databinding.CartItemBinding;
import com.example.recycleit.databinding.ItemFavoriteBinding;
import com.example.recycleit.views.model.Price;
import com.example.recycleit.views.model.PriceI;
import com.example.recycleit.views.model.firebase.PostItem;

import java.util.ArrayList;

public class MyBagAdapter extends RecyclerView.Adapter<MyBagAdapter.Holder> {

    private ArrayList<PostItem> favoriteItem;
    private PriceI priceI;

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
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.binding.itemName.setText(favoriteItem.get(position).getUserName());
        holder.binding.itemPrice.setText(favoriteItem.get(position).getPrice());
        Price.totalPrice = Price.totalPrice + Integer.parseInt(favoriteItem.get(position).getPrice().split("\\.")[0]);
//        priceI.getPrice(Integer.parseInt(favoriteItem.get(position).getPrice().split("\\.")[0]));
        priceI.getPrice(Price.totalPrice);
        Log.i("MyBagFragment", "onBindViewHolder: " + favoriteItem.get(position).getPrice().split("\\.")[0]);
        Log.i("MyBagFragment", "onBindViewHolder: " + Price.totalPrice);
//        Log.i("MyBagFragment", "onBindViewHolder: " + favoriteItem.get(position).getPrice().split("\\.")[0]);

        Glide
                .with(holder.itemView.getContext())
                .load(favoriteItem.get(position).getItemImage())
                .placeholder(R.drawable.ox)
                .into(holder.binding.itemImage);
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

