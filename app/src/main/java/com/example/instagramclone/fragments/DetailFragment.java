package com.example.instagramclone.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.instagramclone.R;
import com.example.instagramclone.databinding.FragmentDetailBinding;
import com.example.instagramclone.models.Post;

import org.parceler.Parcels;

public class DetailFragment extends Fragment {

    FragmentDetailBinding binding;
    Post post;
    public static final String TAG = "DetailFragment";
    String author;
    String description;
    String time;
    String url;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(getLayoutInflater());
        // Get arguments
        Bundle args = getArguments();
        binding.tvAuthor.setText( args.getString("author"));
        binding.tvDescription.setText( args.getString("description"));
        binding.tvCreatedAt.setText(args.getString("time"));
        Glide.with(getContext()).load(args.getString("url")).into(binding.ivPostImage);

        Log.i(TAG, author+description+time+url);
        return binding.getRoot();
    }
}