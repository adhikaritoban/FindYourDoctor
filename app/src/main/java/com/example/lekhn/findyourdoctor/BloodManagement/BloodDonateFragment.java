package com.example.lekhn.findyourdoctor.BloodManagement;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lekhn.findyourdoctor.R;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class BloodDonateFragment extends Fragment {

    EditText donor_name_edit,donor_contact_edit,donote_date_edit,donor_address_edit;
    String donor_name, donor_contact,donote_date,donor_group,donor_zone,donor_address;
    Spinner blood_spinner, zone_spinner;
    ArrayAdapter<CharSequence> blood_adapter;
    ArrayAdapter<CharSequence> zone_adapter;
    private int count=0;

    public BloodDonateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blood_donate,container,false);
        Button registerButton = view.findViewById(R.id.registerDonor);
        donor_name_edit = view.findViewById(R.id.namefield);
        donor_contact_edit = view.findViewById(R.id.numberfield);
        donor_address_edit = view.findViewById(R.id.donor_addressfield);
        donote_date_edit = view.findViewById(R.id.donatedatefield);

        donote_date_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment=new SelectDateFragment();
                dialogFragment.show(getFragmentManager(),"DatePicker");
                //Toast.makeText(getContext(), "Donate", Toast.LENGTH_SHORT).show();
            }
        });

        //to hide keyboard when touch outside the edit text
        view.findViewById(R.id.donateLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

        blood_spinner = view.findViewById(R.id.spinner_blood);
        blood_adapter = ArrayAdapter.createFromResource(getContext(),R.array.blood_group,android.R.layout.simple_spinner_item);
        blood_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        blood_spinner.setAdapter(blood_adapter);

        zone_spinner = view.findViewById(R.id.spinner_zone);
        zone_adapter = ArrayAdapter.createFromResource(getContext(),R.array.donor_zone,android.R.layout.simple_spinner_item);
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
                InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                donor_name = donor_name_edit.getText().toString();
                donor_contact = donor_contact_edit.getText().toString();
                donote_date = donote_date_edit.getText().toString();
                donor_address = donor_address_edit.getText().toString();
                if(TextUtils.isEmpty(donor_name)){
                    donor_name_edit.setError("Enter Your Name");
                }
                else if(TextUtils.isEmpty(donor_contact)){
                    donor_contact_edit.setError("Enter Your Contact number");
                }
                else if(TextUtils.isEmpty(donote_date)){
                    donote_date_edit.setError("Select last donate date");
                }
                else if(TextUtils.isEmpty(donor_address)){
                    donor_address_edit.setError("Enter Your Address");
                }
                else {
                    new AlertDialog.Builder(getContext(),AlertDialog.THEME_HOLO_DARK)
                            .setTitle("REGISTER AS DONOR?")
                            .setMessage("Please Check your details: " + "\n"
                                        +"Full Name: " + donor_name +"\n"
                                        +"Contact: " + donor_contact + "\n"
                                        +"Blood Group: " + donor_group + "\n"
                                        +"Address: " + donor_address)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String method = "register";
                                    BackgroundTaskDonor backgroundTaskDonor = new BackgroundTaskDonor(getActivity());
                                    backgroundTaskDonor.execute(method, donor_name, donor_contact, donote_date, donor_group, donor_zone, donor_address);
                                    Toast.makeText(getContext(), "Registration Success", Toast.LENGTH_SHORT).show();
                                    donor_name_edit.setText(null);
                                    donor_contact_edit.setText(null);
                                    donote_date_edit.setText(null);
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
        BackgroundTaskRequest backgroundTaskRequest = new BackgroundTaskRequest(this.getContext());
        backgroundTaskRequest.execute();
        return view;
    }
}