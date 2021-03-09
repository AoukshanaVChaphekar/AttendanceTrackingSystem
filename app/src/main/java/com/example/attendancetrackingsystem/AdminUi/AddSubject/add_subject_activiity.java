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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class add_subject_activiity extends AppCompatActivity implements AddSubjectAdapter.facultySelectedItemListener {

    EditText subjectName;
    EditText subjectCode;
    Button addSubjectButton;
    RecyclerView recyclerView;
    AddSubjectAdapter addSubjectAdapter;
    ProgressDialog progressDialog;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ArrayList<String> facultyId=new ArrayList<>();
    public ArrayList<String> fid=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_add_subject_activiity);
        subjectCode=findViewById(R.id.SubjectCodeEditText);
        subjectName=findViewById(R.id.SubjectNameEditText);


        addSubjectButton=findViewById(R.id.addSubjectButton);

        progressDialog=new ProgressDialog(add_subject_activiity.this);
              progressDialog.setTitle("Adding Subject");
              progressDialog.setMessage("Subject is being added");

        getIds(new facultyCallBack() {
            @Override
            public void onCallBack(ArrayList<String> list) {
                facultyId=list;
                recyclerView=findViewById(R.id.SubjectrecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                addSubjectAdapter= new AddSubjectAdapter(facultyId,add_subject_activiity.this);
                recyclerView.setAdapter(addSubjectAdapter);

            }
        });
        final boolean[] flag = {false};




        addSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subjectCode.getText().toString().trim().length() > 0 && subjectName.getText().toString().trim().length() > 0
                        && fid.size() > 0) {
                    String Ids= String.valueOf(fid);
                    Ids=Ids.substring(1,Ids.length()-1);
                    Log.d("Fid", Ids);

                    String newCode= subjectCode.getText().toString();
                    String subName=subjectName.getText().toString();
                    final String[] id = new String[1];
                    progressDialog.show();
                    databaseReference=FirebaseDatabase.getInstance().getReference().child("Subject");
                    String finalIds = Ids;
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.d("Fid ", String.valueOf(finalIds));
                            for (DataSnapshot dataSnapshot : snapshot.getChildren())
                            {
                                if (dataSnapshot.child("code").getValue().toString().equals(newCode))
                                {
                                    id[0] = dataSnapshot.getKey();
                                    flag[0] = true;
                                    break;
                                }
                            }
                            if (flag[0])
                            {

                                databaseReference.child(id[0]).child("code").setValue(newCode);
                                databaseReference.child(id[0]).child("name").setValue(subName);
                                databaseReference.child(id[0]).child("fid").setValue(Arrays.asList(finalIds));
                            }
                            else
                                {

                                    Subject subject = new Subject(subName, newCode,new ArrayList<String>(Arrays.asList(finalIds)));
                                    database.getReference().child("Subject").push().setValue(subject, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref)
                                    {
                                        if (error != null)
                                            Toast.makeText(getApplicationContext(), "Error occured while adding subject", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    progressDialog.dismiss();
                    subjectCode.setText("");
                    subjectName.setText("");
                    fid.clear();
                    Toast.makeText(getApplicationContext(), "Subject Added Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getIds(facultyCallBack callBack)
    {
        ArrayList<String> list=new ArrayList<>();
        database=FirebaseDatabase.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Faculty");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    String id=dataSnapshot.child("fid").getValue().toString();
                    list.add(id);

                }
                callBack.onCallBack(list);
                addSubjectAdapter.notifyDataSetChanged();


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onItemClicked(ArrayList<String> list)
    {
        fid=list;
    }
    public interface facultyCallBack
    {
        void onCallBack(ArrayList<String> list);
    }
}