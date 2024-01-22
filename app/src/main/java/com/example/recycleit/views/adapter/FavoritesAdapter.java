package com.example.recycleit.views.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recycleit.R;
import com.example.recycleit.databinding.ItemFavoriteBinding;
import com.example.recycleit.views.model.firebase.PostItem;

import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.Holder> {

    private ArrayList<PostItem> favoriteItem;

    public FavoritesAdapter(ArrayList<PostItem> favoriteItem) {
        this.favoriteItem = favoriteItem;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemFavoriteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.binding.itTvPrice.setText(favoriteItem.get(position).getPrice());

        Glide
                .with(holder.itemView.getContext())
                .load(favoriteItem.get(position).getItemImage())
                .placeholder(R.drawable.ox)
                .into(holder.binding.imItemFav);
    }

    @Override
    public int getItemCount() {
        return favoriteItem.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        private ItemFavoriteBinding binding;
        public Holder(ItemFavoriteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
