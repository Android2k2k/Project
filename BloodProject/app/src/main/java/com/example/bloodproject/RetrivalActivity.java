package com.example.bloodproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodproject.databinding.ActivityRetrivalBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class RetrivalActivity extends AppCompatActivity {

    ActivityRetrivalBinding binding;
    RecyclerView rv;
    ArrayList<Pojo> list;
    DatabaseReference reference;
    Pojo pojo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_retrival);
        rv = findViewById(R.id.rv);
        list = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Data");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Pojo pojo = dataSnapshot.getValue(Pojo.class);
                    list.add(pojo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        pojo = new Pojo("ilink","dname","pcode","bgroup");
        MyAdapter adapter = new MyAdapter(list, this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));


    }

}