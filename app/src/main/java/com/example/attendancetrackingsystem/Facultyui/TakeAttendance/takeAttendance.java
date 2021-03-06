package com.example.attendancetrackingsystem.Facultyui.TakeAttendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.attendancetrackingsystem.Models.User;
import com.example.attendancetrackingsystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class takeAttendance extends AppCompatActivity implements takeAttendanceListener {

    ArrayList<User> userArrayList=new ArrayList<>();
    DatabaseReference databaseReference;

    String code;
    RecyclerView recyclerView;
    faculty_takeAttendance_Adapter faculty_takeAttendance_adapter;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty_activity_take_attendance);

        code=getIntent().getStringExtra("code");

        Log.d("code",code);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userArrayList.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    ArrayList<String> codes= (ArrayList<String>) snapshot1.child("subjects").getValue();
                    if(codes.contains(code))
                    {
                        String uId=snapshot1.child("userid").getValue().toString();
                        userArrayList.add(new User(uId));
                    }
                }
                faculty_takeAttendance_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        recyclerView=findViewById(R.id.takeAttendanceRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        faculty_takeAttendance_adapter=new faculty_takeAttendance_Adapter(userArrayList,takeAttendance.this);
        recyclerView.setAdapter(faculty_takeAttendance_adapter);


    }

    @Override
    public void onClicked(User user, boolean attendance) {


        Date d= Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(d);
        Log.d("date",formattedDate);

        HashMap<String,Object> values=new HashMap<>();
        values.put(formattedDate,attendance);
      databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");
      databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              for(DataSnapshot snapshot1:snapshot.getChildren())
              {
                  if((snapshot1.child("userid").getValue().toString()).equals(user.getUserid()))
                  {
                     String id= snapshot1.getKey();
                    databaseReference=FirebaseDatabase.getInstance().getReference().child("Users").child(id);
                    databaseReference.child("Attendance").child("Subject").child(code).updateChildren(values);
                  }
              }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });

    }
}