package com.example.lekhn.findyourdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.lekhn.findyourdoctor.BloodManagement.BackgroundTaskDonor;
import com.example.lekhn.findyourdoctor.Doctor.DoctorActivity;
import com.example.lekhn.findyourdoctor.Doctor.DoctorBackgroundTask;
import com.example.lekhn.findyourdoctor.Hospital.HospitalBackgroundTask;
import com.facebook.AccessToken;

public class SplashScreen extends AppCompatActivity {

    TextView textView;
    //ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //imageView=(ImageView)findViewById(R.id.imageView);
        textView=(TextView)findViewById(R.id.textview);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.mytransation);
        //imageView.startAnimation(animation);
        textView.startAnimation(animation);
        final Intent loginintent=new Intent(this,LoginActivity.class);
        final Intent mainintent=new Intent(this,MainActivity.class);
        Thread timer=new Thread(){
            public void run(){
                try{
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    if (AccessToken.getCurrentAccessToken()!=null){
                        startActivity(mainintent);
                        finish();
                    }
                    else {
                        startActivity(mainintent);
                        finish();
                    }
                }
            }
        };
        timer.start();
    }
}