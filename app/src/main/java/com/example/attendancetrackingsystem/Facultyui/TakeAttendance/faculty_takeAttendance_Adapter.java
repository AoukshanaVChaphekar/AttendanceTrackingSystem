package com.example.attendancetrackingsystem.Facultyui.TakeAttendance;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancetrackingsystem.Models.User;
import com.example.attendancetrackingsystem.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class faculty_takeAttendance_Adapter extends RecyclerView.Adapter<faculty_takeAttendance_Adapter.TakeAttendanceViewHolder>  {

    ArrayList<User> userArrayList;
    takeAttendanceListener listener;
    TakeAttendanceViewHolder takeAttendanceViewHolder;

    public  faculty_takeAttendance_Adapter(ArrayList<User> users,takeAttendanceListener listener)
    {
        userArrayList=users;
        this.listener=listener;
    }

    @NonNull
    @Override
    public TakeAttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty_take_attendance_list_row,parent,false);
         takeAttendanceViewHolder=new TakeAttendanceViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return takeAttendanceViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TakeAttendanceViewHolder holder, int position) {
            User user=userArrayList.get(position);
            holder.uId.setText("User id:"+user.getUserid());
//           holder.present.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View view) {
//                   flag[0] =true;
//               }
//           });
//           holder.absent.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View view) {
//                   flag[0]=false;
//               }
//           });
//           Log.d("positon", String.valueOf(holder.getAdapterPosition()));
//        listener.onClicked(userArrayList.get(holder.getAdapterPosition()),flag[0]);
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

//    @Override
//    public void onClick(View view) {
//        int position;
//        boolean flag;
//        switch(view.getId())
//        {
//            case R.id.falseAbsent:
//                flag=false;
//                break;
//            case R.id.truePresent:
//                flag=true;
//                break;
//            default:
//                throw new IllegalStateException("Unexpected value: " + view.getId());
//        }
//
//
//
//    }

    class TakeAttendanceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView uId;
        RadioButton present;
        RadioButton absent;

        public TakeAttendanceViewHolder(@NonNull View itemView) {
            super(itemView);

            uId=itemView.findViewById(R.id.uIdTextView);
            present=itemView.findViewById(R.id.truePresent);
            absent=itemView.findViewById(R.id.falseAbsent);

            present.setOnClickListener(this);
            absent.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            boolean flag;
        switch(view.getId())
        {
            case R.id.falseAbsent:
                flag=false;
                break;
            case R.id.truePresent:
                flag=true;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
            Log.d("positon", String.valueOf(getAdapterPosition()));
            listener.onClicked(userArrayList.get(getAdapterPosition()),flag);

        }
    }
}
