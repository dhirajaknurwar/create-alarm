package com.master.setalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    public static final String ACTION = "com.action.alarm";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getExtras() != null) {
            if (intent.getExtras().get("alarmId") != null) {
                if (intent.getExtras().get("selected_day") != null) {
                    Calendar calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_WEEK);
                    if (intent.getExtras().getInt("selected_day") >= day) {
                        Log.e("AlarmReceiver", "onReceive: " + intent.getExtras().get("alarmId") + " " + intent.getExtras().getInt("selected_day"));
                        NotificationUtils.displayNotification(context, "ALARM", "" + intent.getExtras().get("alarmId"));
                    }
                } else {
                    Log.e("AlarmReceiver", "onReceive: " + intent.getExtras().get("alarmId"));
                    NotificationUtils.displayNotification(context, "ALARM", "" + intent.getExtras().get("alarmId"));
                }

            }
        }

    }

}
