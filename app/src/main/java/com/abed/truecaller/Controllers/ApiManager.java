package com.abed.truecaller.Controllers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.abed.truecaller.Controllers.ResponseProcessors.ResponseProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Abed on 5/1/15.
 */


public class ApiManager extends AsyncTask<Void, Integer, String> {
    private String LOG_TAG=getClass().getName();

    private String url;
    private Context context;
    private ResponseProcessor responseProcessor;


    private ApiManager(Context context,String url) {
        this.url = url;
        this.context = context;
    }


    public static ApiManager with(Context c,String url)
    {
        return new ApiManager(c,url);
    }

    public ApiManager processResponceWith(ResponseProcessor r)
    {
        responseProcessor=r;
        return this;
    }


    public void build(){
        this.execute();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        if (checkInternetConnection(context)) {
            try {
                return makeURLRequest(url);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return null;
        }

    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d(LOG_TAG,result);
        if(responseProcessor!=null)
        {
            responseProcessor.processResponse(result);

        }
    }



    /**
     * URL request.
     *
     * @return the string
     */
    protected String makeURLRequest(String url) throws Exception {
        String response = "";
        try {
            URL myURL = new URL(url);
            HttpURLConnection myURLConnection = (HttpURLConnection)myURL.openConnection();
            myURLConnection.setUseCaches(true);
            myURLConnection.setRequestMethod("GET");
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            myURLConnection.addRequestProperty("Cache-Control", "max-stale=" + maxStale);
            myURLConnection.getInputStream();

            InputStream inputStream = myURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                total.append(line);
            }
            response =total.toString().trim();
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * Check internet connection.
     *
     * @param context the context
     * @return true, if successful
     */
    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connection = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connection.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

}
