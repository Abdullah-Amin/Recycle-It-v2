package com.example.recycleit.views.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recycleit.R;
import com.example.recycleit.databinding.ItemMyPostBinding;
import com.example.recycleit.views.model.firebase.PostItem;

import java.util.ArrayList;

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.Holder> {

    ArrayList<PostItem> items;
    public MyPostAdapter(ArrayList<PostItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(ItemMyPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Log.i("MyPostAdapter", "onBindViewHolder: " + position);
        Glide
                .with(holder.itemView.getContext())
                .load(items.get(position).getItemImage())
                .placeholder(R.drawable.wwwww)
                .into(holder.binding.itImage);

        holder.binding.desc.setText(items.get(position).getDescription());
        holder.binding.itHeader.setText(items.get(position).getCaption());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        ItemMyPostBinding binding;
        public Holder(@NonNull ItemMyPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
