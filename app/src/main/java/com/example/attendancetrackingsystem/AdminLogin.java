package com.example.attendancetrackingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.attendancetrackingsystem.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AdminLogin extends AppCompatActivity  {
    EditText adminemail;
    EditText adminpassword;
    Button adminsignup;
    ProgressDialog progressDialog;
    EditText username;


    FirebaseDatabase database;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_admi_login);
        adminemail = findViewById(R.id.emailEditText);
        adminpassword = findViewById(R.id.passwordEditText);
        adminsignup=findViewById(R.id.login);
        username=findViewById(R.id.usernameEditText);


        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(AdminLogin.this);
        progressDialog.setTitle("Login in");
        progressDialog.setMessage("Account is being setup");


        adminsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                auth.signInWithEmailAndPassword(adminemail.getText().toString(),adminpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            startActivity(new Intent(AdminLogin.this, AdminMainActivity.class));
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