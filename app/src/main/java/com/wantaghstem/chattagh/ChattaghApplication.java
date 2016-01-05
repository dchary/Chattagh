package com.wantaghstem.chattagh;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseObject;
import com.wantaghstem.chattagh.techday.TimelinePost;

public class ChattaghApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "zesHxZqpereJ4ZG9AFDlHrnHXDkY9Wpb2xFySdYf", "mRdUibZdwNoR0MBZYZIfW5WPtpqjInKvLB9XUdRa");
        ParseObject.registerSubclass(TimelinePost.class);
    }
}
