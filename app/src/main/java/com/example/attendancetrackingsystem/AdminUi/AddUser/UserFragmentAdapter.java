package com.example.attendancetrackingsystem.AdminUi.AddUser;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancetrackingsystem.Models.User;
import com.example.attendancetrackingsystem.R;

import java.util.ArrayList;


public class UserFragmentAdapter extends RecyclerView.Adapter<UserFragmentAdapter.FragmentViewHolder> {

    ArrayList<User> userArrayList;

    public UserFragmentAdapter(ArrayList<User> stringArrayList)
    {
        userArrayList=stringArrayList;
    }

    @NonNull
    @Override
    public FragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_list_row2,parent,false);

        FragmentViewHolder fragmentViewHolder=new FragmentViewHolder(view);


        return fragmentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position) {
        User user=userArrayList.get(position);
        Log.d("uid",user.getUserid());
        holder.id.setText("Id: "+user.getUserid());
        holder.courses.setText("Course: "+user.getSubjects());
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    class FragmentViewHolder extends RecyclerView.ViewHolder
    {
        TextView id;
        TextView courses;

        public FragmentViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.userId);
            courses=itemView.findViewById(R.id.course);

        }
    }
}
