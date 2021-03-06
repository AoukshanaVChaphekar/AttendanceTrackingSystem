package com.example.attendancetrackingsystem.UserUi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attendancetrackingsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserLogin extends AppCompatActivity {

    EditText email;
    EditText password;
    Button login;
    FirebaseAuth auth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_user_login);

        email=findViewById(R.id.UemailEditText);
        password=findViewById(R.id.UpasswordEditText);
        login=findViewById(R.id.Ulogin);

        progressDialog=new ProgressDialog(UserLogin.this);
        progressDialog.setTitle("Loging In");
        progressDialog.setMessage("Account is being setup");

        auth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                auth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful())
                            {
                                //start activity
                                startActivity(new Intent(UserLogin.this,UserMainActivity.class));
                            }
                            else
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }
}