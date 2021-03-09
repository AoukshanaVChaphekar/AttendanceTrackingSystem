package com.example.attendancetrackingsystem.Facultyui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancetrackingsystem.Models.AttendanceTrack;
import com.example.attendancetrackingsystem.R;

import java.util.ArrayList;

public class viewAttendanceAdapter extends RecyclerView.Adapter<viewAttendanceAdapter.viewAttendanceViewHolder>{

    ArrayList<AttendanceTrack> attendanceTrackArrayList;
    public viewAttendanceAdapter(ArrayList<AttendanceTrack> users)
    {
        attendanceTrackArrayList=users;
    }
    @NonNull
    @Override
    public viewAttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty_view_attendance_list_row,parent,false);
        viewAttendanceViewHolder viewHolder=new viewAttendanceViewHolder(view);

        return  viewHolder;
    }

    @Override
    public int getItemCount() {
        return attendanceTrackArrayList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull viewAttendanceViewHolder holder, int position) {
        AttendanceTrack user=attendanceTrackArrayList.get(position);
        holder.studentId.setText(user.getUserId());
        double percentPresent=(double)(user.getPresentCount()*100)/user.getTotalCount();
        double percentAbsent=(double)(user.getAbsentCount()*100)/user.getTotalCount();

        percentPresent = percentPresent * Math.pow(10,2);
        percentPresent = Math.floor(percentPresent);
        percentPresent = percentPresent / Math.pow(10,2);

        percentAbsent = percentAbsent * Math.pow(10,2);
        percentAbsent = Math.floor(percentAbsent);
        percentAbsent = percentAbsent / Math.pow(10,2);

        holder.present.setText(user.getPresentCount()+"("+percentPresent+"%)");
        holder.absent.setText(user.getAbsentCount()+"("+percentAbsent+"%)");


    }

    class viewAttendanceViewHolder extends RecyclerView.ViewHolder{

        TextView studentId;
        TextView present;
        TextView absent;


        public viewAttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            studentId=itemView.findViewById(R.id.studentIdText);
            present=itemView.findViewById(R.id.presentIdText);
            absent=itemView.findViewById(R.id.absentIdText);


        }
    }
}
