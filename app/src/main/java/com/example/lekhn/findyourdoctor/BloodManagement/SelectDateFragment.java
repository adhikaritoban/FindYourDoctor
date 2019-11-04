package com.example.lekhn.findyourdoctor.BloodManagement;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lekhn.findyourdoctor.R;

import java.time.Year;
import java.util.Calendar;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Lekhn on 1/13/2018.
 */

public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    EditText dateeditText;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),this,year,month,day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        populateSetDate(year,month+1,day);
    }

    public void populateSetDate(final int year, final int month, final int day){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_blood_donate, null);

// = (EditText) view.findViewById(R.id.donatedatefield);
//       dateeditText.setText(year + "-"+month+"-"+day, TextView.BufferType.EDITABLE);
//       Toast.makeText(getApplicationContext(), "Select", Toast.LENGTH_LONG).show();

        dateeditText = getActivity().findViewById(R.id.donatedatefield);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dateeditText.setText(year+"-"+month+"-"+day);
            }
        });

    }
}
