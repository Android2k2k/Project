package com.example.bloodproject.fragments;

import android.content.Intent;

import android.app.Dialog;
import android.content.DialogInterface;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodproject.MainActivity;
import com.example.bloodproject.R;
import com.google.firebase.auth.FirebaseAuth;


public class LogoutFragment extends DialogFragment {
    FirebaseAuth auth;
    TextView tv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance();
        View view = inflater.inflate(R.layout.fragment_logout, container, false);
        return view;
        }

//        public void signout(View view) {
//            auth.signOut();
//            dismiss();
//        }


    public void exit(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        // Setting Alert Dialog Title
        alertDialogBuilder.setTitle("Logout");
//        // Icon Of Alert Dialog
//        alertDialogBuilder.setIcon(R.drawable.question);
        // Setting Alert Dialog Message
        alertDialogBuilder.setMessage("Are you sure,You want to exit");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                auth.signOut();
                getActivity().finish();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "clicked no", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity().getApplicationContext(),"You clicked on Cancel",Toast.LENGTH_SHORT).show();

            }
        });

        alertDialogBuilder.show();
    }

}
