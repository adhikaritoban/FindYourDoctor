package com.example.lekhn.findyourdoctor.MedicineReminder;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.lekhn.findyourdoctor.R;

public final class AlarmLandingPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            );
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_landing_page);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"Landing", Toast.LENGTH_SHORT).show();
        Intent homeIntent=new Intent(this, com.example.lekhn.findyourdoctor.MainActivity.class);
        startActivity(homeIntent);
        finish();
    }
}
