package com.example.lizandro_fbu_insta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lizandro_fbu_insta.model.Post;
import com.parse.GetFileCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.io.File;

public class DetailsActivity extends AppCompatActivity {
    public ImageView imageViewPhoto;
    public TextView textViewDescription;
    public TextView textViewUsername;
    public TextView textViewTimeStamp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imageViewPhoto = findViewById(R.id.imageView);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewUsername = findViewById(R.id.textViewUserName);
        textViewTimeStamp = findViewById(R.id.textViewTimeStamp);

        Post detailpost = getIntent().getParcelableExtra("post");
        ParseUser detailUser = getIntent().getParcelableExtra("user");

        textViewDescription.setText(detailpost.getDescription());
        textViewUsername.setText(detailUser.getUsername());
        textViewTimeStamp.setText(detailpost.getCreatedAt().toString());
        detailpost.getParseFile(Post.KEY_IMAGE).getFileInBackground(new GetFileCallback() {
            @Override
            public void done(File file, ParseException e) {
                if (e == null) {
                    Glide.with(getApplicationContext()).load(file)
                            .into(imageViewPhoto);
                }
            }
        });
    }
}
