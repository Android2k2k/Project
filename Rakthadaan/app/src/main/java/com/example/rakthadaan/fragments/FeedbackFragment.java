package com.example.rakthadaan.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.rakthadaan.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hsalf.smileyrating.SmileyRating;


import java.io.BufferedReader;
import java.util.HashMap;


public class FeedbackFragment extends Fragment {
   SmileyRating smileyRating;
    EditText sugg;
    Button b;
    DatabaseReference reference ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feedback, container, false);
        smileyRating = v.findViewById(R.id.smile_rating);

        sugg = v.findViewById(R.id.cmnt);
        b = v.findViewById(R.id.submit);
        reference = FirebaseDatabase.getInstance().getReference("Feedback");
        SharedPreferences pref = getActivity().getSharedPreferences("uid", Context.MODE_PRIVATE);
        String uid = pref.getString("id",null);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmileyRating.Type type = smileyRating.getSelectedSmiley();
               // int r = type.getRating();
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("id",uid);
                hashMap.put("rating",type.toString());
                hashMap.put("comment",sugg.getText().toString());
                reference.child(uid).setValue(hashMap);
                Toast.makeText(getContext(), "Feedback Submitted", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}