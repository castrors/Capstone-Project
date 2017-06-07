package com.castrodev.wishlist.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.castrodev.wishlist.R;
import com.castrodev.wishlist.main.MainActivity;
import com.castrodev.wishlist.model.Offer;

/**
 * Created by rodrigocastro on 07/06/17.
 */

public class NotificationFactory {

    public static void create(Context context, Offer offer) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(offer.getTitle())
                        .setContentText(offer.getDescription());

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(offer.getUrl()));

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(context, 0, intent, 0);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(42, mBuilder.build());

    }
}
