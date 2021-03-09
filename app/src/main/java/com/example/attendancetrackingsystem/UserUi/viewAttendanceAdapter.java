package com.example.attendancetrackingsystem.UserUi;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancetrackingsystem.Models.UserAttendanceTrack;
import com.example.attendancetrackingsystem.R;

import java.util.ArrayList;

public class viewAttendanceAdapter extends RecyclerView.Adapter<viewAttendanceAdapter.UserviewHolder> {
    ArrayList<UserAttendanceTrack> userAttendanceTrackArrayList;
    public viewAttendanceAdapter(ArrayList<UserAttendanceTrack> list)
    {
        userAttendanceTrackArrayList=list;

    }
    @NonNull
    @Override
    public UserviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_view_attendance_list_row,parent,false);
        UserviewHolder userviewHolder=new UserviewHolder(view);
        return userviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserviewHolder holder, int position) {
        UserAttendanceTrack userAttendanceTrack=userAttendanceTrackArrayList.get(position);
        holder.code.setText(userAttendanceTrack.getCode());
        holder.date.setText(userAttendanceTrack.getDate());
        holder.Fid.setText(userAttendanceTrack.getFaculty());
        if(userAttendanceTrack.isStatus())
        {
            holder.status.setText("P");
        }
        else
            holder.status.setText("F");


    }

    @Override
    public int getItemCount() {
        return userAttendanceTrackArrayList.size();
    }

    class UserviewHolder extends RecyclerView.ViewHolder{

        TextView code;
        TextView date;
        TextView Fid;
        TextView status;
        public UserviewHolder(@NonNull View itemView) {
            super(itemView);

            code=itemView.findViewById(R.id.courseCodeTextView);
            date=itemView.findViewById(R.id.Attdate);
            Fid=itemView.findViewById(R.id.facultyIdTextView);
            status=itemView.findViewById(R.id.statusText);

        }
    }

}
