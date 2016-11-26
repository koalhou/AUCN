package com.aucn.tv.utils;

import android.os.AsyncTask;

import java.text.SimpleDateFormat;

/**
 * Created by mac on 2016/11/6.
 */

public class AsyncLivePeer extends AsyncTask <String, Integer,String>{

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private  String liveIdNow = null;
    public AsyncLivePeer(String initStr){
        this.liveIdNow = initStr;
    }

    @Override
    public String doInBackground(String... params) {
        return "";
    }

    @Override
    protected void onPostExecute(String result){
        if(liveIdNow!=null && result!=null) {
            liveIdNow = result;
        }
        super.onPostExecute(result);
    }
}
