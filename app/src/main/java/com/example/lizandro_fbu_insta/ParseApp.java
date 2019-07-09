package com.example.lizandro_fbu_insta;

import android.app.Application;

import com.example.lizandro_fbu_insta.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        //getting started setting up parse server (initialzing)
        final Parse.Configuration  configuration =  new Parse.Configuration.Builder(this)
                .applicationId("lizandro-instagram")
                .clientKey("4902-43437-s583f")
                .server("http://lizandrof273-fbu-insta.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);
    }
}
