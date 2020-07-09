package com.example.instagramclone.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.instagramclone.R;
import com.example.instagramclone.databinding.FragmentUserBinding;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;


public class UserFragment extends Fragment {

    FragmentUserBinding binding;
    ParseUser user;
    FragmentManager fragmentManager;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args =  getArguments();
        user = Parcels.unwrap( args.getParcelable(ParseUser.class.getSimpleName()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(getLayoutInflater());

        // Setting up profile pic
        String imageUrl = "";
        if(user.get(ProfileFragment.KEY_IMAGE) != null){
            ParseFile file = (ParseFile) ParseUser.getCurrentUser().get(ProfileFragment.KEY_IMAGE);
            if(file != null)
                imageUrl = file.getUrl();
        }
        Glide.with(getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_baseline_person_24)
                .transform(new CircleCrop())
                .into(binding.ivProfilePic);

        // Username
        binding.tvUsername.setText(user.getUsername());

        // Setting up fragments for user pictures
        fragmentManager = getChildFragmentManager();
        fragmentManager = getChildFragmentManager();
        GridFeedFragment gridFeedFragment = new GridFeedFragment();
        gridFeedFragment.filterByUser = user;
        fragmentManager.beginTransaction().replace(R.id.flPics, gridFeedFragment).commit();


        return binding.getRoot();
    }
}