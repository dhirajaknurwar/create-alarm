package com.master.setalarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;


public class NotificationUtils {
    static final String ACTION_1 = "ACCEPT";
    static final String ACTION_2 = "REJECT";
    // The id of the channel.
    static  String id = "SET ALARM";
    //Method displays notification
    public static void displayNotification(Context context, String title, String message) {

        Intent action1Intent = new Intent(context, NotificationActionService.class)
                .setAction(ACTION_1).setAction(ACTION_2);

        PendingIntent action1PendingIntent = PendingIntent.getService(context, 0,
                action1Intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManager
                    mNotificationManager =
                    (NotificationManager) context
                            .getSystemService(Context.NOTIFICATION_SERVICE);

            // The user-visible name of the channel.
            CharSequence name = "SET ALARM CHANNEL";
            // The user-visible description of the channel.
            String description = "ALARM CONTROL";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // Configure the notification channel.
            mChannel.setDescription(description);
            mChannel.setShowBadge(true);
            mChannel.setLightColor(Color.YELLOW);
            mChannel.setSound(soundUri,null);
            mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(mChannel);
            }

            Notification.Builder builder= new Notification.Builder(context, id)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(android.R.drawable.sym_def_app_icon)
                    .setAutoCancel(true);
            Notification notification = builder.build();
            notification.flags |= Notification.FLAG_INSISTENT;

            if (mNotificationManager != null) {
                mNotificationManager.notify(0, notification);
            }

        }else{


            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(context)
                            .setColor(context.getResources().getColor(R.color.colorPrimary))
                            .setSmallIcon(getNotificationIcon())
                            .setContentTitle(title)
                            .setContentText(message)
                            .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                            .setSound(soundUri)
                            .setPriority(Notification.PRIORITY_MAX)
                            .setAutoCancel(false)
                            .setContentIntent(action1PendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Notification notification = notificationBuilder.build();
            notification.flags |= Notification.FLAG_INSISTENT;

            if (notificationManager != null) {
                notificationManager.notify(0, notification);
            }
        }

    }

    //Getting notification icon as per SDK version
    private static int getNotificationIcon() {
        boolean whiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return whiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
    }


}
