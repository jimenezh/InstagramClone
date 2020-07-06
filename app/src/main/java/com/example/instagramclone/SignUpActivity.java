package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.instagramclone.databinding.ActivitySignUpBinding;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    public static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.etUsername.getText().toString();
                String password = binding.etPassword.getText().toString();
                String email = binding.etEmail.getText().toString();
                signUpUser(username, email, password);
            }
        });
    }

    private void signUpUser(final String username, String email, final String password) {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.i(TAG, "Signed in!");
                    Toast.makeText(SignUpActivity.this, "Signed up!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.e(TAG, "Sign Up Error", e);
                    Toast.makeText(SignUpActivity.this, "Could not sign up", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}