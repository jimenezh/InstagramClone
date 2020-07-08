package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.instagramclone.adapters.PostsAdapter;
import com.example.instagramclone.databinding.ActivityLoginBinding;
import com.example.instagramclone.databinding.ActivityMainBinding;
import com.example.instagramclone.fragments.ComposeFragment;
import com.example.instagramclone.fragments.DetailFragment;
import com.example.instagramclone.fragments.PostFragment;
import com.example.instagramclone.fragments.ProfileFragment;
import com.example.instagramclone.models.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PostsAdapter.PostAdapterListener {

    ActivityMainBinding binding;
    public static final String TAG = "MainActivity";
    final FragmentManager fragmentManager = getSupportFragmentManager();
    // Fragments for tab navigation
    final PostFragment postFragment = new PostFragment();
    final ComposeFragment composeFragment = new ComposeFragment();
    final ProfileFragment profileFragment = new ProfileFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setBottomNavigationListener();
    }

    private void setBottomNavigationListener() {
        // Changes frame layout to appropriate fragment
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragment = postFragment;
                        break;
                    case R.id.action_camera:
                        fragment = composeFragment;
                        break;
                    case R.id.action_profile:
                        fragment = profileFragment;
                        break;
                    default:
                        break;
                }
                fragmentManager.beginTransaction().replace(binding.flContainer.getId(), fragment).commit();
                return true;
            }
        });
        binding.bottomNavigation.setSelectedItemId(R.id.action_home);
        // default
    }

    @Override
    public void setPostListener(Post post) {
        Log.i(TAG,post.getDescription());
        // New fragment
        DetailFragment detailFragment = new DetailFragment();

        // Pass post into new fragment
        Bundle bundle = new Bundle();
        bundle.putParcelable(Post.class.getSimpleName(),Parcels.wrap(post));

        detailFragment.setArguments(bundle);
        // Replace frame layout with PostDetails
        fragmentManager.beginTransaction().replace(binding.flContainer.getId(),detailFragment).commit();
    }
}