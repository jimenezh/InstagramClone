package com.example.instagramclone.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.instagramclone.LoginActivity;
import com.example.instagramclone.R;
import com.example.instagramclone.databinding.FragmentComposeBinding;
import com.example.instagramclone.databinding.FragmentProfileBinding;
import com.example.instagramclone.models.Post;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class ProfileFragment extends Fragment {

    private final String TAG = "PostFragment";
    FragmentProfileBinding binding;
    FragmentManager fragmentManager;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(getLayoutInflater(), container, false);
        // Setting up fragments for user pictures
        fragmentManager = getChildFragmentManager();
        PostFragment postFragment = new PostFragment();
        postFragment.filterByUser = ParseUser.getCurrentUser();
        fragmentManager.beginTransaction().replace(R.id.flPics, postFragment).commit();
        // Sign out
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                Toast.makeText(getContext(),"User logged out",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                getActivity().finish();
            }
        });
        return binding.getRoot();
    }
}