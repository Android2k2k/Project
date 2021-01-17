package com.example.rakthadaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
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
    SharedPreferences preferences;
    FirebaseAuth auth;
    MyModel myModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_profile);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile);
        auth = FirebaseAuth.getInstance();
        preferences = getSharedPreferences("Data",MODE_PRIVATE);
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
                    binding.rating.setRating(myModel.getRating());
                    binding.blood.setText(myModel.getBloodgroup());
                    Glide.with(ProfileActivity.this)
                            .load(myModel.getImage())
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(binding.iv);
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
       // View v = LayoutInflater.from(this).inflate(R.layout.update, null, false);
        UpdateBinding updateBinding = DataBindingUtil.inflate(LayoutInflater.from(this),R.layout.update,null,false);
        //final ImageView image = v.findViewById(R.id.iv);
      /*  final EditText name = v.findViewById(R.id.name);
        final EditText email = v.findViewById(R.id.email);
        final EditText number = v.findViewById(R.id.number);
        final EditText age = v.findViewById(R.id.age);
        final EditText pin = v.findViewById(R.id.pin);
        final EditText blood = v.findViewById(R.id.blood);
        final RatingBar rating = v.findViewById(R.id.rating);*/
        builder.setView(updateBinding.getRoot());
        builder.setCancelable(false);

        //image pending
        updateBinding.name.setText(myModel.getFname()+" "+myModel.getLname());
        updateBinding.email.setText(myModel.getMail());
        updateBinding.number.setText(myModel.getMobile());
        updateBinding.age.setText(myModel.getAge());
        updateBinding.pin.setText(myModel.getAddress());
        updateBinding.blood.setText(myModel.getBloodgroup());
        updateBinding.rating.setRating(myModel.getRating());

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                HashMap<String,Object> map = new HashMap<>();
                map.put("name",updateBinding.name.getText().toString());
                map.put("email",updateBinding.email.getText().toString());
                map.put("mobile",updateBinding.number.getText().toString());
                map.put("age",updateBinding.age.getText().toString());
                map.put("pin",updateBinding.pin.getText().toString());
                map.put("rb",updateBinding.blood.getText().toString());
                map.put("rating",updateBinding.rating.getRating());
                reference.updateChildren(map);
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
