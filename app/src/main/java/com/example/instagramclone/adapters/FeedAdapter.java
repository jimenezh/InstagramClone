package com.example.instagramclone.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
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
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.instagramclone.MainActivity;
import com.example.instagramclone.R;
import com.example.instagramclone.databinding.ItemPostBinding;
import com.example.instagramclone.fragments.DetailFragment;
import com.example.instagramclone.fragments.ProfileFragment;
import com.example.instagramclone.fragments.UserFragment;
import com.example.instagramclone.models.Post;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    Context context;
    List<Post> posts;
    public static final String TAG ="PostsAdapter";

    // Interface to access listener on
    public interface PostAdapterListener{
        void setPostListener(Object object, Fragment fragment, String type);
    }

    // Constructor
    public FeedAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    // Created ViewHolder that holds each individual post
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Since using ViewBinding
        ItemPostBinding binding = ItemPostBinding.inflate(LayoutInflater.from(context));
        return new ViewHolder(binding);
    }

    // Binds data to ViewHolder at specific position
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
        Log.i(TAG, "Added "+list.size()+" posts");
    }

    // Custom ViewHolder class
    class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        ItemPostBinding binding;

        // ViewBinding => pass in Binding instead of View into the ViewHolder
        public ViewHolder(@NonNull ItemPostBinding itemPostBinding) {
            super(itemPostBinding.getRoot()); // getRoot() returns a View
            this.binding = itemPostBinding; // Initializing binding
            itemPostBinding.getRoot().setOnClickListener(this);
        }


        public void bind(Post post) {
            binding.tvAuthor.setText(post.getUser().getUsername());
            binding.tvDescription.setText(post.getDescription());
            // Setting images
            ParseFile image = post.getImage();
            ParseFile profilePic = (ParseFile) post.getUser().get(ProfileFragment.KEY_IMAGE);

            String imageUrl = "";
            if (image != null) // in case of dummy posts
                imageUrl = image.getUrl();
            Glide.with(context).load(imageUrl).centerCrop()
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .into(binding.ivPostImage);

            String profileUrl="";
            if(profilePic != null)
                profileUrl = profilePic.getUrl();
            Glide.with(context).load(imageUrl).centerCrop()
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .transform(new CircleCrop())
                    .into(binding.ivProfilePic);
            // Timestamp
            binding.tvCreatedAt.setText(post.getCreatedAt().toString());

            // Listeners for username + profile pic
            setListenerToUserProfile();

        }

        private void setListenerToUserProfile() {
            View.OnClickListener toProfile = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "To profile", Toast.LENGTH_SHORT).show();
                    ParseUser user = posts.get(getAdapterPosition()).getUser();
                    ((MainActivity) context).setPostListener(user, new UserFragment(), "User");
                }
            };
            binding.ivProfilePic.setOnClickListener(toProfile);
            binding.tvAuthor.setOnClickListener(toProfile);
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
