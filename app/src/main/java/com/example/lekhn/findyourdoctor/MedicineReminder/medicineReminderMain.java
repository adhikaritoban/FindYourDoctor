package com.example.lekhn.findyourdoctor.MedicineReminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lekhn.findyourdoctor.MainActivity;
import com.example.lekhn.findyourdoctor.R;

public class medicineReminderMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_reminder_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false); //to remove app title from toolbar

        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Health Reminder");
        ImageView imageView = (ImageView) findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(medicineReminderMain.this, MainActivity.class);
                startActivity(intent);
                medicineReminderMain.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent homeIntent=new Intent(this, com.example.lekhn.findyourdoctor.MainActivity.class);
        startActivity(homeIntent);
        finish();
    }
}
