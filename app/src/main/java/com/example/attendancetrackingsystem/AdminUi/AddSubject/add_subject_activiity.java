package com.example.attendancetrackingsystem.AdminUi.AddSubject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attendancetrackingsystem.Models.Subject;
import com.example.attendancetrackingsystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class add_subject_activiity extends AppCompatActivity {

    EditText subjectName;
    EditText subjectCode;
    Button addSubjectButton;
    RecyclerView recyclerView;
    AddSubjectAdapter addSubjectAdapter;
    ProgressDialog progressDialog;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ArrayList<String> facultyId;

    String fid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_add_subject_activiity);
        subjectCode=findViewById(R.id.SubjectCodeEditText);
        subjectName=findViewById(R.id.SubjectNameEditText);

        facultyId=new ArrayList<>();

        addSubjectButton=findViewById(R.id.addSubjectButton);

        progressDialog=new ProgressDialog(add_subject_activiity.this);
              progressDialog.setTitle("Adding Subject");
              progressDialog.setMessage("Subject is being added");


        database=FirebaseDatabase.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Faculty");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                facultyId.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    String id=dataSnapshot.child("fid").getValue().toString();
                    facultyId.add(id);
                }
                addSubjectAdapter.notifyDataSetChanged();


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView=findViewById(R.id.SubjectrecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        addSubjectAdapter= new AddSubjectAdapter(facultyId);

        recyclerView.setAdapter(addSubjectAdapter);

        ArrayList<String> fid=addSubjectAdapter.getid();


        addSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();


                Subject subject=new Subject(subjectName.getText().toString(),subjectCode.getText().toString(),fid);

                        database.getReference().child("Subject").push().setValue(subject, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if(error!=null)
                                    Toast.makeText(getApplicationContext(),"Error occured while adding subject",Toast.LENGTH_SHORT).show();
                            }
                        });
                        progressDialog.dismiss();
                        subjectCode.setText("");
                        subjectName.setText("");
                        fid.clear();
                        Toast.makeText(getApplicationContext(), "Subject Added Successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(add_subject_activiity.this,AddSubjectFragment.class));
            }
        });
    }
}