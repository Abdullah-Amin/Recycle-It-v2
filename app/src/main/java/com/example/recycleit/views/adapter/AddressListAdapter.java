package com.example.recycleit.views.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleit.R;
import com.example.recycleit.views.model.firebase.Address;
import com.example.recycleit.views.model.firebase.CourseB;

import java.util.ArrayList;
import java.util.List;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.AddressListViewHolder> {

    ArrayList<Address> addresses;
    Context context;

    public AddressListAdapter(ArrayList<Address> addresses, Context context) {
        this.addresses = addresses;
        this.context = context;
    }

    @NonNull
    @Override
    public AddressListAdapter.AddressListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_address,parent,false);
        return new AddressListAdapter.AddressListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressListAdapter.AddressListViewHolder holder, int position) {

        Address addressA=addresses.get(position);
        holder.addressName.setText(addressA.getFirstname());
        holder.addressDate.setText(addressA.getCountry());
        holder.addressState.setText(addressA.getPhone());


    }

    @Override
    public int getItemCount() {
        return  addresses.size();
    }

    public static class AddressListViewHolder extends RecyclerView.ViewHolder{

        TextView addressName,addressDate,addressState ;
        public AddressListViewHolder(@NonNull View itemView) {
            super(itemView);
            addressName=itemView.findViewById(R.id.tv_course);
            addressDate=itemView.findViewById(R.id.tv_calender);
            addressState=itemView.findViewById(R.id.tv_state);

        }


    }
}