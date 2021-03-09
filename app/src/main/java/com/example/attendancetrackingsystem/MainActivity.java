package com.example.attendancetrackingsystem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.attendancetrackingsystem.UserUi.UserLogin;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button admin;
    Button user;
    Button faculty;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        admin=findViewById(R.id.Adminlogin);
        user=findViewById(R.id.Userlogin);
        faculty=findViewById(R.id.Facultylogin);
        admin.setOnClickListener(this);
        user.setOnClickListener(this);
        faculty.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch(view.getId())
        {
            case R.id.Adminlogin:
                intent=new Intent(this, AdminLogin.class);

                break;
            case R.id.Userlogin:
                intent=new Intent(this, UserLogin.class);
                break;
            case R.id.Facultylogin:
                intent=new Intent(this,FacultyLogin.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }


        startActivity(intent);


    }
}