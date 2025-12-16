package com.example.android_worker;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        checkNotificationPermission();
        oneTimeNotification();
        repeatNotification();

    }
    private void oneTimeNotification(){
        //this notification will surely shown if permission is granted after 15 seconds.
        //whatever the app is on background, foreground or killed. the code must be execute
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .setInitialDelay(15, TimeUnit.SECONDS)
                .build();
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);



    }

    private void repeatNotification(){
        /*this notification will shown in every 15 minutes.
        however repeat worker can't be repeated in less than 15 minute*/

        PeriodicWorkRequest periodicWorkRequest =
                new PeriodicWorkRequest.Builder(NotificationWorker.class, 15 , TimeUnit.MINUTES)
                        .build();
        WorkManager.getInstance(this).enqueue(periodicWorkRequest);


    }
    private void checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                openAppNotificationSettings();
            }
        }
    }

    private void openAppNotificationSettings(){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);

        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());

        startActivity(intent);
    }


}