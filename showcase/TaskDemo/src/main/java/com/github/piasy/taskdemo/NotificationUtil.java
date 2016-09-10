/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.piasy.taskdemo;

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
