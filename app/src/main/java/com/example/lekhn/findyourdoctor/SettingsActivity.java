package com.example.lekhn.findyourdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Lekhn on 1/13/2018.
 */

public class SettingsActivity extends AppCompatActivity {
    Toolbar toolbar;
    final CheckBox notificationCb;
    final SwitchCompat silentSwitch;

    public SettingsActivity(CheckBox notificationCb, SwitchCompat silentSwitch) {
        this.notificationCb = notificationCb;
        this.silentSwitch = silentSwitch;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbar=(Toolbar) findViewById(R.id.toolbar_layout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //to remove app title from toolbar

        TextView title=(TextView)findViewById(R.id.title);
        title.setText("Disclaimer");
        ImageView imageView=(ImageView)findViewById(R.id.cross);
        imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
                SettingsActivity.this.finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent homeIntent=new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(homeIntent);
        finish();
    }
}
