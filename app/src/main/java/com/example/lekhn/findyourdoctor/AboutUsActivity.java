package com.example.lekhn.findyourdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        toolbar=(Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false); //to remove app title from toolbar

        TextView title=(TextView)findViewById(R.id.title);
        title.setText("About Us");
        ImageView imageView=(ImageView)findViewById(R.id.cross);
        imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                overridePendingTransition(R.anim.buttom_up, R.anim.buttom_down);
                Intent intent=new Intent(AboutUsActivity.this, MainActivity.class);
                AboutUsActivity.this.finish();
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent homeIntent=new Intent(AboutUsActivity.this, MainActivity.class);
        startActivity(homeIntent);
        finish();
    }
}