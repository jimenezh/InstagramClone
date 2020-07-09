package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.instagramclone.adapters.FeedAdapter;
import com.example.instagramclone.databinding.ActivityMainBinding;
import com.example.instagramclone.databinding.ToolbarBinding;
import com.example.instagramclone.fragments.ComposeFragment;
import com.example.instagramclone.fragments.FeedFragment;
import com.example.instagramclone.fragments.ProfileFragment;
import com.example.instagramclone.models.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

import org.parceler.Parcels;

public class MainActivity extends AppCompatActivity implements FeedAdapter.PostAdapterListener {

    ActivityMainBinding binding;
    public static final String TAG = "MainActivity";
    final FragmentManager fragmentManager = getSupportFragmentManager();
    // Fragments for tab navigation
    final FeedFragment feedFragment = new FeedFragment();
    final ComposeFragment composeFragment = new ComposeFragment();
    final ProfileFragment profileFragment = new ProfileFragment();

    ProgressBar miActionProgressItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setBottomNavigationListener();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setBottomNavigationListener() {
        // Changes frame layout to appropriate fragment
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragment = feedFragment;
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
    public void setPostListener(Object object, Fragment fragment, String type) {
        Log.i(TAG, String.valueOf(object.getClass()));
        // New fragment
//        DetailFragment detailFragment = new DetailFragment();

        // Pass post into new fragment
        Bundle bundle = new Bundle();

        switch (type){
            case "Post":
                bundle.putParcelable(Post.class.getSimpleName(),Parcels.wrap( (Post)  object));
                break;
            case "User":
                bundle.putParcelable(ParseUser.class.getSimpleName(),Parcels.wrap( (ParseUser)  object));
                break;
        }

        fragment.setArguments(bundle);
        // Replace frame layout with PostDetails
        fragmentManager.beginTransaction().replace(binding.flContainer.getId(),fragment).commit();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);

        miActionProgressItem = findViewById(R.id.pbProgressAction);
        hideProgressBar();
        // Return to finish
        return super.onPrepareOptionsMenu(menu);
    }

    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisibility(View.INVISIBLE);
    }
}