package com.example.lekhn.findyourdoctor.Doctor;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lekhn.findyourdoctor.MapsActivity;
import com.example.lekhn.findyourdoctor.R;

public class Doctor_Profile_Activity extends AppCompatActivity{
    TextView doctor_name,doctor_specialist,doctor_hospital,doctor_gmail,doctor_phone,doctor_sms,doctor_comment,
            sundaySch, mondaySch, tuesdaySch, wednesdaySch,thursdaySch, fridaySch, saturdaySch ;
    ImageView doctor_image, location_image;
    Button sendBtn,cancelBtn;
    EditText subject, message;
    String userSubject, userMessage;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    View mview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        Intent intent = getIntent();
        String name = intent.getStringExtra("doctor_name");
        Bitmap bitmap = intent.getParcelableExtra("doctor_image");
        String specialist = intent.getStringExtra("doctor_specialist");
        String hospital = intent.getStringExtra("doctor_hospital");
        String comment = intent.getStringExtra("doctor_comment");
        String contact = intent.getStringExtra("doctor_contact");
        String gmail = intent.getStringExtra("doctor_gmail");

        System.out.println("check int " + name);

        doctor_name = findViewById(R.id.doctor_name_profile);
        doctor_specialist = findViewById(R.id.doctor_specialist_profile);
        doctor_hospital = findViewById(R.id.doctor_hospital_profile);
        doctor_image = findViewById(R.id.doctor_image_profile);
        doctor_gmail = findViewById(R.id.doctor_gmail_profile);
        doctor_phone = findViewById(R.id.doctor_phone_profile);
        doctor_sms = findViewById(R.id.doctor_sms_profile);
        doctor_comment = findViewById(R.id.doctor_comment_profile);
        location_image = findViewById(R.id.location);


        doctor_name.setText(name);
        doctor_specialist.setText(specialist);
        doctor_hospital.setText(hospital);
        doctor_image.setImageBitmap(bitmap);
        doctor_gmail.setText(gmail);
        doctor_phone.setText(contact);
        doctor_sms.setText(contact);
        doctor_comment.setText(comment);


        ImageView imageView = findViewById(R.id.cross);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Doctor_Profile_Activity.this, DoctorActivity.class);
                startActivity(intent);
                Doctor_Profile_Activity.this.finish();
            }
        });

        LinearLayout phoneLayout = findViewById(R.id.doctor_phone_layout_profile);
        phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPhonePermissionGranted()) {
                    call_action();
                }
            }
        });

        LinearLayout smsLayout = findViewById(R.id.doctor_sms_layout_profile);
        smsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSmsPermissionGranted()) {
                    sms_action();
                }
            }
        });

        LinearLayout gmailLayout = findViewById(R.id.doctor_gmail_layout_profile);
        gmailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(Doctor_Profile_Activity.this);
                mview = getLayoutInflater().inflate(R.layout.doctor_profile_diaglog,null);
                sendBtn = mview.findViewById(R.id.sendMailBtn);
                subject = mview.findViewById(R.id.dialogSubject);
                message = mview.findViewById(R.id.dialogMessage);
                builder.setView(mview);
                dialog = builder.create();
                dialog.show();
                sendBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendMail(view);
                    }
                });
                cancelBtn = mview.findViewById(R.id.cancelMailBtn);
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

        location_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckUserPermissions()){
                    openMap();
                }
                else{
                    Toast.makeText(Doctor_Profile_Activity.this,"Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(Doctor_Profile_Activity.this, DoctorActivity.class);
        startActivity(homeIntent);
        finish();
    }

    public boolean isPhonePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {
                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else {
            //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }

    public boolean isSmsPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {
                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
                return false;
            }
        }
        else {
            //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }

    public boolean CheckUserPermissions() {
        Log.d("Check user Permission","Check user permission");
        Toast.makeText(this,"CheckUserPermissions()",Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {
                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return false;
            }
        }
        else {
            //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    call_action();
                } else {
                    //Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 2:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    sms_action();
                } else {
                    //Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
            case 3: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    openMap();
                } else {
                    //Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void openMap() {
        Intent mapIntent = new Intent(Doctor_Profile_Activity.this, MapsActivity.class);
        startActivity(mapIntent);
    }

    private void call_action() {
        String phoneNum = doctor_phone.getText().toString();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNum));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

    private void sms_action() {
        String smsNum = doctor_phone.getText().toString();
        Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + smsNum));
        smsIntent.putExtra("sms_body", "Hello "+doctor_name.getText().toString() + " ");
        startActivity(smsIntent);
    }

    public void sendMail(View view) {
        userSubject = subject.getText().toString();
        userMessage = message.getText().toString();
        if (TextUtils.isEmpty(userSubject)) {
            subject.setError("Enter your Subject");
        } else if (TextUtils.isEmpty(userMessage)) {
            message.setError("Enter Message");
        } else {
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if(activeNetwork !=null && activeNetwork.isConnected()){
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                String doctorMailId = doctor_gmail.getText().toString();
                sendIntent.setType("plain/text");
                sendIntent.setData(Uri.parse("mailto:"));
                sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{doctorMailId});
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, userSubject);
                sendIntent.putExtra(Intent.EXTRA_TEXT,"Dear FindYourDoctor, \n" + userMessage);
                try {
                    startActivity(Intent.createChooser(sendIntent, "Send mail..."));
                }
                catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(this,"No mail app found",Toast.LENGTH_LONG).show();
                }
                catch (Exception ex){
                    Toast.makeText(this,"Unexpected Error" + ex.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            else {
                RelativeLayout relativeLayout = findViewById(R.id.doctor_profile_layout);
                Snackbar snackbar = Snackbar.make(relativeLayout, "No internet connection!", Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();
            }
            dialog.dismiss();
        }
    }
}