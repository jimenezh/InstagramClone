package com.example.instagramclone.models;

import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseClassName;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ParseClassName("Post")
public class Post extends ParseObject {
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_LIKES = "likes";

    public Post() {
    }

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
        setNumLikes(null);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image) {
        put(KEY_IMAGE, image);
    }


    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public void setNumLikes(ParseUser user) {
        this.add(KEY_LIKES, user);
        this.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if( e != null)
                    Log.e("Parse", "No save", e);
                else
                    Log.i("Parse", "Saved");
            }
        });
    }

    public void removeUserFromLikes(ParseUser user) {
        this.removeAll(KEY_LIKES, Collections.singleton((new ArrayList<>()).add(user)));
        this.saveInBackground();
    }

    public List<ParseUser> getUsersWhoLiked() {
        List<ParseUser>  users =  (List<ParseUser> ) get(KEY_LIKES);
        if (users == null)
            return new ArrayList<>();
        return users;
    }

    public int getNumberOfLikes() {
        if (getUsersWhoLiked() == null)
            return 0;
        return getUsersWhoLiked().size();
    }

    public boolean didUserLikePost(ParseUser user) {
        return getUsersWhoLiked().contains(user);
    }
}