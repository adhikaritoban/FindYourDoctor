package com.example.lekhn.findyourdoctor.Doctor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.lekhn.findyourdoctor.utilities.ConnectionServer;
import com.example.lekhn.findyourdoctor.utilities.IParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DoctorBackgroundTask extends AsyncTask<String, Void, String> {

    private Context ctx;
    private IParser iParser;

    ArrayList<DoctorGetterSetter> doctorGetterSetterArrayList = new ArrayList<>();

    public DoctorBackgroundTask(Context ctx, IParser iParser) {
        this.ctx = ctx;
        this.iParser = iParser;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (iParser != null) {
            iParser.onPreExecute();
        }
    }

    @Override
    protected String doInBackground(String... string) {
        return ConnectionServer.executeGet("https://sushil-carol.000webhostapp.com/get_doctor_details.php");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println("HomeNewparser_response " + s);

        try {
            JSONObject jsonObjectMain = new JSONObject(s);

            DoctorGetterSetter doctorGetterSetter;

            JSONArray jsonarray = jsonObjectMain.getJSONArray("server_response");

            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject object = jsonarray.getJSONObject(i);

                doctorGetterSetter = new DoctorGetterSetter();

                doctorGetterSetter.setDoctor_id(object.getString("doctor_id"));
                doctorGetterSetter.setDoctor_name(object.getString("doctor_name"));
                doctorGetterSetter.setHospital_name(object.getString("hospital_name"));
                doctorGetterSetter.setImage_url(object.getString("image_url"));
                doctorGetterSetter.setComment(object.getString("comment"));
                doctorGetterSetter.setContact(object.getString("contact"));
                doctorGetterSetter.setGmail(object.getString("gmail"));
                doctorGetterSetter.setDisease_name(object.getString("disease_name"));

                System.out.println(object.getString("doctor_name") );

                doctorGetterSetterArrayList.add(doctorGetterSetter);
            }

            if (iParser != null) {
                iParser.onPostExecute(doctorGetterSetterArrayList);
            } else {
                if (iParser != null) {
                    iParser.onPostFailure();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (iParser != null)
                iParser.onPostFailure();
        }
    }
}
