package com.example.recycleit.views.adapter;


import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleit.R;
import com.example.recycleit.views.model.firebase.Address;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.AddressViewHolder> {

    ArrayList<Address>addressList=new ArrayList<>();
    Context context;
    public AddressListAdapter(ArrayList<Address> addressList, Context context) {
        this.addressList = addressList;
        this.context = context;
    }
    public AddressListAdapter(){}

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_address,parent,false);
        return new AddressListAdapter.AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressListAdapter.AddressViewHolder holder, int position) {

        Address address=addressList.get(position);
        holder.addressName.setText(address.getFirstname());
        holder.country.setText(address.getCountry());
        holder.addressPhone.setText(address.getPhone());





    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public static class AddressViewHolder extends RecyclerView.ViewHolder{
        Boolean state=true;
        TextView addressName,country,addressPhone ;
        MaterialButton delete,edit;
        private AddressListAdapter adapter;
        private String key ;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            addressName=itemView.findViewById(R.id.item_add_name);
            country=itemView.findViewById(R.id.item_add_address);
            addressPhone=itemView.findViewById(R.id.item_add_phone);
            delete=itemView.findViewById(R.id.it_add_bt_delete);
            edit=itemView.findViewById(R.id.it_add_bt_edit);
         //   key=adapter.addressList.get(getAdapterPosition()).getId();


delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Recycle it database schema").child("address");
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageReference= storage.getReference();
        storageReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                databaseReference.removeValue();
                Toast.makeText(adapter.context, "item Deleted",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(adapter.context, "item failure to delete",Toast.LENGTH_LONG).show();

            }
        });
//        adapter.addressList.remove(getAdapterPosition());
//        adapter.notifyItemRemoved(getAdapterPosition());
      //  Navigation.findNavController(v).navigate(R.id.action_homeAddressFragment_to_editShippingAddressFragment);
//adapter.addressList.remove(getAdapterPosition());

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

    public void removeItem(int position) {
        addressList.remove(position);
        notifyItemRemoved(position);
    }

}