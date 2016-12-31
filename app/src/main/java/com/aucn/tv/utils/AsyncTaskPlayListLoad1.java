package com.aucn.tv.utils;

import android.os.AsyncTask;

import com.aucn.tv.config.Config;
import com.aucn.tv.config.DisplayBase;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import static com.aucn.tv.config.Config.defaultImgUrl;


/**
 * Created by mac on 2016/11/6.
 */

public class AsyncTaskPlayListLoad1 extends AsyncTask <String, Integer, String>{

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
    @Override
    public String doInBackground(String... params) {
//        System.out.println("STEP 1 -----------" + sdf.format(new Date()));
        HttpUtils hu = HttpUtils.getInstance(this.getClass().getName());
        String query = "https://www.googleapis.com/youtube/v3/playlists?part=snippet&maxResults=" + Config.MAX_RESULTS + "&channelId=" + Config.YOUTUBE_CHANNEL_ID + "&key=" + Config.YOUTUBE_API_KEY;
        String playListStr = "";
        Map<String,Object> oriPlayLists = null;
        Gson gson=new Gson();
        try {

//            System.out.println("STEP 2 -----------" + sdf.format(new Date()));
            playListStr = hu.get(query, "UTF-8");
            oriPlayLists = gson.fromJson(playListStr,Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        System.out.println("STEP 3 -----------" + sdf.format(new Date()));
        List<Object> items = (List<Object>) oriPlayLists.get("items");
        Object nextPageToken = oriPlayLists.get("nextPageToken");
        while (nextPageToken != null){
            String queryNextPage = query + "&pageToken=" + nextPageToken;
            Map<String,Object> oriPlayListsNext = null;
            try {
                playListStr = hu.get(queryNextPage, "UTF-8");
                oriPlayListsNext = gson.fromJson(playListStr,Map.class);
                nextPageToken = oriPlayListsNext.get("nextPageToken");
                List<Object> itemsNext = (List<Object>) oriPlayListsNext.get("items");
                items.addAll(itemsNext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        System.out.println("STEP 4 -----------" + sdf.format(new Date()));
        for(Object object : items){
            try{
                Map<String, Object> list = (Map<String, Object>)object;
                String plid = (String)list.get("id");
                Map<String,Object> snippet = (Map<String,Object>)list.get("snippet");
                String title = (String)snippet.get("title");
                Map<String,Object> thumbnails = (Map<String,Object>)snippet.get("thumbnails");
                String plImg = "";
                if(thumbnails == null){
                    plImg = defaultImgUrl;
                }else{
                    Map<String,String> medium = (Map<String,String>)thumbnails.get("medium");
                    if(medium == null){
                        Map<String, Object> aDefault = (Map<String, Object>)thumbnails.get("default");
                        plImg = (String)aDefault.get("url");
                    }else{
                        plImg = (String)medium.get("url");
                    }
                    if(plImg == null || "".equals(plImg)){
                        plImg = defaultImgUrl;
                    }
                    plImg = (String)medium.get("url");
                }
                DisplayBase db = new DisplayBase();
                db.entityType = "playList";
                db.entityId = plid;
                db.entityTytle = title;
                db.entityImg = plImg;
                Config.playLists.add(db);
//                if(!PL_IDS.contains(plid))
//                    PL_IDS.add(plid);
//                if(!PL_TITLES.contains(title))
//                    PL_TITLES.add(title);
//                if(!PL_IMGS.contains(plImg))
//                    PL_IMGS.add(plImg);
//                buildPlaylistDetails(plid);
            }catch (Exception e){
                continue;
            }
        }

//        System.out.println("STEP 5 -----------" + sdf.format(new Date()));
//        Config.initFinished = true;
        return "OK";


    }

    @Override
    protected void onPostExecute(String result){
//        initFinished = true;
        super.onPostExecute(result);
    }
}
