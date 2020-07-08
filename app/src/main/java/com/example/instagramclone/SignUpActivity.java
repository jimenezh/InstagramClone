package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.instagramclone.databinding.ActivitySignUpBinding;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    public static final String TAG ="SignUpActivity";
    private String username;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = binding.etUsername.getText().toString();
                email = binding.etEmail.getText().toString();
                password = binding.etPassword.getText().toString();

                signUpUser();
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void signUpUser() {
        Toast.makeText(SignUpActivity.this, "Sign up!", Toast.LENGTH_SHORT).show();
        Log.i(TAG,"in Sign up user");
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        Log.i(TAG,"User has "+username+" "+password+" "+" "+email);

        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                Toast.makeText(SignUpActivity.this, "In call back!", Toast.LENGTH_SHORT).show();

                if (e == null) {
                    // Hooray! Let them use the app now.
                    Toast.makeText(SignUpActivity.this, "User signed up!",Toast.LENGTH_LONG).show();
                    logInUser();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Log.e(TAG, "Error SignUp", e);
                    Toast.makeText(SignUpActivity.this, "Could not sign up",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void logInUser() {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Log.i(TAG, user.getUsername() + " is signed in");
                    Toast.makeText(SignUpActivity.this, user.getUsername() + " is signed in", Toast.LENGTH_SHORT);
                    launchMainActivity();
                } else {
                    Log.e(TAG, "Parse Log In exception", e);
                    Toast.makeText(SignUpActivity.this, "Could not sign in", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        // Clears stack
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}