package com.example.lizandro_fbu_insta;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lizandro_fbu_insta.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private EditText mDescriptionInput;
    private Button mCreateButton;
    private Button mRefreshButton;
    private Button mLogoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDescriptionInput = findViewById(R.id.etDescription2);
        mCreateButton = findViewById(R.id.btnCreate);
        mRefreshButton = findViewById(R.id.btnRefresh);
        mLogoutButton = findViewById(R.id.buttonLogout);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCaptureActivity();
                final String description = mDescriptionInput.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();
            }
        });
        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTopPosts();
            }
        });
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    private void loadTopPosts() {
        final Post.Query postQuery = new Post.Query(Post.class);
        postQuery.getTop().withUser();
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); ++i) {
                        Log.d("HomeActivity", "Post" + i + "] = "
                                + objects.get(i).getDescription()
                                + "\nusername = "
                                + objects.get(i).getUser().getUsername());
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Uri photoUri = data.getData();
            // Do something with the photo based on Uri
            Bitmap selectedImage = null;
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Load the selected image into a preview
            ImageView imageView = (ImageView) findViewById(R.id.ivUserPic);
            imageView.setImageBitmap(selectedImage);
        }
    }

    //start activity for result
    public void startCaptureActivity() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivity(intent);
    }
}
