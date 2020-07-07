package com.example.instagramclone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagramclone.R;
import com.example.instagramclone.databinding.ItemPostBinding;
import com.example.instagramclone.models.Post;
import com.parse.Parse;
import com.parse.ParseFile;

import org.w3c.dom.Text;

import static com.example.instagramclone.R.layout.item_post;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    Context context;
    List<Post> posts;

    // Constructor
    public PostsAdapter(Context context, List<Post> posts) {
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

    // Custom ViewHolder class
    class ViewHolder extends RecyclerView.ViewHolder{

        ItemPostBinding binding;

        // ViewBinding => pass in Binding instead of View into the ViewHolder
        public ViewHolder(@NonNull ItemPostBinding itemPostBinding) {
            super(itemPostBinding.getRoot()); // getRoot() returns a View
            this.binding = itemPostBinding; // Initializing binding
        }


        public void bind(Post post) {
            binding.tvAuthor.setText(post.getUser().getUsername());
            binding.tvDescription.setText(post.getDescription());
            ParseFile image  = post.getImage();
            if(image != null) // in case of dummy posts
                Glide.with(context).load(post.getImage().getUrl()).centerCrop().into(binding.ivPostImage);
        }
    }
}
