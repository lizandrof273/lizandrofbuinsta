package com.example.lizandro_fbu_insta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    private TextView tvPassword;
    private TextView tvUserName;
    private Button btnLogIn;
    private Button buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            final Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        setContentView(R.layout.activity_login);
        tvUserName = findViewById(R.id.tvUserName);
        tvPassword = findViewById(R.id.tvPassword);
        btnLogIn = findViewById(R.id.btnLogIn);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //takes the info the user passes in
                final String username = tvUserName.getText().toString();
                final String password = tvPassword.getText().toString();
                login(username, password);
            }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = tvUserName.getText().toString();
                final String password = tvPassword.getText().toString();
                signUp(username, password);
            }
        });
    }

    private void login (String username, String password) {
        //check if correct then login
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Log.d("LoginActivity", "Login success!");
                    final Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("LoginActivity", "Login failure." );
                    e.printStackTrace();
                }
            }
        });
    }

    private void signUp (String username, String password) {
        ParseUser user = new ParseUser();
    // Set core properties
        user.setUsername(username);
        user.setPassword(password);
    // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
    }
}
