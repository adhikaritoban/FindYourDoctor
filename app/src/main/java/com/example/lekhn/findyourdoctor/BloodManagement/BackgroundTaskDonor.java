package com.example.lekhn.findyourdoctor.BloodManagement;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Lekhn on 1/13/2018.
 */

public class BackgroundTaskDonor extends AsyncTask<String, Void, String> {

    Context ctx;
    public BackgroundTaskDonor(Context ctx){
        this.ctx = ctx;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String register_url = "http://192.168.43.9/fyp/donor_details.php";
        String method = params[0];

        if (method.equals("register")) {
            String donor_name = params[1];
            String donor_contact = params[2];
            String donate_date = params[3];
            String donor_group = params[4];
            String donor_zone = params[5];
            String donor_address = params[6];

            try {
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter((new OutputStreamWriter(outputStream, "UTF-8")));
                String data = URLEncoder.encode("donor_name", "UTF-8") + "=" + URLEncoder.encode(donor_name, "UTF-8")
                        +"&"+URLEncoder.encode("donor_contact", "UTF-8")+"=" + URLEncoder.encode(donor_contact, "UTF-8")
                        +"&"+URLEncoder.encode("donate_date", "UTF-8")+"="+ URLEncoder.encode(donate_date, "UTF-8")
                        +"&"+URLEncoder.encode("donor_group", "UTF-8")+"=" + URLEncoder.encode(donor_group, "UTF-8")
                        +"&"+URLEncoder.encode("donor_zone", "UTF-8")+"=" + URLEncoder.encode(donor_zone, "UTF-8")
                        +"&"+URLEncoder.encode("donor_address", "UTF-8")+"=" + URLEncoder.encode(donor_address, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                return "Registration Success...";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {

    }
}
