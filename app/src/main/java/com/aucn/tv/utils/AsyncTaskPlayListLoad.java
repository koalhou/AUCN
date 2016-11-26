package com.aucn.tv.utils;

import android.os.AsyncTask;

import com.aucn.tv.config.Config;
import com.aucn.tv.config.DisplayBase;

import java.util.List;
import java.util.Map;

import static com.aucn.tv.config.Config.defaultImgUrl;


/**
 * Created by mac on 2016/11/6.
 */

public class AsyncTaskPlayListLoad extends AsyncTask <String, Integer, String>{

    @Override
    public String doInBackground(String... params) {
        HttpUtils hu = HttpUtils.getInstance();
        String query = "https://www.googleapis.com/youtube/v3/playlists?part=snippet&maxResults=50&channelId=" + Config.YOUTUBE_CHANNEL_ID + "&key=" + Config.YOUTUBE_API_KEY;
        String playListStr = "";
        Map<String,Object> oriPlayLists = null;
        try {
            playListStr = hu.get(query, "UTF-8");
            oriPlayLists = (Map<String, Object>) JacksonUtils.jsonToMap(playListStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String cacheTemp = "";
        List<Object> items = (List<Object>) oriPlayLists.get("items");
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
                cacheTemp += plid +"&&";
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
        return cacheTemp.substring(0, cacheTemp.length() - 1);
    }

    @Override
    protected void onPostExecute(String result){
        FileUtil.cachePlayList(result);
        Config.initFinished = true;
        super.onPostExecute(result);
    }
}
