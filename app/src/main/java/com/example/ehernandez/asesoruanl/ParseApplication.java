package com.example.ehernandez.asesoruanl;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by ehernandez on 26/02/2016.
 */
public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);

        // Add your initialization code
        Parse.initialize(this, "4LADG3ddIdsDEX4wwxDZo4mfHfB6eIujedTE1RWP",
                "1vGZgESLbzmj20VQ9KveA32wx8xUXPweKd7F6XHx");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // Optionally enable public read access.
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
