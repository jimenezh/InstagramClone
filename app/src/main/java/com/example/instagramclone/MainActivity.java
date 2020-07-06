package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.instagramclone.databinding.ActivityLoginBinding;
import com.example.instagramclone.databinding.ActivityMainBinding;
import com.example.instagramclone.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        queryPosts();
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = binding.etDescription.getText().toString();
                if(description.isEmpty())
                    Toast.makeText(MainActivity.this,"Description cannot be empty", Toast.LENGTH_SHORT).show();
                else
                    savePost(description, ParseUser.getCurrentUser());
            }
        });

    }

    private void savePost(String description, ParseUser currentUser) {
        Post p = new Post();
        p.setDescription(description);
//        p.setImage();
        p.setUser(currentUser);
        p.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null) {
                    Log.e(TAG, "Error saving post", e);
                    return;
                }
                Log.i(TAG, "Posted!");
                Toast.makeText(MainActivity.this, "Posted",Toast.LENGTH_SHORT).show();
                binding.etDescription.setText("");
            }
        });
    }

    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Error in query", e);
                    return;
                }
                for(Post p : posts){
                    Log.i(TAG,p.getDescription());
                }
            }
        });
    }
}