package com.example.lizandro_fbu_insta;

import android.app.Application;

import com.parse.Parse;

public class ParseApp extends Application {
    public void onCreate() {
        super.onCreate();

        //getting started setting up parse server (initialzing)
        final Parse.Configuration  configuration =  new Parse.Configuration.Builder(this)
                .applicationId("lizandro-instagram")
                .clientKey("4902-43437-s583f")
                .server("http://lizandrof273-fbu-insta.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);
    }
}
