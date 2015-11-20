package com.example.andrejssileckis.fragmenttestactivity;

/**
 * Created by andrejs.sileckis on 10/20/2015.
 */

import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class NotificationViewer extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        NotificationManager mNotificationManager =
                (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.cancel(getIntent().getExtras().getInt("notificationID"));
    }
}
