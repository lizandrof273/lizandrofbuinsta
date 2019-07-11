package com.example.lizandro_fbu_insta;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lizandro_fbu_insta.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class CaptureActivity extends Activity {
    private final String tag = "CaptureActivity";
    private EditText mEtDescription;
    private Button mBtnCaptureImage;
    private ImageView mIvPostImage;
    private Button mBtnSubmit;
    public String mPhotoFileName = "photo.jpg";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    private File mPhotoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        mEtDescription = findViewById(R.id.etDescription2);
        mBtnCaptureImage = findViewById(R.id.btnTakePic);
        mIvPostImage = findViewById(R.id.ivUserPic);
        mBtnSubmit = findViewById(R.id.btnSubmit);
        queryPosts();
        mBtnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
            }
        });

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = mEtDescription.getText().toString();
                ParseUser user = ParseUser.getCurrentUser();
                if (mPhotoFile == null  || mIvPostImage.getDrawable() == null) {
                    Log.e(tag, "No photo to submit");
                    Toast.makeText(CaptureActivity.this, "There is no photo", Toast.LENGTH_SHORT).show();
                    return;
                }
                savePost(description, user, mPhotoFile);

            }
        });
    }


    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        mPhotoFile = getPhotoFileUri(mPhotoFileName);
        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(CaptureActivity.this, "com.codepath.fileprovider", mPhotoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                mIvPostImage.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), tag);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(tag, "failed to create directory");
        }
        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return file;
    }

    private void savePost(final String description, final ParseUser parseUser, File photoFile) {
        final ParseFile photo = new ParseFile(photoFile);
        photo.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Post post = new Post();
                    post.setDescription(description);
                    post.setUser(parseUser);
                    post.setImage(photo);
                    post.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                Log.d(tag,"Error while saving");
                                e.printStackTrace();
                                return;
                            }
                            Log.d(tag, "Success!");
                            mEtDescription.setText("");
                            mIvPostImage.setImageResource(0);
                        }
                    });
                }
            }
        });
    }


    private void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e("Error", "Error with query");
                    e.printStackTrace();
                    return;
                }
                for(int i = 0; i < posts.size(); i++) {
                    Post post = posts.get(i);
                    Log.d(tag, "Post: " + posts.get(i).getDescription() + " ,username: " + post.getUser().getUsername());
                }
            }
        });
    }
}
