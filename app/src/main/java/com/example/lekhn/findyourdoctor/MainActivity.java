package com.example.lekhn.findyourdoctor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lekhn.findyourdoctor.BloodManagement.BloodManagementActivity;
import com.example.lekhn.findyourdoctor.Doctor.DoctorActivity;
import com.example.lekhn.findyourdoctor.Hospital.HospitalActivity;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    SharedPreferences sharedPreferences;
    String TAG=MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_view);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setTitle("Home");

        ImageView userprofile = (ImageView)navigationView.getHeaderView(0).findViewById(R.id.userprofile);
        TextView username = (TextView)navigationView.getHeaderView(0).findViewById(R.id.username);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String firstName = sharedPreferences.getString("firstName","");
        String lastName = sharedPreferences.getString("lastName","");
        String profileUrl = sharedPreferences.getString("profileImageUrl","");
        Glide.with(this).load(profileUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                .into(userprofile);
        username.setText(firstName+" "+lastName);
        navigationView.setItemIconTintList(null);
        navigationView.getMenu().findItem(R.id.home_id).setChecked(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_id:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.doctor_id:
                        Intent doctorIntent=new Intent(MainActivity.this,DoctorActivity.class);
                        startActivity(doctorIntent);
                        getSupportActionBar().setTitle("Doctor");
                        drawerLayout.closeDrawers();
                        finish();
                        break;
                    case R.id.hospital_id:
                        Intent hospitalIntent=new Intent(MainActivity.this,HospitalActivity.class);
                        startActivity(hospitalIntent);
                        getSupportActionBar().setTitle("Hospital");
                        drawerLayout.closeDrawers();
                        finish();
                        break;
                    case R.id.blood_id:
                        Intent bloodIntent=new Intent(MainActivity.this,BloodManagementActivity.class);
                        startActivity(bloodIntent);
                        getSupportActionBar().setTitle("Blood Management");
                        drawerLayout.closeDrawers();
                        finish();
                        break;
                    case R.id.bmi_id:
                        Intent bmiIntent=new Intent(MainActivity.this,BMIActivity.class);
                        startActivity(bmiIntent);
                        getSupportActionBar().setTitle("BMI Calculator");
                        drawerLayout.closeDrawers();
                        finish();
                        break;
                    case R.id.calendar_id:
                        Intent calendarIntent=new Intent(MainActivity.this,com.example.lekhn.findyourdoctor.BloodManagement.BloodRequestActivity.class);
                        startActivity(calendarIntent);
                        getSupportActionBar().setTitle("Health Calendar");
                        drawerLayout.closeDrawers();
                        finish();
                        break;
                    case R.id.medicine_id:
                        Intent medicineIntent=new Intent(MainActivity.this,com.example.lekhn.findyourdoctor.MedicineReminder.medicineReminderMain.class);
                        startActivity(medicineIntent);
                        getSupportActionBar().setTitle("Medicine Reminder");
                        drawerLayout.closeDrawers();
                        finish();
                        break;
                    case R.id.facebook_id:
                        Intent facebookBrowser= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/lekhnath.katuwal.21"));
                        startActivity(facebookBrowser);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.gmail_id:
                        Intent gmailBrowser=new Intent(Intent.ACTION_VIEW, Uri.parse("https://mail.google.com/mail/u/0/#inbox?compose=new"));
                        startActivity(gmailBrowser);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.share_id:
                        Intent shareIntent=new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        String shareBody = "https://www.facebook.com/lekhnath.katuwal.21";
                        shareIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                        startActivity(Intent.createChooser(shareIntent,"Share Using"));
                        break;
                    case R.id.logout:
                        logout();
                }
                return true;
            }
        });
        LinearLayout doctorLayout=(LinearLayout)findViewById(R.id.doctor_card);
        doctorLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent doctorIntent=new Intent(MainActivity.this, DoctorActivity.class);
                startActivity(doctorIntent);
                finish();
            }
        });

        LinearLayout hospitalLayout=(LinearLayout)findViewById(R.id.hospital_card);
        hospitalLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent hospitalIntent=new Intent(MainActivity.this, HospitalActivity.class);
                startActivity(hospitalIntent);
                finish();
            }
        });

        LinearLayout bloodLayout=(LinearLayout)findViewById(R.id.blood_card);
        bloodLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent bloodIntent=new Intent(MainActivity.this, BloodManagementActivity.class);
                startActivity(bloodIntent);
                finish();
            }
        });

        LinearLayout bmiLayout=(LinearLayout)findViewById(R.id.bmi_card);
        bmiLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent bmiIntent=new Intent(MainActivity.this, MapsActivity.class);
                startActivity(bmiIntent);
                finish();
            }
        });

        LinearLayout calendarLayout=(LinearLayout)findViewById(R.id.calendar_card);
        calendarLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent calendarIntent=new Intent(MainActivity.this, com.example.lekhn.findyourdoctor.MedicineReminder.medicineReminderMain.class);
                startActivity(calendarIntent);
                finish();
            }
        });

        LinearLayout pillLayout=(LinearLayout)findViewById(R.id.pill_card);
        pillLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent pillIntent=new Intent(MainActivity.this, com.example.lekhn.findyourdoctor.MedicineReminder.medicineReminderMain.class);
                startActivity(pillIntent);
                finish();
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feedbackIntent = new Intent(MainActivity.this,FeedbackActivity.class);
                startActivity(feedbackIntent);
            }
        });
    }

    private void logout() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (connectivityManager != null) {
            activeNetwork = connectivityManager.getActiveNetworkInfo();
        }
        if(activeNetwork !=null && activeNetwork.isConnected()) {
            //we are connected to a network
            new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Would you like to logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (AccessToken.getCurrentAccessToken() == null) {
                                // already logged out
                            }
                            new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest.Callback() {
                                @Override
                                public void onCompleted(GraphResponse graphResponse) {
                                    LoginManager.getInstance().logOut();
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    MainActivity.this.finish();
                                }
                            }).executeAsync();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // user doesn't want to logout
                        }
                    })
                    .show();
        }
        else {
            RelativeLayout relativeLayout_logout = findViewById(R.id.relativeLayout_home_logout);
            Toast.makeText(getApplicationContext(),"It seems that you are not connected to the internet. Make sure that your device is connected to internet",Toast.LENGTH_SHORT).show();
            final Snackbar snackbar = Snackbar.make(relativeLayout_logout, "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            retry(view);
                        }
                    });
            snackbar.setActionTextColor(Color.GREEN);
            View sbView = snackbar.getView();
            TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }
    }
    private void retry(View view) {
        logout();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);
                Intent aboutIntent=new Intent(MainActivity.this,AboutUsActivity.class);
                finish();
                startActivity(aboutIntent);
                break;

            case R.id.disclaimer:
                Intent disclaimerIntent=new Intent(MainActivity.this,DisclaimerActivity.class);
                startActivity(disclaimerIntent);
                finish();
                break;

            case R.id.settings:
                Intent settingsIntent=new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(settingsIntent);
                finish();
                break;

            case R.id.Rate:
                Intent RateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/4tech-minds-208268179576081/"));
                startActivity(RateIntent);
                break;

            case R.id.update:
                Intent updateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/4tech-minds-208268179576081/"));
                startActivity(updateIntent);
                break;
        }
        return true;
    }
    private void exit() {
        new AlertDialog.Builder(this,AlertDialog.THEME_HOLO_DARK)
                .setTitle("EXIT")
                .setMessage("Would you like to quit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // user doesn't want to quit
                    }
                })
                .show();
    }
    @Override
    public void onBackPressed() {
        exit();
    }
}