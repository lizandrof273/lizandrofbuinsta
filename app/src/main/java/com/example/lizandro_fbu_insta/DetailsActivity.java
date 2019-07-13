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
    public ImageView mImageViewPhoto;
    public TextView mTextViewDescription;
    public TextView mTextViewUsername;
    public TextView mTextViewTimeStamp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mImageViewPhoto = findViewById(R.id.imageView);
        mTextViewDescription = findViewById(R.id.textViewDescription);
        mTextViewUsername = findViewById(R.id.textViewUserName);
        mTextViewTimeStamp = findViewById(R.id.textViewTimeStamp);

        Post detailpost = getIntent().getParcelableExtra("post");
        ParseUser detailUser = getIntent().getParcelableExtra("user");

        mTextViewDescription.setText(detailpost.getDescription());
        mTextViewUsername.setText(detailUser.getUsername());
        mTextViewTimeStamp.setText(detailpost.getCreatedAt().toString());
        detailpost.getParseFile(Post.KEY_IMAGE).getFileInBackground(new GetFileCallback() {
            @Override
            public void done(File file, ParseException e) {
                if (e == null) {
                    Glide.with(getApplicationContext()).load(file)
                            .into(mImageViewPhoto);
                }
            }
        });
    }
}
