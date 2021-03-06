
package com.example.instagramclone.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instagramclone.R;
import com.example.instagramclone.adapters.FeedAdapter;
import com.example.instagramclone.adapters.PostsGridAdapter;
import com.example.instagramclone.databinding.FragmentFeedBinding;
import com.example.instagramclone.databinding.FragmentGridFeedBinding;
import com.example.instagramclone.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class GridFeedFragment extends Fragment{

    protected List<Post> posts;
    protected PostsGridAdapter adapter;
    protected FragmentGridFeedBinding binding;
    private static final String TAG = "GridFeedFragment";
    protected final int POST_LIMIT = 20;




    ParseUser filterByUser;
    private static final int SPAN_COUNT = 3;

    public GridFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        // Inflate the layout for this fragment
        binding = FragmentGridFeedBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initializing empty posts list
        posts = new ArrayList<>();
        // Setting adapter
        adapter = new PostsGridAdapter(getContext(), posts);
        binding.rvPosts.setAdapter(adapter);
        // Setting layout manager
        binding.rvPosts.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
        // Setup refresh listener which triggers new data loading
        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // once the network request has completed successfully.
                queryPosts();
            }
        });
        // Configure the refreshing colors
        binding.swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        // Inital query
        queryPosts();
    }

    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(POST_LIMIT); // Get only 20 posts
        query.whereEqualTo(Post.KEY_USER, filterByUser);
        query.addDescendingOrder(Post.KEY_CREATED_AT); // // Orders from most recent to least recent
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
                // Clears out old items in adapter + notifies
                adapter.clear();
                // Adds results + notifies
                adapter.addAll(results);
            }
        });
        // Gets rid off swipe container
        binding.swipeContainer.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}