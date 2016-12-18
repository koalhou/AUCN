package com.aucn.tv.utils;

import android.os.AsyncTask;

import com.aucn.tv.config.Config;
import com.aucn.tv.config.DB;
import com.google.gson.Gson;


/**
 * Created by mac on 2016/11/6.
 */

public class AsyncTaskPlayListLoad2 extends AsyncTask <String, Integer, String>{


    @Override
    public String doInBackground(String... params) {
        HttpUtils hu = HttpUtils.getInstance(this.getClass().getName());
//        String query = "http://localhost:8080/AUCN/GetData";
        String query = "http://ec2-54-206-122-135.ap-southeast-2.compute.amazonaws.com:11221/AUCN/GetData";
        String playListStr = "";
        DB db = null;
        Gson gson=new Gson();
        try {
            playListStr = hu.get(query, "UTF-8");
            db = gson.fromJson(playListStr,DB.class);
            Config.playLists.addAll(db.pls);
            Config.PL_DETAILS.putAll(db.plDtls);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "OK";
    }

    @Override
    protected void onPostExecute(String result){
//        initFinished = true;
        super.onPostExecute(result);
    }
}
