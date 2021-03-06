package com.example.attendancetrackingsystem;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FacultyLogin extends AppCompatActivity {

    EditText Femail;
    EditText Fpassword;
    Button Flogin;
    FirebaseAuth auth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty_activity_faculty_login);

        Femail=findViewById(R.id.FemailEditText);
        Fpassword=findViewById(R.id.FpasswordEditText);
        Flogin=findViewById(R.id.Flogin);

        progressDialog=new ProgressDialog(FacultyLogin.this);
        progressDialog.setTitle("Loging In");
        progressDialog.setMessage("Account is being setup");


        auth=FirebaseAuth.getInstance();
        Flogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                auth.signInWithEmailAndPassword(Femail.getText().toString(),Fpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            startActivity(new Intent(FacultyLogin.this, FacultyMainActivity.class));
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        }

                });
            }
        });


    }
}