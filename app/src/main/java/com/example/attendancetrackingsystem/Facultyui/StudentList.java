package com.example.attendancetrackingsystem.Facultyui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.attendancetrackingsystem.Models.User;
import com.example.attendancetrackingsystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentList extends AppCompatActivity {

    RecyclerView recyclerView;
    StudentListAdapter studentListAdapter;

    ArrayList<User> userArrayList=new ArrayList<>();
    DatabaseReference databaseReference;

    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty_activity_student_list);

        code=getIntent().getStringExtra("code");


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
                        String contact=snapshot1.child("phnNo").getValue().toString();
                        userArrayList.add(new User(uId,contact));
                    }
                }
                studentListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        recyclerView=findViewById(R.id.studentListRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        studentListAdapter=new StudentListAdapter(userArrayList,getApplicationContext());
        recyclerView.setAdapter(studentListAdapter);
    }
}