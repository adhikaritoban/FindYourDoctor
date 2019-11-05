package com.example.lekhn.findyourdoctor.BloodManagement;

import android.app.ProgressDialog;
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

public class BackgroundTaskRequest extends AsyncTask<Void, Void, Void> {

    Context ctx;
    ProgressDialog progressDialog;
    private String json_url = "https://sushil-carol.000webhostapp.com/get_donor_details.php";

    public BackgroundTaskRequest(Context ctx){
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading Donor List");
        progressDialog.setCancelable(false);
        progressDialog.show();
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
                Thread.sleep(500);
            }
            httpURLConnection.disconnect();
            inputStream.close();
            bufferedReader.close();
            String json_data = stringBuilder.toString().trim();
            Log.d("JSON-String", json_data);
            JSONObject jsonObject = new JSONObject(json_data);
            JSONArray jsonArray = jsonObject.getJSONArray("donor_server_response");
            BloodRequestDBHelper bloodRequestDBHelper = new BloodRequestDBHelper(ctx);
            SQLiteDatabase sqLiteDatabase = bloodRequestDBHelper.getWritableDatabase();

            int count = 0;
            while (count<jsonArray.length()){
                JSONObject JO = jsonArray.getJSONObject(count);
                count++;
                bloodRequestDBHelper.insertDonorData(JO.getString("donor_name"),
                        JO.getString("donor_contact"),
                        JO.getString("donor_group"),
                        JO.getString("donor_zone"),
                        JO.getString("donor_address"),
                        sqLiteDatabase);
            }
            bloodRequestDBHelper.close();

        } catch (IOException | JSONException | InterruptedException e) {
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
        progressDialog.dismiss();
    }
}
