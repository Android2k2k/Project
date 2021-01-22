package com.example.bloodproject.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bloodproject.MyModel;
import com.example.bloodproject.fragments.DisplayFragment;
import com.example.bloodproject.Pojo;
import com.example.bloodproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DashboardFragment extends Fragment {
    DatabaseReference reference;
    RecyclerView rv;
    ArrayList<MyModel> pojos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        rv = v.findViewById(R.id.rv);
        pojos = new ArrayList<MyModel>();
        reference = FirebaseDatabase.getInstance().getReference("profile");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object object = dataSnapshot.getValue(Object.class);
                String json = new Gson().toJson(object);
                try {
                    JSONArray root = new JSONArray(json);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject jsonObject = root.getJSONObject(i);
                        String fname = jsonObject.getString("First_Name");
                        String lname = jsonObject.getString("Last_Name");
                        String mail = jsonObject.getString("Mail");
                        String mobile = jsonObject.getString("Mobile");
                        String age = jsonObject.getString("Age");
                        String date = jsonObject.getString("Date");
                        String gender = jsonObject.getString("Gender");
                        String bloodgroup = jsonObject.getString("Blood_Group");
                        String image = jsonObject.getString("Image");
                        String address = jsonObject.getString("Address");
                        String rating = jsonObject.getString("Rating");
                        MyModel pojo = new MyModel(fname, lname, mail, mobile, age, date, gender, bloodgroup, image, address, rating);
                        pojos.add(pojo);
                    }
                    MyAdapter adapter = new MyAdapter(getActivity(), pojos);
                    rv.setAdapter(adapter);
                    rv.setLayoutManager(new LinearLayoutManager(getContext()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.HoldView>{
        Context ct;
        ArrayList<MyModel> list;
        public MyAdapter(FragmentActivity activity, ArrayList<MyModel> pojos) {
            ct = activity;
            list = pojos;
        }


        @NonNull
        @Override
        public HoldView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new HoldView(LayoutInflater.from(ct).inflate(R.layout.row,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull HoldView holder, int position) {
            holder.tv1.setText(list.get(position).getFname());
            holder.tv2.setText(list.get(position).getBloodgroup());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class HoldView extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tv,tv1,tv2;
            public HoldView(@NonNull View itemView) {
                super(itemView);
                tv1 = itemView.findViewById(R.id.fname);
                tv2 = itemView.findViewById(R.id.bloodgroup);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int pos = getAdapterPosition();
                DisplayFragment fragment = new DisplayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("fname",list.get(pos).getFname());
                bundle.putString("lname",list.get(pos).getLname());
                bundle.putString("mail",list.get(pos).getMail());
                bundle.putString("mobile",list.get(pos).getMobile());
                bundle.putString("age",list.get(pos).getAge());
                bundle.putString("date",list.get(pos).getDate());
                bundle.putString("gender",list.get(pos).getGender());
                bundle.putString("bloodgroup",list.get(pos).getBloodgroup());
                bundle.putString("image",list.get(pos).getImage());
                bundle.putString("address",list.get(pos).getAddress());
                bundle.putString("rating",list.get(pos).getRating());
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_aboutus,fragment).addToBackStack(null).commit();
            }
        }
    }
}