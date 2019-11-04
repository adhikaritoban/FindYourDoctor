package com.example.lekhn.findyourdoctor.Doctor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DoctorBackgroundTask extends AsyncTask<Void, Void, Void> {

    private Context ctx;

    public DoctorBackgroundTask(Context ctx){
        this.ctx = ctx;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            String json_url = "http://192.168.43.180/fyp/get_doctor_details.php";
            URL url = new URL(json_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line).append("\n");
            }
            httpURLConnection.disconnect();
            inputStream.close();
            bufferedReader.close();
            String json_data = stringBuilder.toString().trim();
            Log.d("JSON-String", json_data);
            JSONObject jsonObject = new JSONObject(json_data);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
            DoctorDBHelper doctorDBHelper = new DoctorDBHelper(ctx);
            SQLiteDatabase sqLiteDatabase = doctorDBHelper.getWritableDatabase();

            int count = 0;
            while (count<jsonArray.length()){
                JSONObject JO = jsonArray.getJSONObject(count);
                doctorDBHelper.insertData(JO.getString("doctor_id"),
                        JO.getString("doctor_name"),
                        JO.getString("specialist"),
                        JO.getString("hospital_name"),
                        JO.getString("image_url"),
                        JO.getString("comment"),
                        JO.getString("contact"),
                        JO.getString("gmail"),
                        JO.getString("sunday"),
                        JO.getString("monday"),
                        JO.getString("tuesday"),
                        JO.getString("wednesday"),
                        JO.getString("thursday"),
                        JO.getString("friday"),
                        JO.getString("saturday"),
                        JO.getString("disease_name"),
                        sqLiteDatabase);
                count++;
            }
            doctorDBHelper.close();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
