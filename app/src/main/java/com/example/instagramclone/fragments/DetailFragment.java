package com.example.instagramclone.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instagramclone.R;
import com.example.instagramclone.databinding.FragmentDetailBinding;

public class DetailFragment extends Fragment {

    FragmentDetailBinding binding;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(getLayoutInflater());
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}