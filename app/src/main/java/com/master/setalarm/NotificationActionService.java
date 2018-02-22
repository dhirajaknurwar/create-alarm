package com.master.setalarm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

public class NotificationActionService extends IntentService {
    public NotificationActionService() {
        super(NotificationActionService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();

        NotificationManager mNotification = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotification.cancelAll();

        if (NotificationUtils.ACTION_1.equalsIgnoreCase(action)) {

        }

        if (NotificationUtils.ACTION_2.equalsIgnoreCase(action)) {

        }
    }

}
