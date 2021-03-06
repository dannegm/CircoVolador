package com.amb.circovolador;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.amb.circovolador.activities.Streaming;

public class StreamingControls {
    private static final String NOTIFICATION_TAG = "StreamingControls";

    public static void notify (final Context context, final String exampleString, final int number) {
        final Resources res = context.getResources();

        final String ticker = exampleString;
        final String title = "Circo Volador Radio";

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setDefaults(Notification.DEFAULT_ALL)

                .setSmallIcon(R.drawable.ic_radio)
                .setContentTitle(title)

                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setTicker(ticker)
                .setContentIntent(
                        PendingIntent.getActivity(context, 0,
                                new Intent(context, Streaming.class),
                                PendingIntent.FLAG_UPDATE_CURRENT))

                .addAction(R.drawable.ic_action_stat_reply, "Stop", null);

        notify(context, builder.build());
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private static void notify(final Context context, final Notification notification) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.notify(NOTIFICATION_TAG, 0, notification);
        } else {
            nm.notify(NOTIFICATION_TAG.hashCode(), notification);
        }
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public static void cancel (final Context context) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.cancel(NOTIFICATION_TAG, 0);
        } else {
            nm.cancel(NOTIFICATION_TAG.hashCode());
        }
    }
}