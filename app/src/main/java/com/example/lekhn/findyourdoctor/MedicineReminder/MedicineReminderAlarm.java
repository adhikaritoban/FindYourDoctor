package com.example.lekhn.findyourdoctor.MedicineReminder;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.example.lekhn.findyourdoctor.R;
import com.facebook.AccessToken;

/**
 * Created by Lekhn on 1/18/2018.
 */

public class MedicineReminderAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        Toast.makeText(context, "Alarm is fired", Toast.LENGTH_SHORT).show();
        final MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.metallica);
        mediaPlayer.start();
    }
}

