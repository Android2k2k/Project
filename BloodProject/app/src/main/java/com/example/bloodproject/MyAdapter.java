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

    public MyAdapter(ArrayList<Pojo> list) {

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
        holder.fname.setText(list.get(position).fname);
        holder.bloodgroup.setText(list.get(position).bloodgroup);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HoldView extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView fname,bloodgroup;
        public HoldView(@NonNull View itemView) {
            super(itemView);
            fname = itemView.findViewById(R.id.fname);
            bloodgroup = itemView.findViewById(R.id.bloodgroup);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int i = getAdapterPosition();
            Intent intent = new Intent(ct, DisplayActivity.class);
            intent.putExtra("fname",list.get(i).fname);
            intent.putExtra("bloodgroup",list.get(i).bloodgroup);
            ct.startActivity(intent);
        }
    }


}