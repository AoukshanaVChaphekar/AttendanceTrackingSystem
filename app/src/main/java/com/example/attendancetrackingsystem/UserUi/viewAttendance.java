package com.example.attendancetrackingsystem.UserUi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendancetrackingsystem.Models.UserAttendanceTrack;
import com.example.attendancetrackingsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class viewAttendance extends AppCompatActivity {

    String code;
    String sDate;
    String eDate;
    String fid;
    Date date;
    Date startDate;
    Date endDate;
    int present=0;
    int absent=0;
    int count=0;
    TextView presentPer;
    TextView absentPer;
    RecyclerView recyclerView;
    viewAttendanceAdapter adapter;

    DatabaseReference databaseReference;

    ArrayList<UserAttendanceTrack> userAttendanceTracks=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_view_attendance);

        code=getIntent().getStringExtra("code");
        sDate=getIntent().getStringExtra("Start");
        eDate=getIntent().getStringExtra("End");
        DecimalFormat data = new DecimalFormat("0.00");

        presentPer=findViewById(R.id.present);
        absentPer=findViewById(R.id.absent);

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String email=firebaseUser.getEmail();


        databaseReference=FirebaseDatabase.getInstance().getReference().child("Subject");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fid="";
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    if(dataSnapshot.child("code").getValue().toString().equals(code))
                    {
                        fid=dataSnapshot.child("fid").getValue().toString();
                        fid=fid.substring(1,fid.length()-1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DateFormat df =new SimpleDateFormat("dd/MM/yyyy");


        try {
            endDate=df.parse(eDate);
            startDate=df.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count=0;
                present=0;
                absent=0;

                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    if(dataSnapshot.child("userMail").getValue().toString().equalsIgnoreCase(email))
                    {
                        String id=dataSnapshot.getKey();
                        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("Attendance");
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.child("Subject").child(code).exists()) {
                                    userAttendanceTracks.clear();
                                    HashMap<String, String> codes = (HashMap<String, String>) snapshot.child("Subject").child(code).getValue();
                                    Log.d("code", codes.toString());
                                    Iterator it = codes.entrySet().iterator();
                                    while (it.hasNext()) {
                                        Map.Entry pair = (Map.Entry) it.next();
                                        try {
                                            @SuppressLint("SimpleDateFormat") Date testDate = new SimpleDateFormat("dd-MMM-yyyy")
                                                    .parse(String.valueOf((pair.getKey())));
                                            assert testDate != null;
                                            if (testDate.getTime() >= startDate.getTime() && testDate.getTime() <= endDate.getTime()) {

                                                String date = df.format(testDate);
                                                userAttendanceTracks.add(new UserAttendanceTrack(date, code, fid, (Boolean) pair.getValue()));
                                                if ((Boolean) pair.getValue()) {
                                                    present++;
                                                } else
                                                    absent++;
                                            }


                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        it.remove();

                                    }

                                    adapter.notifyDataSetChanged();
                                    count = userAttendanceTracks.size();
                                    double p = (double) present * 100 / count;
                                    double a = (double) (100 * absent) / count;
                                    p = p * Math.pow(10, 2);
                                    p = Math.floor(p);
                                    p = p / Math.pow(10, 2);

                                    a = a * Math.pow(10, 2);
                                    a = Math.floor(a);
                                    a = a / Math.pow(10, 2);
                                    presentPer.setText(p + "%");
                                    absentPer.setText(a + "%");
                                } else {
                                    Toast.makeText(getApplicationContext(), "Not record present", Toast.LENGTH_SHORT).show();
                                }
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
        recyclerView=findViewById(R.id.userRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new viewAttendanceAdapter(userAttendanceTracks);

        recyclerView.setAdapter(adapter);
    }
}