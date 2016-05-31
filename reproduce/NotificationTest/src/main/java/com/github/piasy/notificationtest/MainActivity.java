package com.github.piasy.notificationtest;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.mBtnTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });

        findViewById(R.id.mBtnSimpleNotify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotifyActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                NotificationUtil.fireNotification(
                        intent, MainActivity.this);
            }
        });

        findViewById(R.id.mBtnBackNotify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent(MainActivity.this, NotifyActivity.class);
                //resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.this)
                        .addParentStack(NotifyActivity.class)
                        .addNextIntent(resultIntent);
                NotificationUtil.fireNotificationWithPendingIntent(
                        stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT),
                        MainActivity.this);
            }
        });
    }
}
