package com.example.bloodproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.core.content.ContextCompat.startActivity;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.HoldView> {
    Context ct;
    ArrayList<Pojo> list;

    public MyAdapter(ArrayList<Pojo> list, RetrivalActivity retrivalActivity) {
        ct = retrivalActivity;
        this.list = list;
    }

    @NonNull
    @Override
    public HoldView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HoldView(LayoutInflater.from(ct)
                .inflate(R.layout.row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HoldView holder, int position) {
        holder.dname.setText(list.get(position).dname);
        holder.pcode.setText(list.get(position).pcode);
        Glide.with(ct).load(list.get(position).ilink)
                .placeholder(R.drawable.ic_launcher_background).into(holder.civ);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HoldView extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        CircleImageView civ;
        TextView dname,pcode;
        public HoldView(@NonNull View itemView) {
            super(itemView);
            civ = itemView.findViewById(R.id.civ);
            dname = itemView.findViewById(R.id.dn);
            pcode = itemView.findViewById(R.id.pc);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int i = getAdapterPosition();
            Intent intent = new Intent(ct, DisplayActivity.class);
            intent.putExtra("dname",list.get(i).dname);
            intent.putExtra("pcode",list.get(i).pcode);
            intent.putExtra("ilink",list.get(i).ilink);
            ct.startActivity(intent);
        }
    }


}