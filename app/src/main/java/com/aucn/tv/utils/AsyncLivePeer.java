package com.aucn.tv.utils;

import android.os.AsyncTask;

import com.aucn.tv.config.Config;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

/**
 * Created by mac on 2016/11/6.
 */

public class AsyncLivePeer extends AsyncTask <String, Integer,String>{

    @Override
    public String doInBackground(String... params) {
        try {
            HttpUtils hu = new HttpUtils(this.getClass().getName());
            String liveQuery = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=50&order=date&channelId=" + Config.YOUTUBE_CHANNEL_ID + "&key=" + Config.YOUTUBE_API_KEY;
            String updateToday = hu.get(liveQuery, "UTF-8");
            Gson gson = new Gson();
            Map<String, Object> updateTodayOri = gson.fromJson(updateToday,Map.class);
            List<Object> updateTodayitems = (List<Object>) updateTodayOri.get("items");
            boolean flag = false;
            for (Object o : updateTodayitems) {
                Map<String, Object> vedioDetails = (Map<String, Object>) o;
                Map<String, String> vvid = (Map<String, String>) vedioDetails.get("id");
                Map<String, Object> snippet = (Map<String, Object>) vedioDetails.get("snippet");
                String liveFlag = (String) snippet.get("liveBroadcastContent");
                if("live".equals(liveFlag)){
                    Config.liveId = vvid.get("videoId");
                    flag = true;
                }
            }
            if(!flag){
                Config.liveId = "";
            }
            return "OFF";
        }catch (Exception e){
            e.printStackTrace();
            return "OFF";
        }
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }
}
