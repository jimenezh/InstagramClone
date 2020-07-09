package com.example.instagramclone.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagramclone.MainActivity;
import com.example.instagramclone.R;
import com.example.instagramclone.databinding.ItemGridPostBinding;
import com.example.instagramclone.fragments.DetailFragment;
import com.example.instagramclone.fragments.ProfileFragment;
import com.example.instagramclone.fragments.UserFragment;
import com.example.instagramclone.models.Post;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;

public class PostsGridAdapter extends RecyclerView.Adapter<PostsGridAdapter.ViewHolder> {

    Context context;
    List<Post> posts;
    public static final String TAG = "PostsAdapter";

    // Interface to access listener on
    public interface PostAdapterListener {
        void setPostListener(Object object, Fragment fragment, String type);
    }

    // Constructor
    public PostsGridAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    // Created ViewHolder that holds each individual post
    @NonNull
    @Override
    public PostsGridAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Since using ViewBinding
        ItemGridPostBinding binding = ItemGridPostBinding.inflate(LayoutInflater.from(context));
        return new PostsGridAdapter.ViewHolder(binding);
    }

    // Binds data to ViewHolder at specific position
    @Override
    public void onBindViewHolder(@NonNull PostsGridAdapter.ViewHolder holder, int position) {
        holder.bind(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
        Log.i(TAG, "Cleared old posts");
    }

    // Add a list of items
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
        Log.i(TAG, "Added " + list.size() + " posts");
    }

    // Custom ViewHolder class
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemGridPostBinding binding;

        // ViewBinding => pass in Binding instead of View into the ViewHolder
        public ViewHolder(@NonNull ItemGridPostBinding itemGridPostBinding) {
            super(itemGridPostBinding.getRoot()); // getRoot() returns a View
            this.binding = itemGridPostBinding; // Initializing binding
            itemGridPostBinding.getRoot().setOnClickListener(this);
        }


        public void bind(Post post) {
            // Setting images
            ParseFile image = post.getImage();
            setImage(image, binding.ivPostImage);
        }

        private void setImage(ParseFile image, ImageView target) {
            String imageUrl = "";
            if (image != null) // in case of dummy posts
                imageUrl = image.getUrl();
            Glide.with(context).load(imageUrl).centerCrop().placeholder(R.drawable.ic_baseline_person_24).into(target);
        }

        // Interface method. Takes in view.
        @Override
        public void onClick(View view) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Clicked on Post", Toast.LENGTH_SHORT).show();
                    Post post = posts.get(getAdapterPosition());
                    ((MainActivity) context).setPostListener(post, new DetailFragment(), "Post");
                }
            });
        }
    }
}
