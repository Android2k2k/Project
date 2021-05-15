package com.example.rakthadaan;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.rakthadaan.databinding.FragmentDisplayBinding;


public class DisplayFragment extends Fragment {
    FragmentDisplayBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_display, container, false);
        binding.name.setText(getArguments().getString("fname"));
        binding.sid.setText(getArguments().getString("id"));
        binding.email.setText(getArguments().getString("mail"));
        binding.number.setText(getArguments().getString("mobile"));
        binding.age.setText(getArguments().getString("age"));
        binding.date.setText(getArguments().getString("date"));
        binding.gender.setText(getArguments().getString("gender"));
        binding.pin.setText(getArguments().getString("pin"));
        binding.blood.setText(getArguments().getString("blood"));
        Glide.with(getActivity()).load(getArguments().getString("ilink")).into(binding.iv);
        return binding.getRoot();
    }
}