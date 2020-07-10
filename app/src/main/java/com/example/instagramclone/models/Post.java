package com.example.instagramclone.models;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseClassName;
import com.parse.ParseUser;

import java.lang.reflect.Array;

@ParseClassName("Post")
public class Post extends ParseObject {
    public static final String KEY_DESCRIPTION ="description";
    public static final String  KEY_IMAGE="image";
    public static final String KEY_USER="user";
    public static final String KEY_CREATED_AT="createdAt";
    public static final String KEY_LIKES = "likes";

    public Post() {
    }

    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
        setNumLikes(null);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image){
        put(KEY_IMAGE, image);
    }


    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public void setNumLikes(ParseUser[] numLikes){
        put(KEY_LIKES, numLikes);
    }

    public ParseUser[] getUsersWhoLiked(){
        return (ParseUser[]) get(KEY_LIKES);
    }
    public int getNumberOfLikes() {
        if(getUsersWhoLiked() == null)
            return 0;
        return getUsersWhoLiked().length;
    }
}