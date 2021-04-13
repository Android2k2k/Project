package com.example.rakthadaan.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.rakthadaan.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class FeedbackFragment extends Fragment {
    String rating;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feedback, container, false);
        SharedPreferences pref = getActivity().getSharedPreferences("uid", Context.MODE_PRIVATE);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> map = new HashMap<>();
        map.put("rating",rating);
        ref.child("Profile").child(pref.getString("id",null)).updateChildren(map);

        return v;
    }
}