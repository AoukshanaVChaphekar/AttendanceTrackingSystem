package com.example.attendancetrackingsystem.Facultyui.TakeAttendance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancetrackingsystem.Facultyui.StudentList;
import com.example.attendancetrackingsystem.Facultyui.Students.StudentFragment;
import com.example.attendancetrackingsystem.Facultyui.home.HomeFragment;
import com.example.attendancetrackingsystem.Models.Subject;
import com.example.attendancetrackingsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TakeAttendanceFragment extends Fragment {


    Spinner spinner;
    Button takeAttendance;

    ArrayList<Subject> subjectArrayList=new ArrayList<>();
    ArrayList<Subject> list=new ArrayList<>();

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    String fid;

    int position;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.faculty_fragment_take_attendance, container, false);

        spinner = root.findViewById(R.id.spinner2);
        takeAttendance=root.findViewById(R.id.takeAttendanceButton);

        take(new callBackTakeAttendance() {
            @Override
            public void OnCallBack(ArrayList<Subject> list) {
                subjectArrayList=list;
                String[] lists=new String[subjectArrayList.size()+1];
                lists[0]="Select Course";
                for(int i=0;i<lists.length-1;i++)
                {
                    lists[i+1]=subjectArrayList.get(i).getCode()+" - "+subjectArrayList.get(i).getName();

                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item,lists);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter.notifyDataSetChanged();
                spinner.setAdapter(dataAdapter);


            }
        });




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                position=i;
                if(position==0)
                {
                    Toast.makeText(getContext(),"Select Subject",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        takeAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position==0)
                {
                    Toast.makeText(getContext(),"Please select subject",Toast.LENGTH_SHORT).show();
                }
                else{
                String code=subjectArrayList.get(position-1).getCode();

                Intent intent=new Intent(getActivity(), takeAttendance.class);
                intent.putExtra("code",code);
                startActivity(intent);
                }
            }
        });


        return root;
    }
    public void take(callBackTakeAttendance callBack)
    {
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String email=firebaseUser.getEmail();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Faculty");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    if(snapshot1.child("email").getValue().toString().equalsIgnoreCase(email))
                    {
                        fid=snapshot1.child("fid").getValue().toString();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        databaseReference=FirebaseDatabase.getInstance().getReference().child("Subject");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    ArrayList<String> fids= (ArrayList<String>) snapshot1.child("fid").getValue();
                    if(fids.contains(fid))
                    {
                        String subCode=snapshot1.child("code").getValue().toString();
                        String subName=snapshot1.child("name").getValue().toString();
                        list.add(new Subject(subName,subCode));

                    }
                }
                callBack.OnCallBack(list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error",error.getMessage());
            }
        });

    }

    private interface callBackTakeAttendance{
        void OnCallBack(ArrayList<Subject> list);
    }

}