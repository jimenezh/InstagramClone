package com.example.instagramclone.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.instagramclone.MainActivity;
import com.example.instagramclone.R;
import com.example.instagramclone.databinding.FragmentComposeBinding;
import com.example.instagramclone.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ComposeFragment extends Fragment {

    public static FragmentComposeBinding binding;
    private File photoFile;
    public String photoFileName = "photo.jpg";
    public static final String TAG = "ComposeFragment";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;

    public ComposeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentComposeBinding.inflate(getLayoutInflater(), container, false);

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = binding.etDescription.getText().toString();
                if(description.isEmpty())
                    Toast.makeText(getContext(),"Description cannot be empty", Toast.LENGTH_SHORT).show();
                else if(photoFile == null || binding.ivPostImage.getDrawable() == null)
                    Toast.makeText(getContext(), "No image!", Toast.LENGTH_SHORT).show();
                else
                    savePost(description, ParseUser.getCurrentUser(), photoFile);
            }
        });

        binding.btnPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }
    private void savePost(String description, ParseUser currentUser, File photoFile) {
        Post p = new Post();
        p.setDescription(description);
//        p.setImage();
        p.setUser(currentUser);
        p.setImage(new ParseFile(photoFile));
        p.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null) {
                    Log.e(TAG, "Error saving post", e);
                    return;
                }
                Log.i(TAG, "Posted!");
                Toast.makeText(getContext(), "Posted",Toast.LENGTH_SHORT).show();
                binding.etDescription.setText("");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                takenImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                // Load the taken image into a preview
                binding.ivPostImage.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}