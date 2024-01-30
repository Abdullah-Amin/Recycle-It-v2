package com.example.recycleit.views.adapter;


import static androidx.core.content.ContextCompat.createDeviceProtectedStorageContext;
import static androidx.core.content.ContextCompat.getAttributionTag;
import static androidx.core.content.ContextCompat.startActivity;
import static com.example.recycleit.views.repository.CourseRepo.generateIdBasedOnSeconds;

import android.app.FragmentManagerNonConfig;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleit.R;
import com.example.recycleit.views.auth.SharedPreferenceManager;
import com.example.recycleit.views.model.firebase.Address;
import com.example.recycleit.views.view.home.StartActivity;
import com.example.recycleit.views.view.profile.address.EditShippingAddressFragment;
import com.example.recycleit.views.view.profile.address.HomeAddressFragment;
import com.example.recycleit.views.view.profile.address.HomeAddressFragmentDirections;
import com.example.recycleit.views.view.profile.address.ShippingAddressFragment;
import com.example.recycleit.views.view.profile.address.ShippingAddressFragmentDirections;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

//
public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.AddressViewHolder> {

    ArrayList<Address> addressList = new ArrayList<>();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private static final String TAG = "AddressListAdapter";
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private ArrayList<String> key = new ArrayList<>();
    private SharedPreferenceManager preferenceManager=new SharedPreferenceManager();
    private CollectionReference usersCollection =
            firestore.collection("Recycle it database schema")
                    .document("address").collection(auth.getCurrentUser().getUid());


    private DatabaseReference root = db.getReference().child("address");
    Context context;

    public AddressListAdapter(ArrayList<Address> addressList, Context context) {
        this.addressList = addressList;
        this.context = context;


    }



    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_address, parent, false);
        return new AddressListAdapter.AddressViewHolder(view).link(this);
    }



    @Override
    public void onBindViewHolder(@NonNull AddressListAdapter.AddressViewHolder holder, int position) {

        Address address = addressList.get(position);
        holder.addressName.setText(address.getFirstname());
        holder.country.setText(address.getCountry());
        holder.addressPhone.setText(address.getPhone());


        // need to handel this method due to delete when add deprecated  and when remove after last one the app crash
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addressList.isEmpty()) {

                                    firestore.collection("Recycle it database schema")
                                            .document("address")
                                            .collection(auth.getUid())
                                            .document(addressList.get(position).getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        addressList.remove(position);
                                                    notifyItemRemoved(position);
                                                        Toast.makeText(context, "Address deleted", Toast.LENGTH_LONG).show();
                                                        Log.i(TAG, "onComplete: Address deleted");
                                                    }
                                                  //  notifyDataSetChanged();
                                                   notifyItemRemoved(position);
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(context, "Can't delete address because " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                                    Log.i(TAG, "onComplete:Can't delete address because" + e.getLocalizedMessage());
                                                }
                                            });

                                } else {
                                    Toast.makeText(context, "Can't delete address because the list of empty", Toast.LENGTH_LONG).show();

                                }

            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(context, EditShippingAddressFragment.class);
//                intent.putExtra("id",key);
//                context.startActivity(intent);
//                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

//                FragmentManager fragmentManager
//                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//                Bundle args = new Bundle();
//                args.putString("id",key.get(position));

                preferenceManager.setAddKey(context,addressList.get(position).getId());

                Navigation.findNavController(v).navigate(R.id.action_homeAddressFragment_to_editShippingAddressFragment);
            }
        });

    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public static class AddressViewHolder extends RecyclerView.ViewHolder {
        Boolean state = true;
        TextView addressName, country, addressPhone;
        MaterialButton delete, edit;
        private AddressListAdapter adapter;


        private Address address = new Address();


        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);

            addressName = itemView.findViewById(R.id.item_add_name);
            country = itemView.findViewById(R.id.item_add_address);
            addressPhone = itemView.findViewById(R.id.item_add_phone);
            delete = itemView.findViewById(R.id.it_add_bt_delete);
            edit = itemView.findViewById(R.id.it_add_bt_edit);


        }
        public AddressViewHolder link(AddressListAdapter adaptor) {
            this.adapter = adaptor;
            return this;
        }

    }
}