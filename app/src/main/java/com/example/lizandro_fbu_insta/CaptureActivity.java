package com.example.lizandro_fbu_insta;

import android.app.Activity;
import android.os.Bundle;

import java.io.File;

public class CaptureActivity extends Activity {
    private final String tag = "CaptureActivity";
    public String mPhotoFileName = "photo.jpg";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    private File mPhotoFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

    }




}
