package com.example.attendancetrackingsystem.AdminUi.AddFaculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attendancetrackingsystem.Models.Faculty;
import com.example.attendancetrackingsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddFacultyActivity extends AppCompatActivity {

    EditText FFname;
    EditText FLname;
    EditText FMobNo;
    EditText Femail;
    EditText Fpassword;
    Button FAdd;
    EditText Fid;
    ProgressDialog progressDialog;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_add_faculty);

        auth= FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();




        FFname=findViewById(R.id.FacutlyfNameEditText);
        FLname=findViewById(R.id.FacultylNameEditText);
        FMobNo=findViewById(R.id.FacultyphoneNoEditText);
        Femail=findViewById(R.id.FacultyemailEditText);
        Fid=findViewById(R.id.FacultyIdEditText);
        Fpassword=findViewById(R.id.FacultypasswordEditText);
        FAdd=findViewById(R.id.addFacultyButton);

        progressDialog=new ProgressDialog(AddFacultyActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Account is being created");

        FAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                auth.createUserWithEmailAndPassword(Femail.getText().toString(),Fpassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if(task.isSuccessful())
                                {
                                    Faculty faculty=new Faculty(Fid.getText().toString(),FFname.getText().toString(),FLname.getText().toString(),Fpassword.getText().toString(),
                                            Femail.getText().toString(),FMobNo.getText().toString());

                                    database.getReference().child("Faculty").push().setValue(faculty);
                                    Femail.setText("");
                                    Fid.setText("");
                                    Fpassword.setText("");
                                    FFname.setText("");
                                    FLname.setText("");
                                    FMobNo.setText("");

                                    Toast.makeText(getApplicationContext(),"Faculty Created Successfully!",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(AddFacultyActivity.this, AddFacultyFragment.class));
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }
        });





    }


}