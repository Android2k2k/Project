package com.example.rakthadaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rakthadaan.databinding.ActivityProfileBinding;
import com.example.rakthadaan.databinding.UpdateBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    DatabaseReference reference;
    SharedPreferences preferences,pref;
    FirebaseAuth auth;
    MyModel myModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile);
        auth = FirebaseAuth.getInstance();
        preferences = getSharedPreferences("Data",MODE_PRIVATE);
        pref = getSharedPreferences("uid",MODE_PRIVATE);
        reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Profile").orderByChild("mail").equalTo(auth.getCurrentUser().getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    myModel = dataSnapshot.getValue(MyModel.class);
                    binding.name.setText(myModel.getFname() + " " + myModel.getLname());
                    binding.number.setText(myModel.getMobile());
                    binding.pin.setText(myModel.getAddress());
                    binding.email.setText(myModel.getMail());
                    binding.age.setText(myModel.getAge());
                    binding.date.setText(myModel.getDate());
                    binding.gender.setText(myModel.getGender());
                    binding.address.setText(myModel.getAddress());
                    binding.pin.setText(myModel.pin);
                    binding.blood.setText(myModel.getBloodgroup());
                    Glide.with(ProfileActivity.this)
                            .load(myModel.getImage())
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(binding.iv);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("profile","1");
                    editor.commit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //for updating thw profile
    public void update(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        UpdateBinding updateBinding = DataBindingUtil.inflate(LayoutInflater.from(this),R.layout.update,null,false);
        builder.setView(updateBinding.getRoot());
        builder.setCancelable(false);

        updateBinding.name.setText(myModel.getFname()+" "+myModel.getLname());
        updateBinding.email.setText(myModel.getMail());
        updateBinding.email.setEnabled(false);
        updateBinding.number.setText(myModel.getMobile());
        updateBinding.age.setText(myModel.getAge());
        updateBinding.age.setEnabled(false);
        updateBinding.pin.setText(myModel.getAddress());
        updateBinding.pin.setEnabled(false);
        updateBinding.blood.setText(myModel.getBloodgroup());
        updateBinding.blood.setEnabled(false);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                HashMap<String,Object> map = new HashMap<>();
                map.put("name",updateBinding.name.getText().toString());
                map.put("mobile",updateBinding.number.getText().toString());
                reference.child("Profile").child(pref.getString("id",null)).updateChildren(map);
                startActivity(new Intent(ProfileActivity.this,NavigationActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
