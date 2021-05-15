package com.example.rakthadaan.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import com.bumptech.glide.Glide;
import com.example.rakthadaan.DisplayFragment;
import com.example.rakthadaan.MyModel;
import com.example.rakthadaan.NavigationActivity;
import com.example.rakthadaan.ProfileActivity;
import com.example.rakthadaan.R;
import com.example.rakthadaan.RegisterActivity;
import com.example.rakthadaan.models.Pojo;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DashboardFragment extends Fragment {
    DatabaseReference reference;
    ArrayList<MyModel> pojos;
    RecyclerView rv;
    SharedPreferences preferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        rv = v.findViewById(R.id.rv);
        pojos = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Profile");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pojos.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    MyModel myModel = dataSnapshot.getValue(MyModel.class);
                    pojos.add(myModel);
                }
                DashAdapter adapter = new DashAdapter(getActivity(),pojos);
                rv.setAdapter(adapter);
                rv.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
    public class DashAdapter extends RecyclerView.Adapter<DashAdapter.HoldView> {
        Context ct;
        ArrayList<MyModel> list;

        public DashAdapter(Context ct, ArrayList<MyModel> list) {
            this.ct = ct;
            this.list = list;
        }

        @NonNull
        @Override
        public HoldView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new HoldView(LayoutInflater.from(ct).inflate(R.layout.row,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull HoldView holder, int position) {
            Glide.with(ct).load(list.get(position).getImage());
            holder.pc.setText(list.get(position).getPin());
            holder.n.setText(list.get(position).getFname());
            holder.bg.setText(list.get(position).getBloodgroup());
            holder.id.setText(list.get(position).getId());
            holder.c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("tel:"+list.get(position).getMobile());
                    Intent i = new Intent(Intent.ACTION_CALL,uri);
                    ct.startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class HoldView extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView id,n,pc,bg;
            CircleImageView iv;
            ImageButton c;
            public HoldView(@NonNull View itemView) {
                super(itemView);
                id = itemView.findViewById(R.id.id);
                n = itemView.findViewById(R.id.n);
                pc = itemView.findViewById(R.id.pc);
                iv = itemView.findViewById(R.id.civ);
                bg = itemView.findViewById(R.id.bg);
                c = itemView.findViewById(R.id.c);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int pos = getAdapterPosition();
                preferences = getActivity().getSharedPreferences("uid",Context.MODE_PRIVATE);
                String sid = preferences.getString("id",null);
                if (sid==null) {
                    Snackbar.make(v,"Please complete your registration",Snackbar.LENGTH_LONG)
                            .setAction("Register", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(getContext(), RegisterActivity.class));
                                }
                            }).show();
                }else{
                    DisplayFragment fragment = new DisplayFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("fname", list.get(pos).getFname() + " " + list.get(pos).getLname());
                    bundle.putString("age", list.get(pos).getAge());
                    bundle.putString("blood", list.get(pos).getBloodgroup());
                    bundle.putString("date", list.get(pos).getDate());
                    bundle.putString("pin", list.get(pos).getPin());
                    bundle.putString("gender", list.get(pos).getGender());
                    bundle.putString("id", list.get(pos).getId());
                    bundle.putString("ilink", list.get(pos).getImage());
                    bundle.putString("mail", list.get(pos).getMail());
                    bundle.putString("mobile", list.get(pos).getMobile());
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();

                }
            }
        }
    }
}