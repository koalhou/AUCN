package com.aucn.tv.utils;

import android.os.AsyncTask;

import com.aucn.tv.config.Config;
import com.aucn.tv.config.DisplayBase;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by mac on 2016/11/6.
 */

public class AsyncUpdateTodayDataLoad extends AsyncTask <String, Integer, String>{

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public String defaultImgUrl = "https://i.ytimg.com/vi/1AVTGtZn28Q/mqdefault_live.jpg";

    @Override
    public String doInBackground(String... params) {
        HttpUtils hu = HttpUtils.getInstance(this.getClass().getName());
        String updateTodayQuery = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=50&order=date&channelId=" + Config.YOUTUBE_CHANNEL_ID + "&key=" + Config.YOUTUBE_API_KEY;
        String updateToday = "";
        Map<String,Object> updateTodayOri = null;
        try {
            updateToday = hu.get(updateTodayQuery,"UTF-8");
            Gson gson = new Gson();
            updateTodayOri = gson.fromJson(updateToday,Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
            List<Object> updateTodayitems = (List<Object>) updateTodayOri.get("items");
        Config.updateToday.clear();
        for(Object o : updateTodayitems){
            try {
                Map<String, Object> vedioDetails = (Map<String,Object>)o;
                Map<String, String> vvid = (Map<String,String>)vedioDetails.get("id");
                String vid = vvid.get("videoId");
                Map<String, Object> snippet = (Map<String,Object>)vedioDetails.get("snippet");
                String title = (String)snippet.get("title");
                Map<String,Object> thumbnails = (Map<String,Object>)snippet.get("thumbnails");
//
                String publishedAt = (String)snippet.get("publishedAt");
                String publishTime = publishedAt.substring(0,10);
                if(sdf.format(new Date()).equals(publishTime)){
                    String tDimg = "";
                    if(thumbnails == null){
                        tDimg = defaultImgUrl;
                    }else{
                        Map<String,String> medium = (Map<String,String>)thumbnails.get("medium");
                        if(medium == null){
                            Map<String, Object> aDefault = (Map<String, Object>)thumbnails.get("default");
                            tDimg = (String)aDefault.get("url");
                        }else{
                            tDimg = (String)medium.get("url");
                        }
                        if(tDimg == null || "".equals(tDimg)){
                            tDimg = defaultImgUrl;
                        }
                        tDimg = (String)medium.get("url");
                    }
//                    UTD_IDS.add(vid);
//                    UTD_TITLES.add(title);
//                    UTD_IMGS.add(tDimg);
                    DisplayBase db = new DisplayBase();
                    db.entityType = "updateToday";
                    db.entityId = vid;
                    db.entityTytle = title;
                    db.entityImg = tDimg;
                    Config.updateToday.add(db);
                }
            }catch (Exception e){
                continue;
            }
        }
        return "";
    }

    @Override
    protected void onPostExecute(String result)
    {
//        if(allData!=null && result!=null) {
//            allData.putAll(result);
//        }
        Config.updateTodayFinished = true;

        super.onPostExecute(result);
    }
}
