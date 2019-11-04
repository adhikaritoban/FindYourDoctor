package com.example.lekhn.findyourdoctor.BloodManagement;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lekhn.findyourdoctor.MainActivity;
import com.example.lekhn.findyourdoctor.R;

import java.util.Calendar;

public class BloodManagementActivity extends AppCompatActivity{
    Toolbar toolbar;
    EditText donor_name_edit,donor_contact_edit,donate_date_edit,donor_address_edit;
    String donor_name, donor_contact,donote_date,donor_group,donor_zone,donor_address;
    Spinner blood_spinner, zone_spinner;
    ArrayAdapter<CharSequence> blood_adapter;
    ArrayAdapter<CharSequence> zone_adapter;
    private int count=0;
    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_management);
        toolbar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayShowTitleEnabled(false); //to remove app title from toolbar

        Button registerButton = findViewById(R.id.registerDonor);
        donor_name_edit = findViewById(R.id.namefield);
        donor_contact_edit = findViewById(R.id.numberfield);
        donor_address_edit = findViewById(R.id.donor_addressfield);
        donate_date_edit = findViewById(R.id.donatedatefield);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                donate_date_edit.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
            }
        };

        donate_date_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(BloodManagementActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Blood Management");
        ImageView imageView = findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BloodManagementActivity.this, MainActivity.class);
                startActivity(intent);
                BloodManagementActivity.this.finish();
            }
        });

        findViewById(R.id.donateLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

        blood_spinner = findViewById(R.id.spinner_blood);
        blood_adapter = ArrayAdapter.createFromResource(this,R.array.blood_group,android.R.layout.simple_spinner_item);
        blood_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        blood_spinner.setAdapter(blood_adapter);

        zone_spinner = findViewById(R.id.spinner_zone);
        zone_adapter = ArrayAdapter.createFromResource(this,R.array.donor_zone,android.R.layout.simple_spinner_item);
        zone_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zone_spinner.setAdapter(zone_adapter);

        blood_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                donor_group = adapterView.getItemAtPosition(position).toString();
                //Toast.makeText(getContext(),adapterView.getItemAtPosition(position) + " selected",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        zone_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                donor_zone= adapterView.getItemAtPosition(position).toString();
                //Toast.makeText(getContext(),adapterView.getItemAtPosition(position) + " selected",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //to hide keyboard when click on button
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                donor_name = donor_name_edit.getText().toString();
                donor_contact = donor_contact_edit.getText().toString();
                donote_date = donate_date_edit.getText().toString();
                donor_address = donor_address_edit.getText().toString();
                if (TextUtils.isEmpty(donor_name)) {
                    donor_name_edit.setError("Enter Your Name");
                } else if (TextUtils.isEmpty(donor_contact)) {
                    donor_contact_edit.setError("Enter Your Contact number");
                } else if (TextUtils.isEmpty(donote_date)) {
                    donate_date_edit.setError("Select last donate date");
                } else if (TextUtils.isEmpty(donor_address)) {
                    donor_address_edit.setError("Enter Your Address");
                } else {
                    new AlertDialog.Builder(BloodManagementActivity.this, AlertDialog.THEME_HOLO_DARK)
                            .setTitle("REGISTER AS DONOR?")
                            .setMessage("Please Check your details: " + "\n"
                                    + "Full Name: " + donor_name + "\n"
                                    + "Contact: " + donor_contact + "\n"
                                    + "Blood Group: " + donor_group + "\n"
                                    + "Address: " + donor_address)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String method = "register";
                                    BackgroundTaskDonor backgroundTaskDonor = new BackgroundTaskDonor(BloodManagementActivity.this);
                                    backgroundTaskDonor.execute(method, donor_name, donor_contact, donote_date, donor_group, donor_zone, donor_address);
                                    Toast.makeText(BloodManagementActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                                    donor_name_edit.setText(null);
                                    donor_contact_edit.setText(null);
                                    donate_date_edit.setText(null);
                                    donor_address_edit.setText(null);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(BloodManagementActivity.this, MainActivity.class);
        startActivity(homeIntent);
        finish();
    }
}
