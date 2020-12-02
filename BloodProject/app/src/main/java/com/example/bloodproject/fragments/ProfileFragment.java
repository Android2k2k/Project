package com.example.bloodproject.fragments;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodproject.R;
import com.example.bloodproject.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        binding = DataBindingUtil.setContentView(getActivity(),R.layout.fragment_profile);
        // Inflate the layout for this fragment
       // TextView tv = v.findViewById(R.id.);


        NotificationManager nm = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        //   NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
        /* this - getContext(),getActivity(),getApplicationContext()*/
        return v;
    }
}