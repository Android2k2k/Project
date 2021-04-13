package com.example.rakthadaan.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.rakthadaan.R;
import com.google.firebase.auth.FirebaseAuth;


public class LogoutFragment extends Fragment {
    FirebaseAuth auth;
    Button b;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();

        View view1 = inflater.inflate(R.layout.fragment_logout, container, false);
//        b = view1.findViewById(R.id.exit);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                // Setting Alert Dialog Title
                alertDialogBuilder.setTitle("Logout");
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
                        Toast.makeText(getActivity().getApplicationContext(), "clicked no", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity().getApplicationContext(), "You clicked on Cancel", Toast.LENGTH_SHORT).show();

                    }
                });
                alertDialogBuilder.show();
//            }
//        });
       return view1;
    }
}