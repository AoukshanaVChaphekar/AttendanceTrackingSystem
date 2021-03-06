package com.example.attendancetrackingsystem.UserUi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancetrackingsystem.Models.Subject;
import com.example.attendancetrackingsystem.R;

import java.util.ArrayList;

public class homeFragmentAdapter extends RecyclerView.Adapter<homeFragmentAdapter.HomeFragmentViewHolder> {

    ArrayList<Subject> subjectArrayList;
    public homeFragmentAdapter(ArrayList<Subject> subjects)
    {
        subjectArrayList=subjects;
    }

    @NonNull
    @Override
    public HomeFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_home_fragment_list_row,parent,false);
        HomeFragmentViewHolder homeFragmentViewHolder=new HomeFragmentViewHolder(view);



        return homeFragmentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFragmentViewHolder holder, int position) {
        Subject subject=subjectArrayList.get(position);
        holder.code.setText(subject.getCode());
        holder.name.setText(subject.getName());
        holder.fid.setText(subject.getFid().get(0));
    }

    @Override
    public int getItemCount() {
        return subjectArrayList.size();
    }

    class HomeFragmentViewHolder extends RecyclerView.ViewHolder{


        TextView code;
        TextView name;
        TextView fid;
        public HomeFragmentViewHolder(@NonNull View itemView) {
            super(itemView);
            code=itemView.findViewById(R.id.codeT);
            name=itemView.findViewById(R.id.nameT);
            fid=itemView.findViewById(R.id.facultyT);
        }
    }
}
