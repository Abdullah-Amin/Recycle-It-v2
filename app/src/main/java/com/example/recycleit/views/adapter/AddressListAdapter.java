package com.example.recycleit.views.adapter;


import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleit.R;
import com.example.recycleit.views.model.firebase.Address;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.AddressViewHolder> {

    ArrayList<Address>addressList=new ArrayList<>();
    Context context;

    public AddressListAdapter(ArrayList<Address> addressList, Context context) {
        this.addressList = addressList;
        this.context = context;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_address,parent,false);
        return new AddressListAdapter.AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressListAdapter.AddressViewHolder holder, int position) {

        Address address=addressList.get(position);
        holder.courseName.setText(address.getFirstname());
        holder.courseDate.setText(address.getCountry());
        holder.courseState.setText(address.getPhone());




    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public static class AddressViewHolder extends RecyclerView.ViewHolder{
        Boolean state=true;
        TextView courseName,courseDate,courseState ;
        MaterialButton delete,edit;

        private AddressListAdapter adapter;
        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName=itemView.findViewById(R.id.item_add_name);
            courseDate=itemView.findViewById(R.id.item_add_address);
            courseState=itemView.findViewById(R.id.item_add_phone);
            delete=itemView.findViewById(R.id.it_add_bt_delete);
            edit=itemView.findViewById(R.id.it_add_bt_edit);

delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Navigation.findNavController(v).navigate(R.id.action_homeAddressFragment_to_editShippingAddressFragment);
//adapter.addressList.remove(getAdapterPosition());
adapter.notifyDataSetChanged();
    }
});
edit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Navigation.findNavController(v).navigate(R.id.action_homeAddressFragment_to_editShippingAddressFragment);


    }
});
        }





    }
}