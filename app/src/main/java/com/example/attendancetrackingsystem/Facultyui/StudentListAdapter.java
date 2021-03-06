package com.example.attendancetrackingsystem.Facultyui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancetrackingsystem.Models.User;
import com.example.attendancetrackingsystem.R;

import java.util.ArrayList;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentViewHolder> {

    ArrayList<User> studentArrayList;
    Context context;
    public StudentListAdapter(ArrayList<User> studentArrayList,Context context)
    {
        this.studentArrayList=studentArrayList;
        this.context=context;
    }


    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty_student_list_row,parent,false);
        StudentViewHolder studentViewHolder=new StudentViewHolder(view);
        return studentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {


        User user=studentArrayList.get(position);
        holder.userId.setText("UserId:"+user.getUserid());
        holder.contact.setText("Contact:"+user.getPhnNo());

    }

    @Override
    public int getItemCount() {
        return studentArrayList.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder
    {
        TextView userId;
        TextView contact;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            userId=itemView.findViewById(R.id.UserIdTextView);
            contact=itemView.findViewById(R.id.contactTextView);
        }
    }
}
