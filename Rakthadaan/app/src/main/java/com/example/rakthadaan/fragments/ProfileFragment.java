package com.example.rakthadaan.fragments;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.rakthadaan.MyModel;
import com.example.rakthadaan.R;
import com.example.rakthadaan.databinding.FragmentProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    DatabaseReference reference;
    SharedPreferences preferences;
    MyModel myModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile,container,false);
        binding = DataBindingUtil.setContentView(getActivity(),R.layout.fragment_profile);
        preferences = getContext().getSharedPreferences("Data",MODE_PRIVATE);
        reference = FirebaseDatabase.getInstance().getReference("Profile");
        //binding thw profile
        reference.addValueEventListener(new ValueEventListener() {
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
                    Glide.with(ProfileFragment.this)
                            .load(myModel.getImage())
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(binding.iv);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();

            }
        });
        return v;
    }
    //for updating thw profile
    public void update(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.update, null, false);
//        final ImageView image = v.findViewById(R.id.iv);
        final EditText name = v.findViewById(R.id.name);
        final EditText email = v.findViewById(R.id.email);
        final EditText mobile = v.findViewById(R.id.mobile);
        final EditText age = v.findViewById(R.id.age);
        final EditText pin = v.findViewById(R.id.address);
        final EditText rb = v.findViewById(R.id.blood);
        final EditText rating = v.findViewById(R.id.rating);
        builder.setView(v);
        builder.setCancelable(false);

        //image pending
        name.setText(myModel.getFname()+" "+myModel.getLname());
        email.setText(myModel.getMail());
        mobile.setText(myModel.getMobile());
        age.setText(myModel.getAge());
        pin.setText(myModel.getAddress());
        rb.setText(myModel.getBloodgroup());
        rating.setText(myModel.getRating());

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                HashMap<String,Object> map = new HashMap<>();
                map.put("name",name.getText().toString());
                map.put("email",email.getText().toString());
                map.put("mobile",mobile.getText().toString());
                map.put("age",age.getText().toString());
                map.put("pin",pin.getText().toString());
                map.put("rb",rb.getText().toString());
                map.put("rating",rating.getText().toString());
                reference.child(mobile.getText().toString()).updateChildren(map);
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