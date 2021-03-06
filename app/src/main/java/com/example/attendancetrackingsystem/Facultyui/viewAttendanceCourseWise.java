package com.example.attendancetrackingsystem.Facultyui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.attendancetrackingsystem.Models.AttendanceTrack;
import com.example.attendancetrackingsystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class viewAttendanceCourseWise extends AppCompatActivity {
    TextView textView;
    RecyclerView recyclerView;
    viewAttendanceAdapter adapter;



    DatabaseReference databaseReference;
    Date startDate1;
    Date endDate1;
    ArrayList<AttendanceTrack> attendanceTrackArrayList=new ArrayList<>();
    int count=0;
    int falseCount=0;
    int trueCount=0;
    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty_activity_view_attendance_course_wise);
        String startDate=getIntent().getStringExtra("Start");
        String endDate=getIntent().getStringExtra("End");
        String code=getIntent().getStringExtra("code");
        DateFormat df =new SimpleDateFormat("dd/MM/yyyy");

        try {
            endDate1=df.parse(endDate);
            startDate1=df.parse(startDate);
            Log.d("endddddd", String.valueOf(endDate1));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        textView=findViewById(R.id.cardViewText);
        textView.setText("Attendance report from "+startDate+" to "+endDate+" for course "+code);


        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    ArrayList<String> subjectList= (ArrayList<String>) dataSnapshot.child("subjects").getValue();
                    if(subjectList.contains(code))
                    {
                            String id=dataSnapshot.getKey();
                            String userId=dataSnapshot.child("userid").getValue().toString();
                            databaseReference=FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("Attendance").
                                    child("Subject").child(code);
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    for(DataSnapshot dataSnapshot1:snapshot.getChildren())
                                    {
                                        try {
                                            @SuppressLint("SimpleDateFormat") Date testDate=new SimpleDateFormat("dd-MMM-yyyy")
                                                    .parse(Objects.requireNonNull(dataSnapshot1.getKey()));

                                            assert testDate != null;
                                            if(testDate.getTime()>=startDate1.getTime() && testDate.getTime()<=endDate1.getTime())
                                            {

                                                if(dataSnapshot1.getValue().toString().equals("false"))
                                                {
                                                    count++;
                                                    falseCount++;
                                                }
                                                else {
                                                    trueCount++;
                                                    count++;
                                                }

                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                    attendanceTrackArrayList.add(new AttendanceTrack(userId,trueCount,falseCount,count));
                                    Log.d("Data user",userId+trueCount+falseCount+count);
                                    count=0;
                                    trueCount=0;
                                    falseCount=0;
                                    adapter.notifyDataSetChanged();
                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView=findViewById(R.id.viewAttendanceRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new viewAttendanceAdapter(attendanceTrackArrayList);
        recyclerView.setAdapter(adapter);

    }
}