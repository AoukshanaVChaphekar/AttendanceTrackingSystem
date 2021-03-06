package com.example.attendancetrackingsystem.AdminUi.AddUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attendancetrackingsystem.Models.Subject;
import com.example.attendancetrackingsystem.Models.User;
import com.example.attendancetrackingsystem.R;
import com.example.attendancetrackingsystem.AdminlistItemClicked;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class user_add extends AppCompatActivity implements AdminlistItemClicked {


    EditText userFname;
    EditText userLname;
    EditText userMobNo;
    EditText useremail;
    EditText userpassword;
    Button userAdd;
    EditText userid;
    ProgressDialog progressDialog;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    private RecyclerView recyclerView;
    UserAddAdapter addAdapter;
    ArrayList<Subject>subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_user_add2);
        subjects=new ArrayList<>();

        auth= FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
        databaseReference=database.getReference().child("Subject");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                subjects.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    String name=dataSnapshot.child("name").getValue().toString();
                    String code=dataSnapshot.child("code").getValue().toString();
                    subjects.add(new Subject(name,code));
                }
                addAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        addAdapter=new UserAddAdapter(this,subjects);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(addAdapter);

        userFname=findViewById(R.id.fNameEditText);
        userLname=findViewById(R.id.lNameEditText);
        userMobNo=findViewById(R.id.phoneNoEditText);
        useremail=findViewById(R.id.emailEditText);
        userid=findViewById(R.id.userIdEditText);
        userpassword=findViewById(R.id.passwordEditText);
        userAdd=findViewById(R.id.addUserButton);
        final List<String> selectedItems=addAdapter.getSelectedItems() ;


        progressDialog=new ProgressDialog(user_add.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Account is being created");

        userAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                auth.createUserWithEmailAndPassword(useremail.getText().toString(),userpassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if(task.isSuccessful())
                                {
                                    User user=new User(userid.getText().toString(),useremail.getText().toString(),userpassword.getText().toString(),
                                            userFname.getText().toString(),userLname.getText().toString(),userMobNo.getText().toString(),selectedItems);
                                    String id=task.getResult().getUser().getUid();
                                    database.getReference().child("Users").child(id).setValue(user);
                                    useremail.setText("");
                                    userid.setText("");
                                    userpassword.setText("");
                                    userFname.setText("");
                                    userLname.setText("");
                                    userMobNo.setText("");
                                    selectedItems.clear();

                                    Toast.makeText(getApplicationContext(),"User Created Successfully!",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(user_add.this, AddUserFragment.class));
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

    @Override
    public void OnitemClicked(String subject) {

    }
}