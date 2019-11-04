package com.example.lekhn.findyourdoctor.Hospital;

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

public class HospitalBackgroundTask extends AsyncTask<Void, Void, Void> {
    Context ctx;
    private String json_url = "http://192.168.43.180/fyp/get_hospital_details.php";

    public HospitalBackgroundTask(Context ctx){
        this.ctx = ctx;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL(json_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line+"\n");
            }

            httpURLConnection.disconnect();
            inputStream.close();
            bufferedReader.close();

            String json_data = stringBuilder.toString().trim();
            Log.d("Hospital JSON-String", json_data);
            JSONObject jsonObject = new JSONObject(json_data);
            JSONArray jsonArray = jsonObject.getJSONArray("h_server_response");
            HospitalDBHelper hospitalDBHelper = new HospitalDBHelper(ctx);
            SQLiteDatabase sqLiteDatabase = hospitalDBHelper.getWritableDatabase();

            int count = 0;
            while (count<jsonArray.length()){
                JSONObject JO = jsonArray.getJSONObject(count);
                hospitalDBHelper.insert_hos_Data(JO.getString("hospital_id"),JO.getString("hospital_names"),
                        JO.getString("hospital_address"),
                        JO.getString("image_url"),
                        JO.getString("total_doc"),
                        JO.getString("total_bed"),
                        JO.getString("total_icu"),sqLiteDatabase);
                count++;
            }
            hospitalDBHelper.close();

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