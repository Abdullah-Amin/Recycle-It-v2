package com.example.recycleit.views.adapter;

import android.content.Context;
import android.util.Log;
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
import com.example.recycleit.views.model.WorkShop;
import com.google.android.material.button.MaterialButton;


import java.util.ArrayList;

public class WorkshopAdaptor extends RecyclerView.Adapter<WorkshopAdaptor.WorkshopViewHolder> {


    ArrayList<WorkShop> workshopList;
    Context context;
    private static final String TAG = "WorkshopAdaptor";

    public WorkshopAdaptor(ArrayList<WorkShop> workshopList, Context context) {
        this.workshopList = workshopList;
        this.context = context;
    }

    @NonNull
    @Override
    public WorkshopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_course,parent,false);
        return new WorkshopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkshopAdaptor.WorkshopViewHolder holder, int position) {

        WorkShop WorkShop=workshopList.get(position);
        holder.workshopName.setText(WorkShop.getWorkshopName());
        holder.workshopDate.setText(WorkShop.getWorkshopDate());
        holder.workshopState.setText(WorkShop.getWorkshopGoal());
        holder.goal_1.setText("1- "+WorkShop.getWorkshopGoal());

    }
    @Override
    public int getItemCount() {
        return workshopList.size();
    }

    public static class WorkshopViewHolder extends RecyclerView.ViewHolder{

        TextView workshopName,workshopDate,workshopState ;
        ImageView displayMore;
        EditText goal_1,goal_2,goal_3;

        ConstraintLayout theGoals;
        MaterialButton button;
        Boolean state=true;
        public WorkshopViewHolder(@NonNull View itemView) {
            super(itemView);

            workshopName=itemView.findViewById(R.id.tv_course);
            workshopDate=itemView.findViewById(R.id.tv_calender);
            workshopState=itemView.findViewById(R.id.tv_state);
            theGoals=itemView.findViewById(R.id.constraint_displaymore);
            displayMore=itemView.findViewById(R.id.im_arrow_down);
            theGoals.setVisibility(View.GONE);
            button=itemView.findViewById(R.id.registrationBtn);
            goal_1=itemView.findViewById(R.id.firstGoalEt);
            goal_2=itemView.findViewById(R.id.secondGoalEt);
            goal_3=itemView.findViewById(R.id.thirdGoalEt);
            displayMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!state){
                        theGoals.setVisibility(View.VISIBLE);
                        Toast.makeText(v.getContext(), "your click me well " + getAdapterPosition(), Toast.LENGTH_SHORT).show();

                    }else {

                        theGoals.setVisibility(View.GONE);
                    }
                    state=!state;
                }


            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 Navigation.findNavController(v).navigate(R.id.action_listWorkshopFragment_to_registerWorkshopFragment);
                    Log.i(TAG, "onClick: you arrive here ");
               //     Toast.makeText(v.getContext(), "handel navigate soon" + getAdapterPosition(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
