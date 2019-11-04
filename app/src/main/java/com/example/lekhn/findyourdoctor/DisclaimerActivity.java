package com.example.lekhn.findyourdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DisclaimerActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer);
        toolbar=(Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false); //to remove app title from toolbar

        TextView title=(TextView)findViewById(R.id.title);
        title.setText("Disclaimer");
        ImageView imageView=(ImageView)findViewById(R.id.cross);
        imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DisclaimerActivity.this, MainActivity.class);
                startActivity(intent);
                DisclaimerActivity.this.finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent homeIntent=new Intent(DisclaimerActivity.this, MainActivity.class);
        startActivity(homeIntent);
        finish();
    }
}
