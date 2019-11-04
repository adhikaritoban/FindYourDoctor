package com.example.lekhn.findyourdoctor.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Vurung on 1/14/2019.
 */

public class ConnectionServer {

    static String RESPONSE_CODE_SUCCESS = "200";

    /**
     * Check if internet is available or not
     * @param ctx pass context of the class from where method is called
     * @return  returns internet connectivity status
     */

    public static boolean checkConnection(Context ctx){
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info == null){
            return false;
        }else if (!info.isConnected()){
            return false;
        }else if (!info.isAvailable()){
            return false;
        }
        return true;
    }

    /*
     *  Parse Post url
     * @param context context of the class from where the method is called
     * @param targetURL post url
     * @param urlParameters post parameters of the url
     * @return returns response send by the url
     */

    public static String executePost(Context context, String targetURL, String urlParameters){

        Log.d("url---", targetURL + ">>");
        Log.d("parameter---", urlParameters + ">>");

        URL url;
        HttpURLConnection connection = null;

        try{
            if (checkConnection(context)){
                url = new URL(targetURL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                //connection.setConnectTimeout(10000);
                connection.setRequestProperty("Content-Type","application/json");

                connection.setUseCaches(false);
                connection.setDoOutput(true);
                connection.setDoInput(true);
                // connection.setRequestProperty("Authorization", TOKEN);
                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());

                dataOutputStream.writeBytes(urlParameters);

                dataOutputStream.flush();
                dataOutputStream.close();

                connection.connect();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line = "";
                StringBuilder responseOutput = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null) {
                    responseOutput.append(line);
                }

                Log.d("Output   ", responseOutput.toString());

                bufferedReader.close();

                return (responseOutput.toString());
            }else{
                Toast.makeText(context, "No Internet Available!", Toast.LENGTH_SHORT).show();
                return "";
            }
        }catch (Exception e) {
            e.printStackTrace();
            return "";
        }finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /*
     *  Parse Get url
     * @param context context of the class from where the method is called
     * @param targetURL get url
     * @return returns response send by the url
     */
    @Nullable
    public static String executeGet(String urlString){

        urlString = urlString.replaceAll(" ", "%20");
        Log.d("url---", urlString + ">>");
        InputStream in = null;
        int rescode = -1;
        HttpURLConnection httpConn = null;
        try {
            URL url = new URL(urlString);

            httpConn = (HttpURLConnection) url.openConnection();;
            //httpConn.setAllowUserInteraction(false);
            //httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setDoInput(true);
            httpConn.connect();

            rescode = httpConn.getResponseCode();
            if (rescode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
    }
}
