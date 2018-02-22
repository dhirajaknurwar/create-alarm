package com.master.setalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, TimePickerInterface {

    private TextView mSelectedTimeTextView;

    //Every Day Check Box
    private CheckBox mRepeatCheckBox;

    //Days Check Box
    private CheckBox mSundayCheckBox;
    private CheckBox mMondayCheckBox;
    private CheckBox mTuesdayCheckBox;
    private CheckBox mWednesdayCheckBox;
    private CheckBox mThursdayCheckBox;
    private CheckBox mFridayCheckBox;
    private CheckBox mSaturdayCheckBox;

    private LinearLayout mDaysLinearLayout;

    private int hours = 0;
    private int minutes = 0;
    private AlarmManager mAlarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {

        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        mSelectedTimeTextView = findViewById(R.id.timeValue);
        Button mSelectTimeButton = findViewById(R.id.setTime);
        Button mSetAlarmButton = findViewById(R.id.setAlarm);
        Button mGetAlarmListButton = findViewById(R.id.getAlarmList);

        mRepeatCheckBox = findViewById(R.id.repeatAlarm);

        mSundayCheckBox = findViewById(R.id.daySun);
        mMondayCheckBox = findViewById(R.id.dayMon);
        mTuesdayCheckBox = findViewById(R.id.dayTue);
        mWednesdayCheckBox = findViewById(R.id.dayWed);
        mThursdayCheckBox = findViewById(R.id.dayThu);
        mFridayCheckBox = findViewById(R.id.dayFri);
        mSaturdayCheckBox = findViewById(R.id.daySat);

        mDaysLinearLayout = findViewById(R.id.daysLL);

        mSetAlarmButton.setOnClickListener(this);
        mGetAlarmListButton.setOnClickListener(this);
        mSelectTimeButton.setOnClickListener(this);

        mRepeatCheckBox.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setAlarm:
                //TODO code to set Alarm
                if (mRepeatCheckBox.isChecked()) {
                    //TODO set repeating alarm for selected Time for All Days
                    if (mSundayCheckBox.isChecked() && mMondayCheckBox.isChecked()
                            && mTuesdayCheckBox.isChecked() && mWednesdayCheckBox.isChecked()
                            && mThursdayCheckBox.isChecked() && mFridayCheckBox.isChecked() && mSaturdayCheckBox.isChecked()) {
                        setRepeatingAlarmForAllDays();
                    } else {
                        int alarmId = (Math.abs((int) System.currentTimeMillis()));
                        if (mSundayCheckBox.isChecked()) {
                            setRepeatAlarmForAParticularDay(Calendar.SUNDAY, hours, minutes, alarmId, getUniqueRequestCode());
                        }
                        if (mMondayCheckBox.isChecked()) {
                            setRepeatAlarmForAParticularDay(Calendar.MONDAY, hours, minutes, alarmId, getUniqueRequestCode());
                        }
                        if (mTuesdayCheckBox.isChecked()) {
                            setRepeatAlarmForAParticularDay(Calendar.TUESDAY, hours, minutes, alarmId, getUniqueRequestCode());
                        }
                        if (mWednesdayCheckBox.isChecked()) {
                            setRepeatAlarmForAParticularDay(Calendar.WEDNESDAY, hours, minutes, alarmId, getUniqueRequestCode());
                        }
                        if (mThursdayCheckBox.isChecked()) {
                            setRepeatAlarmForAParticularDay(Calendar.THURSDAY, hours, minutes, alarmId, getUniqueRequestCode());
                        }
                        if (mFridayCheckBox.isChecked()) {
                            setRepeatAlarmForAParticularDay(Calendar.FRIDAY, hours, minutes, alarmId, getUniqueRequestCode());
                        }
                        if (mSaturdayCheckBox.isChecked()) {
                            setRepeatAlarmForAParticularDay(Calendar.SATURDAY, hours, minutes, alarmId, getUniqueRequestCode());
                        }
                    }

                } else {
                    //TODO set alarm for selected Time for selected Days
                    setNormalAlarm();
                }
                break;
            case R.id.getAlarmList:
                //TODO code to get Alarm list

                break;
            case R.id.setTime:
                //TODO code to open time Picker
                openTimePicker();
                break;
            default:
                break;
        }
    }

    private void setNormalAlarm() {
        int requestCode=getUniqueRequestCode();
        Calendar calSet = Calendar.getInstance();
        calSet.set(Calendar.HOUR_OF_DAY, hours);
        calSet.set(Calendar.MINUTE, minutes);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        int alarmId = (Math.abs((int) System.currentTimeMillis()));
        Intent intent = new Intent(getBaseContext(), AlarmBroadcastReceiver.class);
        intent.setAction(AlarmBroadcastReceiver.ACTION);
        intent.putExtra("alarmId", alarmId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), requestCode, intent, 0);

        mAlarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(),
                pendingIntent);
        Log.d("setNormalAlarm", "" + requestCode + "hours" + hours);
    }

    private int getUniqueRequestCode() {
        long requestCode = System.currentTimeMillis();
        return Math.abs((int) requestCode);
    }

    private void setRepeatingAlarmForAllDays() {
        Random random = new Random();
        int requestCode = random.nextInt(10000);
        int alarmId = (Math.abs((int) System.currentTimeMillis()));

        Intent intentPrevious = new Intent(getBaseContext(), AlarmBroadcastReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(getBaseContext(), random.nextInt(10000), intentPrevious, 0);
        mAlarmManager.cancel(pIntent);
        pIntent.cancel();

        Calendar calSet = Calendar.getInstance();
        calSet.set(Calendar.HOUR_OF_DAY, hours);
        calSet.set(Calendar.MINUTE, minutes);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        Intent intent = new Intent(getBaseContext(), AlarmBroadcastReceiver.class);
        intent.setAction(AlarmBroadcastReceiver.ACTION);
        intent.putExtra("alarmId", alarmId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), requestCode, intent, 0);
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                calSet.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Log.d("repeatForAllDays", "" + requestCode + "hours" + hours);
    }

    private void setRepeatAlarmForAParticularDay(int dayOfWeek, int hour, int minutes, int alarmId, int requestCode) {

        Calendar calSet = Calendar.getInstance();
        calSet.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        calSet.set(Calendar.HOUR_OF_DAY, hour);
        calSet.set(Calendar.MINUTE, minutes);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        Intent intent = new Intent(getBaseContext(), AlarmBroadcastReceiver.class);
        intent.setAction(AlarmBroadcastReceiver.ACTION);
        intent.putExtra("alarmId", alarmId);
        intent.putExtra("selected_day", dayOfWeek);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), requestCode, intent, 0);

        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                calSet.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);

        Log.d("repeatForAParticularDay", "" + requestCode + "dayOfWeek" + dayOfWeek);
    }

    private void openTimePicker() {
        TimePickerFragment timePicker = new TimePickerFragment();
        timePicker.delegate = MainActivity.this;
        timePicker.setCancelable(false);
        timePicker.show(getSupportFragmentManager(), "timePicker");
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.repeatAlarm:
                if (!isChecked) {
                    mDaysLinearLayout.setVisibility(View.GONE);
                } else {
                    mDaysLinearLayout.setVisibility(View.VISIBLE);
                }
                checkUnCheckAllDays(isChecked);
                break;

            case R.id.daySun:

                break;

            case R.id.dayMon:
                break;

            case R.id.dayTue:
                break;

            case R.id.dayWed:
                break;

            case R.id.dayThu:
                break;

            case R.id.dayFri:
                break;

            case R.id.daySat:
                break;

            default:
                break;
        }
    }

    private void checkUnCheckAllDays(boolean isChecked) {

        mSundayCheckBox.setChecked(isChecked);
        mMondayCheckBox.setChecked(isChecked);
        mTuesdayCheckBox.setChecked(isChecked);
        mWednesdayCheckBox.setChecked(isChecked);
        mThursdayCheckBox.setChecked(isChecked);
        mFridayCheckBox.setChecked(isChecked);
        mSaturdayCheckBox.setChecked(isChecked);
    }

    @Override
    public void onTimeSelected(int hour, int minute) {
        hours = hour;
        minutes = minute;
        mSelectedTimeTextView.setText(String.valueOf(AppUtils.formatCharLength(2, hour) + ":" + AppUtils.formatCharLength(2, minute)));
       /* Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
        String formatted = format1.format(cal.getTime());*/

    }

}
