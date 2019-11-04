package com.example.lekhn.findyourdoctor.MedicineReminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lekhn.findyourdoctor.MainActivity;
import com.example.lekhn.findyourdoctor.R;

import java.util.Calendar;

public class MedicineReminderActivity extends AppCompatActivity {

    Toolbar toolbar;
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_reminder);
        toolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        timePicker = (TimePicker)findViewById(R.id.timepickerMedicine);

        getSupportActionBar().setDisplayShowTitleEnabled(false); //to remove app title from toolbar

        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Medicine Reminder");
        ImageView imageView = (ImageView) findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MedicineReminderActivity.this, MainActivity.class);
                startActivity(intent);
                MedicineReminderActivity.this.finish();
            }
        });

        findViewById(R.id.buttonSetAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //use calendar object to get time and millisecond
                Calendar calendar = Calendar.getInstance();

                if (Build.VERSION.SDK_INT >= 23) {
                    calendar.set(
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH),
                            timePicker.getHour(),
                            timePicker.getMinute(),
                            0);
                }
                else {
                    calendar.set(
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH),
                            timePicker.getCurrentHour(),
                            timePicker.getCurrentMinute(),
                            0);
                }

                setAlram(calendar.getTimeInMillis());
            }
        });
    }

    private void setAlram(long timeInMillis) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MedicineReminderAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
            Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show(); //RTC Wakeup is to use alarm even in sleep mode
        } else {
            Toast.makeText(this, "Alarm is not set", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent homeIntent=new Intent(MedicineReminderActivity.this, MainActivity.class);
        startActivity(homeIntent);
        finish();
    }
}