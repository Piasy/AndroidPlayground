package com.github.piasy.notificationtest;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
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
                startActivity(new Intent(MainActivity.this, NotifyActivity.class));
                fireNotification(new Intent(MainActivity.this, NotifyActivity.class));
            }
        });
    }

    private void fireNotification(Intent intent) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] pattern = { 500, 500, 500, 500 };

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
                        .setSound(alarmSound)
                        .setVibrate(pattern)
                        .setLights(Color.BLUE, 500, 500)
                        .setAutoCancel(true);
        NotificationManager notifyMgr =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pi =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notifyMgr.notify(1000, builder.setContentTitle(this.getString(R.string.app_name))
                .setContentText("test notification")
                .setContentIntent(pi)
                .build());
    }
}
