package com.example.rakthadaan.fragments;

import android.content.Context;
import android.content.Intent;
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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rakthadaan.MyModel;
import com.example.rakthadaan.MyModel2;
import com.example.rakthadaan.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class NearestbloodbanksFragment extends Fragment {
    DatabaseReference reference;
    RecyclerView rv;
    ArrayList<MyModel2> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nearestbloodbanks, container, false);
        rv = v.findViewById(R.id.nb_rv);
        list = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("BloodBank");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    MyModel2 model = dataSnapshot.getValue(MyModel2.class);
                    list.add(model);
                }
                BankAdapter adapter = new BankAdapter(getActivity(),list);
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

    public class BankAdapter extends RecyclerView.Adapter<BankAdapter.HoldView> {
        Context ct;
        ArrayList<MyModel2> list;

        public BankAdapter(Context ct, ArrayList<MyModel2> list) {
            this.ct = ct;
            this.list = list;
        }

        @NonNull
        @Override
        public HoldView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new HoldView(LayoutInflater.from(ct).inflate(R.layout.b_row,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull HoldView holder, int position) {
            holder.tv.setText(list.get(position).getBbname());
            holder.tv1.setText(list.get(position).getBbpname());
            holder.tv2.setText(list.get(position).getBbpin());
            String loc = list.get(position).getBbaddress();
            String lines[] = loc.split("\\r?\\n");
            holder.ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("geo:"+lines[0]+","+lines[1]);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(mapIntent);
                }
            });
            holder.ib1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse("tel:"+list.get(position).getBbmobile());
                    Intent i = new Intent(Intent.ACTION_CALL,uri);
                    ct.startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class HoldView extends RecyclerView.ViewHolder {
            TextView tv,tv1,tv2;
            ImageButton ib,ib1;
            public HoldView(@NonNull View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.bname);
                tv1 = itemView.findViewById(R.id.bpname);
                tv2 = itemView.findViewById(R.id.bpin);
                ib = itemView.findViewById(R.id.locate);
                ib1 = itemView.findViewById(R.id.call);
            }
        }
    }
}