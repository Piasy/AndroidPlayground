package com.github.piasy.notificationtest;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Piasy{github.com/Piasy} on 16/1/11.
 */
public class NotificationUtil {

    private static int sCount = 0;

    public static void fireNotification(Intent intent, Context context) {
        NotificationManager notifyMgr =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent pi =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notifyMgr.notify(1000, getCommonBuilder(context).setContentTitle(
                context.getString(R.string.app_name))
                .setContentText("fireNotification " + sCount)
                .setContentIntent(pi)
                .build());
        sCount++;
    }

    public static void fireNotificationWithPendingIntent(PendingIntent pendingIntent,
            Context context) {
        NotificationManager notifyMgr =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notifyMgr.notify(1001, getCommonBuilder(context).setContentTitle(
                context.getString(R.string.app_name))
                .setContentText("fireNotificationWithPendingIntent " + sCount)
                .setContentIntent(pendingIntent)
                .build());
        sCount++;
    }

    private static NotificationCompat.Builder getCommonBuilder(Context context) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] pattern = { 500, 500, 500, 500 };
        return new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.ic_launcher)
                .setSound(alarmSound)
                .setVibrate(pattern)
                .setLights(Color.BLUE, 500, 500)
                .setAutoCancel(true);
    }

}
