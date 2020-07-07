package com.example.instagramclone.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instagramclone.R;
import com.example.instagramclone.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class ProfileFragment extends PostFragment {

    private final String TAG ="PostFragment";

    @Override
    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(POST_LIMIT); // Get only 20 posts
        query.addDescendingOrder(Post.KEY_CREATED_AT); // Orders from most recent to least recent
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> results, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error in query", e);
                    return;
                }
                for (Post p : results) {
                    Log.i(TAG, p.getDescription());
                }
                posts.addAll(results); // adding posts to class field
                Log.i(TAG, String.valueOf(posts.size()));
                adapter.notifyDataSetChanged();
            }
        });
    }
}